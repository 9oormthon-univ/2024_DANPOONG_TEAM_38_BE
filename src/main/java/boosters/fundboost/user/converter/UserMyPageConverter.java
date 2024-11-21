package boosters.fundboost.user.converter;

import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.response.UserMyPageResponse;

public class UserMyPageConverter {
    public static UserMyPageResponse toUserMyPageResponse(User user, long followingCount, long followerCount) {
        return UserMyPageResponse.builder()
                .name(user.getName())
                .image(user.getImage())
                .link(user.getLink())
                .tag(user.getTag().getValue())
                .followingCount(followingCount)
                .followerCount(followerCount)
                .build();
    }
}
