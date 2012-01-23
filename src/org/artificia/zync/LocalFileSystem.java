package org.artificia.zync;

import java.io.File;
import java.net.URI;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Iterator;

import org.apache.commons.io.*;
import org.artificia.zync.FileSystem;

public class LocalFileSystem implements FileSystem {
	
	private LocalFileSystemSettings settings;
	private AssetRefDatabase refDatabase;
	
	private String[] assetSpec = {"mp3", "m4a", "flac", "ogg", "mp4"};		// stub
	
	
	LocalFileSystem()
	{
		this.settings = new LocalFileSystemSettings("/Users/Scott/Music/ZyncTest/porticoLocalFSSettings.xml");
	}
	
	LocalFileSystem(LocalFileSystemSettings inSettings)
	{
		this.settings = inSettings;
	}
	
	LocalFileSystem(String inSettingsPath)
	{
		this.settings = new LocalFileSystemSettings(inSettingsPath);
	}
	
	public LocalFileSystemSettings getSettings()
	{
		return this.settings;
	}
	
	public String constructPath(String path, String name)
	{	
		try
		{
			return (new File(settings.rootPath, new File(path, name).getPath())).getCanonicalPath();
		}
		catch (Exception e)
		{
			return "";
		}
	}

	public String getRootPath()
	{
		return settings.rootPath;
	}
	
	public URI getRootURI()
	{
		return (new File(settings.rootPath)).toURI();
	}
	
	public LinkedList<File> findAllAssets()
	{
		LinkedList<File> assets = new LinkedList<File>();
		
		Iterator<File> fileIter = FileUtils.iterateFiles(new File(settings.rootPath), assetSpec, true);
		while (fileIter.hasNext())
		{
			assets.add(fileIter.next());
		}
		
		return assets;
	}
}
