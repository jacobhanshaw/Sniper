package com.sniper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sniper.core.Camera;
import com.sniper.core.Game;
import com.sniper.utility.MenuHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityGamesHome extends FragmentActivity {

	ArrayList<String> gameNames = new ArrayList<String>();
	List<Game> games = new ArrayList<Game>();
	ArrayAdapter<String> adapter;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    return MenuHelper.onOptionsItemSelected(item, this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_games_home);

		gameNames.add("Loading...");
		UpdateGamesList();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Game.class.getSimpleName());
		//query.whereContains(key, substring);		
		//query.wh
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
		  public void done(List<ParseObject> objects, ParseException e) {			  
		    if (e == null) {
		     	games = new ArrayList<Game>();
		     	gameNames.clear();
		     	for(int i=0; i<objects.size(); i++){
		     		Game game = new Game(objects.get(i));
		     		if(game.getName() != null && game.getPlayers()
		     				.contains(ParseUser.getCurrentUser().getObjectId())){
		     			games.add(game);
			     		gameNames.add(game.getName());
		     		}
		     	}
		     	adapter.notifyDataSetChanged();
		    } else {
		      // something went wrong
		    }
		  }
		});
		
	}
	
	private void UpdateGamesList(){
		adapter = new ArrayAdapter<String>(this, 
		        R.layout.list_item, R.id.label,
		        gameNames);
		ListView listView = (ListView) findViewById(R.id.games_list);
		listView.setAdapter(adapter);
		final Activity act = this;
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				Game game = games.get(position);
				if(game.getModeratorId().equals(ParseUser.getCurrentUser().getObjectId())){
					ActivityModeratorGameView.game = game;;
					Intent intent = new Intent(act, ActivityModeratorGameView.class);
	            	startActivity(intent);
				}
				else{
					ActivityGeneralYourGameView.game = game;
					Intent intent = new Intent(act, ActivityGeneralYourGameView.class);
	            	startActivity(intent);
				}
			}
			});
		adapter.notifyDataSetChanged();
	}
	
	public void JoinGame(View view){
		Intent intent = new Intent(this, ActivityJoinGame.class);
    	startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.games_home, menu);
		return true;
	}
		
	public void onNewGameClick(View v) {
    	Intent intent = new Intent(this, ActivityNewGame.class);
    	startActivity(intent);
    }
//	public void onListItemClick(ListView l, View v, int position, long id) {
//		ListView listView = (ListView) findViewById(R.id.games_list);
//		listView.setSelection(position);
//    }

}
