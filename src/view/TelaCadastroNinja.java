package view;

import connection.DatabaseSetup;
import database.model.Ninja;
import service.NinjaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TelaCadastroNinja extends JFrame {

    private JLabel lblNome, lblVila, lblCla, lblRank, lblNatureza, lblStatus;
    private JTextField txfNome, txfVila, txfCla, txfNatureza;
    private JComboBox<String> cmbRank, cmbStatus;
    private JButton btnSalvar;
    private JTable tblNinjas;
    private DefaultTableModel modelNinjas;
    private JScrollPane scrollNinjas;

    public TelaCadastroNinja() {
        setTitle("Cadastro de Ninjas");
        setSize(720, 580);
        setLayout(null);
        setLocationRelativeTo(null);
        NinjaTheme.estilizarFrame(this);
        componentesCriar();
        carregarTabela();
        setVisible(true);
    }

    private Connection getConexao() throws SQLException {
        return DatabaseSetup.getConexaoApp();
    }

    private void carregarTabela() {
        modelNinjas.setRowCount(0);
        try {
            NinjaService service = new NinjaService(getConexao());
            ArrayList<Ninja> lista = service.listarNinjas();
            for (Ninja n : lista) {
                modelNinjas.addRow(new Object[]{
                        n.getId(), n.getNome(), n.getVila(), n.getCla(),
                        n.getRankNinja(), n.getNaturezaChakra(), n.getStatus()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar ninjas: " + ex.getMessage());
        }
    }

    private void componentesCriar() {
        JPanel painelForm = new JPanel();
        painelForm.setLayout(null);
        painelForm.setBackground(NinjaTheme.BG_PANEL);
        painelForm.setBorder(NinjaTheme.bordaTitulo("Dados do Ninja"));
        painelForm.setBounds(10, 10, 690, 280);
        getContentPane().add(painelForm);

        int lx = 15, fx = 110, fw = 250, fh = 26, y = 25, gap = 35;

        lblNome = label("Nome:"); lblNome.setBounds(lx, y, 90, fh); painelForm.add(lblNome);
        txfNome = campo(); txfNome.setBounds(fx, y, fw, fh); painelForm.add(txfNome); y += gap;

        lblVila = label("Vila:"); lblVila.setBounds(lx, y, 90, fh); painelForm.add(lblVila);
        txfVila = campo(); txfVila.setBounds(fx, y, fw, fh); painelForm.add(txfVila); y += gap;

        lblCla = label("Cla:"); lblCla.setBounds(lx, y, 90, fh); painelForm.add(lblCla);
        txfCla = campo(); txfCla.setBounds(fx, y, fw, fh); painelForm.add(txfCla); y += gap;

        lblRank = label("Rank:"); lblRank.setBounds(lx, y, 90, fh); painelForm.add(lblRank);
        cmbRank = combo(new String[]{"Genin", "Chunin", "Jounin", "Kage"});
        cmbRank.setBounds(fx, y, fw, fh); painelForm.add(cmbRank); y += gap;

        lblNatureza = label("Natureza:"); lblNatureza.setBounds(lx, y, 90, fh); painelForm.add(lblNatureza);
        txfNatureza = campo(); txfNatureza.setBounds(fx, y, fw, fh); painelForm.add(txfNatureza); y += gap;

        lblStatus = label("Status:"); lblStatus.setBounds(lx, y, 90, fh); painelForm.add(lblStatus);
        cmbStatus = combo(new String[]{"Ativo", "Inativo"});
        cmbStatus.setBounds(fx, y, fw, fh); painelForm.add(cmbStatus);

        btnSalvar = botao("SALVAR NINJA");
        btnSalvar.setBounds(lx, 238, 160, 30);
        painelForm.add(btnSalvar);
        btnSalvar.addActionListener(e -> salvar());

        modelNinjas = new DefaultTableModel(
                new String[]{"ID", "Nome", "Vila", "Cla", "Rank", "Natureza", "Status"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblNinjas = new JTable(modelNinjas);
        NinjaTheme.estilizarTabela(tblNinjas);
        scrollNinjas = NinjaTheme.scrollEstilizado(tblNinjas);
        scrollNinjas.setBounds(10, 300, 690, 240);
        getContentPane().add(scrollNinjas);
    }

    private void salvar() {
        String nome = txfNome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o nome do ninja!");
            return;
        }
        Ninja ninja = new Ninja();
        ninja.setNome(nome);
        ninja.setVila(txfVila.getText().trim());
        ninja.setCla(txfCla.getText().trim());
        ninja.setRankNinja((String) cmbRank.getSelectedItem());
        ninja.setNaturezaChakra(txfNatureza.getText().trim());
        ninja.setStatus((String) cmbStatus.getSelectedItem());
        try {
            NinjaService service = new NinjaService(getConexao());
            if (service.cadastrarNinja(ninja)) {
                JOptionPane.showMessageDialog(null, "Ninja cadastrado com sucesso!");
                txfNome.setText(""); txfVila.setText(""); txfCla.setText(""); txfNatureza.setText("");
                cmbRank.setSelectedIndex(0); cmbStatus.setSelectedIndex(0);
                carregarTabela();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
        }
    }

    private JLabel label(String t) {
        JLabel l = new JLabel(t);
        l.setForeground(NinjaTheme.TEXT_ORANGE);
        l.setFont(new Font("Arial", Font.BOLD, 12));
        return l;
    }

    private JTextField campo() {
        JTextField f = new JTextField();
        f.setBackground(NinjaTheme.BG_INPUT);
        f.setForeground(Color.WHITE);
        f.setCaretColor(NinjaTheme.ORANGE_HOT);
        f.setBorder(BorderFactory.createLineBorder(NinjaTheme.BORDER_CLR));
        return f;
    }

    private JComboBox<String> combo(String[] itens) {
        JComboBox<String> c = new JComboBox<>(itens);
        c.setBackground(NinjaTheme.BG_INPUT);
        c.setForeground(Color.WHITE);
        return c;
    }

    private JButton botao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(NinjaTheme.ORANGE);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
