package ufpb.project.todolist.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ufpb.project.todolist.domain.todo.DadosCriarTarefa;
import ufpb.project.todolist.domain.todo.DadosDetalhamentoToDo;
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
    public ResponseEntity criarTarefa(@RequestBody @Valid DadosCriarTarefa dados, UriComponentsBuilder uriBuilder) {
        var todo = toDoService.criartarefa(dados);
        var uri = toDoService.criarURI(todo,uriBuilder);
        return ResponseEntity.created(uri).body(new DadosDetalhamentoToDo(todo));
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoToDo>> findAll(){
        return ResponseEntity.ok(toDoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        return ResponseEntity.ok(toDoService.findById(id));
    }
}
