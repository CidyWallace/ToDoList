package ufpb.project.todolist.infra.security;

import io.swagger.v3.oas.annotations.media.Schema;

public record DTOTokenJWT(
        @Schema(description = "Token de validação", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlhZ29AZ21haWwuY29tIiwiaXNzIjoiQVBJIFRvRG8iLCJleHAiOjE3NTI2MDI3OTd9.noZcTp6coc3uo7ibiUnbZj42UF6TOgcuK5IXptI_cW4")
        String token) {
}
