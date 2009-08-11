// $Id: WADLUnmarshaller.java,v 1.6 2009-08-11 13:43:00 jean-gui Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

import java.util.List;
import java.util.Map;

import javax.activation.MimeType;

import org.w3c.unicorn.util.LocalizedString;
import org.w3c.unicorn.util.Unmarshaller;

/**
 * WADLUnmarshallerXPath<br />
 * Created: May 24, 2006 12:34:38 PM<br />
 */
public interface WADLUnmarshaller extends Unmarshaller {

	/**
	 * @return Returns a list of methods.
	 */
	public abstract List<CallMethod> getListOfCallMethod();

	Map<EnumInputMethod, InputMethod> getMapOfInputMethod();

	public String getID();

	public LocalizedString getName();

	public LocalizedString getDescription();

	public LocalizedString getHelpLocation();

	public LocalizedString getProvider();

	public String getNameOfLangParameter();

	public String getResponseType();

	public List<MimeType> getSupportedMimeTypes();

}
