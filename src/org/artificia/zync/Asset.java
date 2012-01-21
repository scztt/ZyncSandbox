package org.artificia.zync;

import java.sql.Date;

public class Asset {
	public String uniqueID;		// Will be numeric, eventually
	public AssetMetadata metadata;
	public Date lastUpdate;

	public String toString()
	{
		String str = "";
		str += "Asset {";
		str += "uniqueID:" + uniqueID.toString() + ", ";
		str += "lastUpdate:" + lastUpdate.toString() + ", ";
		str += "metadata:" + metadata.toString();
		str += "}\n";
		return str;
	}
}
