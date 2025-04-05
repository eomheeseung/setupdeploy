package org.example.securitylogintest.oauth2.kakao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * TODO
 *  received accessToken으로 KakaoUserDetailService를 구현하여, 사용자의 정보를 DB에 넣고, redirect
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final KakaoProperties kakaoProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String requestUrlFromHeader = request.getHeader("Request URL");

        log.info("header value:{}", requestUrlFromHeader);
    }
}
