package org.artificia.zync;

import java.sql.ResultSet;

public class SqlQueryFactory {

	///////////////////////////////////////////////////////////////
	// AssetRef
	public static String AssetRef_CreateTable()
	{
		String str = "create table assetref (" 
				+ "id int,"
				+ "uniqueID string,"
				+ "path string,"
				+ "name string"
				+ ")";
		return str;	
	}

	public static String AssetRef_Update(AssetRef inAssetRef)
	{		
		String str = "insert into assetref (uniqueID, path, name) VALUES (" 
				+ "'" + inAssetRef.uniqueID + "',"
				+ "'" + inAssetRef.path + "',"
				+ "'" + inAssetRef.name + "')";
		return str;
	}
	
	///////////////////////////////////////////////////////////////
	// Asset
	public static String Asset_CreateTable()
	{
		String str = "create table asset (" 
				+ "id int,"
				+ "uniqueID string,"
				+ "lastUpdate date,"
				+ "metadata string"
				+ ")";
		return str;	
	}

	public static String Asset_Update(Asset inAsset)
	{
		String str = "insert into asset (uniqueID, lastUpdate, metadata) VALUES ("
				+ "'" + inAsset.uniqueID + "',"
				+ "'" + inAsset.lastUpdate + "',"
				+ "'" + inAsset.metadata + "')";
		return str;
	}

	public static String AssetRef_QueryByUniqueId(String inUniqueId) {
		String str = String.format("select * from assetref where uniqueID = '%'", inUniqueId);
		return str;
	}

	public static AssetRef AssetRef_FromResultSet(ResultSet result) {
		AssetRef assetRef = null;
		
		try {
			if (result.first())
			{
				assetRef = new AssetRef();
				assetRef.name = result.getString("name");
				assetRef.path = result.getString("path");
				assetRef.uniqueID = result.getString("uniqueID");
			}
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());						
		}
		
		return assetRef;
	}
}
