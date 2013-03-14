package com.example.sudoku_grafico;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class FinishActivity extends Activity{

	private EditText textoFelicitaciones;
	private Button salir;
	private ImageButton facebook;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finish_activity);
		inicializar();
		eventos();
		
	}

	public void inicializar(){
		
		salir = (Button) findViewById(R.id.button1);
		facebook = (ImageButton) findViewById(R.id.imageButton1);
		
		Bundle bundle = getIntent().getExtras();
		String str = bundle.getString("Tiempo");
		int totalSeg = bundle.getInt("Tiempo_int");
		boolean es = bundle.getBoolean("Validacion");
		
		String txt = "";
		
		if(es){
			txt = "¡Felicitaciones!\n"+
					 "Has terminado el sudoku del dia\n" +
					 "Te has tardado: "+totalSeg+" seg en resolverlo\n" +
					 "eso es: "+str;
		}else{
			txt = "Lo sentimos, la respuesta no es correcta.";
		}
		
		textoFelicitaciones = (EditText) findViewById(R.id.editText1);
		textoFelicitaciones.setText(txt);
		
		MessageBox("El tiempo total fué: "+str+"\nEso en segundos es: "+totalSeg+"seg.");
		
	}
	
	public void eventos(){
		salir.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				System.exit(0);
//				Intent intent = new Intent(Intent.ACTION_MAIN); 
//				finish();
			}
		});
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
