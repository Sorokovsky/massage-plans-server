package pro.sorokovsky.massageplansserver.serializer;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sorokovsky.massageplansserver.model.Token;

@RequiredArgsConstructor
@Builder
public class JweTokenSerializer extends JwtTokenSerializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JweTokenSerializer.class);

    private final JWEAlgorithm algorithm;
    private final EncryptionMethod method;
    private final JWEEncrypter encrypter;

    @Override
    public String apply(Token token) {
        try {
            final var header = new JWEHeader.Builder(algorithm, method)
                    .keyID(token.id().toString())
                    .build();
            final var claims = getClaimsFromToken(token);
            final var encrypted = new EncryptedJWT(header, claims);
            encrypted.encrypt(encrypter);
            return encrypted.serialize();
        } catch (JOSEException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }
}
