package boosters.fundboost.global.utils;

import boosters.fundboost.global.exception.SearchException;
import boosters.fundboost.global.response.code.status.ErrorStatus;

public class SearchValidator {
    public static void ValidateKeyword(String keyword) {
        if(keyword.length()<2) {
            throw new SearchException(ErrorStatus.KEYWORD_LENGTH_ERROR);
        }
    }
}
