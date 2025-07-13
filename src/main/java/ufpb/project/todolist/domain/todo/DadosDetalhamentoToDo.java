package ufpb.project.todolist.domain.todo;

public record DadosDetalhamentoToDo(Long id, String titulo, String task, Boolean completd) {
    public DadosDetalhamentoToDo(ToDo toDo) {
        this(toDo.getId(), toDo.getTitulo(), toDo.getTask(), toDo.getCompleted());
    }
}
