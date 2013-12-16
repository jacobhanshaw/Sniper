package com.sniper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class CrosshairsView extends SurfaceView{
	private static Bitmap landscape, portrait;
	public CrosshairsView(Context context) {
		super(context);
		if(landscape == null || portrait == null)
		{
			LoadBitmaps t = new LoadBitmaps();
			t.execute();
		}
	}
	
	//load bitmap images asyncronously once
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
		// draw appropriate bitmap over camera preview
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
