CREATE TABLE pagamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(10,2) NOT NULL,
    metodo_pagamento VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_confirmacao TIMESTAMP NULL,
    pedido_id BIGINT NOT NULL
) ENGINE=InnoDB;


CREATE TABLE transacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_transacao VARCHAR(255) NOT NULL UNIQUE,
    valor DECIMAL(10,2) NOT NULL,
    data_processamento TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    pagamento_id BIGINT NOT NULL,
    CONSTRAINT fk_transacao_pagamento FOREIGN KEY (pagamento_id) REFERENCES pagamentos(id) ON DELETE CASCADE
) ENGINE=InnoDB;