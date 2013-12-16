package com.sniper;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.sniper.core.Game;
import com.sniper.utility.BasicListAdapter;
import com.sniper.utility.LoadUserImage;
import com.sniper.utility.MenuHelper;
import com.sniper.utility.UtilityMethods;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityTargets extends FragmentActivity {
	String[] myStringArray = { "Loading..."};
	List<ParseUser> usersInList;//targets for current user
	BasicListAdapter adapter;
	//list of names of games where each "usersInList[i]" is the current target
	List<ArrayList<String>> gamesForTarget;
	//images for usersInList
	public Bitmap[] images;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    return MenuHelper.onOptionsItemSelected(item, this);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_targets);
		
		SetupList();
		
		UtilityMethods.GetTargets(new FindCallback<ParseUser>(){
			@Override
			public void done(List<ParseUser> users, ParseException e) {
				if(e==null){
					ActivityTargets.this.images = new Bitmap[users.size()];
					ActivityTargets.this.myStringArray = new String[users.size()];
			    	for(int i=0; i<users.size(); i++){
			    		//update username for display
			    		ActivityTargets.this.myStringArray[i] = users.get(i).getUsername();
			    		// start query for image for user, display automatically
			    		// updated when user done (required name "bitmaps" not be changed)
			    		LoadUserImage.GetImage(users.get(i), ActivityTargets.this, i);
			    	}
			    	usersInList = users; //update actual users to be referenced on click
			    	
			    	//update the list of names of games where each "usersInList[i]" is the current target
			    	UtilityMethods.GetActiveGames(new FindCallback<ParseObject>(){
						@Override
						public void done(List<ParseObject> games,
								ParseException e) {
							if(e==null){
								//update array list to be correct length to match targets
								gamesForTarget = new ArrayList<ArrayList<String>>();
								for(int j=0; j<ActivityTargets.this.usersInList.size(); j++){
									gamesForTarget.add(new ArrayList<String>());
								}
								
								// for all games the user is in
								for(int i=0; i<games.size(); i++){
									Game game = new Game(games.get(i));
									//find the users target in the game
									String targetId = game.GetTargetIdForUser(ParseUser.getCurrentUser());
									//match the game's target to one of the users targets
									for(int j=0; j<ActivityTargets.this.usersInList.size(); j++){
										ParseUser user = ActivityTargets.this.usersInList.get(j);
										if(user.getObjectId().equals(targetId)){
											gamesForTarget.get(j).add(game.getName());
										}
									}
								}
								
								//update display
						    	ActivityTargets.this.SetupList();								
							}
						}});
			    	
				}				
			}});

	}

	public void SetupList(){
		adapter = new BasicListAdapter(this, myStringArray,0,
		        R.layout.target_item){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				//for each view, need to update fields
				LayoutInflater inflater = (LayoutInflater) this.context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 
				// username is simplest update
				View rowView = inflater.inflate(this.resource, parent, false);
				TextView textView = (TextView) rowView.findViewById(R.id.username);
				textView.setText(this.values[position]);
				
				// if users actual image is loaded, use it. else, show question mark
				ImageView iv = (ImageView) rowView.findViewById(R.id.user_image);
				if(images == null || images[position] == null){
					iv.setImageResource(R.drawable.questionmark);
				}
				else{
					iv.setImageBitmap(images[position]);
				}

				//display string of list of games the user is a target in
				TextView games = (TextView)rowView.findViewById(R.id.games_list);
				if(gamesForTarget != null){
					List<String> strings = gamesForTarget.get(position);
					String string = "";
					for(int i=0; i<strings.size(); i++){
						if(i!=0){
							string += ", ";
						}
						string += strings.get(i);
					}
					games.setText(string);
				}
				else{					
					games.setText("");
				}
				
				return rowView;
			}
		};
		ListView listView = (ListView) findViewById(R.id.leaders_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				//on click show target in more detail
				ActivityUserDetail.User = usersInList.get(position);
				Intent intent = new Intent(ActivityTargets.this, ActivityUserDetail.class);
            	startActivity(intent);
			}
			});
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_leaders_home, menu);
		return true;
	}
}
