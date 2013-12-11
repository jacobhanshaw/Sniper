package com.sniper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.sniper.core.AWSRequest;
import com.sniper.utility.DbContract;
import com.sniper.utility.MenuHelper;

public class ActivityKillConfirm extends Activity {

	public static final String PARSE_EXTRA_DATA_KEY = "com.parse.Data";
	public static final String PARSE_JSON_ALERT_KEY = "alert";
	public static final String PARSE_JSON_CHANNELS_KEY = "com.parse.Channel";
	
	private static final String TAG = "TestBroadcastReceiver";
	
	private String killActionId;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    return MenuHelper.onOptionsItemSelected(item, this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle b = getIntent().getExtras();
		String url = b.getString("URL");
		killActionId = b.getString("killActionId");
		
	/*	try
		{
			Intent intent = getIntent();
			String action = intent.getAction();

			// "com.parse.Channel"
			String channel = intent.getExtras().getString(PARSE_JSON_CHANNELS_KEY);
			JSONObject json = new JSONObject(intent.getExtras().getString(PARSE_EXTRA_DATA_KEY));


			Iterator<String> itr = json.keys();
			while (itr.hasNext())
			{
				String key = (String) itr.next();
				String value = json.getString(key);
			}

			url = json.getString("url");
			
			Log.d("Debug", "Received it here");
		} catch (JSONException e)
		{
			Log.d(TAG, "JSONException: " + e.getMessage());
		}
		*/
		setContentView(R.layout.activity_kill_confirm);
		WebView picture = (WebView) findViewById(R.id.wvPicture);
		picture.loadUrl(url);
	}
	
	public void confirmYes(View v) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("KillAction");
		query.getInBackground(killActionId, new GetCallback<ParseObject>() {
			  public void done(ParseObject killAction, ParseException e) {
			    if (e == null) 
			    {
			      killAction.put(DbContract.PlayerAction.IS_VERIFIED, true);
			      killAction.saveEventually();
			    }
			  }
			});
	}
	
	public void confirmNo(View v) {
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_kill_confirm, menu);
		return true;
	}

}
