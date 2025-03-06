package org.example.securitylogintest.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.securitylogintest.jpa.UserEntity;
import org.example.securitylogintest.jpa.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 해당 클래스가 사용되지 않음.
 *  언제사용되고 loadUserByUsername()는 언제 사용하는지?
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity findedUserEntity = userRepository.findByEmail(username);

        // 권한 설정 (여기서는 'ROLE_USER'를 부여)
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // 예시로 ROLE_USER 권한 부여

        // User 객체를 반환하며, 권한을 포함시킨다

        log.info("UserDetailsService call");

        if (findedUserEntity == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(findedUserEntity.getEmail(), findedUserEntity.getPassword(), authorities);
    }
}
