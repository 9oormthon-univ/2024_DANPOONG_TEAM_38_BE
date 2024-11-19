package boosters.fundboost.company.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompanyLoginResponse {
    private String accessToken;
    private String refreshToken;
}
