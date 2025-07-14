package ufpb.project.todolist.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ufpb.project.todolist.domain.usuario.DTOAutenticacao;
import ufpb.project.todolist.domain.usuario.DTODetalhentoUsuario;
import ufpb.project.todolist.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService autenticacaoService) {
        this.usuarioService = autenticacaoService;
    }

    @PostMapping
    public ResponseEntity<DTODetalhentoUsuario> cadastrar(@RequestBody @Valid DTOAutenticacao dados, UriComponentsBuilder uriBuilder) {
        try{
            var cadastro = usuarioService.cadastrar(dados);
            var uri = usuarioService.criarURI(cadastro, uriBuilder);
            return ResponseEntity.created(uri).body(new DTODetalhentoUsuario(cadastro));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTODetalhentoUsuario> findUsuarioById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(usuarioService.findByIdUser(id));
        }catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
