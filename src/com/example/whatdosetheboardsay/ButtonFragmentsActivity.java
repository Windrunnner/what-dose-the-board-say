package com.example.whatdosetheboardsay;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ButtonFragmentsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.button_fragments);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.button_fragments, menu);
		return true;
	}

}
