package com.sniper;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

public class ApplicationSniper extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Parse.initialize(this, "NiXxI3aBRsW4xv1Og9p1jada5qOV9ldWAVHFXUgo", "Dld5dAF5rIvpy7laIaIbqMuROnOckDP11u9NSn0h"); 
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		defaultACL.setPublicReadAccess(true);
		defaultACL.setPublicWriteAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
		PushService.setDefaultPushCallback(this, ActivityMain.class);
		
		ParseInstallation.getCurrentInstallation().saveInBackground();		
	}



}
