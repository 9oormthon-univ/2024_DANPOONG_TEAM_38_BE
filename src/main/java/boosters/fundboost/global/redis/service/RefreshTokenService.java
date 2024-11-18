package boosters.fundboost.global.redis.service;

import boosters.fundboost.global.redis.RefreshToken;
import boosters.fundboost.global.redis.exception.TokenException;
import boosters.fundboost.global.redis.repository.RefreshTokenRepository;
import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public boolean isEqualsToken(String refreshToken) {
        RefreshToken savedRefreshToken =
                refreshTokenRepository
                        .findById(jwtTokenProvider.getId(refreshToken))
                        .orElseThrow(() -> new TokenException(ErrorStatus.NOT_FOUND_TOKEN));
        return savedRefreshToken.getToken().equals(refreshToken);
    }

    @Transactional
    public void saveToken(String refreshToken) {
        RefreshToken newRefreshToken =
                refreshTokenRepository.save(
                        RefreshToken.builder()
                                .id(jwtTokenProvider.getId(refreshToken))
                                .token(refreshToken)
                                .build());
        refreshTokenRepository.save(newRefreshToken);
    }

    @Transactional
    public void deleteToken(Long userId) {
        RefreshToken refreshToken =
                refreshTokenRepository
                        .findById(userId)
                        .orElseThrow(() -> new TokenException(ErrorStatus.NOT_FOUND_TOKEN));

        refreshTokenRepository.delete(refreshToken);
    }
}

