package com.example.whatdosetheboardsay;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class JoinInterface_activity extends Activity {
	public static int uid = -1;
	public static JoinBoard joinThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join_interface_activity);
        final Intent intent = getIntent();   
        final Uri uri = intent.getData(); 
        if (!String.valueOf(uri).equals("null")){
        	setTitle(String.valueOf(uri).split("://")[1]);
        	if (String.valueOf(uri).split(":").length == 3){
        		((EditText) findViewById(R.id.ipedit)).setText(String.valueOf(uri).split("://")[1].split("[:]")[0]);
        		((EditText) findViewById(R.id.passedit)).setText(String.valueOf(uri).split("://")[1].split("[:]")[1]);
        	}
        	else {
        		((EditText) findViewById(R.id.ipedit)).setText(String.valueOf(uri).split("://")[1]);
        	}
        }
        else
        	setTitle("Join a Board");
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.join_interface_activity, menu);
		return true;
	}
	
	public void tryJoin(View view) {
    	String ipString =((EditText) findViewById(R.id.ipedit)).getText().toString();
    	String passString = ((EditText) findViewById(R.id.passedit)).getText().toString();
    	GDB_sc.SetServerIpAddress(ipString);
    	JoinBoard.serverIP = ipString;
    	JoinBoard.password = passString;
    	joinThread = new JoinBoard();
    	new Thread(joinThread).start();
    	synchronized (joinThread) {
    		try {
				joinThread.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	Log.d("JOIN", new Integer(uid).toString());
    	if (uid >= 0){
    		MainActivity.client = new Client();
    		new Thread(MainActivity.client).start();
        	Intent intent = new Intent(this, MainframeActivity.class);
      	  	startActivity(intent);
    	}
    	else if (uid == -1){
    		Toast.makeText(this, "Join Failed. Check your password!", Toast.LENGTH_LONG).show();
    	}
    	else 
    		Toast.makeText(this, "Joining process timed out. Check the IP!", Toast.LENGTH_LONG).show();
	}

}
