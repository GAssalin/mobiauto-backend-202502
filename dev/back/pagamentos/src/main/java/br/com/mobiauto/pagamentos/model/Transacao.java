package br.com.mobiauto.pagamentos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoTransacao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataProcessamento;

    @OneToOne
    @JoinColumn(name = "pagamento_id", nullable = false)
    private Pagamento pagamento;

    @PrePersist
    protected void onProcess() {
        this.dataProcessamento = LocalDateTime.now();
    }

}