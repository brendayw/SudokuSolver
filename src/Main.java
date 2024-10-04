import javax.swing.*;
import GUI.PantallaPrincipal;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PantallaPrincipal().setVisible(true);
            }
        });
    }

}