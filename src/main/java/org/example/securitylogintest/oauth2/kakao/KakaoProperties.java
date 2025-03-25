package org.example.securitylogintest.oauth2.kakao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoProperties {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String authenticationUri;
    private final String tokenUri;
    private final String userInfoUri;
    private final String clientName;
    private final String authenticationGrantType;

    public KakaoProperties(@Value("${security.oauth2.client.registration.kakao.client-id}") String clientId,
                           @Value("${security.oauth2.client.registration.kakao.client-secret}") String clientSecret,
                           @Value("${security.oauth2.client.registration.kakao.redirect-uri}") String redirectUri,
                           @Value("${security.oauth2.client.provider.kakao.authorization-uri}") String authenticationUri,
                           @Value("${security.oauth2.client.provider.kakao.token-uri}") String tokenUri,
                           @Value("${security.oauth2.client.provider.kakao.user-info-uri}") String userInfoUri,
                           @Value("${security.oauth2.client.registration.kakao.client-name}")String clientName,
                           @Value("${security.oauth2.client.registration.kakao.authorization-grant-type}") String authenticationGrantType) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.authenticationUri = authenticationUri;
        this.tokenUri = tokenUri;
        this.userInfoUri = userInfoUri;
        this.clientName = clientName;
        this.authenticationGrantType = authenticationGrantType;
    }


    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getAuthenticationUri() {
        return authenticationUri;
    }

    public String getTokenUri() {
        return tokenUri;
    }

    public String getUserInfoUri() {
        return userInfoUri;
    }

    public String getClientName() {
        return clientName;
    }

    public String getAuthenticationGrantType() {
        return authenticationGrantType;
    }
}
