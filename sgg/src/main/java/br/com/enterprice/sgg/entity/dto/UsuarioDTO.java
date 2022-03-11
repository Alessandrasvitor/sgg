package br.com.enterprice.sgg.entity.dto;

import br.com.enterprice.sgg.constant.Perfil;
import br.com.enterprice.sgg.entity.Usuario;
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
