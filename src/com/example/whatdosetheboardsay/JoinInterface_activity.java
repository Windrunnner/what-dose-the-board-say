package com.example.whatdosetheboardsay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class JoinInterface_activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join_interface_activity);
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
    	int uid;
    	if (passString.equals(""))
    		uid = JoinBoard.join(ipString);
    	else
    		uid = JoinBoard.join(ipString, passString);
    	if (uid != -1){
    		Intent intent = new Intent(this, WorkSpaceActivity.class);
    		startActivity(intent);
    	}
	}

}
