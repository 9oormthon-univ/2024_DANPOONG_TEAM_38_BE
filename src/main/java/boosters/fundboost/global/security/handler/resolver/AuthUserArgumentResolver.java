package boosters.fundboost.global.security.handler.resolver;

import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.global.security.handler.annotation.AuthUser;
import boosters.fundboost.global.security.util.SecurityUtils;
import boosters.fundboost.user.auth.exception.AuthException;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class)
                && parameter.hasParameterAnnotation(AuthUser.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getName().equals("anonymousUser")) {
                throw new AuthException(ErrorStatus.UNAUTHORIZED_USER);
            }
        }

        String userEmail = SecurityUtils.getAuthUserEmail(authentication);
        return userService.getUser(userEmail);
    }
}
