package com.erike.virtualpet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;


public class Sprite {
	
	private static final String TAG = "Sprite";
	//instance variables
	private Bitmap sprite;
	private int xPos;
	private int yPos;
	private int frame;
	private Rect selection;
	private int columns;
	private int width;
	private int height;
	private int fps;
	private long frameTimer;
	private int velx;
	private int vely;
	
	public Sprite(Bitmap sprite, int frame, int columns, int rows, int fps)
	{
		Log.d(TAG, "Constructor Start");
		this.sprite = sprite;
		if(frame < 0)
			frame = 0;
		this.frame = frame;
		this.columns = columns;
		this.width = sprite.getWidth() / columns;
		this.height = sprite.getHeight() / rows;
		this.xPos = 0;
		this.yPos = 0;
		this.velx = 0;
		this.vely = 0;
		if(fps <= 0)
			fps = 1;
		this.fps = 1000/ fps;
		this.frameTimer = 0;
		
		int fx = (frame % columns) * width;
		int fy = (frame / columns) * height;
		this.selection = new Rect(fx, fy, fx + width, fy + height);
		Log.d(TAG, "left: " + selection.left + " right: " + selection.right + 
				" top: " + selection.top + " bottom: " + selection.bottom);
	}
	
	//Accessors
	public int getXPos()
	{
		return this.xPos;
	}
	
	public int getYPos()
	{
		return this.yPos;
	}
	
	public int getFrame()
	{
		return this.frame;
	}
	
	public int getVelX()
	{
		return this.velx;
	}
	
	public int getVelY()
	{
		return this.vely;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public Rect getRect()
	{
		Rect box = new Rect(getXPos(), getYPos(), 
				getXPos() + width, getYPos() + height);
		return box;
	}
	
	
	//Mutators
	public void setXPos(int x)
	{
		this.xPos = x;
	}
	
	public void setYPos(int y)
	{
		this.yPos = y;
	}
	
	public void setVelX(int x)
	{
		this.velx = x;
	}
	
	public void setVelY(int y)
	{
		this.vely = y;
	}
	
	
	
	public void animate(int sFrame, int eFrame, int direct, long gameTime)
	{
		if(gameTime > frameTimer + fps)
		{
			frameTimer = gameTime;
			
			frame += direct;
			if(frame > eFrame)
				frame = sFrame;
			if(frame < sFrame)
				frame = eFrame;
			
			int fx = (frame % columns) * width;
			int fy = (frame / columns) * height;
			this.selection.set(fx, fy, fx + width, fy + height);
		}
	}
	
	public void draw(Canvas canvas)
	{
		Rect dest = new Rect(getXPos(), getYPos(), 
				getXPos() + width, getYPos() + height);
		
		canvas.drawBitmap(sprite, selection, dest, null);
	}

}
