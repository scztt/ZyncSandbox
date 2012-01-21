package org.artificia.zync;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class AssetMetadata extends Hashtable<String, String> 
{
	private static String[] mdFields = {
		"artist", "trackName", "album", "duration", "fileType", "bitRate", "sampleRate"};

	public static AssetMetadata newFromSqlResultRow(ResultSet inResult) {
		try
		{
			ResultSetMetaData resultMD = inResult.getMetaData();
			AssetMetadata md = new AssetMetadata();
						
			for(int col = 0; col < resultMD.getColumnCount(); col++)
			{
				switch (resultMD.getColumnTypeName(col))
				{
					case "string":
					{
						md.put(resultMD.getColumnName(col), inResult.getString(col));
					}
				}			
			}
			
			return md;
		}
		catch(Exception e)
		{
			return null;
		}
	}

}
