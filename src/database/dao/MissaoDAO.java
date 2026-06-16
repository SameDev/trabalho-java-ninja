package database.dao;

import database.model.Missao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MissaoDAO {

    private String insert =
            "INSERT INTO tb_missao(titulo, descricao, rank_missao, vila_origem, status) VALUES(?, ?, ?, ?, ?)";

    private String update =
            "UPDATE tb_missao SET titulo = ?, descricao = ?, rank_missao = ?, vila_origem = ?, status = ? WHERE id = ?";

    private String delete =
            "DELETE FROM tb_missao WHERE id = ?";

    private String selectAll =
            "SELECT * FROM tb_missao ORDER BY id";

    private PreparedStatement pstInsert;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstDelete;
    private PreparedStatement pstSelectAll;

    public MissaoDAO(Connection conexao) throws SQLException {
        pstInsert = conexao.prepareStatement(insert);
        pstUpdate = conexao.prepareStatement(update);
        pstDelete = conexao.prepareStatement(delete);
        pstSelectAll = conexao.prepareStatement(selectAll);
    }

    public boolean insert(Missao missao) throws SQLException {
        pstInsert.setString(1, missao.getTitulo());
        pstInsert.setString(2, missao.getDescricao());
        pstInsert.setString(3, missao.getRankMissao());
        pstInsert.setString(4, missao.getVilaOrigem());
        pstInsert.setString(5, missao.getStatus());
        pstInsert.execute();
        return pstInsert.getUpdateCount() > 0;
    }

    public boolean update(Missao missao) throws SQLException {
        pstUpdate.setString(1, missao.getTitulo());
        pstUpdate.setString(2, missao.getDescricao());
        pstUpdate.setString(3, missao.getRankMissao());
        pstUpdate.setString(4, missao.getVilaOrigem());
        pstUpdate.setString(5, missao.getStatus());
        pstUpdate.setLong(6, missao.getId());
        pstUpdate.execute();
        return pstUpdate.getUpdateCount() > 0;
    }

    public boolean delete(Long id) throws SQLException {
        pstDelete.setLong(1, id);
        pstDelete.execute();
        return pstDelete.getUpdateCount() > 0;
    }

    public ArrayList<Missao> selectAll() throws SQLException {
        ArrayList<Missao> lista = new ArrayList<>();
        ResultSet rs = pstSelectAll.executeQuery();
        while (rs.next()) {
            Missao m = new Missao();
            m.setId(rs.getLong("id"));
            m.setTitulo(rs.getString("titulo"));
            m.setDescricao(rs.getString("descricao"));
            m.setRankMissao(rs.getString("rank_missao"));
            m.setVilaOrigem(rs.getString("vila_origem"));
            m.setStatus(rs.getString("status"));
            lista.add(m);
        }
        return lista;
    }

}
