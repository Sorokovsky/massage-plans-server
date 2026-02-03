package pro.sorokovsky.massageplansserver.contract;

import jakarta.validation.constraints.Email;

public record NewUserPayload(
        @Email(message = "")
        String email,
        String password,
        String firstName,
        String lastName,
        String middleName
) {
}
