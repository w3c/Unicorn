// $Id: ListFiles.java,v 1.1.1.1 2006-08-31 09:09:28 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ListFiles<br />
 * Created: Jun 26, 2006 2:17:42 PM<br />
 * This class provide static method to list file into a directory.
 * @author Jean-Guilhem ROUEL
 */
public class ListFiles {

	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.util");

	public static File[] listFiles (
			final String sDirectory,
			final String sFilterPattern) throws
			FileNotFoundException {
		ListFiles.logger.trace("listFiles(String, String)");
		if (ListFiles.logger.isDebugEnabled()) {
			ListFiles.logger.debug("Directory : " + sDirectory + ".");
			ListFiles.logger.debug("Filter pattern : " + sFilterPattern + ".");
		}

		final File aDirectory = new File(sDirectory);
		final Pattern aPattern = Pattern.compile(sFilterPattern);		
		final FilenameFilter aFilenameFilter = new FilenameFilter() {
			public boolean accept (File aDirectory, String sName) {
				File aFile = new File(aDirectory.getPath() + sName);
				if (aFile.isDirectory()) {
					return false;
				}
				Matcher matcher = aPattern.matcher(sName);
				return matcher.find();
			}
		};

		final File[] tFile = aDirectory.listFiles(aFilenameFilter);
		if (null == tFile) {
			throw new FileNotFoundException(
					"File in " +
					sDirectory +
					" matching pattern " +
					sFilterPattern +
					" not found.");
		}
		return tFile;
	}

	public static File[] listFiles (final String sDirectory) 
	throws FileNotFoundException {
		ListFiles.logger.trace("listFiles(String)");
		if (ListFiles.logger.isDebugEnabled()) {
			ListFiles.logger.debug("Directory : " + sDirectory + ".");
		}

		final File aDirectory = new File(sDirectory);
		final FileFilter aFileFilter = new FileFilter() {
	        public boolean accept (File aFile) {
	            return !aFile.isDirectory();
	        }
	    };

		final File[] tFile = aDirectory.listFiles(aFileFilter);
		if (null == tFile) {
			throw new FileNotFoundException("File in "+sDirectory+" not found.");
		}
		return tFile;
	}

	public static void main (String[] args) throws FileNotFoundException {
		File[] files = listFiles("/home/jean");
		for (final File file : files) {
			System.out.println(file.getAbsolutePath());
		}
		System.out.println("----------------------------------");
		files = listFiles("/home/jean", ".+\\..+");
		for (final File file : files) {
			System.out.println(file.getAbsolutePath());
		}
	}

}
