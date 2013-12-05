package com.sniper;

import com.sniper.core.Game;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.TextView;

public class ActivityGeneralYourGameView extends FragmentActivity {
	public static Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_general_your_game_view);
		
		UpdateText(R.id.Name, game.getName());
		UpdateText(R.id.Moderator, "Moderator: "+game.getModeratorId());
		UpdateText(R.id.StartTime, "Start Time: "+game.getStartTime());
		UpdateText(R.id.BoolDescriptors, (game.getIsPublic() ? "Is Public, ":
			"Is not Public, ") + 
				(game.isSafeInside()? "Is Safe Inside" : "Not Safe Inside"));
		UpdateText(R.id.HouseRules, "House Rules: "+game.getHouseRules());
	}
	
	private void UpdateText(int id, String string){
		TextView view = (TextView) findViewById(id);
		view.setText(string);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_general_your_game_view, menu);
		return true;
	}

}
