package com.sniper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class CrosshairsView extends SurfaceView{
	public CrosshairsView(Context context) {
		super(context);
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
        
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scope_view);        
        RectF area = new RectF(0, 0, this.getWidth(), this.getHeight());
        canvas.drawBitmap(bitmap, null,  area, null);
    }
}
