package pro.sorokovsky.massageplansserver.contract;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginPayload(
        @NotNull(message = "{errors.email.null}")
        @NotBlank(message = "{errors.email.empty}")
        @Email(message = "{errors.email.format}")
        String email,

        @NotBlank(message = "{errors.password.null}")
        @NotBlank(message = "{errors.password.empty}")
        @Size(min = 6, max = 20, message = "{errors.password.size}")
        String password
) {
}
