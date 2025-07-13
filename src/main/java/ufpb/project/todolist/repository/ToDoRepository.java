package ufpb.project.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufpb.project.todolist.domain.todo.ToDo;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
}
