package com.sniper;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;

import com.sniper.core.AWSRequest;

public class ActivityKillConfirm extends Activity {

	public static final String PARSE_EXTRA_DATA_KEY = "com.parse.Data";
	public static final String PARSE_JSON_ALERT_KEY = "alert";
	public static final String PARSE_JSON_CHANNELS_KEY = "com.parse.Channel";
	
	private static final String TAG = "TestBroadcastReceiver";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AWSRequest request = new AWSRequest();
		String url = "www.google.com";
		try
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
		
		setContentView(R.layout.activity_kill_confirm);
		WebView picture = (WebView) findViewById(R.id.wvPicture);
		picture.loadUrl(url);
	}
	
	public void confirmYes(View v) {
		
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
