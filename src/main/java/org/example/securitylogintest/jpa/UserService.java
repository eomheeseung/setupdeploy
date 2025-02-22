package org.example.securitylogintest.jpa;

import org.example.securitylogintest.vo.RequestUserDto;
import org.example.securitylogintest.vo.ResponseUserDto;

public interface UserService {
    ResponseUserDto createUser(RequestUserDto requestUserDto);

    ResponseUserDto info();
}
