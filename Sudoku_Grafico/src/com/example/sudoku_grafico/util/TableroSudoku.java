package com.example.sudoku_grafico.util;


public class TableroSudoku {
    final int VACIO = 0;
    final int tamano;
    final int tamano_caja;

    private int[][] tablero;

    public TableroSudoku(int size) {
        tablero = new int[size][size];
        this.tamano = size;
        this.tamano_caja = (int) Math.sqrt(size);
    }
    
    public TableroSudoku(int[][] board) {
        this(board.length);
        this.tablero = board;
    }

    public void setCelda(int num, int fila, int col) {
        tablero[fila][col] = num;
    }

    public int getCelda(int fila, int col) {
        return tablero[fila][col];
    }
    
    public int[][] getTablero(){
    	return this.tablero;
    }
}