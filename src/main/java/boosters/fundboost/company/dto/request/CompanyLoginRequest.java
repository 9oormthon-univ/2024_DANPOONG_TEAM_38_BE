package boosters.fundboost.company.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyLoginRequest {
    private String email;
    private String password;
}
