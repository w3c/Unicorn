package org.w3c.unicorn.output;

public class AttachmentOutputFormater extends SimpleOutputFormater {

	private String fileName;
	
	public AttachmentOutputFormater(String format, String lang, String mimeType, String fileName) {
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
