CREATE TABLE IF NOT EXISTS simulacoes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    valorInvestido DECIMAL(15, 2) NOT NULL,
    prazoMeses INT NOT NULL,
    tipoProduto VARCHAR(255) NOT NULL,
    dataSimulacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO produtos (nome, tipoProduto, rentabilidadeAnual, risco,
                      prazoMinMeses, prazoMaxMeses, valorMin, valorMax)
VALUES
( "CDB Caixa", "CDB", 0.1, 0.2, 1, 12, 1000, 100000),
( "LCI Caixa", "LCI", 0.08, 0.1, 3, 24, 5000, 50000),
( "LCA Caixa", "LCA", 0.09, 0.15, 2, 36, 2000, 20000),
( "Tesouro Selic", "Tesouro Direto", 0.07, 0.05, 1, 120, 1000, 100000),
( "Tesouro IPCA+", "Tesouro Direto", 0.12, 0.25, 6, 120, 1000, 100000),
( "CDB Alternativo", "CDB", 0.15, 0.3, 1, 12, 1000, 100000);

CREATE TABLE IF NOT EXISTS simulacoes_teste (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    valorInvestido DECIMAL(15, 2) NOT NULL,
    prazoMeses INT NOT NULL,
    tipoProduto VARCHAR(255) NOT NULL,
    dataSimulacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO simulacoes (clienteId, produtoNome, tipoProduto, valorInvestido, prazoMeses,
                              rentabilidadeAplicada, valorFinal, dataSimulacao)
VALUES
(1234, "CDB teste", "CDB", 10000,
 12, 0.1, 10000, CURRENT_TIMESTAMP);