-- Script de criacao das views do Sistema Ninja (MySQL)

-- View 1: Ninjas e suas missoes
CREATE OR REPLACE VIEW vw_ninja_missoes AS
SELECT
    n.nome          AS nome_ninja,
    n.vila,
    n.cla,
    n.rank_ninja,
    m.titulo        AS titulo_missao,
    m.rank_missao,
    nm.funcao,
    m.status        AS status_missao
FROM tb_ninja_missao nm
JOIN tb_ninja  n ON n.id = nm.id_ninja
JOIN tb_missao m ON m.id = nm.id_missao;

-- View 2: Total de ninjas por vila
CREATE OR REPLACE VIEW vw_total_ninjas_por_vila AS
SELECT
    vila,
    COUNT(*) AS quantidade_ninjas
FROM tb_ninja
GROUP BY vila
ORDER BY vila;

-- View 3: Total de missoes por rank
CREATE OR REPLACE VIEW vw_total_missoes_por_rank AS
SELECT
    rank_missao,
    COUNT(*) AS quantidade_missoes
FROM tb_missao
GROUP BY rank_missao
ORDER BY rank_missao;

-- View 4: Missoes sem nenhum ninja vinculado
CREATE OR REPLACE VIEW vw_missoes_sem_ninjas AS
SELECT
    m.id    AS id_missao,
    m.titulo,
    m.rank_missao,
    m.vila_origem,
    m.status
FROM tb_missao m
WHERE m.id NOT IN (SELECT DISTINCT id_missao FROM tb_ninja_missao);
