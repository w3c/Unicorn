// $Id: FileItemInputModule.java,v 1.1.1.1 2006-08-31 09:09:25 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.io.IOException;
import java.io.InputStream;

import javax.activation.MimeType;

import org.apache.commons.fileupload.FileItem;
import org.w3c.unicorn.contract.EnumInputMethod;

/**
 * @author Damien LEROY
 *
 */
public class FileItemInputModule implements UploadInputModule {

	private final EnumInputMethod aEnumInputMethod = EnumInputMethod.UPLOAD;

	private FileItem aFileItem = null;
	private MimeType aMimeType = null;

	protected FileItemInputModule (
			final MimeType aMimeType,
			final Object oInputParameterValue) {
		FileItemInputModule.logger.trace("Constructor");
		if (FileItemInputModule.logger.isDebugEnabled()) {
			FileItemInputModule.logger.debug("Mime type : " + aMimeType + ".");
			FileItemInputModule.logger.debug("Input parameter value : " + oInputParameterValue + ".");
		}
		if (!(oInputParameterValue instanceof FileItem)) {
			throw new IllegalArgumentException("Object oInputParameterValue : " + oInputParameterValue.toString() + ".");
		}
		this.aMimeType = aMimeType;
		this.aFileItem = (FileItem) oInputParameterValue;
	}
/*
	protected FileItemInputModule (
			final InputModule aInputModule) {
		FileItemInputModule.logger.trace("Constructor.");
		// maybe use a FakeUploadInputModule
		//this.aFileItem = DiskFileItemFactory.createItem();
	}
*/
	public EnumInputMethod getEnumInputMethod () {
		FileItemInputModule.logger.trace("getEnumInputMethod");
		return this.aEnumInputMethod;
	}

	public String getFileName () {
		FileItemInputModule.logger.trace("getFileName");
		return this.aFileItem.getName();
	}

	public InputStream getInputStream () throws IOException {
		FileItemInputModule.logger.trace("getInputStream");
		return this.aFileItem.getInputStream();
	}

	public MimeType getMimeType () {
		FileItemInputModule.logger.trace("getMimeType");
		return this.aMimeType;
	}

	public Object getParameterValue () {
		FileItemInputModule.logger.trace("getParameterValue");
		return this.aFileItem;
	}

	public String getStringContent () {
		FileItemInputModule.logger.trace("getStringContent");
		return this.aFileItem.getString();
	}

	public void dispose () {
		FileItemInputModule.logger.trace("dispose");
		if (null != this.aFileItem) {
			this.aFileItem.delete();
			this.aFileItem = null;
		}
	}

	public String toString () {
		final int iStringBufferSize = 500;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("FileItemInputModule");
		aStringBuffer.append("\n");
		aStringBuffer.append("Mime type : ").append(this.aMimeType);
		aStringBuffer.append("\n");
		aStringBuffer.append("File name : ").append(this.aFileItem.getName());
		return aStringBuffer.toString();
	}

}
