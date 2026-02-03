package pro.sorokovsky.massageplansserver.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import pro.sorokovsky.massageplansserver.deserializer.TokenDeserializer;
import pro.sorokovsky.massageplansserver.model.Token;
import pro.sorokovsky.massageplansserver.serializer.TokenSerializer;

import java.util.Optional;

@RequiredArgsConstructor
@Builder
public class BearerTokenStorage implements TokenStorage {
    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenSerializer serializer;
    private final TokenDeserializer deserializer;

    @Override
    public Optional<Token> getToken(String token, HttpServletRequest request) {
        final var header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(BEARER_PREFIX)) return Optional.empty();
        return deserializer.apply(header.substring(BEARER_PREFIX.length()));
    }

    @Override
    public void setToken(Token token, HttpServletResponse response) {
        response.setHeader(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + serializer.apply(token));
    }

    @Override
    public void removeToken(HttpServletResponse response) {
        response.setHeader(HttpHeaders.AUTHORIZATION, "");
    }
}
