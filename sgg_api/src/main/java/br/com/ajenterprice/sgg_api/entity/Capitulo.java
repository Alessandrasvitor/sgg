package br.com.ajenterprice.sgg_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name="capitulo")
public class Capitulo {

    @Id
    @Column(columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Integer ordem;
    @NotNull
    private String titulo;
    @NotNull
    @Column(name = "data_criacao")
    private Date dataCriacao;
    @NotNull
    private String capitulo;
    @NotNull
    @JoinColumn(name = "livro_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Livro livro;

    public Long getLivroId() {
        return this.livro.getId();
    }

}
