package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    private static final String HOST     = "localhost";
    private static final String PORT     = "3306";
    private static final String DATABASE = "ninja_db";
    private static final String USUARIO  = "root";
    private static final String SENHA    = "admin";

    public static void inicializar() throws SQLException {
        criarBancoDeDados();
        try (Connection con = getConexaoDB()) {
            criarTabelas(con);
            criarViews(con);
            inserirDadosIniciais(con);
        }
    }

    public static Connection getConexaoApp() throws SQLException {
        return ConnectionFactory.getConnection(HOST, PORT, DATABASE, USUARIO, SENHA);
    }

    private static final String PARAMS = "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private static Connection getConexaoDB() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + PARAMS,
                USUARIO, SENHA
        );
    }

    private static void criarBancoDeDados() throws SQLException {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://" + HOST + ":" + PORT + "/" + PARAMS,
                USUARIO, SENHA);
             Statement st = con.createStatement()) {
            st.execute("CREATE DATABASE IF NOT EXISTS " + DATABASE + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
        }
    }

    private static void criarTabelas(Connection con) throws SQLException {
        try (Statement st = con.createStatement()) {
            st.execute(
                "CREATE TABLE IF NOT EXISTS tb_ninja (" +
                "  id              INT AUTO_INCREMENT PRIMARY KEY," +
                "  nome            VARCHAR(100) NOT NULL," +
                "  vila            VARCHAR(100)," +
                "  cla             VARCHAR(100)," +
                "  rank_ninja      VARCHAR(50)," +
                "  natureza_chakra VARCHAR(100)," +
                "  status          VARCHAR(50)" +
                ")"
            );
            st.execute(
                "CREATE TABLE IF NOT EXISTS tb_missao (" +
                "  id           INT AUTO_INCREMENT PRIMARY KEY," +
                "  titulo       VARCHAR(200) NOT NULL," +
                "  descricao    TEXT," +
                "  rank_missao  CHAR(1)," +
                "  vila_origem  VARCHAR(100)," +
                "  status       VARCHAR(50)" +
                ")"
            );
            st.execute(
                "CREATE TABLE IF NOT EXISTS tb_ninja_missao (" +
                "  id                INT AUTO_INCREMENT PRIMARY KEY," +
                "  id_ninja          INT NOT NULL," +
                "  id_missao         INT NOT NULL," +
                "  funcao            VARCHAR(100)," +
                "  data_participacao DATE," +
                "  UNIQUE KEY uq_ninja_missao (id_ninja, id_missao)," +
                "  FOREIGN KEY (id_ninja)  REFERENCES tb_ninja(id)  ON DELETE CASCADE," +
                "  FOREIGN KEY (id_missao) REFERENCES tb_missao(id) ON DELETE CASCADE" +
                ")"
            );
            st.execute(
                "CREATE TABLE IF NOT EXISTS tb_totalizador_ninja (" +
                "  id           INT AUTO_INCREMENT PRIMARY KEY," +
                "  descricao    VARCHAR(200)," +
                "  quantidade   INT," +
                "  data_geracao DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );
        }
    }

    private static void criarViews(Connection con) throws SQLException {
        try (Statement st = con.createStatement()) {
            st.execute("CREATE OR REPLACE VIEW vw_ninja_missoes AS " +
                "SELECT n.nome AS nome_ninja, n.vila, n.cla, n.rank_ninja, " +
                "m.titulo AS titulo_missao, m.rank_missao, nm.funcao, m.status AS status_missao " +
                "FROM tb_ninja_missao nm " +
                "JOIN tb_ninja  n ON n.id = nm.id_ninja " +
                "JOIN tb_missao m ON m.id = nm.id_missao"
            );
            st.execute("CREATE OR REPLACE VIEW vw_total_ninjas_por_vila AS " +
                "SELECT vila, COUNT(*) AS quantidade_ninjas FROM tb_ninja GROUP BY vila ORDER BY vila"
            );
            st.execute("CREATE OR REPLACE VIEW vw_total_missoes_por_rank AS " +
                "SELECT rank_missao, COUNT(*) AS quantidade_missoes FROM tb_missao GROUP BY rank_missao ORDER BY rank_missao"
            );
            st.execute("CREATE OR REPLACE VIEW vw_missoes_sem_ninjas AS " +
                "SELECT m.id AS id_missao, m.titulo, m.rank_missao, m.vila_origem, m.status " +
                "FROM tb_missao m " +
                "WHERE m.id NOT IN (SELECT DISTINCT id_missao FROM tb_ninja_missao)"
            );
        }
    }

    private static void inserirDadosIniciais(Connection con) throws SQLException {
        try (Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM tb_ninja");
            rs.next();
            if (rs.getInt(1) > 0) return;
        }

        try (Statement st = con.createStatement()) {
            st.execute("INSERT INTO tb_ninja (nome, vila, cla, rank_ninja, natureza_chakra, status) VALUES " +
                "('Naruto',  'Konoha', 'Uzumaki',  'Genin',  'Vento', 'Ativo')," +
                "('Sasuke',  'Konoha', 'Uchiha',   'Genin',  'Raio',  'Ativo')," +
                "('Sakura',  'Konoha', 'Haruno',   'Chunin', 'Terra', 'Ativo')," +
                "('Kakashi', 'Konoha', 'Hatake',   'Jounin', 'Raio',  'Ativo')," +
                "('Gaara',   'Suna',   'Kazekage', 'Kage',   'Areia', 'Ativo')"
            );
            st.execute("INSERT INTO tb_missao (titulo, descricao, rank_missao, vila_origem, status) VALUES " +
                "('Capturar gato perdido',  'Recuperar o gato da senhora Neko',       'D', 'Konoha', 'Aberta')," +
                "('Escolta de comerciante', 'Proteger comerciante ate a fronteira',    'C', 'Konoha', 'Aberta')," +
                "('Defender a fronteira',   'Patrulha e defesa da fronteira norte',    'B', 'Suna',   'Aberta')," +
                "('Investigar Akatsuki',    'Rastrear membros da Akatsuki',            'A', 'Konoha', 'Aberta')," +
                "('Proteger o Kazekage',    'Escolta e protecao do Kazekage',          'S', 'Suna',   'Aberta')"
            );
        }
    }

}
