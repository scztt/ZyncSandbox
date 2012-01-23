package org.artificia.zync;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class AssetRefDatabase {
	private Connection dbConnection;
	FileSystemSettings settings;

	public AssetRefDatabase(FileSystemSettings inSettings)
	{
		this.settings = inSettings;
		
		try
		{
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {}

		connectDatabase();
	}
	
	public void connectDatabase()
	{
		try
		{
			// create a database connection
			dbConnection = DriverManager.getConnection("jdbc:sqlite:" + settings.getLocalDatabasePath());
			Statement statement = dbConnection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			statement.executeUpdate(SqlQueryFactory.AssetRef_CreateTable());
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
	
	public void addAssetRef(AssetRef inAssetRef)
	{
		try 
		{
			PreparedStatement statement = dbConnection.prepareStatement(SqlQueryFactory.AssetRef_Insert());
			statement.setString(1, inAssetRef.uniqueID);
			statement.setString(2, inAssetRef.path);
			statement.setString(3, inAssetRef.name);
			statement.setInt(4, inAssetRef.size);
			statement.setTime(5, inAssetRef.lastChanged);
			statement.executeUpdate();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());			
		}
	}
	
	public AssetRef getAssetRefById(String inUniqueId)
	{
		AssetRef assetRef = null;
		
		try 
		{
			Statement statement = dbConnection.createStatement();
			ResultSet result = statement.executeQuery(SqlQueryFactory.AssetRef_QueryByUniqueId(inUniqueId));
			assetRef = SqlQueryFactory.AssetRef_FromResultSet(result);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());			
		}
		
		return assetRef;
	}
	
	public AssetRefIterator iterator()
	{
		try
		{
			Statement statement = dbConnection.createStatement();
			ResultSet result = statement.executeQuery(SqlQueryFactory.AssetRef_All());
			
			return new AssetRefIterator(result);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());			
			return null;
		}
	}
}
