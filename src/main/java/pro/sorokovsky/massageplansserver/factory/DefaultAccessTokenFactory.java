package pro.sorokovsky.massageplansserver.factory;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import pro.sorokovsky.massageplansserver.model.Authorities;
import pro.sorokovsky.massageplansserver.model.Token;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Builder
public class DefaultAccessTokenFactory implements AccessTokenFactory {
    private final Duration lifetime;

    @Override
    public Token apply(Token token) {
        final var now = Instant.now();
        return new Token(
                token.id(),
                token.email(),
                now,
                now.plus(lifetime),
                List.of(Authorities.LOGOUT)
        );
    }
}
