package com.example.whatdosetheboardsay;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class CreateInterfaceActivity extends Activity {
	private static Thread serverThread = null;
	public static Server server;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_interface);
		EditText ipText = (EditText) findViewById(R.id.editIpAddress_create);
		ipText.setText(GDB_sc.GetLocalIpAddress());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_interface, menu);
		return true;
	}
	
	public void tryInvite(View view){
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("message/rfc822");
		intent.putExtra(Intent.EXTRA_EMAIL,
				((EditText) findViewById(R.id.editInvite_create)).getText().toString().split(";"));
		intent.putExtra(Intent.EXTRA_SUBJECT, "You've received a Whiteboard invitation!");
		intent.putExtra(Intent.EXTRA_TEXT, 
				Html.fromHtml("<!DOCTYPE html><html><body><a href=\"wdbs://"
				+GDB_sc.GetLocalIpAddress()+":"
				+((EditText) findViewById(R.id.editSetPassword_create)).getText().toString()
				+"\">wdbs://"
				+GDB_sc.GetLocalIpAddress()+":"
				+((EditText) findViewById(R.id.editSetPassword_create)).getText().toString()
				+"</a></body></html>"));
		startActivity(Intent.createChooser(intent, "Send invitation"));
	}
	
    public void createServer(View view) {
    	//if (serverThread == null){
    		server = new Server();
    		Server.password = ((EditText) findViewById(R.id.editSetPassword_create)).getText().toString();
    		serverThread = new Thread(server);
    		serverThread.start();
        	Intent intent = new Intent(this, MainframeActivity.class);
      	  	startActivity(intent);
    	//}   		
    	
    }

}
