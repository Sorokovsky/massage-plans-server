package pro.sorokovsky.massageplansserver.serializer;

import com.nimbusds.jwt.JWTClaimsSet;
import pro.sorokovsky.massageplansserver.model.ClaimsKeys;
import pro.sorokovsky.massageplansserver.model.Token;

import java.util.Date;

public abstract class JwtTokenSerializer implements TokenSerializer {
    public JWTClaimsSet getClaimsFromToken(Token token) {
        return new JWTClaimsSet
                .Builder()
                .jwtID(token.id().toString())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .subject(token.email())
                .claim(ClaimsKeys.AUTHORITIES, token.authorities())
                .build();
    }
}
