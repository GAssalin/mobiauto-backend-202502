package br.com.mobiauto.pagamentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mobiauto.pagamentos.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
