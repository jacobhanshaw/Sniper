package com.sniper.core;

import java.util.Date;

import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.sniper.utility.DbContract;

public class PlayerAction extends SniperParseObject
{

	public PlayerAction()
	{
		super();
		
		Date currentDate = new Date();
		parseObject.put(DbContract.PlayerAction.PLAYER, ParseUser.getCurrentUser());
		Log.v("Debug", "Logging Kill as: " + ParseUser.getCurrentUser().getEmail());
		parseObject.put(DbContract.PlayerAction.CREATED, currentDate);
		
	}

	public PlayerAction(ParseObject object)
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
	
	public ParseUser getPlayer()
	{
		return parseObject.getParseUser(DbContract.PlayerAction.PLAYER);
	}
	
	public Date getCreatedTime()
	{
		return parseObject.getDate(DbContract.PlayerAction.CREATED);
	}
	
}
