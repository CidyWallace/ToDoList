package ufpb.project.todolist.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ufpb.project.todolist.domain.todo.DadosCriarTarefa;
import ufpb.project.todolist.domain.todo.DadosDetalhamentoToDo;
import ufpb.project.todolist.domain.usuario.Usuario;
import ufpb.project.todolist.service.ToDoService;

import java.util.List;

@RestController
@RequestMapping("/ToDo")
public class ToDoController {
    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoToDo> criarTarefa(@RequestBody @Valid DadosCriarTarefa dados, UriComponentsBuilder uriBuilder) {
        var user = getUser();
        var todo = toDoService.criartarefa(dados, user);
        var uri = toDoService.criarURI(todo,uriBuilder);
        return ResponseEntity.created(uri).body(new DadosDetalhamentoToDo(todo));
    }

    @GetMapping("/false")
    public ResponseEntity<List<DadosDetalhamentoToDo>> findAll(){
        return ResponseEntity.ok(toDoService.findAllTasksCompletedFalse(getUser()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoToDo> findById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(new DadosDetalhamentoToDo(toDoService.findById(id, getUser())));
        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/true")
    public ResponseEntity<List<DadosDetalhamentoToDo>> findAllTasksCompletedTrue(){
        return ResponseEntity.ok(toDoService.findAllTasksCompletedTrue(getUser()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> completTask(@PathVariable Long id){
        try{
            toDoService.completTaks(id, getUser());
            return ResponseEntity.ok().build();
        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoToDo> editarTask(@PathVariable Long id, @RequestBody @Valid DadosCriarTarefa dados){
        try{
            var todo = toDoService.editaTask(id, dados, getUser());
            return ResponseEntity.ok(new DadosDetalhamentoToDo(todo));
        } catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }

    private Usuario getUser(){
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
