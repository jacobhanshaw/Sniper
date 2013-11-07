package com.sniper.core;

import java.io.File;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;

public class ApplicationServices
{
        
        private static ApplicationServices instance = null;

        private AmazonS3Client s3Client;
        private final static String baseBucketName = "sniper";
        private final static String killBucketName = "kill";
        
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

        public String uploadUserPhoto(File photo, String name)
        {
                String bucket = baseBucketName + Model.getInstance().currentUser.name.substring(0, 1);
                return uploadImageToBucket(photo, name, bucket);
        }
        
        public String uploadKillPhoto(File photo, String name)
        {
                String bucket = baseBucketName + killBucketName;
                return uploadImageToBucket(photo, name, bucket);
        }
        
        private String uploadImageToBucket(File photo, String name, String bucket)
        {
                if(!s3Client.doesBucketExist(bucket))
                {
                        s3Client.createBucket(bucket);
                        s3Client.setBucketAcl(bucket, CannedAccessControlList.PublicRead);
                }
                
                // Bucket, filename, file
                PutObjectRequest por = new PutObjectRequest(bucket, name, photo);  
                por.withCannedAcl(CannedAccessControlList.PublicRead);
                s3Client.putObject(por);
                
                ResponseHeaderOverrides override = new ResponseHeaderOverrides();
                override.setContentType( "image/jpeg" );
                
                return "http://s3.amazonaws.com/" + bucket + "/" + name;
        }

}