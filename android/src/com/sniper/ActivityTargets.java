package com.sniper;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sniper.core.Game;
import com.sniper.utility.BasicListAdapter;
import com.sniper.utility.LoadUserImage;
import com.sniper.utility.MenuHelper;
import com.sniper.utility.UtilityMethods;

import android.os.Bundle;
import android.app.Activity;
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
	List<ParseUser> usersInList;
	BasicListAdapter adapter;
	List<ArrayList<String>> gamesForTarget;
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
			    		ActivityTargets.this.myStringArray[i] = users.get(i).getUsername();
			    		LoadUserImage.GetImage(users.get(i), ActivityTargets.this, i);
			    	}
			    	usersInList = users;
			    	UtilityMethods.GetActiveGames(new FindCallback<ParseObject>(){
						@Override
						public void done(List<ParseObject> games,
								ParseException e) {
							if(e==null){
								gamesForTarget = new ArrayList<ArrayList<String>>();
								for(int j=0; j<ActivityTargets.this.usersInList.size(); j++){
									gamesForTarget.add(new ArrayList<String>());
								}
								for(int i=0; i<games.size(); i++){
									Game game = new Game(games.get(i));
									String targetId = game.GetTargetIdForUser(ParseUser.getCurrentUser());
									for(int j=0; j<ActivityTargets.this.usersInList.size(); j++){
										ParseUser user = ActivityTargets.this.usersInList.get(j);
										if(user.getObjectId().equals(targetId)){
											gamesForTarget.get(j).add(game.getName());
										}
									}
								}
						    	ActivityTargets.this.SetupList();								
							}
						}});
			    	
				}				
			}});
		
//		ParseQuery<ParseUser> query = ParseUser.getQuery();
//		//query.orderByDescending(key)
//		query.findInBackground(new FindCallback<ParseUser>() {
//		  @Override
//		  public void done(List<ParseUser> objects, ParseException e) {
//		    if (e == null) {
//		        // The query was successful.
//		    	//myStringArray
//		    	ActivityTargets.this.myStringArray = new String[objects.size()];
//		    	for(int i=0; i<objects.size() && i<ActivityTargets.this.myStringArray.length; i++){
//		    		ActivityTargets.this.myStringArray[i] = objects.get(i).getUsername();
//		    	}
//		    	usersInList = objects;
//		    	ActivityTargets.this.SetupList();
//		    	//act.adapter.notifyDataSetChanged();		    	
//		    } else {
//		        // Something went wrong.
//		    }
//		  }
//		});
	}

	public void SetupList(){
		adapter = new BasicListAdapter(this, myStringArray,0,
		        R.layout.target_item){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				//View v = super.getView(position, convertView, parent);
				LayoutInflater inflater = (LayoutInflater) this.context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 
				View rowView = inflater.inflate(this.resource, parent, false);
				TextView textView = (TextView) rowView.findViewById(R.id.username);
				textView.setText(this.values[position]);
				
				ImageView iv = (ImageView) rowView.findViewById(R.id.user_image);
				if(images == null || images[position] == null){
					iv.setImageResource(R.drawable.questionmark);
				}
				else{
					iv.setImageBitmap(images[position]);
				}

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
				
				//TextView placeView = (TextView)rowView.findViewById(R.id.leaderboardNumber);
				//placeView.setText(""+(position+1));
					
				return rowView;
			}
		};
		ListView listView = (ListView) findViewById(R.id.leaders_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				//view.setSelected(true);
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
