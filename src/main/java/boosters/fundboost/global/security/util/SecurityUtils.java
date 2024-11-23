package boosters.fundboost.global.security.util;

import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.global.security.userdetails.CustomUserDetails;
import boosters.fundboost.user.exception.UserException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new UserException(ErrorStatus.UNAUTHORIZED_USER);
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }

    public static String getAuthUserEmail(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal == null || principal.getClass() == String.class) {
            throw new UserException(ErrorStatus.USER_NOT_FOUND);
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) authentication;

        return authenticationToken.getName();
    }
}