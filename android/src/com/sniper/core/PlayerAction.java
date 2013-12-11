package com.sniper.core;

import java.util.Date;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.sniper.utility.DbContract;

public class PlayerAction extends SniperParseObject
{

	public PlayerAction()
	{
		super();
		
		Date currentDate = new Date();
		parseObject.put(DbContract.PlayerAction.PLAYER, ParseUser.getCurrentUser().getObjectId());
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
	
	public String getPlayer()
	{
		return parseObject.getString(DbContract.PlayerAction.PLAYER);
	}
	
	public Date getCreatedTime()
	{
		return parseObject.getDate(DbContract.PlayerAction.CREATED);
	}
	
}
