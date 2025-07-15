package ufpb.project.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ufpb.project.todolist.domain.usuario.DTOAuthentication;
import ufpb.project.todolist.domain.usuario.DTOUserDetails;
import ufpb.project.todolist.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService autenticacaoService) {
        this.usuarioService = autenticacaoService;
    }

    @Operation(summary = "Cadastrar um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DTOUserDetails.class)) }),
            @ApiResponse(responseCode = "400", description = "Alguma informação foi passada de forma incorreta",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<DTOUserDetails> cadastrar(
            @RequestBody @Valid DTOAuthentication dados,
            UriComponentsBuilder uriBuilder) {
        try{
            var cadastro = usuarioService.cadastrar(dados);
            var uri = usuarioService.criarURI(cadastro, uriBuilder);
            return ResponseEntity.created(uri).body(new DTOUserDetails(cadastro));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Buscar um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DTOUserDetails.class)) }),
            @ApiResponse(responseCode = "404", description = "tarefa não encontrada",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<DTOUserDetails> findUsuarioById(
            @Parameter(description = "ID do usuário para busca", required = true, example = "1")
            @PathVariable Long id) {
        try{
            return ResponseEntity.ok(usuarioService.findByIdUser(id));
        }catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
