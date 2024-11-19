package boosters.fundboost.user.auth.converter;

import boosters.fundboost.user.auth.dto.AuthResponse.TokenRefreshResponse;
import boosters.fundboost.user.auth.dto.AuthResponse.OAuthResponse;
import boosters.fundboost.user.auth.dto.KakaoProfile;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.domain.enums.UserType;

public class AuthConverter {
    public static User toUser(KakaoProfile kakaoProfile) {
        //TODO: 이름 닉네임, 이미지 디폴트 값 설정 필요
        return User.builder()
                .email(kakaoProfile.getKakao_account().getEmail())
                .name("tmp")
                .userType(UserType.USER)
                .image("DEFAULT IMAGE")
                .build();

    }

    public static OAuthResponse toOAuthResponse(String accessToken, String refreshToken, boolean isLogin, User user) {
        return OAuthResponse.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isLogin(isLogin)
                .build();
    }

    public static TokenRefreshResponse toTokenRefreshResponse(String accessToken, String refreshToken) {
        return TokenRefreshResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
