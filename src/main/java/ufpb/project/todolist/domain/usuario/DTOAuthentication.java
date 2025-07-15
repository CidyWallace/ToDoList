package ufpb.project.todolist.domain.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DTOAuthentication(
        @Schema(description = "Email do usuário", example = "usuario@gmail.com")
        @NotBlank @Email
        String login,
        @Schema(description = "senha do usuário", example = "123")
        @NotBlank
        String senha) {
}
