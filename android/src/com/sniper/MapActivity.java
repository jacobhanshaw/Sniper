package com.sniper;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sniper.core.GpsLocationService;

public class MapActivity extends FragmentActivity {

	private GoogleMap map;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		if(map == null)
		{
			map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		}
		/*GoogleMap map = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();*/

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(ActivityMain.gps.getLatitude(), ActivityMain.gps.getLongitude()), 16));

		// You can customize the marker image using images bundled with
		// your app, or dynamically generated bitmaps. 
		
		/*GoogleMap googleMap;
        googleMap = ((SupportMapFragment)(getSupportFragmentManager().findFragmentById(R.id.map))).getMap();
        LatLng latLng = new LatLng(-33.796923, 150.922433);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("My Spot")
                .snippet("This is my spot!")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));*/
	}
	
	public void PlaceMine(View view)
	{
		map.addMarker(new MarkerOptions()
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.mine_marker))
		.anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
		.position(new LatLng(ActivityMain.gps.getLatitude(), ActivityMain.gps.getLongitude())));
	}

}
