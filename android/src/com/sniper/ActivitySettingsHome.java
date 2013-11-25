package com.sniper;

import java.io.FileNotFoundException;
import java.io.InputStream;

import com.parse.ParseUser;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ActivitySettingsHome extends FragmentActivity {
	private static final int SELECT_PHOTO = 100;
	
	private ImageView userImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_home);
		
		TextView username = (TextView) findViewById(R.id.username);
		username.setText(ParseUser.getCurrentUser().getUsername());
		
		TextView email = (TextView) findViewById(R.id.email);
		email.setText(ParseUser.getCurrentUser().getEmail());
		
		userImageView = (ImageView) findViewById(R.id.user_image);
		userImageView.setImageResource(R.drawable.target_image);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_settings_home, menu);
		return true;
	}

	public void ChangePicture(View view){
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, SELECT_PHOTO);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

	    switch(requestCode) { 
	    case SELECT_PHOTO:
	        if(resultCode == RESULT_OK){  
	            Uri selectedImage = imageReturnedIntent.getData();
	            InputStream imageStream;
				try {
					imageStream = getContentResolver().openInputStream(selectedImage);
		            Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
		            userImageView.setImageBitmap(yourSelectedImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	}
}
