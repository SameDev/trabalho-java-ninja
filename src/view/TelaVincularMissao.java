package view;

import connection.DatabaseSetup;
import database.model.Missao;
import database.model.Ninja;
import database.model.NinjaMissao;
import service.MissaoService;
import service.NinjaService;
import service.NinjaMissaoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaVincularMissao extends JFrame {

    private JComboBox<Ninja> cmbNinja;
    private JComboBox<Missao> cmbMissao;
    private JComboBox<String> cmbFuncao;
    private JSpinner spnData;
    private JButton btnVincular;
    private JTable tblVinculos;
    private DefaultTableModel modelVinculos;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public TelaVincularMissao() {
        setTitle("Vincular Ninja a Missao");
        setSize(760, 520);
        setLayout(null);
        setLocationRelativeTo(null);
        NinjaTheme.estilizarFrame(this);
        componentesCriar();
        carregarCombos();
        carregarTabela();
        setVisible(true);
    }

    private Connection getConexao() throws SQLException { return DatabaseSetup.getConexaoApp(); }

    private void carregarCombos() {
        cmbNinja.removeAllItems(); cmbMissao.removeAllItems();
        try {
            for (Ninja n : new NinjaService(getConexao()).listarNinjas()) cmbNinja.addItem(n);
            for (Missao m : new MissaoService(getConexao()).listarMissoes()) cmbMissao.addItem(m);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void carregarTabela() {
        modelVinculos.setRowCount(0);
        try {
            for (NinjaMissao nm : new NinjaMissaoService(getConexao()).listarVinculos()) {
                modelVinculos.addRow(new Object[]{
                        nm.getId(), nm.getNomeNinja(), nm.getTituloMissao(),
                        nm.getFuncao(), nm.getDataParticipacao()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void componentesCriar() {
        JPanel painelForm = new JPanel();
        painelForm.setLayout(null);
        painelForm.setBackground(NinjaTheme.BG_PANEL);
        painelForm.setBorder(NinjaTheme.bordaTitulo("Vincular Ninja a Missao"));
        painelForm.setBounds(10, 10, 730, 220);
        getContentPane().add(painelForm);

        int lx = 15, fx = 160, fw = 280, fh = 26, y = 25, gap = 38;

        label("Ninja:", painelForm, lx, y, 140, fh);
        cmbNinja = new JComboBox<>(); estilizarCombo(cmbNinja);
        cmbNinja.setBounds(fx, y, fw, fh); painelForm.add(cmbNinja); y += gap;

        label("Missao:", painelForm, lx, y, 140, fh);
        cmbMissao = new JComboBox<>(); estilizarCombo(cmbMissao);
        cmbMissao.setBounds(fx, y, fw, fh); painelForm.add(cmbMissao); y += gap;

        label("Funcao:", painelForm, lx, y, 140, fh);
        cmbFuncao = new JComboBox<>(new String[]{"Lider", "Ataque", "Suporte", "Sensorial", "Medico", "Defesa"});
        cmbFuncao.setBackground(NinjaTheme.BG_INPUT); cmbFuncao.setForeground(Color.WHITE);
        cmbFuncao.setBounds(fx, y, fw, fh); painelForm.add(cmbFuncao); y += gap;

        label("Data:", painelForm, lx, y, 140, fh);
        spnData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnData, "dd/MM/yyyy");
        spnData.setEditor(dateEditor);
        spnData.setValue(new Date());
        estilizarSpinner(spnData, dateEditor);
        spnData.setBounds(fx, y, 160, fh); painelForm.add(spnData);

        btnVincular = botao("VINCULAR");
        btnVincular.setBounds(lx, 180, 180, 30);
        painelForm.add(btnVincular);
        btnVincular.addActionListener(e -> vincular());

        modelVinculos = new DefaultTableModel(
                new String[]{"ID", "Ninja", "Missao", "Funcao", "Data"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblVinculos = new JTable(modelVinculos);
        NinjaTheme.estilizarTabela(tblVinculos);
        JScrollPane scroll = NinjaTheme.scrollEstilizado(tblVinculos);
        scroll.setBounds(10, 240, 730, 235);
        getContentPane().add(scroll);
    }

    private void vincular() {
        Ninja ninja = (Ninja) cmbNinja.getSelectedItem();
        Missao missao = (Missao) cmbMissao.getSelectedItem();
        if (ninja == null || missao == null) {
            JOptionPane.showMessageDialog(null, "Selecione ninja e missao!"); return;
        }
        String dataStr = SDF.format((Date) spnData.getValue());
        NinjaMissao nm = new NinjaMissao();
        nm.setIdNinja(ninja.getId()); nm.setIdMissao(missao.getId());
        nm.setFuncao((String) cmbFuncao.getSelectedItem());
        nm.setDataParticipacao(dataStr);
        try {
            NinjaMissaoService service = new NinjaMissaoService(getConexao());
            if (!service.rankPermitido(ninja.getRankNinja(), missao.getRankMissao())) {
                JOptionPane.showMessageDialog(null,
                        "Ninja " + ninja.getNome() + " (Rank: " + ninja.getRankNinja() + ")" +
                        " nao pode participar de missao Rank " + missao.getRankMissao() + "!",
                        "Regra de Negocio Violada", JOptionPane.WARNING_MESSAGE);
                return;
            }
            service.vincularNinjaMissao(nm, ninja.getRankNinja(), missao.getRankMissao());
            JOptionPane.showMessageDialog(null, "Vinculo realizado com sucesso!");
            spnData.setValue(new Date());
            carregarTabela();
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
        }
    }

    private void estilizarSpinner(JSpinner spinner, JSpinner.DateEditor editor) {
        spinner.setBackground(NinjaTheme.BG_INPUT);
        spinner.setForeground(Color.WHITE);
        spinner.setBorder(BorderFactory.createLineBorder(NinjaTheme.BORDER_CLR));
        editor.getTextField().setBackground(NinjaTheme.BG_INPUT);
        editor.getTextField().setForeground(Color.WHITE);
        editor.getTextField().setCaretColor(NinjaTheme.ORANGE_HOT);
        editor.getTextField().setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
        // style the arrow buttons
        for (Component c : spinner.getComponents()) {
            if (c instanceof JPanel) {
                ((JPanel) c).setBackground(NinjaTheme.BG_INPUT);
                for (Component btn : ((JPanel) c).getComponents()) {
                    btn.setBackground(NinjaTheme.ORANGE);
                    btn.setForeground(Color.WHITE);
                }
            }
        }
    }

    private void label(String t, JPanel p, int x, int y, int w, int h) {
        JLabel l = new JLabel(t);
        l.setForeground(NinjaTheme.TEXT_ORANGE);
        l.setFont(new Font("Arial", Font.BOLD, 12));
        l.setBounds(x, y, w, h);
        p.add(l);
    }

    private <T> void estilizarCombo(JComboBox<T> c) {
        c.setBackground(NinjaTheme.BG_INPUT);
        c.setForeground(Color.WHITE);
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
