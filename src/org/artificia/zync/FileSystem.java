package org.artificia.zync;

import java.io.File;
import java.net.URI;
import java.util.Vector;

public interface FileSystem {
	public Vector<File> findAllAssets();
	public String getRootPath();
	public URI getRootURI();
}
