package com.sniper.core;

import java.util.ArrayList;
import java.util.Date;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class Game {

	public static String sName;
	public static ArrayList<Player> alPlayers, alTargets;
	public static Date dStartTime, dEndTime;
	public static ArrayList<String> alHouseRules;
	public static boolean bSafeInside;
	public static Player pModerator;
	public static int iPoints;
	public static ArrayList<GpsLocation> alLocationObjects;
	
	public Game() {
		
		createParseObject();
	}
	
	private void createParseObject() {
		ParseObject game = new ParseObject(Game.class.getSimpleName());
		game.put("createdBy", ParseUser.getCurrentUser());
		game.put("name", sName);
		
		ArrayList<ParseObject> alPlayersParse = new ArrayList<ParseObject>();
		for(int i = 0; i < alPlayers.size(); i++) {
			alPlayersParse.add(alPlayers.get(i).poPlayer);
		}
		game.put("playerList", alPlayersParse);
		//TODO: Convert other objects to ParseObjects
		game.put("targetsList", alTargets);
		game.put("houseRules", alHouseRules);
		game.put("safeInside", bSafeInside);
		game.put("moderator", pModerator);
		game.put("locationObjects", alLocationObjects);
	}
}
