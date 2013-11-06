package com.sniper;

import com.sniper.core.Camera;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
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
			
			View v;
			if(getResources().getConfiguration().orientation == 
					Configuration.ORIENTATION_PORTRAIT){
				v = inflater.inflate(R.layout.activity_toolbar,
				        container, false);
			}
			else{
				v = inflater.inflate(R.layout.activity_toolbar_landscape,
				        container, false);
			}
			

			LinkButton(v, R.id.game_button, GamesHome.class);
			//LinkButton(v, R.id.home_button, MainActivity.class);
			LinkButton(v, R.id.armory_button, ArmoryHome.class);
			
			Button button = (Button)v.findViewById(R.id.home_button);
	        button.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	Activity activity = getActivity();
	            	
	            	if(MainActivity.class.isInstance(activity)){
	            		Camera.TakePicture();
	            	}
	            	else{
		            	Intent intent = new Intent(activity, MainActivity.class);
		            	startActivity(intent);	            		
	            	}
	            	
	            }
	        });
						
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
