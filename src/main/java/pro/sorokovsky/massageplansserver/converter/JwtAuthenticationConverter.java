package pro.sorokovsky.massageplansserver.converter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import pro.sorokovsky.massageplansserver.service.TokenStorage;

@RequiredArgsConstructor
@Builder
public class JwtAuthenticationConverter implements AuthenticationConverter {
    private final TokenStorage accessTokenStorage;

    @Override
    public @Nullable Authentication convert(@NonNull HttpServletRequest request) {
        final var accessToken = accessTokenStorage.getToken(request).orElse(null);
        if (accessToken == null) return null;
        return new PreAuthenticatedAuthenticationToken(accessToken, accessToken);

    }
}
