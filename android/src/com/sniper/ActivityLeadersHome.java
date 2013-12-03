package com.sniper;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sniper.utility.BasicListAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityLeadersHome extends FragmentActivity {

	String[] myStringArray = new String[0];
	List<ParseUser> usersInList;
	BasicListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaders_home);
		
		SetupList();
		
		final ActivityLeadersHome act = this;
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		//query.orderByDescending(key)
		query.findInBackground(new FindCallback<ParseUser>() {
		  @Override
		  public void done(List<ParseUser> objects, ParseException e) {
		    if (e == null) {
		        // The query was successful.
		    	//myStringArray
		    	act.myStringArray = new String[objects.size()];
		    	for(int i=0; i<objects.size() && i<act.myStringArray.length; i++){
		    		act.myStringArray[i] = objects.get(i).getUsername();
		    	}
		    	usersInList = objects;
		    	act.SetupList();
		    	//act.adapter.notifyDataSetChanged();		    	
		    } else {
		        // Something went wrong.
		    }
		  }
		});
	}
	
	private void SetupList(){
		adapter = new BasicListAdapter(this, myStringArray,0,
		        R.layout.leaderboard_view){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				//View v = super.getView(position, convertView, parent);
				LayoutInflater inflater = (LayoutInflater) this.context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 
				View rowView = inflater.inflate(this.resource, parent, false);
				TextView textView = (TextView) rowView.findViewById(R.id.label);
				textView.setText(this.values[position]);
				TextView placeView = (TextView)rowView.findViewById(R.id.leaderboardNumber);
				placeView.setText(""+(position+1));
					
				return rowView;
			}
		};
		ListView listView = (ListView) findViewById(R.id.leaders_list);
		listView.setAdapter(adapter);
		final Activity act = this;
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				//view.setSelected(true);
				ActivityUserDetail.User = usersInList.get(position);
				Intent intent = new Intent(act, ActivityUserDetail.class);
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
