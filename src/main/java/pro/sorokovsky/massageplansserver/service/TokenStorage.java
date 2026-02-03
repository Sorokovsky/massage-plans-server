package pro.sorokovsky.massageplansserver.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pro.sorokovsky.massageplansserver.model.Token;

import java.util.Optional;

public interface TokenStorage {
    Optional<Token> getToken(String token, HttpServletRequest request);

    void setToken(Token token, HttpServletResponse response);

    void removeToken(HttpServletResponse response);
}
