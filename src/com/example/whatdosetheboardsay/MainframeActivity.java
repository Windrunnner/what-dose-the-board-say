package com.example.whatdosetheboardsay;
/**
 * The MainframeActivity Class is for the activities in 
 * mainframe. It is composed of 2 fragments, one for button
 * and another for the whiteboard workspace.
 * related to activity_mainframe.xml
 * @author Zhuo Chen, Yik Fei Wong
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainframeActivity extends Activity {
	public static final int MODE_PENCIL = 0;
	public static final int MODE_ERASER = 1;
	public static final String defaultSavePath = Environment.getExternalStorageDirectory() + "/Pictures/whiteboard.png";
	public int buttonflag = 0;
	Button penbutton;
	Button eraserbutton;
	public static int currentColor = Color.BLACK;
	public static int currentSize = 2;
	public static int currentEraserSize = 30;
	final static int pensize_S = 0;
	final static int pensize_M = 1;
	final static int pensize_L = 2;
	final static int pencolor1 = 3;
	final static int pencolor2 = 4;
	final static int pencolordef = 5;
	final static int pencolor3 = 6;
	final static int pencolor4 = 7;
	final static int pencolor5 = 8;
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
		WorkSpaceView.mMainframeActivity = this;
		if (GDB_sc.getIsServer())
			this.setTitle("Whiteboard: Server");
		else
			this.setTitle("Whiteboard: Client " + JoinInterface_activity.uid);
	}

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mainframe, menu);
		return true;
	}*/
	
	@SuppressWarnings("deprecation")
	public void userClick(View view){
		showDialog(1);
	}
	
	@Override
	protected Dialog onCreateDialog(int id){
		String[] listing;
		Dialog dialog = null;
		final boolean[] flags;
		int count = GDB_sc.GetClientCount();
		//test for count
		//count = 8;
		if(count >= 0){
			listing = new String[count];
			flags = new boolean[count];
			for(int i = 0; i < count; i++){
				listing[i] = "User" + (i+1);
				flags[i] = false;
			}
		
		switch(id){
		case 1:
			Builder builder = new android.app.AlertDialog.Builder(this);
			builder.setTitle("User Management (Check to ignore user)");
			builder.setMultiChoiceItems(listing, flags, new DialogInterface.OnMultiChoiceClickListener(){
				public void onClick(DialogInterface dialog, int which, boolean isChecked){
					int[] permit = GDB_sc.GetClientPremit();
					flags[which] = isChecked;
					for(int j = 0; j < flags.length; j++){
						if(flags[j] == false){

							GDB_sc.SetPremit(j, 0);

						}else if(flags[j] == true){

							GDB_sc.SetPremit(j, 1);

						}
					}
				}
			});

			builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    
                }
            });
			dialog=builder.create();
			break;
			}
		}
		return dialog;
	}
	
	public void pencilClick(View view) {
    	WorkSpaceView.mMode = MainframeActivity.MODE_PENCIL;
    	WorkSpaceView.mSize = currentSize;
    	WorkSpaceView.mColor = currentColor;
    	WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
    	WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
    	buttonflag = 0;
    }
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		
		if(v.getId() == R.id.pen_button){
		menu.add(0, pencolordef, 0, "default");
		menu.add(0, pensize_S, 0, "small");
		menu.add(0, pensize_M, 0, "medium");
		menu.add(0, pensize_L, 0, "larger");
		menu.add(0, pencolor1, 0, "red");
		menu.add(0, pencolor2, 0, "blue");
		menu.add(0, pencolor3, 0, "yellow");
		menu.add(0, pencolor4, 0, "green");
		menu.add(0, pencolor5, 0, "magenta");
		}
		if(v.getId() == R.id.eraser_button){
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
			currentSize = 1;
			WorkSpaceView.mColor = currentColor;
	    	WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case pensize_M:
			Toast.makeText(this, "You chose medium font size", Toast.LENGTH_LONG).show();
			WorkSpaceView.mSize = 2;
			currentSize = 2;
			WorkSpaceView.mColor = currentColor;
	    	WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case pensize_L:
			Toast.makeText(this, "You chose large font size", Toast.LENGTH_LONG).show();
			WorkSpaceView.mSize = 5;
			currentSize = 5;
			WorkSpaceView.mColor = currentColor;
	    	WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case pencolor1:
			Toast.makeText(this, "You chose red font color", Toast.LENGTH_LONG).show();
			WorkSpaceView.mColor = Color.RED;
			currentColor = Color.RED;
			WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
			WorkSpaceView.mSize = currentSize;
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case pencolor2:
			Toast.makeText(this, "You chose blue font color", Toast.LENGTH_LONG).show();
			WorkSpaceView.mColor = Color.BLUE;
			currentColor = Color.BLUE;
			WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
			WorkSpaceView.mSize = currentSize;
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case pencolor3:
			Toast.makeText(this, "You chose yellow font color", Toast.LENGTH_LONG).show();
			WorkSpaceView.mColor = Color.YELLOW;
			currentColor = Color.YELLOW;
			WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
			WorkSpaceView.mSize = currentSize;
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case pencolor4:
			Toast.makeText(this, "You chose green font color", Toast.LENGTH_LONG).show();
			WorkSpaceView.mColor = Color.GREEN;
			currentColor = Color.GREEN;
			WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
			WorkSpaceView.mSize = currentSize;
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case pencolor5:
			Toast.makeText(this, "You chose MAGENTA font color", Toast.LENGTH_LONG).show();
			WorkSpaceView.mColor = Color.MAGENTA;
			currentColor = Color.MAGENTA;
			WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
			WorkSpaceView.mSize = currentSize;
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case pencolordef:
			Toast.makeText(this, "Reset to default", Toast.LENGTH_LONG).show();
			WorkSpaceView.mSize = 2;
	    	WorkSpaceView.mColor = Color.BLACK;
	    	currentColor = Color.BLACK;
	    	currentSize = 2;
	    	WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
	    	WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
	    	break;
		case erasersizeS:
			Toast.makeText(this, "You chose small eraser size", Toast.LENGTH_LONG).show();
			WorkSpaceView.mSize = 15;
			currentEraserSize = 15;
			WorkSpaceView.mColor = Color.WHITE;
	    	WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case erasersizeM:
			Toast.makeText(this, "You chose medium eraser size", Toast.LENGTH_LONG).show();
			WorkSpaceView.mSize = 30;
			currentEraserSize = 30;
			WorkSpaceView.mColor = Color.WHITE;
	    	WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		case erasersizeL:
			Toast.makeText(this, "You chose Large eraser size", Toast.LENGTH_LONG).show();
			WorkSpaceView.mSize = 50;
			currentEraserSize = 50;
			WorkSpaceView.mColor = Color.WHITE;
	    	WorkSpaceView.mPaint.setColor(WorkSpaceView.mColor);
			WorkSpaceView.mPaint.setStrokeWidth(WorkSpaceView.mSize);
			break;
		}
		
		return super.onContextItemSelected(item);
	}
	
	
	public void eraserClick(View view) {
    	WorkSpaceView.mMode = MainframeActivity.MODE_ERASER;
    	WorkSpaceView.mSize = currentEraserSize;
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

	public void saveClick(View view) {
		File f = new File(defaultSavePath);
		Log.i("Save", "Save");
		
		if (f.exists())
		{
			f.delete();
		}
		
		try
		{
			FileOutputStream out = new FileOutputStream(f);
			
			if (WorkSpaceView.mmBitmap != null)
			{
				WorkSpaceView.mmBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
				out.flush();
				out.close();
				Log.i("Save", "Picture Saved");
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}	
	}
	
	public void loadClick(View view)
	{
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inJustDecodeBounds = true;
		option.inPreferredConfig = Bitmap.Config.ARGB_8888;
		BitmapFactory.decodeFile(defaultSavePath, option);
		int width = option.outWidth;
		int height = option.outHeight;
		
		Bitmap bitmap = BitmapFactory.decodeFile(defaultSavePath);
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		
		Log.i("Load", "Load");
		IMessage loadMsg = new LoadMsg(pixels, width, height);
    	WorkSpaceView.mWorkSpaceView.messageReceived(loadMsg);
    	/*try {
			GDB_sc.sendByteMessage(GDB_sc.getBytes(loadMsg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void showToast(final String string){
		Log.d("Toast", "Toasting: " + string);
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(MainframeActivity.this, string, Toast.LENGTH_SHORT).show();
			}
		});
		
	}
    /**
     * Called if received an ExitMsg, it will show a "Toast" message to notify the users.
     * @param client Quitting Client number
     */
    public void onSbExit(int client){
    	showToast("Client " + Integer.toString(client) + " just quited.");
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
        	if (GDB_sc.getIsServer()){
        		IMessage shutMsg = new ShutMsg();
        		//WorkSpaceView.mWorkSpaceView.messageReceived(shutMsg);x
        		try {
        			GDB_sc.sendByteMessage(GDB_sc.getBytes(shutMsg));
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		//Server.exit = true;
        		//GDB_sc.sendByteMessage(null);
        		return super.onKeyDown(keyCode, event);//The Back key also need to be handled by system, so return false. 
        	} else {
        		IMessage exitMsg = new ExitMsg(JoinInterface_activity.uid);
        		//WorkSpaceView.mWorkSpaceView.messageReceived(exitMsg);
        		try {
        			GDB_sc.sendByteMessage(GDB_sc.getBytes(exitMsg));
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		//Client.exit = true;
        		return super.onKeyDown(keyCode, event);//The Back key also need to be handled by system, so return false.
        	}
        }
        return super.onKeyDown(keyCode, event);//Other keys will just be handled by the super class.
    }

}
