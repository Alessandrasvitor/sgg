package br.com.ajenterprice.sgg_api.entity;

import br.com.ajenterprice.sgg_api.constant.TipoAnotacao;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name="anotacao")
public class Anotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String titulo;
    private String descricao;
    @NotNull
    @CreationTimestamp
    @Column(name = "data_criacao")
    private Date dataCriacao;
    @Column(name = "data_vencimento")
    private Date dataVencimento;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_anotacao")
    private TipoAnotacao tipoAnotacao;
    @NotNull
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

}
