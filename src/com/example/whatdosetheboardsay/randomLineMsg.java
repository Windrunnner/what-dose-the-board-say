package com.example.whatdosetheboardsay;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class randomLineMsg implements Serializable
{
	private Vector points = new Vector();
	private int x0, y0, x1, y1;
	private CustomPoint point, start, end;
	
	public void setPoints(int x, int y)
	{
		point = new CustomPoint(x, y);
		this.points.add(point);
	}
	
	public void paint(Paint p, Canvas canvas)
	{
		Enumeration allPoints = points.elements();
		
		if (!allPoints.hasMoreElements())
		{
			return;
		}
		
		start = (CustomPoint)allPoints.nextElement();
		x0 = start.x;
		y0 = start.y;
		
		while(allPoints.hasMoreElements())
		{
			end = (CustomPoint)allPoints.nextElement();
			x1 = end.x;
			y1 = end.y;
			canvas.drawLine(x0, y0, x1, y1, p);
			x0 = x1;
			y0 = y1;
		}
	}
	
	public void print()
	{
		Enumeration allPoints = points.elements();
		Point temp;
		
		temp = (Point)allPoints.nextElement();
		int i = 0;
		
		while (allPoints.hasMoreElements())
		{
			System.out.println("Point" + i + ": " + "(" + temp.x + ", " + temp.y + ")");
			temp = (Point)allPoints.nextElement();
			i++;
		}
	}
}
