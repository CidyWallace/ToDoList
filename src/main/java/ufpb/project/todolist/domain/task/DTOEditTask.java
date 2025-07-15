package ufpb.project.todolist.domain.task;

import io.swagger.v3.oas.annotations.media.Schema;

public record DTOEditTask(
        @Schema(description = "Titulo da tarefa", example = "Titulo")
        String titulo,
        @Schema(description = "Descrição da tarefa", example = "descrição")
        String task) {
}
