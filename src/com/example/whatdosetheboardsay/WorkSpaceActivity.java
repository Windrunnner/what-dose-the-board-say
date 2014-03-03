package com.example.whatdosetheboardsay;
/**
 * This the fragment of the whiteboard workspace.
 * It redirect the activity to the WorkSpaceView class.
 * related to activity_work_space.xml
 * @author Yik Fei Wong
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

/*public class WorkSpaceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_work_space);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.work_space, menu);
		return true;
	}

}
*/

@SuppressLint("NewApi")
public class WorkSpaceActivity extends Fragment {  
	  
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
        return inflater.inflate(R.layout.activity_work_space, container, false);  
    }  
  
}  