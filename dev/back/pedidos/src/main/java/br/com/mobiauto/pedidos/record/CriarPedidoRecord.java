package br.com.mobiauto.pedidos.record;

import java.util.List;

import br.com.mobiauto.pedidos.model.TipoPedido;

public record CriarPedidoRecord(Long clienteId, TipoPedido tipo, List<Long> veiculosIds) {
}
