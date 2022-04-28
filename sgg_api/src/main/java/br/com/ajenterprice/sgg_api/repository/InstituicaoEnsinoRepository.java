package br.com.ajenterprice.sgg_api.repository;

import br.com.ajenterprice.sgg_api.entity.InstituicaoEnsino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituicaoEnsinoRepository extends JpaRepository<InstituicaoEnsino, Long> {
    InstituicaoEnsino findByNome(String nome);
}
