package boosters.fundboost.global.config;

import boosters.fundboost.global.security.handler.resolver.AuthInfoArgumentResolver;
import boosters.fundboost.global.security.handler.resolver.AuthUserArgumentResolver;
import boosters.fundboost.global.security.handler.resolver.TokenArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuthUserArgumentResolver authUserArgumentResolver;
    private final TokenArgumentResolver tokenArgumentResolver;
    private final AuthInfoArgumentResolver authInfoArgumentResolver;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://2024-danpoong-team-38-fe.vercel.app", "https://fb-server.shop","http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(6000);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authUserArgumentResolver);
        resolvers.add(tokenArgumentResolver);
        resolvers.add(authInfoArgumentResolver);
    }
}
