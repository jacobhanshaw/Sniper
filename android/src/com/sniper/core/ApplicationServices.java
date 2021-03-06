package com.sniper.core;

import java.io.File;
import java.lang.reflect.Method;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

public class ApplicationServices
{
        private static ApplicationServices instance = null;

        private AmazonS3Client s3Client;
        private final String baseBucketName = "sniper";
        private final String killBucketName = "kill";
        private final String UserPhotoBucketName = "profilepictures";
        
        /*
         * Make ApplicationServices a singleton by protecting its constructor and accessing it by getting the singleton instance
         * 
         */
        
        protected ApplicationServices()
        {
                s3Client = new AmazonS3Client( new BasicAWSCredentials("AKIAIMVSJFLDXG2HFRRQ", "N+NXPGsf1tY03BcEZiE7oYR/mkSv5H7N9f5k5djB")); 
        }

        public static ApplicationServices getInstance()
        {
                if (instance == null)
                        instance = new ApplicationServices();
                return instance;
        }

        /*
         * Upload photos to buckets (similar to folders) based on type with specified photo name
         * 
         */
        
        public void uploadUserPhoto(File photo, String name, Method completionMethod)
        {                        
        	String bucket = baseBucketName + "_" + UserPhotoBucketName;    
        	uploadImageToBucket(photo, name, bucket, completionMethod);
        }
        
        public void uploadKillPhoto(File photo, String name, Method completionMethod)
        {
                String bucket = baseBucketName + "_"  + killBucketName;
                uploadImageToBucket(photo, name, bucket, completionMethod);
        }
        
        private void uploadImageToBucket(File photo, String name, String bucket, Method completionMethod)
        {
                    AWSFileUploadObject awsObject = new AWSFileUploadObject();
                    awsObject.s3Client = s3Client;
                    awsObject.file = photo;
                    awsObject.bucket = bucket;
                    awsObject.name = name;
                    awsObject.postExecute = completionMethod;
                    
                    AWSRequest request = new AWSRequest();
                
                request.execute(awsObject);
        }

}