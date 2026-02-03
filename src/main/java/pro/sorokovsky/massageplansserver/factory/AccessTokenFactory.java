package pro.sorokovsky.massageplansserver.factory;

import pro.sorokovsky.massageplansserver.model.Token;

import java.util.function.Function;

public interface AccessTokenFactory extends Function<Token, Token> {
}
