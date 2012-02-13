package com.erike.virtualpet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Button {
	
	//position variables
	private int x;
	private int y;
	
	private Bitmap button;
	
	private String name;
	
	public Button(int x, int y, Bitmap button, String name)
	{
		this.x = x;
		this.y = y;
		this.button = button;
		this.name = name;
	}
	
	public Button(Bitmap button, String name)
	{
		this(0, 0, button, name);
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public int getWidth()
	{
		return button.getWidth();
	}
	
	public int getHeight()
	{
		return button.getHeight();
	}
	
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(button, x, y, null);
	}
	
	public void draw(int x, int y, Canvas canvas)
	{
		canvas.drawBitmap(button, x, y, null);
	}
	
	public Boolean isTouching(int x, int y)
	{
		Rect pos = new Rect(x, y, 
				x + button.getWidth(), y + button.getHeight());
		if(pos.contains( x, y) )
			return true;
		else 
			return false;
	}
	
	

}
