package ufpb.project.todolist.domain.usuario;

public record DadosDetalhentoUsuario(Long id, String login) {
    public DadosDetalhentoUsuario (Usuario usuario){
        this(usuario.getId(), usuario.getLogin());
    }
}
