package ufpb.project.todolist.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ufpb.project.todolist.domain.task.DTOCreateTask;
import ufpb.project.todolist.domain.task.DTOTaskDetails;
import ufpb.project.todolist.domain.task.DTOEditTask;
import ufpb.project.todolist.domain.task.Task;
import ufpb.project.todolist.domain.usuario.Usuario;
import ufpb.project.todolist.repository.ToDoRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final ToDoRepository toDoRepository;

    public TaskService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Transactional
    public Task createTask(DTOCreateTask dados, Usuario user) {
        var todo = new Task(dados);
        todo.setUsuario(user);
        toDoRepository.save(todo);
        return todo;
    }

    public List<DTOTaskDetails> findAllTasksCompletedFalse(Usuario user) {
        var todo = toDoRepository.findAllByUsuario_IdAndCompletedFalse(user.getId());
        return todo.map(t -> t.stream().map(DTOTaskDetails::new).toList()).orElseGet(() -> List.of(new DTOTaskDetails(new Task())));
    }

    public Task findById(Long id, Usuario user) {
        var todo = toDoRepository.findByIdAndUsuario_Id(id, user.getId());
        if (todo.isPresent()) {
            return todo.get();
        }else{
            throw new EntityNotFoundException("Nenhuma tarefa encontrada!");
        }
    }

    @Transactional
    public void completTaks(Long id, Usuario user) {
        var todo = toDoRepository.findByIdAndCompletedFalseAndUsuario_Id(id, user.getId());
        if(todo.isPresent() && !todo.get().getCompleted()) {
            todo.get().setCompleted(true);
        }else{
            throw new NullPointerException("Task não encontrada!");
        }
    }

    public List<DTOTaskDetails> findAllTasksCompleted(String completd, Usuario user) {
        Optional<List<Task>> todo;
        if(completd.equals("true")) {
             todo = toDoRepository.findAllByCompletedTrueAndUsuario_Id(user.getId());
             return todo.map(t -> t.stream().map(DTOTaskDetails::new).toList()).orElseGet(List::of);
        }else if(completd.equals("false")) {
             todo = toDoRepository.findAllByUsuario_IdAndCompletedFalse(user.getId());
             return todo.map(t -> t.stream().map(DTOTaskDetails::new).toList()).orElseGet(List::of);
        }
        return List.of(new DTOTaskDetails(new Task()));
    }

    @Transactional
    public Task editeTask(Long id, DTOEditTask dados, Usuario user) {
        var task = findById(id, user);
        if(task.getId() != null && !task.getCompleted()) {
            return task.AtualizaDados(dados);
        }else{
            throw new NullPointerException("Nenhuma tarefa encontrada ou já foi completada!");
        }
    }

    public URI createURI(Task todo, UriComponentsBuilder uriBuilder) {
        return uriBuilder.path("/ToDo/{id}").buildAndExpand(todo.getId()).toUri();
    }
}
