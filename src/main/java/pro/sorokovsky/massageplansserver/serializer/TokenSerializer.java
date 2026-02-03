package pro.sorokovsky.massageplansserver.serializer;

import pro.sorokovsky.massageplansserver.model.Token;

import java.util.Optional;
import java.util.function.Function;

public interface TokenSerializer extends Function<Token, Optional<String>> {
}
