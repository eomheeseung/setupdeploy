package org.example.securitylogintest.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
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

    private final String secretKey;

    public JwtTokenProvider(@Value("${jwt.issuer}") String issuer,
                            @Value("${jwt.expired-time}") long expiredTime,
                            @Value("${jwt.secret-key}") String secretKey) {
        this.issuer = issuer;
        this.expiredTime = expiredTime;
        this.secretKey = secretKey;
    }

    public String createToken(String username) {
        return JWT
                .create()
                .withSubject(username)
                .withIssuer(issuer)
                .withExpiresAt(Date.from(Instant.now().plus(expiredTime, ChronoUnit.MINUTES)))
                .sign(Algorithm.HMAC256(secretKey));
    }

    // JWT 토큰 유효성 검증
    public boolean isTokenValid(String token) {
        try {
            // JWT 토큰 파싱
            DecodedJWT decodedJWT = decodeJWT(token);

            // 만료일 확인
            if (decodedJWT.getExpiresAt().before(new Date())) {
                return false;  // 토큰 만료
            }

            return true;
        } catch (JWTVerificationException e) {
            // 서명 검증 실패 또는 다른 오류 발생 시 false 반환
            return false;
        }
    }

    // JWT 디코딩 및 서명 검증
    private DecodedJWT decodeJWT(String token) {
        // 서명 검증을 위한 알고리즘 설정
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        // JWTVerifier 생성 (서명 검증 및 토큰 검증 처리)
        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        // JWT 토큰 검증 (서명, 만료 등)
        return verifier.verify(token);
    }

    // JWT 토큰을 사용하여 Authentication 객체를 생성
    public Authentication getAuthentication(String token) {
        // JWT 디코딩
        DecodedJWT decodedJWT = JWT.decode(token);

        // 사용자 이름 추출
        String username = decodedJWT.getSubject();  // 사용자 이름 (subject)

        // 권한(roles) 추출 (옵션: 토큰에 권한 정보가 있다면)
        String roles = decodedJWT.getClaim("roles").asString();

        // 사용자 정보와 권한을 바탕으로 Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(
                username,
                null,  // 비밀번호는 필요 없으므로 null
                AuthorityUtils.commaSeparatedStringToAuthorityList(roles)  // 권한 목록
        );
    }


}
