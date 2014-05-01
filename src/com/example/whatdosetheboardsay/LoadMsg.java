package com.example.whatdosetheboardsay;

import java.io.Serializable;

import android.graphics.Bitmap;

public class LoadMsg implements IMessage {
	public int[] mPixels;
	public int mWidth, mHeight;
	
	public LoadMsg(int[] pixels, int width, int height)
	{
		mPixels = pixels;
		mWidth = width;
		mHeight = height;
	}
}
