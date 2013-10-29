package com.sniper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Toolbar extends Fragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		    // Inflate the layout for this fragment
			
			View v = inflater.inflate(R.layout.activity_toolbar,
			        container, false);

			LinkButton(v, R.id.game_button, GamesHome.class);
			LinkButton(v, R.id.home_button, MainActivity.class);
			LinkButton(v, R.id.armory_button, ArmoryHome.class);
			
		    return v;
		}
		
		private void LinkButton(View v, int id, final Class<?> link){
			Button button = (Button)v.findViewById(id);
	        button.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	Activity activity = getActivity();
	            	
	            	if(link.isInstance(activity)){
	            		return;
	            	}
	            	
	            	Intent intent = new Intent(activity, link);
	            	startActivity(intent);
	            }
	        });
		}

}
