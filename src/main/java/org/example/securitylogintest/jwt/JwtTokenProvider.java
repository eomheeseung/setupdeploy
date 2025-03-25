package org.example.securitylogintest.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Configuration
public class JwtTokenProvider {

    private final String issuer;

    private final long expiredTime;

    private final JWTVerifier jwtVerifier;

    private final Algorithm algorithm;


    public JwtTokenProvider(@Value("${jwt.issuer}") String issuer,
                            @Value("${jwt.expired-time}") long expiredTime,
                            @Value("${jwt.secret-key}") String secretKey) {
        this.issuer = issuer;
        this.expiredTime = expiredTime;
        this.algorithm = Algorithm.HMAC256(secretKey); // 필드에 저장
        this.jwtVerifier = JWT.require(algorithm).build(); // 알고리즘을 활용하여 Verifier 생성
    }

    public String createToken(String username) {

        Date expiredAt = Date.from(Instant.now().plus(expiredTime, ChronoUnit.MINUTES));

        return JWT.create()
                .withSubject(username)  // 사용자명 설정
                .withIssuer(issuer)  // 발급자 정보 추가
                .withExpiresAt(expiredAt)  // 만료 시간 설정
                .sign(algorithm);  // 사인(Sign) 적용
    }


    public boolean isTokenValid(String token) {
        try {
            jwtVerifier.verify(token);
            return true;
        } catch (TokenExpiredException e) {
            return false; // 만료된 토큰 처리
        } catch (JWTVerificationException e) {
            return false; // 서명 검증 실패
        }
    }

    public Authentication getAuthentication(String token) {
        // JWT 서명 및 만료 검증
        DecodedJWT decodedJWT = jwtVerifier.verify(token); // 검증된 JWT 사용

        // 사용자 이름 추출
        String username = decodedJWT.getSubject();

        // 권한(roles) 추출 (토큰에 roles 정보가 있을 경우)
        String roles = decodedJWT.getClaim("roles").asString();

        // Authentication 객체 생성 및 반환
        return new UsernamePasswordAuthenticationToken(
                username,
                null,  // 비밀번호는 JWT 기반 인증에서 필요 없음
                AuthorityUtils.commaSeparatedStringToAuthorityList(roles)  // 권한 목록
        );
    }
}
