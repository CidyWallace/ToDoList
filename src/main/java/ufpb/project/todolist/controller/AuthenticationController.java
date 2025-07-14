package ufpb.project.todolist.controller;

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

    @PostMapping
    public ResponseEntity<DTOTokenJWT> autenticar(@RequestBody @Valid DTOAuthentication dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authenticacion = manager.authenticate(authenticationToken);

        var token = tokenService.gerarToken((Usuario) authenticacion.getPrincipal());
        return ResponseEntity.ok(new DTOTokenJWT(token));
    }
}
