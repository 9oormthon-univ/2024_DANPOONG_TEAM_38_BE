package boosters.fundboost.global.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PeriodUtil {
    public static String localDateToPeriodFormat(LocalDate startDate, LocalDate endDate) {
        return String.format("%s-%s", LocalDateToString(startDate), LocalDateToString(endDate));
    }

    private static String LocalDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return date.format(formatter);
    }
}
