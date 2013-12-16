package com.sniper.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sniper.core.KillAction.KillActionType;
import com.sniper.utility.DbContract;

public class GpsLocationService extends Service implements LocationListener, android.location.LocationListener{

	private int updateTimer;

	private final Context mContext;

	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	// flag for GPS status
	boolean canGetLocation = false;

	static Location location;
	static double latitude;
	static double longitude;
	private List<Mine> actualMines;

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 10 * 1; // 10 seconds

	// Declaring a Location Manager
	protected LocationManager locationManager;

	public GpsLocationService(Context context)
	{
		this.mContext = context;
		getLocation();
		updateTimer = 0;
	}


	public void onCreate() {
		/*mLocationRequest = LocationRequest.create();
		mLocationRequest.setInterval(MIN_TIME_BW_UPDATES);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setFastestInterval(MIN_TIME_BW_UPDATES);
		mLocationClient = new LocationClient(getApplicationContext(), this,this);
		mLocationClient.connect();*/
	}

	@Override
	public void onStart(Intent intent, int startId)
	{
		super.onStart(intent, startId);
		int start = Service.START_STICKY;
	}

	public Location getLocation()
	{
		try {
			locationManager = (LocationManager)mContext.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled)
			{
				// no network provider is enabled
			}
			else
			{
				this.canGetLocation = true;
				if (isNetworkEnabled)
				{
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if (locationManager != null)
					{
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null)
						{
							latitude = location.getLatitude();
							longitude = location.getLongitude();
							Log.i("info", "Latitude : " + latitude + "\nLongitude : " + longitude);///
						}
					}
				}
				// if GPS Enabled get lat/lng using GPS Services
				if(isGPSEnabled)
				{
					if (location == null)
					{
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null)
						{
							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null)
							{
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return location;
	}

	/**
	 * Stop using GPS listener
	 * Calling this function will stop using GPS in your app
	 * */
	public void stopUsingGPS()
	{
		if(locationManager != null)
		{
			locationManager.removeUpdates(GpsLocationService.this);
		}       
	}

	/**
	 * Function to get latitude
	 * */
	public static double getLatitude()
	{
		if(location != null)
		{
			latitude = location.getLatitude();
		}
		return latitude;
	}

	/**
	 * Function to get longitude
	 * */
	public static double getLongitude()
	{
		if(location != null)
		{
			longitude = location.getLongitude();
		}
		return longitude;
	}

	/**
	 * Function to check GPS/wifi enabled
	 * @return boolean
	 * */
	public boolean canGetLocation()
	{
		return this.canGetLocation;
	}

	/**
	 * Function to show settings alert dialog
	 * On pressing Settings button will lauch Settings Options
	 * */
	public void showSettingsAlert(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

		// Setting Dialog Title
		alertDialog.setTitle("GPS is settings");

		// Setting Dialog Message
		alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				mContext.startActivity(intent);
			}
		});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	@Override
	public void onLocationChanged(Location location)
	{
		this.getLocation();
		updateTimer++;
		if(updateTimer == 360)
		{
			ParseQuery<ParseObject> query = ParseQuery.getQuery(Game.class.getSimpleName());
			query.whereEqualTo("players", ParseUser.getCurrentUser().getObjectId());
			query.whereLessThanOrEqualTo(DbContract.Game.START_TIME, new Date());
			query.whereGreaterThanOrEqualTo(DbContract.Game.END_TIME, new Date());
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> objects, ParseException e) {			  
					if (e == null) {
						List<String> mines = new ArrayList<String>();
						Log.d("Games in:", ""+objects.size());
						for(int i=0; i<objects.size(); i++){
							Game game = new Game(objects.get(i));
							List<String> gameMines = game.getLocationObjects();
							for(int j=0; j<gameMines.size(); j++){
								mines.add(gameMines.get(j));
							}
							//targetIds.add(targetId);
							//Log.d("Target",targetId);
						}
						ParseQuery<ParseObject> mineQuery = ParseQuery.getQuery(Mine.class.getSimpleName());
						mineQuery.whereContainedIn("objectId", mines);
						mineQuery.findInBackground(new FindCallback<ParseObject>(){

							@Override
							public void done(List<ParseObject> objects, ParseException e) {
								List<Mine> mines = new ArrayList<Mine>();
								for(int i=0; i<objects.size(); i++){
									mines.add(new Mine(objects.get(i)));
								}
								actualMines = mines;
							}
						});
					}
					else
					{
						// something went wrong
					}
				}
			});
		}
		//this.latitude = location.getLatitude();
		//this.longitude = location.getLongitude();
		for(int i = 0; actualMines != null && i < actualMines.size() ; i++)
		{
			if(actualMines.get(i).getPlayer() != ParseUser.getCurrentUser().getObjectId())
			{
				double dlong = (actualMines.get(i).getLongitude() - this.longitude) * 0.0174532925199433;
				double dlat = (actualMines.get(i).getLatitude() - this.latitude) * 0.0174532925199433;
				double a = Math.pow(Math.sin(dlat/2.0), 2) + Math.cos(this.latitude*0.0174532925199433) * Math.cos(actualMines.get(i).getLatitude()*0.0174532925199433) * Math.pow(Math.sin(dlong/2.0), 2);
				double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
				double distance = 6367 * c;
				if(distance <= .01)
				{
					KillAction kill = new KillAction(KillActionType.GPS);
					kill.setPlayer(actualMines.get(i).getPlayer());
					kill.setIsVerified(true);
					kill.push();
				}
			}
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}