package com.example.sudoku_grafico;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {
	private Button button1;
	private Button button2;
	public int codigo = 1;
	private String sudoku = "", id = "2";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button1 = (Button) findViewById(R.id.Button03);
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
//				if(id.equals("")||id.equals("0")){
//					id = "1";
//				}else if(id.equals("1")){
//					id = "2";
//				}
				new LongRunningGetIO().execute();
			}
		});
		
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				System.exit(0);
			}
		});
	}
	
	private void intent_sudoku(int codigo){
		Intent i= new Intent(getApplicationContext(), SudukoActivity.class);
		i.putExtra("Sudoku", sudoku);
		startActivityForResult(i, codigo);
	}
	
	public void MessageBox(String message, int length){
		Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
	}
	
	//con esta clase se trae el sudoku desde el servidor en widnows azure
	private class LongRunningGetIO extends AsyncTask <Void, Void, String> {
		protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
			InputStream in = entity.getContent();
			StringBuffer out = new StringBuffer();
			int n = 1;
			while (n>0) {
				byte[] b = new byte[4096];
				n =  in.read(b);
				if (n>0) out.append(new String(b, 0, n));
			}
			return out.toString();
		}
		@Override
		protected String doInBackground(Void... params) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpGet httpGet = new HttpGet("http://sudokuapp.azurewebsites.net/api/sudokus/?id="+id);
			String text = null;
			try {
				HttpResponse response = httpClient.execute(httpGet, localContext);
				HttpEntity entity = response.getEntity();
				text = getASCIIContentFromEntity(entity);
			} catch (Exception e) {
				return e.getLocalizedMessage();
			}
			String[] t1 = text.split(":");
			String[] t2 = t1[2].split("\"");
			
			String t = t2[1];
			text = t;
			return text;
		}
		protected void onPostExecute(String results) {
			if (results!=null) {
				sudoku = results;
				intent_sudoku(codigo);
			}
		}
	}

}
