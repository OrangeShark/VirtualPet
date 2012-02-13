package com.erike.virtualpet;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PetView extends SurfaceView implements SurfaceHolder.Callback{
	
	private static final String TAG = "PetView";
	
	private GameThread thread;
	private PetSprite currentSprite;
	private ArrayList<PetSprite> sprites;
	private Button[] buttons;
	
	//States
	private boolean isFeeding;
	
	public PetView(Context context){
		super(context);
		Log.d(TAG, "Constructor");
		this.currentSprite = null;
		this.sprites = new ArrayList<PetSprite>();
		getHolder().addCallback(this);
		thread = new GameThread(this);
		setFocusable(true);

		Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.petspritesheet);
		currentSprite = new PetSprite(image, 0, 4, 1);
		sprites.add(currentSprite);
		this.initButtons();
		this.isFeeding = false;
		
	}
	
	private void initButtons()
	{
		Log.d(TAG, "Height: "  + this.getHeight());
		buttons = new Button[8];
		Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.gamebutton);
		buttons[0] = new Button(0, 0, image, "food");
		buttons[1] = new Button(image.getWidth(), 0, image, "play");
		buttons[2] = new Button(image.getWidth()*2, 0, image, "sleep");
		buttons[3] = new Button(image.getWidth()*3, 0, image, "clean");
		buttons[4] = new Button( image, "medicine");
		buttons[5] = new Button( image, "status");
		buttons[6] = new Button( image, "options");
		buttons[7] = new Button( image, "medicine");
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!thread.isAlive()) {
            thread = new GameThread(this);
        }
		thread.setRunning(true);
		thread.start();
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while (retry){
			try{
				thread.join();
				retry = false;
			}catch (InterruptedException e){
				//we will try it again and again...
			}
		}
	}
	
	
	
	@Override
	public void onDraw(Canvas canvas){
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.background));
		canvas.drawRect(0,0, getWidth(), getHeight(), background);
		
		//Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.block);
		//image = Bitmap.createScaledBitmap(image, 100, 100, false);
		
		//canvas.drawBitmap(image, 100, 100, null);
		//Log.d(TAG, "onDraw");
		//image = BitmapFactory.decodeResource(getResources(), R.drawable.kirby);
		//canvas.drawBitmap(image, 0, 0, null);

		for(PetSprite graphic : sprites)
		{
			graphic.draw(canvas);
		}
		
		this.drawButtons(canvas);
		
		if(isFeeding)
		{
			Paint box = new Paint();
			box.setColor(Color.BLACK);
			canvas.drawRect(30, 80, this.getWidth() - 30, this.getHeight() - 80, box );
		}
	}
	
		private void drawButtons(Canvas canvas)
		{
			for(int i = 0; i < 4; i++)
			{
				buttons[i].draw(canvas);
			}
			for(int i = 4; i < buttons.length; i++)
			{
				buttons[i].draw((i-4)*buttons[i].getWidth(), this.getHeight() - buttons[i].getHeight(), canvas);
			}
		}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (getHolder()){
			if(event.getAction() == MotionEvent.ACTION_DOWN)
			{
				/*Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.android2);
				Sprite sprite = new Sprite(image, 0, 1, image.getWidth(), image.getHeight(), 1);
				sprite.setXPos( (int) event.getX() - sprite.getWidth() /2);
				sprite.setYPos( (int) event.getY() - sprite.getHeight() /2);
				currentSprite = sprite;*/
				if(buttons[0].isTouching( (int)event.getX(), (int)event.getY() ) )
				{
					this.isFeeding = true;
				}
				if( currentSprite.isTouching((int)event.getX(), (int)event.getY()) )
					if(!currentSprite.getIsJumping())
						currentSprite.setDY(-4);
			}else if(event.getAction() == MotionEvent.ACTION_UP)
			{
				/*sprites.add(currentSprite);
				currentSprite = null;*/
			}
			
			return true;
		}
	}
	
	public void update() {
		//Log.d(TAG, this.getWidth() + " " + this.getHeight());
		
		for(PetSprite graphic: sprites)
		{
			if(graphic.getX() == 0 && graphic.getY() == 0)
			{
				graphic.setX(this.getWidth() / 2 - graphic.getWidth()/2);
				graphic.setY(this.getHeight() / 2 - graphic.getHeight()/2);
			}
			
			//Log.d(TAG, "updatePhysics");
			graphic.update();
			
			//graphic.jump();
				
				
			
			//border
			if(graphic.getX() < 0 - graphic.getWidth())
			{
				graphic.setDX(graphic.getDX());
				graphic.setX(this.getWidth());
			}/*else if(graphic.getXPos() + graphic.getWidth() > this.getWidth() )
			{
				graphic.setVelX(-graphic.getVelX());
			}*/
			
			if(graphic.getY() < 0)
			{
				graphic.setDY(-graphic.getDY());
			}else if(graphic.getY() + graphic.getHeight() > this.getHeight() )
			{
				graphic.setDY(-graphic.getDY());
			}
			
			
		}
		
	}
	
	
	
}
		
