package org.artificia.zync;

import java.lang.String;
import java.io.*;
import java.util.Properties;

public class LocalFileSystemSettings implements FileSystemSettings {
	// make these private later
	public String rootPath;
	public String libraryDatabasePath;
	public String localDatabasePath;

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
	
	public String getLibraryDatabasePath()
	{
		return libraryDatabasePath;
	}
	
	public String getLocalDatabasePath()
	{
		return localDatabasePath;
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
		
		try
		{
			if (newProperties.containsKey("rootPath"))
			{
				this.rootPath = new File(newProperties.getProperty("rootPath")).getCanonicalPath();
			}
			
			if (newProperties.containsKey("libraryDatabasePath"))
			{
				this.libraryDatabasePath = newProperties.getProperty("libraryDatabasePath");
			}
			
			if (newProperties.containsKey("localDatabasePath"))
			{
				this.localDatabasePath = newProperties.getProperty("localDatabasePath");
			}
		}
		catch (Exception e)
		{ }

		return true;
	}
	
	public Boolean serializeToFile(String inPath)
	{
		Properties outProps = new Properties();
		outProps.put("rootPath", this.rootPath);
		outProps.put("localDatabasePath", localDatabasePath);
		outProps.put("libraryDatabasePath", libraryDatabasePath);
		
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
