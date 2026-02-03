package pro.sorokovsky.massageplansserver.factory;

import pro.sorokovsky.massageplansserver.model.Token;
import pro.sorokovsky.massageplansserver.model.UserModel;

import java.util.function.Function;

public interface RefreshTokenFactory extends Function<UserModel, Token> {
}
