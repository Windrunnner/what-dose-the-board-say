package com.example.whatdosetheboardsay;
/**
 * The MainframeActivity Class is for the activities in 
 * mainframe. It is composed of 2 fragments, one for button
 * and another for the whiteboard workspace.
 * related to activity_mainframe.xml
 * @author Zhuo Chen, Yik Fei Wong
 *
 */
import java.io.IOException;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class MainframeActivity extends Activity {
	public static final int MODE_PENCIL = 0;
	public static final int MODE_ERASER = 1;

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
	
	public void pencilClick(View view) {
    	WorkSpaceView.mMode = MainframeActivity.MODE_PENCIL;
    	WorkSpaceView.mSize = 2;
    	WorkSpaceView.mColor = Color.BLACK;
    	WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
    	WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
    }
	
	public void eraserClick(View view) {
    	WorkSpaceView.mMode = MainframeActivity.MODE_ERASER;
    	WorkSpaceView.mSize = 50;
    	WorkSpaceView.mColor = Color.WHITE;
    	WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
    	WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
    }
	
	public void cleanClick(View view) {
    	IMessage cleanMsg = new CleanMsg();
    	WorkSpaceView.mWorkSpaceView.messageReceived(cleanMsg);
    	try {
			GDB_sc.sendByteMessage(GDB_sc.getBytes(cleanMsg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    /**
     * Called if received an ExitMsg, it will show a "Toast" message to notify the users.
     * @param client Quitting Client number
     */
    public void onSbExit(int client){
    	Toast.makeText(this, Server.addList.get(client) + "just quited.", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
        	if (GDB_sc.getIsServer()){
        		IMessage shutMsg = new ShutMsg();
        		//WorkSpaceView.mWorkSpaceView.messageReceived(shutMsg);
        		try {
        			GDB_sc.sendByteMessage(GDB_sc.getBytes(shutMsg));
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		return false;//The Back key also need to be handled by system, so return false. 
        	} else {
        		IMessage exitMsg = new ExitMsg(JoinInterface_activity.uid);
        		//WorkSpaceView.mWorkSpaceView.messageReceived(exitMsg);
        		try {
        			GDB_sc.sendByteMessage(GDB_sc.getBytes(exitMsg));
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		return false;//The Back key also need to be handled by system, so return false.
        	}
        }
        return super.onKeyDown(keyCode, event);//Other keys will just be handled by the super class.
    }

}
