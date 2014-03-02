package com.example.whatdosetheboardsay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	public static Client client;
	@Override
    public void onCreate(Bundle icicle) {
    	        super.onCreate(icicle);
    	        setContentView(R.layout.activity_main);
    }
	
    public void sendClient(View view) {
     	 Intent intent = new Intent(this, DisplayMessageActivity.class);
 	     EditText editText = (EditText) findViewById(R.id.editText1);
 	     String message = editText.getText().toString();
 	     GDB_sc.SetServerIpAddress(message);
 	     client = new Client();
   	     new Thread(client).start();

  //  	 Intent intent = new Intent(this, DisplayMessageActivity.class);
  // 	     EditText editText = (EditText) findViewById(R.id.editText1);
  //  	 String message = editText.getText().toString();
   // 	 intent.putExtra(EXTRA_MESSAGE, message);
   // 	 startActivity(intent);
        }
    public void sendServer(View view) {
    	  new Thread(new Server()).start();
    	  Intent intent = new Intent(this, DisplayMessageActivity.class);
    	  intent.putExtra(EXTRA_MESSAGE, GDB_sc.GetLocalIpAddress());
    	  startActivity(intent);
    }
    public void sendMessage(View view){
 	 EditText editText = (EditText) findViewById(R.id.editText4);
   	 Client.str = editText.getText().toString();
   	synchronized (MainActivity.client) {
   	 client.notify();
   	}
   	}
    
    public void launchMainFrame(View view){
    	Intent intent = new Intent(this, MainframeActivity.class);
  	  	startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
