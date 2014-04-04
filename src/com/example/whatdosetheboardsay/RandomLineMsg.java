package com.example.whatdosetheboardsay;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class RandomLineMsg implements IMessage
{
	private int mSize;
	private int mColor;
	private Vector<Integer> points;
	private int x0, y0, x1, y1;
	
	public RandomLineMsg(int color, int size)
	{
		mSize = size;
		mColor = color;
		points = new Vector<Integer>();
	}
	
	public void setPoints(int x, int y)
	{
		points.add(x);
		points.add(y);
	}
	
	public void paint(Canvas canvas)
	{	
		Paint p = new Paint();
		p.setColor(mColor);
		p.setStrokeWidth(mSize);
		
		Enumeration allPoints = points.elements();
		
		if (!allPoints.hasMoreElements())
		{
			return;
		}
		
		x0 = (Integer)allPoints.nextElement();
		
		if (!allPoints.hasMoreElements())
		{
			return;
		}
		
		y0 = (Integer)allPoints.nextElement();
		
		while(allPoints.hasMoreElements())
		{
			x1 = (Integer)allPoints.nextElement();
			
			if (!allPoints.hasMoreElements())
			{
				return;
			}
			
			y1 = (Integer)allPoints.nextElement();
			canvas.drawLine(x0, y0, x1, y1, p);
			x0 = x1;
			y0 = y1;
		}
	}
	
	public void clear()
	{
		mSize = 0;
		mColor = 0;
		points.clear();
	}
	
	public void update()
	{
		mSize = WorkSpaceView.mSize;
		mColor = WorkSpaceView.mColor;
	}
}

