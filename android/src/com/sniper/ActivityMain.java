package com.sniper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;
import com.sniper.core.Camera;
import com.sniper.core.Game;
import com.sniper.core.GpsLocationService;
import com.sniper.utility.LoadUserImage;
import com.sniper.utility.MenuHelper;
import com.sniper.utility.UtilityMethods;

public class ActivityMain extends FragmentActivity
{
	private Camera camera;
	public static GpsLocationService gps;

	List<ParseUser> targets = new ArrayList<ParseUser>();
	String[] targetUserNames =	{ };
	ArrayList<Game> games = new ArrayList<Game>();
	public ProgressDialog progressDialog;
	public static ParseUser target;
	public AlertDialog alert;

	public static final String ACTION = "com.androidbook.parse.TestPushAction";
	public static final String PARSE_EXTRA_DATA_KEY = "com.parse.Data";
	public static final String PARSE_JSON_ALERT_KEY = "alert";
	public static final String PARSE_JSON_CHANNELS_KEY = "com.parse.Channel";

	private static final String TAG = "TestBroadcastReceiver";
 
	public static boolean inBackground;

	@Override
	public void onPause()
	{
	     inBackground=true;
	     super.onPause();
	}

	@Override
	public void onResume()
	{
	     inBackground=false;
	     super.onResume();
	}
	
	private void GetTargets(final ActivityMain act){
		this.progressDialog = ProgressDialog.show(this, "",
				"Loading Targets...", true);
		UtilityMethods.GetTargets(new FindCallback<ParseUser>(){
			@Override
			public void done(List<ParseUser> targets, ParseException e) {
				if(e==null){
					Log.d("num targets", ""+targets.size());
					act.targets = targets;
					targetUserNames = new String[targets.size()];
					boolean foundTarget = false;
					for(int i=0; i<targets.size(); i++){
						act.targetUserNames[i] = targets.get(i).getUsername();
						if(target != null && targets.get(i).getObjectId().equals(target.getObjectId())){
							foundTarget = true;
						}
					}
					if(!foundTarget){
						if(targets.size() > 0)
							target = targets.get(0);
						else
							target = null;
					}
					ActivityMain.this.progressDialog.dismiss();
					if(target != null){
						NewTarget(act);
					}
				}
			}});		
	}
	
	public void LandMine(View v){
		Intent intent = new Intent(this, MapActivity.class);
		startActivity(intent);
	}

	private void NewTarget(Activity activity){
		Button b = (Button) findViewById(R.id.name_button);
		b.setText(target.getUsername());
		LoadUserImage.GetImage(target, activity);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ParseAnalytics.trackAppOpened(getIntent());

		updateSubscriptions();

		this.gps = new GpsLocationService(ActivityMain.this);

		if(gps.canGetLocation())
		{    
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
		}

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			setContentView(R.layout.activity_main);
		} else
		{
			setContentView(R.layout.activity_main_landscape);
		}

		if(android.hardware.Camera.getNumberOfCameras() > 0){			
			camera = new Camera(this);
			FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
			preview.addView(camera);
		}

		ImageView iv = (ImageView) findViewById(R.id.user_image);
		iv.setImageResource(R.drawable.questionmark);
		
		ImageButton butt = (ImageButton)findViewById(R.id.mine_button);
		butt.setImageResource(R.drawable.mineicon);

		Button b = (Button) findViewById(R.id.weapon_button);
		b.setText(ActivityArmoryHome.myStringArray[
		                                           ActivityArmoryHome.selectedPosition]);
		// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		// intent.setType("image/*");
		// startActivityForResult(intent, SELECT_PHOTO)

		GetTargets(this);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void updateSubscriptions() {
		//Unsubscribe from all current Pushes
		Set<String> subscriptions = PushService.getSubscriptions(this);
		Iterator<String> iter = subscriptions.iterator();
		while(iter.hasNext()) {
			String curr = iter.next();
			Log.v("Debug", "Installation " + curr);
			PushService.unsubscribe(this, curr);
		}
		//Subscribe User
		String userChannel = "user_" + ParseUser.getCurrentUser().getObjectId();
		PushService.subscribe(this, userChannel, ActivityKillConfirm.class);

		//Subscribe Games
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Game.class.getSimpleName());
		query.whereEqualTo("players", ParseUser.getCurrentUser().getObjectId());
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {			  
				if (e == null) {
					games = new ArrayList<Game>();
					for(int i=0; i<objects.size(); i++){
						Game game = new Game(objects.get(i));
						games.add(game);
					}
					subscribeGames();
				} else {
					Log.e("Debug", e.getMessage());
					// something went wrong
				}
			}
		});
		
		Log.v("Debug", ParseInstallation.getCurrentInstallation().getObjectId());
	}
	
	private void subscribeGames() {
		for(int i = 0; i < games.size(); i++) {
			PushService.subscribe(this, "game_" + games.get(i).getObjectId(), ActivityMain.class);
		}
	}

	public void SelectTargetClick(View view){
		AlertDialog.Builder builder = 
				new AlertDialog.Builder(this);
		builder.setTitle("Select Target");

		final Activity act = this;
		builder.setSingleChoiceItems(
				targetUserNames, 
				0, 
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
							DialogInterface dialog, 
							int which) {
						target = targets.get(which);
						NewTarget(act);
						dialog.dismiss();
					}
				});
		alert = builder.create();
		alert.show();
	}

	public void showDialogButtonClick(View view) {
		Log.i(TAG, "show Dialog ButtonClick");
		AlertDialog.Builder builder = 
				new AlertDialog.Builder(this);
		builder.setTitle("Shoot With");

		builder.setSingleChoiceItems(
				ActivityArmoryHome.myStringArray, 
				ActivityArmoryHome.selectedPosition, 
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
							DialogInterface dialog, 
							int which) {
						ActivityArmoryHome.selectedPosition = which;
						Button b = (Button) findViewById(R.id.weapon_button);
						b.setText(
							ActivityArmoryHome.myStringArray[ActivityArmoryHome.selectedPosition]);
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void onReceive(Context context, Intent intent)
	{
		try
		{
			JSONObject json = new JSONObject(intent.getExtras().getString(PARSE_EXTRA_DATA_KEY));


			@SuppressWarnings("unchecked")
			Iterator<String> itr = (Iterator<String>)json.keys();
			while (itr.hasNext())
			{
				String key = (String) itr.next();
				String value = json.getString(key);
			}
			Log.d("Debug", "Received it main");
		} catch (JSONException e)
		{
			Log.d(TAG, "JSONException: " + e.getMessage());
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return MenuHelper.onOptionsItemSelected(item, this);
	}
	
	public GpsLocationService getGpsLocationService()
	{
		return this.gps;
	}
}
