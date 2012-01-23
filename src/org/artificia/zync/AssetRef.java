package org.artificia.zync;

import java.sql.Time;

public class AssetRef {
	public String uniqueID;		// Will be numeric, eventually
	public String name;
	public String path;			// FileSystem.rootPath +/+ Asset.path +/+ Asset.name == full uri
	public int size;
	public Time lastChanged;
}
