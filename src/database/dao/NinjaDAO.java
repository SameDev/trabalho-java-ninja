package database.dao;

import database.model.Ninja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NinjaDAO {

    private String insert =
            "INSERT INTO tb_ninja(nome, vila, cla, rank_ninja, natureza_chakra, status) VALUES(?, ?, ?, ?, ?, ?)";

    private String selectAll =
            "SELECT * FROM tb_ninja ORDER BY id";

    private PreparedStatement pstInsert;
    private PreparedStatement pstSelectAll;

    public NinjaDAO(Connection conexao) throws SQLException {
        pstInsert = conexao.prepareStatement(insert);
        pstSelectAll = conexao.prepareStatement(selectAll);
    }

    public boolean insert(Ninja ninja) throws SQLException {
        pstInsert.setString(1, ninja.getNome());
        pstInsert.setString(2, ninja.getVila());
        pstInsert.setString(3, ninja.getCla());
        pstInsert.setString(4, ninja.getRankNinja());
        pstInsert.setString(5, ninja.getNaturezaChakra());
        pstInsert.setString(6, ninja.getStatus());
        pstInsert.execute();
        return pstInsert.getUpdateCount() > 0;
    }

    public ArrayList<Ninja> selectAll() throws SQLException {
        ArrayList<Ninja> lista = new ArrayList<>();
        ResultSet rs = pstSelectAll.executeQuery();
        while (rs.next()) {
            Ninja n = new Ninja();
            n.setId(rs.getLong("id"));
            n.setNome(rs.getString("nome"));
            n.setVila(rs.getString("vila"));
            n.setCla(rs.getString("cla"));
            n.setRankNinja(rs.getString("rank_ninja"));
            n.setNaturezaChakra(rs.getString("natureza_chakra"));
            n.setStatus(rs.getString("status"));
            lista.add(n);
        }
        return lista;
    }

}
