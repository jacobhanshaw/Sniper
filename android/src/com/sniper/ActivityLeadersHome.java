package com.sniper;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityLeadersHome extends FragmentActivity {

	String[] myStringArray = {"Chelsey Denton", "Adam Hart", "Jacob Hanshaw",
			"Mikkel Nielsen", "Drew Miller", "Michael Meador", 
			"Allison Perin", "Ellie McCabe", "Erin Stummer", "Josh Kemp", 
			"Nick Connel"};
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaders_home);
		
		adapter = new ArrayAdapter<String>(this, 
				//android.R.layout.simple_list_item_single_choice,
		        R.layout.list_item,
		        myStringArray);
		ListView listView = (ListView) findViewById(R.id.leaders_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				
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
