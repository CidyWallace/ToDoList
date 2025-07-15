package ufpb.project.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ufpb.project.todolist.domain.usuario.DTOAuthentication;
import ufpb.project.todolist.domain.usuario.Usuario;
import ufpb.project.todolist.infra.security.DTOTokenJWT;
import ufpb.project.todolist.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final TokenService tokenService;
    private final AuthenticationManager manager;

    public AuthenticationController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Autentica um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DTOTokenJWT.class)) }),
            @ApiResponse(responseCode = "500", description = "O usuário não foi encontrado ou senha incorreta",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Alguma informação foi passada de forma incorreta",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<DTOTokenJWT> autenticar(
            @RequestBody @Valid DTOAuthentication dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        var token = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DTOTokenJWT(token));
    }
}
