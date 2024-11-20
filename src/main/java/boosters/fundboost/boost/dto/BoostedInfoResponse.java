package boosters.fundboost.boost.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoostedInfoResponse {
    private double achievementRate;
    private String achievementAmount;
    private long leftDays;
    private long boostedUserCount;
}
