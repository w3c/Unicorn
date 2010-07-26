// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
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
 * This class provides static methods to list files into a directory.
 * 
 * @author Jean-Guilhem ROUEL
 */
public class ListFiles {

	private static final Log logger = LogFactory.getLog(ListFiles.class);

	/**
	 * List all files matching a pattern in a directory
	 * 
	 * @param sDirectory
	 *            the directory to list
	 * @param sFilterPattern
	 *            only filenames matching this pattern will be returned
	 * @return an array of files matching the pattern
	 * @throws FileNotFoundException
	 */
	public static File[] listFiles(final String sDirectory,
			final String sFilterPattern) {//throws FileNotFoundException {
		logger.trace("listFiles(String, String)");
		logger.trace("Directory : " + sDirectory + ".");
		logger.trace("Filter pattern : " + sFilterPattern + ".");

		final File aDirectory = new File(sDirectory);
		final Pattern aPattern = Pattern.compile(sFilterPattern);
		final FilenameFilter aFilenameFilter = new FilenameFilter() {
			public boolean accept(File aDirectory, String sName) {
				File aFile = new File(aDirectory.getPath() + sName);
				if (aFile.isDirectory()) {
					return false;
				}
				Matcher matcher = aPattern.matcher(sName);
				return matcher.find();
			}
		};

		final File[] tFile = aDirectory.listFiles(aFilenameFilter);
		return tFile;
	}

	/**
	 * List all files in a directory
	 * 
	 * @param sDirectory
	 *            the directory to list
	 * @return an array of files
	 * @throws FileNotFoundException
	 */
	public static File[] listFiles(final String sDirectory)
			throws FileNotFoundException {
		logger.trace("listFiles(String)");
		logger.debug("Directory : " + sDirectory + ".");

		final File aDirectory = new File(sDirectory);
		final FileFilter aFileFilter = new FileFilter() {
			public boolean accept(File aFile) {
				return !aFile.isDirectory();
			}
		};

		final File[] tFile = aDirectory.listFiles(aFileFilter);
		if (null == tFile) {
			throw new FileNotFoundException("File in " + sDirectory
					+ " not found.");
		}
		return tFile;
	}

	/**
	 * For testing purpose
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
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
