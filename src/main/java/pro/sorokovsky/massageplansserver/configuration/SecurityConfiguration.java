package pro.sorokovsky.massageplansserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pro.sorokovsky.massageplansserver.factory.AccessTokenFactory;
import pro.sorokovsky.massageplansserver.factory.RefreshTokenFactory;
import pro.sorokovsky.massageplansserver.service.AuthorizationService;
import pro.sorokovsky.massageplansserver.service.BearerTokenStorage;
import pro.sorokovsky.massageplansserver.service.CookieTokenStorage;
import pro.sorokovsky.massageplansserver.service.UsersService;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/swagger-ui/**", "/v3/**").permitAll()
                        .requestMatchers("/authorization/register", "/authorization/login").anonymous()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .build();
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
}
