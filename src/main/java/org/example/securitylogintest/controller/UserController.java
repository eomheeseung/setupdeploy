package org.example.securitylogintest.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.securitylogintest.jpa.UserService;
import org.example.securitylogintest.vo.RequestUserDto;
import org.example.securitylogintest.vo.ResponseUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<ResponseUserDto> signUp(@RequestBody RequestUserDto requestUserDto) {
        ResponseUserDto result = userService.createUser(requestUserDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/info")
    public ResponseEntity<ResponseUserDto> userInfo(HttpServletRequest request) {

        ResponseUserDto result = userService.info();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
