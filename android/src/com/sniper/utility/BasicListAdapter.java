package com.sniper.utility;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sniper.R;

public class BasicListAdapter extends ArrayAdapter<String> {
	protected final Context context;
	protected final String[] values;
	protected int firstPosition;
	private View currentView;
	protected int resource = R.layout.list_item;
	
	public static View lastRowView; // can be used after getView to edit inflated row
 
	public BasicListAdapter(Context context, String[] values, int firstPosition) {
		super(context, R.layout.list_item, values);
		this.context = context;
		this.values = values;
		this.firstPosition = firstPosition;
	}
	
	public BasicListAdapter(Context context, String[] values, int firstPosition, int resource) {
		super(context, R.layout.list_item, values);
		this.context = context;
		this.values = values;
		this.firstPosition = firstPosition;
		this.resource = resource;
	}
	
	public void SelectView(View view){
		this.unhighlightCurrentRow();
		currentView = view;
		this.highlightCurrentRow();		
	}
	
	private void unhighlightCurrentRow() {
		unhighlightRow(currentView);
	}
	
	private void unhighlightRow(View row){
		row.setBackgroundColor(Color.DKGRAY);
	}

	private void highlightCurrentRow() {
		currentView.setBackgroundColor(0xff2d9fc9);
	} 
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(resource, parent, false);
		lastRowView = rowView;
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		textView.setText(values[position]);
		
		if(position == this.firstPosition){
			currentView = rowView;
			highlightCurrentRow();
		}else{
			unhighlightRow(rowView);
		}
		
 			
		/*
		// Change icon based on name
		String s = values[position];
 
		System.out.println(s);
 
		if (s.equals("WindowsMobile")) {
			imageView.setImageResource(R.drawable.windowsmobile_logo);
		} else if (s.equals("iOS")) {
			imageView.setImageResource(R.drawable.ios_logo);
		} else if (s.equals("Blackberry")) {
			imageView.setImageResource(R.drawable.blackberry_logo);
		} else {
			imageView.setImageResource(R.drawable.android_logo);
		}*/
 
		return rowView;
	}
}