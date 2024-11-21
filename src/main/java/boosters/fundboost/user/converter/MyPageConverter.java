package boosters.fundboost.user.converter;

import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.response.PeerMyPageResponse;
import boosters.fundboost.user.dto.response.UserMyPageResponse;

public class MyPageConverter {
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

    public static PeerMyPageResponse toPeerMyPageResponse(User user, long followingCount, long followerCount) {
        return PeerMyPageResponse.builder()
                .userInfo(toUserMyPageResponse(user, followingCount, followerCount))
                .title(user.getTitle())
                .content(user.getContent())
                .build();
    }
}
