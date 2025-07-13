package ufpb.project.todolist.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ufpb.project.todolist.domain.todo.DadosCriarTarefa;
import ufpb.project.todolist.domain.todo.DadosDetalhamentoToDo;
import ufpb.project.todolist.domain.todo.ToDo;
import ufpb.project.todolist.domain.usuario.Usuario;
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
    public ToDo criartarefa(DadosCriarTarefa dados, Usuario user) {
        var todo = new ToDo(dados);
        todo.setUsuario(user);
        toDoRepository.save(todo);
        return todo;
    }

    public List<DadosDetalhamentoToDo> findAllTasksCompletedFalse(Usuario user) {
        var todo = toDoRepository.findAllByUsuario_IdAndCompletedFalse(user.getId());
        return todo.map(t -> t.stream().map(DadosDetalhamentoToDo::new).toList()).orElseGet(() -> List.of(new DadosDetalhamentoToDo(new ToDo())));
    }

    public ToDo findById(Long id, Usuario user) {
        var todo = toDoRepository.findByIdAndUsuario_Id(id, user.getId());
        if (todo.isPresent()) {
            return todo.get();
        }else{
            throw new NullPointerException("Nenhuma tarefa encontrada!");
        }
    }

    @Transactional
    public void completTaks(Long id, Usuario user) {
        var todo = toDoRepository.findByIdAndCompletedFalseAndUsuario_Id(id, user.getId());
        if(todo.isPresent()) {
            todo.get().setCompleted(true);
        }else{
            throw new NullPointerException("Task não encontrada!");
        }
    }

    public List<DadosDetalhamentoToDo> findAllTasksCompletedTrue(Usuario user) {
        var todo = toDoRepository.findAllByCompletedTrueAndUsuario_Id(user.getId());
        return todo.map(t -> t.stream().map(DadosDetalhamentoToDo::new).toList()).orElseGet(() -> List.of(new DadosDetalhamentoToDo(new ToDo())));
    }

    @Transactional
    public ToDo editaTask(Long id, DadosCriarTarefa dados, Usuario user) {
        var task = findById(id, user);
        if(task.getId() != null && !task.getCompleted()) {
            return task.AtualizaDados(dados);
        }else{
            throw new NullPointerException("Nenhuma tarefa encontrada ou já foi completada!");
        }
    }

    public URI criarURI(ToDo todo, UriComponentsBuilder uriBuilder) {
        return uriBuilder.path("/ToDo/{id}").buildAndExpand(todo.getId()).toUri();
    }
}
