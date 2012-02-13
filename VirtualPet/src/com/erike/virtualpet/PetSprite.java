package com.erike.virtualpet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;



public class PetSprite {
	

	//position variables
	private int x;
	private int y;
	private int dx;
	private int dy;
	private int jheight;
	
	private static final int JUMP_MAX = 40;
	
	private Bitmap image;
	
	//State variables
	private boolean isJumping;
	
	//Stat variables
	private String name;
	private int age;
	private double weight;
	private int fullness;
	private int energy;
	private int cleanliness;
	private int health;
	private int happyness;
	
	
	//Animation variables
	private int frame;
	private int columns;
	private int width;
	private int height;
	private Rect selection;
	
	private static final String TAG = "PetSprite";
	public PetSprite(Bitmap image, int frame, int columns, int rows)
	{
		
		this.image = image;
        
        this.x = 0;
        this.y = 0;
        
        if(frame < 0)
			frame = 0;
		this.frame = frame;
		this.columns = columns;
		this.width = image.getWidth() / columns;
		this.height = image.getHeight() / rows;
        
        this.dx = 0;
        this.dy = 0;
        this.jheight = 0;
        
        this.isJumping = false;
        
        int fx = (frame % columns) * width;
		int fy = (frame / columns) * height;
		this.selection = new Rect(fx, fy, fx + width, fy + height);
	}
	
	public void move()
	{
		this.x += this.dx;
		if(this.dy != 0)
		{
			this.y += this.dy;
			if(this.dy < 0)
			{
				this.isJumping = true;
				if((this.jheight - this.y) >= PetSprite.JUMP_MAX)
					this.dy = -dy;
			}
			if(this.dy > 0)
			{	
				if(this.y >= this.jheight)
				{
					this.dy = 0;
					this.y = this.jheight;
					this.isJumping = false;
				}
			}
		}
	}
	
	public void jump()
	{
		Log.d(TAG, y + " " + jheight);
		if(this.jheight > PetSprite.JUMP_MAX)
		{
			this.y += this.dy;
			this.jheight += this.dy;
			this.setFrame(1);
			if(this.jheight >= 0)
			{
				this.dy = 0;
				this.setFrame(0);
			}
		}else{
			this.dy = -this.dy;
			this.jheight += this.dy;
			this.setFrame(0);
		}
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public int getDX()
	{
		return this.dx;
	}
	
	public int getDY()
	{
		return this.dy;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public Bitmap getImage()
	{
		return this.image;
	}
	public String getName()
	{
		return this.name;
	}
	
	public int getAge()
	{
		return this.age;
	}

	public double getWeight()
	{
		return this.weight;
	}

	public int getFullness()
	{
		return this.fullness;
	}

	public int getEnergy()
	{
		return this.energy;
	}

	public int getCleanliness()
	{
		return this.cleanliness;
	}

	public int getHealth()
	{
		return this.health;
	}

	public int getHappyness()
	{
		return this.happyness;
	}
	
	public boolean getIsJumping()
	{
		return this.isJumping;
	}
	
	public Rect getRect()
	{
		Rect box = new Rect(getX(), getY(), 
				getX() + width, getY() + height);
		return box;
	}
	
	public Boolean isTouching(int x, int y)
	{
		Rect pos = new Rect(getX(), getY(), 
				getX() + width, getY() + height);
		if(pos.contains( x, y) )
			return true;
		else 
			return false;
	}

	//Mutators
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
		this.jheight = y;
	}
	
	public void setDX(int x)
	{
		this.dx = x;
	}
	
	public void setDY(int y)
	{
		this.dy = y;
	}
	
	public void update()
	{
		this.move();
		if(this.isJumping)
			this.setFrame(1);
		else
			this.animate(0,3,1);
	}
	
	public void animate(int sFrame, int eFrame, int direct)
	{
			frame += direct;
			if(frame > eFrame)
				frame = sFrame;
			if(frame < sFrame)
				frame = eFrame;
			
			this.setFrame(frame);
	}
	
	public void setFrame(int frame)
	{
		int fx = (frame % columns) * width;
		int fy = (frame / columns) * height;
		this.selection.set(fx, fy, fx + width, fy + height);
	}
	
	public void draw(Canvas canvas)
	{
		Rect dest = new Rect(getX(), getY(), 
				getX() + width, getY() + height);
		
		canvas.drawBitmap(image, selection, dest, null);
	}
	
	public void feed()
	{
		//TODO Change in stats
	}
	
	public void play()
	{
		//TODO Change in stats
	}
	
	public void sleep()
	{
		//TODO Change in stats
	}
	
	public void clean()
	{
		//TODO Change in stats
	}
	
	public void medicine()
	{
		//TODO Change in stats
	}
	
	public void stats()
	{
		//TODO Change in stats
	}
	

}
