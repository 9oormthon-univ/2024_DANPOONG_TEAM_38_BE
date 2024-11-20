package boosters.fundboost.global.utils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalculatorUtil {
    private static final String DECIMAL_PATTERN = "#.0";
    private static final int PERCENTAGE = 100;

    public static double calculateProgressRate(long achievedAmount, long targetAmount) {
        DecimalFormat df = new DecimalFormat(DECIMAL_PATTERN);
        return Double.parseDouble(df.format(((double) achievedAmount / targetAmount) * PERCENTAGE));
    }

    public static long calculateDaysLeft(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}
