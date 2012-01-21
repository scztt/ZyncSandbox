package org.artificia.zync;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class AssetRefDatabase {
	private Connection dbConnection;

	public AssetRefDatabase()
	{
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
			dbConnection = DriverManager.getConnection("jdbc:sqlite:localassets.db");
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
			Statement statement = dbConnection.createStatement();
			statement.executeUpdate(SqlQueryFactory.AssetRef_Update(inAssetRef));
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
	
	public AssetRefIterator getDatabaseIterator()
	{
		
	}
}
