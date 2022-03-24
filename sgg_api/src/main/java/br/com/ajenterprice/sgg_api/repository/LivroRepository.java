package br.com.ajenterprice.sgg_api.repository;

import br.com.ajenterprice.sgg_api.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Livro findByNome(String nome);
}
