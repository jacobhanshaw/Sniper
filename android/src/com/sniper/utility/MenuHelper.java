package com.sniper.utility;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;
import com.sniper.ActivityLogin;
import com.sniper.R;

public class MenuHelper {
	public static void SetupStandardMenu(Activity act, Menu menu){
		act.getMenuInflater().inflate(R.menu.main, menu);		
	}

	//use this to have global logout filled in from activity
	@SuppressWarnings("static-access")
	public static boolean onOptionsItemSelected(MenuItem item, Activity act) {
	    switch (item.getItemId()) {
	    	case R.id.action_settings:
	        case R.id.LogOutId:
	        	ParseUser.getCurrentUser().logOut();
	        	Intent intent = new Intent(act, ActivityLogin.class);
	        	act.startActivity(intent);
	            return true;
	        default:
	            return false;
	    }
	}
	
	
}
