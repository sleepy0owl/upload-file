package com.msqube.qii.sales.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
	//the upload file directory 
	private String uploadDir;
	
	//getter and setter methods 
	public String getUploadDir() {
		return uploadDir;
	}
	
	public void setUploadDir(String dir) {
		this.uploadDir = dir;
	}
}
