package org.artificia.zync;

import java.sql.ResultSet;

public class SqlQueryFactory {

	///////////////////////////////////////////////////////////////
	// AssetRef
	public static String AssetRef_CreateTable()
	{
		String str = "create table assetref (" 
				+ "id INTEGER PRIMARY KEY,"
				+ "uniqueID string,"
				+ "path string,"
				+ "name string,"
				+ "size int,"
				+ "lastChanged time"
				+ ")";
		return str;	
	}

	public static String AssetRef_Insert()
	{		
		return "insert into assetref (uniqueID, path, name, size, lastChanged) VALUES (?, ?, ?, ?, ?)";
	}
	
	///////////////////////////////////////////////////////////////
	// Asset
	public static String Asset_CreateTable()
	{
		String str = "create table asset (" 
				+ "id INTEGER PRIMARY KEY,"
				+ "uniqueID string,"
				+ "lastUpdate date,"
				+ "metadata string"
				+ ")";
		return str;	
	}

	public static String Asset_Insert(Asset inAsset)
	{
//		String str = "insert into asset (uniqueID, lastUpdate, metadata) VALUES ("
//				+ "'" + inAsset.uniqueID + "',"
//				+ "'" + inAsset.lastUpdate + "',"
//				+ "'" + inAsset.metadata + "')";
		String str = "insert into asset (uniqueID, lastUpdate, metadata) VALUES (?, ?, ?)";
		return str;
	}

	public static String AssetRef_QueryByUniqueId(String inUniqueId) {
		String str = String.format("select * from assetref where uniqueID = '%'", inUniqueId);
		return str;
	}

	public static AssetRef AssetRef_FromResultSet(ResultSet result) {
		AssetRef assetRef = null;
		
		try 
		{
			if (true)
			{
				assetRef = new AssetRef();
				assetRef.name = result.getString("name");
				assetRef.path = result.getString("path");
				assetRef.uniqueID = result.getString("uniqueID");
				assetRef.size = result.getInt("size");
				assetRef.lastChanged = result.getTime("lastChanged");
			}
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());						
		}
		
		return assetRef;
	}

	public static String AssetRef_All()
	{
		return "select * from assetref";
	}
}
