package org.artificia.zync;

import java.io.File;
import java.util.*;

public class Node 
	implements AssetIDGenerator
{
	private static int id = 0;
	
	private FileSystem ownFileSystem;
	private AssetDatabase ownAssets;
	private ChangeDatabase ownChanges;
	
	private String nodeID;
	private int someNumber = 0;
	
	public Node(FileSystem inFileSystem)
	{
		ownFileSystem = inFileSystem;
		ownAssets = new AssetDatabase();
		ownChanges = new ChangeDatabase();
		nodeID = Integer.toString(Node.id++);
	}
	
	public FileSystem getFileSystem()
	{
		return this.ownFileSystem;
	}
	
	public void test_populateDatabase()
	{
		// Assumes an empty database, and populates it.
		ownAssets = new AssetDatabase();
		Vector<File> assets = this.ownFileSystem.findAllAssets();
		
		Iterator<File> iter = assets.iterator();
		while (iter.hasNext())
		{
			File file = iter.next();
			
			Asset newAsset = new Asset();
			newAsset.name = file.getName();
			newAsset.path = PathUtils.getRelativePath(file.getParentFile().toString(), this.ownFileSystem.getRootPath(), "/");
			newAsset.uniqueID = generateUniqueAssetID();
			
			this.ownAssets.insertAsset(newAsset);
		}
	}
	
	public void test_dumpDatabase()
	{
		Iterator<Asset> iter = this.ownAssets.assetDB.iterator();
		while (iter.hasNext())
		{
			System.out.print(iter.next().toString());
		}
	}
	
	public String generateUniqueAssetID()
	{
		return (new Date()).getTime() + "." +  this.nodeID + "." + (someNumber++);
	}
}
