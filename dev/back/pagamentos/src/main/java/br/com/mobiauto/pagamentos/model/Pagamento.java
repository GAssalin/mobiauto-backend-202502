package br.com.mobiauto.pagamentos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPagamento metodoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column
    private LocalDateTime dataConfirmacao;

    @OneToOne(mappedBy = "pagamento", cascade = CascadeType.ALL)
    private Transacao transacao;
    
    @NotNull
    private Long pedidoId;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusPagamento.PENDENTE;
    }
    
}