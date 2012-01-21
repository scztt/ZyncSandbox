package org.artificia.zync;

import java.util.Date;

public class AssetDatabaseChange {
	public Date date;

	public String nodeOriginator;

	public String assetUniqueID;
	public ChangeType type;
	
	public Asset asset; 		// should be more like an asset specifier
}
