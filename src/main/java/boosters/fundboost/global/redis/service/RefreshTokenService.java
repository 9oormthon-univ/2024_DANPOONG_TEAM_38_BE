package boosters.fundboost.global.redis.service;

import boosters.fundboost.global.redis.RefreshToken;
import boosters.fundboost.global.redis.exception.TokenException;
import boosters.fundboost.global.redis.repository.RefreshTokenRepository;
import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;


    @Transactional
    public void saveRefreshToken(String email, String refreshToken, long refreshTokenValidity) {
        String redisKey = "refresh_token:" + email;
        redisTemplate.opsForValue().set(
                redisKey,
                refreshToken,
                refreshTokenValidity,
                TimeUnit.MILLISECONDS
        );
        RefreshToken token = RefreshToken.builder()
                .token(refreshToken).id(email).build();

        refreshTokenRepository.save(token);
    }

    @Transactional
    public void deleteToken(String email) {
        RefreshToken refreshToken =
                refreshTokenRepository
                        .findById(email)
                        .orElseThrow(() -> new TokenException(ErrorStatus.NOT_FOUND_TOKEN));

        refreshTokenRepository.delete(refreshToken);
    }
}

