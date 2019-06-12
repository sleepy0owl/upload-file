package com.msqube.qii.sales.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.msqube.qii.sales.exception.FileStorageException;
import com.msqube.qii.sales.exception.MyFileNotFoundException;
import com.msqube.qii.sales.property.FileStorageProperties;

@Service
public class SalesService {
	//file location Path
	private final Path fileStorageLocation;
	
	@Autowired
	public SalesService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
				.toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new FileStorageException("Can not create the upload directory", e);
		}
	}
	
	/**
	 * Method to store a multiPartFile 
	 * */
	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("File name contains invalid path " + fileName);
			}
			
			//target Location
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			//copy the file to targetLocation by replacing existing file with same fileName
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return fileName;
		} catch (IOException e) {
			// TODO: handle exception
			throw new FileStorageException("Can not store file " + fileName + "Please try again!", e);
		}
	}
	
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException e) {
			// TODO: handle exception
			throw new MyFileNotFoundException("File not found" + fileName, e);
		}
	}
}
