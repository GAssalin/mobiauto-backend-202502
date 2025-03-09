package br.com.mobiauto.pagamentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mobiauto.pagamentos.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

}
