package pro.sorokovsky.massageplansserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pro.sorokovsky.massageplansserver.contract.GetUserPayload;
import pro.sorokovsky.massageplansserver.contract.LoginPayload;
import pro.sorokovsky.massageplansserver.contract.NewUserPayload;
import pro.sorokovsky.massageplansserver.mapper.UserMapper;
import pro.sorokovsky.massageplansserver.service.AuthorizationService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Авторизація")
@RequestMapping("authorization")
public class AuthorizationController {
    private final AuthorizationService service;
    private final UserMapper mapper;

    @PostMapping("register")
    @Operation(
            description = "Реєстрація користувача",
            summary = "Реєстрація",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            contentSchema = @Schema(implementation = NewUserPayload.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    content = @Content(
                            schema = @Schema(implementation = GetUserPayload.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    responseCode = "201",
                    description = "Успішна реєстрація",
                    headers = {
                            @Header(
                                    name = HttpHeaders.LOCATION,
                                    schema = @Schema(implementation = String.class),
                                    required = true,
                                    description = "Посилання на профіль",
                                    example = "/authorization/profile"
                            ),
                            @Header(
                                    name = HttpHeaders.AUTHORIZATION,
                                    schema = @Schema(implementation = String.class),
                                    required = true,
                                    description = "Токен доступу",
                                    example = "Bearer <TOKEN>"
                            )
                    }
            ),
            @ApiResponse(
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    responseCode = "403",
                    description = "Немає доступу"
            ),
            @ApiResponse(
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    responseCode = "409",
                    description = "Користувач вже існує"
            ),
            @ApiResponse(
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    responseCode = "400",
                    description = "Не коректні данні"
            )
    })
    public ResponseEntity<GetUserPayload> register(
            @Valid @RequestBody NewUserPayload payload,
            UriComponentsBuilder uriBuilder
    ) {
        final var user = service.register(payload);
        return ResponseEntity
                .created(uriBuilder.replacePath("/authorization/profile").build().toUri())
                .body(mapper.toGet(user));
    }

    @PostMapping("login")
    @Operation(
            description = "Вхід користувача",
            summary = "Вхід",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            contentSchema = @Schema(implementation = LoginPayload.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    content = @Content(
                            schema = @Schema(implementation = GetUserPayload.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    responseCode = "200",
                    description = "Успішний вхід",
                    headers = {
                            @Header(
                                    name = HttpHeaders.AUTHORIZATION,
                                    schema = @Schema(implementation = String.class),
                                    required = true,
                                    description = "Токен доступу",
                                    example = "Bearer <TOKEN>"
                            )
                    }
            ),
            @ApiResponse(
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    responseCode = "403",
                    description = "Немає доступу"
            ),
            @ApiResponse(
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    responseCode = "400",
                    description = "Не коректні данні"
            )
    })
    public ResponseEntity<GetUserPayload> login(@Valid @RequestBody LoginPayload payload) {
        return ResponseEntity.ok(mapper.toGet(service.login(payload)));
    }

    @DeleteMapping("logout")
    @Operation(
            description = "Вихід користувача",
            summary = "Вихід"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Успішний вихід"
            ),
            @ApiResponse(
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    responseCode = "401",
                    description = "Неавторизований користувач"
            )
    })
    public ResponseEntity<Void> logout() {
        service.logout();
        return ResponseEntity.noContent().build();
    }
}
