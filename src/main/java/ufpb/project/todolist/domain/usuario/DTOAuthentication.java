package ufpb.project.todolist.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DTOAuthentication(
        @NotBlank @Email
        String login,
        @NotBlank
        String senha) {
}
