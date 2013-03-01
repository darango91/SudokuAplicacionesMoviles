package com.example.sudoku_grafico.util;

import java.util.ArrayList;

public class MatrizSudoku {

	private char[][] tableroInicial = new char[9][9];
	private char[][] tableroResuelto = new char[9][9];
	private boolean esCorrecto;
	private ArrayList<char[][]> matricesInternas = new ArrayList<char[][]>();
	private ArrayList<char[]> filas = new ArrayList<char[]>();
	private ArrayList<char[]> columnas = new ArrayList<char[]>();

	public MatrizSudoku(char[][] tableroInicial) {
		super();
		this.tableroInicial = tableroInicial;
		obtenerMatricesInternas();
		sacarEnArreglo(0);
		sacarEnArreglo(1);
	}

	public void resolver(){
		
		//for (int i = 0; i < matricesInternas.size(); i++) {
			char[][] matriz = matricesInternas.get(0); //poner i en este lugar
			
			System.out.println("Matriz: "+(1));
			imprimirSubMatriz(matriz);
			
			int[] indices = indiceDeSeleccion(0);//Indices que corresponden al inicio de conteo			
			ArrayList[] coordenadas = new ArrayList[9];
			
			for (int i = 0; i < coordenadas.length; i++) {
				System.out.println(coordenadas[i]);
			}
			
			coordenadas = coordenadasOcupadas(matriz);//Primer alteración al arreglo
			
			for (int j = 0; j < matriz.length; j++) {
				for (int k = 0; k < matriz.length; k++) {
					System.out.println("Value at: "+k);
					char[] fila = filas.get(j+indices[0]);
					char[] columna = columnas.get(k+indices[1]);
					
					imprimirArreglo(fila);
					imprimirArreglo(columna);
					
					String cadena = "" + matriz[j][k]; 
					int li = Integer.parseInt(cadena) - 1;
					
					//imprimirArreglo(coordenadasOcupadas);
				}
			}
		//}
	}
	
	//Este método ingresa un null en la posicion que corresponda al numero encontrado
	//Ej: numero encontrado=2 => se pone null en la posición 1 del arreglo.
	public ArrayList<char[]>[]  coordenadasOcupadas(char[][] matriz){
		
		ArrayList[] listas = new ArrayList[9];
		
		for (int i1 = 0; i1 < matriz.length; i1++) {
			for (int j1 = 0; j1 < matriz.length; j1++) {
				if(matriz[i1][j1]!=' '){
					String cadena = "" + matriz[i1][j1]; 
					int l = Integer.parseInt (cadena) - 1;
					listas[l] = null;
				}
			}
		}
		
		return listas;
	}
	
	public int[] indiceDeSeleccion(int indiceSubMatriz){
		int[] indiceXY = new int[2];
		int i = 0, j = 0;
		
		switch (indiceSubMatriz) {
		case 0:
			i = 0;
			j = 0;
			break;

		case 1:
			i = 0;
			j = 3;
			break;

		case 2:
			i = 0;
			j = 6;
			break;

		case 3:
			i = 3;
			j = 0;
			break;

		case 4:
			i = 3;
			j = 3;
			break;

		case 5:
			i = 3;
			j = 6;
			break;

		case 6:
			i = 6;
			j = 0;
			break;

		case 7:
			i = 6;
			j = 3;
			break;

		case 8:
			i = 6;
			j = 6;
			break;

		default:
			break;
		}
		
		indiceXY[0] = i;
		indiceXY[1] = j;
		
		return indiceXY;
	}
	
	
	//-------------------------------------------------------------
	public void llenarTablero(){
		
		boolean seguir = true;
		int cantidad = 0;
		
		do{
			for (int i = 0; i < tableroInicial.length; i++) {
				for (int j = 0; j < tableroInicial.length; j++) {
					char car = tableroInicial[i][j];
					if(car==' '){
						tableroResuelto[i][j] = car;		//Envio el caracter a la matríz resuelta para guardarlo
						char[] fila = filas.get(i);
						char[] columna = columnas.get(j);
						int indiceSubMatriz = indiceSubMatriz(i, j); //ya se obtuvo el valor de la sub-matriz a la que corresponde el número ubicado.
						char[][] subMatriz = matricesInternas.get(indiceSubMatriz);
												
						char[] opcionesFila = valoresActuales(fila);
						
						char[] opcionesColumna = valoresActuales(columna);
						
						char[] opcionesSubMatriz = evaluarPosibilidadesSubMatriz(subMatriz);
						
						char[] opciones = validarCasilla(opcionesFila, opcionesColumna, opcionesSubMatriz);
						
						int valor = valorAPoner(opciones)+1;
						System.out.println(valor);
						if(valor != -1){
							tableroResuelto[i][j] = Character.forDigit(valor, 10);
						}
					}else{
						tableroResuelto[i][j] = car;
					}
				}
			}
			if(estaCompleto(tableroResuelto)){
				System.out.println("Se resolvió!!! :D");
				seguir = false;
			}else{
				seguir = true;
				cantidad++;
			}
			
		}while(seguir && cantidad < 100000);
		
		imprimirTablero(tableroResuelto);		
		
	}
	
