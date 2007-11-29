package org.w3c.unicorn.output;

import org.apache.velocity.app.event.ReferenceInsertionEventHandler;

/**
 * Escape all XML Entities in the reference insertion.
 * Specifically, the following conversions are performed:
 * <DL>
 * <DT>&amp;</DT><DD>&amp;amp;</DD>
 * <DT>&lt;</DT><DD>&amp;lt;</DD>
 * <DT>&gt;</DT><DD>&amp;gt;</DD>
 * <DT>&quot;</DT><DD>&amp;quot;</DD>
 * </DL>
 *
 * @author <a href="mailto:wglass@forio.com">Will Glass-Husain</a>
 */
public class EscapeXMLEntities implements ReferenceInsertionEventHandler {
	
	/**
	 * Escape the XML entities for all inserted references.
	 */
	public Object referenceInsert (final String sUnused, final Object oValue) {
		final String sValue = oValue.toString();
		return EscapeXMLEntities.escapeText(sValue);
	}

	/**
	 * Escape the provided text.
	 * @param sValue
	 * @return
	 */
	public static String escapeText (final String sValue) {
		final StringBuffer aStringBuffer = new StringBuffer(sValue.length());
		final int iLength = sValue.length();
		int iPosition = 0;
		int iNextPosition = EscapeXMLEntities.nextPosition(sValue, iPosition);

		while (iNextPosition != -1) {
			aStringBuffer.append(sValue.substring(iPosition, iNextPosition));
			aStringBuffer.append(EscapeXMLEntities.escapeChar(sValue.charAt(iNextPosition)));
			iPosition = iNextPosition + 1;
			if (iPosition < iLength) {
				iNextPosition = EscapeXMLEntities.nextPosition(sValue, iPosition);
			} else {
				iNextPosition = -1;
			}
		}

		if (iPosition < iLength) {
			aStringBuffer.append(sValue.substring(iPosition));
		}

		return aStringBuffer.toString();
	}
	
	private static String escapeChar (final char c) {
		switch (c) {
			case '<' : return "&lt;";
			case '>' : return "&gt;";
			case '&' : return "&amp;";
			case '"' : return "&quot;";
		}
		return null;
	}
	
	private static int nextPosition (final String s, final int iCurrentPosition) {
		final int iLT = s.indexOf('<', iCurrentPosition);
		final int iGT = s.indexOf('>', iCurrentPosition);
		final int iAMP = s.indexOf('&', iCurrentPosition);
		final int iQUOT = s.indexOf('"', iCurrentPosition);
		
		if (
				(iLT != -1) &&
				((iGT == -1) || (iLT < iGT)) &&
				((iAMP == -1) || (iLT < iAMP)) &&
				((iQUOT == -1) || (iLT < iQUOT))) {
			return iLT;
		}
		if (
				(iGT != -1) &&
				((iAMP == -1) || (iGT < iAMP)) &&
				((iQUOT == -1) || (iGT < iQUOT))) {
			return iGT;
		}
		if (
				(iAMP != -1) &&
				((iQUOT == -1) || (iAMP < iQUOT))) {
			return iAMP;
		}
		return iQUOT;
	}
	
}
