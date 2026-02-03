package pro.sorokovsky.massageplansserver.deserializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sorokovsky.massageplansserver.model.Token;

import java.text.ParseException;
import java.util.Optional;

@RequiredArgsConstructor
@Builder
public class JwsTokenDeserializer extends JwtTokenDeserializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwsTokenDeserializer.class);

    private final JWSVerifier verifier;

    @Override
    public Optional<Token> apply(String string) {
        try {
            final var signed = SignedJWT.parse(string);
            if (!signed.verify(verifier)) return Optional.empty();
            return Optional.ofNullable(extractTokenFromClaims(signed.getJWTClaimsSet()));
        } catch (ParseException | JOSEException exception) {
            LOGGER.error("Invalid JWT Token", exception);
            return Optional.empty();
        }
    }
}
