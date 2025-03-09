package br.com.mobiauto.pagamentos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.mobiauto.pagamentos.model.Pagamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDto {

	private Long id;
    private String codigoTransacao;
    private BigDecimal valor;
    private LocalDateTime dataProcessamento;
    private Pagamento pagamento;
	
}
