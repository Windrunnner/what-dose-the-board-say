package com.example.whatdosetheboardsay;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class WorkSpaceView extends View
{
	private int mLastX, mLastY;
	private int mCurrX, mCurrY;
	private static RandomLineMsg ranLine;
    private static RandomLineMsg receivedRanLine = null;
    public static WorkSpaceView mWorkSpaceView = null;
    public static MainframeActivity mMainframeActivity = null;
    public static Bitmap mmBitmap = null;
      
    private Bitmap mBitmap;  //保存每次绘画的结果  
    private Handler messageHandler;
    public static Paint mPaint;
    public static int mMode;
    public static int mColor;
    public static int mSize;
	private static boolean isRanLine;
    public static boolean isClean;
      
    public WorkSpaceView(Context context, AttributeSet attrs) {  
        super(context, attrs);
        
        if (mWorkSpaceView == null)
        {
        	mWorkSpaceView = this;
        }
        	
        Looper looper = Looper.myLooper();
        messageHandler = new MessageHandler(looper);
        
        mMode = MainframeActivity.MODE_PENCIL;
        mColor = Color.BLACK;
        mSize = 2;
        isRanLine = false;
        isClean = false;
        
        ranLine = new RandomLineMsg(mColor, mSize);
        
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(mSize);
    }  
    
    public void messageReceived(Object obj)
    {
    	if (obj instanceof RandomLineMsg)
    	{
    		System.out.println("Great Chen BABA!!!");
    		receivedRanLine = ((RandomLineMsg) obj);
    		isRanLine = true;
    		
    		Message message = Message.obtain();
    		message.obj = receivedRanLine;
    		messageHandler.sendMessage(message);
    	}
    	
    	if (obj instanceof CleanMsg)
    	{
    		System.out.println("Great Chen BABA!!!");
    		CleanMsg cleanMsg = (CleanMsg) obj;
    		isClean = true;
    		
    		Message message = Message.obtain();
    		message.obj = cleanMsg;
    		messageHandler.sendMessage(message);
    	}
    	
    	if (obj instanceof ShutMsg)
    	{
    		// TODO Implement shutting down process
    		mMainframeActivity.showToast("Server has already shut down! Nothing will be sychronized any more.");
    		
    	}
    	
    	if (obj instanceof ExitMsg)
    	{
    		// TODO Implement Exit message, also exit messaging for Server
    		mMainframeActivity.onSbExit(((ExitMsg) obj).getClient());
    		if (GDB_sc.getIsServer()){
        		try {
        			GDB_sc.sendByteMessage(GDB_sc.getBytes(obj));
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
    		}
    	}
    }
  
    private void createWhiteBitmap(Bitmap bitmap, int width, int height)
    {

    	int []pix = new int[width * height];
    	
    	for (int y = 0; y < height; y++)
    	{
    		for (int x = 0; x < width; x++)
    		{
    			int index = y * width + x;
    			int r = ((pix[index] >> 16) & 0xff)|0xff;
    			int g = ((pix[index] >> 8) & 0xff)|0xff;
    			int b = (pix[index] & 0xff)|0xff;
    			pix[index] = 0xff000000 | (r << 16) | (g << 8) | b;
    		}
    	}
    	
    	mBitmap.setPixels(pix, 0, width, 0, 0, width, height);
    }
    
    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);
          
        int width = getWidth();  
        int height = getHeight();  
          
        if (mBitmap == null)
        {
        	mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        	createWhiteBitmap(mBitmap, width, height);
        }
          
        //先将结果画到Bitmap上  
        Canvas tmpCanvas = new Canvas(mBitmap);
        
        if(isRanLine)
        {
        	receivedRanLine.paint(tmpCanvas);
        	isRanLine = false;
        }
        else if (isClean)
        {
        	mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        	createWhiteBitmap(mBitmap, width, height);
        	isClean = false;
        }
        else
        {
        	tmpCanvas.drawLine(mLastX, mLastY, mCurrX, mCurrY, mPaint);
        }
        //再把Bitmap画到canvas上  
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        mmBitmap = mBitmap;
    }  
      
    @Override  
    public boolean onTouchEvent(MotionEvent event) {
        ranLine.update();
    	mLastX = mCurrX;
    	mLastY = mCurrY;
    	mCurrX = (int)event.getX();
    	mCurrY = (int)event.getY();
    	ranLine.setPoints(mCurrX, mCurrY);
    	
        switch (event.getAction()) {  
        case MotionEvent.ACTION_DOWN:  
            mLastX = mCurrX;
            mLastY = mCurrY;
            break;
        case MotionEvent.ACTION_UP:
			try {
				GDB_sc.sendByteMessage(GDB_sc.getBytes(ranLine));
				ranLine.clear();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	break;
        default:  
            break;  
        }  
        
        invalidate();  
  
        return true; //必须返回true  
    }
}

class MessageHandler extends Handler
{
	public MessageHandler(Looper looper)
	{
		super(looper);
	}
	
	@Override
	public void handleMessage(Message msg)
	{
		WorkSpaceView.mWorkSpaceView.invalidate();
	}
}