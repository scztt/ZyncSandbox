package org.artificia.zync;

import java.io.File;
import java.lang.String;
import java.util.regex.Pattern;

public class PathUtils {
	public static String getRelativePath(String targetPath, String basePath, 
			String pathSeparator) {

		boolean isDir = false;
		{
			File f = new File(targetPath);
			isDir = f.isDirectory();
		}
		//  We need the -1 argument to split to make sure we get a trailing 
		//  "" token if the base ends in the path separator and is therefore
		//  a directory. We require directory paths to end in the path
		//  separator -- otherwise they are indistinguishable from files.
		String[] base = basePath.split(Pattern.quote(pathSeparator), -1);
		String[] target = targetPath.split(Pattern.quote(pathSeparator), 0);

		//  First get all the common elements. Store them as a string,
		//  and also count how many of them there are. 
		String common = "";
		int commonIndex = 0;
		for (int i = 0; i < target.length && i < base.length; i++) {
			if (target[i].equals(base[i])) {
				common += target[i] + pathSeparator;
				commonIndex++;
			}
			else break;
		}

		if (commonIndex == 0)
		{
			//  Whoops -- not even a single common path element. This most
			//  likely indicates differing drive letters, like C: and D:. 
			//  These paths cannot be relativized. Return the target path.
			return targetPath;
			//  This should never happen when all absolute paths
			//  begin with / as in *nix. 
		}

		String relative = "";
		if (base.length == commonIndex) {
			//  Comment this out if you prefer that a relative path not start with ./
			relative = "." + pathSeparator;
		}
		else {
			int numDirsUp = base.length - commonIndex - (isDir?0:1); /* only subtract 1 if it  is a file. */
			//  The number of directories we have to backtrack is the length of 
			//  the base path MINUS the number of common path elements, minus
			//  one because the last element in the path isn't a directory.
			for (int i = 1; i <= (numDirsUp); i++) {
				relative += ".." + pathSeparator;
			}
		}
		//if we are comparing directories then we 
		if (targetPath.length() > common.length()) {
			//it's OK, it isn't a directory
			relative += targetPath.substring(common.length());
		}

		return relative;
	}
}
