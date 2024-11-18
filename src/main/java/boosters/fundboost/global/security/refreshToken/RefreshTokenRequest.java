package boosters.fundboost.global.security.refreshToken;

import lombok.Getter;

@Getter
public class RefreshTokenRequest {
    private String email;
    private String refreshToken;
}