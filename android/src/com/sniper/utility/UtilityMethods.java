package com.sniper.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sniper.core.Game;

public class UtilityMethods {

	public static ParseObject arrayToParseObject(ArrayList<?> array, String name) {
		ParseObject poArray = new ParseObject(name);
		for(int i = 0; i < array.size(); i++) {
			poArray.put(Integer.toBinaryString(i), array.get(i));
		}
		return poArray;
	}
	
	public static void GetTargets(final FindCallback<ParseUser> callback){
		ParseQuery<ParseObject> gamequery = ParseQuery.getQuery(Game.class.getSimpleName());
		gamequery.whereEqualTo("players", ParseUser.getCurrentUser().getObjectId());
		gamequery.whereLessThanOrEqualTo(DbContract.Game.START_TIME, new Date());
		gamequery.whereGreaterThanOrEqualTo(DbContract.Game.END_TIME, new Date());
		gamequery.findInBackground(new FindCallback<ParseObject>() {
			@Override
		  public void done(List<ParseObject> objects, ParseException e) {			  
		    if (e == null) {
		    	List<String> targetIds = new ArrayList<String>();
		    	Log.d("Games in:", ""+objects.size());
		     	for(int i=0; i<objects.size(); i++){
		     		Game game = new Game(objects.get(i));
		     		String targetId = game.GetTargetIdForUser(ParseUser.getCurrentUser());
		     		targetIds.add(targetId);
		     		Log.d("Target",targetId);
		     	}
		     	ParseQuery<ParseUser> targetsQuery = ParseUser.getQuery();
		     	targetsQuery.whereContainedIn("objectId", targetIds);
		     	targetsQuery.findInBackground(callback);
		    } else {
		      // something went wrong
		    }
		  }
		});
	}
	
}
