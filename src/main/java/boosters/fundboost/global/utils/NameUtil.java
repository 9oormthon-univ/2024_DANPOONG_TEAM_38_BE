package boosters.fundboost.global.utils;

import static java.util.UUID.randomUUID;

public class NameUtil {
    public static String createNickname() {
        String uuid = randomUUID().toString().replace("-", "");
        return "user_" + uuid.substring(0, 10);
    }
}
