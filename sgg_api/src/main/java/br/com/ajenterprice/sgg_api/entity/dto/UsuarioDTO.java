package br.com.ajenterprice.sgg_api.entity.dto;

import br.com.ajenterprice.sgg_api.constant.Perfil;
import br.com.ajenterprice.sgg_api.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDTO {

    private String nome;
    private String email;
    private String senha;
    private Perfil perfil;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.perfil = usuario.getPerfil();
    }
}
