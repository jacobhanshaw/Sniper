package com.sniper.core;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.sniper.utility.DbContract;

public class SniperParseObject
{
	protected ParseObject parseObject;

	/*
	 * Super class of all ParseObjects. Abstracts most of server access for all subclasses. Allows us to change 
	 * server calls in a single place
	 * 
	 */
	
	public SniperParseObject()
	{
		parseObject = new ParseObject(this.getClass().getSimpleName());
		Date currentDate = new Date();
		parseObject.put(DbContract.Game.CREATED, currentDate);
	}

	public SniperParseObject(ParseObject object)
	{
		pullData(object);
	}
	
	public SniperParseObject(String objectId)
	{
		ParseQuery<ParseObject> query = ParseQuery.getQuery(this.getClass().getSimpleName());
		query.getInBackground(objectId, new GetCallback<ParseObject>() {
		  public void done(ParseObject object, ParseException e) {
		    if (e == null) {
		      parseObject = object;
		    } else {
		      // something went wrong
		    }
		  }
		});
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
	
	public void delete()
	{
		parseObject.deleteEventually();
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
