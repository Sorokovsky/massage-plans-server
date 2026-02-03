package pro.sorokovsky.massageplansserver.deserializer;

import pro.sorokovsky.massageplansserver.model.Token;

import java.util.Optional;
import java.util.function.Function;

public interface TokenDeserializer extends Function<String, Optional<Token>> {
}
