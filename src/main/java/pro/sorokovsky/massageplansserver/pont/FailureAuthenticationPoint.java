package pro.sorokovsky.massageplansserver.pont;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@RequiredArgsConstructor
@Builder
public class FailureAuthenticationPoint implements AuthenticationEntryPoint {
    private final MessageSource messageSource;

    @Override
    public void commence(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull AuthenticationException authException
    )
            throws IOException {
        final var locale = LocaleContextHolder.getLocale();
        final var message = messageSource.getMessage("errors.authorization.unauthorized", new Object[0], locale);
        final var details = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, message);
        final var mapper = new ObjectMapper();
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, mapper.writeValueAsString(details));
    }
}
