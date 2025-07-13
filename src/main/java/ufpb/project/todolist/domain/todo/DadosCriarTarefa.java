package ufpb.project.todolist.domain.todo;

import jakarta.validation.constraints.NotBlank;

public record DadosCriarTarefa(
        @NotBlank
        String titulo,
        @NotBlank
        String task) {
}
