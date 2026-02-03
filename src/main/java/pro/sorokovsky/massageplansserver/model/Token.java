package pro.sorokovsky.massageplansserver.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record Token(UUID id, String email, Instant createdAt, Instant expiresAt, List<String> authorities) {
}
