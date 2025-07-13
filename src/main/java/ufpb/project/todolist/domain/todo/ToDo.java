package ufpb.project.todolist.domain.todo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "ToDo")
@Table(name = "todos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class ToDo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String task;
    private Boolean completed;

    public ToDo(DadosCriarTarefa dados) {
        this.titulo = dados.titulo();
        this.task = dados.task();
        this.completed = true;
    }
}
