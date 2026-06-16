-- Script de criacao das tabelas do Sistema Ninja
-- Banco: ninja_db (MySQL)

CREATE TABLE IF NOT EXISTS tb_ninja (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    nome            VARCHAR(100) NOT NULL,
    vila            VARCHAR(100),
    cla             VARCHAR(100),
    rank_ninja      VARCHAR(50),
    natureza_chakra VARCHAR(100),
    status          VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS tb_missao (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    titulo       VARCHAR(200) NOT NULL,
    descricao    TEXT,
    rank_missao  CHAR(1) CHECK (rank_missao IN ('D', 'C', 'B', 'A', 'S')),
    vila_origem  VARCHAR(100),
    status       VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS tb_ninja_missao (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    id_ninja          INT NOT NULL,
    id_missao         INT NOT NULL,
    funcao            VARCHAR(100),
    data_participacao DATE,
    UNIQUE KEY uq_ninja_missao (id_ninja, id_missao),
    FOREIGN KEY (id_ninja)  REFERENCES tb_ninja(id)  ON DELETE CASCADE,
    FOREIGN KEY (id_missao) REFERENCES tb_missao(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tb_totalizador_ninja (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    descricao    VARCHAR(200),
    quantidade   INT,
    data_geracao DATETIME DEFAULT CURRENT_TIMESTAMP
);
