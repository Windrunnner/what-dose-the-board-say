package com.example.whatdosetheboardsay;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class StartUpInterfaceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_up_interface);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_up_interface, menu);
		return true;
	}
	
	public void toJoin(View view){
    	Intent intent = new Intent(this, JoinInterface_activity.class);
  	  	startActivity(intent);
    }

	public void toCreate(View view){
    	Intent intent = new Intent(this, CreateInterfaceActivity.class);
  	  	startActivity(intent);
    }
}
