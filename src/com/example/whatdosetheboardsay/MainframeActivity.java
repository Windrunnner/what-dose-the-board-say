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
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainframeActivity extends Activity {
	public static final int MODE_PENCIL = 0;
	public static final int MODE_ERASER = 1;
	public int buttonflag = 0;
	Button penbutton;
	Button eraserbutton;
	final static int pensize_S = 0;
	final static int pensize_M = 1;
	final static int pensize_L = 2;
	final static int pencolor1 = 3;
	final static int pencolor2 = 4;
	final static int pencolordef = 5;
	final static int erasersizeS = 11;
	final static int erasersizeM = 12;
	final static int erasersizeL = 13;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainframe);
		penbutton = (Button)findViewById(R.id.pen_button);
		eraserbutton = (Button)findViewById(R.id.eraser_button);
		registerForContextMenu(penbutton);
		registerForContextMenu(eraserbutton);
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
    	buttonflag = 0;
    	
    	
    }
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		if(buttonflag == 0){
		menu.add(0, pensize_S, 0, "small");
		menu.add(0, pensize_M, 0, "medium");
		menu.add(0, pensize_L, 0, "larger");
		menu.add(0, pencolor1, 0, "red");
		menu.add(0, pencolor2, 0, "blue");
		menu.add(0, pencolordef, 0, "default");
		}
		if(buttonflag == 1){
		menu.add(0, erasersizeS, 0, "small");
		menu.add(0, erasersizeM, 0, "medium");
		menu.add(0, erasersizeL, 0, "larger");
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		switch(item.getItemId()){
		case pensize_S:
			Toast.makeText(this, "You chose small font size", Toast.LENGTH_LONG).show();
			WorkSpaceView.mSize = 1;
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case pensize_M:
			Toast.makeText(this, "You chose medium font size", Toast.LENGTH_LONG).show();
			WorkSpaceView.mSize = 2;
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case pensize_L:
			Toast.makeText(this, "You chose large font size", Toast.LENGTH_LONG).show();
			WorkSpaceView.mSize = 5;
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case pencolor1:
			Toast.makeText(this, "You chose red font color", Toast.LENGTH_LONG).show();
			WorkSpaceView.mColor = Color.RED;
			WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
			break;
		case pencolor2:
			Toast.makeText(this, "You chose blue font color", Toast.LENGTH_LONG).show();
			WorkSpaceView.mColor = Color.BLUE;
			WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
			break;
		case pencolordef:
			Toast.makeText(this, "Reset to default", Toast.LENGTH_LONG).show();
			WorkSpaceView.mSize = 2;
	    	WorkSpaceView.mColor = Color.BLACK;
	    	WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
	    	WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
	    	break;
		case erasersizeS:
			Toast.makeText(this, "You chose small eraser size", Toast.LENGTH_LONG).show();
			WorkSpaceView.mSize = 15;
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case erasersizeM:
			Toast.makeText(this, "You chose medium eraser size", Toast.LENGTH_LONG).show();
			WorkSpaceView.mSize = 30;
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case erasersizeL:
			Toast.makeText(this, "You chose Large eraser size", Toast.LENGTH_LONG).show();
			WorkSpaceView.mSize = 50;
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		}
		
		return super.onContextItemSelected(item);
	}
	
	
	public void eraserClick(View view) {
    	WorkSpaceView.mMode = MainframeActivity.MODE_ERASER;
    	WorkSpaceView.mSize = 50;
    	WorkSpaceView.mColor = Color.WHITE;
    	WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
    	WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
    	buttonflag = 1;
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

}
