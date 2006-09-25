// $Id: URIInputModule.java,v 1.2 2006-09-25 15:42:55 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;

import javax.activation.MimeType;

import org.w3c.unicorn.Framework;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.output.EscapeXMLEntities;
import org.w3c.unicorn.util.Property;

/**
 * @author Damien LEROY
 *
 */
public class URIInputModule implements InputModule {

	private final EnumInputMethod aEnumInputMethod = EnumInputMethod.URI;

	private MimeType aMimeType = null;
	private String sURI = null;
	private File aFile = null;

	protected URIInputModule (
			final MimeType aMimeType,
			final Object oInputParameterValue) {
		URIInputModule.logger.trace("Constructor.");
		if (URIInputModule.logger.isDebugEnabled()) {
			URIInputModule.logger.debug("oInputParameterValue : " + oInputParameterValue + ".");
		}
		if (!(oInputParameterValue instanceof String)) {
			throw new IllegalArgumentException("Object oInputParameterValue : " + oInputParameterValue.toString() + ".");
		}
		this.aMimeType = aMimeType;
		this.sURI = (String) oInputParameterValue;
		this.sURI = EscapeXMLEntities.escapeText(this.sURI);
	}

	protected URIInputModule (final InputModule aInputModule) throws IOException {
		URIInputModule.logger.trace("Constructor.");
		if (URIInputModule.logger.isDebugEnabled()) {
			URIInputModule.logger.debug("InputModule : " + aInputModule + ".");
		}
		this.aMimeType = aInputModule.getMimeType();
		final Date aDate = new Date();
		final String sFileName;
		sFileName = "tmp_" + aDate.getTime() + "_." + Framework.aPropertiesExtension.getProperty(this.aMimeType.toString());
		this.aFile = new File(
				Property.get("PATH_TO_TEMPORARY_FILES") +
				sFileName);
		this.aFile.createNewFile();
		final PrintWriter aPrintWriter = new PrintWriter(aFile);
		aPrintWriter.print(aInputModule.getStringContent());
		aPrintWriter.close();
		this.sURI = Property.get("URL_TO_TEMPORARY_FILES") + sFileName;
	}

	public EnumInputMethod getEnumInputMethod () {
		URIInputModule.logger.trace("getEnumInputMethod");
		return this.aEnumInputMethod;
	}

	public MimeType getMimeType () {
		URIInputModule.logger.trace("getMimeType");
		return this.aMimeType;
	}

	public Object getParameterValue () {
		URIInputModule.logger.trace("getParameterValue");
		return this.sURI;
	}

	public String getStringContent () throws IOException {
		URIInputModule.logger.trace("getString.");
		final URL aURL = new URL(this.sURI);
		final String sResult = (String) aURL.openConnection().getContent();
		if (URIInputModule.logger.isDebugEnabled()) {
			URIInputModule.logger.debug("sResult : " + sResult + ".");
		}
		return sResult;
	}

	public String getURI () {
		URIInputModule.logger.trace("getURI");
		return this.sURI;
	}

	public void dispose () {
		URIInputModule.logger.trace("dispose");
		if (null != this.aFile && this.aFile.delete()) {
			URIInputModule.logger.info("File deleted.");
			this.aFile = null;
		}
	}

	public String toString () {
		final int iStringBufferSize = 500;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("URIInputModule");
		aStringBuffer.append("\n");
		aStringBuffer.append("Mime type : ").append(this.aMimeType);
		aStringBuffer.append("\n");
		aStringBuffer.append("URI : ").append(this.sURI);
		return aStringBuffer.toString();
	}

}
