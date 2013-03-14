package com.example.sudoku_grafico.util;

import java.util.ArrayList;

import android.widget.ArrayAdapter;


public class TableroSudoku {
	final int VACIO = 0;
	final int tamano;
	final int tamano_caja;

	private ArrayList<int[]> filas = new ArrayList<int[]>();
	private ArrayList<int[]> columnas = new ArrayList<int[]>();
	private ArrayList<int[][]> matricesInternas = new ArrayList<int[][]>();

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

	public boolean estaCorrecto(){
		boolean esCorrecto =  true;

		if(tieneRepetidosCoF(0) || tieneRepetidosCoF(1) || tieneRepetidosEnSub_Matrices()) esCorrecto = false;

		return esCorrecto;
	}

	public void sacarEnArreglo(int opcion){ //0 por filas, 1 por columnas

		if(opcion == 0){//por filas

			for (int i = 0; i < tablero.length; i++) {
				int[] arreglo = new int[9];
				for (int j = 0; j < tablero.length; j++) {
					arreglo[j] = tablero[i][j];
				}
				filas.add(arreglo);
			}
		}else{
			for (int i = 0; i < tablero.length; i++) {
				int[] arreglo = new int[9];

				for (int j = 0; j < tablero.length; j++) {
					int var = tablero[j][i];
					arreglo[j] = var;
				}
				columnas.add(arreglo);
			}
		}
	}

	public boolean tieneRepetidos(int[] arreglo){

		for(int i=0;i<arreglo.length-1;i++){
			for(int j=i+1;j<arreglo.length;j++){
				if(arreglo[i]!=0){
					if(arreglo[i]==arreglo[j]){
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean tieneRepetidosCoF(int op){//0 para filas, 1 para columnas

		if(op == 0){
			int[] temp = new int[9];
			for (int i = 0; i < filas.size(); i++) {
				temp = filas.get(i);
				boolean tiene = tieneRepetidos(temp);
				if(tiene == true){
					System.out.println("Valor del boolean: "+tiene);
					return true;
				}
			}
		}else{
			int[] temp = new int[9];
			for (int i = 0; i < columnas.size(); i++) {
				temp = columnas.get(i);
				boolean tiene = tieneRepetidos(temp);
				if(tiene){
					System.out.println("Valor del boolean: "+tiene);
					return true;
				}
			}
		}


		return false;
	}

	public boolean tieneRepetidosEnSub_Matrices(){

		int[][] temp = new int[3][3];
		for (int i = 0; i < matricesInternas.size(); i++) {
			temp = matricesInternas.get(i);
			boolean tiene = tieneRepetidosMatrizIterna(temp);
			if(tiene){
				System.out.println("Valor del boolean: "+tiene);
				return true;
			}
		}		
		return false;
	}

	public boolean tieneRepetidosMatrizIterna(int[][] matrizInterna){
		int[] arreglo = new int[9];
		int indice = 0;
		for (int i = 0; i < matrizInterna.length; i++) {
			for (int j = 0; j < matrizInterna.length; j++) {
				arreglo[indice] = matrizInterna[i][j];
				indice++;
			}
		}
		boolean tieneRepetidos = tieneRepetidos(arreglo);
		return tieneRepetidos;
	}

	public void obtenerMatricesInternas(){

		int cIni = 0;
		int fIni = 0;
		int cantMatrices = 0;

		do {
			int[][] matriz = new int[3][3];

			switch (cantMatrices) {
			case 0:
				cIni = 0;
				fIni = 0;
				break;

			case 1:
				cIni = 0;
				fIni = 3;
				break;

			case 2:
				cIni = 0;
				fIni = 6;
				break;

			case 3:
				cIni = 3;
				fIni = 0;
				break;

			case 4:
				cIni = 3;
				fIni = 3;
				break;

			case 5:
				cIni = 3;
				fIni = 6;
				break;

			case 6:
				cIni = 6;
				fIni = 0;
				break;

			case 7:
				cIni = 6;
				fIni = 3;
				break;

			case 8:
				cIni = 6;
				fIni = 6;
				break;

			default:
				break;
			}

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if(i+fIni<9 && j+cIni<9){
						matriz[i][j] =  tablero[i+fIni][j+cIni];
					}
				}
			}

			matricesInternas.add(matriz);

			cantMatrices = cantMatrices+1;

		} while (cantMatrices < 9);
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