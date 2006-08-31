// $Id: FakeUploadInputModule.java,v 1.1.1.1 2006-08-31 09:09:25 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.activation.MimeType;

import org.w3c.unicorn.contract.EnumInputMethod;

/**
 * @author Damien LEROY
 *
 */
public class FakeUploadInputModule implements UploadInputModule {

	private final EnumInputMethod aEnumInputMethod = EnumInputMethod.UPLOAD;

	private String sContent = null;
	private String sFileName = null;
	private MimeType aMimeType = null;

	protected FakeUploadInputModule (final InputModule aInputModule) throws IOException {
		FakeUploadInputModule.logger.trace("Constructor");
		if (FakeUploadInputModule.logger.isDebugEnabled()) {
			FakeUploadInputModule.logger.debug("Input module : " + aInputModule + ".");
		}
		this.aMimeType = aInputModule.getMimeType();
		this.sContent = aInputModule.getStringContent();
	}

	public String getFileName () {
		FakeUploadInputModule.logger.trace("getFileName");
		return this.sFileName;
	}

	public InputStream getInputStream () throws IOException {
		FakeUploadInputModule.logger.trace("getInputStream");
		final PipedOutputStream aPipedOutputStream = new PipedOutputStream();
		aPipedOutputStream.write(this.sContent.getBytes());
		aPipedOutputStream.close();
		final PipedInputStream aPipedInputStream = new PipedInputStream();
		aPipedOutputStream.connect(aPipedInputStream);
		return aPipedInputStream;
	}

	public EnumInputMethod getEnumInputMethod () {
		FakeUploadInputModule.logger.trace("getEnumInputMethod");
		return this.aEnumInputMethod;
	}

	public MimeType getMimeType () {
		FakeUploadInputModule.logger.trace("getMimeType");
		return this.aMimeType;
	}

	public Object getParameterValue () {
		FakeUploadInputModule.logger.trace("getParameterValue");
		return this.sContent;
	}

	public String getStringContent() throws IOException {
		FakeUploadInputModule.logger.trace("getStringContent");
		return this.sContent;
	}

	public void dispose () {
		FakeUploadInputModule.logger.trace("dispose");
	}

	public String toString () {
		final int iStringBufferSize = 500;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("FakeUploadInputModule");
		aStringBuffer.append("\n");
		aStringBuffer.append("Mime type : ").append(this.aMimeType);
		aStringBuffer.append("\n");
		aStringBuffer.append("File name : ").append(this.sFileName);
		return aStringBuffer.toString();
	}

}
