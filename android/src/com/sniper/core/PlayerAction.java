package com.sniper.core;

import android.text.format.Time;

public class PlayerAction
{
	public Time started;
	public Time ended;
	
	
	public PlayerAction()
	{
		Time now = new Time();
		now.setToNow();
		started = now;
	}
}
