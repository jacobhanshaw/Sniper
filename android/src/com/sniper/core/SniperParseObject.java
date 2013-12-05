package com.sniper.core;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.text.format.Time;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.sniper.utility.DbContract;

public class SniperParseObject
{
	protected ParseObject parseObject;

	public SniperParseObject()
	{
		parseObject = new ParseObject(this.getClass().getName());
		Time now = new Time();
		now.setToNow();
		parseObject.put(DbContract.Game.CREATED, now);
	}

	public SniperParseObject(ParseObject object)
	{
		pullData(object);
	}

	public void pull()
	{
		parseObject.fetchInBackground(new GetCallback<ParseObject>()
		{
			public void done(ParseObject gameObject, ParseException e)
			{
				if (e == null)
					pullData(gameObject);
				else
					pull();
			}
		});
	}

	protected void pullData(ParseObject object)
	{
		parseObject = object;
	}

	public void push()
	{
		parseObject.saveEventually(new SaveCallback()
		{
			public void done(ParseException e)
			{
				if (e != null)
					push();
			}
		});
	}
	
	protected ArrayList<String> convertJSONStringArrayToArrayList(
			JSONArray jsonArray)
	{
		ArrayList<String> list = new ArrayList<String>();
		if (jsonArray != null)
		{
			int len = jsonArray.length();
			for (int i = 0; i < len; i++)
			{
				try
				{
					list.add(jsonArray.get(i).toString());
				} catch (JSONException e)
				{
					e.printStackTrace();
				}
			}
		}

		return list;
	}
	
}
