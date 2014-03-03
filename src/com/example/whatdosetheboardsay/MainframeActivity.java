package com.example.whatdosetheboardsay;
/**
 * The MainframeActivity Class is for the activities in 
 * mainframe. It is composed of 2 fragments, one for button
 * and another for the whiteboard workspace.
 * related to activity_mainframe.xml
 * @author Zhuo Chen, Yik Fei Wong
 *
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainframeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainframe);
		
	}

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mainframe, menu);
		return true;
	}*/

}
