package com.example.sudoku_grafico;

import java.util.Timer;
import java.util.TimerTask;

import com.example.sudoku_grafico.util.*;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SudukoActivity extends Activity{

	private TextView timeString;
	private Handler handler; 
	private Timer timer;
	private int[][] tablero = new int[9][9];
	private int[][] tableroResuelto = new int[9][9];

	private String sudoku, str = "";
	private Button solucionar, verificar, pausaPlay;
	private ImageButton restart;
	private EditText text1_1, text1_2, text1_3, text1_4, text1_5, text1_6, text1_7, text1_8, text1_9,
	text2_1, text2_2, text2_3, text2_4, text2_5, text2_6, text2_7, text2_8, text2_9,
	text3_1, text3_2, text3_3, text3_4, text3_5, text3_6, text3_7, text3_8, text3_9,
	text4_1, text4_2, text4_3, text4_4, text4_5, text4_6, text4_7, text4_8, text4_9,
	text5_1, text5_2, text5_3, text5_4, text5_5, text5_6, text5_7, text5_8, text5_9,
	text6_1, text6_2, text6_3, text6_4, text6_5, text6_6, text6_7, text6_8, text6_9,
	text7_1, text7_2, text7_3, text7_4, text7_5, text7_6, text7_7, text7_8, text7_9,
	text8_1, text8_2, text8_3, text8_4, text8_5, text8_6, text8_7, text8_8, text8_9,
	text9_1, text9_2, text9_3, text9_4, text9_5, text9_6, text9_7, text9_8, text9_9;

	private int seg = 0, min = 0, h = 0, estado = 0, totalSeg = 0;

	private TableroSudoku ts;
	private SudoSolucionador ss;

	private boolean es = false, es1 = false, ayudado = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suduko);
		
		String value = "8,0,0,0,0,0,0,0,6,0,0,4,8,0,3,9,0,0,0,0,3,0,0,0,7,0,0,0,7,0,2,0,5,0,1,0,0,0,0,0,1,0,0,0,0,0,3,0,9,0,7,0,6,0,0,0,7,0,0,0,2,0,0,0,0,1,7,0,4,5,0,0,5,0,0,0,0,0,0,0,8";
		
		tablero = stringToIntArray(value, ",");
		
		ts = new TableroSudoku(tablero);
		ss = new SudoSolucionador(ts);
		
		handler = new Handler();
		timeString = (TextView) findViewById(R.id.time);

		sudoku = Util.matrizToString(tablero);

		incializarBotonesCamposTexto();
		eventos();

		temporizador();
	}
	
	private int[][] stringToIntArray(String str, String regex){
		int[][] tab = new int[9][9];
		
		String[] arr = str.split(regex);
		
		int contador = 0;
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab.length; j++) {
				tab[i][j] = Integer.parseInt(arr[contador]);
				contador++;
			}
		}
		
		return tab;		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_soduko, menu);
		return true;
	}

	public void eventos(){

		pausaPlay.setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						//Pausa, reanuda el juego
						if(estado==0){//Estaba activo y va a pausarlo
							pausaPlay.setText("▶");
							timer.cancel();
							timer.purge();
							estado = 1;
						}else{//Estaba pausado y lo va activar
							pausaPlay.setText("||");
							temporizador();
							estado = 0;
						}
					}
				}
				);

		solucionar.setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						MessageBox("Lo voy a resolver -_-'",Toast.LENGTH_SHORT);
						ss.adivina(0,0);
						tableroResuelto = ss.retornaTablero();
						ponerResuelto();
						timer.cancel();
						timer.purge();
						ayudado = true;
					}
				}
				);

		verificar.setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						es = ts.estaCorrecto();
						es1 = ts.estaCompleto(tablero);
						timer.cancel();
						timer.purge();
						if(es&&es1&&!ayudado){
							intent_terminar(2);
						}else{//Aqui se crearía un dialogo indicando que esta mala la solución y que si se quiere continuar
							//o salir del juego
							if(es&&es1&&ayudado){
								MessageBox("Nuestro solucionador funcionó correctamente, ahora resuelvelo tú :3",Toast.LENGTH_LONG);
							}else if(!es||!es1){
								MessageBox("El sudoku está incompleto, debes de terminarlo antes de presionar este botón otra vez", Toast.LENGTH_LONG);
							}
						}
					}
				}
				);

		restart.setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						MessageBox("No pudiste hacerlo T_T (Já já!)", Toast.LENGTH_SHORT);
						incializarBotonesCamposTexto();
						h=0; min=0; seg=0;
						timer.cancel();
						timer.purge();
						temporizador();
					}
				}
				);		

		//Eventos de los textos
		text1_1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String str = s.toString();
				int value = Integer.parseInt(str);
				tablero[0][0] = value;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}
		});
		
		//Acomodar validaciones importantes
		text1_2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String str = s.toString();
				int value = Integer.parseInt(str);
				tablero[0][1] = value;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void intent_terminar(int codigo){
		Intent i = new Intent(getApplicationContext(), FinishActivity.class);
		i.putExtra("Tiempo", str);
		i.putExtra("Tiempo_int", totalSeg);
		i.putExtra("Validacion", es);
		startActivityForResult(i, codigo);
	}

	@Override
	protected void onStart() {
		super.onStart();

		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		sharedPref.getString("key_sudoku", "");
	}

	@Override
	protected void onResume() {
		super.onResume();

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString("key_sudoku",sudoku);
		editor.commit();		
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	public void temporizador(){
		TimerTask tarea = new TimerTask(){
			@Override
			public void run() {

				handler.post(new Runnable(){
					public void run() {
						if(seg<60){
							seg++;
						}else{
							seg = 0;
							min++;
							if(min==60){
								h++;
							}
						}
						totalSeg++;
						str = "";
						if(h<10&&min<10&&seg<10){
							str = "0"+h+":0"+min+":0"+seg;
						}else if(h<10&&min<10&&seg>=10){
							str = "0"+h+":0"+min+":"+seg;
						}else if(h<10&&min>=10&&seg>=10){
							str = "0"+h+":"+min+":"+seg;
						}else if(h>=10&&min>=10&&seg>=10){
							str = h+":0"+min+":"+seg;
						}
						showTime(str);
					}});
			}
		};

		timer = new Timer();
		timer.schedule(tarea, 1000, 1000);
	}

	public void showTime(String time){
		timeString.setText(time);
	}

	public void MessageBox(String message, int length){
		Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
	}

	public void incializarBotonesCamposTexto(){
		//Se inicializan los botones y se asigna el valor que le corresponde a cada uno

		solucionar = (Button) findViewById(R.id.Button01);
		verificar = (Button) findViewById(R.id.Button02);
		pausaPlay = (Button) findViewById(R.id.Button03);
		restart = (ImageButton) findViewById(R.id.imageButton01);

		text1_1 = (EditText) findViewById(R.id.editText1);
		if(tablero[0][0]!=0){
			text1_1.setText(""+tablero[0][0]);
			text1_1.setEnabled(false);
		}else{
			text1_1.setText("");
		}

		text1_2 = (EditText) findViewById(R.id.editText2);
		if(tablero[0][1]!=0){
			text1_2.setText(""+tablero[0][1]);
			text1_2.setEnabled(false);
		}else{
			text1_2.setText("");
		}

		text1_3 = (EditText) findViewById(R.id.editText3);
		if(tablero[0][2]!=0){
			text1_3.setText(""+tablero[0][2]);
			text1_3.setEnabled(false);
		}else{
			text1_3.setText("");
		}

		text1_4 = (EditText) findViewById(R.id.editText4);
		if(tablero[0][3]!=0){
			text1_4.setText(""+tablero[0][3]);
			text1_4.setEnabled(false);
		}else{
			text1_4.setText("");
		}

		text1_5 = (EditText) findViewById(R.id.editText5);
		if(tablero[0][4]!=0){
			text1_5.setText(""+tablero[0][4]);
			text1_5.setEnabled(false);
		}else{
			text1_5.setText("");
		}

		text1_6 = (EditText) findViewById(R.id.editText6);
		if(tablero[0][5]!=0){
			text1_6.setText(""+tablero[0][5]);
			text1_6.setEnabled(false);
		}else{
			text1_6.setText("");
		}

		text1_7 = (EditText) findViewById(R.id.editText7);
		if(tablero[0][6]!=0){
			text1_7.setText(""+tablero[0][6]);
			text1_7.setEnabled(false);
		}else{
			text1_7.setText("");
		}

		text1_8 = (EditText) findViewById(R.id.editText8);
		if(tablero[0][7]!=0){
			text1_8.setText(""+tablero[0][7]);
			text1_8.setEnabled(false);
		}else{
			text1_8.setText("");
		}

		text1_9 = (EditText) findViewById(R.id.editText9);
		if(tablero[0][8]!=0){
			text1_9.setText(""+tablero[0][8]);
			text1_9.setEnabled(false);
		}else{
			text1_9.setText("");
		}

		text2_1 = (EditText) findViewById(R.id.editText10);
		if(tablero[1][0]!=0){
			text2_1.setText(""+tablero[1][0]);
			text2_1.setEnabled(false);
		}else{
			text2_1.setText("");
		}

		text2_2 = (EditText) findViewById(R.id.editText11);
		if(tablero[1][1]!=0){
			text2_2.setText(""+tablero[1][1]);
			text2_2.setEnabled(false);
		}else{
			text2_2.setText("");
		}

		text2_3 = (EditText) findViewById(R.id.editText12);
		if(tablero[1][2]!=0){
			text2_3.setText(""+tablero[1][2]);
			text2_3.setEnabled(false);
		}else{
			text2_3.setText("");
		}

		text2_4 = (EditText) findViewById(R.id.editText13);
		if(tablero[1][3]!=0){
			text2_4.setText(""+tablero[1][3]);
			text2_4.setEnabled(false);
		}else{
			text2_4.setText("");
		}

		text2_5 = (EditText) findViewById(R.id.editText14);
		if(tablero[1][4]!=0){
			text2_5.setText(""+tablero[1][4]);
			text2_5.setEnabled(false);
		}else{
			text2_5.setText("");
		}

		text2_6 = (EditText) findViewById(R.id.editText15);
		if(tablero[1][5]!=0){
			text2_6.setText(""+tablero[1][5]);
			text2_6.setEnabled(false);
		}else{
			text2_6.setText("");
		}

		text2_7 = (EditText) findViewById(R.id.editText16);
		if(tablero[1][6]!=0){
			text2_7.setText(""+tablero[1][6]);
			text2_7.setEnabled(false);
		}else{
			text2_7.setText("");
		}

		text2_8 = (EditText) findViewById(R.id.editText17);
		if(tablero[1][7]!=0){
			text2_8.setText(""+tablero[1][7]);
			text2_8.setEnabled(false);
		}else{
			text2_8.setText("");
		}

		text2_9 = (EditText) findViewById(R.id.editText18);
		if(tablero[1][8]!=0){
			text2_9.setText(""+tablero[1][8]);
			text2_9.setEnabled(false);
		}else{
			text2_9.setText("");
		}

		text3_1 = (EditText) findViewById(R.id.editText19);
		if(tablero[2][0]!=0){
			text3_1.setText(""+tablero[2][0]);
			text3_1.setEnabled(false);
		}else{
			text3_1.setText("");
		}

		text3_2 = (EditText) findViewById(R.id.editText20);
		if(tablero[2][1]!=0){
			text3_2.setText(""+tablero[2][1]);
			text3_2.setEnabled(false);
		}else{
			text3_2.setText("");
		}

		text3_3 = (EditText) findViewById(R.id.editText21);
		if(tablero[2][2]!=0){
			text3_3.setText(""+tablero[2][2]);
			text3_3.setEnabled(false);
		}else{
			text3_3.setText("");
		}

		text3_4 = (EditText) findViewById(R.id.editText22);
		if(tablero[2][3]!=0){
			text3_4.setText(""+tablero[2][3]);
			text3_4.setEnabled(false);
		}else{
			text3_4.setText("");
		}

		text3_5 = (EditText) findViewById(R.id.editText23);
		if(tablero[2][4]!=0){
			text3_5.setText(""+tablero[2][4]);
			text3_5.setEnabled(false);
		}else{
			text3_5.setText("");
		}

		text3_6 = (EditText) findViewById(R.id.editText24);
		if(tablero[2][5]!=0){
			text3_6.setText(""+tablero[2][5]);
			text3_6.setEnabled(false);
		}else{
			text3_6.setText("");
		}

		text3_7 = (EditText) findViewById(R.id.editText25);
		if(tablero[2][6]!=0){
			text3_7.setText(""+tablero[2][6]);
			text3_7.setEnabled(false);
		}else{
			text3_7.setText("");
		}

		text3_8 = (EditText) findViewById(R.id.editText26);
		if(tablero[2][7]!=0){
			text3_8.setText(""+tablero[2][7]);
			text3_8.setEnabled(false);
		}else{
			text3_8.setText("");
		}

		text3_9 = (EditText) findViewById(R.id.editText27);
		if(tablero[2][8]!=0){
			text3_9.setText(""+tablero[2][8]);
			text3_9.setEnabled(false);
		}else{
			text3_9.setText("");
		}

		text4_1 = (EditText) findViewById(R.id.editText28);
		if(tablero[3][0]!=0){
			text4_1.setText(""+tablero[3][0]);
			text4_1.setEnabled(false);
		}else{
			text4_1.setText("");
		}

		text4_2 = (EditText) findViewById(R.id.editText29);
		if(tablero[3][1]!=0){
			text4_2.setText(""+tablero[3][1]);
			text4_2.setEnabled(false);
		}else{
			text4_2.setText("");
		}

		text4_3 = (EditText) findViewById(R.id.editText30);
		if(tablero[3][2]!=0){
			text4_3.setText(""+tablero[3][2]);
			text4_3.setEnabled(false);
		}else{
			text4_3.setText("");
		}

		text4_4 = (EditText) findViewById(R.id.editText31);
		if(tablero[3][3]!=0){
			text4_4.setText(""+tablero[3][3]);
			text4_4.setEnabled(false);
		}else{
			text4_4.setText("");
		}

		text4_5 = (EditText) findViewById(R.id.editText32);
		if(tablero[3][4]!=0){
			text4_5.setText(""+tablero[3][4]);
			text4_5.setEnabled(false);
		}else{
			text4_5.setText("");
		}

		text4_6 = (EditText) findViewById(R.id.editText33);
		if(tablero[3][5]!=0){
			text4_6.setText(""+tablero[3][5]);
			text4_6.setEnabled(false);
		}else{
			text4_6.setText("");
		}

		text4_7 = (EditText) findViewById(R.id.editText34);
		if(tablero[3][6]!=0){
			text4_7.setText(""+tablero[3][6]);
			text4_7.setEnabled(false);
		}else{
			text4_7.setText("");
		}

		text4_8 = (EditText) findViewById(R.id.editText35);
		if(tablero[3][7]!=0){
			text4_8.setText(""+tablero[3][7]);
			text4_8.setEnabled(false);
		}else{
			text4_8.setText("");
		}

		text4_9 = (EditText) findViewById(R.id.editText36);
		if(tablero[3][8]!=0){
			text4_9.setText(""+tablero[3][8]);
			text4_9.setEnabled(false);
		}else{
			text4_9.setText("");
		}

		text5_1 = (EditText) findViewById(R.id.editText37);
		if(tablero[4][0]!=0){
			text5_1.setText(""+tablero[4][0]);
			text5_1.setEnabled(false);
		}else{
			text5_1.setText("");
		}

		text5_2 = (EditText) findViewById(R.id.editText38);
		if(tablero[4][1]!=0){
			text5_2.setText(""+tablero[4][1]);
			text5_2.setEnabled(false);
		}else{
			text5_2.setText("");
		}

		text5_3 = (EditText) findViewById(R.id.editText39);
		if(tablero[4][2]!=0){
			text5_3.setText(""+tablero[4][2]);
			text5_3.setEnabled(false);
		}else{
			text5_3.setText("");
		}

		text5_4 = (EditText) findViewById(R.id.editText40);
		if(tablero[4][3]!=0){
			text5_4.setText(""+tablero[4][3]);
			text5_4.setEnabled(false);
		}else{
			text5_4.setText("");
		}

		text5_5 = (EditText) findViewById(R.id.editText41);
		if(tablero[4][4]!=0){
			text5_5.setText(""+tablero[4][4]);
			text5_5.setEnabled(false);
		}else{
			text5_5.setText("");
		}

		text5_6 = (EditText) findViewById(R.id.editText42);
		if(tablero[4][5]!=0){
			text5_6.setText(""+tablero[4][5]);
			text5_6.setEnabled(false);
		}else{
			text5_6.setText("");
		}

		text5_7 = (EditText) findViewById(R.id.editText43);
		if(tablero[4][6]!=0){
			text5_7.setText(""+tablero[4][6]);
			text5_7.setEnabled(false);
		}else{
			text5_7.setText("");
		}

		text5_8 = (EditText) findViewById(R.id.editText44);
		if(tablero[4][7]!=0){
			text5_8.setText(""+tablero[4][7]);
			text5_8.setEnabled(false);
		}else{
			text5_8.setText("");
		}

		text5_9 = (EditText) findViewById(R.id.editText45);
		if(tablero[4][8]!=0){
			text5_9.setText(""+tablero[4][8]);
			text5_9.setEnabled(false);
		}else{
			text5_9.setText("");
		}

		text6_1 = (EditText) findViewById(R.id.editText46);
		if(tablero[5][0]!=0){
			text6_1.setText(""+tablero[5][0]);
			text6_1.setEnabled(false);
		}else{
			text6_1.setText("");
		}

		text6_2 = (EditText) findViewById(R.id.editText47);
		if(tablero[5][1]!=0){
			text6_2.setText(""+tablero[5][1]);
			text6_2.setEnabled(false);
		}else{
			text6_2.setText("");
		}

		text6_3 = (EditText) findViewById(R.id.editText48);
		if(tablero[5][2]!=0){
			text6_3.setText(""+tablero[5][2]);
			text6_3.setEnabled(false);
		}else{
			text6_3.setText("");
		}

		text6_4 = (EditText) findViewById(R.id.editText49);
		if(tablero[5][3]!=0){
			text6_4.setText(""+tablero[5][3]);
			text6_4.setEnabled(false);
		}else{
			text6_4.setText("");
		}

		text6_5 = (EditText) findViewById(R.id.editText50);
		if(tablero[5][4]!=0){
			text6_5.setText(""+tablero[5][4]);
			text6_5.setEnabled(false);
		}else{
			text6_5.setText("");
		}

		text6_6 = (EditText) findViewById(R.id.editText51);
		if(tablero[5][5]!=0){
			text6_6.setText(""+tablero[5][5]);
			text6_6.setEnabled(false);
		}else{
			text6_6.setText("");
		}

		text6_7 = (EditText) findViewById(R.id.editText52);
		if(tablero[5][6]!=0){
			text6_7.setText(""+tablero[5][6]);
			text6_7.setEnabled(false);
		}else{
			text6_7.setText("");
		}

		text6_8 = (EditText) findViewById(R.id.editText53);
		if(tablero[5][7]!=0){
			text6_8.setText(""+tablero[5][7]);
			text6_8.setEnabled(false);
		}else{
			text6_8.setText("");
		}

		text6_9 = (EditText) findViewById(R.id.editText54);
		if(tablero[5][8]!=0){
			text6_9.setText(""+tablero[5][8]);
			text6_9.setEnabled(false);
		}else{
			text6_9.setText("");
		}

		text7_1 = (EditText) findViewById(R.id.editText55);
		if(tablero[6][0]!=0){
			text7_1.setText(""+tablero[6][0]);
			text7_1.setEnabled(false);
		}else{
			text7_1.setText("");
		}

		text7_2 = (EditText) findViewById(R.id.editText56);
		if(tablero[6][1]!=0){
			text7_2.setText(""+tablero[6][1]);
			text7_2.setEnabled(false);
		}else{
			text7_2.setText("");
		}

		text7_3 = (EditText) findViewById(R.id.editText57);
		if(tablero[6][2]!=0){
			text7_3.setText(""+tablero[6][2]);
			text7_3.setEnabled(false);
		}else{
			text7_3.setText("");
		}

		text7_4 = (EditText) findViewById(R.id.editText58);
		if(tablero[6][3]!=0){
			text7_4.setText(""+tablero[6][3]);
			text7_4.setEnabled(false);
		}else{
			text7_4.setText("");
		}

		text7_5 = (EditText) findViewById(R.id.editText59);
		if(tablero[6][4]!=0){
			text7_5.setText(""+tablero[6][4]);
			text7_5.setEnabled(false);
		}else{
			text7_5.setText("");
		}

		text7_6 = (EditText) findViewById(R.id.editText60);
		if(tablero[6][5]!=0){
			text7_6.setText(""+tablero[6][5]);
			text7_6.setEnabled(false);
		}else{
			text7_6.setText("");
		}

		text7_7 = (EditText) findViewById(R.id.editText61);
		if(tablero[6][6]!=0){
			text7_7.setText(""+tablero[6][6]);
			text7_7.setEnabled(false);
		}else{
			text7_7.setText("");
		}

		text7_8 = (EditText) findViewById(R.id.editText62);
		if(tablero[6][7]!=0){
			text7_8.setText(""+tablero[6][7]);
			text7_8.setEnabled(false);
		}else{
			text7_8.setText("");
		}

		text7_9 = (EditText) findViewById(R.id.editText63);
		if(tablero[6][8]!=0){
			text7_9.setText(""+tablero[6][8]);
			text7_9.setEnabled(false);
		}else{
			text7_9.setText("");
		}

		text8_1 = (EditText) findViewById(R.id.editText64);
		if(tablero[7][0]!=0){
			text8_1.setText(""+tablero[7][0]);
			text8_1.setEnabled(false);
		}else{
			text8_1.setText("");
		}

		text8_2 = (EditText) findViewById(R.id.editText65);
		if(tablero[7][1]!=0){
			text8_2.setText(""+tablero[7][1]);
			text8_2.setEnabled(false);
		}else{
			text8_2.setText("");
		}

		text8_3 = (EditText) findViewById(R.id.editText66);
		if(tablero[7][2]!=0){
			text8_3.setText(""+tablero[7][2]);
			text8_3.setEnabled(false);
		}else{
			text8_3.setText("");
		}

		text8_4 = (EditText) findViewById(R.id.editText67);
		if(tablero[7][3]!=0){
			text8_4.setText(""+tablero[7][3]);
			text8_4.setEnabled(false);
		}else{
			text8_4.setText("");
		}

		text8_5 = (EditText) findViewById(R.id.editText68);
		if(tablero[7][4]!=0){
			text8_5.setText(""+tablero[7][4]);
			text8_5.setEnabled(false);
		}else{
			text8_5.setText("");
		}

		text8_6 = (EditText) findViewById(R.id.editText69);
		if(tablero[7][5]!=0){
			text8_6.setText(""+tablero[7][5]);
			text8_6.setEnabled(false);
		}else{
			text8_6.setText("");
		}

		text8_7 = (EditText) findViewById(R.id.editText70);
		if(tablero[7][6]!=0){
			text8_7.setText(""+tablero[7][6]);
			text8_7.setEnabled(false);
		}else{
			text8_7.setText("");
		}

		text8_8 = (EditText) findViewById(R.id.editText71);
		if(tablero[7][7]!=0){
			text8_8.setText(""+tablero[7][7]);
			text8_8.setEnabled(false);
		}else{
			text8_8.setText("");
		}

		text8_9 = (EditText) findViewById(R.id.editText72);
		if(tablero[7][8]!=0){
			text8_9.setText(""+tablero[7][8]);
			text8_9.setEnabled(false);
		}else{
			text8_9.setText("");
		}

		text9_1 = (EditText) findViewById(R.id.editText73);
		if(tablero[8][0]!=0){
			text9_1.setText(""+tablero[8][0]);
			text9_1.setEnabled(false);
		}else{
			text9_1.setText("");
		}

		text9_2 = (EditText) findViewById(R.id.editText74);
		if(tablero[8][1]!=0){
			text9_2.setText(""+tablero[8][1]);
			text9_2.setEnabled(false);
		}else{
			text9_2.setText("");
		}

		text9_3 = (EditText) findViewById(R.id.editText75);
		if(tablero[8][2]!=0){
			text9_3.setText(""+tablero[8][2]);
			text9_3.setEnabled(false);
		}else{
			text9_3.setText("");
		}

		text9_4 = (EditText) findViewById(R.id.editText76);
		if(tablero[8][3]!=0){
			text9_4.setText(""+tablero[8][3]);
			text9_4.setEnabled(false);
		}else{
			text9_4.setText("");
		}

		text9_5 = (EditText) findViewById(R.id.editText77);
		if(tablero[8][4]!=0){
			text9_5.setText(""+tablero[8][4]);
			text9_5.setEnabled(false);
		}else{
			text9_5.setText("");
		}

		text9_6 = (EditText) findViewById(R.id.editText78);
		if(tablero[8][5]!=0){
			text9_6.setText(""+tablero[8][5]);
			text9_6.setEnabled(false);
		}else{
			text9_6.setText("");
		}

		text9_7 = (EditText) findViewById(R.id.editText79);
		if(tablero[8][6]!=0){
			text9_7.setText(""+tablero[8][6]);
			text9_7.setEnabled(false);
		}else{
			text9_7.setText("");
		}

		text9_8 = (EditText) findViewById(R.id.editText80);
		if(tablero[8][7]!=0){
			text9_8.setText(""+tablero[8][7]);
			text9_8.setEnabled(false);
		}else{
			text9_8.setText("");
		}

		text9_9 = (EditText) findViewById(R.id.editText81);
		if(tablero[8][8]!=0){
			text9_9.setText(""+tablero[8][8]);
			text9_9.setEnabled(false);
		}else{
			text9_9.setText("");
		}
	}

	public void ponerResuelto(){
		text1_1 = (EditText) findViewById(R.id.editText1);
		if(tableroResuelto[0][0]!=0){
			text1_1.setText(""+tableroResuelto[0][0]);
			text1_1.setEnabled(false);
		}else{
			text1_1.setText("");
		}

		text1_2 = (EditText) findViewById(R.id.editText2);
		if(tableroResuelto[0][1]!=0){
			text1_2.setText(""+tablero[0][1]);
			text1_2.setEnabled(false);
		}else{
			text1_2.setText("");
		}

		text1_3 = (EditText) findViewById(R.id.editText3);
		if(tablero[0][2]!=0){
			text1_3.setText(""+tablero[0][2]);
			text1_3.setEnabled(false);
		}else{
			text1_3.setText("");
		}

		text1_4 = (EditText) findViewById(R.id.editText4);
		if(tablero[0][3]!=0){
			text1_4.setText(""+tablero[0][3]);
			text1_4.setEnabled(false);
		}else{
			text1_4.setText("");
		}

		text1_5 = (EditText) findViewById(R.id.editText5);
		if(tablero[0][4]!=0){
			text1_5.setText(""+tablero[0][4]);
			text1_5.setEnabled(false);
		}else{
			text1_5.setText("");
		}

		text1_6 = (EditText) findViewById(R.id.editText6);
		if(tablero[0][5]!=0){
			text1_6.setText(""+tablero[0][5]);
			text1_6.setEnabled(false);
		}else{
			text1_6.setText("");
		}

		text1_7 = (EditText) findViewById(R.id.editText7);
		if(tablero[0][6]!=0){
			text1_7.setText(""+tablero[0][6]);
			text1_7.setEnabled(false);
		}else{
			text1_7.setText("");
		}

		text1_8 = (EditText) findViewById(R.id.editText8);
		if(tablero[0][7]!=0){
			text1_8.setText(""+tablero[0][7]);
			text1_8.setEnabled(false);
		}else{
			text1_8.setText("");
		}

		text1_9 = (EditText) findViewById(R.id.editText9);
		if(tablero[0][8]!=0){
			text1_9.setText(""+tablero[0][8]);
			text1_9.setEnabled(false);
		}else{
			text1_9.setText("");
		}

		text2_1 = (EditText) findViewById(R.id.editText10);
		if(tablero[1][0]!=0){
			text2_1.setText(""+tablero[1][0]);
			text2_1.setEnabled(false);
		}else{
			text2_1.setText("");
		}

		text2_2 = (EditText) findViewById(R.id.editText11);
		if(tablero[1][1]!=0){
			text2_2.setText(""+tablero[1][1]);
			text2_2.setEnabled(false);
		}else{
			text2_2.setText("");
		}

		text2_3 = (EditText) findViewById(R.id.editText12);
		if(tablero[1][2]!=0){
			text2_3.setText(""+tablero[1][2]);
			text2_3.setEnabled(false);
		}else{
			text2_3.setText("");
		}

		text2_4 = (EditText) findViewById(R.id.editText13);
		if(tablero[1][3]!=0){
			text2_4.setText(""+tablero[1][3]);
			text2_4.setEnabled(false);
		}else{
			text2_4.setText("");
		}

		text2_5 = (EditText) findViewById(R.id.editText14);
		if(tablero[1][4]!=0){
			text2_5.setText(""+tablero[1][4]);
			text2_5.setEnabled(false);
		}else{
			text2_5.setText("");
		}

		text2_6 = (EditText) findViewById(R.id.editText15);
		if(tablero[1][5]!=0){
			text2_6.setText(""+tablero[1][5]);
			text2_6.setEnabled(false);
		}else{
			text2_6.setText("");
		}

		text2_7 = (EditText) findViewById(R.id.editText16);
		if(tablero[1][6]!=0){
			text2_7.setText(""+tablero[1][6]);
			text2_7.setEnabled(false);
		}else{
			text2_7.setText("");
		}

		text2_8 = (EditText) findViewById(R.id.editText17);
		if(tablero[1][7]!=0){
			text2_8.setText(""+tablero[1][7]);
			text2_8.setEnabled(false);
		}else{
			text2_8.setText("");
		}

		text2_9 = (EditText) findViewById(R.id.editText18);
		if(tablero[1][8]!=0){
			text2_9.setText(""+tablero[1][8]);
			text2_9.setEnabled(false);
		}else{
			text2_9.setText("");
		}

		text3_1 = (EditText) findViewById(R.id.editText19);
		if(tablero[2][0]!=0){
			text3_1.setText(""+tablero[2][0]);
			text3_1.setEnabled(false);
		}else{
			text3_1.setText("");
		}

		text3_2 = (EditText) findViewById(R.id.editText20);
		if(tablero[2][1]!=0){
			text3_2.setText(""+tablero[2][1]);
			text3_2.setEnabled(false);
		}else{
			text3_2.setText("");
		}

		text3_3 = (EditText) findViewById(R.id.editText21);
		if(tablero[2][2]!=0){
			text3_3.setText(""+tablero[2][2]);
			text3_3.setEnabled(false);
		}else{
			text3_3.setText("");
		}

		text3_4 = (EditText) findViewById(R.id.editText22);
		if(tablero[2][3]!=0){
			text3_4.setText(""+tablero[2][3]);
			text3_4.setEnabled(false);
		}else{
			text3_4.setText("");
		}

		text3_5 = (EditText) findViewById(R.id.editText23);
		if(tablero[2][4]!=0){
			text3_5.setText(""+tablero[2][4]);
			text3_5.setEnabled(false);
		}else{
			text3_5.setText("");
		}

		text3_6 = (EditText) findViewById(R.id.editText24);
		if(tablero[2][5]!=0){
			text3_6.setText(""+tablero[2][5]);
			text3_6.setEnabled(false);
		}else{
			text3_6.setText("");
		}

		text3_7 = (EditText) findViewById(R.id.editText25);
		if(tablero[2][6]!=0){
			text3_7.setText(""+tablero[2][6]);
			text3_7.setEnabled(false);
		}else{
			text3_7.setText("");
		}

		text3_8 = (EditText) findViewById(R.id.editText26);
		if(tablero[2][7]!=0){
			text3_8.setText(""+tablero[2][7]);
			text3_8.setEnabled(false);
		}else{
			text3_8.setText("");
		}

		text3_9 = (EditText) findViewById(R.id.editText27);
		if(tablero[2][8]!=0){
			text3_9.setText(""+tablero[2][8]);
			text3_9.setEnabled(false);
		}else{
			text3_9.setText("");
		}

		text4_1 = (EditText) findViewById(R.id.editText28);
		if(tablero[3][0]!=0){
			text4_1.setText(""+tablero[3][0]);
			text4_1.setEnabled(false);
		}else{
			text4_1.setText("");
		}

		text4_2 = (EditText) findViewById(R.id.editText29);
		if(tablero[3][1]!=0){
			text4_2.setText(""+tablero[3][1]);
			text4_2.setEnabled(false);
		}else{
			text4_2.setText("");
		}

		text4_3 = (EditText) findViewById(R.id.editText30);
		if(tablero[3][2]!=0){
			text4_3.setText(""+tablero[3][2]);
			text4_3.setEnabled(false);
		}else{
			text4_3.setText("");
		}

		text4_4 = (EditText) findViewById(R.id.editText31);
		if(tablero[3][3]!=0){
			text4_4.setText(""+tablero[3][3]);
			text4_4.setEnabled(false);
		}else{
			text4_4.setText("");
		}

		text4_5 = (EditText) findViewById(R.id.editText32);
		if(tablero[3][4]!=0){
			text4_5.setText(""+tablero[3][4]);
			text4_5.setEnabled(false);
		}else{
			text4_5.setText("");
		}

		text4_6 = (EditText) findViewById(R.id.editText33);
		if(tablero[3][5]!=0){
			text4_6.setText(""+tablero[3][5]);
			text4_6.setEnabled(false);
		}else{
			text4_6.setText("");
		}

		text4_7 = (EditText) findViewById(R.id.editText34);
		if(tablero[3][6]!=0){
			text4_7.setText(""+tablero[3][6]);
			text4_7.setEnabled(false);
		}else{
			text4_7.setText("");
		}

		text4_8 = (EditText) findViewById(R.id.editText35);
		if(tablero[3][7]!=0){
			text4_8.setText(""+tablero[3][7]);
			text4_8.setEnabled(false);
		}else{
			text4_8.setText("");
		}

		text4_9 = (EditText) findViewById(R.id.editText36);
		if(tablero[3][8]!=0){
			text4_9.setText(""+tablero[3][8]);
			text4_9.setEnabled(false);
		}else{
			text4_9.setText("");
		}

		text5_1 = (EditText) findViewById(R.id.editText37);
		if(tablero[4][0]!=0){
			text5_1.setText(""+tablero[4][0]);
			text5_1.setEnabled(false);
		}else{
			text5_1.setText("");
		}

		text5_2 = (EditText) findViewById(R.id.editText38);
		if(tablero[4][1]!=0){
			text5_2.setText(""+tablero[4][1]);
			text5_2.setEnabled(false);
		}else{
			text5_2.setText("");
		}

		text5_3 = (EditText) findViewById(R.id.editText39);
		if(tablero[4][2]!=0){
			text5_3.setText(""+tablero[4][2]);
			text5_3.setEnabled(false);
		}else{
			text5_3.setText("");
		}

		text5_4 = (EditText) findViewById(R.id.editText40);
		if(tablero[4][3]!=0){
			text5_4.setText(""+tablero[4][3]);
			text5_4.setEnabled(false);
		}else{
			text5_4.setText("");
		}

		text5_5 = (EditText) findViewById(R.id.editText41);
		if(tablero[4][4]!=0){
			text5_5.setText(""+tablero[4][4]);
			text5_5.setEnabled(false);
		}else{
			text5_5.setText("");
		}

		text5_6 = (EditText) findViewById(R.id.editText42);
		if(tablero[4][5]!=0){
			text5_6.setText(""+tablero[4][5]);
			text5_6.setEnabled(false);
		}else{
			text5_6.setText("");
		}

		text5_7 = (EditText) findViewById(R.id.editText43);
		if(tablero[4][6]!=0){
			text5_7.setText(""+tablero[4][6]);
			text5_7.setEnabled(false);
		}else{
			text5_7.setText("");
		}

		text5_8 = (EditText) findViewById(R.id.editText44);
		if(tablero[4][7]!=0){
			text5_8.setText(""+tablero[4][7]);
			text5_8.setEnabled(false);
		}else{
			text5_8.setText("");
		}

		text5_9 = (EditText) findViewById(R.id.editText45);
		if(tablero[4][8]!=0){
			text5_9.setText(""+tablero[4][8]);
			text5_9.setEnabled(false);
		}else{
			text5_9.setText("");
		}

		text6_1 = (EditText) findViewById(R.id.editText46);
		if(tablero[5][0]!=0){
			text6_1.setText(""+tablero[5][0]);
			text6_1.setEnabled(false);
		}else{
			text6_1.setText("");
		}

		text6_2 = (EditText) findViewById(R.id.editText47);
		if(tablero[5][1]!=0){
			text6_2.setText(""+tablero[5][1]);
			text6_2.setEnabled(false);
		}else{
			text6_2.setText("");
		}

		text6_3 = (EditText) findViewById(R.id.editText48);
		if(tablero[5][2]!=0){
			text6_3.setText(""+tablero[5][2]);
			text6_3.setEnabled(false);
		}else{
			text6_3.setText("");
		}

		text6_4 = (EditText) findViewById(R.id.editText49);
		if(tablero[5][3]!=0){
			text6_4.setText(""+tablero[5][3]);
			text6_4.setEnabled(false);
		}else{
			text6_4.setText("");
		}

		text6_5 = (EditText) findViewById(R.id.editText50);
		if(tablero[5][4]!=0){
			text6_5.setText(""+tablero[5][4]);
			text6_5.setEnabled(false);
		}else{
			text6_5.setText("");
		}

		text6_6 = (EditText) findViewById(R.id.editText51);
		if(tablero[5][5]!=0){
			text6_6.setText(""+tablero[5][5]);
			text6_6.setEnabled(false);
		}else{
			text6_6.setText("");
		}

		text6_7 = (EditText) findViewById(R.id.editText52);
		if(tablero[5][6]!=0){
			text6_7.setText(""+tablero[5][6]);
			text6_7.setEnabled(false);
		}else{
			text6_7.setText("");
		}

		text6_8 = (EditText) findViewById(R.id.editText53);
		if(tablero[5][7]!=0){
			text6_8.setText(""+tablero[5][7]);
			text6_8.setEnabled(false);
		}else{
			text6_8.setText("");
		}

		text6_9 = (EditText) findViewById(R.id.editText54);
		if(tablero[5][8]!=0){
			text6_9.setText(""+tablero[5][8]);
			text6_9.setEnabled(false);
		}else{
			text6_9.setText("");
		}

		text7_1 = (EditText) findViewById(R.id.editText55);
		if(tablero[6][0]!=0){
			text7_1.setText(""+tablero[6][0]);
			text7_1.setEnabled(false);
		}else{
			text7_1.setText("");
		}

		text7_2 = (EditText) findViewById(R.id.editText56);
		if(tablero[6][1]!=0){
			text7_2.setText(""+tablero[6][1]);
			text7_2.setEnabled(false);
		}else{
			text7_2.setText("");
		}

		text7_3 = (EditText) findViewById(R.id.editText57);
		if(tablero[6][2]!=0){
			text7_3.setText(""+tablero[6][2]);
			text7_3.setEnabled(false);
		}else{
			text7_3.setText("");
		}

		text7_4 = (EditText) findViewById(R.id.editText58);
		if(tablero[6][3]!=0){
			text7_4.setText(""+tablero[6][3]);
			text7_4.setEnabled(false);
		}else{
			text7_4.setText("");
		}

		text7_5 = (EditText) findViewById(R.id.editText59);
		if(tablero[6][4]!=0){
			text7_5.setText(""+tablero[6][4]);
			text7_5.setEnabled(false);
		}else{
			text7_5.setText("");
		}

		text7_6 = (EditText) findViewById(R.id.editText60);
		if(tablero[6][5]!=0){
			text7_6.setText(""+tablero[6][5]);
			text7_6.setEnabled(false);
		}else{
			text7_6.setText("");
		}

		text7_7 = (EditText) findViewById(R.id.editText61);
		if(tablero[6][6]!=0){
			text7_7.setText(""+tablero[6][6]);
			text7_7.setEnabled(false);
		}else{
			text7_7.setText("");
		}

		text7_8 = (EditText) findViewById(R.id.editText62);
		if(tablero[6][7]!=0){
			text7_8.setText(""+tablero[6][7]);
			text7_8.setEnabled(false);
		}else{
			text7_8.setText("");
		}

		text7_9 = (EditText) findViewById(R.id.editText63);
		if(tablero[6][8]!=0){
			text7_9.setText(""+tablero[6][8]);
			text7_9.setEnabled(false);
		}else{
			text7_9.setText("");
		}

		text8_1 = (EditText) findViewById(R.id.editText64);
		if(tablero[7][0]!=0){
			text8_1.setText(""+tablero[7][0]);
			text8_1.setEnabled(false);
		}else{
			text8_1.setText("");
		}

		text8_2 = (EditText) findViewById(R.id.editText65);
		if(tablero[7][1]!=0){
			text8_2.setText(""+tablero[7][1]);
			text8_2.setEnabled(false);
		}else{
			text8_2.setText("");
		}

		text8_3 = (EditText) findViewById(R.id.editText66);
		if(tablero[7][2]!=0){
			text8_3.setText(""+tablero[7][2]);
			text8_3.setEnabled(false);
		}else{
			text8_3.setText("");
		}

		text8_4 = (EditText) findViewById(R.id.editText67);
		if(tablero[7][3]!=0){
			text8_4.setText(""+tablero[7][3]);
			text8_4.setEnabled(false);
		}else{
			text8_4.setText("");
		}

		text8_5 = (EditText) findViewById(R.id.editText68);
		if(tablero[7][4]!=0){
			text8_5.setText(""+tablero[7][4]);
			text8_5.setEnabled(false);
		}else{
			text8_5.setText("");
		}

		text8_6 = (EditText) findViewById(R.id.editText69);
		if(tablero[7][5]!=0){
			text8_6.setText(""+tablero[7][5]);
			text8_6.setEnabled(false);
		}else{
			text8_6.setText("");
		}

		text8_7 = (EditText) findViewById(R.id.editText70);
		if(tablero[7][6]!=0){
			text8_7.setText(""+tablero[7][6]);
			text8_7.setEnabled(false);
		}else{
			text8_7.setText("");
		}

		text8_8 = (EditText) findViewById(R.id.editText71);
		if(tablero[7][7]!=0){
			text8_8.setText(""+tablero[7][7]);
			text8_8.setEnabled(false);
		}else{
			text8_8.setText("");
		}

		text8_9 = (EditText) findViewById(R.id.editText72);
		if(tablero[7][8]!=0){
			text8_9.setText(""+tablero[7][8]);
			text8_9.setEnabled(false);
		}else{
			text8_9.setText("");
		}

		text9_1 = (EditText) findViewById(R.id.editText73);
		if(tablero[8][0]!=0){
			text9_1.setText(""+tablero[8][0]);
			text9_1.setEnabled(false);
		}else{
			text9_1.setText("");
		}

		text9_2 = (EditText) findViewById(R.id.editText74);
		if(tablero[8][1]!=0){
			text9_2.setText(""+tablero[8][1]);
			text9_2.setEnabled(false);
		}else{
			text9_2.setText("");
		}

		text9_3 = (EditText) findViewById(R.id.editText75);
		if(tablero[8][2]!=0){
			text9_3.setText(""+tablero[8][2]);
			text9_3.setEnabled(false);
		}else{
			text9_3.setText("");
		}

		text9_4 = (EditText) findViewById(R.id.editText76);
		if(tablero[8][3]!=0){
			text9_4.setText(""+tablero[8][3]);
			text9_4.setEnabled(false);
		}else{
			text9_4.setText("");
		}

		text9_5 = (EditText) findViewById(R.id.editText77);
		if(tablero[8][4]!=0){
			text9_5.setText(""+tablero[8][4]);
			text9_5.setEnabled(false);
		}else{
			text9_5.setText("");
		}

		text9_6 = (EditText) findViewById(R.id.editText78);
		if(tablero[8][5]!=0){
			text9_6.setText(""+tablero[8][5]);
			text9_6.setEnabled(false);
		}else{
			text9_6.setText("");
		}

		text9_7 = (EditText) findViewById(R.id.editText79);
		if(tablero[8][6]!=0){
			text9_7.setText(""+tablero[8][6]);
			text9_7.setEnabled(false);
		}else{
			text9_7.setText("");
		}

		text9_8 = (EditText) findViewById(R.id.editText80);
		if(tablero[8][7]!=0){
			text9_8.setText(""+tablero[8][7]);
			text9_8.setEnabled(false);
		}else{
			text9_8.setText("");
		}

		text9_9 = (EditText) findViewById(R.id.editText81);
		if(tablero[8][8]!=0){
			text9_9.setText(""+tablero[8][8]);
			text9_9.setEnabled(false);
		}else{
			text9_9.setText("");
		}
	}

}