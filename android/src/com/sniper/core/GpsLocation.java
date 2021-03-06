package com.sniper.core;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.sniper.utility.DbContract;

public class GpsLocation extends SniperParseObject
{
	public GpsLocation()
	{
		super();
		parseObject.put(DbContract.Game.CREATOR, ParseUser.getCurrentUser());
		double lat = GpsLocationService.getLatitude();
		double lng = GpsLocationService.getLongitude();
	}

	public GpsLocation(ParseObject object)
	{
		pullData(object);
	}
	
	public GpsLocation(String objectId)
	{
		super(objectId);
	}

	public void pull()
	{
		super.pull();
	}

	protected void pullData(ParseObject object)
	{
		super.pullData(object);

		String debugInfo = parseObject.getString(DbContract.Game.DEBUGINFO);
		if (debugInfo != null && !debugInfo.equals(""))
			System.out.println("Error in " + this.getClass().getName()
					+ " pull method: " + debugInfo);
	}

	public void push()
	{
		super.push();
	}
	
	public void delete()
	{
		super.delete();
	}
}
