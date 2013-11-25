package com.sniper;

import com.sniper.utility.BasicListAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
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

	String[] myStringArray = {"Chelsey Denton", "Adam Hart", "Jacob Hanshaw",
			"Mikkel Nielsen", "Drew Miller", "Michael Meador", 
			"Allison Perin", "Ellie McCabe", "Erin Stummer", "Josh Kemp", 
			"Nick Connel"};
	BasicListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaders_home);
		
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
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				//view.setSelected(true);
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
