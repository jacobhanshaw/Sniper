package com.sniper.core;

import java.util.ArrayList;

import com.parse.ParseObject;

public class Player {
	
	private String m_sName, m_sObjectId;
	private ArrayList<String> m_alPhotos;
	private boolean m_bAlive;
	private String m_sRole;
	private ArrayList<Weapon> m_alArsenal;
	
	private ParseObject m_poPlayer;
	
	public Player() {
		
	}
	
	private void createParseObject() {
		m_poPlayer = new ParseObject("Player");
		m_poPlayer.put("name", m_sName);
		
		ArrayList<ParseObject> alParsePhotos = new ArrayList<ParseObject>();
		for(int i = 0; i < m_alPhotos.size(); i++) {
			ParseObject poURL = new ParseObject("URL");
			poURL.put("url", m_alPhotos.get(i));
			alParsePhotos.add(poURL);
		}
		m_poPlayer.put("photos", alParsePhotos);
		m_poPlayer.put("alive", m_bAlive+"");
		m_poPlayer.put("role", m_sRole);
		ArrayList<ParseObject> alParseArsenal = new ArrayList<ParseObject>();
		m_poPlayer.saveInBackground();
		m_sObjectId = m_poPlayer.getObjectId();
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
	 * @return the m_alPhotos
	 */
	public ArrayList<String> getM_alPhotos() {
		return m_alPhotos;
	}

	/**
	 * @param m_alPhotos the m_alPhotos to set
	 */
	public void setM_alPhotos(ArrayList<String> m_alPhotos) {
		this.m_alPhotos = m_alPhotos;
	}

	/**
	 * @return the m_bAlive
	 */
	public boolean isM_bAlive() {
		return m_bAlive;
	}

	/**
	 * @param m_bAlive the m_bAlive to set
	 */
	public void setM_bAlive(boolean m_bAlive) {
		this.m_bAlive = m_bAlive;
	}

	/**
	 * @return the m_sRole
	 */
	public String getM_sRole() {
		return m_sRole;
	}

	/**
	 * @param m_sRole the m_sRole to set
	 */
	public void setM_sRole(String m_sRole) {
		this.m_sRole = m_sRole;
	}

	/**
	 * @return the m_alArsenal
	 */
	public ArrayList<Weapon> getM_alArsenal() {
		return m_alArsenal;
	}

	/**
	 * @param m_alArsenal the m_alArsenal to set
	 */
	public void setM_alArsenal(ArrayList<Weapon> m_alArsenal) {
		this.m_alArsenal = m_alArsenal;
	}

	/**
	 * @return the m_poPlayer
	 */
	public ParseObject getM_poPlayer() {
		return m_poPlayer;
	}

	/**
	 * @param m_poPlayer the m_poPlayer to set
	 */
	public void setM_poPlayer(ParseObject m_poPlayer) {
		this.m_poPlayer = m_poPlayer;
	}
	

}
