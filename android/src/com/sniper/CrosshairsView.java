package com.sniper;

import com.sniper.core.Camera;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

public class CrosshairsView extends SurfaceView{
	Bitmap landscape, portrait;
	public CrosshairsView(Context context) {
		super(context);
		LoadBitmaps t = new LoadBitmaps();
		t.execute();
	}
	
	private class LoadBitmaps extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			portrait = BitmapFactory.decodeResource(getResources(), R.drawable.scope_view);       
			landscape = BitmapFactory.decodeResource(getResources(), R.drawable.scope_view_land);
			return null;
		}

	}
			
	public CrosshairsView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
		
	public CrosshairsView(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
    public void onDraw(Canvas canvas) 
    {
        super.onDraw(canvas);
        
        Bitmap bitmap;
        if(getResources().getConfiguration().orientation == 
				Configuration.ORIENTATION_PORTRAIT){
        	bitmap = portrait;
        }
        else{
        	bitmap = landscape;
        }
        RectF area = new RectF(0, 0, this.getWidth(), this.getHeight());
        if(bitmap != null)
        	canvas.drawBitmap(bitmap, null,  area, null);
    }
}
