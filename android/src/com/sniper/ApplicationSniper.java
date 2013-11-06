package com.sniper;

import java.net.URL;
import java.util.Date;

import android.app.Application;
import android.text.format.Time;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class ApplicationSniper extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		AmazonS3Client s3Client = new AmazonS3Client( new BasicAWSCredentials("AKIAIMVSJFLDXG2HFRRQ", "N+NXPGsf1tY03BcEZiE7oYR/mkSv5H7N9f5k5djB"));   
		
		if(!s3Client.doesBucketExist("PlayerName"))
			s3Client.createBucket( "PlayerName" );
		
		Time now = new Time();
		now.setToNow();
		
		// Bucket, filename, file
	//	PutObjectRequest por = new PutObjectRequest( "PlayerName", "EventName", new java.io.File( filePath) );  
	//	por.withCannedAcl(CannedAccessControlList.PublicRead);
	//	s3Client.putObject(por);
		
		ResponseHeaderOverrides override = new ResponseHeaderOverrides();
		override.setContentType( "image/jpeg" );
		
		GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest( "PlayerName", "EventName");
		urlRequest.setExpiration( new Date( System.currentTimeMillis() + 3600000 ) );  // Added an hour's worth of milliseconds to the current time.
		urlRequest.setResponseHeaders( override );
		
		URL url = s3Client.generatePresignedUrl( urlRequest );
		
		Parse.initialize(this, "NiXxI3aBRsW4xv1Og9p1jada5qOV9ldWAVHFXUgo", "Dld5dAF5rIvpy7laIaIbqMuROnOckDP11u9NSn0h"); 
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		// defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
	}



}
