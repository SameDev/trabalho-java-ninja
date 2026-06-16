package view;

import connection.DatabaseSetup;
import database.model.Missao;
import service.MissaoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TelaCadastroMissao extends JFrame {

    private JTextField txfTitulo, txfVila;
    private JTextArea txaDescricao;
    private JComboBox<String> cmbRank, cmbStatus;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnLimpar;
    private JTable tblMissoes;
    private DefaultTableModel modelMissoes;
    private ArrayList<Missao> listaMissoes = new ArrayList<>();
    private Long idSelecionado = null;

    public TelaCadastroMissao() {
        setTitle("Cadastro de Missoes");
        setSize(730, 670);
        setLayout(null);
        setLocationRelativeTo(null);
        NinjaTheme.estilizarFrame(this);
        componentesCriar();
        carregarTabela();
        setVisible(true);
    }

    private Connection getConexao() throws SQLException { return DatabaseSetup.getConexaoApp(); }

    private void carregarTabela() {
        modelMissoes.setRowCount(0);
        listaMissoes.clear();
        try {
            MissaoService service = new MissaoService(getConexao());
            listaMissoes = service.listarMissoes();
            for (Missao m : listaMissoes) {
                modelMissoes.addRow(new Object[]{m.getId(), m.getTitulo(), m.getRankMissao(), m.getVilaOrigem(), m.getStatus()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txfTitulo.setText(""); txaDescricao.setText(""); txfVila.setText("");
        cmbRank.setSelectedIndex(0); cmbStatus.setSelectedIndex(0);
        idSelecionado = null;
    }

    private void componentesCriar() {
        JPanel painelForm = new JPanel();
        painelForm.setLayout(null);
        painelForm.setBackground(NinjaTheme.BG_PANEL);
        painelForm.setBorder(NinjaTheme.bordaTitulo("Dados da Missao"));
        painelForm.setBounds(10, 10, 700, 265);
        getContentPane().add(painelForm);

        int lx = 15, fx = 120, fw = 300, fh = 26, y = 25, gap = 35;

        JLabel lblTitulo = label("Titulo:"); lblTitulo.setBounds(lx, y, 100, fh); painelForm.add(lblTitulo);
        txfTitulo = campo(); txfTitulo.setBounds(fx, y, fw, fh); painelForm.add(txfTitulo); y += gap;

        JLabel lblDesc = label("Descricao:"); lblDesc.setBounds(lx, y, 100, fh); painelForm.add(lblDesc);
        txaDescricao = new JTextArea();
        txaDescricao.setBackground(NinjaTheme.BG_INPUT);
        txaDescricao.setForeground(Color.WHITE);
        txaDescricao.setCaretColor(NinjaTheme.ORANGE_HOT);
        txaDescricao.setLineWrap(true);
        JScrollPane scrollDesc = new JScrollPane(txaDescricao);
        scrollDesc.setBorder(BorderFactory.createLineBorder(NinjaTheme.BORDER_CLR));
        scrollDesc.getViewport().setBackground(NinjaTheme.BG_INPUT);
        scrollDesc.setBounds(fx, y, fw, 60); painelForm.add(scrollDesc); y += 70;

        JLabel lblRank = label("Rank:"); lblRank.setBounds(lx, y, 100, fh); painelForm.add(lblRank);
        cmbRank = combo(new String[]{"D", "C", "B", "A", "S"});
        cmbRank.setBounds(fx, y, 100, fh); painelForm.add(cmbRank); y += gap;

        JLabel lblVila = label("Vila Origem:"); lblVila.setBounds(lx, y, 100, fh); painelForm.add(lblVila);
        txfVila = campo(); txfVila.setBounds(fx, y, fw, fh); painelForm.add(txfVila); y += gap;

        JLabel lblStatus = label("Status:"); lblStatus.setBounds(lx, y, 100, fh); painelForm.add(lblStatus);
        cmbStatus = combo(new String[]{"Aberta", "Em Andamento", "Concluida", "Cancelada"});
        cmbStatus.setBounds(fx, y, 200, fh); painelForm.add(cmbStatus);

        int by = 230, bw = 130, bh = 28, bx = lx;
        btnSalvar  = botao("SALVAR");   btnSalvar.setBounds(bx,       by, bw, bh); painelForm.add(btnSalvar);  bx += bw + 8;
        btnAlterar = botao("ALTERAR");  btnAlterar.setBounds(bx,      by, bw, bh); painelForm.add(btnAlterar); bx += bw + 8;
        btnExcluir = botao("EXCLUIR");  btnExcluir.setBackground(NinjaTheme.RED_DARK);
        btnExcluir.setBounds(bx, by, bw, bh); painelForm.add(btnExcluir); bx += bw + 8;
        btnLimpar  = botao("LIMPAR");   btnLimpar.setBackground(new Color(50, 50, 50));
        btnLimpar.setBounds(bx, by, bw, bh); painelForm.add(btnLimpar);

        btnSalvar.addActionListener(e -> salvar());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparCampos());

        modelMissoes = new DefaultTableModel(
                new String[]{"ID", "Titulo", "Rank", "Vila Origem", "Status"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblMissoes = new JTable(modelMissoes);
        NinjaTheme.estilizarTabela(tblMissoes);
        tblMissoes.getSelectionModel().addListSelectionListener(ev -> {
            int row = tblMissoes.getSelectedRow();
            if (row >= 0 && row < listaMissoes.size()) {
                Missao m = listaMissoes.get(row);
                idSelecionado = m.getId();
                txfTitulo.setText(m.getTitulo());
                txaDescricao.setText(m.getDescricao());
                txfVila.setText(m.getVilaOrigem());
                cmbRank.setSelectedItem(m.getRankMissao());
                cmbStatus.setSelectedItem(m.getStatus());
            }
        });
        JScrollPane scroll = NinjaTheme.scrollEstilizado(tblMissoes);
        scroll.setBounds(10, 285, 700, 340);
        getContentPane().add(scroll);
    }

    private void salvar() {
        String titulo = txfTitulo.getText().trim();
        if (titulo.isEmpty()) { JOptionPane.showMessageDialog(null, "Informe o titulo!"); return; }
        Missao m = new Missao();
        m.setTitulo(titulo); m.setDescricao(txaDescricao.getText().trim());
        m.setRankMissao((String) cmbRank.getSelectedItem());
        m.setVilaOrigem(txfVila.getText().trim());
        m.setStatus((String) cmbStatus.getSelectedItem());
        try {
            if (new MissaoService(getConexao()).cadastrarMissao(m)) {
                JOptionPane.showMessageDialog(null, "Missao cadastrada!");
                limparCampos(); carregarTabela();
            }
        } catch (SQLException ex) { JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage()); }
    }

    private void alterar() {
        if (idSelecionado == null) { JOptionPane.showMessageDialog(null, "Selecione uma missao!"); return; }
        Missao m = new Missao();
        m.setId(idSelecionado); m.setTitulo(txfTitulo.getText().trim());
        m.setDescricao(txaDescricao.getText().trim());
        m.setRankMissao((String) cmbRank.getSelectedItem());
        m.setVilaOrigem(txfVila.getText().trim());
        m.setStatus((String) cmbStatus.getSelectedItem());
        try {
            if (new MissaoService(getConexao()).alterarMissao(m)) {
                JOptionPane.showMessageDialog(null, "Missao alterada!");
                limparCampos(); carregarTabela();
            }
        } catch (SQLException ex) { JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage()); }
    }

    private void excluir() {
        if (idSelecionado == null) { JOptionPane.showMessageDialog(null, "Selecione uma missao!"); return; }
        int ok = JOptionPane.showConfirmDialog(null, "Confirma exclusao?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            try {
                if (new MissaoService(getConexao()).excluirMissao(idSelecionado)) {
                    JOptionPane.showMessageDialog(null, "Missao excluida!");
                    limparCampos(); carregarTabela();
                }
            } catch (SQLException ex) { JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage()); }
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
