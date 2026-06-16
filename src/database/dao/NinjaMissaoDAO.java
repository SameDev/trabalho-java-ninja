package database.dao;

import database.model.NinjaMissao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NinjaMissaoDAO {

    private String insert =
            "INSERT INTO tb_ninja_missao(id_ninja, id_missao, funcao, data_participacao) VALUES(?, ?, ?, ?)";

    private String selectAll =
            "SELECT nm.id, nm.id_ninja, nm.id_missao, n.nome AS nome_ninja, " +
            "m.titulo AS titulo_missao, nm.funcao, nm.data_participacao " +
            "FROM tb_ninja_missao nm " +
            "JOIN tb_ninja n ON n.id = nm.id_ninja " +
            "JOIN tb_missao m ON m.id = nm.id_missao " +
            "ORDER BY nm.id";

    private String checkDuplicate =
            "SELECT COUNT(*) FROM tb_ninja_missao WHERE id_ninja = ? AND id_missao = ?";

    private PreparedStatement pstInsert;
    private PreparedStatement pstSelectAll;
    private PreparedStatement pstCheckDuplicate;

    public NinjaMissaoDAO(Connection conexao) throws SQLException {
        pstInsert = conexao.prepareStatement(insert);
        pstSelectAll = conexao.prepareStatement(selectAll);
        pstCheckDuplicate = conexao.prepareStatement(checkDuplicate);
    }

    public boolean insert(NinjaMissao nm) throws SQLException {
        pstInsert.setLong(1, nm.getIdNinja());
        pstInsert.setLong(2, nm.getIdMissao());
        pstInsert.setString(3, nm.getFuncao());
        if (nm.getDataParticipacao() != null && !nm.getDataParticipacao().isEmpty()) {
            pstInsert.setDate(4, Date.valueOf(nm.getDataParticipacao()));
        } else {
            pstInsert.setDate(4, new Date(System.currentTimeMillis()));
        }
        pstInsert.execute();
        return pstInsert.getUpdateCount() > 0;
    }

    public boolean existeVinculo(Long idNinja, Long idMissao) throws SQLException {
        pstCheckDuplicate.setLong(1, idNinja);
        pstCheckDuplicate.setLong(2, idMissao);
        ResultSet rs = pstCheckDuplicate.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

    public ArrayList<NinjaMissao> selectAll() throws SQLException {
        ArrayList<NinjaMissao> lista = new ArrayList<>();
        ResultSet rs = pstSelectAll.executeQuery();
        while (rs.next()) {
            NinjaMissao nm = new NinjaMissao();
            nm.setId(rs.getLong("id"));
            nm.setIdNinja(rs.getLong("id_ninja"));
            nm.setIdMissao(rs.getLong("id_missao"));
            nm.setNomeNinja(rs.getString("nome_ninja"));
            nm.setTituloMissao(rs.getString("titulo_missao"));
            nm.setFuncao(rs.getString("funcao"));
            Date d = rs.getDate("data_participacao");
            nm.setDataParticipacao(d != null ? d.toString() : "");
            lista.add(nm);
        }
        return lista;
    }

}
