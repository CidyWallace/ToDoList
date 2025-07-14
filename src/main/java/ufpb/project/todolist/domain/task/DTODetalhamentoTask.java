package ufpb.project.todolist.domain.task;

public record DTODetalhamentoTask(Long id, String titulo, String task, Boolean completd) {
    public DTODetalhamentoTask(Task toDo) {
        this(toDo.getId(), toDo.getTitulo(), toDo.getTask(), toDo.getCompleted());
    }
}
