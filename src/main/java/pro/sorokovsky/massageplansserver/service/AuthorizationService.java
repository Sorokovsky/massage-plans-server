package pro.sorokovsky.massageplansserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pro.sorokovsky.massageplansserver.contract.LoginPayload;
import pro.sorokovsky.massageplansserver.contract.NewUserPayload;
import pro.sorokovsky.massageplansserver.exception.authorization.BadCredentialsException;
import pro.sorokovsky.massageplansserver.mapper.UserMapper;
import pro.sorokovsky.massageplansserver.model.UserModel;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public UserModel register(NewUserPayload payload) {
        final var user = usersService.create(payload);
        authorize(user);
        return user;
    }

    public UserModel login(LoginPayload payload) {
        final var candidate = usersService.getByEmail(payload.email()).orElse(null);
        if (candidate == null) throw new BadCredentialsException();
        if (!passwordEncoder.matches(payload.password(), candidate.getPassword())) throw new BadCredentialsException();
        authorize(candidate);
        return candidate;
    }

    public void logout() {

    }

    private void authorize(UserModel user) {

    }
}
