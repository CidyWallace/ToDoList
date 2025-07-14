package ufpb.project.todolist.domain.usuario;

public record DTODetalhentoUsuario(Long id, String login) {
    public DTODetalhentoUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getLogin());
    }
}
