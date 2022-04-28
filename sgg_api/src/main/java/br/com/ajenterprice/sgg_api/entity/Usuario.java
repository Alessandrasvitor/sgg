package br.com.ajenterprice.sgg_api.entity;

import br.com.ajenterprice.sgg_api.constant.Perfil;
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
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private String email;
    @NotNull
    //senhas padrão bh1234
    private String senha;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Perfil perfil;

}
