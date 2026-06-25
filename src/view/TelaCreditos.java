package view;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class TelaCreditos extends JFrame {

    public TelaCreditos() {
        setTitle("Créditos");
        setSize(720, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        NinjaTheme.estilizarFrame(this);
        construir();
        setVisible(true);
    }

    private void construir() {
        JLabel lblTitulo = new JLabel("CRÉDITOS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 22));
        lblTitulo.setForeground(NinjaTheme.ORANGE_HOT);
        lblTitulo.setBounds(0, 15, 720, 40);
        add(lblTitulo);

        int startX = 30;
        int cardW   = 210;
        int gap     = 15;

        cartao(startX,                  80, cardW, "naruto.png",  "NARUTO",  "Alisson Gonçalves Selau",  null);
        cartao(startX + cardW + gap,    80, cardW, "sasuke.jpeg",  "SASUKE",  "Saymon Espindola Inacio",  null);
        cartao(startX + (cardW+gap)*2,  80, cardW, "goku.png",    "GOKU",    "Samuel Conradt do Amaral", "(Ele não sabe de anime)");
    }

    private void cartao(int x, int y, int w, String arquivo, String personagem, String nome, String obs) {
        JPanel painel = new JPanel(null);
        painel.setBackground(NinjaTheme.BG_PANEL);
        painel.setBorder(NinjaTheme.bordaTitulo(""));
        painel.setBounds(x, y, w, 340);
        add(painel);

        ImageIcon icone = carregarImagem(arquivo, 170, 180);
        JLabel lblImg = new JLabel(icone, SwingConstants.CENTER);
        lblImg.setBounds(20, 15, 170, 180);
        painel.add(lblImg);

        JLabel lblPersonagem = new JLabel(personagem, SwingConstants.CENTER);
        lblPersonagem.setFont(new Font("Arial Black", Font.BOLD, 16));
        lblPersonagem.setForeground(NinjaTheme.ORANGE_HOT);
        lblPersonagem.setBounds(5, 205, w - 10, 28);
        painel.add(lblPersonagem);

        JLabel lblNome = new JLabel("<html><center>" + nome + "</center></html>", SwingConstants.CENTER);
        lblNome.setFont(new Font("Arial", Font.BOLD, 12));
        lblNome.setForeground(NinjaTheme.TEXT_WHITE);
        lblNome.setBounds(5, 238, w - 10, 40);
        painel.add(lblNome);

        if (obs != null) {
            JLabel lblObs = new JLabel(obs, SwingConstants.CENTER);
            lblObs.setFont(new Font("Arial", Font.ITALIC, 10));
            lblObs.setForeground(NinjaTheme.TEXT_ORANGE);
            lblObs.setBounds(5, 282, w - 10, 20);
            painel.add(lblObs);
        }
    }

    private ImageIcon carregarImagem(String arquivo, int largura, int altura) {
        try (InputStream is = getClass().getResourceAsStream("/assets/" + arquivo)) {
            if (is != null) {
                ImageIcon raw = new ImageIcon(is.readAllBytes());
                Image scaled = raw.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return semImagem(largura, altura);
    }

    private ImageIcon semImagem(int w, int h) {
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(NinjaTheme.BG_INPUT);
        g.fillRect(0, 0, w, h);
        g.setColor(NinjaTheme.BORDER_CLR);
        g.drawRect(0, 0, w - 1, h - 1);
        g.setColor(NinjaTheme.TEXT_ORANGE);
        g.setFont(new Font("Arial", Font.BOLD, 11));
        g.drawString("sem imagem", 30, h / 2);
        g.dispose();
        return new ImageIcon(img);
    }
}
