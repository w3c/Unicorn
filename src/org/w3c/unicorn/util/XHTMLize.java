// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

import org.apache.velocity.app.event.ReferenceInsertionEventHandler;

public class XHTMLize implements ReferenceInsertionEventHandler {

	/**
	 * Escape the XML entities for all inserted references.
	 */
	public Object referenceInsert(final String sReference, final Object oValue) {
		if (oValue == null) {
			return null;
		}
		return oValue.toString();
		
		/*if (sReference.startsWith("$ucn.evaluate"))
			return oValue.toString();
		if (sReference.contains("getDescription()"))
			return oValue.toString();
		else
			return StringEscapeUtils.escapeHtml(oValue.toString());*/
	}

}
