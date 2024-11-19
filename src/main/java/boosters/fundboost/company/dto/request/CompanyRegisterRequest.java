package boosters.fundboost.company.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRegisterRequest {
    private String email;
    private String password;
    private String businessNumber;
}