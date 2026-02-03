package pro.sorokovsky.massageplansserver.configurer;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import pro.sorokovsky.massageplansserver.converter.JwtAuthenticationConverter;
import pro.sorokovsky.massageplansserver.service.TokenStorage;

@RequiredArgsConstructor
@Builder
public class JwtAuthenticationConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenStorage accessTokenStorage;
    private final AuthenticationEntryPoint failureEntryPoint;

    @Override
    public void init(HttpSecurity builder) {

    }

    @Override
    public void configure(HttpSecurity builder) {
        final var converter = JwtAuthenticationConverter
                .builder()
                .accessTokenStorage(accessTokenStorage)
                .build();
        final var authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        final var authenticationFilter = new AuthenticationFilter(authenticationManager, converter);
        authenticationFilter.setSuccessHandler((_, _, _) -> {
        });
        authenticationFilter.setFailureHandler(failureEntryPoint::commence);
        builder.addFilterAfter(authenticationFilter, CsrfFilter.class);
    }
}
