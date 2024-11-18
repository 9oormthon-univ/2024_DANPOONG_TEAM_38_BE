package boosters.fundboost.global.security.provider;

import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.user.auth.dto.AuthResponse.KakaoOAuthToken;
import boosters.fundboost.user.auth.dto.KakaoProfile;
import boosters.fundboost.user.auth.exception.AuthException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoAuthProvider {
    @Value("${kakao.client}")
    private String client;

    @Value("${kakao.redirect-uri}")
    private String redirect;

    public KakaoOAuthToken getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client);
        params.add("redirect_uri", redirect);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);
        ResponseEntity<String> response =
                restTemplate.exchange(
                        "https://kauth.kakao.com/oauth/token",
                        HttpMethod.POST,
                        kakaoTokenRequest,
                        String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoOAuthToken oAuthToken;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), KakaoOAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new AuthException(ErrorStatus.INVALID_REQUEST_INFO);
        }

        return oAuthToken;
    }

    public KakaoProfile getKakaoProfile(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<MultiValueMap<String, String>> ProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        "https://kapi.kakao.com/v2/user/me",
                        HttpMethod.POST,
                        ProfileRequest,
                        String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        KakaoProfile kakaoProfile;
        try {
            kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new AuthException(ErrorStatus.INVALID_REQUEST_INFO);
        }

        return kakaoProfile;
    }
}