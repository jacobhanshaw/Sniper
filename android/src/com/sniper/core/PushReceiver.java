package com.sniper.core;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sniper.ActivityKillConfirm;

public class PushReceiver extends BroadcastReceiver {
	
	public static final String PARSE_EXTRA_DATA_KEY = "com.parse.Data";
	public static final String PARSE_JSON_ALERT_KEY = "alert";
	public static final String PARSE_JSON_CHANNELS_KEY = "com.parse.Channel";
	
	private static final String TAG = "TestBroadcastReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {

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

			if(channel.equals("KILL_CONFIRM")) {
				Intent confirmIntent = new Intent(context, ActivityKillConfirm.class);
				context.startActivity(confirmIntent);
			}
			Log.d("Debug", "Received it here");
		} catch (JSONException e)
		{
			Log.d(TAG, "JSONException: " + e.getMessage());
		}

	}

}
