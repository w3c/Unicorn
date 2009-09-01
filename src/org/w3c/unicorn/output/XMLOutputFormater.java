// $Id: XMLOutputFormater.java,v 1.5 2009-09-01 16:00:24 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import org.apache.velocity.app.event.EventCartridge;
import org.w3c.unicorn.util.XHTMLize;

/**
 * Class for XML output formater.
 * 
 * @author Jean-Guilhem ROUEL
 */

public class XMLOutputFormater extends SimpleOutputFormater {
	
	public XMLOutputFormater(final String format, final String lang) {
		super(format, lang);
	}
	
	public void setLang(String lang) {
		super.setLang(lang);

		// Replace tag objects (A, Img, ...) with their XHTML representation
		final EventCartridge aEventCartridge = new EventCartridge();
		aEventCartridge.addEventHandler(new XHTMLize());
		aEventCartridge.attachToContext(aVelocityContext);
	}

}