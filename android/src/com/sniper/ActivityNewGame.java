package com.sniper;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class ActivityNewGame extends FragmentActivity {
	private TextView startDisplayTime;
	private TextView endDisplayTime;
	private Button btnChangeTime;
	private Button btnChangeEndTime;
 
	private int starthour;
	private int startminute;
	private int endhour;
	private int endminute;
	
	private TextView startDisplayDate, endDisplayDate;
	private Button btnChangeStartDate, btnChangeEndDate;
	private int startDay, startMonth, startYear;
	private int endDay, endMonth, endYear;
 
	static final int TIME_DIALOG_ID_Start = 999;
	static final int TIME_DIALOG_ID_End = 998;
	static final int DATE_DIALOG_ID_Start = 997;
	static final int DATE_DIALOG_ID_End = 996;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_new_game);
		
		setCurrentTimeOnView();
		addListenerOnButton();
	}
	
	public void CreateGame(View v){
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_game, menu);
		return true;
	}
	public void setCurrentTimeOnView() {
		 
		startDisplayTime = (TextView) findViewById(R.id.tvTime);
		endDisplayTime = (TextView) findViewById(R.id.endTime);
		startDisplayDate = (TextView) findViewById(R.id.startdate);
		endDisplayDate = (TextView) findViewById(R.id.enddate);
 
		final Calendar c = Calendar.getInstance();
		starthour = c.get(Calendar.HOUR_OF_DAY);
		startminute = c.get(Calendar.MINUTE);
		endhour = starthour;
		endminute = startminute;
		startDay = c.get(Calendar.DAY_OF_MONTH);
		startMonth = c.get(Calendar.MONTH);
		startYear = c.get(Calendar.YEAR);
		endDay = startDay;
		endMonth = startMonth;
		endYear = startYear;
 
		// set current time into textview
		startDisplayTime.setText(
                    new StringBuilder().append(pad(starthour))
                                       .append(":").append(pad(startminute))); 
		
		endDisplayTime.setText(
                new StringBuilder().append(pad(endhour))
                                   .append(":").append(pad(endminute)));
		
		startDisplayDate.setText(
				new StringBuilder().append(pad(startMonth)).append("/")
				.append(pad(startDay)).append("/").append(startYear));
		
		endDisplayDate.setText(
				new StringBuilder().append(pad(endMonth)).append("/")
				.append(pad(endDay)).append("/").append(endYear));
 
	}
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
	public void addListenerOnButton() {
		 
		btnChangeTime = (Button) findViewById(R.id.btnChangeTime);
 
		btnChangeTime.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID_Start);
 
			}
 
		});
		
		btnChangeEndTime = (Button) findViewById(R.id.btnChangeEndTime);		 
		btnChangeEndTime.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID_End); 
			}
 
		});
		
		btnChangeEndDate = (Button) findViewById(R.id.changeEndDate);		 
		btnChangeEndDate.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID_End); 
			}
 
		});
		
		btnChangeStartDate = (Button) findViewById(R.id.changeStartDate);		 
		btnChangeStartDate.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID_Start); 
			}
 
		});
 
	}
 
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID_Start:
			// set time picker as current time
			return new TimePickerDialog(this, 
                    timePickerListener, starthour, startminute,false);
		case TIME_DIALOG_ID_End:
			return new TimePickerDialog(this, 
                    timePickerListenerEnd, endhour, endminute,false);
		case DATE_DIALOG_ID_Start:
			return new DatePickerDialog(this,
					startDatePickerListener, startYear, startMonth, startDay);
		case DATE_DIALOG_ID_End:
			return new DatePickerDialog(this,
					endDatePickerListener, endYear, endMonth, endDay); 
		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener startDatePickerListener = 
            new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int year, int month, int day) {
			startYear = year;
			startMonth= month;
			startDay = day;
			
			startDisplayDate.setText(
					new StringBuilder().append(pad(startMonth)).append("/")
					.append(pad(startDay)).append("/").append(startYear));
			
		}
	};
	
	private DatePickerDialog.OnDateSetListener endDatePickerListener = 
            new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int year, int month, int day) {
			endYear = year;
			endMonth= month;
			endDay = day;
			
			endDisplayDate.setText(
					new StringBuilder().append(pad(endMonth)).append("/")
					.append(pad(endDay)).append("/").append(endYear));
			
		}
	};
 
	private TimePickerDialog.OnTimeSetListener timePickerListener = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {			
			starthour = selectedHour;
			startminute = selectedMinute;
 
			// set current time into textview
			startDisplayTime.setText(new StringBuilder().append(pad(starthour))
					.append(":").append(pad(startminute)));
		}
	};

	private TimePickerDialog.OnTimeSetListener timePickerListenerEnd = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {			
				endhour = selectedHour;
				endminute = selectedMinute;
	 
				// set current time into textview
				endDisplayTime.setText(new StringBuilder().append(pad(endhour))
						.append(":").append(pad(endminute)));	
			
		}
	};
}
