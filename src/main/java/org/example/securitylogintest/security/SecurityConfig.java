package org.example.securitylogintest.security;

import lombok.RequiredArgsConstructor;
import org.example.securitylogintest.jwt.JWTAuthenticationFilter;
import org.example.securitylogintest.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter
                = new CustomAuthenticationFilter(jwtTokenProvider, authenticationManager());

        customAuthenticationFilter.setFilterProcessesUrl("/signIn");

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(customizer -> {
                    customizer.requestMatchers("/info").authenticated();
                    customizer.requestMatchers("/signUp", "/signIn", "/h2-console/**").permitAll();
                })
                .headers(customizer -> customizer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .userDetailsService(userDetailsService)
                // JWT 필터를 인증 필터보다 먼저 실행하도록 설정
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  // JWT 필터가 먼저 실행되도록
                .addFilter(customAuthenticationFilter) // 인증 필터 두 번째로 추가
                .build();
    }

}
