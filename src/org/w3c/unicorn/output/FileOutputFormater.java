// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

public class FileOutputFormater extends SimpleOutputFormater {

	private String fileName;
	
	public FileOutputFormater(String format, String lang, String mimeType, String fileName) {
		super(format, lang, mimeType);
		this.fileName = fileName;	
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
