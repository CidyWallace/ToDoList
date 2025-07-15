package ufpb.project.todolist.domain.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

public record DTOUserDetails(
        @Schema(description = "ID do usuário", example = "1")
        Long id,
        @Schema(description = "Email do usuário", example = "usuario@gmail.com")
        String login) {
    public DTOUserDetails(Usuario usuario){
        this(usuario.getId(), usuario.getLogin());
    }
}
