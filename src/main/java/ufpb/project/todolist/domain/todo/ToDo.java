package ufpb.project.todolist.domain.todo;

import jakarta.persistence.*;
import lombok.*;
import ufpb.project.todolist.domain.usuario.Usuario;

@Entity(name = "ToDo")
@Table(name = "todos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ToDo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String task;
    private Boolean completed;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    public ToDo(DadosCriarTarefa dados) {
        this.titulo = dados.titulo();
        this.task = dados.task();
        this.completed = false;
    }

    public ToDo AtualizaDados(DadosCriarTarefa dados) {
        if(dados.titulo() != null){
            this.titulo = dados.titulo();
        }
        if(dados.task() != null){
            this.task = dados.task();
        }
        return this;
    }
}
