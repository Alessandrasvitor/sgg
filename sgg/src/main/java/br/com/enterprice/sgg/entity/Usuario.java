package br.com.enterprice.sgg.entity;

import br.com.enterprice.sgg.constant.Perfil;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name="usuario")
public class Usuario {

    @Id
    @Column(columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private String email;
    @NotNull
    private String senha;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    public Usuario() {
    }

}
