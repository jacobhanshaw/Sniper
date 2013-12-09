package com.sniper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;
import com.sniper.core.ApplicationServices;
import com.sniper.core.Camera;
import com.sniper.core.Game;
import com.sniper.utility.LoadUserImage;
import com.sniper.utility.MenuHelper;
import com.sniper.utility.UtilityMethods;

public class ActivityMain extends FragmentActivity
{
	private Camera camera;
	// private static final int SELECT_PHOTO = 100;
	
	List<ParseUser> targets = new ArrayList<ParseUser>();
	String[] targetUserNames =	{ };
	private ProgressDialog progressDialog;
	public static ParseUser target;

	public static final String ACTION = "com.androidbook.parse.TestPushAction";
	public static final String PARSE_EXTRA_DATA_KEY = "com.parse.Data";
	public static final String PARSE_JSON_ALERT_KEY = "alert";
	public static final String PARSE_JSON_CHANNELS_KEY = "com.parse.Channel";

	private static final String TAG = "TestBroadcastReceiver";
	
	private void GetTargets(final ActivityMain act){
		this.progressDialog = ProgressDialog.show(this, "",
				"Loading Targets...", true);
		UtilityMethods.GetTargets(new FindCallback<ParseUser>(){
			@Override
			public void done(List<ParseUser> targets, ParseException e) {
				if(e==null){
					Log.d("num targets", ""+targets.size());
					act.targets = targets;
					targetUserNames = new String[targets.size()];
					boolean foundTarget = false;
			    	for(int i=0; i<targets.size(); i++){
			    		act.targetUserNames[i] = targets.get(i).getUsername();
			    		if(target != null && targets.get(i).getObjectId().equals(target.getObjectId())){
			    			foundTarget = true;
			    		}
			    	}
			    	if(!foundTarget){
			    		if(targets.size() > 0)
			    			target = targets.get(0);
			    		else
			    			target = null;
			    	}
			    	ActivityMain.this.progressDialog.dismiss();
			    	if(target != null){
			    		NewTarget(act);
			    	}
				}
			}});		
	}
	
	private void NewTarget(Activity activity){
		Button b = (Button) findViewById(R.id.name_button);
    	b.setText(target.getUsername());
    	LoadUserImage.GetImage(target, activity);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ParseAnalytics.trackAppOpened(getIntent());
		String userChannel = "user_" + ParseUser.getCurrentUser().getObjectId();
		PushService.subscribe(this, userChannel, ActivityKillConfirm.class);
		//Log.v("Debug", ParseUser.getCurrentUser().getEmail().toString());

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			setContentView(R.layout.activity_main);
		} else
		{
			setContentView(R.layout.activity_main_landscape);
		}

		if(android.hardware.Camera.getNumberOfCameras() > 0){			
			camera = new Camera(this);
			FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
			preview.addView(camera);
		}

		ImageView iv = (ImageView) findViewById(R.id.user_image);
		iv.setImageResource(R.drawable.questionmark);

		Button b = (Button) findViewById(R.id.weapon_button);
    	b.setText(ActivityArmoryHome.myStringArray[
    	     ActivityArmoryHome.selectedPosition]);
		// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		// intent.setType("image/*");
		// startActivityForResult(intent, SELECT_PHOTO)
    	
    	GetTargets(this);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void SelectTargetClick(View view){
		AlertDialog.Builder builder = 
	            new AlertDialog.Builder(this);
	        builder.setTitle("Select Target");
	                  
	        final Activity act = this;
	        final Context c = this;
	        builder.setSingleChoiceItems(
	                targetUserNames, 
	                0, 
	                new DialogInterface.OnClickListener() {
	             
	            @Override
	            public void onClick(
	                    DialogInterface dialog, 
	                    int which) {
	            	target = targets.get(which);
	            	NewTarget(act);
	            	dialog.dismiss();
	            }
	        });
	        AlertDialog alert = builder.create();
	        alert.show();
	}

	public void showDialogButtonClick(View view) {
        Log.i(TAG, "show Dialog ButtonClick");
        AlertDialog.Builder builder = 
            new AlertDialog.Builder(this);
        builder.setTitle("Shoot With");
                  
        final Context c = this;
        builder.setSingleChoiceItems(
                ActivityArmoryHome.myStringArray, 
                ActivityArmoryHome.selectedPosition, 
                new DialogInterface.OnClickListener() {
             
            @Override
            public void onClick(
                    DialogInterface dialog, 
                    int which) {
            	ActivityArmoryHome.selectedPosition = which;
            	Button b = (Button) findViewById(R.id.weapon_button);
            	b.setText(ActivityArmoryHome.myStringArray[
            	     ActivityArmoryHome.selectedPosition]);
            	dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
	
	public void onReceive(Context context, Intent intent)
	{
		try
		{
			String action = intent.getAction();

			// "com.parse.Channel"
			String channel = intent.getExtras().getString(PARSE_JSON_CHANNELS_KEY);
			JSONObject json = new JSONObject(intent.getExtras().getString(PARSE_EXTRA_DATA_KEY));
			

			Iterator<String> itr = json.keys();
			while (itr.hasNext())
			{
				String key = (String) itr.next();
				String value = json.getString(key);
			}
			Log.d("Debug", "Received it main");
		} catch (JSONException e)
		{
			Log.d(TAG, "JSONException: " + e.getMessage());
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    return MenuHelper.onOptionsItemSelected(item, this);
	}
}
