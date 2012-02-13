package com.erike.virtualpet;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class PetGame extends Activity {
	
	private static final String TAG = "PetGame";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.d(TAG, "Constructor");
        setContentView(new PetView(this));
    }
}