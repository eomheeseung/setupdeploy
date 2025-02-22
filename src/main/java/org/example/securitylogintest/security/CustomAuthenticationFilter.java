package org.example.securitylogintest.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.securitylogintest.jwt.JwtTokenProvider;
import org.example.securitylogintest.vo.RequestUserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            RequestUserDto credentials
                    = new ObjectMapper().readValue(request.getInputStream(), RequestUserDto.class);

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // 인증 성공 시 사용자 정보를 가져옴
        User user = (User) authResult.getPrincipal();

        // JWT 생성
        String token = jwtTokenProvider.createToken(user.getUsername());

        response.addHeader("Authorization", "Bearer " + token);

        SecurityContextHolder.getContext().setAuthentication(authResult);

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<Object, Object> responseMap = new HashMap<>();
        responseMap.put("email", user.getUsername());


        String result = objectMapper.writeValueAsString(responseMap);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("content-type", "application/json");
        response.getWriter().write(result);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        // 인증 실패 시 처리
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", "Authentication failed");
        responseBody.put("message", failed.getMessage());

        new ObjectMapper().writeValue(response.getWriter(), responseBody);
    }
}
