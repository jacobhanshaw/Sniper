package com.sniper.core;

//import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sniper.utility.DbContract;

public class Game
{

	private ParseObject game;

	public Game()
	{
		game = new ParseObject(Game.class.getSimpleName());
		game.put(DbContract.Game.CREATOR, ParseUser.getCurrentUser());
	}

	public Game(ParseObject gameObject)
	{
		pullDataFromParseObject(gameObject);
	}

	public void pullParseObject()
	{
		game.fetchInBackground(new GetCallback<ParseObject>()
		{
			public void done(ParseObject gameObject, ParseException e)
			{
				if (e == null)
					pullDataFromParseObject(gameObject);
				else
					pullParseObject();
			}
		});
	}

	private void pullDataFromParseObject(ParseObject gameObject)
	{
		game = gameObject;

		String debugInfo = game.getString(DbContract.Game.DEBUGINFO);
		if (debugInfo != null && !debugInfo.equals(""))
			System.out.println("Error in " + Game.class.getSimpleName()
					+ " pull method: " + debugInfo);
	}

	public void pushParseObject()
	{
		game.saveEventually(new SaveCallback()
		{
			public void done(ParseException e)
			{
				if (e != null)
					pushParseObject();
			}
		});
	}

	private ArrayList<String> convertJSONStringArrayToArrayList(
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
	public ParseUser getCreator()
	{
		return game.getParseUser(DbContract.Game.CREATOR);
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return game.getString(DbContract.Game.NAME);
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		game.put(DbContract.Game.NAME, name);
		;
	}

	/**
	 * @return the players
	 */
	public ArrayList<String> getPlayers()
	{
		return convertJSONStringArrayToArrayList(game
				.getJSONArray(DbContract.Game.PLAYERS));
	}

	/**
	 * @param players
	 *            the players to set
	 */
	public void setPlayers(ArrayList<String> playerIds)
	{
		game.put(DbContract.Game.PLAYERS, playerIds);
	}

	/**
	 * @return the targets
	 */
	public ArrayList<String> getTargetIds()
	{
		return convertJSONStringArrayToArrayList(game
				.getJSONArray(DbContract.Game.TARGETS));
	}

	/**
	 * @param targets
	 *            the targets to set
	 */
	public void setTargetIds(ArrayList<String> targetIds)
	{
		game.put(DbContract.Game.TARGETS, targetIds);
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime()
	{
		return game.getDate(DbContract.Game.START_TIME);
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime)
	{
		game.put(DbContract.Game.START_TIME, startTime); // .getTime());
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime()
	{
		return game.getDate(DbContract.Game.END_TIME);
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime)
	{
		game.put(DbContract.Game.END_TIME, endTime); // .getTime());
	}

	/**
	 * @return the houseRules
	 */
	public String getHouseRules()
	{
		return game.getString(DbContract.Game.HOUSE_RULES);
	}

	/**
	 * @param houseRules
	 *            the houseRules to set
	 */
	public void setHouseRules(String houseRules)
	{
		game.put(DbContract.Game.HOUSE_RULES, houseRules);
	}

	/**
	 * @return the safeInside
	 */
	public boolean isSafeInside()
	{
		return game.getBoolean(DbContract.Game.SAFE_INSIDE);
	}

	/**
	 * @param safeInside
	 *            the safeInside to set
	 */
	public void setSafeInside(boolean safeInside)
	{
		game.put(DbContract.Game.SAFE_INSIDE, safeInside);
	}

	/**
	 * @return the moderator
	 */
	public String getModeratorId()
	{
		return game.getString(DbContract.Game.MODERATOR);
	}

	/**
	 * @param moderator
	 *            the moderator to set
	 */
	public void setModeratorId(String moderatorId)
	{
		game.put(DbContract.Game.MODERATOR, moderatorId);
	}

	/**
	 * @return the points
	 */
	public ArrayList<String> getPoints()
	{
		return convertJSONStringArrayToArrayList(game
				.getJSONArray(DbContract.Game.POINTS));
	}

	/**
	 * @param points
	 *            the points to set
	 */
	public void setPoints(ArrayList<String> points)
	{
		game.put(DbContract.Game.POINTS, points);
	}

	/**
	 * @return the locationObjects
	 */
	public ArrayList<String> getLocationObjects()
	{
		return convertJSONStringArrayToArrayList(game
				.getJSONArray(DbContract.Game.LOCATION_OBJECTS));
	}

	/**
	 * @param locationObjects
	 *            the locationObjects to set
	 */
	public void setLocationObjects(ArrayList<String> locationIds)
	{
		game.put(DbContract.Game.LOCATION_OBJECTS, locationIds);
	}

	/**
	 * @return the isPublic
	 */
	public boolean getIsPublic()
	{
		return game.getBoolean(DbContract.Game.IS_PUBLIC);
	}

	/**
	 * @param isPublic
	 *            the isPublic to set
	 */
	public void setIsPublic(boolean isPublic)
	{
		game.put(DbContract.Game.IS_PUBLIC, isPublic);
	}
}