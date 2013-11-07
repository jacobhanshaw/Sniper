package com.sniper.core;

import java.util.ArrayList;

import com.parse.ParseObject;

public class Player {
	
	public String sName, sObjectID;
	public ArrayList<String> alPhotos;
	public boolean bAlive;
	public String sRole;
	public ArrayList<Weapon> alArsenal;
	
	public ParseObject poPlayer;
	
	public Player() {
		
		createParseObject();
	}
	
	private void createParseObject() {
		poPlayer = new ParseObject("Player");
		poPlayer.put("name", sName);
		
		ArrayList<ParseObject> alParsePhotos = new ArrayList<ParseObject>();
		for(int i = 0; i < alPhotos.size(); i++) {
			ParseObject poURL = new ParseObject("URL");
			poURL.put("url", alPhotos.get(i));
			alParsePhotos.add(poURL);
		}
		poPlayer.put("photos", alParsePhotos);
		poPlayer.put("alive", bAlive+"");
		poPlayer.put("role", sRole);
		ArrayList<ParseObject> alParseArsenal = new ArrayList<ParseObject>();
	}
	
}
