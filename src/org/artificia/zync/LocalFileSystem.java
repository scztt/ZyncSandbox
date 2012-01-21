package org.artificia.zync;

import java.io.File;
import java.net.URI;
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
	
	public String getRootPath()
	{
		return settings.rootPath;
	}
	
	public URI getRootURI()
	{
		return (new File(settings.rootPath)).toURI();
	}
	
	public Vector<File> findAllAssets()
	{
		Vector<File> assets = new Vector<File>();
		
		Iterator<File> fileIter = FileUtils.iterateFiles(new File(settings.rootPath), assetSpec, true);
		while (fileIter.hasNext())
		{
			assets.addElement(fileIter.next());
		}
		
		return assets;
	}
	
	
}
