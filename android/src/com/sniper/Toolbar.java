package com.sniper;

import com.sniper.core.Camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
			

			LinkButton(v, R.id.game_button, ActivityGamesHome.class);
			LinkButton(v, R.id.armory_button, ActivityArmoryHome.class);
			LinkButton(v, R.id.home_button, ActivityMain.class, true);
			LinkButton(v, R.id.leaders_button, ActivityLeadersHome.class);
			LinkButton(v, R.id.settings_button, ActivitySettingsHome.class);
				
		    return v;
		}
		private void LinkButton(View v, int id, final Class<?> link){
			LinkButton(v, id, link, false);			
		}
		private void LinkButton(View v, int id, final Class<?> link, final boolean isHome){
			Button button = (Button)v.findViewById(id);
	        button.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	Activity activity = getActivity();
	            	
	            	if(link.isInstance(activity)){
	            		if(isHome){
	            			Camera.TakePicture();		            		
		            		CharSequence text = "You just tried to shoot Chelsey";
		            		int duration = Toast.LENGTH_SHORT;
		            		Toast toast = Toast.makeText(Camera.context, text, duration);
		            		toast.setGravity(Gravity.CENTER, 0, 0);
		            		toast.show();	            			
	            		}
	            		return;
	            	}
	            	
	            	Intent intent = new Intent(activity, link);
	            	startActivity(intent);
	            }
	        });
		}

}
