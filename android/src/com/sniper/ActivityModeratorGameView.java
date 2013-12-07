package com.sniper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sniper.core.Game;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityModeratorGameView extends FragmentActivity {
	public static Game game;
	private ParseUser moderator;
	private List<ParseUser> players = new ArrayList<ParseUser>();
	private ArrayList<String> playerNames = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moderator_game_view);
		
		UpdateText(R.id.Name, game.getName());
		TextView view = (TextView) findViewById(R.id.Moderator);
		view.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		UpdateText(R.id.StartTime, "Start Time: "+
				new SimpleDateFormat("E MMM dd @ hh:mm a").format(game.getStartTime()));
		UpdateText(R.id.BoolDescriptors, (game.getIsPublic() ? "Is Public, ":
			"Is not Public, ") + 
				(game.isSafeInside()? "Is Safe Inside" : "Not Safe Inside"));
		UpdateText(R.id.HouseRules, "House Rules: "+game.getHouseRules());
		
		playerNames.add("Loading...");
		
		moderator = ParseUser.getCurrentUser();
		UpdateText(R.id.Moderator, moderator.getUsername());
		
		for(int i=0; i<game.getPlayers().size(); i++){
			ParseQuery<ParseUser> query = ParseUser.getQuery();
			query.getInBackground(game.getPlayers().get(i), new GetCallback<ParseUser>(){
				@Override
				public void done(ParseUser user, ParseException e) {
					if(e==null){
						if(players.isEmpty()){
							playerNames.clear();
						}
						
						players.add(user);
						playerNames.add(user.getUsername());
						
						if(players.size() == game.getPlayers().size()){
							adapter.notifyDataSetChanged();
						}
					}					
				}			
			});
		}
		
		adapter = new ArrayAdapter<String>(this, 
		        R.layout.list_item, R.id.label,
		        playerNames);
		ListView listView = (ListView) findViewById(R.id.Players);
		listView.setAdapter(adapter);
		final Activity act = this;
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				if(players == null || players.size() ==0){
					return;
				}
				ActivityUserDetail.User = players.get(position);
				Intent intent = new Intent(act, ActivityUserDetail.class);
		    	startActivity(intent);
			}
			});
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_moderator_game_view, menu);
		return true;
	}
	private void UpdateText(int id, String string){
		TextView view = (TextView) findViewById(id);
		view.setText(string);
	}
	
	public void Delete(View view){
		game.Delete();
	}
	
	public void ModeratorClick(View view){
		ActivityUserDetail.User = ParseUser.getCurrentUser();
		Intent intent = new Intent(this, ActivityUserDetail.class);
    	startActivity(intent);
	}
}
