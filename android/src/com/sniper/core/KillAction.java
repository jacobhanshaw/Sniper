package com.sniper.core;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.sniper.utility.DbContract;



public class KillAction extends PlayerAction
{

	public enum KillActionType
	{
		CAMERA, GPS
	}
	
	/*
	 * Overloaded constructors allow creation of new objects or pull information from server objects
	 * Super class handles much of the implementation
	 * 
	 */
	
	public KillAction(KillActionType actionType)
	{
		super();
		
		setIsVerified(false);
		
		if(actionType == KillActionType.GPS)
		{
			parseObject.put(DbContract.PlayerAction.PLAYER, null);
			parseObject.put(DbContract.PlayerAction.TARGET, ParseUser.getCurrentUser().getObjectId());
		}
	}

	public KillAction(ParseObject object)
	{
		pullData(object);
	}
	
	public KillAction(String objectId)
	{
		super(objectId);
	}

	public void pull()
	{
		super.pull();
	}
	
	public void delete()
	{
		super.delete();
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
	
	public void setPlayer(String target) {
		parseObject.put(DbContract.PlayerAction.PLAYER, target);
	}
	
	public String getTarget()
	{
		return parseObject.getString(DbContract.PlayerAction.TARGET);
	}
	
	public void setTarget(String target) {
		parseObject.put(DbContract.PlayerAction.TARGET, target);
	}
	
	public void setPhotoURL(String url)
	{
		parseObject.put(DbContract.PlayerAction.URL, url);
	}
	
	public String getPhotoURL()
	{
		return parseObject.getString(DbContract.PlayerAction.TARGET);
	}
	
	/**
	 * @return the verified
	 */
	public boolean getIsVerified()
	{
		return parseObject.getBoolean(DbContract.PlayerAction.IS_VERIFIED);
	}

	/**
	 * @param isVerified
	 *            the isVerified to set
	 */
	public void setIsVerified(boolean isVerified)
	{
		parseObject.put(DbContract.PlayerAction.IS_VERIFIED, isVerified);
	}
	
}
