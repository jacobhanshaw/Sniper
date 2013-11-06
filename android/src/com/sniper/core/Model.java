package com.sniper.core;

public class Model
{
	public Profile currentUser;

	private static Model instance = null;

	protected Model()
	{
	}

	public static Model getInstance()
	{
		if (instance == null)
			instance = new Model();
		return instance;
	}
}
