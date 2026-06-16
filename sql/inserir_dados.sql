-- Script de insercao de dados iniciais

-- Ninjas
INSERT INTO tb_ninja (nome, vila, cla, rank_ninja, natureza_chakra, status) VALUES
    ('Naruto',  'Konoha', 'Uzumaki',  'Genin',  'Vento', 'Ativo'),
    ('Sasuke',  'Konoha', 'Uchiha',   'Genin',  'Raio',  'Ativo'),
    ('Sakura',  'Konoha', 'Haruno',   'Chunin', 'Terra', 'Ativo'),
    ('Kakashi', 'Konoha', 'Hatake',   'Jounin', 'Raio',  'Ativo'),
    ('Gaara',   'Suna',   'Kazekage', 'Kage',   'Areia', 'Ativo');

-- Missoes
INSERT INTO tb_missao (titulo, descricao, rank_missao, vila_origem, status) VALUES
    ('Capturar gato perdido',   'Recuperar o gato da senhora Neko', 'D', 'Konoha', 'Aberta'),
    ('Escolta de comerciante',  'Proteger comerciante ate a fronteira', 'C', 'Konoha', 'Aberta'),
    ('Defender a fronteira',    'Patrulha e defesa da fronteira norte', 'B', 'Suna',   'Aberta'),
    ('Investigar Akatsuki',     'Rastrear membros da Akatsuki', 'A', 'Konoha', 'Aberta'),
    ('Proteger o Kazekage',     'Escolta e protecao do Kazekage', 'S', 'Suna',   'Aberta');
