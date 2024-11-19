package boosters.fundboost.user.service;


import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.response.UserMyPageResponse;

public interface UserService {
    User getUser(Long userId);

    UserMyPageResponse getMyPage(User user);
}
