package com.sniper.utility;

import android.provider.BaseColumns;

public class DbContract {

	private DbContract() {}
	
	public static abstract class Game implements BaseColumns {
		public static final String NAME = "name";
		public static final String DEBUGINFO = "debugInfo";
		public static final String POINTS = "points";
		public static final String CREATOR = "createdBy";
		public static final String CREATED = "created";
		public static final String PLAYERS = "players";
		public static final String TARGETS = "targets";
		public static final String START_TIME = "startTime";
		public static final String END_TIME = "endTime";
		public static final String HOUSE_RULES = "houseRules";
		public static final String SAFE_INSIDE = "safeInside";
		public static final String MODERATOR = "moderator";
		public static final String LOCATION_OBJECTS = "locationObjects";
		public static final String IS_PUBLIC = "isPublic";
	}
	
	public static abstract class Player implements BaseColumns {
		public static final String NAME = "name";
		public static final String DEBUGINFO = "debugInfo";
		public static final String CREATED = "created";
		public static final String PHOTOURLS = "photoURLs";
		public static final String IS_ALIVE = "isAlive";
		public static final String ROLE = "role";
		public static final String ARSENAL = "arsenal";
	}
	
	public static abstract class PlayerAction implements BaseColumns {
		public static final String NAME = "name";
		public static final String DEBUGINFO = "debugInfo";
		public static final String CREATED = "created";
		public static final String PLAYER = "player";
		public static final String TARGET = "target";
		public static final String URL = "URL";
		public static final String IS_VERIFIED = "isVerified";
	}
}