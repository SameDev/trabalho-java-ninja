package database.dao;

import database.model.Totalizador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TotalizadorDAO {

    private String insert =
            "INSERT INTO tb_totalizador_ninja(descricao, quantidade, data_geracao) VALUES(?, ?, NOW())";

    private String deleteAll =
            "DELETE FROM tb_totalizador_ninja";

    private String selectAll =
            "SELECT * FROM tb_totalizador_ninja ORDER BY id";

    private String ninjasPorVila =
            "SELECT vila, COUNT(*) AS qtd FROM tb_ninja GROUP BY vila ORDER BY vila";

    private String ninjasPorRank =
            "SELECT rank_ninja, COUNT(*) AS qtd FROM tb_ninja GROUP BY rank_ninja ORDER BY rank_ninja";

    private String ninjasPorNatureza =
            "SELECT natureza_chakra, COUNT(*) AS qtd FROM tb_ninja GROUP BY natureza_chakra ORDER BY natureza_chakra";

    private String missoesPorRank =
            "SELECT rank_missao, COUNT(*) AS qtd FROM tb_missao GROUP BY rank_missao ORDER BY rank_missao";

    private String missoesPorStatus =
            "SELECT status, COUNT(*) AS qtd FROM tb_missao GROUP BY status ORDER BY status";

    private String ninjasVinculados =
            "SELECT COUNT(DISTINCT id_ninja) AS qtd FROM tb_ninja_missao";

    private String missoesSemNinjas =
            "SELECT COUNT(*) AS qtd FROM tb_missao WHERE id NOT IN (SELECT DISTINCT id_missao FROM tb_ninja_missao)";

    private PreparedStatement pstInsert;
    private PreparedStatement pstDeleteAll;
    private PreparedStatement pstSelectAll;
    private PreparedStatement pstNinjasPorVila;
    private PreparedStatement pstNinjasPorRank;
    private PreparedStatement pstNinjasPorNatureza;
    private PreparedStatement pstMissoesPorRank;
    private PreparedStatement pstMissoesPorStatus;
    private PreparedStatement pstNinjasVinculados;
    private PreparedStatement pstMissoesSemNinjas;

    public TotalizadorDAO(Connection conexao) throws SQLException {
        pstInsert = conexao.prepareStatement(insert);
        pstDeleteAll = conexao.prepareStatement(deleteAll);
        pstSelectAll = conexao.prepareStatement(selectAll);
        pstNinjasPorVila = conexao.prepareStatement(ninjasPorVila);
        pstNinjasPorRank = conexao.prepareStatement(ninjasPorRank);
        pstNinjasPorNatureza = conexao.prepareStatement(ninjasPorNatureza);
        pstMissoesPorRank = conexao.prepareStatement(missoesPorRank);
        pstMissoesPorStatus = conexao.prepareStatement(missoesPorStatus);
        pstNinjasVinculados = conexao.prepareStatement(ninjasVinculados);
        pstMissoesSemNinjas = conexao.prepareStatement(missoesSemNinjas);
    }

    public void insert(Totalizador t) throws SQLException {
        pstInsert.setString(1, t.getDescricao());
        pstInsert.setInt(2, t.getQuantidade());
        pstInsert.execute();
    }

    public void deleteAll() throws SQLException {
        pstDeleteAll.execute();
    }

    public ArrayList<Totalizador> selectAll() throws SQLException {
        ArrayList<Totalizador> lista = new ArrayList<>();
        ResultSet rs = pstSelectAll.executeQuery();
        while (rs.next()) {
            Totalizador t = new Totalizador();
            t.setId(rs.getLong("id"));
            t.setDescricao(rs.getString("descricao"));
            t.setQuantidade(rs.getInt("quantidade"));
            t.setDataGeracao(rs.getString("data_geracao"));
            lista.add(t);
        }
        return lista;
    }

    public ArrayList<Totalizador> contarNinjasPorVila() throws SQLException {
        ArrayList<Totalizador> lista = new ArrayList<>();
        ResultSet rs = pstNinjasPorVila.executeQuery();
        while (rs.next()) {
            Totalizador t = new Totalizador();
            t.setDescricao("Ninjas na Vila: " + rs.getString("vila"));
            t.setQuantidade(rs.getInt("qtd"));
            lista.add(t);
        }
        return lista;
    }

    public ArrayList<Totalizador> contarNinjasPorRank() throws SQLException {
        ArrayList<Totalizador> lista = new ArrayList<>();
        ResultSet rs = pstNinjasPorRank.executeQuery();
        while (rs.next()) {
            Totalizador t = new Totalizador();
            t.setDescricao("Ninjas Rank " + rs.getString("rank_ninja"));
            t.setQuantidade(rs.getInt("qtd"));
            lista.add(t);
        }
        return lista;
    }

    public ArrayList<Totalizador> contarNinjasPorNatureza() throws SQLException {
        ArrayList<Totalizador> lista = new ArrayList<>();
        ResultSet rs = pstNinjasPorNatureza.executeQuery();
        while (rs.next()) {
            Totalizador t = new Totalizador();
            t.setDescricao("Ninjas Natureza: " + rs.getString("natureza_chakra"));
            t.setQuantidade(rs.getInt("qtd"));
            lista.add(t);
        }
        return lista;
    }

    public ArrayList<Totalizador> contarMissoesPorRank() throws SQLException {
        ArrayList<Totalizador> lista = new ArrayList<>();
        ResultSet rs = pstMissoesPorRank.executeQuery();
        while (rs.next()) {
            Totalizador t = new Totalizador();
            t.setDescricao("Missoes Rank " + rs.getString("rank_missao"));
            t.setQuantidade(rs.getInt("qtd"));
            lista.add(t);
        }
        return lista;
    }

    public ArrayList<Totalizador> contarMissoesPorStatus() throws SQLException {
        ArrayList<Totalizador> lista = new ArrayList<>();
        ResultSet rs = pstMissoesPorStatus.executeQuery();
        while (rs.next()) {
            Totalizador t = new Totalizador();
            t.setDescricao("Missoes Status " + rs.getString("status"));
            t.setQuantidade(rs.getInt("qtd"));
            lista.add(t);
        }
        return lista;
    }

    public Totalizador contarNinjasVinculados() throws SQLException {
        ResultSet rs = pstNinjasVinculados.executeQuery();
        if (rs.next()) {
            Totalizador t = new Totalizador();
            t.setDescricao("Total de Ninjas Vinculados a Missoes");
            t.setQuantidade(rs.getInt("qtd"));
            return t;
        }
        return null;
    }

    public Totalizador contarMissoesSemNinjas() throws SQLException {
        ResultSet rs = pstMissoesSemNinjas.executeQuery();
        if (rs.next()) {
            Totalizador t = new Totalizador();
            t.setDescricao("Missoes sem Nenhum Ninja Vinculado");
            t.setQuantidade(rs.getInt("qtd"));
            return t;
        }
        return null;
    }

}
