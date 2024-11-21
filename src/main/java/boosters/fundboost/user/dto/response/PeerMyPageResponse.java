package boosters.fundboost.user.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeerMyPageResponse {
    private UserMyPageResponse userInfo;
    private String title;
    private String content;
    private boolean isCompany;
}
