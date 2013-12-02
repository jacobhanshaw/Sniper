package com.sniper;

import com.sniper.core.AWSRequest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class ActivityKillConfirm extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AWSRequest request = new AWSRequest();
		
		setContentView(R.layout.activity_kill_confirm);
	}
	
	public void confirmYes(View v) {
		
	}
	
	public void confirmNo(View v) {
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_kill_confirm, menu);
		return true;
	}

}
