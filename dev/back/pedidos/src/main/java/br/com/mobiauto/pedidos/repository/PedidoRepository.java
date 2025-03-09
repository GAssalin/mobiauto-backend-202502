package br.com.mobiauto.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mobiauto.pedidos.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
