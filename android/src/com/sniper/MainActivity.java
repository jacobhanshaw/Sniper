package com.sniper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.FrameLayout;

import com.parse.ParseObject;
import com.sniper.core.Camera;

public class MainActivity extends FragmentActivity {
	private Camera camera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		camera = new Camera(this);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	    preview.addView(camera);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
