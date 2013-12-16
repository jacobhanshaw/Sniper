package com.sniper.core;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.sniper.utility.DbContract;

public class Mine extends GpsLocation
{
	public Mine()
	{
		super();
		parseObject.put(DbContract.Game.CREATOR, ParseUser.getCurrentUser());
		double lat = GpsLocationService.getLatitude();
		double lng = GpsLocationService.getLongitude();
		parseObject.put(DbContract.GpsLocation.LAT, lat);
		parseObject.put(DbContract.GpsLocation.LNG, lng);
		
		
	}

	public Mine(ParseObject object)
	{
		pullData(object);
	}

	public Mine(String objectId)
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
	
	public double getLongitude()
	{
		return parseObject.getDouble(DbContract.GpsLocation.LNG);
	}
	
	public double getLatitude()
	{
		return parseObject.getDouble(DbContract.GpsLocation.LAT);
	}
	
	public void setPlayer(String p)
	{
		parseObject.put(DbContract.GpsLocation.PLAYER, p);
	}
	
	public String getPlayer()
	{
		return parseObject.getString(DbContract.GpsLocation.PLAYER);
	}
}
