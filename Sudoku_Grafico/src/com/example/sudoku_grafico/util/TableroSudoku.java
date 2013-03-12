package com.example.sudoku_grafico.util;


public class TableroSudoku {
    final int VACIO = 0;      // Empty cells marker
    final int tamano;           // Size of the board (number of rows and columns)
    final int tamano_caja;       // Size of the inner boxes

    private int[][] tablero;    // 2D array representing the game board

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
}