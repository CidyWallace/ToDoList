package ufpb.project.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ufpb.project.todolist.domain.task.DTOCreateTask;
import ufpb.project.todolist.domain.task.DTOEditTask;
import ufpb.project.todolist.domain.task.DTOTaskDetails;
import ufpb.project.todolist.domain.usuario.Usuario;
import ufpb.project.todolist.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/ToDo")
public class TaskController {
    private final TaskService toDoService;

    public TaskController(TaskService toDoService) {
        this.toDoService = toDoService;
    }

    @Operation(summary = "Criar uma nova tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa criada com sucesso",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DTOTaskDetails.class)) }),
            @ApiResponse(responseCode = "400", description = "Alguma informação foi passada de forma incorreta",
                content = @Content)
    })
    @PostMapping
    public ResponseEntity<DTOTaskDetails> criarTarefa(@RequestBody @Valid DTOCreateTask dados, UriComponentsBuilder uriBuilder) {
        var todo = toDoService.createTask(dados, getUser());
        var uri = toDoService.createURI(todo,uriBuilder);
        return ResponseEntity.created(uri).body(new DTOTaskDetails(todo));
    }

    @Operation(summary = "Busca uma tarefa pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DTOTaskDetails.class)) }),
            @ApiResponse(responseCode = "404", description = "tarefa não encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<DTOTaskDetails> findById(
            @Parameter(description = "ID da atividade a ser buscada", required = true, example = "1")
            @PathVariable Long id){
        try{
            return ResponseEntity.ok(new DTOTaskDetails(toDoService.findById(id, getUser())));
        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Busca todas as tarefas se ele foi completa ou não")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DTOTaskDetails.class)) }),
            @ApiResponse(responseCode = "404", description = "tarefa não encontrada",
                    content = @Content)
    })
    @GetMapping()
    public ResponseEntity<List<DTOTaskDetails>> findAllTasksCompleted(
            @Parameter(description = "true para as tarefas completas e false para as tarefas incompletas", required = true, example = "true")
            @RequestParam(name = "completed", defaultValue = "true") String completed){
        try{
            System.out.println(completed);
            return ResponseEntity.ok(toDoService.findAllTasksCompleted(completed, getUser()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Marcar uma tarefa como completa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas marcada como completa com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DTOTaskDetails.class)) }),
            @ApiResponse(responseCode = "404", description = "tarefa não encontrada",
                    content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> completeTask(
            @Parameter(description = "ID da tarefa para busca", required = true, example = "1")
            @PathVariable Long id){
        try{
            toDoService.completTaks(id, getUser());
            return ResponseEntity.ok().build();
        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Atualiza o titulo e a descrição da tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas editada com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DTOTaskDetails.class)) }),
            @ApiResponse(responseCode = "404", description = "tarefa não encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Alguma informação foi passada de forma incorreta",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<DTOTaskDetails> editarTask(
            @Parameter(description = "ID da tarefa para busca", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody @Valid DTOEditTask dados){
        try{
            var todo = toDoService.editeTask(id, dados, getUser());
            return ResponseEntity.ok(new DTOTaskDetails(todo));
        } catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }

    private Usuario getUser(){
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
