package com.threedaystartup.sniper;

import java.io.IOException;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder holder;
	private Camera camera;

	public CameraSurfaceView(Context context) {
		super(context);

		// Initiate the Surface Holder properly
		this.holder = this.getHolder();
		this.holder.addCallback(this);
		this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// Open the Camera in preview mode
			this.camera = Camera.open();
			this.camera.setPreviewDisplay(this.holder);
		} catch (IOException ioe) {
			ioe.printStackTrace(System.out);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		Camera.Parameters p = camera.getParameters();
		
		/*if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT)
        {   
            p.set("orientation", "portrait");
            p.set("rotation",90);
        }
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
        {                         */      
            //p.set("orientation", "landscape");          
            //p.set("rotation", 90);
        //}
        
        //p.set("orientation", "portrait");
        //p.set("rotation", 0);
		//parameters.setPreviewSize(width, height);
		camera.setParameters(p);
		setDisplayOrientation(camera, 90);
		camera.startPreview();
	}
	
	 protected void setDisplayOrientation(Camera camera, int angle){
		    Method downPolymorphic;
		    try
		    {
		        downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[] { int.class });
		        if (downPolymorphic != null)
		            downPolymorphic.invoke(camera, new Object[] { angle });
		    }
		    catch (Exception e1)
		    {
		    }
		}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when replaced with a new screen
		// Always make sure to release the Camera instance
		camera.stopPreview();
		camera.release();
		camera = null;
	}
}
