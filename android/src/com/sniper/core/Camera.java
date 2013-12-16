package com.sniper.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import com.parse.ParseUser;
import com.sniper.ActivityMain;
import com.sniper.CrosshairsView;
import com.sniper.core.KillAction.KillActionType;

/*
 * Implement surface view for camera which includes camera functions
 * to keep all the camera stuff packaged together
 */
public class Camera extends CrosshairsView implements SurfaceHolder.Callback  {
	private static final String TAG = "Camera Error";
	//hold onto context used to create for later use
	public static Context context;
		
	private SurfaceHolder mHolder;
    public static android.hardware.Camera mCamera;
    //get manager to check for orientation
    private WindowManager mWindowManager;
    
    public static boolean isPortrait;

    public Camera(Context context) {
        super(context);
        Camera.context = context;
        
        RefreshCamera();
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        
        mHolder = getHolder();
        mHolder.addCallback(this);
    }
        
    // take picture as static so it can be called from anywhere
    // trust others to only call from home screen when
    // camera open, or will fail...
    public static void TakePicture(){
    	mCamera.takePicture(null, null, jpegCallback);
		mCamera.startPreview();
    }
    
    // callback after take picture to save image and send kill notification
    private static PictureCallback jpegCallback = new PictureCallback() {
        @SuppressLint("SimpleDateFormat")
		@Override
            public void onPictureTaken(byte[] data, android.hardware.Camera camera) { 
        	//setup bitmap
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true; 
            options.inTempStorage = new byte[32 * 1024];
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bMap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

            // rotate if portrait
            Bitmap bMapRotate;
            if (Camera.isPortrait) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(),
                        bMap.getHeight(), matrix, true);
            } else
                bMapRotate = Bitmap.createScaledBitmap(bMap, bMap.getWidth(),
                        bMap.getHeight(), true);


            FileOutputStream out;
            try {
            	//save file
	            String baseDir = Environment.getExternalStoragePublicDirectory(
	            		Environment.DIRECTORY_PICTURES).getAbsolutePath();
	            String fileName = "/MyCameraApp/" + System.currentTimeMillis() + ".jpg";

                out = new FileOutputStream(baseDir + fileName);
                bMapRotate.compress(Bitmap.CompressFormat.JPEG, 50, out);
                if (bMapRotate != null) {
                    bMapRotate.recycle();
                    bMapRotate = null;
                }

                Method method = null;
    			try
    			{
    				method = Camera.class.getMethod("receiveResponse", String.class);
    			} catch (NoSuchMethodException e)
    			{
    				//dont care
    			}
    	        
    			out.flush();
    			out.close();
    			
    			// save kill photo to aws
    			String title = ParseUser.getCurrentUser().getUsername()+"_";
    			title += new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    	        ApplicationServices.getInstance().uploadKillPhoto(new File(baseDir + fileName), title, method);

            } catch (FileNotFoundException e) {
                // ignore
            } catch (IOException e) {
				// ignore
			}

        }
    };
    
    //called after kill photo uploaded sends kill action to parse
	public static void receiveResponse(String response)
	{
		Log.v("Debug", "receiveResponse");		
		KillAction kill = new KillAction(KillActionType.CAMERA);
		kill.setPhotoURL(response);
		kill.setTarget(ActivityMain.target.getObjectId());
		kill.push();

	}
		
	// re-open camera if possible
	private void RefreshCamera(){
    	try{
    		// try to open camera
    		mCamera = android.hardware.Camera.open();
    		
    	}catch(Exception e){
    		// camera was already open
    	}
    }

    public void surfaceCreated(SurfaceHolder holder) {
    	RefreshCamera();
    	setWillNotDraw(false);
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    	mCamera.release();
    }
    
    //update orienation
    private void SetOrientation(){
    	if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
        	mCamera.setDisplayOrientation(90);
        	isPortrait = true;
        }        
        else{
        	Display mDisplay = mWindowManager.getDefaultDisplay();
        	int rot = mDisplay.getRotation();
        	if(!(rot == Surface.ROTATION_0 || rot == Surface.ROTATION_90)){
        		mCamera.setDisplayOrientation(180);
        	}
        	isPortrait = false;
        } 
    }
    
    //get highest quality suported size for preveiw
    private android.hardware.Camera.Size getBestPreviewSize(int width, int height,
    		android.hardware.Camera.Parameters parameters) {
    	
	    android.hardware.Camera.Size result=null;
	
		for (android.hardware.Camera.Size size : parameters.getSupportedPreviewSizes()) {
			if (size.width <= width && size.height <= height) {
				if (result == null) {
					result=size;
				}
				else {
					int resultArea=result.width * result.height;
					int newArea=size.width * size.height;
					
					if (newArea > resultArea) {
						result=size;
					}
				}
			}
		}

		return(result);
    }

    // get highest quality size suported for saving picture
	private android.hardware.Camera.Size getBestPictureSize(android.hardware.Camera.Parameters parameters) {
		android.hardware.Camera.Size result=null;
		
		for (android.hardware.Camera.Size size : parameters.getSupportedPictureSizes()) {
			if (result == null) {
				result=size;
			}
			else {
				int resultArea=result.width * result.height;
				int newArea=size.width * size.height;
				
				if (newArea > resultArea) {
					result=size;
				}
			}
		}
		
		return(result);
	}

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        RefreshCamera();
    	
        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }

        SetOrientation(); 

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            
            android.hardware.Camera.Parameters parameters=mCamera.getParameters();
            android.hardware.Camera.Size size= getBestPreviewSize(w, h, parameters);
            android.hardware.Camera.Size pictureSize= getBestPictureSize(parameters);

            if (size != null && pictureSize != null) {
               parameters.setPreviewSize(size.width, size.height);
               parameters.setPictureSize(pictureSize.width,
                                            pictureSize.height);
               parameters.setPictureFormat(ImageFormat.JPEG);
               mCamera.setParameters(parameters);
             }
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }    
}
