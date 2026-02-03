package pro.sorokovsky.massageplansserver.contract;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(
        description = "Данні для отримання користувача",
        requiredMode = Schema.RequiredMode.REQUIRED
)
public record GetUserPayload(
        @Schema(
                description = "Ідентифікатор користувача",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long id,

        @Schema(
                description = "Дата створення користувача",
                example = "2026-02-03T11:50:29.215Z",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Date createdAt,

        @Schema(
                description = "Дата оновлення користувача",
                example = "2026-02-03T11:50:29.215Z",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Date updatedAt,

        @Schema(
                description = "Електронна адреса користувача",
                example = "Sorokovskys@ukr.net",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email,

        @Schema(
                description = "Ім'я користувача",
                example = "Андрій",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String firstName,

        @Schema(
                description = "Прізвище користувача",
                example = "Сороковський",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String lastName,

        @Schema(
                description = "По батькові користувача",
                example = "Іванович",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String middleName
) {
}
