package techchallenge3.hospitalservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import techchallenge3.hospitalservice.enums.PerfilUsuario;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    @JsonIgnore
    private String senha;

    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfil;
}