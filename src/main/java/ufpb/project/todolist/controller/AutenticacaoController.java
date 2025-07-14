package ufpb.project.todolist.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ufpb.project.todolist.domain.usuario.DTOAutenticacao;
import ufpb.project.todolist.domain.usuario.Usuario;
import ufpb.project.todolist.infra.security.DTOTokenJWT;
import ufpb.project.todolist.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private final TokenService tokenService;
    private final AuthenticationManager manager;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<DTOTokenJWT> autenticar(@RequestBody @Valid DTOAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authenticacion = manager.authenticate(authenticationToken);

        var token = tokenService.gerarToken((Usuario) authenticacion.getPrincipal());
        return ResponseEntity.ok(new DTOTokenJWT(token));
    }
}
