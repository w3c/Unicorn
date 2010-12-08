// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.activation.MimeType;

import org.w3c.unicorn.contract.EnumInputMethod;

/**
 * Class used for the fake upload input method check It means make the observer
 * believe it was a direct input whereas it wasn't
 * 
 * @author Damien LEROY
 */
public class FakeUploadInputModule implements UploadInputModule {

	/**
	 * Set the Input method to UPLOAD
	 */
	private final EnumInputMethod aEnumInputMethod = EnumInputMethod.UPLOAD;

	/**
	 * Content of the input
	 */
	private String sContent = null;

	/**
	 * Name of the file to input
	 */
	private String sFileName = null;

	/**
	 * Mime type of the input
	 */
	private MimeType aMimeType = null;

	/**
	 * Build the input Module by setting the properties
	 * 
	 * @param aInputModule
	 * @throws IOException
	 */
	public FakeUploadInputModule(final InputModule aInputModule)
			throws IOException {
		logger.trace("Constructor");
		logger.debug("Input module : " + aInputModule + ".");
		this.aMimeType = aInputModule.getMimeType();
		this.sContent = aInputModule.getStringContent();
	}

	/**
	 * Get the filename of the input
	 */
	public String getFileName() {
		logger.trace("getFileName");
		return this.sFileName;
	}

	/**
	 * Get the stream of the input using the content
	 */
	public InputStream getInputStream() throws IOException {
		logger.trace("getInputStream");
		final PipedOutputStream aPipedOutputStream = new PipedOutputStream();
		aPipedOutputStream.write(this.sContent.getBytes());
		aPipedOutputStream.close();
		final PipedInputStream aPipedInputStream = new PipedInputStream();
		aPipedOutputStream.connect(aPipedInputStream);
		return aPipedInputStream;
	}

	public EnumInputMethod getEnumInputMethod() {
		logger.trace("getEnumInputMethod");
		return this.aEnumInputMethod;
	}

	public MimeType getMimeType() {
		logger.trace("getMimeType");
		return this.aMimeType;
	}

	public Object getParameterValue() {
		logger.trace("getParameterValue");
		return this.sContent;
	}

	public String getStringContent() throws IOException {
		logger.trace("getStringContent");
		return this.sContent;
	}

	/**
	 * Dispose the object
	 */
	public void dispose() {
		logger.trace("dispose");
	}

	/**
	 * prints the object
	 */
	@Override
	public String toString() {
		final int iStringBufferSize = 500;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("FakeUploadInputModule{mimetype: ").append(
				this.aMimeType);
		aStringBuffer.append(", filename: ").append(this.sFileName).append("}");

		return aStringBuffer.toString();
	}

}
