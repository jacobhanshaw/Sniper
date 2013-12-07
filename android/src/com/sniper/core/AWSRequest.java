package com.sniper.core;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;

public class AWSRequest extends AsyncTask<AWSFileUploadObject, Void, String>
{
	public AWSFileUploadObject awsObject;
		
	protected String doInBackground(AWSFileUploadObject... params)
	{
		awsObject = params[0];
        if(!awsObject.s3Client.doesBucketExist(awsObject.bucket))
        {
        	awsObject.s3Client.createBucket(awsObject.bucket);
        	awsObject.s3Client.setBucketAcl(awsObject.bucket, CannedAccessControlList.PublicRead);
        }
        
        // Bucket, filename, file
        PutObjectRequest por = new PutObjectRequest(awsObject.bucket, awsObject.name, awsObject.file);  
        por.withCannedAcl(CannedAccessControlList.PublicRead);
        awsObject.s3Client.putObject(por);
        
        ResponseHeaderOverrides override = new ResponseHeaderOverrides();
        override.setContentType( "image/jpeg" );
        
        return "http://s3.amazonaws.com/" + awsObject.bucket + "/" + awsObject.name;
	}
	
	protected void onPostExecute(String result)
	{
		super.onPostExecute(result);
		try
		{
			Log.v("Debug", result);
			awsObject.postExecute.invoke(Camera.class, result);
		}
		catch(Exception e) { }
	}

}
