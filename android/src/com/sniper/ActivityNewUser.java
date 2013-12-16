package com.sniper;

import java.util.concurrent.ExecutionException;

import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ActivityNewUser extends Activity {
	
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
	}

	//gets edit text from id used in xml
	private String GetString(int id){
		EditText e = (EditText) findViewById(id);
		return e.getText().toString();
	}
	
	//read fields, creates and pushes a new user
	public void register(View v) throws InterruptedException, ExecutionException {
		ParseUser user = new ParseUser();
		user.put("firstName", GetString(R.id.FirstName));
		user.put("lastName", GetString(R.id.LastName));
		user.setUsername(GetString(R.id.UserName));
		user.setPassword(GetString(R.id.Password));
		user.setEmail(GetString(R.id.Email));

		//show progress dialog while registering
		this.progressDialog = ProgressDialog.show(this, "",
				"Registering...", true);		
		user.signUpInBackground(new SignUpCallback() {
			@Override
			public void done(com.parse.ParseException e) {
				if (e == null) {
				} else {
					Log.e("Debug", "Register Error: " + e.getMessage());
				}
				ActivityNewUser.this.progressDialog.dismiss();
				
				ActivityNewUser.this.progressDialog = 
						ProgressDialog.show(ActivityNewUser.this, "",
						"Logging In...", true);
				ParseUser.logInInBackground(
						ActivityNewUser.this.GetString(R.id.UserName), 
						ActivityNewUser.this.GetString(R.id.Password), 
						new LogInCallback() {		  

					//when done registering, dismiss progress dialog and return to sign in
					@Override
					public void done(ParseUser user,
							com.parse.ParseException e) {
						if (user != null) {
							Intent intent = new Intent(ActivityNewUser.this, ActivityMain.class);
							startActivity(intent);   				      
					    } else {
					    	Log.e("Debug", "Sign In Error: " + e.getMessage());
					    }
						ActivityNewUser.this.progressDialog.dismiss();						
					}
					});
			}
		});
	}

}
