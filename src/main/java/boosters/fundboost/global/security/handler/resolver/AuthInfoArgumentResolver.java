package boosters.fundboost.global.security.handler.resolver;

import boosters.fundboost.global.security.handler.annotation.AuthInfo;
import boosters.fundboost.global.security.util.SecurityUtils;
import boosters.fundboost.user.auth.exception.AuthException;
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
public class AuthInfoArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Long.class)
                && parameter.hasParameterAnnotation(AuthInfo.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if ((authentication == null) || authentication.getName().equals("anonymousUser")) {
            return null;
        }

        String userEmail = SecurityUtils.getAuthUserEmail(authentication);
        return userService.getUser(userEmail).getId();
    }

}
