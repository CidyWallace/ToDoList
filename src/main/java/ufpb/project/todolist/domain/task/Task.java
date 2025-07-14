package ufpb.project.todolist.domain.task;

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
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String task;
    private Boolean completed;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    public Task(DTOCreateTask dados) {
        this.titulo = dados.titulo();
        this.task = dados.task();
        this.completed = false;
    }

    public Task AtualizaDados(DTOEditTask dados) {
        if(dados.titulo() != null){
            this.titulo = dados.titulo();
        }
        if(dados.task() != null){
            this.task = dados.task();
        }
        return this;
    }
}
