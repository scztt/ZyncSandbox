package org.artificia.zync;

import java.io.File;
import java.net.URI;
import java.util.LinkedList;
import java.util.Vector;

public interface FileSystem {
	public LinkedList<File> findAllAssets();
	public String getRootPath();
	public URI getRootURI();
	
	public FileSystemSettings getSettings();
	public String constructPath(String path, String name);
}
