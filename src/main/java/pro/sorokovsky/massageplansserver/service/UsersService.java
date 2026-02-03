package pro.sorokovsky.massageplansserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sorokovsky.massageplansserver.contract.NewUserPayload;
import pro.sorokovsky.massageplansserver.exception.user.UserAlreadyExistsException;
import pro.sorokovsky.massageplansserver.exception.user.UserNotFoundException;
import pro.sorokovsky.massageplansserver.mapper.UserMapper;
import pro.sorokovsky.massageplansserver.model.UserModel;
import pro.sorokovsky.massageplansserver.repository.UsersRepository;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final UsersRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<UserModel> getById(long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Transactional(readOnly = true)
    public Optional<UserModel> getByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toModel);
    }

    @Transactional
    public UserModel create(NewUserPayload payload) {
        final var candidate = getByEmail(payload.email());
        if (candidate.isPresent()) throw new UserAlreadyExistsException();
        final var now = Date.from(Instant.now());
        final var user = UserModel
                .builder()
                .createdAt(now)
                .updatedAt(now)
                .email(payload.email())
                .password(passwordEncoder.encode(payload.password()))
                .firstName(payload.firstName())
                .lastName(payload.lastName())
                .middleName(payload.middleName())
                .build();
        return mapper.toModel(repository.save(mapper.toEntity(user)));
    }

    @Transactional
    public void deleteById(long id) {
        getById(id).orElseThrow(UserNotFoundException::new);
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByEmail(username).orElseThrow(UserNotFoundException::new);
    }
}
