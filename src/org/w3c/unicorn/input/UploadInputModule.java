// $Id: UploadInputModule.java,v 1.2 2009-08-28 12:40:04 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.io.IOException;
import java.io.InputStream;

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

}