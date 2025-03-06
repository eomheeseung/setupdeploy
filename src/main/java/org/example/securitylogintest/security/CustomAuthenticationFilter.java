package org.example.securitylogintest.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.securitylogintest.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
        //            RequestUserDto credentials
//                    = new ObjectMapper().readValue(request.getInputStream(), RequestUserDto.class);

//            UsernamePasswordAuthenticationToken authenticationToken
//                    = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());


        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(request.getParameter("email"),
                request.getParameter("password"));

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler =
                new CustomAuthenticationSuccessHandler(jwtTokenProvider);

        customAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
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
