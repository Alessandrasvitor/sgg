package br.com.ajenterprice.sgg_api.entity;

import br.com.ajenterprice.sgg_api.constant.Genero;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name="livro")
public class Livro {

    @Id
    @Column(columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String nome;
    @Column(name = "sub_nome")
    private String subNome;
    @NotNull
    @Column(name = "data_criacao")
    private Date dataCriacao;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @OneToMany(mappedBy = "livro", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordem ASC")
    private List<Capitulo> capitulos;

    @NotNull
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

}
