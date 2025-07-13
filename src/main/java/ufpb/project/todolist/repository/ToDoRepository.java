package ufpb.project.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufpb.project.todolist.domain.todo.ToDo;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    Optional<List<ToDo>> findAllByUsuario_IdAndCompletedFalse(Long id);

    Optional<ToDo> findByIdAndUsuario_Id(Long id, Long usuarioId);

    Optional<ToDo> findByIdAndCompletedFalseAndUsuario_Id(Long id, Long userId);

    Optional<List<ToDo>> findAllByCompletedTrueAndUsuario_Id(Long id);
}
