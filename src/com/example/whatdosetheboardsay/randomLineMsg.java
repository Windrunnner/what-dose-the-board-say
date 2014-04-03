package com.example.whatdosetheboardsay;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;

public class randomLineMsg implements Serializable
{
	//private Paint myPaint;
	private Vector<Integer> points;
	private int x0, y0, x1, y1;
	
	public randomLineMsg(Paint p)
	{
		//myPaint = p;
		points = new Vector<Integer>();
	}
	
	public void setPoints(int x, int y)
	{
		points.add(x);
		points.add(y);
	}
	
	public void paint(Canvas canvas, Paint p)
	{
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
		points.clear();
	}
}
