package com.sniper;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GamesHome extends FragmentActivity {

	String[] myStringArray = {"Mission X", "Black Hawk", "007", "t",
			"e", "s", "t", "i", "n", "g", " ", "s", "c", "r", "o", "l", "l"};
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_games_home);
		
		adapter = new ArrayAdapter<String>(this, 				
		        R.layout.list_item, myStringArray);
		ListView listView = (ListView) findViewById(R.id.games_list);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.games_home, menu);
		return true;
	}

}
