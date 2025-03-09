package br.com.mobiauto.pagamentos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDto {

	private Long id;
    private String codigoTransacao;
    private BigDecimal valor;
    private LocalDateTime dataProcessamento;
    private Long pagamentoId;
	
}
