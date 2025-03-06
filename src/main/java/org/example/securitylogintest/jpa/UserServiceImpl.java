package org.example.securitylogintest.jpa;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.securitylogintest.jwt.JwtTokenProvider;
import org.example.securitylogintest.vo.RequestUserDto;
import org.example.securitylogintest.vo.ResponseUserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private ModelMapper modelMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public ResponseUserDto createUser(RequestUserDto requestUserDto) {
        UserEntity userEntity = modelMapper.map(requestUserDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userRepository.save(userEntity);

        return modelMapper.map(userEntity, ResponseUserDto.class);
    }

    @Override
    public ResponseUserDto info() {
        // SecurityContext에서 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();



        // Authentication 객체에서 username을 가져오기
        String username = (String) authentication.getPrincipal();

        // username을 이용해 UserEntity 조회
        UserEntity userEntity = userRepository.findByEmail(username);

        // DB에서 조회한 정보로 ResponseUserDto 생성
        ResponseUserDto responseUserDto = new ResponseUserDto();
        responseUserDto.setEmail(userEntity.getEmail());
        responseUserDto.setName(userEntity.getName());

        return responseUserDto;
    }
}
