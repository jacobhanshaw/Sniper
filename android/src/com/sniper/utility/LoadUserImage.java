package com.sniper.utility;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.parse.ParseUser;
import com.sniper.R;

public class LoadUserImage {
	public static void GetImage(ParseUser user, Activity activity){
		URL url = null;
		try {
			url = new URL("https://s3.amazonaws.com/sniper_profilepictures/" 
					+ user.getObjectId());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}		
		GetImageRequest request = new GetImageRequest(activity);
		request.execute(url);
	}
	
	class UpdateImage implements Runnable{
		public Bitmap bitmap;
		public Activity activity;
		public UpdateImage(Bitmap bitmap, Activity activity){
			this.bitmap = bitmap;
			this.activity = activity;
		}
		public void run(){
			ImageView output = (ImageView) activity.findViewById(R.id.user_image);
			output.setImageBitmap(bitmap);
		}	
	}
	public static class GetImageRequest extends AsyncTask<URL, Void, String>
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
				activity.runOnUiThread(new LoadUserImage().new UpdateImage(image, activity));
			} catch (IOException e) {
				e.printStackTrace();
			}

	        return url.toString();
		}
		
	}
}
