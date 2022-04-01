package br.com.ajenterprice.sgg_api.repository;

import br.com.ajenterprice.sgg_api.entity.Curso;
import br.com.ajenterprice.sgg_api.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNome(String nome);
}
