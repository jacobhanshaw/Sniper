package com.sniper.core;

import java.io.File;
import java.lang.reflect.Method;

import com.amazonaws.services.s3.AmazonS3Client;

public class AWSFileUploadObject
{
	public AmazonS3Client s3Client;
	public File file;
	public String bucket;
	public String name;
	public Method postExecute;
}