	public int valorAPoner(char[] opciones){
		
		int cuentaVacios = 0, value = 0;
		for (int i = 0; i < opciones.length; i++) {
			if(opciones[i]=='x'){
				cuentaVacios++;
				value = i;
			}
		}
		
		if(cuentaVacios > 1) return -1;
		else return value;
	}
	
	public int indiceSubMatriz(int i, int j){
		int indiceSubMatriz = -1;
		if(i>=0 && i<3){
			if(j<3){
				indiceSubMatriz = 0;
			}else{
				if(j>=3 && j<6){
					indiceSubMatriz = 3;
				}else{
					if(j>=6 && j<9){
						indiceSubMatriz = 6;
					}
				}
			}
		}else{
			if(i >= 3 && i<6){
				if(j<3){
					indiceSubMatriz = 2;
				}else{
					if(j>=3 && j<6){
						indiceSubMatriz = 4;
					}else{
						if(j>=6 && j<9){
							indiceSubMatriz = 7;
						}
					}
				}						
			}else{
				if(i >= 6 && i<9){
					if(j<3){
						indiceSubMatriz = 3;
					}else{
						if(j>=3 && j<6){
							indiceSubMatriz = 6;
						}else{
							if(j>=6 && j<9){
								indiceSubMatriz = 8;
							}
						}
					}
				}
			}
		}
		return indiceSubMatriz;
	}

	public char[] evaluarPosibilidadesSubMatriz(char[][] matrizInterna){
		char[] arreglo = new char[9];

		for (int i = 0; i < matrizInterna.length; i++) {
			for (int j = 0; j < matrizInterna.length; j++) {
				if(matrizInterna[i][j]!=' '){
					String cadena = "" + matrizInterna[i][j]; 
					int l = Integer.parseInt (cadena) - 1;
					arreglo[l] = 'x';
				}
			}
		}

		return arreglo;
	}

	public char [] valoresActuales(char[] o){
		char[] arr = new char[9];
		for (int i = 0; i < o.length; i++) {
			
			if(o[i] == ' ' || o[i] == '0'){
				//arr[l]=' ';
			}else{
				String cadena = "" + o[i]; 
				int l = Integer.parseInt (cadena) - 1;
				arr[l]='x';
			}
		}
		return arr;
	}

	public char[] validarCasilla(char[]fila, char[] col, char[] interna){
		char [] retorno= new char[9];
		for (int i = 0; i < 9;  i++) {
			if(fila[i]!='x' || col[i]!='x'|| interna[i]!='x'){
				retorno[i]='x';
			}
		}
		return retorno ;	 
	}
	//-----------------------------------------------------------------------
	
