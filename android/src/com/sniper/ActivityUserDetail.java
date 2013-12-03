package com.sniper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.parse.ParseUser;
import com.sniper.ActivitySettingsHome.GetImageRequest;
import com.sniper.ActivitySettingsHome.UpdateImage;

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
		
		//TextView email = (TextView) findViewById(R.id.UserName);
		//email.setText(ParseUser.getCurrentUser().getEmail());
		
		TextView email = (TextView) findViewById(R.id.Email);
		email.setText(User.getEmail());
		
		ImageView userImageView = (ImageView) findViewById(R.id.user_image);
		userImageView.setImageResource(R.drawable.questionmark);
		
		URL url = null;
		try {
			url = new URL("https://s3.amazonaws.com/sniper_profilepictures/" 
					+ User.getObjectId());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}		
		GetImageRequest request = new GetImageRequest(this);
		request.execute(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_user_detail, menu);
		return true;
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
