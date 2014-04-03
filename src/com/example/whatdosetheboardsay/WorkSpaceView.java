package com.example.whatdosetheboardsay;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
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
	private static boolean flag = false;
    private MainframeActivity mMainframActivity;
    private static randomLineMsg receivedRanLine = null;
    private randomLineMsg ranLine;
    public static WorkSpaceView mWorkSpaceView = null;
      
    private Bitmap mBitmap;  //保存每次绘画的结果  
    private Paint mPaint;
    private Handler messageHandler;
    public static Context mContext;
      
    public WorkSpaceView(Context context, AttributeSet attrs) {  
        super(context, attrs);
        
        if (mWorkSpaceView == null)
        {
        	mWorkSpaceView = this;
        }
        
        Looper looper = Looper.myLooper();
        messageHandler = new MessageHandler(looper);
        
        ranLine = new randomLineMsg(mPaint);
        mContext = context;
        mPaint = new Paint();  
        mPaint.setStrokeWidth(2);
    }  
    
    public void messageReceived(Object obj)
    {
    	if (obj instanceof randomLineMsg)
    	{
    		System.out.println("Great Chen BABA!!!");
    		receivedRanLine = ((randomLineMsg) obj);
    		flag = true;
    		
    		Message message = Message.obtain();
    		message.obj = receivedRanLine;
    		messageHandler.sendMessage(message);
    		//invalidate();
    	}
    }
  
    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);
          
        int width = getWidth();  
        int height = getHeight();  
          
        if (mBitmap == null) {  
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);  
        }  
          
        //先将结果画到Bitmap上  
        Canvas tmpCanvas = new Canvas(mBitmap);
        
        if (flag == true)
        {
        	receivedRanLine.paint(tmpCanvas, mPaint);
        	flag = false;
        }
        else
        {
        	tmpCanvas.drawLine(mLastX, mLastY, mCurrX, mCurrY, mPaint);
        }
        //再把Bitmap画到canvas上  
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);  
    }  
      
    @Override  
    public boolean onTouchEvent(MotionEvent event) {
    	randomLineMsg temp = null;
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