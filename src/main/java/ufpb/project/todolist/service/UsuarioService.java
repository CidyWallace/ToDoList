package ufpb.project.todolist.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ufpb.project.todolist.domain.usuario.DadosAutenticacao;
import ufpb.project.todolist.domain.usuario.DadosDetalhentoUsuario;
import ufpb.project.todolist.domain.usuario.Usuario;
import ufpb.project.todolist.infra.security.TokenService;
import ufpb.project.todolist.repository.UsuarioRepository;

import java.net.URI;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, AuthenticationManager manager, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrar(DadosAutenticacao dados) {
        var email = usuarioRepository.existsByLogin(dados.login());

        if (email) {
            throw new RuntimeException("Esse email já foi registrado");
        }
        var senhaBcrypt = passwordEncoder.encode(dados.senha());
        var usuario = new Usuario(dados.login(), senhaBcrypt);
        usuarioRepository.save(usuario);
        return usuario;
    }

    public List<DadosDetalhentoUsuario> findAllUser(){
        return usuarioRepository.findAll().stream().map(DadosDetalhentoUsuario::new).toList();
    }

    public DadosDetalhentoUsuario findByIdUser(Long id) {
        var user = usuarioRepository.findById(id);
        if(user.isPresent()) {
            return new DadosDetalhentoUsuario(user.get());
        }else{
            throw new NullPointerException("Usuário não encontrado!");
        }

    }

    public URI criarURI(Usuario usuario, UriComponentsBuilder uriBuilder) {
        return uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
    }

}
