package com.example.sudoku_grafico;

import java.util.Timer;
import java.util.TimerTask;

import com.example.sudoku_grafico.util.Util;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.TextView;

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
	private TextView text1_1, text1_2, text1_3, text1_4, text1_5, text1_6, text1_7, text1_8, text1_9,
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
	
	public void incializarBotones(){
		text1_1 = (TextView) findViewById(R.id.editText1);
		text1_2 = (TextView) findViewById(R.id.editText2);
		text1_3 = (TextView) findViewById(R.id.editText3);
		text1_4 = (TextView) findViewById(R.id.editText4);
		text1_5 = (TextView) findViewById(R.id.editText5);
		text1_6 = (TextView) findViewById(R.id.editText6);
		text1_7 = (TextView) findViewById(R.id.editText7);
		text1_8 = (TextView) findViewById(R.id.editText8);
		text1_9 = (TextView) findViewById(R.id.editText9);
		text2_1 = (TextView) findViewById(R.id.editText10);
		text2_2 = (TextView) findViewById(R.id.editText11);
		text2_3 = (TextView) findViewById(R.id.editText12);
		text2_4 = (TextView) findViewById(R.id.editText13);
		text2_5 = (TextView) findViewById(R.id.editText14);
		text2_6 = (TextView) findViewById(R.id.editText15);
		text2_7 = (TextView) findViewById(R.id.editText16);
		text2_8 = (TextView) findViewById(R.id.editText17);
		text2_9 = (TextView) findViewById(R.id.editText18);
		text3_1 = (TextView) findViewById(R.id.editText19);
		text3_2 = (TextView) findViewById(R.id.editText20);
		text3_3 = (TextView) findViewById(R.id.editText21);
		text3_4 = (TextView) findViewById(R.id.editText22);
		text3_5 = (TextView) findViewById(R.id.editText23);
		text3_6 = (TextView) findViewById(R.id.editText24);
		text3_7 = (TextView) findViewById(R.id.editText25);
		text3_8 = (TextView) findViewById(R.id.editText26);
		text3_9 = (TextView) findViewById(R.id.editText27);
		text4_1 = (TextView) findViewById(R.id.editText28);
		text4_2 = (TextView) findViewById(R.id.editText29);
		text4_3 = (TextView) findViewById(R.id.editText30);
		text4_4 = (TextView) findViewById(R.id.editText31);
		text4_5 = (TextView) findViewById(R.id.editText32);
		text4_6 = (TextView) findViewById(R.id.editText33);
		text4_7 = (TextView) findViewById(R.id.editText34);
		text4_8 = (TextView) findViewById(R.id.editText35);
		text4_9 = (TextView) findViewById(R.id.editText36);
		text5_1 = (TextView) findViewById(R.id.editText37);
		text5_2 = (TextView) findViewById(R.id.editText38);
//		text1_1.setOnKeyListener();
	}
}