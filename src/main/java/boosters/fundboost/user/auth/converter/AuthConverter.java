package boosters.fundboost.user.auth.converter;

import boosters.fundboost.global.utils.NameUtil;
import boosters.fundboost.user.auth.dto.AuthResponse.TokenRefreshResponse;
import boosters.fundboost.user.auth.dto.AuthResponse.OAuthResponse;
import boosters.fundboost.user.auth.dto.KakaoProfile;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.domain.enums.Tag;
import boosters.fundboost.user.domain.enums.UserType;

public class AuthConverter {
    private final static String DEFAULT_IMAGE = "https://fundboost-bucket.s3.ap-northeast-2.amazonaws.com/logo.png";

    public static User toUser(KakaoProfile kakaoProfile) {
        return User.builder()
                .email(kakaoProfile.getKakao_account().getEmail())
                .name(NameUtil.createNickname())
                .userType(UserType.USER)
                .image(DEFAULT_IMAGE)
                .tag(Tag.SEASSAK_INVESTOR)
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
