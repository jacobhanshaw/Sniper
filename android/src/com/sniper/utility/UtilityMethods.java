package com.sniper.utility;

import java.util.ArrayList;

import com.parse.ParseObject;

public class UtilityMethods {

	public static ParseObject arrayToParseObject(ArrayList<?> array, String name) {
		ParseObject poArray = new ParseObject(name);
		for(int i = 0; i < array.size(); i++) {
			poArray.put(Integer.toBinaryString(i), array.get(i));
		}
		return poArray;
	}
	
}
