package org.example.securitylogintest.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.securitylogintest.jpa.UserService;
import org.example.securitylogintest.vo.RequestUserDto;
import org.example.securitylogintest.vo.ResponseUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        RequestUserDto defaultUserDto = new RequestUserDto("abcd@naver.com", "1234", "user1");

        model.addAttribute("signupRequest", defaultUserDto);
        return "signup";  // /WEB-INF/jsp/signup.jsp로 렌더링
    }

    @PostMapping("/signup")
    public String handleSignUp(@ModelAttribute("signupRequest") RequestUserDto requestUserDto) {
        // 회원가입 로직을 처리합니다.
        // 예를 들어, 사용자 정보를 DB에 저장하고, 로그인 페이지로 리다이렉트합니다.

        // 회원가입 완료 후 로그인 페이지로 리다이렉트
        userService.createUser(requestUserDto);

        return "redirect:/signin";  // /signin 경로로 리다이렉트
    }

    @GetMapping("/signin")
    public String signIn(Model model) {
        RequestUserDto user1 = new RequestUserDto();
        user1.setEmail("abcd@naver.com");
        user1.setPassword("1234");
        model.addAttribute("signinRequest", user1);

        return "signin";
    }

    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        ResponseUserDto userInfo = userService.info();

        // Optional로 감싸기
        Optional<Object> optional = Optional.ofNullable(userInfo);

        // 값이 존재하면 true, null이면 false를 String으로 출력
        log.info("Value present: {}", optional.isPresent() ? "true" : "null");


        if (userInfo != null) {
            // 모델에 사용자 정보 추가
            model.addAttribute("user", userInfo);
            log.info("userInfo:{}",userInfo.getName());
        } else {
            // 인증되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }
        // home.jsp로 사용자 정보 전달
        return "home";  // home.jsp를 리턴
    }
}

