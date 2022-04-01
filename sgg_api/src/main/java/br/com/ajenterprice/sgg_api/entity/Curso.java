package br.com.ajenterprice.sgg_api.entity;

import br.com.ajenterprice.sgg_api.constant.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="curso")
public class Curso {

    @Id
    @Column(columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_criacao")
    private Date dataCriacao;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_inicio")
    private Date dataInicio;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_fim")
    private Date dataFim;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "instituicao_ensino_ID", referencedColumnName = "id")
    private InstituicaoEnsino instituicaoEnsino;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Column(name = "nota")
    private Float nota;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column(name = "finalizado")
    private Boolean finalizado;

}
