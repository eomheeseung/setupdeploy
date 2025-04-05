package org.example.securitylogintest.oauth2.kakao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * 한국 기업들은 header basic 방식이 아닌 http body에 넣는 것이 보안상으로 더 낫다고 판단하여 이렇게 구현을 함
 * oauth2의 표준에는 어긋남
 * 그래서 token을 요청받을 때 body에 넣어야 하기 때문에 custom이 필요...
 */
@Component
@Slf4j
public class KakaoTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest
                                                              request) {

        OAuth2AuthorizationRequest authRequest = request.getAuthorizationExchange().getAuthorizationRequest();
        OAuth2AuthorizationResponse authResponse = request.getAuthorizationExchange().getAuthorizationResponse();

        ClientRegistration clientRegistration = request.getClientRegistration();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();

        formParams.add("grant_type", "authorization_code");
        formParams.add("client_id", clientRegistration.getClientId());
        formParams.add("redirect_uri", clientRegistration.getRedirectUri());
        formParams.add("code", authResponse.getCode());

        if (clientRegistration.getClientSecret() != null) {
            formParams.add("client_secret", clientRegistration.getClientSecret());
        }

        HttpEntity<MultiValueMap<String, String>> httpRequest = new HttpEntity<>(formParams, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                clientRegistration.getProviderDetails().getTokenUri(),
                HttpMethod.POST,
                httpRequest,
                new ParameterizedTypeReference<>() {
                }
        );

        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null) {
            throw new OAuth2AuthenticationException(new OAuth2Error("No token received"));
        }

        log.info("token received");

        return OAuth2AccessTokenResponse.withToken((String) responseBody.get("access_token"))
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .expiresIn(((Number) responseBody.get("expires_in")).longValue())
                .refreshToken((String) responseBody.get("refresh_token"))
                .scopes(clientRegistration.getScopes())
                .build();
    }
}
