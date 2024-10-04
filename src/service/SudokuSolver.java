package service;

import model.SudokuGrafo;
import model.SudokuNodo;
import java.util.List;

public class SudokuSolver {
    private SudokuGrafo grafo;

    public SudokuSolver(SudokuGrafo grafo) {
        this.grafo = grafo;
    }

    public boolean resolver() {
        SudokuNodo[][] nodo = grafo.getNodos();
        List<SudokuNodo> nodoVacio = grafo.getNodosVacios();

        boolean solucionCorrecta = resolucionRecursiva(nodo, nodoVacio, 0);

        if (!solucionCorrecta) {
            resetearNodoVacio();
        }
        return solucionCorrecta;
    }

    private boolean resolucionRecursiva(SudokuNodo[][] nodo, List<SudokuNodo> nodoVacio, int index) {
        if (index == nodoVacio.size()) {
            return true;
        }

        SudokuNodo nodoActual = nodoVacio.get(index);
        for (int valor = 1; valor <= 9; valor++) {
            if (grafo.esValido(nodoActual, valor)) {
                nodoActual.setValor(valor);
                if (resolucionRecursiva(nodo, nodoVacio, index + 1)) {
                    return true;
                }
                nodoActual.setValor(0);
            }
        }
        return false;
    }

    private void resetearNodoVacio() {
        List<SudokuNodo> nodoVacio = grafo.getNodosVacios();
        for (SudokuNodo nodo : nodoVacio) {
            nodo.setValor(0);
        }
    }

    public SudokuGrafo getGrafo() {
        return grafo;
    }
}
