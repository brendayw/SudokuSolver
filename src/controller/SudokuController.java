package controller;

import model.SudokuGrafo;
import service.SudokuSolver;

import java.util.Random;


public class SudokuController {
    private SudokuGrafo sudokuGrafo;
    private SudokuSolver solver;

    //constructor
    public SudokuController() {
        this.sudokuGrafo = new SudokuGrafo();
        this.solver = new SudokuSolver(sudokuGrafo);
    }

    //getter
    public SudokuGrafo getGrafo() {
        return sudokuGrafo;
    }

    public boolean resolver(int[][] tablero) {
        sudokuGrafo.inicializar(tablero);
        return solver.resolver();
    }

    public void cargarSudokuPrecargado() {
        int[][][] tablero = {
                {
                        {5, 3, 0, 0, 7, 0, 0, 0, 0},
                        {6, 0, 0, 1, 9, 5, 0, 0, 0},
                        {0, 9, 8, 0, 0, 0, 0, 6, 0},
                        {8, 0, 0, 0, 6, 0, 0, 0, 3},
                        {4, 0, 0, 8, 0, 3, 0, 0, 1},
                        {7, 0, 0, 0, 2, 0, 0, 0, 6},
                        {0, 6, 0, 0, 0, 0, 2, 8, 0},
                        {0, 0, 0, 4, 1, 9, 0, 0, 5},
                        {0, 0, 0, 0, 8, 0, 0, 7, 9}
                },
                {
                        {0, 6, 0, 1, 0, 4, 0, 5, 0},
                        {0, 0, 8, 3, 0, 5, 6, 0, 0},
                        {2, 0, 0, 0, 0, 0, 0, 0, 1},
                        {8, 0, 0, 4, 0, 7, 0, 0, 6},
                        {0, 0, 6, 0, 0, 0, 3, 0, 0},
                        {7, 0, 0, 9, 0, 1, 0, 0, 4},
                        {5, 0, 0, 0, 0, 0, 0, 0, 2},
                        {0, 0, 7, 2, 0, 6, 9, 0, 0},
                        {0, 4, 0, 5, 0, 8, 0, 7, 0}
                },
                {
                        {0, 8, 0, 5, 0, 0, 7, 0, 0},
                        {0, 2, 0, 0, 0, 7, 0, 0, 9},
                        {0, 0, 0, 0, 4, 9, 0, 0, 3},
                        {0, 0, 4, 0, 2, 0, 6, 0, 0},
                        {0, 9, 0, 0, 0, 0, 0, 5, 0},
                        {0, 0, 5, 0, 1, 0, 9, 0, 0},
                        {7, 0, 0, 4, 6, 0, 0, 0, 0},
                        {2, 0, 0, 1, 0, 0, 0, 3, 0},
                        {0, 0, 8, 0, 0, 3, 0, 6, 0}
                }
        };

        Random rand = new Random();
        int index = rand.nextInt(tablero.length);
        sudokuGrafo.inicializar(tablero[index]);
    }

    public SudokuGrafo getSudokuGrafo() {
        return sudokuGrafo;
    }
}
