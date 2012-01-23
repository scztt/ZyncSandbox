package org.artificia.zync;

import java.io.File;
import java.sql.Time;
import java.util.*;
import java.util.logging.*;

public class Node 
	implements AssetIDGenerator
{
    private static Logger logger = Logger.getLogger("org.artificia.zync");
	private static int id = 0;
	
	private AssetDatabase allAssets;
	private FileSystem ownFileSystem;
	private AssetRefDatabase ownAssets;
	private ChangeDatabase ownChanges;
	
	private String nodeID;
	private int someNumber = 0;
	
	public Node(FileSystem inFileSystem)
	{
		ownFileSystem = inFileSystem;
		allAssets = new AssetDatabase(ownFileSystem.getSettings());
		ownAssets = new AssetRefDatabase(ownFileSystem.getSettings());
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
		LinkedList<File> assets = this.ownFileSystem.findAllAssets();
		
		Iterator<File> iter = assets.iterator();
		while (iter.hasNext())
		{
			File file = iter.next();
			
			Asset newAsset = new Asset();
			newAsset.uniqueID = generateUniqueAssetID();
			
			//this.ownAssets.insertAsset(newAsset);
			AssetDatabaseChange add = new AssetDatabaseChange();
			add.asset = newAsset;
			add.assetUniqueID = newAsset.uniqueID;
			add.date = new Date();
			add.nodeOriginator = "someNode";
			add.type = ChangeType.Add;
			allAssets.applyChange(add);
			
			AssetRef newAssetRef = new AssetRef();
			newAssetRef.uniqueID = newAsset.uniqueID;
			newAssetRef.name = file.getName();
			newAssetRef.path = PathUtils.getRelativePath(file.getParentFile().toString(), this.ownFileSystem.getRootPath(), "/");
			newAssetRef.lastChanged = new Time(file.lastModified());
			newAssetRef.size = (int)file.length();
			ownAssets.addAssetRef(newAssetRef);
		}
	}
	
	public void test_updateDatebase()
	{
		Date startTime = new Date();
		
		LinkedList<AssetRef> missingFiles = new LinkedList<AssetRef>();
		LinkedList<AssetRef> changedFiles = new LinkedList<AssetRef>();
		LinkedList<File> existingFiles = new LinkedList<File>();
		LinkedList<File> newFiles;
		
		// Iterate all AssetRef's and match them to on-disk assets.
		AssetRefIterator iter = ownAssets.iterator();
		while (iter.hasNext())
		{
			AssetRef ref = iter.next();
			File localFile = new File(ownFileSystem.constructPath(ref.path, ref.name));
			
			if (!localFile.exists())
				missingFiles.add(ref);
			else 
			{
				if(localFile.length() != ref.size || localFile.lastModified() != ref.lastChanged.getTime())
					changedFiles.add(ref); 
				existingFiles.add(localFile);
			}
		}
		
		// Iterate over all files to check for new ones
		newFiles = this.ownFileSystem.findAllAssets();

		newFiles.removeAll(existingFiles);
		
		Date endTime = new Date();

		System.out.println("existing:\n " + existingFiles);
		System.out.println("new:\n " + newFiles);
		System.out.println("missing:\n " + missingFiles);
		System.out.println("changed:\n " + changedFiles);
		System.out.println("time: " + ((endTime.getTime() - startTime.getTime())));  	
	}
	
	public void test_dumpDatabase()
	{
	}
	
	public String generateUniqueAssetID()
	{
		return (new Date()).getTime() + "." +  this.nodeID + "." + (someNumber++);
	}
}
