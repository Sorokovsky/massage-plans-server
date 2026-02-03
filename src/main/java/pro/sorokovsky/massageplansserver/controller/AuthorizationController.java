package pro.sorokovsky.massageplansserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import pro.sorokovsky.massageplansserver.contract.GetUserPayload;
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
                    responseCode = "201"
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
}
