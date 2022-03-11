package br.com.enterprice.sgg.services;

import br.com.enterprice.sgg.entity.Usuario;
import br.com.enterprice.sgg.entity.dto.UsuarioDTO;
import br.com.enterprice.sgg.exception.ServiceException;
import br.com.enterprice.sgg.repository.UsuarioRepository;
import br.com.enterprice.sgg.util.CriptografiaUtil;
import br.com.enterprice.sgg.util.GeralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List listar() {
        return usuarioRepository.findAll();
    }

    public void remover(Long id) {
        Usuario usuario = buscar(id);
        usuarioRepository.delete(usuario);
    }

    public Usuario buscar(Long id) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);
        if(usuarioOp.isEmpty()) {
            throw new ServiceException("Usuário não encontrado!");
        }
        return usuarioOp.get();
    }

    public Usuario alterar(Long id, UsuarioDTO usuario) {
        if(validarEmailExistente(id, usuario.getEmail())) {
            throw new ServiceException("Email já cadastrado no sistema!");
        }
        Usuario usuarioNovo = buscar(id);
        usuarioNovo.setNome(usuario.getNome());
        usuarioNovo.setPerfil(usuario.getPerfil());
        usuarioNovo.setEmail(usuario.getEmail());
        usuarioRepository.save(usuarioNovo);

        return usuarioNovo;
    }

    public void salvar(UsuarioDTO usuario) {
        if(validarEmailExistente(usuario.getEmail())) {
            throw new ServiceException("Email já existe no sistema!");
        }
        validarSenha(usuario.getSenha());
        Usuario usuarioNovo = new Usuario();
        usuarioNovo.setNome(usuario.getNome());
        usuarioNovo.setPerfil(usuario.getPerfil());
        usuarioNovo.setEmail(usuario.getEmail());
        usuarioNovo.setSenha(CriptografiaUtil.criptografarSHA256(usuario.getSenha()));
        usuarioRepository.save(usuarioNovo);
    }

    private void validarSenha(String senha) {
        if(!GeralUtil.stringNullOrEmpty(senha)) {
            throw new ServiceException("O preenchimento da senha é obrigatório!");
        }
        if(senha.length() < 6 || senha.length() > 8) {
            throw new ServiceException("A senha deve conter entre 6 e 8 caracteres!");
        }
        if(senha.matches("^[a-zA-ZÁÂÃÀÇÉÊÍÓÔÕÚÜáâãàçéêíóôõúü]*$") || senha.matches("^[0-9]*$")) {
            throw new ServiceException("A senha deve conter letras e números");
        }
    }

    private Boolean validarEmailExistente(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        return usuario != null;
    }

    private Boolean validarEmailExistente(Long id, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        return usuario != null && usuario.getId() != id;
    }

    public void alterarSenha(Long id, String senha) {
        validarSenha(senha);
        Usuario usuario = buscar(id);
        usuario.setSenha(CriptografiaUtil.criptografarSHA256(senha));
        usuarioRepository.save(usuario);
    }

    public void logar(UsuarioDTO u) {
        if(!GeralUtil.stringNullOrEmpty(u.getEmail())) {
            throw new ServiceException("Email não informado!");
        }
        if(!GeralUtil.stringNullOrEmpty(u.getSenha())) {
            throw new ServiceException("Senha não informada!");
        }
        Usuario usuario = usuarioRepository.findByEmailAndSenha(u.getEmail(), CriptografiaUtil.criptografarSHA256(u.getSenha()));
        if(usuario == null) {
            throw new ServiceException("Usuário ou Senha inválido!");
        }
    }

}
