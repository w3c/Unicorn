// $Id: RDFContractUnmarshaller.java,v 1.3 2008-06-17 13:41:12 fbatard Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

import java.util.Map;

import org.w3c.unicorn.util.LocalizedString;
import org.w3c.unicorn.util.Unmarshaller;

/**
 * Interface to unmarshall RDF file.
 * 
 * @author Damien LEROY
 */
public interface RDFContractUnmarshaller extends Unmarshaller {

	public String getID();

	public LocalizedString getName();

	public LocalizedString getDescription();

	public LocalizedString getHelpLocation();

	public LocalizedString getProvider();

	public String getNameOfLangParameter();

	Map<EnumInputMethod, InputMethod> getMapOfInputMethod();

}
