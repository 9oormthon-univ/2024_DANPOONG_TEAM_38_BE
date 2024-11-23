package boosters.fundboost.user.auth.service;


import boosters.fundboost.user.auth.dto.AuthResponse.TokenRefreshResponse;
import boosters.fundboost.user.auth.dto.AuthResponse.OAuthResponse;
import boosters.fundboost.user.domain.User;

public interface AuthService {
    OAuthResponse kakaoLogin(String authorizationCode);

    void kakaoLogout(User user);

    TokenRefreshResponse refresh(String refreshToken);
}
