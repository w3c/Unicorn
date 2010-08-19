// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

/**
 * Class for XML output formater.
 * 
 * @author Jean-Guilhem ROUEL
 */

public class XMLOutputFormater extends SimpleOutputFormater {
	
	public XMLOutputFormater(final String format, final String lang, String mimeType) {
		super(format, lang, mimeType);
		
		// Replace tag objects (A, Img, ...) with their XHTML representation
		//final EventCartridge aEventCartridge = new EventCartridge();
		//aEventCartridge.addEventHandler(new XHTMLize());
		//aEventCartridge.attachToContext(aVelocityContext);
	}

}