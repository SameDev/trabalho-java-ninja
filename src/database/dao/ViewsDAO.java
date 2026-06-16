package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewsDAO {

    private PreparedStatement pstNinjaMissoes;
    private PreparedStatement pstNinjasPorVila;
    private PreparedStatement pstMissoesPorRank;
    private PreparedStatement pstMissoesSemNinjas;

    public ViewsDAO(Connection conexao) throws SQLException {
        pstNinjaMissoes     = conexao.prepareStatement("SELECT * FROM vw_ninja_missoes ORDER BY nome_ninja");
        pstNinjasPorVila    = conexao.prepareStatement("SELECT * FROM vw_total_ninjas_por_vila ORDER BY vila");
        pstMissoesPorRank   = conexao.prepareStatement("SELECT * FROM vw_total_missoes_por_rank ORDER BY rank_missao");
        pstMissoesSemNinjas = conexao.prepareStatement("SELECT * FROM vw_missoes_sem_ninjas ORDER BY id_missao");
    }

    public ArrayList<String[]> consultarNinjaMissoes() throws SQLException {
        ArrayList<String[]> lista = new ArrayList<>();
        ResultSet rs = pstNinjaMissoes.executeQuery();
        while (rs.next()) {
            lista.add(new String[]{
                rs.getString("nome_ninja"), rs.getString("vila"),
                rs.getString("cla"),        rs.getString("rank_ninja"),
                rs.getString("titulo_missao"), rs.getString("rank_missao"),
                rs.getString("funcao"),     rs.getString("status_missao")
            });
        }
        return lista;
    }

    public ArrayList<String[]> consultarNinjasPorVila() throws SQLException {
        ArrayList<String[]> lista = new ArrayList<>();
        ResultSet rs = pstNinjasPorVila.executeQuery();
        while (rs.next()) {
            lista.add(new String[]{rs.getString("vila"), rs.getString("quantidade_ninjas")});
        }
        return lista;
    }

    public ArrayList<String[]> consultarMissoesPorRank() throws SQLException {
        ArrayList<String[]> lista = new ArrayList<>();
        ResultSet rs = pstMissoesPorRank.executeQuery();
        while (rs.next()) {
            lista.add(new String[]{rs.getString("rank_missao"), rs.getString("quantidade_missoes")});
        }
        return lista;
    }

    public ArrayList<String[]> consultarMissoesSemNinjas() throws SQLException {
        ArrayList<String[]> lista = new ArrayList<>();
        ResultSet rs = pstMissoesSemNinjas.executeQuery();
        while (rs.next()) {
            lista.add(new String[]{
                rs.getString("id_missao"), rs.getString("titulo"),
                rs.getString("rank_missao"), rs.getString("vila_origem"),
                rs.getString("status")
            });
        }
        return lista;
    }

}
