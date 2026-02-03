package pro.sorokovsky.massageplansserver.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(
        description = "Данні для створення користувача",
        requiredMode = Schema.RequiredMode.REQUIRED
)
public record NewUserPayload(
        @NotNull(message = "{errors.email.null}")
        @NotBlank(message = "{errors.email.empty}")
        @Email(message = "{errors.email.format}")
        @Schema(
                description = "Електронна адреса користувача",
                example = "Sorokovskys@ukr.net",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email,

        @NotBlank(message = "{errors.password.null}")
        @NotBlank(message = "{errors.password.empty}")
        @Size(min = 6, max = 20, message = "{errors.password.size}")
        @Schema(
                description = "Пароль користувача",
                example = "<PASSWORD>",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String password,

        @NotBlank(message = "{errors.first-name.null}")
        @NotBlank(message = "{errors.first-name.empty}")
        @Schema(
                description = "Ім'я користувача",
                example = "Андрій",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String firstName,

        @NotBlank(message = "{errors.last-name.null}")
        @NotBlank(message = "{errors.last-name.empty}")
        @Schema(
                description = "Прізвище користувача",
                example = "Сороковський",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String lastName,

        @NotBlank(message = "{errors.middle-name.null}")
        @NotBlank(message = "{errors.middle-name.empty}")
        @Schema(
                description = "По батькові користувача",
                example = "Іванович",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String middleName
) {
}
