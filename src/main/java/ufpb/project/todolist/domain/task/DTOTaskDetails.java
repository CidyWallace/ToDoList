package ufpb.project.todolist.domain.task;

import io.swagger.v3.oas.annotations.media.Schema;

public record DTOTaskDetails(
        @Schema(description = "ID da tarefa", example = "1")
        Long id,
        @Schema(description = "Titulo da tarefa", example = "titulo")
        String titulo,
        @Schema(description = "Descrição da tarefa", example = "descrição")
        String task,
        @Schema(description = "Se a tarefa está completa", example = "true")
        Boolean completd) {
    public DTOTaskDetails(Task toDo) {
        this(toDo.getId(), toDo.getTitulo(), toDo.getTask(), toDo.getCompleted());
    }
}
