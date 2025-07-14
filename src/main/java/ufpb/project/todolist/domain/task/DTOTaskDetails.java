package ufpb.project.todolist.domain.task;

public record DTOTaskDetails(Long id, String titulo, String task, Boolean completd) {
    public DTOTaskDetails(Task toDo) {
        this(toDo.getId(), toDo.getTitulo(), toDo.getTask(), toDo.getCompleted());
    }
}
