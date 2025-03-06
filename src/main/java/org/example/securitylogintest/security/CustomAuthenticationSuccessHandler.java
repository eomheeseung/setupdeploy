package org.example.securitylogintest.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.securitylogintest.jwt.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        // 인증된 사용자 정보 가져오기
        User user = (User) authentication.getPrincipal();

        // JWT 생성
        String token = jwtTokenProvider.createToken(user.getUsername());

        // JWT를 HttpOnly 쿠키에 저장 (XSS 보호)
        Cookie jwtCookie = new Cookie("Authorization", "Bearer " + token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60); // 1시간
        response.addCookie(jwtCookie);


        response.addCookie(jwtCookie);

        // /home으로 리다이렉트
        response.sendRedirect("/home");
    }

}
