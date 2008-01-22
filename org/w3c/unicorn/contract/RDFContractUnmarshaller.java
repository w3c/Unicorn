// $Id: RDFContractUnmarshaller.java,v 1.2 2008-01-22 13:52:05 dtea Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

import java.util.Map;

import org.w3c.unicorn.util.LocalizedString;
import org.w3c.unicorn.util.Unmarshaller;

/**
 * Interface to unmarshall RDF file.
 * @author Damien LEROY
 */
public interface RDFContractUnmarshaller extends Unmarshaller {

	public String getID ();

	public LocalizedString getName ();

	public LocalizedString getDescription ();

	public LocalizedString getHelpLocation ();
	
	public LocalizedString getProvider ();
	
	public String getNameOfLangParameter ();

	//ObserverDescription getDescription();

	Map<EnumInputMethod, InputMethod> getMapOfInputMethod();

}
