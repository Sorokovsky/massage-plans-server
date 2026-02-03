package pro.sorokovsky.massageplansserver.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import pro.sorokovsky.massageplansserver.model.Token;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Builder
public class JwtUserService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    private final UsersService usersService;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authenticationToken)
            throws UsernameNotFoundException {
        if (authenticationToken.getPrincipal() instanceof Token token) {
            final var user = usersService.getByEmail(token.email())
                    .orElseThrow(() -> new UsernameNotFoundException("errors.user.not-found"));
            user.setAuthorities(
                    Stream.concat(
                            user.getAuthorities().stream(),
                            token.authorities().stream().map(SimpleGrantedAuthority::new)
                    ).toList()
            );
            return user;
        } else {
            throw new UsernameNotFoundException("errors.user.not-found");
        }
    }
}
