package com.sniper.core;

import android.text.format.Time;

public class PlayerAction
{
	Time created;
	Time finished;
	Time updated;
	
	public PlayerAction()
	{
		Time now = new Time();
		now.setToNow();
		created = now;
	}
}
