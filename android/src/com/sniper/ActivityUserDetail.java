package com.sniper;

import com.parse.ParseUser;
import com.sniper.utility.LoadUserImage;
import com.sniper.utility.MenuHelper;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityUserDetail extends FragmentActivity {
	public static ParseUser User;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    return MenuHelper.onOptionsItemSelected(item, this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_detail);
		
		TextView username = (TextView) findViewById(R.id.UserName);
		username.setText(User.getUsername());
		
		//TODO set points, didn't get done. not enough time
		
		TextView email = (TextView) findViewById(R.id.Email);
		email.setText(User.getEmail());
		
		//start user image as question mark, then start load
		ImageView userImageView = (ImageView) findViewById(R.id.user_image);
		userImageView.setImageResource(R.drawable.questionmark);		
		LoadUserImage.GetImage(User, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_user_detail, menu);
		return true;
	}
}
