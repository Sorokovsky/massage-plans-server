package pro.sorokovsky.massageplansserver.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import pro.sorokovsky.massageplansserver.deserializer.TokenDeserializer;
import pro.sorokovsky.massageplansserver.model.Token;
import pro.sorokovsky.massageplansserver.serializer.TokenSerializer;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Builder
public class CookieTokenStorage implements TokenStorage {
    private static final String COOKIE_NAME = "__Host-refresh-token";

    private final TokenSerializer serializer;
    private final TokenDeserializer deserializer;

    private static Cookie generateCookie(String value, int maxAge) {
        final var cookie = new Cookie(COOKIE_NAME, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setDomain(null);
        return cookie;
    }

    @Override
    public Optional<Token> getToken(String token, HttpServletRequest request) {
        if (request.getCookies() == null) return Optional.empty();
        return Stream.of(request.getCookies())
                .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                .findFirst()
                .flatMap(cookie -> deserializer.apply(cookie.getValue()));
    }

    @Override
    public void setToken(Token token, HttpServletResponse response) {
        final var maxAge = (int) ChronoUnit.SECONDS.between(token.createdAt(), token.expiresAt());
        response.addCookie(generateCookie(serializer.apply(token), maxAge));
    }

    @Override
    public void removeToken(String token, HttpServletResponse response) {
        response.addCookie(generateCookie(null, 0));
    }
}
