package ufpb.project.todolist.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ufpb.project.todolist.domain.todo.DadosCriarTarefa;
import ufpb.project.todolist.domain.todo.DadosDetalhamentoToDo;
import ufpb.project.todolist.domain.todo.ToDo;
import ufpb.project.todolist.repository.ToDoRepository;

import java.net.URI;
import java.util.List;

@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Transactional
    public ToDo criartarefa(DadosCriarTarefa dados) {
        var todo = new ToDo(dados);
        toDoRepository.save(todo);
        return todo;
    }

    public URI criarURI(ToDo todo, UriComponentsBuilder uriBuilder) {
        return uriBuilder.path("/ToDo/{id}").buildAndExpand(todo.getId()).toUri();
    }

    public List<DadosDetalhamentoToDo> findAll() {
        return toDoRepository.findAll().stream().map(DadosDetalhamentoToDo::new).toList();
    }

    public DadosDetalhamentoToDo findById(Long id) {
        return new DadosDetalhamentoToDo(toDoRepository.findById(id).orElse(new ToDo()));
    }
}
