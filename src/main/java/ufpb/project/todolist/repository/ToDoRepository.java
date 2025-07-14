package ufpb.project.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufpb.project.todolist.domain.task.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<Task, Long> {
    Optional<List<Task>> findAllByUsuario_IdAndCompletedFalse(Long id);

    Optional<Task> findByIdAndUsuario_Id(Long id, Long usuarioId);

    Optional<Task> findByIdAndCompletedFalseAndUsuario_Id(Long id, Long userId);

    Optional<List<Task>> findAllByCompletedTrueAndUsuario_Id(Long id);
}
