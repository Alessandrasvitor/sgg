package br.com.ajenterprice.sgg_api.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CapituloDTO {

    private Integer ordem;
    private String titulo;
    private Date dataCriacao;
    private String capitulo;
    private Long livro;
}
