package com.sniper;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import com.parse.ParseUser;
import com.sniper.core.Camera;

public class MainActivity extends FragmentActivity {
	private Camera camera;
	//private static final int SELECT_PHOTO = 100;
	String[] targetNames = {};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("Debug", ParseUser.getCurrentUser().getEmail().toString());
		
		if(getResources().getConfiguration().orientation == 
				Configuration.ORIENTATION_PORTRAIT){
			setContentView(R.layout.activity_main);
		}
		else{
			setContentView(R.layout.activity_main_landscape);
		}
		//setContentView(R.layout.activity_main);
		
		camera = new Camera(this);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	    preview.addView(camera);
	    
	    ImageView iv= (ImageView)findViewById(R.id.target_image);
	    iv.setImageResource(R.drawable.target_image);
	    
	    //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	    //intent.setType("image/*");
	    //startActivityForResult(intent, SELECT_PHOTO)
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
