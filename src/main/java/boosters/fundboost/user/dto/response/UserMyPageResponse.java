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
public class UserMyPageResponse {
    private String name;
    private String image;
    private String link;
    private String tag;
    private long followingCount;
    private long followerCount;
}
