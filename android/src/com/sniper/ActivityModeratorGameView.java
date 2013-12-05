package com.sniper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ActivityModeratorGameView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moderator_game_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_moderator_game_view, menu);
		return true;
	}

}
