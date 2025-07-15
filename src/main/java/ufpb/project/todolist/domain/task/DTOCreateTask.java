package ufpb.project.todolist.domain.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record DTOCreateTask(
        @Schema(description = "Titulo da tarefa", example = "Titulo")
        @NotBlank
        String titulo,
        @Schema(description = "descrição da tarefa", example = "descrição")
        @NotBlank
        String task) {
}
