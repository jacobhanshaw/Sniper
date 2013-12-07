package com.sniper.core;
/*
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
*/
import com.parse.ParseObject;
//import com.parse.ParsePush;
import com.parse.ParseUser;
import com.sniper.utility.DbContract;

public class KillAction extends PlayerAction
{
	
	public KillAction()
	{
		super();
	}

	public KillAction(ParseObject object)
	{
		pullData(object);
	}

	public void pull()
	{
		super.pull();
	}

	protected void pullData(ParseObject object)
	{
		super.pullData(object);
	}

	public void push()
	{
		super.push();
	}
	
	public ParseUser getTarget()
	{
		return parseObject.getParseUser(DbContract.PlayerAction.TARGET);
	}
	
	public void setPhotoURL(String url)
	{
		parseObject.put(DbContract.PlayerAction.URL, url);
	}
	
	public String getPhotoURL()
	{
		return parseObject.getString(DbContract.PlayerAction.TARGET);
	}
	
/*	public void publish() {
		JSONObject data = new JSONObject();
		try {
			//data.put("killer", killer.getObjectId());
			//data.put("dead", dead.getObjectId());
			data.put("url", url);
			data.put("action", "com.sniper.UPDATE");
			ParsePush push = new ParsePush();
			push.setChannel("AdamTesting");
			push.setData(data);
			push.sendInBackground();
		} catch (JSONException e) {
			Log.e("Debug", "KillAction Publish");
		}
	} */
}
