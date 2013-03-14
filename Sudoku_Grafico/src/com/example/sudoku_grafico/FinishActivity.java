package com.example.sudoku_grafico;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

public class FinishActivity extends Activity{

	private EditText textoFelicitaciones;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finish_activity);
		
		Bundle bundle = getIntent().getExtras();
		String str = bundle.getString("Tiempo");
		int totalSeg = bundle.getInt("Tiempo_int");
		
		String txt = "¡Felicitaciones!\n"+
					 "Has terminado el sudoku del dia\n" +
					 "Te has tardado: "+totalSeg+" seg en resolverlo\n" +
					 "eso es: "+str;
		textoFelicitaciones = (EditText) findViewById(R.id.editText1);
		textoFelicitaciones.setText(txt);
		MessageBox("El tiempo total fué: "+str+"\nEso en segundos es: "+totalSeg+"seg.");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void MessageBox(String message){
		Toast.makeText(this,message,Toast.LENGTH_LONG).show();
	}
}
