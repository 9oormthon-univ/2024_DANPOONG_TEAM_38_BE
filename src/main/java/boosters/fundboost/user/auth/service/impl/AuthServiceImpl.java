package boosters.fundboost.user.auth.service.impl;

import boosters.fundboost.global.redis.service.RefreshTokenService;
import boosters.fundboost.global.security.jwt.JwtTokenProvider;
import boosters.fundboost.global.security.provider.KakaoAuthProvider;
import boosters.fundboost.user.auth.converter.AuthConverter;
import boosters.fundboost.user.auth.dto.AuthResponse.KakaoOAuthToken;
import boosters.fundboost.user.auth.dto.AuthResponse.OAuthResponse;
import boosters.fundboost.user.auth.dto.AuthResponse.TokenRefreshResponse;
import boosters.fundboost.user.auth.dto.KakaoProfile;
import boosters.fundboost.user.auth.service.AuthService;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final KakaoAuthProvider kakaoAuthProvider;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenDuration;

    @Override
    public OAuthResponse kakaoLogin(String code) {
        KakaoOAuthToken kakaoToken = kakaoAuthProvider.getAccessToken(code);
        KakaoProfile kakaoProfile = kakaoAuthProvider.getKakaoProfile(kakaoToken.getAccessToken());

        Optional<User> user = userRepository.findByEmail(kakaoProfile.getKakao_account().getEmail());

        AtomicBoolean isLogin = new AtomicBoolean(false);
        User currentUser = user.orElseGet(() -> {
            isLogin.set(true);
            return userRepository.save(AuthConverter.toUser(kakaoProfile));
        });
        String accessToken = jwtTokenProvider.createToken(currentUser.getEmail());
        String refreshToken = saveRefreshToken(currentUser.getEmail());

        return AuthConverter.toOAuthResponse(accessToken, refreshToken, isLogin.get(), currentUser);
    }

    @Override
    public void kakaoLogout(User user) {
        refreshTokenService.deleteToken(user.getEmail());
    }

    @Override
    @Transactional
    public TokenRefreshResponse refresh(String refreshToken) {
        String email = jwtTokenProvider.getId(refreshToken);

        String newAccessToken = jwtTokenProvider.createToken(email);
        String newRefreshToken = saveRefreshToken(email);

        return AuthConverter.toTokenRefreshResponse(newAccessToken, newRefreshToken);
    }

    private String saveRefreshToken(String email) {
        String refreshToken = jwtTokenProvider.createRefreshToken(email);
        refreshTokenService.saveRefreshToken(email, refreshToken, refreshTokenDuration);

        return refreshToken;
    }
}
