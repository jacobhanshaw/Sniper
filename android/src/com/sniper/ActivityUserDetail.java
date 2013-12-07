package com.sniper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.parse.ParseUser;
import com.sniper.utility.LoadUserImage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityUserDetail extends FragmentActivity {
	public static ParseUser User;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_detail);
		
		TextView username = (TextView) findViewById(R.id.UserName);
		username.setText(User.getUsername());
		
		//TODO set points
		//TextView email = (TextView) findViewById(R.id.UserName);
		//email.setText(ParseUser.getCurrentUser().getEmail());
		
		TextView email = (TextView) findViewById(R.id.Email);
		email.setText(User.getEmail());
		
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
