// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.io.IOException;
import java.io.InputStream;

import javax.activation.MimeType;

import org.apache.commons.fileupload.FileItem;
import org.w3c.unicorn.contract.EnumInputMethod;

/**
 * Class to deal with the file input method
 * 
 * @author Damien LEROY
 * 
 */
public class FileItemInputModule implements UploadInputModule {

	/**
	 * Sets the input method to UPLOAD
	 */
	private final EnumInputMethod aEnumInputMethod = EnumInputMethod.UPLOAD;

	/**
	 * The file item
	 */
	private FileItem aFileItem = null;

	/**
	 * The mime-type of the file
	 */
	private MimeType aMimeType = null;

	/**
	 * Build the Input module
	 * 
	 * @param aMimeType
	 *            mime-type of the file for the input
	 * @param oInputParameterValue
	 *            parameter of the input
	 */
	protected FileItemInputModule(final MimeType aMimeType,
			final Object oInputParameterValue) {
		logger.trace("Constructor");
		logger.debug("Mime type : " + aMimeType + ".");
		logger.debug("Input parameter value : " + oInputParameterValue + ".");
		if (!(oInputParameterValue instanceof FileItem)) {
			throw new IllegalArgumentException("Object oInputParameterValue : "
					+ oInputParameterValue.toString() + ".");
		}
		this.aMimeType = aMimeType;
		this.aFileItem = (FileItem) oInputParameterValue;
	}

	public EnumInputMethod getEnumInputMethod() {
		logger.trace("getEnumInputMethod");
		return this.aEnumInputMethod;
	}

	public String getFileName() {
		logger.trace("getFileName");
		return this.aFileItem.getName();
	}

	public InputStream getInputStream() throws IOException {
		logger.trace("getInputStream");
		return this.aFileItem.getInputStream();
	}

	public MimeType getMimeType() {
		logger.trace("getMimeType");
		return this.aMimeType;
	}

	public Object getParameterValue() {
		logger.trace("getParameterValue");
		return this.aFileItem;
	}

	public String getStringContent() {
		logger.trace("getStringContent");
		return this.aFileItem.getString();
	}

	/**
	 * Dispose the object
	 */
	public void dispose() {
		if (null != this.aFileItem) {
			logger.trace("dispose");
			this.aFileItem.delete();
			this.aFileItem = null;
		}
	}

	/**
	 * Prints the object
	 */
	@Override
	public String toString() {
		final int iStringBufferSize = 500;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("FileItemInputModule{mimetype: ").append(
				this.aMimeType);
		aStringBuffer.append(", filename: ").append(this.aFileItem.getName())
				.append("}");

		return aStringBuffer.toString();
	}

}
