package com.example.whatdosetheboardsay;
/**
 * This is the fragment for buttons. The buttons
 * will provide different functions to control 
 * the activity in whiteboard workspace fragment.
 * related to button_fragments.xml
 * @author Zhuo Chen, Yik Fei Wong
 *
 */
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.view.Menu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*public class ButtonFragmentsActivity extends Activity {

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

}*/

@SuppressLint("NewApi")
public class ButtonFragmentsActivity extends Fragment {
	public ButtonFragmentsActivity(){
		
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.button_fragments, container, false);
        return view;
	}
}