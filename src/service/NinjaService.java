package service;

import database.dao.NinjaDAO;
import database.model.Ninja;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class NinjaService {

    private NinjaDAO dao;

    public NinjaService(Connection conexao) throws SQLException {
        this.dao = new NinjaDAO(conexao);
    }

    public boolean cadastrarNinja(Ninja ninja) throws SQLException {
        return dao.insert(ninja);
    }

    public ArrayList<Ninja> listarNinjas() throws SQLException {
        return dao.selectAll();
    }

}
