package ufpb.project.todolist.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ufpb.project.todolist.domain.task.DTOCreateTask;
import ufpb.project.todolist.domain.task.DTOTaskDetails;
import ufpb.project.todolist.domain.task.DTOEditTask;
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

    @PostMapping
    public ResponseEntity<DTOTaskDetails> criarTarefa(@RequestBody @Valid DTOCreateTask dados, UriComponentsBuilder uriBuilder) {
        var todo = toDoService.createTask(dados, getUser());
        var uri = toDoService.createURI(todo,uriBuilder);
        return ResponseEntity.created(uri).body(new DTOTaskDetails(todo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTOTaskDetails> findById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(new DTOTaskDetails(toDoService.findById(id, getUser())));
        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<DTOTaskDetails>> findAllTasksCompleted(@RequestParam(name = "completed", defaultValue = "true") String completed){
        try{
            System.out.println(completed);
            return ResponseEntity.ok(toDoService.findAllTasksCompleted(completed, getUser()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<DTOTaskDetails> editarTask(@PathVariable Long id, @RequestBody @Valid DTOEditTask dados){
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
