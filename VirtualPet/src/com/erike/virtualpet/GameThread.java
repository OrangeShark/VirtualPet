package com.erike.virtualpet;

import android.graphics.Canvas;
import android.util.Log;

class GameThread extends Thread implements Runnable{
	private static final String TAG = "GameThread";

	private static final long DELAY = 70;
	
	private PetView _gameView;
	private boolean _run = false;
		
	public GameThread(PetView gameView){
		_gameView = gameView;
		Log.d(TAG, "Constructor");
	}
		
	public void setRunning(boolean run){
		_run = run;
	}
		
	@Override
	public void run(){
		
		long beforeTime, timeDiff, sleep;
		
		beforeTime = System.currentTimeMillis();
		
		Canvas c;
		while (_run){
			c = null;
			try{
				c = _gameView.getHolder().lockCanvas(null);
				synchronized (_gameView.getHolder()) {
					_gameView.update();
					_gameView.onDraw(c);
				}
			} finally {
				if (c != null){
					_gameView.getHolder().unlockCanvasAndPost(c);
				}
			}
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;
			
			if (sleep < 0)
				sleep = 5;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				Log.d(TAG, "interrupted");
			}
			
			beforeTime = System.currentTimeMillis();
		}
			
	}
}