package org.artificia.zync;

import java.lang.String;
import java.io.*;
import java.util.Properties;

public class LocalFileSystemSettings {
	// make these private later
	public String rootPath;
	public String databasePath;

	public LocalFileSystemSettings()
	{
	
	}
	
	public LocalFileSystemSettings(String inPath)
	{
		this.serializeFromFile(inPath);
	}
	
	public String getRootPath()
	{
		return rootPath;
	}
	
	public String getDatabaseLocation()
	{
		return databasePath;
	}
	
	public Boolean serializeFromFile(String inPath)
	{
		Properties newProperties = new Properties();
		
		try {
			newProperties.loadFromXML(new FileInputStream(new File(inPath)));
		}
		catch (Exception e)
		{
			return false;
		}
		
		if (newProperties.containsKey("rootPath"))
		{
			this.rootPath = newProperties.getProperty("rootPath");
		}
		
		if (newProperties.containsKey("databasePath"))
		{
			this.databasePath = newProperties.getProperty("databasePath");
		}
		
		return true;
	}
	
	public Boolean serializeToFile(String inPath)
	{
		Properties outProps = new Properties();
		outProps.put("rootPath", this.rootPath);
		outProps.put("databasePath", databasePath);
		
		try {
			File outFile = new File(inPath);
			FileOutputStream stream = new FileOutputStream(outFile);
			outProps.storeToXML(stream, "No comment.");
		}
		catch (Exception e)
		{
			return false;
		}
		
		return true;
	}

	
	private Boolean validatePath(String path)
	{
		File prospectiveFile = new File(path);
		return prospectiveFile.exists();
	}
}
