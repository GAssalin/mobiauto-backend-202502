package br.com.mobiauto.pedidos.dto;

import java.util.List;

import br.com.mobiauto.pedidos.model.TipoPedido;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarPedidoDTO {

	private Long clienteId;
    private TipoPedido tipo;
    private List<Long> veiculosIds;
	
}
