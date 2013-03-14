package com.example.sudoku_grafico.util;

public class Util {

	public static String matrizToString(int[][] matriz){
		String str = "";
		
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				str += matriz[i][j];
			}
		}
		return str;
	}
}
