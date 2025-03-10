package br.com.mobiauto.pedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.mobiauto.pedidos.model.PedidoVeiculo;
import jakarta.transaction.Transactional;

public interface PedidoVeiculoRepository extends JpaRepository<PedidoVeiculo, Long> {

    List<PedidoVeiculo> findByPedidoId(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM PedidoVeiculo pv WHERE pv.pedido.id = :id")
    void deleteByPedidoId(@Param("id") Long id);

}
