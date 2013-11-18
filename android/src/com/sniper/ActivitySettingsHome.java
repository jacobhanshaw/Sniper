package com.sniper;

import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ActivitySettingsHome extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_home);
		
		TextView username = (TextView) findViewById(R.id.username);
		username.setText(ParseUser.getCurrentUser().getUsername());
		
		TextView email = (TextView) findViewById(R.id.email);
		email.setText(ParseUser.getCurrentUser().getEmail());
		
		ImageView iv = (ImageView) findViewById(R.id.user_image);
		iv.setImageResource(R.drawable.target_image);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_settings_home, menu);
		return true;
	}

}
