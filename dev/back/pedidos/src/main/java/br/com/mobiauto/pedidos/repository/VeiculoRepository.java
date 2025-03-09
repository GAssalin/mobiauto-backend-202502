package br.com.mobiauto.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mobiauto.pedidos.model.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

}
