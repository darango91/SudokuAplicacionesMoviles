package com.example.sudoku_grafico;

import java.util.Timer;
import java.util.TimerTask;

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

public class SodukoActivity extends Activity implements OnKeyListener {

	private TextView timeString;
	private Handler handler; 
	private Timer timer;
	private int[][] board = {{8, 0, 0, 0, 0, 0, 0, 0, 6},
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
	private TextView text1_1;
	private int seg = 0, min = 0, h = 0, totalEnSeg = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soduko);

		text1_1 = (TextView) findViewById(R.id.editText1);
//		text1_1.setOnKeyListener();
		handler = new Handler();
		timeString = (TextView) findViewById(R.id.time);

		
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

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		return false;
	}
}