package br.com.ajenterprice.sgg_api.entity.dto;

import br.com.ajenterprice.sgg_api.constant.TipoAnotacao;
import br.com.ajenterprice.sgg_api.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class AnotacaoDTO {

    private String titulo;
    private String descricao;
    private Date dataCriacao;
    private Date dataVencimento;
    private TipoAnotacao tipoAnotacao;
    private Usuario usuario;

}
