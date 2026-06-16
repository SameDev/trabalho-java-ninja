package service;

import database.dao.TotalizadorDAO;
import database.model.Totalizador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TotalizadorService {

    private TotalizadorDAO dao;

    public TotalizadorService(Connection conexao) throws SQLException {
        this.dao = new TotalizadorDAO(conexao);
    }

    public ArrayList<Totalizador> gerarTotalizadores() throws SQLException {
        dao.deleteAll();

        ArrayList<Totalizador> todos = new ArrayList<>();
        todos.addAll(dao.contarNinjasPorVila());
        todos.addAll(dao.contarNinjasPorRank());
        todos.addAll(dao.contarNinjasPorNatureza());
        todos.addAll(dao.contarMissoesPorRank());
        todos.addAll(dao.contarMissoesPorStatus());

        Totalizador vinculados = dao.contarNinjasVinculados();
        if (vinculados != null) todos.add(vinculados);

        Totalizador semNinjas = dao.contarMissoesSemNinjas();
        if (semNinjas != null) todos.add(semNinjas);

        for (Totalizador t : todos) {
            dao.insert(t);
        }

        return dao.selectAll();
    }

    public ArrayList<Totalizador> listarTotalizadores() throws SQLException {
        return dao.selectAll();
    }

}
