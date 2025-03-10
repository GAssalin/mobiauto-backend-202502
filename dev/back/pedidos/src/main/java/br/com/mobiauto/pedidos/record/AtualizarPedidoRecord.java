package br.com.mobiauto.pedidos.record;

import java.time.LocalDateTime;
import java.util.List;

import br.com.mobiauto.pedidos.model.StatusPedido;
import br.com.mobiauto.pedidos.model.TipoPedido;

public record AtualizarPedidoRecord(Long id, Long clienteId, StatusPedido status, TipoPedido tipo, LocalDateTime dataCriacao, List<Long> veiculosIds) {

}
