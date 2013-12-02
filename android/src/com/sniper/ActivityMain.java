package com.sniper;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.sniper.core.ApplicationServices;
import com.sniper.core.Camera;

public class ActivityMain extends FragmentActivity
{
	private Camera camera;
	// private static final int SELECT_PHOTO = 100;
	String[] targetNames =
	{};

	public static final String ACTION = "com.androidbook.parse.TestPushAction";
	public static final String PARSE_EXTRA_DATA_KEY = "com.parse.Data";
	public static final String PARSE_JSON_ALERT_KEY = "alert";
	public static final String PARSE_JSON_CHANNELS_KEY = "com.parse.Channel";

	private static final String TAG = "TestBroadcastReceiver";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ParseAnalytics.trackAppOpened(getIntent());

		//Log.v("Debug", ParseUser.getCurrentUser().getEmail().toString());

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			setContentView(R.layout.activity_main);
		} else
		{
			setContentView(R.layout.activity_main_landscape);
		}

		camera = new Camera(this);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(camera);

		ImageView iv = (ImageView) findViewById(R.id.target_image);
		iv.setImageResource(R.drawable.target_image);

		Button b = (Button) findViewById(R.id.weapon_button);
    	b.setText(ActivityArmoryHome.myStringArray[
    	     ActivityArmoryHome.selectedPosition]);
		// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		// intent.setType("image/*");
		// startActivityForResult(intent, SELECT_PHOTO)
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
//                Toast.makeText(
//                        c, 
//                        "Select "+ActivityArmoryHome.myStringArray[which], 
//                        Toast.LENGTH_SHORT
//                        )
//                        .show();
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

}