	public boolean estaCompleto(char[][] tablero){

		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if(tablero[i][j] == '0' || tablero[i][j] == ' '){
					return false;
				}
			}
		}

		return true;
	}	

	public boolean estaCorrecto(){
		esCorrecto =  true;

		if(tieneRepetidosCoF(0) || tieneRepetidosCoF(1) || tieneRepetidosEnSub_Matrices()) esCorrecto = false;

		return esCorrecto;
	}

	public boolean tieneRepetidosCoF(int op){//0 para filas, 1 para columnas

		if(op == 0){
			char[] temp = new char[9];
			for (int i = 0; i < filas.size(); i++) {
				temp = filas.get(i);
				boolean tiene = tieneRepetidos(temp);
				if(tiene == true){
					System.out.println("Valor del boolean: "+tiene);
					return true;
				}
			}
		}else{
			char[] temp = new char[9];
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

		char[][] temp = new char[3][3];
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

	/*
	 * Este metodo sirve para sacar en arreglos cada una de las filas o columnas (depende de
	 * la opcion dada) y se almacenan en las listas que le corresponde.
	 */
	public void sacarEnArreglo(int opcion){ //0 por filas, 1 por columnas

		if(opcion == 0){//por filas

			for (int i = 0; i < tableroInicial.length; i++) {
				char[] arreglo = new char[9];
				for (int j = 0; j < tableroInicial.length; j++) {
					arreglo[j] = tableroInicial[i][j];
				}
				filas.add(arreglo);
			}
		}else{
			for (int i = 0; i < tableroInicial.length; i++) {
				char[] arreglo = new char[9];

				for (int j = 0; j < tableroInicial.length; j++) {
					char var = tableroInicial[j][i];
					arreglo[j] = var;
				}
				columnas.add(arreglo);
			}
		}
	}

	/*
	 * Este método sirve para revisar si un arreglo tiene o no valores repetidos dentro
	 * de este.
	 */
	public boolean tieneRepetidos(char[] arreglo){

		for(int i=0;i<arreglo.length-1;i++){
			for(int j=i+1;j<arreglo.length;j++){
				if(arreglo[i]!=' '){
					if(arreglo[i]==arreglo[j]){
						return true;
					}
				}
			}
		}
		return false;
	}

	/*
	 * Este método valida que una matriz interna (la matriz de 3x3) no tenga valores repetidos
	 */
	public boolean tieneRepetidosMatrizIterna(char[][] matrizInterna){
		char[] arreglo = new char[9];
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

	/*
	 * Con este metodo se hallan las sub-matrices que conforman el sudoku y se almacenan en un
	 * ArrayList llamado matricesInternas
	 */
	public void obtenerMatricesInternas(){

		int cIni = 0;
		int fIni = 0;
		int cantMatrices = 0;

		do {
			char[][] matriz = new char[3][3];

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
						matriz[i][j] =  tableroInicial[i+fIni][j+cIni];
					}
				}
			}

			matricesInternas.add(matriz);

			cantMatrices = cantMatrices+1;

		} while (cantMatrices < 9);
	}

	public void imprimirTablero(char[][] tablero){
		String matriz = "";
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero.length; j++) {
				if(j==0) matriz += "| ";
				if(j != 8){
					if(j==2||j==5){
						matriz += tablero[i][j]+" || ";
					}else{
						matriz += tablero[i][j]+" | ";
					}
				}else{
					if(i==2||i==5){
						//matriz += "| 1 | 2 | 3 || 4 | 5 | 6 || 7 | 8 | 9 |"
						matriz += tablero[i][j]+" |\n";
						matriz += "---------------------------------------\n";
					}else{
						matriz += tablero[i][j]+" |\n";
					}
				}
			}
		}
		System.out.println(matriz);
	}

	public void imprimirFilasOColumnas(int op){ //0 para filas, 1 para columnas
		if(op == 0){
			System.out.println("Contenido por filas");
			char[] temp = new char[9];
			for (int i = 0; i < filas.size(); i++) {
				temp = filas.get(i);
				System.out.println("Fila numero: "+i);
				for (int j = 0; j < temp.length; j++) {
					System.out.print(temp[j]+" ");
				}
				System.out.println("\n");
			}
		}else{
			System.out.println("Contenido por columnas");
			char[] temp = new char[9];
			for (int i = 0; i < columnas.size(); i++) {
				temp = columnas.get(i);
				System.out.println("Columna numero: "+i);
				for (int j = 0; j < temp.length; j++) {
					System.out.print(temp[j]+" ");
				}
				System.out.println("\n");
			}
		}
	}
	
	public void imprimirArreglo(char[] arreglo){
		for (int i = 0; i < arreglo.length; i++) {
			if((i+1)==arreglo.length){
				System.out.print(arreglo[i]+"\n");
			}else{
				System.out.print(arreglo[i]);
			}
		}
	}
	
	public void imprimirSubMatriz(char[][] subMatriz){
		String m = "";
		for (int i = 0; i < subMatriz.length; i++) {
			for (int j = 0; j < subMatriz.length; j++) {
				m += subMatriz[i][j]+" ";
				if(j == 2) m += "\n";
			}
		}
		System.out.println(m);
	}

	public char[][] getTableroInicial() {
		return tableroInicial;
	}
	

	public void setTableroInicial(char[][] tableroInicial) {
		this.tableroInicial = tableroInicial;
	}
}