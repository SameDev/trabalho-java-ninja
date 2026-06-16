package service;

import database.dao.NinjaMissaoDAO;
import database.model.NinjaMissao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class NinjaMissaoService {

    private NinjaMissaoDAO dao;

    public NinjaMissaoService(Connection conexao) throws SQLException {
        this.dao = new NinjaMissaoDAO(conexao);
    }

    public boolean rankPermitido(String rankNinja, String rankMissao) {
        switch (rankNinja) {
            case "Genin":
                return rankMissao.equals("D") || rankMissao.equals("C");
            case "Chunin":
                return rankMissao.equals("D") || rankMissao.equals("C") || rankMissao.equals("B");
            case "Jounin":
                return true;
            case "Kage":
                return rankMissao.equals("A") || rankMissao.equals("S");
            default:
                return false;
        }
    }

    public boolean vincularNinjaMissao(NinjaMissao nm, String rankNinja, String rankMissao) throws SQLException {
        if (!rankPermitido(rankNinja, rankMissao)) {
            return false;
        }
        if (dao.existeVinculo(nm.getIdNinja(), nm.getIdMissao())) {
            throw new IllegalStateException("Ninja ja esta vinculado a esta missao!");
        }
        return dao.insert(nm);
    }

    public ArrayList<NinjaMissao> listarVinculos() throws SQLException {
        return dao.selectAll();
    }

}
