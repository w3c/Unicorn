package org.w3c.unicorn.output;

import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.w3c.unicorn.response.A;
import org.w3c.unicorn.response.Code;
import org.w3c.unicorn.response.Img;

/**
 * Escape all XML Entities in the reference insertion. Specifically, the
 * following conversions are performed:
 * <DL>
 * <DT>&amp;</DT>
 * <DD>&amp;amp;</DD>
 * <DT>&lt;</DT>
 * <DD>&amp;lt;</DD>
 * <DT>&gt;</DT>
 * <DD>&amp;gt;</DD>
 * <DT>&quot;</DT>
 * <DD>&amp;quot;</DD>
 * </DL>
 * 
 * @author <a href="mailto:wglass@forio.com">Will Glass-Husain</a>
 */
public class XHTMLize implements ReferenceInsertionEventHandler {

	/**
	 * Escape the XML entities for all inserted references.
	 */
	public Object referenceInsert(final String sUnused, final Object oValue) {
    if(oValue == null) {
      return null;
    }
		if (oValue instanceof A) {
			A link = (A) oValue;
			return insertA(link);
		}
		if (oValue instanceof Img) {
			Img image = (Img) oValue;
			return insertImg(image);
		}
		if (oValue instanceof Code) {
			Code code = (Code) oValue;
			return insertCode(code);
		}
		return EscapeXMLEntities.escapeText(oValue.toString());
	}

	/**
	 * Insert a link
	 * 
	 * @param aLink
	 *            link to insert
	 * @return return the object containing the link
	 */
	private Object insertA(final A aLink) {
		String sResultat = "<a href=\""
				+ EscapeXMLEntities.escapeText(aLink.getHref()) + "\">";
		for (final Object oElement : aLink.getContent()) {
			if (oElement instanceof Img) {
				sResultat += insertImg((Img) oElement);
			} else {
				sResultat += EscapeXMLEntities.escapeText(oElement.toString());
			}
		}
		sResultat += "</a>";
		return sResultat;
	}

	/**
	 * Insert code tag into the tags
	 * 
	 * @param aCode
	 *            code to insert
	 * @return object with code inserted
	 */
	private Object insertCode(final Code aCode) {
		String sResultat = "<code>";
		for (final Object oElement : aCode.getContent()) {
			if (oElement instanceof A) {
				sResultat += insertA((A) oElement);
			} else if (oElement instanceof Img) {
				sResultat += insertImg((Img) oElement);
			} else {
				sResultat += EscapeXMLEntities.escapeText(oElement.toString());
			}
		}
		sResultat += "</code>";
		return sResultat;
	}

	/**
	 * Insert an img tag
	 * 
	 * @param img
	 *            image path to insert
	 * @return the string containing the image tag
	 */
	private String insertImg(final Img aImage) {
		return "<img src=\"" + EscapeXMLEntities.escapeText(aImage.getSrc())
				+ "\" alt=\"" + EscapeXMLEntities.escapeText(aImage.getAlt())
				+ "\"/>";
	}

}
