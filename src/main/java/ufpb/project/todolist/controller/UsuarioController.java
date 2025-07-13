package ufpb.project.todolist.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ufpb.project.todolist.domain.usuario.DadosAutenticacao;
import ufpb.project.todolist.domain.usuario.DadosDetalhentoUsuario;
import ufpb.project.todolist.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService autenticacaoService;

    public UsuarioController(UsuarioService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhentoUsuario> cadastrar(@RequestBody @Valid DadosAutenticacao dados, UriComponentsBuilder uriBuilder) {
        var cadastro = autenticacaoService.cadastrar(dados);
        var uri = autenticacaoService.criarURI(cadastro, uriBuilder);
        return ResponseEntity.created(uri).body(new DadosDetalhentoUsuario(cadastro));
    }
}
