package model;

public class SudokuNodo {
    private int fila;
    private int col;
    private int valor;

    //constructor
    public SudokuNodo(int fila, int col, int valor) {
        this.fila = fila;
        this.col = col;
        this.valor = valor;
    }

    //getters y setters
    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
