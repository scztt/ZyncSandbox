package org.artificia.zync;

import java.util.Date;
import java.util.Vector;
import java.util.ListIterator;
import java.lang.Class;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AssetDatabase {
	public Vector<Asset> assetDB;
	private Connection dbConnection;
	
	private String trackNameQuery = "" +
			"select * " +
			"from asset " +
			"where trackName = '%s' ";

	private String trackAlbumArtistQuery = "" +
			"select * " +
			"from asset " +
			"where trackName = '%s' AND " +
			"album = '%s' AND " + 
			"artist = '%s' ";

	public AssetDatabase()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {}

		assetDB = new Vector<Asset>();
		connectDatabase();
	}
	
	public void connectDatabase()
	{
		try
		{
			// create a database connection
			dbConnection = DriverManager.getConnection("jdbc:sqlite:assets.db");
			Statement statement = dbConnection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			statement.executeUpdate(SqlQueryFactory.Asset_CreateTable());
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
	}

	public void disconnectDatabse()
	{
		try 
		{
			dbConnection.close();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}

	public void insertAsset(Asset inAsset)
	{
		//this.assetDB.add(inAsset);
		
		// sql
		try
		{
			Statement statement = dbConnection.createStatement();
			statement.executeUpdate(SqlQueryFactory.Asset_Update(inAsset));
		} 
		catch (Exception e)
		{
			System.err.println(e.getMessage());			
		}
	}
	
	public void removeAssetById(String inId)
	{
		ListIterator<Asset> assetIter = this.assetDB.listIterator();
		while (assetIter.hasNext())
		{
			if (assetIter.next().uniqueID == inId)
			{
				assetIter.remove();
				return;
			}
		}
		
		// sql
		try 
		{
			Statement statement = dbConnection.createStatement();
			statement.executeUpdate("delete FROM asset WHERE uniqueID = " + inId);
		} 
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	public void applyChange(AssetDatabaseChange inChange)
	{
		switch (inChange.type)
		{
			case Add:
			{
				insertAsset(inChange.asset);
			}
			case Delete:
			{
				removeAssetById(inChange.assetUniqueID);
			}
			// If successful, serialize change
		}
	}
}
