package com.sniper.core;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.sniper.ActivityKillConfirm;
import com.sniper.ActivityMain;

public class PushReceiver extends BroadcastReceiver
{

	public static final String PARSE_EXTRA_DATA_KEY = "com.parse.Data";
	public static final String PARSE_JSON_ALERT_KEY = "alert";
	public static final String PARSE_JSON_CHANNELS_KEY = "com.parse.Channel";

	private static final String TAG = "TestBroadcastReceiver";

	@Override
	public void onReceive(Context context, Intent intent)
	{

		try
		{
			String action = intent.getAction();
			String channel = intent.getExtras().getString(
					PARSE_JSON_CHANNELS_KEY);
			JSONObject json = new JSONObject(intent.getExtras().getString(
					PARSE_EXTRA_DATA_KEY));

			if (action.equals("com.sniper.POTENTIAL_KILL"))
			{
				Intent confirmIntent = new Intent(context,
						ActivityKillConfirm.class);
				confirmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Bundle b = new Bundle();
				b.putString("killActionId", json.getString("killActionId"));
				b.putString("URL",  json.getString("URL"));
				b.putString("source", this.getClass().getSimpleName());
				confirmIntent.putExtras(b); 
				
				if (ActivityMain.inBackground)
				{
					NotificationCompat.Builder mBuilder =
						    new NotificationCompat.Builder(context)
						 //   .setSmallIcon(R.drawable.)
						    .setContentTitle("Shot Down")
						    .setContentText(json.getString("killerName") + " claims to have shot you!");
					PendingIntent resultPendingIntent =
						    PendingIntent.getActivity(
						    context,
						    0,
						    confirmIntent,
						    PendingIntent.FLAG_UPDATE_CURRENT
						);
					mBuilder.setContentIntent(resultPendingIntent);
	
					int mNotificationId = (int)System.currentTimeMillis();
					
					NotificationManager mNotifyMgr = 
							(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
					mNotifyMgr.notify(mNotificationId, mBuilder.build());
				} else
				{
					System.out.println("Should Be Doing Shit");
					context.startActivity(confirmIntent);
				}
			} else if (action.equals("com.sniper.CONFIRMED_KILL"))
			{
				// Intent confirmIntent = new Intent(context,
				// ActivityKillConfirm.class);
				// confirmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// context.startActivity(confirmIntent);
				System.out.println("Kill Confirmed");
			}
			// "com.parse.Channel"

			Iterator<String> itr = json.keys();
			while (itr.hasNext())
			{
				String key = (String) itr.next();
				String value = json.getString(key);
				System.out.println("Key : " + key + " Value: " + value);
			}

			// if(channel.equals("KILL_CONFIRM")) {

			// }
			Log.d("Debug", "Received it here");
		} catch (JSONException e)
		{
			Log.d(TAG, "JSONException: " + e.getMessage());
		}

	}

}
