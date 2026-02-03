package pro.sorokovsky.massageplansserver.serializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.SignedJWT;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sorokovsky.massageplansserver.model.Token;

@RequiredArgsConstructor
@Builder
public class JwsTokenSerializer extends JwtTokenSerializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwsTokenSerializer.class);

    private final JWSAlgorithm algorithm;
    private final JWSSigner signer;

    @Override
    public String apply(Token token) {
        try {
            final var header = new JWSHeader
                    .Builder(algorithm)
                    .keyID(token.id().toString())
                    .build();
            final var claims = getClaimsFromToken(token);
            final var signed = new SignedJWT(header, claims);
            signed.sign(signer);
            return signed.serialize();
        } catch (JOSEException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }
}