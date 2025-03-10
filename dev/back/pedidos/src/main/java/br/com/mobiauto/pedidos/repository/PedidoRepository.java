package br.com.mobiauto.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.mobiauto.pedidos.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	@Query("SELECT p FROM Pedido p " + "JOIN FETCH p.pedidoVeiculos pv " + "JOIN FETCH pv.veiculo v "
			+ "WHERE p.id = :pedidoId")
	Pedido findPedidoComVeiculos(@Param("pedidoId") Long pedidoId);

}
