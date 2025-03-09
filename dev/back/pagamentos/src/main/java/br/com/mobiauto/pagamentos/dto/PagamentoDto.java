package br.com.mobiauto.pagamentos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.mobiauto.pagamentos.model.MetodoPagamento;
import br.com.mobiauto.pagamentos.model.StatusPagamento;
import br.com.mobiauto.pagamentos.model.Transacao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoDto {

	private Long id;
    private BigDecimal valor;
    private MetodoPagamento metodoPagamento;
    private StatusPagamento status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConfirmacao;
    private Transacao transacao;
    private Long pedidoId;
	
}
