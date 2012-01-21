package org.artificia.zync;

import java.sql.ResultSet;
import java.util.Iterator;

public class AssetRefIterator implements Iterator<AssetRef> {
	ResultSet resultSet;
	
	AssetRefIterator(ResultSet inResultSet)
	{
		resultSet = inResultSet;
		try {
			resultSet.first();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AssetRef next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
}