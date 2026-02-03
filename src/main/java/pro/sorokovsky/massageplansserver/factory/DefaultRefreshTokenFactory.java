package pro.sorokovsky.massageplansserver.factory;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import pro.sorokovsky.massageplansserver.model.Authorities;
import pro.sorokovsky.massageplansserver.model.Token;
import pro.sorokovsky.massageplansserver.model.UserModel;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Builder
public class DefaultRefreshTokenFactory implements RefreshTokenFactory {
    private final Duration lifetime;

    @Override
    public Token apply(UserModel model) {
        final var now = Instant.now();
        return new Token(UUID.randomUUID(), model.getEmail(), now, now.plus(lifetime), List.of(Authorities.REFRESH_TOKEN));
    }
}
