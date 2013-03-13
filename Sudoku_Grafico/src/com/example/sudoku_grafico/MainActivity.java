package com.example.sudoku_grafico;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Scanner;

import org.json.*;

import android.os.Build;
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
		button1 = (Button) findViewById(R.id.Button03);
		button2 = (Button) findViewById(R.id.button2);
		evento();
			
		webSer("app-appmovil.rhcloud.com");
		
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
		Intent abrirSudoku= new Intent(getApplicationContext(), SudukoActivity.class);
		startActivityForResult(abrirSudoku, codigo);
	}
	public static JSONObject webSer(String serviceUrl) {
	    disableConnectionReuseIfNecessary();
        HttpURLConnection urlConnection = null;
		    try {
	        // create connection
	        URL urlToRequest = new URL(serviceUrl);
	        urlConnection = (HttpURLConnection)
	        urlToRequest.openConnection();
	         
		    // handle issues
		    int statusCode = urlConnection.getResponseCode();
		    if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
		   // handle unauthorized (if service requires user login)
		    } else if (statusCode != HttpURLConnection.HTTP_OK) {
		     // handle any other errors, like 404, 500,..
		       }
		        // create JSON object from content
				        InputStream in = new BufferedInputStream(
			            urlConnection.getInputStream());
				        return new JSONObject(getResponseText(in));
		    } catch (MalformedURLException e) {
		   // URL is invalid
		   } catch (SocketTimeoutException e) {
		      // data retrieval or connection timed out
		   } catch (IOException e) {
		   // could not read response body
		
		        // (could not create input stream)
		   } catch (JSONException e) {
		     // response body is no valid JSON string
		    } finally {
		     if (urlConnection != null) {
		          urlConnection.disconnect();
		   }
		
		    }      
		
		
				    return null;
		
		}
					
		private static void disableConnectionReuseIfNecessary() {
		
		   // see HttpURLConnection API doc
				    if (Integer.parseInt(Build.VERSION.SDK)
				            < Build.VERSION_CODES.FROYO) {
		
		        System.setProperty("http.keepAlive", "false");
		
		    }
		
		}
		
		private static String getResponseText(InputStream inStream) {
				
		   return new Scanner(inStream).useDelimiter("\\A").next();
		
		}

}
