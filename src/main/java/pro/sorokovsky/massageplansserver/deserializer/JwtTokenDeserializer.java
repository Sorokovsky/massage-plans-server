package pro.sorokovsky.massageplansserver.deserializer;

import com.nimbusds.jwt.JWTClaimsSet;
import pro.sorokovsky.massageplansserver.model.ClaimsKeys;
import pro.sorokovsky.massageplansserver.model.Token;

import java.text.ParseException;
import java.util.UUID;

public abstract class JwtTokenDeserializer implements TokenDeserializer {
    public Token extractTokenFromClaims(JWTClaimsSet claims) throws ParseException {
        return new Token(
                UUID.fromString(claims.getJWTID()),
                claims.getSubject(),
                claims.getIssueTime().toInstant(),
                claims.getExpirationTime().toInstant(),
                claims.getStringListClaim(ClaimsKeys.AUTHORITIES)
        );
    }
}
