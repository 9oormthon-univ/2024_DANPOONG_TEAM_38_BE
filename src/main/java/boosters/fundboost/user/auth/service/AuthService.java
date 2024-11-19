package boosters.fundboost.user.auth.service;


import boosters.fundboost.user.auth.dto.AuthResponse.TokenRefreshResponse;
import boosters.fundboost.user.auth.dto.AuthResponse.OAuthResponse;

public interface AuthService {
    OAuthResponse kakaoLogin(String authorizationCode);

    void kakaoLogout(Long userId);

    TokenRefreshResponse refresh(String refreshToken);
}
