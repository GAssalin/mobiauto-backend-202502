package br.com.mobiauto.pedidos.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.mobiauto.pedidos.model.StatusPedido;
import br.com.mobiauto.pedidos.model.TipoPedido;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {

	private Long id;
    private Long clienteId;
    private StatusPedido status;
    private TipoPedido tipo;
    private LocalDateTime dataCriacao;
    private List<VeiculoDTO> veiculos;
    
}
