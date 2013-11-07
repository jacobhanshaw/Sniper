package com.sniper;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class ActivityLogin extends Activity {

	private Dialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
public void login(View v) {
		
		//Pull Field Info
		EditText tEmail = (EditText) findViewById(R.id.editText1);
		EditText tPassword = (EditText) findViewById(R.id.editText2);
		String sEmail = tEmail.getText().toString();
		String sPassword = tPassword.getText().toString();		
		
		ActivityLogin.this.progressDialog = ProgressDialog.show(ActivityLogin.this, "",
				"Logging In...", true);
		ParseUser.logInInBackground(sEmail, sPassword, new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			    	success();	    	
			      
			    } else {
			    	Log.e("Debug", "Sign In Error: " + e.getMessage());
			    }
			    ActivityLogin.this.progressDialog.dismiss();
			  }
			});
			
	}
	private void success() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void register(View v) throws InterruptedException, ExecutionException {
		//Pull Field Info
		String sEmail = "User@domain.com";
		String sPassword = "password";

		ParseUser user = new ParseUser();
		user.put("firstName", "User");
		user.put("lastName", "Test");
		user.setUsername(sEmail);
		user.setPassword(sPassword);
		user.setEmail(sEmail);

		ActivityLogin.this.progressDialog = ProgressDialog.show(ActivityLogin.this, "",
				"Registering...", true);
		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
				} else {
					Log.e("Debug", "Register Error: " + e.getMessage());
				}
				ActivityLogin.this.progressDialog.dismiss();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

}
