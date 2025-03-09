package br.com.mobiauto.pedidos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoDTO {

	private Long id;
    private String marca;
    private String modelo;
    private int ano;
    private Double preco;
	
}
