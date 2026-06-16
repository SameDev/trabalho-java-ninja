package view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;

public class NinjaTheme {

    public static final Color BG_DARK    = new Color(12, 12, 12);
    public static final Color BG_PANEL   = new Color(22, 22, 22);
    public static final Color BG_INPUT   = new Color(18, 18, 18);
    public static final Color ORANGE     = new Color(232, 93, 4);
    public static final Color ORANGE_HOT = new Color(255, 140, 0);
    public static final Color RED_DARK   = new Color(120, 15, 5);
    public static final Color TEXT_WHITE = Color.WHITE;
    public static final Color TEXT_ORANGE= new Color(255, 165, 50);
    public static final Color TBL_ROW_A  = new Color(18, 18, 18);
    public static final Color TBL_ROW_B  = new Color(28, 14, 4);
    public static final Color TBL_HEADER = new Color(100, 28, 0);
    public static final Color BORDER_CLR = new Color(180, 70, 0);

    private static final Font FONT_LABEL  = new Font("Arial", Font.BOLD, 12);
    private static final Font FONT_BTN    = new Font("Arial", Font.BOLD, 13);
    private static final Font FONT_HEADER = new Font("Arial", Font.BOLD, 12);

    public static void apply() {
        UIManager.put("Panel.background",                  BG_PANEL);
        UIManager.put("OptionPane.background",             BG_PANEL);
        UIManager.put("OptionPane.messageForeground",      TEXT_WHITE);

        UIManager.put("Button.background",                 ORANGE);
        UIManager.put("Button.foreground",                 Color.WHITE);
        UIManager.put("Button.font",                       FONT_BTN);
        UIManager.put("Button.focus",                      ORANGE);
        UIManager.put("Button.select",                     RED_DARK);

        UIManager.put("Label.foreground",                  TEXT_ORANGE);
        UIManager.put("Label.font",                        FONT_LABEL);

        UIManager.put("TextField.background",              BG_INPUT);
        UIManager.put("TextField.foreground",              TEXT_WHITE);
        UIManager.put("TextField.caretForeground",         ORANGE_HOT);
        UIManager.put("TextField.selectionBackground",     ORANGE);
        UIManager.put("TextField.selectionForeground",     Color.WHITE);

        UIManager.put("TextArea.background",               BG_INPUT);
        UIManager.put("TextArea.foreground",               TEXT_WHITE);
        UIManager.put("TextArea.caretForeground",          ORANGE_HOT);
        UIManager.put("TextArea.selectionBackground",      ORANGE);

        UIManager.put("ComboBox.background",               BG_INPUT);
        UIManager.put("ComboBox.foreground",               TEXT_WHITE);
        UIManager.put("ComboBox.selectionBackground",      ORANGE);
        UIManager.put("ComboBox.selectionForeground",      Color.WHITE);
        UIManager.put("ComboBox.buttonBackground",         BG_INPUT);

        UIManager.put("Table.background",                  TBL_ROW_A);
        UIManager.put("Table.foreground",                  TEXT_WHITE);
        UIManager.put("Table.selectionBackground",         ORANGE);
        UIManager.put("Table.selectionForeground",         Color.WHITE);
        UIManager.put("Table.gridColor",                   new Color(45, 45, 45));
        UIManager.put("TableHeader.background",            TBL_HEADER);
        UIManager.put("TableHeader.foreground",            Color.WHITE);
        UIManager.put("TableHeader.font",                  FONT_HEADER);
        UIManager.put("TableHeader.cellBorder",            BorderFactory.createLineBorder(BORDER_CLR));

        UIManager.put("ScrollPane.background",             BG_DARK);
        UIManager.put("Viewport.background",               BG_DARK);
        UIManager.put("ScrollBar.background",              BG_PANEL);
        UIManager.put("ScrollBar.thumb",                   ORANGE);
        UIManager.put("ScrollBar.thumbDarkShadow",         RED_DARK);
        UIManager.put("ScrollBar.thumbHighlight",          ORANGE_HOT);
        UIManager.put("ScrollBar.thumbShadow",             RED_DARK);
        UIManager.put("ScrollBar.track",                   BG_DARK);
        UIManager.put("ScrollBar.trackHighlight",          BG_PANEL);

        UIManager.put("TabbedPane.background",             BG_PANEL);
        UIManager.put("TabbedPane.foreground",             TEXT_ORANGE);
        UIManager.put("TabbedPane.selected",               ORANGE);
        UIManager.put("TabbedPane.selectedForeground",     Color.WHITE);
        UIManager.put("TabbedPane.contentAreaColor",       BG_PANEL);
        UIManager.put("TabbedPane.light",                  BG_PANEL);
        UIManager.put("TabbedPane.highlight",              ORANGE);
        UIManager.put("TabbedPane.shadow",                 RED_DARK);
        UIManager.put("TabbedPane.darkShadow",             BG_DARK);
        UIManager.put("TabbedPane.font",                   FONT_BTN);

        UIManager.put("List.background",                   BG_INPUT);
        UIManager.put("List.foreground",                   TEXT_WHITE);
        UIManager.put("List.selectionBackground",          ORANGE);
        UIManager.put("List.selectionForeground",          Color.WHITE);

        UIManager.put("CheckBox.background",               BG_PANEL);
        UIManager.put("CheckBox.foreground",               TEXT_ORANGE);
        UIManager.put("RadioButton.background",            BG_PANEL);
        UIManager.put("RadioButton.foreground",            TEXT_ORANGE);

        UIManager.put("ToolTip.background",                BG_PANEL);
        UIManager.put("ToolTip.foreground",                TEXT_ORANGE);
    }

    public static void estilizarFrame(JFrame frame) {
        frame.getContentPane().setBackground(BG_DARK);
    }

    public static void estilizarTabela(JTable tabela) {
        tabela.setBackground(TBL_ROW_A);
        tabela.setForeground(TEXT_WHITE);
        tabela.setSelectionBackground(ORANGE);
        tabela.setSelectionForeground(Color.WHITE);
        tabela.setGridColor(new Color(45, 45, 45));
        tabela.setRowHeight(26);
        tabela.setFont(new Font("Arial", Font.PLAIN, 12));
        tabela.setShowGrid(true);

        JTableHeader header = tabela.getTableHeader();
        header.setBackground(TBL_HEADER);
        header.setForeground(Color.WHITE);
        header.setFont(FONT_HEADER);
        header.setBorder(BorderFactory.createLineBorder(BORDER_CLR));

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                if (isSelected) {
                    setBackground(ORANGE);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(row % 2 == 0 ? TBL_ROW_A : TBL_ROW_B);
                    setForeground(TEXT_WHITE);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                return this;
            }
        });
    }

    public static Border bordaTitulo(String titulo) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_CLR, 2),
                titulo,
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 11),
                TEXT_ORANGE
        );
    }

    public static JScrollPane scrollEstilizado(JTable tabela) {
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(BG_DARK);
        scroll.setBackground(BG_DARK);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_CLR, 1));
        return scroll;
    }
}
