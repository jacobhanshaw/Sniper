package com.threedaystartup.sniper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
      //Setup the FrameLayout with the Camera Preview Screen
        final CameraSurfaceView cameraSurfaceView = new CameraSurfaceView(this);
        
        //openCamera();
        LinearLayout view = (LinearLayout) findViewById(R.id.surfaceLayout);
        view.addView(cameraSurfaceView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    }
}
