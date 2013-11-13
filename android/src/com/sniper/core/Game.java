package com.sniper.core;

import java.util.ArrayList;
import java.util.Date;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.sniper.utility.DbContract;

public class Game {

	private String m_sName, m_sObjectId;
	private ArrayList<Player> m_alPlayers, m_alTargets;
	private Date m_dStartTime, m_dEndTime;
	private ArrayList<String> m_alHouseRules;
	private boolean m_bSafeInside;
	private Player m_pModerator;
	private int m_iPoints;
	private ArrayList<GpsLocation> m_alLocationObjects;
	
	public Game() {
		
	}
	
	private void createParseObject() {
		ParseObject game = new ParseObject(Game.class.getSimpleName());
		game.put(DbContract.Game.CREATOR, ParseUser.getCurrentUser());
		game.put(DbContract.Game.NAME, m_sName);
		
		ArrayList<ParseObject> m_alPlayersParse = new ArrayList<ParseObject>();
		for(int i = 0; i < m_alPlayers.size(); i++) {
			m_alPlayersParse.add(m_alPlayers.get(i).getM_poPlayer());
		}
		game.put(DbContract.Game.PLAYERS, m_alPlayersParse);
		//TODO: Convert other objects to ParseObjects
		game.put(DbContract.Game.TARGETS, m_alTargets);
		game.put(DbContract.Game.HOUSE_RULES, m_alHouseRules);
		game.put(DbContract.Game.SAFE_INSIDE, m_bSafeInside);
		game.put(DbContract.Game.MODERATOR, m_pModerator);
		game.put(DbContract.Game.LOCATION_OBJECTS, m_alLocationObjects);
	}

	
	/*************************************
	 * Accessor Methods
	 *************************************/
	/**
	 * @return the m_sName
	 */
	public String getM_sName() {
		return m_sName;
	}

	/**
	 * @param m_sName the m_sName to set
	 */
	public void setM_sName(String m_sName) {
		this.m_sName = m_sName;
	}

	/**
	 * @return the m_sObjectId
	 */
	public String getM_sObjectId() {
		return m_sObjectId;
	}

	/**
	 * @param m_sObjectId the m_sObjectId to set
	 */
	public void setM_sObjectId(String m_sObjectId) {
		this.m_sObjectId = m_sObjectId;
	}

	/**
	 * @return the m_alPlayers
	 */
	public ArrayList<Player> getM_alPlayers() {
		return m_alPlayers;
	}

	/**
	 * @param m_alPlayers the m_alPlayers to set
	 */
	public void setM_alPlayers(ArrayList<Player> m_alPlayers) {
		this.m_alPlayers = m_alPlayers;
	}

	/**
	 * @return the m_alTargets
	 */
	public ArrayList<Player> getM_alTargets() {
		return m_alTargets;
	}

	/**
	 * @param m_alTargets the m_alTargets to set
	 */
	public void setM_alTargets(ArrayList<Player> m_alTargets) {
		this.m_alTargets = m_alTargets;
	}

	/**
	 * @return the m_dStartTime
	 */
	public Date getM_dStartTime() {
		return m_dStartTime;
	}

	/**
	 * @param m_dStartTime the m_dStartTime to set
	 */
	public void setM_dStartTime(Date m_dStartTime) {
		this.m_dStartTime = m_dStartTime;
	}

	/**
	 * @return the m_dEndTime
	 */
	public Date getM_dEndTime() {
		return m_dEndTime;
	}

	/**
	 * @param m_dEndTime the m_dEndTime to set
	 */
	public void setM_dEndTime(Date m_dEndTime) {
		this.m_dEndTime = m_dEndTime;
	}

	/**
	 * @return the m_alHouseRules
	 */
	public ArrayList<String> getM_alHouseRules() {
		return m_alHouseRules;
	}

	/**
	 * @param m_alHouseRules the m_alHouseRules to set
	 */
	public void setM_alHouseRules(ArrayList<String> m_alHouseRules) {
		this.m_alHouseRules = m_alHouseRules;
	}

	/**
	 * @return the m_bSafeInside
	 */
	public boolean isM_bSafeInside() {
		return m_bSafeInside;
	}

	/**
	 * @param m_bSafeInside the m_bSafeInside to set
	 */
	public void setM_bSafeInside(boolean m_bSafeInside) {
		this.m_bSafeInside = m_bSafeInside;
	}

	/**
	 * @return the m_pModerator
	 */
	public Player getM_pModerator() {
		return m_pModerator;
	}

	/**
	 * @param m_pModerator the m_pModerator to set
	 */
	public void setM_pModerator(Player m_pModerator) {
		this.m_pModerator = m_pModerator;
	}

	/**
	 * @return the m_iPoints
	 */
	public int getM_iPoints() {
		return m_iPoints;
	}

	/**
	 * @param m_iPoints the m_iPoints to set
	 */
	public void setM_iPoints(int m_iPoints) {
		this.m_iPoints = m_iPoints;
	}

	/**
	 * @return the m_alLocationObjects
	 */
	public ArrayList<GpsLocation> getM_alLocationObjects() {
		return m_alLocationObjects;
	}

	/**
	 * @param m_alLocationObjects the m_alLocationObjects to set
	 */
	public void setM_alLocationObjects(ArrayList<GpsLocation> m_alLocationObjects) {
		this.m_alLocationObjects = m_alLocationObjects;
	}
}
