package org.example.securitylogintest.oauth2.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginController {
    private final KakaoProperties kakaoProperties;

    // 카카오 로그인 요청 URL
//    @GetMapping("/login/kakao")
//    public String kakaoLogin() {
//        return "redirect:/login/oauth2/authorization/kakao";
//    }
}
