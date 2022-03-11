package br.com.enterprice.sgg.repository;

import br.com.enterprice.sgg.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);
    Usuario findByEmailAndSenha(String email, String senha);
}
