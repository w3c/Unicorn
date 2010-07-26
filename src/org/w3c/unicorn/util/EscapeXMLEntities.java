// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;

public class EscapeXMLEntities implements ReferenceInsertionEventHandler {

	/**
	 * Escape the XML entities for all inserted references.
	 */
	public Object referenceInsert(final String sUnused, final Object oValue) {
		final String sValue = oValue.toString();
		return StringEscapeUtils.escapeXml(sValue);
	}
	
}
