package com.sniper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.parse.ParseUser;
import com.sniper.core.AWSFileUploadObject;
import com.sniper.core.ApplicationServices;
import com.sniper.core.Camera;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
		userImageView.setImageResource(R.drawable.questionmark);
		
		URL url = null;
		try {
			url = new URL("https://s3.amazonaws.com/sniper_profilepictures/" 
					+ ParseUser.getCurrentUser().getEmail());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		GetImageRequest request = new GetImageRequest(this);
		request.execute(url);
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
		
	public static void receiveResponse(String response)
	{
		
	}
	
	public String getPath(Uri uri) 
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
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
		            
		            Method method = null;
					try
					{
						method = ActivitySettingsHome.class.getMethod("receiveResponse");
					} catch (NoSuchMethodException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					File file = new File(getPath(selectedImage));					
					String title = ParseUser.getCurrentUser().getEmail();
			        ApplicationServices.getInstance().uploadUserPhoto(file, title, method);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	}
	
	class UpdateImage implements Runnable{
		public Bitmap bitmap;
		public UpdateImage(Bitmap bitmap){
			this.bitmap = bitmap;
		}
		public void run(){
			ImageView output = (ImageView) findViewById(R.id.user_image);
			output.setImageBitmap(bitmap);
		}	
	}
	public class GetImageRequest extends AsyncTask<URL, Void, String>
	{
		/** progress dialog to show user that the backup is processing. */
	    private ProgressDialog dialog;
	    /** application context. */
	    private Activity activity;
	    /** application context. */
        private Context context;
	    
		public GetImageRequest(Activity activity){
			this.activity = activity;
			context = activity;
			dialog = new ProgressDialog(context);
		}
		
		 protected void onPreExecute() {
	            this.dialog.setMessage("Loading...");
	            this.dialog.show();
	        }
		 
		 protected void onPostExecute(String result)
			{
				super.onPostExecute(result);
				if (dialog.isShowing()) 
	                dialog.dismiss();
			}
		protected String doInBackground(URL... params)
		{
			URL url = params[0];
			
			try {
				Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
				activity.runOnUiThread(new UpdateImage(image));
			} catch (IOException e) {
				e.printStackTrace();
			}

	        return url.toString();
		}
		
	}
}
