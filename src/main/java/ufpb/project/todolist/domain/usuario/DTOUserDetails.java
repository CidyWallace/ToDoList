package ufpb.project.todolist.domain.usuario;

public record DTOUserDetails(Long id, String login) {
    public DTOUserDetails(Usuario usuario){
        this(usuario.getId(), usuario.getLogin());
    }
}
