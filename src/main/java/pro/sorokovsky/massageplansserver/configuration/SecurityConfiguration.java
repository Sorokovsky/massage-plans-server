package pro.sorokovsky.massageplansserver.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import pro.sorokovsky.massageplansserver.configurer.JwtAuthenticationConfigurer;
import pro.sorokovsky.massageplansserver.factory.AccessTokenFactory;
import pro.sorokovsky.massageplansserver.factory.RefreshTokenFactory;
import pro.sorokovsky.massageplansserver.pont.FailureAuthenticationPoint;
import pro.sorokovsky.massageplansserver.service.*;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            BearerTokenStorage accessTokenStorage,
            FailureAuthenticationPoint failureAuthenticationPoint
    ) {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/swagger-ui/**", "/v3/**").permitAll()
                        .requestMatchers("/authorization/register", "/authorization/login").anonymous()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(failureAuthenticationPoint)
                        .accessDeniedHandler((_, response, accessDeniedException) -> {
                            final var details = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, accessDeniedException.getLocalizedMessage());
                            final var mapper = new ObjectMapper();
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, mapper.writeValueAsString(details));
                        })
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);
        http.apply(JwtAuthenticationConfigurer
                .builder()
                .accessTokenStorage(accessTokenStorage)
                .failureEntryPoint(failureAuthenticationPoint)
                .build());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthorizationService authorizationService(
            PasswordEncoder passwordEncoder,
            UsersService usersService,
            BearerTokenStorage accessTokenStorage,
            CookieTokenStorage refreshTokenStorage,
            AccessTokenFactory accessTokenFactory,
            RefreshTokenFactory refreshTokenFactory
    ) {
        return AuthorizationService
                .builder()
                .passwordEncoder(passwordEncoder)
                .usersService(usersService)
                .accessTokenStorage(accessTokenStorage)
                .refreshTokenStorage(refreshTokenStorage)
                .accessTokenFactory(accessTokenFactory)
                .refreshTokenFactory(refreshTokenFactory)
                .build();
    }

    @Bean
    public FailureAuthenticationPoint failureAuthenticationPoint(MessageSource messageSource) {
        return FailureAuthenticationPoint
                .builder()
                .messageSource(messageSource)
                .build();
    }

    @Bean
    public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider(UsersService usersService) {
        final var service = JwtUserService.builder().usersService(usersService).build();
        final var provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(service);
        return provider;
    }
}
