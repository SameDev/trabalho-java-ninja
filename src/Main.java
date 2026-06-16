import connection.DatabaseSetup;
import view.NinjaTheme;
import view.TelaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        NinjaTheme.apply();

        try {
            DatabaseSetup.inicializar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao conectar ao banco de dados:\n" + e.getMessage() +
                    "\n\nVerifique se o MySQL esta rodando e as credenciais em DatabaseSetup.java",
                    "Erro de Conexao", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new TelaPrincipal();
    }
}
