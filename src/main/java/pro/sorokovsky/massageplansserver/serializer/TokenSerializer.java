package pro.sorokovsky.massageplansserver.serializer;

import pro.sorokovsky.massageplansserver.model.Token;

import java.util.function.Function;

public interface TokenSerializer extends Function<Token, String> {
}
