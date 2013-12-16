package com.sniper;

import com.sniper.utility.BasicListAdapter;
import com.sniper.utility.MenuHelper;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityArmoryHome extends FragmentActivity 
	implements OnItemClickListener {
	
	//used for selected index in armory and on main
	public static int selectedPosition = 0;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    return MenuHelper.onOptionsItemSelected(item, this);
	}
	
	// also usedd for armory in main
	public static String[] myStringArray = {"Standard Bullet"};
	BasicListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_armory_home);
		
		adapter = new BasicListAdapter(this, myStringArray, selectedPosition);
		ListView listView = (ListView) findViewById(R.id.shootable_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.armory_home, menu);
		return true;
	}
	
	public void LandMinesClick(View view){
		Intent intent = new Intent(this, MapActivity.class);
		startActivity(intent);
	}

	private boolean first = true;
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		//select desired item on click so correct weapon always highlighted
		view.setSelected(true);
		if(first){
			ListView listView = (ListView) findViewById(R.id.shootable_list);
			View v = listView.getChildAt(selectedPosition);
			adapter.SelectView(v);
			first = false;
		}
		selectedPosition = pos;
		adapter.SelectView(view);
	}
}
