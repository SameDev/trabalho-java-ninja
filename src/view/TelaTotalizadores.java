package view;

import connection.DatabaseSetup;
import database.model.Totalizador;
import service.TotalizadorService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TelaTotalizadores extends JFrame {

    private JButton btnGerar;
    private JTable tblTotalizadores;
    private DefaultTableModel modelTotalizadores;

    public TelaTotalizadores() {
        setTitle("Totalizadores");
        setSize(660, 480);
        setLayout(null);
        setLocationRelativeTo(null);
        NinjaTheme.estilizarFrame(this);
        componentesCriar();
        carregarTabela();
        setVisible(true);
    }

    private Connection getConexao() throws SQLException { return DatabaseSetup.getConexaoApp(); }

    private void carregarTabela() {
        modelTotalizadores.setRowCount(0);
        try {
            for (Totalizador t : new TotalizadorService(getConexao()).listarTotalizadores()) {
                modelTotalizadores.addRow(new Object[]{t.getDescricao(), t.getQuantidade(), t.getDataGeracao()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void componentesCriar() {
        JLabel lblTitulo = new JLabel("TOTALIZADORES", SwingConstants.LEFT);
        lblTitulo.setForeground(NinjaTheme.ORANGE_HOT);
        lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 16));
        lblTitulo.setBounds(15, 12, 350, 30);
        getContentPane().add(lblTitulo);

        btnGerar = new JButton("GERAR TOTALIZADORES");
        btnGerar.setBackground(NinjaTheme.ORANGE);
        btnGerar.setForeground(Color.WHITE);
        btnGerar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGerar.setFocusPainted(false);
        btnGerar.setBorderPainted(false);
        btnGerar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGerar.setBounds(400, 10, 240, 35);
        getContentPane().add(btnGerar);

        btnGerar.addActionListener(e -> {
            try {
                ArrayList<Totalizador> lista = new TotalizadorService(getConexao()).gerarTotalizadores();
                modelTotalizadores.setRowCount(0);
                for (Totalizador t : lista) {
                    modelTotalizadores.addRow(new Object[]{t.getDescricao(), t.getQuantidade(), t.getDataGeracao()});
                }
                JOptionPane.showMessageDialog(null, "Totalizadores gerados com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            }
        });

        modelTotalizadores = new DefaultTableModel(
                new String[]{"Descricao", "Quantidade", "Data Geracao"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblTotalizadores = new JTable(modelTotalizadores);
        NinjaTheme.estilizarTabela(tblTotalizadores);
        JScrollPane scroll = NinjaTheme.scrollEstilizado(tblTotalizadores);
        scroll.setBounds(10, 55, 630, 380);
        getContentPane().add(scroll);
    }
}
