package com.sniper.utility;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.parse.ParseUser;
import com.sniper.ActivityTargets;
import com.sniper.R;

//used to make loading images easier, some hackish things to make things
//work quickly and easily. no bugs though
public class LoadUserImage {
	//create hash map of images already loaded so that don't have to re-query constantly
	//for common images
	private static HashMap<String, UserImage> map = new HashMap<String, UserImage>();
	
	//create class for images to add extra data
	public class UserImage{
		public Bitmap image;
		private Date date;
		public UserImage(Bitmap image){
			this(image, new Date());
		}
		public UserImage(Bitmap image, Date date){
			this.date = date;
			this.image = image;
		}
		//returns that the image should be re-downloaded if it
		//hasn't been checked in more than 5 minutes. could change this time
		public boolean ShouldUpdate(){
			Date now = new Date();
			final Calendar c = Calendar.getInstance();
			c.add(Calendar.MINUTE, -5);
			now.setTime(c.getTimeInMillis());
			
			return now.after(date);
		}
	}
	
	//if an image is acquired from outside of this utility,
	//add it to the "cache" this way
	public static void UpdateImage(ParseUser user, Bitmap image){
		 LoadUserImage.map.put(user.getObjectId(), 
				 new LoadUserImage().new UserImage(image));         		
	}
	
	// update "userImage" in the xml of the current activity from the image
	// for the user
	public static void GetImage(ParseUser user, Activity activity){
		//first try getting the image from the "cache"
		UserImage image = map.get(user.getObjectId());
		if(image != null && !image.ShouldUpdate()){
			activity.runOnUiThread(new LoadUserImage().new UpdateImage(image.image, activity));
			return;
		}
		
		// If the image was not presented or needed to be updated, begin
		// new async task to download
		URL url = null;
		try {
			url = new URL("https://s3.amazonaws.com/sniper_profilepictures/" 
					+ user.getObjectId());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}		
		GetImageRequest request = new GetImageRequest(activity, user);
		request.execute(url);
	}
	
	// update "userImage" in array of images. currently only supports for 
	// ActivityTargets but can easily be updated
	public static void GetImage(ParseUser user, Activity activity, int index){
		//check cache
		UserImage image = map.get(user.getObjectId());
		if(image != null && !image.ShouldUpdate()){
			activity.runOnUiThread(new LoadUserImage().new UpdateImage(image.image, activity, index));
			return;
		}
		
		//start download if needed
		URL url = null;
		try {
			url = new URL("https://s3.amazonaws.com/sniper_profilepictures/" 
					+ user.getObjectId());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}		
		GetImageRequest request = new GetImageRequest(activity, user, index);
		request.execute(url);
	}
	
	// specifically for updating ActivityKillConfirm
	public static void ShowKillImage(Activity activity, String urlS){
		URL url = null;
		try {
			url = new URL(urlS);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}		
		GetImageRequest request = new GetImageRequest(activity, null);
		request.execute(url);
	}
	
	// execute this runnable on ui thread to update "user_image"
	// or bitmaps in ActivityTargets
	class UpdateImage implements Runnable{
		public Bitmap bitmap;
		public Activity activity;
		public int index;
		public UpdateImage(Bitmap bitmap, Activity activity){
			this.bitmap = bitmap;
			this.activity = activity;
		}
		public UpdateImage(Bitmap bitmap, Activity activity, int index){
			this.bitmap = bitmap;
			this.index = index;
			this.activity = activity;
		}
		public void run(){
			if(activity instanceof  ActivityTargets){
				ActivityTargets targetActivity = (ActivityTargets)activity;
				targetActivity.images[index] = bitmap;
				targetActivity.SetupList();
				return;
			}
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
	    
        private String userId = null;
        
        private int index;
        
		public GetImageRequest(Activity activity, ParseUser user){
			this.activity = activity;
			context = activity;
			dialog = new ProgressDialog(context);
			if(user != null)
				userId = user.getObjectId();
		}
		
		public GetImageRequest(Activity activity, ParseUser user, int index){
			this.activity = activity;
			context = activity;
			dialog = new ProgressDialog(context);
			userId = user.getObjectId();
			this.index = index;
		}
		
		 protected void onPreExecute() {
	            this.dialog.setMessage("Loading Image...");
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
				//always scale down image as user may have uploaded one too big to be displayed
				int nh = (int) ( image.getHeight() * 
	            		(512.0 / image.getWidth()) );
				image = Bitmap.createScaledBitmap(image, 512, nh, true);
				
				// add image to "cache"
				if(userId != null)
					LoadUserImage.map.put(userId, 
						 new LoadUserImage().new UserImage(image)); 	
				
				// update display
				if(activity instanceof  ActivityTargets){
					activity.runOnUiThread(new LoadUserImage().new UpdateImage(image, activity, index));
				}
				else{
					activity.runOnUiThread(new LoadUserImage().new UpdateImage(image, activity));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

	        return url.toString();
		}
		
	}
}
