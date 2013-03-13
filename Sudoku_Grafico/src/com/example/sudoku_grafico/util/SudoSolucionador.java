package com.example.sudoku_grafico.util;

public class SudoSolucionador {
    private TableroSudoku ts;    // Puzzle to solve;

    public SudoSolucionador(TableroSudoku ts) {
        this.ts = ts;
    }
    
    private boolean revisar(int num, int fila, int col) {
        int r = (fila / ts.tamano_caja) * ts.tamano_caja;
        int c = (col / ts.tamano_caja) * ts.tamano_caja;
        
        for (int i = 0; i < ts.tamano; i++) {
            if (ts.getCelda(fila, i) == num ||
                ts.getCelda(i, col) == num ||
                ts.getCelda(r + (i % ts.tamano_caja), c + (i / ts.tamano_caja)) == num) {
                return false;
            }
        }
        return true;
    }

    public boolean adivina(int fila, int col) {
        int sigCol = (col + 1) % ts.tamano;
        int sigFila = (sigCol == 0) ? fila + 1 : fila;
        
        try {
            if (ts.getCelda(fila, col) != ts.VACIO)
                return adivina(sigFila, sigCol);
        }
        catch (ArrayIndexOutOfBoundsException e) {
                return true;
        }

        for (int i = 1; i <= ts.tamano; i++) {
            if (revisar(i, fila, col)) {
                ts.setCelda(i, fila, col);
                if (adivina(sigFila, sigCol)) {
                    return true;
                }
            }
        }
        ts.setCelda(ts.VACIO, fila, col);
        return false;
    }
    
    public int[][] retornaTablero(){
    	return ts.getTablero();
    }
}