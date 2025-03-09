package br.com.mobiauto.pedidos.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clienteId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPedido tipo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoVeiculo> pedidoVeiculos;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusPedido.AGUARDANDO_PAGAMENTO;
    }
    
}
