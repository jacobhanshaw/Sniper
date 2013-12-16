package com.sniper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.parse.ParseUser;
import com.sniper.core.Game;
import com.sniper.utility.MenuHelper;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

@SuppressLint("SimpleDateFormat")
public class ActivityNewGame extends FragmentActivity {
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    return MenuHelper.onOptionsItemSelected(item, this);
	}
	
	//views that need to be repeatedly referenced
	private TextView startDisplayTime;
	private TextView endDisplayTime;
	private Button btnChangeTime;
	private Button btnChangeEndTime;
	private TextView startDisplayDate, endDisplayDate;
	private Button btnChangeStartDate, btnChangeEndDate;
	
	
	private Date startDate, endDate;
 	
	// const for defining dialog types
	static final int TIME_DIALOG_ID_Start = 999;
	static final int TIME_DIALOG_ID_End = 998;
	static final int DATE_DIALOG_ID_Start = 997;
	static final int DATE_DIALOG_ID_End = 996;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_new_game);
		
		startDate = new Date();
		endDate = new Date();
				
		setCurrentTimeOnView();
		addListenerOnButton();
	}
	
	// create game from fields, push to parse and navigate back to game home
	public void CreateGame(View v){		
		Game game = new Game();
		
		EditText name = (EditText) findViewById(R.id.GameName);
		game.setName(name.getText().toString());
		
		EditText houseRules = (EditText) findViewById(R.id.houserules);
		game.setHouseRules(houseRules.getText().toString());
				
		game.setStartTime(startDate);
		game.setEndTime(endDate);
		
		CheckBox safe = (CheckBox)findViewById(R.id.SafeInside);
		CheckBox publicGame = (CheckBox)findViewById(R.id.Public);		
		game.setIsPublic(publicGame.isChecked());
		game.setSafeInside(safe.isChecked());
		
		//set moderator
		game.setModeratorId(ParseUser.getCurrentUser().getObjectId().toString());
		game.setTargetIds(new ArrayList<String>());
		game.setLocationObjects(new ArrayList<String>());
		
		game.push();
		
		Intent intent = new Intent(this, ActivityGamesHome.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_game, menu);
		return true;
	}
	
	//set default times to start tomorrow at current time and
	//end a week from tomorrow at current time
	public void setCurrentTimeOnView() {
		 
		startDisplayTime = (TextView) findViewById(R.id.tvTime);
		endDisplayTime = (TextView) findViewById(R.id.endTime);
		startDisplayDate = (TextView) findViewById(R.id.startdate);
		endDisplayDate = (TextView) findViewById(R.id.enddate);
 
		final Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 1);
		startDate.setTime(c.getTimeInMillis());
		c.add(Calendar.DAY_OF_MONTH, 7);
		endDate.setTime(c.getTimeInMillis());
		 
		startDisplayDate.setText(new SimpleDateFormat("MM/dd/yyyy")
			.format(startDate));
		
		startDisplayTime.setText(new SimpleDateFormat("HH:mm")
			.format(startDate));		
		
		endDisplayDate.setText(new SimpleDateFormat("MM/dd/yyyy")
		.format(endDate));
	
		endDisplayTime.setText(new SimpleDateFormat("HH:mm")
			.format(endDate));
	}
	
	//setup listeners to launch dialogs to edit start and end dates
	//and times when clicked
	public void addListenerOnButton() {
		 
		btnChangeTime = (Button) findViewById(R.id.btnChangeTime);
 
		btnChangeTime.setOnClickListener(new OnClickListener() {
 
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID_Start);
 
			}
 
		});
		
		btnChangeEndTime = (Button) findViewById(R.id.btnChangeEndTime);		 
		btnChangeEndTime.setOnClickListener(new OnClickListener() { 
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID_End); 
			}
 
		});
		
		btnChangeEndDate = (Button) findViewById(R.id.changeEndDate);		 
		btnChangeEndDate.setOnClickListener(new OnClickListener() { 
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID_End); 
			}
 
		});
		
		btnChangeStartDate = (Button) findViewById(R.id.changeStartDate);		 
		btnChangeStartDate.setOnClickListener(new OnClickListener() { 
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID_Start); 
			}
 
		});
 
	}
 
	//create either a date or time picker dialog to set the start and end date options
	@Override
	protected Dialog onCreateDialog(int id) {
		final Calendar c = Calendar.getInstance();
		switch (id) {
		case TIME_DIALOG_ID_Start:
			// set time picker as current time
			c.setTime(startDate);
			return new TimePickerDialog(this, 
                    timePickerListener, c.get(Calendar.HOUR_OF_DAY), 
                    c.get(Calendar.MINUTE),false);
		case TIME_DIALOG_ID_End:
			c.setTime(endDate);
			return new TimePickerDialog(this, 
                    timePickerListenerEnd, c.get(Calendar.HOUR_OF_DAY), 
                    c.get(Calendar.MINUTE),false);
		case DATE_DIALOG_ID_Start:
			c.setTime(startDate);
			return new DatePickerDialog(this,
					startDatePickerListener, c.get(Calendar.YEAR), 
					c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		case DATE_DIALOG_ID_End:
			c.setTime(endDate);
			return new DatePickerDialog(this,
					endDatePickerListener, c.get(Calendar.YEAR), 
					c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		}
		return null;
	}
	
	//set start date from dialog
	private DatePickerDialog.OnDateSetListener startDatePickerListener = 
            new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int year, int month, int day) {
			
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);			
			c.set(year, month, day);	
			startDate.setTime(c.getTimeInMillis());
			
			startDisplayDate.setText(new SimpleDateFormat("MM/dd/yyyy")
				.format(startDate));
			
		}
	};
	
	//set end date from dialog
	private DatePickerDialog.OnDateSetListener endDatePickerListener = 
            new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int year, int month, int day) {
			
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);			
			c.set(year, month, day);	
			endDate.setTime(c.getTimeInMillis());
			
			endDisplayDate.setText(new SimpleDateFormat("MM/dd/yyyy")
				.format(endDate));		
			
		}
	};
 
	//set start time from dialog
	private TimePickerDialog.OnTimeSetListener timePickerListener = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {	
			
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);			
			c.set(Calendar.HOUR_OF_DAY, selectedHour);
			c.set(Calendar.MINUTE, selectedMinute);	
			startDate.setTime(c.getTimeInMillis());
			
			startDisplayTime.setText(new SimpleDateFormat("HH:mm")
				.format(startDate));
		}
	};

	//set end date from dialog
	private TimePickerDialog.OnTimeSetListener timePickerListenerEnd = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {	
			
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);			
			c.set(Calendar.HOUR_OF_DAY, selectedHour);
			c.set(Calendar.MINUTE, selectedMinute);
			endDate.setTime(c.getTimeInMillis());
			
			endDisplayTime.setText(new SimpleDateFormat("HH:mm")
				.format(endDate));					
		}
	};
}
