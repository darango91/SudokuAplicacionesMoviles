package com.example.sudoku_grafico;

import java.util.Timer;
import java.util.TimerTask;

import com.example.sudoku_grafico.util.Util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SodukoActivity extends Activity{

	private TextView timeString;
	private Handler handler; 
	private Timer timer;
	private int[][] tablero =  {{8, 0, 0, 0, 0, 0, 0, 0, 6},
					            {0, 0, 4, 8, 0, 3, 9, 0, 0},
					            {0, 0, 3, 0, 0, 0, 7, 0, 0},
					            {0, 7, 0, 2, 0, 5, 0, 1, 0},
					            {0, 0, 0, 0, 1, 0, 0, 0, 0},
					            {0, 3, 0, 9, 0, 7, 0, 6, 0},
					            {0, 0, 7, 0, 0, 0, 2, 0, 0},
					            {0, 0, 1, 7, 0, 4, 5, 0, 0},
					            {5, 0, 0, 0, 0, 0, 0, 0, 8}};
	private String sudoku;
	private Button solucionar;
	private Button verificar;
	private EditText text1_1, text1_2, text1_3, text1_4, text1_5, text1_6, text1_7, text1_8, text1_9,
					text2_1, text2_2, text2_3, text2_4, text2_5, text2_6, text2_7, text2_8, text2_9,
					text3_1, text3_2, text3_3, text3_4, text3_5, text3_6, text3_7, text3_8, text3_9,
					text4_1, text4_2, text4_3, text4_4, text4_5, text4_6, text4_7, text4_8, text4_9,
					text5_1, text5_2, text5_3, text5_4, text5_5, text5_6, text5_7, text5_8, text5_9,
					text6_1, text6_2, text6_3, text6_4, text6_5, text6_6, text6_7, text6_8, text6_9,
					text7_1, text7_2, text7_3, text7_4, text7_5, text7_6, text7_7, text7_8, text7_9,
					text8_1, text8_2, text8_3, text8_4, text8_5, text8_6, text8_7, text8_8, text8_9,
					text9_1, text9_2, text9_3, text9_4, text9_5, text9_6, text9_7, text9_8, text9_9;
	private int seg = 0, min = 0, h = 0, totalEnSeg = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soduko);

		handler = new Handler();
		timeString = (TextView) findViewById(R.id.time);

		sudoku = Util.matrizToString(tablero);
		
		incializarBotones();
		
		TimerTask tarea = new TimerTask(){
			@Override
			public void run() {

				handler.post(new Runnable(){
					public void run() {
						totalEnSeg++;
						if(seg<60){
							seg++;
						}else{
							seg = 0;
							min++;
							if(min==60){
								h++;
							}
						}
						String str = h+":"+min+":"+seg;
						showTime(str);
				}});
			}
		};
		
		timer = new Timer();
		timer.schedule(tarea, 0, 1000);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_soduko, menu);
		return true;
	}

	public void eventos(){
		solucionar.setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						//Llama al método de solución de sudokus
					}
				}
			);
		
		verificar.setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						//Llama al método de verificación de sudokus
					}
				}
			);
		
		//Eventos de los botones
		text1_1.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		//int defaultValue = getResources().getInteger(R.string.saved_high_score_default);
		String highScore = sharedPref.getString("key_sudoku", "");
		//texto.setText(highScore);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
	}
	@Override
	protected void onPause() {
		super.onPause();
		
	}
	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString("key_sudoku",sudoku);
		editor.commit();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}

	public void showTime(String time){
		timeString.setText(time);
	}
	
	public void llenarTableroGrafico(){
		int indice = 1;
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero.length; j++) {
				
			}
		}
	}
	
	public void MessageBox(String message){
	    Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
	    //Muestra un mensaje
	}
	
	public void incializarBotones(){
		//Se inicializan los botones y se asigna el valor que le corresponde a cada uno
		
		text1_1 = (EditText) findViewById(R.id.editText1);
		if(tablero[0][0]!=0){
			text1_1.setText(""+tablero[0][0]);
			text1_1.setEnabled(false);
		}

		text1_2 = (EditText) findViewById(R.id.editText2);
		if(tablero[0][1]!=0){
			text1_2.setText(""+tablero[0][1]);
			text1_2.setEnabled(false);
		}
		
		text1_3 = (EditText) findViewById(R.id.editText3);
		if(tablero[0][2]!=0){
			text1_3.setText(""+tablero[0][2]);
			text1_3.setEnabled(false);
		}
		
		text1_4 = (EditText) findViewById(R.id.editText4);
		if(tablero[0][3]!=0){
			text1_4.setText(""+tablero[0][3]);
			text1_4.setEnabled(false);
		}
		
		text1_5 = (EditText) findViewById(R.id.editText5);
		if(tablero[0][4]!=0){
			text1_5.setText(""+tablero[0][4]);
			text1_5.setEnabled(false);
		}
		
		text1_6 = (EditText) findViewById(R.id.editText6);
		if(tablero[0][5]!=0){
			text1_6.setText(""+tablero[0][5]);
			text1_6.setEnabled(false);
		}
		
		text1_7 = (EditText) findViewById(R.id.editText7);
		if(tablero[0][6]!=0){
			text1_7.setText(""+tablero[0][6]);
			text1_7.setEnabled(false);
		}
		
		text1_8 = (EditText) findViewById(R.id.editText8);
		if(tablero[0][7]!=0){
			text1_8.setText(""+tablero[0][7]);
			text1_8.setEnabled(false);
		}
		
		text1_9 = (EditText) findViewById(R.id.editText9);
		if(tablero[0][8]!=0){
			text1_9.setText(""+tablero[0][8]);
			text1_9.setEnabled(false);
		}
		
		text2_1 = (EditText) findViewById(R.id.editText10);
		if(tablero[1][0]!=0){
			text2_1.setText(""+tablero[1][0]);
			text2_1.setEnabled(false);
		}
		
		text2_2 = (EditText) findViewById(R.id.editText11);
		if(tablero[1][1]!=0){
			text2_2.setText(""+tablero[1][1]);
			text2_2.setEnabled(false);
		}
		
		text2_3 = (EditText) findViewById(R.id.editText12);
		if(tablero[1][2]!=0){
			text2_3.setText(""+tablero[1][2]);
			text2_3.setEnabled(false);
		}
		
		text2_4 = (EditText) findViewById(R.id.editText13);
		if(tablero[1][3]!=0){
			text2_4.setText(""+tablero[1][3]);
			text2_4.setEnabled(false);
		}
		
		text2_5 = (EditText) findViewById(R.id.editText14);
		if(tablero[1][4]!=0){
			text2_5.setText(""+tablero[1][4]);
			text2_5.setEnabled(false);
		}
		
		text2_6 = (EditText) findViewById(R.id.editText15);
		if(tablero[1][5]!=0){
			text2_6.setText(""+tablero[1][5]);
			text2_6.setEnabled(false);
		}
		
		text2_7 = (EditText) findViewById(R.id.editText16);
		if(tablero[1][6]!=0){
			text2_7.setText(""+tablero[1][6]);
			text2_7.setEnabled(false);
		}
		
		text2_8 = (EditText) findViewById(R.id.editText17);
		if(tablero[1][7]!=0){
			text2_8.setText(""+tablero[1][7]);
			text2_8.setEnabled(false);
		}
		
		text2_9 = (EditText) findViewById(R.id.editText18);
		if(tablero[1][8]!=0){
			text2_9.setText(""+tablero[1][8]);
			text2_9.setEnabled(false);
		}
		
		text3_1 = (EditText) findViewById(R.id.editText19);
		if(tablero[2][0]!=0){
			text3_1.setText(""+tablero[2][0]);
			text3_1.setEnabled(false);
		}
		
		text3_2 = (EditText) findViewById(R.id.editText20);
		if(tablero[2][1]!=0){
			text3_2.setText(""+tablero[2][1]);
			text3_2.setEnabled(false);
		}
		
		text3_3 = (EditText) findViewById(R.id.editText21);
		if(tablero[2][2]!=0){
			text3_3.setText(""+tablero[2][2]);
			text3_3.setEnabled(false);
		}
		
		text3_4 = (EditText) findViewById(R.id.editText22);
		if(tablero[2][3]!=0){
			text3_4.setText(""+tablero[2][3]);
			text3_4.setEnabled(false);
		}
		
		text3_5 = (EditText) findViewById(R.id.editText23);
		if(tablero[2][4]!=0){
			text3_5.setText(""+tablero[2][4]);
			text3_5.setEnabled(false);
		}
		
		text3_6 = (EditText) findViewById(R.id.editText24);
		if(tablero[2][5]!=0){
			text3_6.setText(""+tablero[2][5]);
			text3_6.setEnabled(false);
		}
		
		text3_7 = (EditText) findViewById(R.id.editText25);
		if(tablero[2][6]!=0){
			text3_7.setText(""+tablero[2][6]);
			text3_7.setEnabled(false);
		}
		
		text3_8 = (EditText) findViewById(R.id.editText26);
		if(tablero[2][7]!=0){
			text3_8.setText(""+tablero[2][7]);
			text3_8.setEnabled(false);
		}
		
		text3_9 = (EditText) findViewById(R.id.editText27);
		if(tablero[2][8]!=0){
			text3_9.setText(""+tablero[2][8]);
			text3_9.setEnabled(false);
		}
		
		text4_1 = (EditText) findViewById(R.id.editText28);
		if(tablero[3][0]!=0){
			text4_1.setText(""+tablero[3][0]);
			text4_1.setEnabled(false);
		}
		
		text4_2 = (EditText) findViewById(R.id.editText29);
		if(tablero[3][1]!=0){
			text4_2.setText(""+tablero[3][1]);
			text4_2.setEnabled(false);
		}
		
		text4_3 = (EditText) findViewById(R.id.editText30);
		if(tablero[3][2]!=0){
			text1_1.setText(""+tablero[3][2]);
			text1_1.setEnabled(false);
		}
		
		text4_4 = (EditText) findViewById(R.id.editText31);
		if(tablero[3][3]!=0){
			text1_1.setText(""+tablero[3][3]);
			text1_1.setEnabled(false);
		}
		
		text4_5 = (EditText) findViewById(R.id.editText32);
		if(tablero[3][4]!=0){
			text1_1.setText(""+tablero[3][4]);
			text1_1.setEnabled(false);
		}
		
		text4_6 = (EditText) findViewById(R.id.editText33);
		if(tablero[3][5]!=0){
			text1_1.setText(""+tablero[3][5]);
			text1_1.setEnabled(false);
		}
		
		text4_7 = (EditText) findViewById(R.id.editText34);
		if(tablero[3][6]!=0){
			text1_1.setText(""+tablero[3][6]);
			text1_1.setEnabled(false);
		}
		
		text4_8 = (EditText) findViewById(R.id.editText35);
		if(tablero[3][7]!=0){
			text1_1.setText(""+tablero[3][7]);
			text1_1.setEnabled(false);
		}
		
		text4_9 = (EditText) findViewById(R.id.editText36);
		if(tablero[3][8]!=0){
			text1_1.setText(""+tablero[3][8]);
			text1_1.setEnabled(false);
		}
		
		text5_1 = (EditText) findViewById(R.id.editText37);
		if(tablero[4][0]!=0){
			text1_1.setText(""+tablero[4][0]);
			text1_1.setEnabled(false);
		}
		//Editar desde acá
		text5_2 = (EditText) findViewById(R.id.editText38);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text5_3 = (EditText) findViewById(R.id.editText39);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text5_4 = (EditText) findViewById(R.id.editText40);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text5_5 = (EditText) findViewById(R.id.editText41);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text5_6 = (EditText) findViewById(R.id.editText42);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text5_7 = (EditText) findViewById(R.id.editText43);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text5_8 = (EditText) findViewById(R.id.editText44);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text5_9 = (EditText) findViewById(R.id.editText45);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text6_1 = (EditText) findViewById(R.id.editText46);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text6_2 = (EditText) findViewById(R.id.editText47);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text6_3 = (EditText) findViewById(R.id.editText48);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text6_4 = (EditText) findViewById(R.id.editText49);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text6_5 = (EditText) findViewById(R.id.editText50);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text6_6 = (EditText) findViewById(R.id.editText51);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text6_7 = (EditText) findViewById(R.id.editText52);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text6_8 = (EditText) findViewById(R.id.editText53);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text6_9 = (EditText) findViewById(R.id.editText54);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text7_1 = (EditText) findViewById(R.id.editText55);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text7_2 = (EditText) findViewById(R.id.editText56);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text7_3 = (EditText) findViewById(R.id.editText57);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text7_4 = (EditText) findViewById(R.id.editText58);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text7_5 = (EditText) findViewById(R.id.editText59);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text7_6 = (EditText) findViewById(R.id.editText60);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text7_7 = (EditText) findViewById(R.id.editText61);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text7_8 = (EditText) findViewById(R.id.editText62);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text7_9 = (EditText) findViewById(R.id.editText63);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text8_1 = (EditText) findViewById(R.id.editText64);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text8_2 = (EditText) findViewById(R.id.editText65);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text8_3 = (EditText) findViewById(R.id.editText66);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text8_4 = (EditText) findViewById(R.id.editText67);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text8_5 = (EditText) findViewById(R.id.editText68);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text8_6 = (EditText) findViewById(R.id.editText69);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text8_7 = (EditText) findViewById(R.id.editText70);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text8_8 = (EditText) findViewById(R.id.editText71);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text8_9 = (EditText) findViewById(R.id.editText72);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text9_1 = (EditText) findViewById(R.id.editText73);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text9_2 = (EditText) findViewById(R.id.editText74);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text9_3 = (EditText) findViewById(R.id.editText75);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text9_4 = (EditText) findViewById(R.id.editText76);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text9_5 = (EditText) findViewById(R.id.editText77);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text9_6 = (EditText) findViewById(R.id.editText78);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text9_7 = (EditText) findViewById(R.id.editText79);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text9_8 = (EditText) findViewById(R.id.editText80);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
		
		text9_9 = (EditText) findViewById(R.id.editText81);
		if(tablero[0][0]!=0){
			text1_1.setText(tablero[0][0]);
			text1_1.setEnabled(false);
		}
	}
}