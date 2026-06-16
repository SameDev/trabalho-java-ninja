package view;

import connection.DatabaseSetup;
import service.ViewsService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class TelaViews extends JFrame {

    private JTabbedPane abas;
    private JButton btnCarregarNinjaMissoes, btnCarregarNinjasPorVila,
                    btnCarregarMissoesPorRank, btnCarregarSemNinjas;
    private JTable tblNinjaMissoes, tblNinjasPorVila, tblMissoesPorRank, tblSemNinjas;
    private DefaultTableModel modelNinjaMissoes, modelNinjasPorVila,
                               modelMissoesPorRank, modelSemNinjas;

    public TelaViews() {
        setTitle("Consultar Views");
        setSize(920, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        NinjaTheme.estilizarFrame(this);
        componentesCriar();
        setVisible(true);
    }

    private Connection getConexao() throws SQLException { return DatabaseSetup.getConexaoApp(); }

    private void componentesCriar() {
        abas = new JTabbedPane();
        abas.setBackground(NinjaTheme.BG_PANEL);
        abas.setForeground(NinjaTheme.TEXT_ORANGE);
        abas.setFont(new Font("Arial", Font.BOLD, 12));
        abas.setBounds(0, 0, 905, 565);

        abas.addTab("Ninja / Missoes",    criarPainel(
                new String[]{"Ninja","Vila","Cla","Rank Ninja","Missao","Rank Missao","Funcao","Status"},
                tbl -> { modelNinjaMissoes = tbl; },
                btn -> { btnCarregarNinjaMissoes = btn; },
                () -> new ViewsService(getConexao()).consultarNinjaMissoes()
        ));
        abas.addTab("Ninjas por Vila",     criarPainel(
                new String[]{"Vila","Qtd Ninjas"},
                tbl -> { modelNinjasPorVila = tbl; },
                btn -> { btnCarregarNinjasPorVila = btn; },
                () -> new ViewsService(getConexao()).consultarNinjasPorVila()
        ));
        abas.addTab("Missoes por Rank",    criarPainel(
                new String[]{"Rank Missao","Qtd Missoes"},
                tbl -> { modelMissoesPorRank = tbl; },
                btn -> { btnCarregarMissoesPorRank = btn; },
                () -> new ViewsService(getConexao()).consultarMissoesPorRank()
        ));
        abas.addTab("Missoes sem Ninjas",  criarPainel(
                new String[]{"ID","Titulo","Rank","Vila Origem","Status"},
                tbl -> { modelSemNinjas = tbl; },
                btn -> { btnCarregarSemNinjas = btn; },
                () -> new ViewsService(getConexao()).consultarMissoesSemNinjas()
        ));

        getContentPane().add(abas);
    }

    @FunctionalInterface
    interface Consulta { java.util.ArrayList<String[]> executar() throws SQLException; }

    private JPanel criarPainel(String[] colunas,
                                java.util.function.Consumer<DefaultTableModel> modelSetter,
                                java.util.function.Consumer<JButton> btnSetter,
                                Consulta consulta) {
        JPanel painel = new JPanel();
        painel.setLayout(null);
        painel.setBackground(NinjaTheme.BG_PANEL);

        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        modelSetter.accept(model);

        JTable tabela = new JTable(model);
        NinjaTheme.estilizarTabela(tabela);
        JScrollPane scroll = NinjaTheme.scrollEstilizado(tabela);
        scroll.setBounds(5, 50, 880, 460);
        painel.add(scroll);

        JButton btn = new JButton("CARREGAR");
        btn.setBackground(NinjaTheme.ORANGE);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBounds(5, 10, 160, 32);
        painel.add(btn);
        btnSetter.accept(btn);

        btn.addActionListener(e -> {
            model.setRowCount(0);
            try {
                for (String[] row : consulta.executar()) model.addRow(row);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            }
        });

        return painel;
    }
}
