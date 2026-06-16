package service;

import database.dao.MissaoDAO;
import database.model.Missao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MissaoService {

    private MissaoDAO dao;

    public MissaoService(Connection conexao) throws SQLException {
        this.dao = new MissaoDAO(conexao);
    }

    public boolean cadastrarMissao(Missao missao) throws SQLException {
        return dao.insert(missao);
    }

    public boolean alterarMissao(Missao missao) throws SQLException {
        return dao.update(missao);
    }

    public boolean excluirMissao(Long id) throws SQLException {
        return dao.delete(id);
    }

    public ArrayList<Missao> listarMissoes() throws SQLException {
        return dao.selectAll();
    }

}
