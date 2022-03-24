package br.com.ajenterprice.sgg_api.repository;

import br.com.ajenterprice.sgg_api.entity.Anotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnotacaoRepository extends JpaRepository<Anotacao, Long> {

}
