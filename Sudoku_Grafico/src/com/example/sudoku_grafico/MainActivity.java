package com.example.sudoku_grafico;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button button1;
	private Button button2;
	public int codigo =1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		evento();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private void evento(){
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				intent_sudoku(codigo);
			}
		});
		
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				System.exit(0);
			}
		});
	}
	
	private void intent_sudoku(int codigo){
		Intent abrirSudoku= new Intent(getApplicationContext(), SodukoActivity.class);
		startActivityForResult(abrirSudoku, codigo);
	}
}
