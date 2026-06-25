package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaPrincipal extends JFrame {

    private JLabel lblTitulo;
    private JPanel painelBotoes;
    private JButton btnNinjas;
    private JButton btnMissoes;
    private JButton btnVincular;
    private JButton btnTotalizadores;
    private JButton btnViews;
    private JButton btnCreditos;

    public TelaPrincipal() {
        setTitle("NinJava");
        setSize(420, 490);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        NinjaTheme.estilizarFrame(this);
        componentesCriar();
        setVisible(true);
    }

    private void componentesCriar() {
        lblTitulo = new JLabel("NINJAVA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 20));
        lblTitulo.setForeground(NinjaTheme.ORANGE_HOT);
        lblTitulo.setBounds(20, 15, 380, 45);
        getContentPane().add(lblTitulo);

        painelBotoes = new JPanel();
        painelBotoes.setLayout(null);
        painelBotoes.setBackground(NinjaTheme.BG_PANEL);
        painelBotoes.setBorder(NinjaTheme.bordaTitulo("Menu Principal"));
        painelBotoes.setBounds(30, 75, 355, 385);
        getContentPane().add(painelBotoes);

        int y = 30;
        int h = 45;
        int gap = 12;

        btnNinjas = botao("Cadastro de Ninjas");
        btnNinjas.setBounds(25, y, 305, h); y += h + gap;
        painelBotoes.add(btnNinjas);
        btnNinjas.addActionListener(e -> new TelaCadastroNinja());

        btnMissoes = botao("Cadastro de Missoes");
        btnMissoes.setBounds(25, y, 305, h); y += h + gap;
        painelBotoes.add(btnMissoes);
        btnMissoes.addActionListener(e -> new TelaCadastroMissao());

        btnVincular = botao("Vincular Ninja / Missao");
        btnVincular.setBounds(25, y, 305, h); y += h + gap;
        painelBotoes.add(btnVincular);
        btnVincular.addActionListener(e -> new TelaVincularMissao());

        btnTotalizadores = botao("Totalizadores");
        btnTotalizadores.setBounds(25, y, 305, h); y += h + gap;
        painelBotoes.add(btnTotalizadores);
        btnTotalizadores.addActionListener(e -> new TelaTotalizadores());

        btnViews = botao("Consultar Views");
        btnViews.setBounds(25, y, 305, h); y += h + gap;
        painelBotoes.add(btnViews);
        btnViews.addActionListener(e -> new TelaViews());

        btnCreditos = botao("Créditos");
        btnCreditos.setBounds(25, y, 305, h);
        painelBotoes.add(btnCreditos);
        btnCreditos.addActionListener(e -> new TelaCreditos());
    }

    private JButton botao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(NinjaTheme.ORANGE);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(NinjaTheme.ORANGE_HOT);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(NinjaTheme.ORANGE);
            }
        });
        return btn;
    }

}
