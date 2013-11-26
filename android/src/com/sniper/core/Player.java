package com.sniper.core;

import java.util.ArrayList;

import org.json.JSONObject;

import com.parse.ParseObject;

public class Player {
	
	private String name, objectId;
	private ArrayList<String> photos;
	private boolean alive;
	private String role;
	private ArrayList<Weapon> arsenal;
	
	private ParseObject player;
	
	public Player() {
		
	}
	
	//NEEDS FINISHED
	public Player(ParseObject player)
	{
		
	}
	
	public Player(JSONObject json)
	{
		
	}
	
	private void createParseObject() {
		player = new ParseObject("Player");
		player.put("name", name);
		
		ArrayList<ParseObject> alParsePhotos = new ArrayList<ParseObject>();
		for(int i = 0; i < photos.size(); i++) {
			ParseObject poURL = new ParseObject("URL");
			poURL.put("url", photos.get(i));
			alParsePhotos.add(poURL);
		}
		player.put("photos", alParsePhotos);
		player.put("alive", alive+"");
		player.put("role", role);
		ArrayList<ParseObject> alParseArsenal = new ArrayList<ParseObject>();
		player.saveInBackground();
		objectId = player.getObjectId();
	}

	
	/*************************************
	 * Accessor Methods
	 *************************************/
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the objectId
	 */
	public String getObjectId() {
		return objectId;
	}

	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	/**
	 * @return the photos
	 */
	public ArrayList<String> getPhotos() {
		return photos;
	}

	/**
	 * @param photos the photos to set
	 */
	public void setPhotos(ArrayList<String> photos) {
		this.photos = photos;
	}

	/**
	 * @return the alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * @param alive the alive to set
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the arsenal
	 */
	public ArrayList<Weapon> getArsenal() {
		return arsenal;
	}

	/**
	 * @param arsenal the arsenal to set
	 */
	public void setArsenal(ArrayList<Weapon> arsenal) {
		this.arsenal = arsenal;
	}

	/**
	 * @return the player
	 */
	public ParseObject getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(ParseObject player) {
		this.player = player;
	}
	

}
