// $Id: UploadInputModule.java,v 1.3 2009-09-04 15:49:48 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.io.IOException;
import java.io.InputStream;

import javax.activation.MimeType;

import org.w3c.unicorn.contract.EnumInputMethod;

/**
 * Interface for the UploadInputModule
 * 
 * @author Damien LEROY
 */
public interface UploadInputModule extends InputModule {

	public final EnumInputMethod aEnumInputMethod = EnumInputMethod.UPLOAD;

	public String getFileName();

	public InputStream getInputStream() throws IOException;
	
	public MimeType getMimeType();

}
