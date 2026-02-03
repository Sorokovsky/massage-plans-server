package pro.sorokovsky.massageplansserver.configuration;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.sorokovsky.massageplansserver.deserializer.JweTokenDeserializer;
import pro.sorokovsky.massageplansserver.deserializer.JwsTokenDeserializer;
import pro.sorokovsky.massageplansserver.serializer.JweTokenSerializer;
import pro.sorokovsky.massageplansserver.serializer.JwsTokenSerializer;

import java.text.ParseException;

@Configuration
public class TokensConfiguration {
    @Bean
    public JwsTokenSerializer jwsTokenSerializer(@Value("${tokens.access.secret-key}") String secretKey)
            throws ParseException, KeyLengthException {
        return JwsTokenSerializer
                .builder()
                .algorithm(JWSAlgorithm.HS256)
                .signer(new MACSigner(OctetSequenceKey.parse(secretKey)))
                .build();
    }

    @Bean
    public JwsTokenDeserializer jwsTokenDeserializer(@Value("${tokens.access.secret-key}") String secretKey)
            throws ParseException, JOSEException {
        return JwsTokenDeserializer
                .builder()
                .verifier(new MACVerifier(OctetSequenceKey.parse(secretKey)))
                .build();
    }

    @Bean
    public JweTokenSerializer jweTokenSerializer(@Value("${tokens.refresh.secret-key}") String secretKey)
            throws ParseException, KeyLengthException {
        return JweTokenSerializer
                .builder()
                .algorithm(JWEAlgorithm.DIR)
                .encrypter(new DirectEncrypter(OctetSequenceKey.parse(secretKey)))
                .method(EncryptionMethod.A192GCM)
                .build();
    }

    @Bean
    public JweTokenDeserializer jweTokenDeserializer(@Value("${tokens.refresh.secret-key}") String secretKey)
            throws ParseException, KeyLengthException {
        return JweTokenDeserializer
                .builder()
                .decrypter(new DirectDecrypter(OctetSequenceKey.parse(secretKey)))
                .build();
    }
}
