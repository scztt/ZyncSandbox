package org.artificia.zync;

import java.sql.ResultSet;
import java.util.Iterator;

public class AssetRefIterator implements Iterator<AssetRef> {
	ResultSet resultSet;
	
	AssetRefIterator(ResultSet inResultSet)
	{
		resultSet = inResultSet;
		try 
		{
			resultSet.next();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public boolean hasNext() 
	{
		try 
		{
			return !resultSet.isAfterLast();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			return false;
		}
	}

	@Override
	public AssetRef next() {
		try 
		{
			AssetRef ref = SqlQueryFactory.AssetRef_FromResultSet(resultSet);
			resultSet.next();
			return ref;
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			return null;
		}
	}

	@Override
	public void remove() {
		// Does nothing
	}
}