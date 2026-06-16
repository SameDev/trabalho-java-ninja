package service;

import database.dao.ViewsDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewsService {

    private ViewsDAO dao;

    public ViewsService(Connection conexao) throws SQLException {
        this.dao = new ViewsDAO(conexao);
    }

    public ArrayList<String[]> consultarNinjaMissoes() throws SQLException {
        return dao.consultarNinjaMissoes();
    }

    public ArrayList<String[]> consultarNinjasPorVila() throws SQLException {
        return dao.consultarNinjasPorVila();
    }

    public ArrayList<String[]> consultarMissoesPorRank() throws SQLException {
        return dao.consultarMissoesPorRank();
    }

    public ArrayList<String[]> consultarMissoesSemNinjas() throws SQLException {
        return dao.consultarMissoesSemNinjas();
    }

}
