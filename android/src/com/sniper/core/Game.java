package com.sniper.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sniper.utility.DbContract;

public class Game extends SniperParseObject
{

	/*
	 * Overloaded constructors allow creation of new objects or pull information from server objects
	 * Super class handles much of the implementation
	 * 
	 */
	
	public Game()
	{
		super();
		parseObject.put(DbContract.Game.CREATOR, ParseUser.getCurrentUser());
		this.Join(ParseUser.getCurrentUser());
	}

	public Game(ParseObject object)
	{
		pullData(object);
	}
	
	public Game(String objectId)
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
	
	public String GetTargetIdForUser(ParseUser user){
		ArrayList<String> players = this.getPlayers();
		int index = players.indexOf(user.getObjectId());
		return this.getTargetIds().get(index).substring(user.getObjectId().length()+1);		
	}
	
	public void Join(ParseUser user){
		Log.d("game", "hit join"); //object not found for update
		ArrayList<String> players = this.getPlayers();
		if(players.contains(user.getObjectId())){
			return; // don't add user more than once
		}
		players.add(user.getObjectId());
		this.setPlayers(players);
		this.push();
	}
	
	public void Remove(ParseUser user){
		ArrayList<String> players = this.getPlayers();
		players.remove(user.getObjectId());
		this.setPlayers(players);
		this.push();
	}
	
	public void StartGame()
	{
		setStartTime(new Date());
		parseObject.put("notifSent", false);
		push();
	}
	
	public void invitePlayer(String playerEmail)
	{
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
		query.whereEqualTo("email", playerEmail);
		query.findInBackground(new FindCallback<ParseObject>()
		{
			public void done(List<ParseObject> userList, ParseException e)
			{
				if (e == null)
				{
					// send notification to
					userList.get(0);
				} else
				{
					// send email
				}
			}
		});
	}

	/*************************************
	 * Accessor Methods
	 *************************************/
	
	/**
	 * @return the creator
	 */
	public String getObjectId()
	{
		return parseObject.getObjectId();
	}
	
	/**
	 * @return the creator
	 */
	public ParseUser getCreator()
	{
		return parseObject.getParseUser(DbContract.Game.CREATOR);
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return parseObject.getString(DbContract.Game.NAME);
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		parseObject.put(DbContract.Game.NAME, name);
	}

	/**
	 * @return the players
	 */
	public ArrayList<String> getPlayers()
	{
		return convertJSONStringArrayToArrayList(parseObject
				.getJSONArray(DbContract.Game.PLAYERS));
	}

	/**
	 * @param players
	 *            the players to set
	 */
	private void setPlayers(ArrayList<String> playerIds)
	{
		parseObject.put(DbContract.Game.PLAYERS, playerIds);
	}

	/**
	 * @return the targets
	 */
	public ArrayList<String> getTargetIds()
	{
		return convertJSONStringArrayToArrayList(parseObject
				.getJSONArray(DbContract.Game.TARGETS));
	}

	/**
	 * @param targets
	 *            the targets to set
	 */
	public void setTargetIds(ArrayList<String> targetIds)
	{
		parseObject.put(DbContract.Game.TARGETS, targetIds);
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime()
	{
		return parseObject.getDate(DbContract.Game.START_TIME);
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime)
	{
		parseObject.put(DbContract.Game.START_TIME, startTime); // .getTime());
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime()
	{
		return parseObject.getDate(DbContract.Game.END_TIME);
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime)
	{
		parseObject.put(DbContract.Game.END_TIME, endTime); // .getTime());
	}

	/**
	 * @return the houseRules
	 */
	public String getHouseRules()
	{
		return parseObject.getString(DbContract.Game.HOUSE_RULES);
	}

	/**
	 * @param houseRules
	 *            the houseRules to set
	 */
	public void setHouseRules(String houseRules)
	{
		parseObject.put(DbContract.Game.HOUSE_RULES, houseRules);
	}

	/**
	 * @return the safeInside
	 */
	public boolean isSafeInside()
	{
		return parseObject.getBoolean(DbContract.Game.SAFE_INSIDE);
	}

	/**
	 * @param safeInside
	 *            the safeInside to set
	 */
	public void setSafeInside(boolean safeInside)
	{
		parseObject.put(DbContract.Game.SAFE_INSIDE, safeInside);
	}

	/**
	 * @return the moderator
	 */
	public String getModeratorId()
	{
		return parseObject.getString(DbContract.Game.MODERATOR);
	}

	/**
	 * @param moderator
	 *            the moderator to set
	 */
	public void setModeratorId(String moderatorId)
	{
		parseObject.put(DbContract.Game.MODERATOR, moderatorId);
	}

	/**
	 * @return the points
	 */
	public ArrayList<String> getPoints()
	{
		return convertJSONStringArrayToArrayList(parseObject
				.getJSONArray(DbContract.Game.POINTS));
	}

	/**
	 * @param points
	 *            the points to set
	 */
	public void setPoints(ArrayList<String> points)
	{
		parseObject.put(DbContract.Game.POINTS, points);
	}

	/**
	 * @return the locationObjects
	 */
	public ArrayList<String> getLocationObjects()
	{
		return convertJSONStringArrayToArrayList(parseObject
				.getJSONArray(DbContract.Game.LOCATION_OBJECTS));
	}

	/**
	 * @param locationObjects
	 *            the locationObjects to set
	 */
	public void setLocationObjects(ArrayList<String> locationIds)
	{
		parseObject.put(DbContract.Game.LOCATION_OBJECTS, locationIds);
	}

	/**
	 * @return the isPublic
	 */
	public boolean getIsPublic()
	{
		return parseObject.getBoolean(DbContract.Game.IS_PUBLIC);
	}

	/**
	 * @param isPublic
	 *            the isPublic to set
	 */
	public void setIsPublic(boolean isPublic)
	{
		parseObject.put(DbContract.Game.IS_PUBLIC, isPublic);
	}
	
}