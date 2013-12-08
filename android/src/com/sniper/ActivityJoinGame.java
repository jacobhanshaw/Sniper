package com.sniper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sniper.core.Game;
import com.sniper.utility.DbContract;
import com.sniper.utility.MenuHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityJoinGame extends FragmentActivity {

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
		setContentView(R.layout.activity_join_game);
		
		gameNames.add("Loading...");
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Game.class.getSimpleName());
		query.whereGreaterThan(DbContract.Game.START_TIME, new Date());
		//exclude games you're already in
		query.orderByAscending(DbContract.Game.START_TIME);		
		final SimpleDateFormat format = 
				new SimpleDateFormat("E MMM dd @ hh:mm a");		
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
		  public void done(List<ParseObject> objects, ParseException e) {			  
		    if (e == null) {
		     	games = new ArrayList<Game>();
		     	gameNames.clear();
		     	for(int i=0; i<objects.size(); i++){
		     		Game game = new Game(objects.get(i));
		     		if(game.getName() != null && !game.getPlayers()
		     				.contains(ParseUser.getCurrentUser().getObjectId())){
		     			games.add(game);
			     		gameNames.add(game.getName()+"\nStarts: "+
			     				format.format(game.getStartTime()));
		     		}		     		
		     	}
		     	adapter.notifyDataSetChanged();
		    } else {
		      // something went wrong
		    }
		  }
		});
		
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
				ActivityGameDetailJoin.game = games.get(position);
				Intent intent = new Intent(act, ActivityGameDetailJoin.class);
            	startActivity(intent);
			}
			});
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_join_game, menu);
		return true;
	}

}
