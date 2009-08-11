// $Id: Unmarshaller.java,v 1.4 2009-08-11 13:43:02 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

import java.io.IOException;
import java.net.URL;

/**
 * Interface for all unmarshaller class in package unicorn.
 * 
 * @author Damien LEROY
 */
public interface Unmarshaller {

	public void addURL(final URL aURL) throws IOException;

	public void unmarshal() throws Exception;

}
