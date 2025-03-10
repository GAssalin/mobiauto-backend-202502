# 📌 Mobiauto - Arquitetura de Microservices

## 🚀 Introdução
A **Mobiauto** é uma plataforma inovadora para facilitar a compra e venda de veículos no Brasil. Esta arquitetura utiliza microservices para gerenciar pagamentos, pedidos e um API Gateway para roteamento e descoberta de serviços.

## 🏗️ Visão Geral
### 🔹 Tecnologias Utilizadas
- **Backend**: Java + Spring Boot
- **Banco de Dados**: MySQL + Flyway (para versionamento de banco)
- **Autenticação**: JWT (JSON Web Token) -> 🛠️ [em construção]
- **Service Discovery**: Eureka Server
- **API Gateway**: Spring Cloud Gateway

### 🔹 Arquitetura do Serviço
O sistema segue uma arquitetura baseada em **microservices**, onde os serviços se comunicam via REST.

🔹 **Fluxo de Comunicação**
```
[Usuário] --> [API Gateway (8082)] --> [Eureka Server (8761)] --> [Microservices]
                                        |--> [Serviço de Pagamentos]
                                        |--> [Serviço de Pedidos]
```

## ⚙️ Configuração e Variáveis de Ambiente
### 🔹 Pré-requisitos
- Java 23
- MySQL instalado e configurado

### 🔹 Configuração do Banco de Dados
```
url=jdbc:mysql://localhost:3306/mobiauto
username=root
password=root
```
### ⚠️ IMPORTANTE ⚠️
Em ambiente real, é aconselhado as informações acima estarem presentes em variáveis de ambiente.



#### 📌 **Configuração de Portas**
```properties
DBManager: 8080  # Porta padrão
pedidos: random  # Porta aleatória
pagamentos: random  # Porta aleatória
server: 8761  # Porta padrão para Eureka Server
gateway: 8082
```

## 📂 Estrutura do Projeto
```
📂 mobiauto-microservices
 ┣ 📂 dbManager (Gerenciamento do banco de dados com Flyway)
 ┣ 📂 pedido-service (Gerenciamento de pedidos)
 ┣ 📂 pagamento-service (Gerenciamento de pagamentos)
 ┣ 📂 eureka-server (Service Discovery com Eureka Server)
 ┣ 📂 api-gateway (Gateway e roteamento com Spring Cloud Gateway)
```

## 🛠️ Versionamento de Banco de Dados com Flyway
Para criar uma nova migração, no DBManager, adicione um arquivo SQL em:
```
src/main/resources/db/migration/
```
Exemplo de arquivo de migração:
```sql
CREATE TABLE pagamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(10,2) NOT NULL,
    metodo_pagamento VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_confirmacao TIMESTAMP NULL,
    pedido_id BIGINT NOT NULL
) ENGINE=InnoDB;
```
"ENGINE=InnoDB" é utilizado em banco de dados MySql por conta de aplicações transacionais.
Vantagens:
- Suporte a Transações
- Integridade Referencial (Chaves Estrangeiras)
- Bloqueio a Nível de Linha (Row-Level Locking)
- Journaling e Redo Logs (Recuperação de Falhas)
- MVCC (Multi-Version Concurrency Control)
- Índices Clusterizados

## 🎮 Como Executar o Projeto
### 🔹 Pré-requisitos
- Executar o terminal para subir as instâncias
- Executar o DBManager para que seja criado as primeiras tabelas dentro do banco de dados.
- Executar o Server para que seja criado o servidor de armazenagem dos MS.
- Executar o Gateway para que seja criado o local de requisição centralizado.
### 🔹 Execução
Comando de execução:
```
& "[substituir pelo caminho do microservico]\mvnw.cmd" spring-boot:run -f "[substituir pelo caminho do microservico]\pom.xml"
```
### 🔹 Exemplo:
& "E:\PROJETOS\mobiauto-backend-202502\dev\back\DBManager\mvnw.cmd" spring-boot:run -f "E:\PROJETOS\mobiauto-backend-202502\dev\back\DBManager\pom.xml"
### 🔹 Load Balancing
Caso haja a necessidade de escalabilidade horizontal, ou seja, o sistema conseguir processar mais requisições simultaneamente, é possível executar diversas instâncias ao mesmo tempo, basta repetir o comando para o mesmo serviço.

Após a execução é possível verificar as execuções acessando a pagina local do server.
Neste projeto, seria: "http://localhost:8761/"

## 📞 Suporte e Contato
Caso tenha dúvidas ou precise de suporte, utilize os canais abaixo:
- 📧 E-mail: 🛠️[em construção]
- 📖 FAQ e documentação: 🛠️[em construção]

---

💡 **Contribuições são bem-vindas!** Caso queira contribuir, abra um PR ou entre em contato pelo e-mail acima. 🎯

---

📢 **Licença:** 🛠️[em construção].
