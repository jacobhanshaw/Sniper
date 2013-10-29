package com.sniper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Button;

public class HomeButton extends Button{
    private Paint paint = new Paint();
    
	public HomeButton(Context context) {
		super(context);
	}
	
	public HomeButton(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	
	public HomeButton(Context context, AttributeSet attrs){
		super(context, attrs);
	}

	@SuppressLint("DrawAllocation")
	@Override
    public void onDraw(Canvas canvas) 
    {
        super.onDraw(canvas);
        
        int pad = dpToPx(3.5);
               
        //canvas.drawColor(Color.WHITE, Mode.SRC_OVER);  
        paint.setColor(Color.WHITE);
        RectF rect;
        if(this.isPressed()){
        	rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        }
        else{
        	rect = new RectF(pad, pad, this.getWidth()-pad, this.getHeight()-pad);
        }
    	canvas.drawRect(rect, paint);
    	

        if(this.isPressed())
        {
        	paint.setColor(Color.rgb(172, 224, 244));
            DrawShape(canvas, 0, true); 
        }

        paint.setColor(0xffd5d5d5);
        if(this.isPressed()){
        	paint.setColor(Color.rgb(76, 190, 232));        	
        }
        DrawShape(canvas, pad, false);
               
        paint.setColor(0xff707070);
        if(this.isPressed()){
        	paint.setColor(Color.rgb(65, 170, 209));        	
        }
        rect = new RectF(pad, this.getHeight()-pad-2, this.getWidth()-pad, this.getHeight()-pad-1);
    	canvas.drawRect(rect, paint);
    	
    	paint.setColor(0xffd1d1d1);
    	if(this.isPressed()){
        	paint.setColor(Color.rgb(139, 193, 214));        	
        }
    	rect = new RectF(pad, this.getHeight()-pad-1, this.getWidth()-pad, this.getHeight()-pad);
    	canvas.drawRect(rect, paint);
        
    }
	
	private void DrawShape(Canvas canvas, int pad, boolean round){
		float radius = this.getHeight()*2/5;
        if(round){
        	RectF rect = new RectF(pad, radius, this.getWidth()-pad, this.getHeight()- pad - dpToPx(10));
        	canvas.drawRect(rect, paint);
        	rect = new RectF(pad, radius, this.getWidth()-pad, this.getHeight()- pad);
        	canvas.drawRoundRect(rect, radius*.1f, radius*.1f, paint);
        }
        else{
        	RectF rect = new RectF(pad, radius, this.getWidth()-pad, this.getHeight()- pad);
            canvas.drawRect(rect, paint);
        }
       
        RectF oval = new RectF((float) (pad+.1*this.getWidth()), pad, (float) (.9*this.getWidth()-pad), 2*radius);
        //RectF oval = new RectF(pad, pad, this.getWidth()-pad, 2*radius);
        canvas.drawArc(oval, 180, 180, true, paint);
	}
	
	private int dpToPx(double dp) {
	    DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
	    int px = (int) Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}
}
