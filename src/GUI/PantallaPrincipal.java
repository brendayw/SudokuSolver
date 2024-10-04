package GUI;

import controller.SudokuController;
import model.SudokuNodo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashSet;
import java.util.Set;


public class PantallaPrincipal extends JFrame {
    private static PantallaPrincipal instance;
    private JLabel tituloLabel;
    private JPanel panelSudoku;
    private JPanel panelBotones;
    private JPanel subpanel;
    private JTextField[][] textFields;
    private SudokuController controller;

    private enum EstadoPantalla {
        INICIO,
        JUEGO
    }

    @SuppressWarnings(("unused"))
    private EstadoPantalla estadoActualPantalla;

    public PantallaPrincipal() {
        estadoActualPantalla = EstadoPantalla.INICIO;
        controller = new SudokuController();
        inicializarPantalla();
    }

    public static PantallaPrincipal getInstance() {
        if (instance == null) {
            instance = new PantallaPrincipal();
        }
        return  instance;
    }

    public void inicializarPantalla() {
        setTitle("Sudoku Solver");
        setSize(600,600);
        setLayout(new BorderLayout());

        panelSudoku = new JPanel(new BorderLayout());

        tituloLabel = new JLabel("Sudoku Solver", JLabel.CENTER);
        tituloLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        panelSudoku.add(tituloLabel, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout());

        JLabel instruccionesLabel = new JLabel("<html><div style='text-align: center;'>Instrucciones:" +
                "<br><br><br> * Completa el sudoku sin repetir números en filas, columnas o subcuadros." +
                "<br><br> * Al apretar ´Iniciar Juego´ se mortrará el tablero." +
                "<br><br> * Debes llenar al menos 17 casilleros para poder ver las respuestas correctas" +
                "<br><br> * Al reiniciar el juego, el tablero se vaciará por completo</div></html>");
        instruccionesLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        panelCentro.add(instruccionesLabel, BorderLayout.CENTER);

        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton iniciarButton = new JButton("Iniciar Juego");
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego();
            }
        });

        JButton salirButton = new JButton("Salir");
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panelBotones.add(iniciarButton);
        panelBotones.add(salirButton);
        panelCentro.add(panelBotones, BorderLayout.NORTH);

        panelSudoku.add(panelCentro, BorderLayout.CENTER);

        add(panelSudoku, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void iniciarJuego() {
        estadoActualPantalla = EstadoPantalla.JUEGO;
        controller.cargarSudokuPrecargado();
        remove(panelSudoku);

        subpanel = new JPanel();
        subpanel.setLayout(new GridLayout(9,9));
        textFields = new JTextField[9][9];
        subpanel.setSize(600,600);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                textFields[i][j] = new JTextField();
                textFields[i][j].setPreferredSize(new Dimension(50, 50));
                textFields[i][j].setHorizontalAlignment(JTextField.CENTER);
                textFields[i][j].setFont(new Font("Arial", Font.PLAIN, 20));

                int valor = controller.getGrafo().getNodos()[i][j].getValor();
                if (valor != 0) {
                    textFields[i][j].setText(Integer.toString(valor));
                    textFields[i][j].setEditable(false);
                }
                if (deberiaSerColoreado(i, j)) {
                    textFields[i][j].setBackground(Color.decode("#CCFFCC"));
                } else {
                    textFields[i][j].setBackground(Color.WHITE);
                }
                subpanel.add(textFields[i][j]);
            }
        }

        panelBotones = new JPanel();
        JButton reiniciarButton = new JButton("Reiniciar");

        reiniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarSudoku();
            }
        });

        JButton resolverButton = new JButton("Resolver");
        resolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resolverSudoku();
            }
        });

        JButton salirSinResolverButton = new JButton("Salir sin resolver");
        salirSinResolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volver();
            }
        });

        panelBotones.add(reiniciarButton);
        panelBotones.add(resolverButton);
        panelBotones.add(salirSinResolverButton);

        add(subpanel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    private boolean deberiaSerColoreado(int fila, int col) {
        int subCuadroFila = fila / 3;
        int subCuadroCol = col / 3;
        return (subCuadroFila + subCuadroCol) % 2 == 0;
    }

    private void reiniciarSudoku() {
        controller.cargarSudokuPrecargado(); //carga nuevo juego
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                textFields[i][j].setText("");

                int valor = controller.getGrafo().getNodos()[i][j].getValor();
                if (valor !=0) {
                    textFields[i][j].setText(Integer.toString(valor));
                    textFields[i][j].setEditable(false);
                } else {
                    textFields[i][j].setEditable(true);
                }
            }
        }
    }

    private void resolverSudoku() {
        int[][] tablero = new int[9][9];
        boolean error = false;
        int contador = 0;
        Set<String> seen = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String texto = textFields[i][j].getText().trim();

                if (!texto.isEmpty()) {
                    try {
                        int valor = Integer.parseInt(texto);
                        if (valor < 1 || valor > 9) {
                            throw new NumberFormatException();
                        }
                        int subCuadro = (i / 3) * 3 + (j / 3);
                        if (seen.contains("fila" + i + "valor" + valor) ||
                                seen.contains("columna" + j + "valor" + valor) ||
                                seen.contains("subCuadro" + subCuadro + "valor" + valor)) {
                            JOptionPane.showMessageDialog(this, "No se permiten números duplicados en filas, " +
                                    "columnas o subcuadros.");
                            error = true;
                            break;
                        }
                        tablero[i][j] = valor;
                        contador++;
                        seen.add("fila" + i + "valor" + valor);
                        seen.add("columna" + j + "valor" + valor);
                        seen.add("subCuadro" + subCuadro + "valor" + valor);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Ingrese solo números válidos (del 1 al 9).");
                        error = true;
                        break;
                    }
                } else {
                    tablero[i][j] = 0;
                }
            }
            if (error) {
                break;
            }
        }

        if (!error) {
            if (contador < 17) {
                JOptionPane.showMessageDialog(this, "El Sudoku debe tener al menos 17 casillas rellenas.");
            } else {
                controller = new SudokuController();
                if (controller.resolver(tablero)) {
                    SudokuNodo[][] nodos = controller.getGrafo().getNodos();
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            textFields[i][j].setText(Integer.toString(nodos[i][j].getValor()));
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Sudoku resuelto con éxito.");
                    volver();
                } else {
                    JOptionPane.showMessageDialog(this, "El Sudoku no tiene solución válida.");
                }
            }
        }
    }

    private void volver() {
        estadoActualPantalla = EstadoPantalla.INICIO;

        remove(subpanel);
        remove(panelBotones);
        add(panelSudoku, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public void mostrarVentana() {
        setVisible(true);
    }
}
