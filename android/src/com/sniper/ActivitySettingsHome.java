package com.sniper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import com.parse.ParseUser;
import com.sniper.core.ApplicationServices;
import com.sniper.utility.LoadUserImage;
import com.sniper.utility.MenuHelper;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivitySettingsHome extends FragmentActivity {
	private static final int SELECT_PHOTO = 100;
	private TextView userName, email;
	private ImageView userImageView;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    return MenuHelper.onOptionsItemSelected(item, this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_home);
		
		userName = (TextView) findViewById(R.id.username);
		userName.setText(ParseUser.getCurrentUser().getUsername());
		
		email = (TextView) findViewById(R.id.email);
		email.setText(ParseUser.getCurrentUser().getEmail());
		
		userImageView = (ImageView) findViewById(R.id.user_image);
		userImageView.setImageResource(R.drawable.questionmark);
		
		LoadUserImage.GetImage(ParseUser.getCurrentUser(), this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_settings_home, menu);
				
		return true;
	}
	
	public void ChangePassword(View view){
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.input_text, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		TextView label = (TextView) promptsView
				.findViewById(R.id.label);
		label.setText("Enter New Password:");
		
		final EditText userInput = (EditText) promptsView
				.findViewById(R.id.inputText);

		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				// get user input and set it to result
				// edit text
			    	String newText = userInput.getText().toString();
			    	//userName.setText(userInput.getText());
			    	ParseUser.getCurrentUser().setPassword(newText);
			    	ParseUser.getCurrentUser().saveInBackground();
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	
	public void ChangeEmail(View view){
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.input_text, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		TextView label = (TextView) promptsView
				.findViewById(R.id.label);
		label.setText("Enter New Email:");
		
		final EditText userInput = (EditText) promptsView
				.findViewById(R.id.inputText);

		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				// get user input and set it to result
				// edit text
			    	String newText = userInput.getText().toString();
			    	//userName.setText(userInput.getText());
			    	ParseUser.getCurrentUser().setEmail(newText);
			    	email.setText(ParseUser.getCurrentUser().getEmail());
			    	ParseUser.getCurrentUser().saveInBackground();
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	
	public void ChangeUserName(View view){
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.input_text, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		final EditText userInput = (EditText) promptsView
				.findViewById(R.id.inputText);

		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				// get user input and set it to result
				// edit text
			    	String newText = userInput.getText().toString();
			    	//userName.setText(userInput.getText());
			    	ParseUser.getCurrentUser().setUsername(newText);
			    	userName.setText(ParseUser.getCurrentUser().getUsername());
			    	ParseUser.getCurrentUser().saveInBackground();
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	
	public void ChangePicture(View view){
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, SELECT_PHOTO);
	}
		
	public static void receiveResponse(String response)
	{
		
	}
	
	//gets exact path from uri, would have debugged further if had more time
	public String getPath(Uri uri) 
    {
		//TODO crashes if you select same picture twice, need to fix
		// should get rid of deprecated method
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor == null){
        	 return null;
        }
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(colIndex);
        cursor.close();
        return s;
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
	    
	    if(SELECT_PHOTO == requestCode && resultCode == RESULT_OK){
	    	//this should always be the case, but oh well
	    	Uri selectedImage = imageReturnedIntent.getData();
            InputStream imageStream;
			try {
				imageStream = getContentResolver().openInputStream(selectedImage);
				
	            Bitmap profilePic = BitmapFactory.decodeStream(imageStream);
	            //scale down bitmap, user might save image thats too big
	            int nh = (int) ( profilePic.getHeight() * 
	            		(512.0 / profilePic.getWidth()) );
	            Bitmap scaled = Bitmap.createScaledBitmap(profilePic, 512, nh, true);
	            profilePic = scaled;
	            
	            userImageView.setImageBitmap(profilePic);
	            
	            // update "cache" of images
	            LoadUserImage.UpdateImage(ParseUser.getCurrentUser(),
	            		profilePic);
	           		            
	            Method method = null;
				try
				{
					method = ActivitySettingsHome.class.getMethod("receiveResponse");
				} catch (NoSuchMethodException e)
				{
					// if its not there, we just wont call it, its ok
				}
				
				//upload to aws the users new profile image
				String path = getPath(selectedImage);
				File file = new File(path);					
				String title = ParseUser.getCurrentUser().getObjectId();
		        ApplicationServices.getInstance().uploadUserPhoto(file, title, method);		        
		        
			} catch (FileNotFoundException e) {
				// ignore
			}
	    }
	    
	}
	
}
