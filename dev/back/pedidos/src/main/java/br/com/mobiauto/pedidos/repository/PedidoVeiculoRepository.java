package br.com.mobiauto.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mobiauto.pedidos.model.PedidoVeiculo;

public interface PedidoVeiculoRepository extends JpaRepository<PedidoVeiculo, Long> {

}
