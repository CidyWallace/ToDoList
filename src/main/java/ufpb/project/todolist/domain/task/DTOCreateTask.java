package ufpb.project.todolist.domain.task;

import jakarta.validation.constraints.NotBlank;

public record DTOCreateTask(
        @NotBlank
        String titulo,
        @NotBlank
        String task) {
}
