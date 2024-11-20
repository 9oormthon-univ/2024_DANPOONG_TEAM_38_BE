package boosters.fundboost.boost.converter;

import boosters.fundboost.boost.dto.BoostedInfoResponse;
import boosters.fundboost.global.utils.AmountUtil;
import boosters.fundboost.global.utils.CalculatorUtil;
import boosters.fundboost.project.domain.Project;

import java.time.LocalDate;

public class BoostedInfoConverter {
    public static BoostedInfoResponse toBoostedInfoResponse(Project project, long achievementAmount, long boostedUserCount) {
        return BoostedInfoResponse.builder()
                .achievementRate(CalculatorUtil.calculateProgressRate(achievementAmount, project.getTargetAmount()))
                .achievementAmount(AmountUtil.formatAmount(achievementAmount))
                .leftDays(CalculatorUtil.calculateDaysLeft(LocalDate.now(), project.getEndDate()))
                .boostedUserCount(boostedUserCount)
                .build();
    }
}
