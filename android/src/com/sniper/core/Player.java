package com.sniper.core;

import java.util.ArrayList;

import com.parse.ParseObject;
import com.sniper.utility.DbContract;

public class Player extends SniperParseObject
{

	public Player()
	{
		super();
	}

	public Player(ParseObject object)
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

	/*************************************
	 * Accessor Methods
	 *************************************/
	/**
	 * @return the name
	 */
	public String getName()
	{
		return parseObject.getString(DbContract.Player.NAME);
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		parseObject.put(DbContract.Player.NAME, name);
	}

	/**
	 * @return the objectId
	 */
	public String getObjectId()
	{
		return parseObject.getObjectId();
	}

	/**
	 * @return the photos
	 */
	public ArrayList<String> getPhotoURLs()
	{
		return convertJSONStringArrayToArrayList(parseObject
				.getJSONArray(DbContract.Player.PHOTOURLS));
	}

	/**
	 * @param photos
	 *            the photos to set
	 */
	public void setPhotoURLs(ArrayList<String> photoURLs)
	{
		parseObject.put(DbContract.Player.PHOTOURLS, photoURLs);
	}

	/**
	 * @return the alive
	 */
	public boolean isAlive()
	{
		return parseObject.getBoolean(DbContract.Player.IS_ALIVE);
	}

	/**
	 * @param alive
	 *            the alive to set
	 */
	public void setAlive(boolean alive)
	{
		parseObject.put(DbContract.Player.IS_ALIVE, alive);
	}

	/**
	 * @return the role
	 */
	public String getRole()
	{
		return parseObject.getString(DbContract.Player.ROLE);
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role)
	{
		parseObject.put(DbContract.Player.ROLE, role);
	}

	/**
	 * @return the arsenal
	 */
	public ArrayList<String> getArsenal()
	{
		return convertJSONStringArrayToArrayList(parseObject
				.getJSONArray(DbContract.Player.ARSENAL));
	}

	/**
	 * @param arsenal
	 *            the arsenal to set
	 */
	public void setArsenal(ArrayList<String> arsenal)
	{
		parseObject.put(DbContract.Player.ARSENAL, arsenal);
	}

}
