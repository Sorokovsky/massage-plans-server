package pro.sorokovsky.massageplansserver.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import pro.sorokovsky.massageplansserver.contract.LoginPayload;
import pro.sorokovsky.massageplansserver.contract.NewUserPayload;
import pro.sorokovsky.massageplansserver.exception.authorization.BadCredentialsException;
import pro.sorokovsky.massageplansserver.factory.AccessTokenFactory;
import pro.sorokovsky.massageplansserver.factory.RefreshTokenFactory;
import pro.sorokovsky.massageplansserver.model.UserModel;

@RequiredArgsConstructor
@Builder
public class AuthorizationService {
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final TokenStorage accessTokenStorage;
    private final TokenStorage refreshTokenStorage;
    private final AccessTokenFactory accessTokenFactory;
    private final RefreshTokenFactory refreshTokenFactory;

    public UserModel register(NewUserPayload payload, HttpServletResponse response) {
        final var user = usersService.create(payload);
        authorize(user, response);
        return user;
    }

    public UserModel login(LoginPayload payload, HttpServletResponse response) {
        final var candidate = usersService.getByEmail(payload.email()).orElse(null);
        if (candidate == null) throw new BadCredentialsException();
        if (!passwordEncoder.matches(payload.password(), candidate.getPassword())) throw new BadCredentialsException();
        authorize(candidate, response);
        return candidate;
    }

    public void logout(HttpServletResponse response) {
        accessTokenStorage.removeToken(response);
        refreshTokenStorage.removeToken(response);
    }

    private void authorize(UserModel user, HttpServletResponse response) {
        final var refreshToken = refreshTokenFactory.apply(user);
        final var accessToken = accessTokenFactory.apply(refreshToken);
        accessTokenStorage.setToken(accessToken, response);
        refreshTokenStorage.setToken(refreshToken, response);
    }
}
