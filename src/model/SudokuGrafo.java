package model;

import java.util.List;
import java.util.ArrayList;

public class SudokuGrafo {
    private SudokuNodo[][] nodos;
    private List<SudokuNodo> nodosVacios;

    //constructor
    public SudokuGrafo() {
        this.nodos = new SudokuNodo[9][9];
        this.nodosVacios = new ArrayList<>();
    }

    public void inicializar(int[][] tablero) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                nodos[i][j] = new SudokuNodo(i, j, tablero[i][j]);
                if (tablero[i][j] == 0) {
                    nodosVacios.add(nodos[i][j]);
                }
            }
        }
    }

    public boolean resolver() {
        SudokuNodo nodoVacio = encontrarNodoVacio();
        if (nodoVacio == null) {
            return true;
        }

        int fila = nodoVacio.getFila();
        int col = nodoVacio.getCol();

        for (int num = 1; num <= 9; num++) {
            if (esValido(nodoVacio, num)) {
                nodos[fila][col].setValor(num);
                if (resolver()) {
                    return true;
                }
                nodos[fila][col].setValor(0);  // Backtracking
            }
        }
        return false;
    }

    private SudokuNodo encontrarNodoVacio() {
        for (SudokuNodo nodo : nodosVacios) {
            if (nodo.getValor() == 0) {
                return nodo;
            }
        }
        return null;
    }

    public boolean esValido(SudokuNodo nodo, int valor) {
        for (int col = 0; col < 9; col++) {
            if (nodos[nodo.getFila()][col].getValor() == valor) {
                return false;
            }
        }

        for (int fila = 0; fila < 9; fila++) {
            if (nodos[fila][nodo.getCol()].getValor() == valor) {
                return false;
            }
        }

        // verifica la caja 3x3
        int startFila = (nodo.getFila() / 3) * 3;
        int startCol = (nodo.getCol() / 3) * 3;
        for (int fila = startFila; fila < startFila + 3; fila++) {
            for (int col = startCol; col < startCol + 3; col++) {
                if (nodos[fila][col].getValor() == valor) {
                    return false;
                }
            }
        }
        return true;
    }
    public void resetearNodoVacio() {
        nodosVacios.clear();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (nodos[i][j].getValor() == 0) {
                    nodosVacios.add(nodos[i][j]);
                }
            }
        }
    }
    //getters
    public SudokuNodo[][] getNodos() {
        return nodos;
    }

    public List<SudokuNodo> getNodosVacios() {
        return nodosVacios;
    }

}
