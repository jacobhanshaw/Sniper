package com.sniper.core;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.parse.ParsePush;

public class KillAction extends PlayerAction
{
	Player killer;
	Player dead;
	String url;
	
	public KillAction()
	{
		super();
	}
	
	public void publish() {
		JSONObject data = new JSONObject();
		try {
			//data.put("killer", killer.getObjectId());
			//data.put("dead", dead.getObjectId());
			data.put("url", url);
			ParsePush push = new ParsePush();
			push.setChannel("AdamTesting");
			push.setData(data);
			push.sendInBackground();
		} catch (JSONException e) {
			Log.e("Debug", "KillAction Publish");
		}
		
		
		
	}
}
