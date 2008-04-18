// $Id: Observer.java,v 1.4 2008-04-18 12:35:21 jean-gui Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

import java.util.List;
import java.util.Map;

import javax.activation.MimeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.util.LocalizedString;

/**
 * Observer<br />
 * Created: May 22, 2006 2:56:07 PM<br />
 * @author Jean-Guilhem Rouel
 */
public class Observer {

	/**
	 * Use to log any information during use of this class.
	 */
	private static final Log logger = LogFactory.getLog(Observer.class);

	private String sID;
	
	private LocalizedString aLocalizedStringName = null;
	private LocalizedString aLocalizedStringDescription = null;
	private LocalizedString aLocalizedStringHelpLocation = null;
	private LocalizedString aLocalizedStringProvider = null;
	private String sParamLangName = null;
	private String responseType = null;

	/**
	 * List of method who can be used to call this observer.
	 */
	private List<CallMethod> listOfCallMethod;

	/**
	 * Map of input method handle by this observer.
	 */
	private Map<EnumInputMethod, InputMethod> mapOfInputMethod;

	/**
	 * @param url
	 * @throws Exception
	 */
	public Observer () {
		Observer.logger.trace("Constructor");
	}

	public String getID () {return this.sID;}
	public String getName (final String sLang) {return this.aLocalizedStringName.getLocalization(sLang);}
	public String getDescription (final String sLang) {return this.aLocalizedStringDescription.getLocalization(sLang);}
	public String getHelpLocation (final String sLang) {return this.aLocalizedStringHelpLocation.getLocalization(sLang);}
	public String getProvider (final String sLang) {return this.aLocalizedStringProvider.getLocalization(sLang);}
	public String getParamLangName () {return this.sParamLangName;}

	/* (non-Javadoc)
	 * @see org.w3c.unicorn.contract.Observer#getMethods()
	 */
	public List<CallMethod> getListOfCallMethod() {
		return this.listOfCallMethod;
	}

	public void setID (final String sID) {this.sID = sID;}
	public void setName (final LocalizedString aLocalizedString) {this.aLocalizedStringName = aLocalizedString;}
	public void setDescription (final LocalizedString aLocalizedString) {this.aLocalizedStringDescription = aLocalizedString;}
	public void setHelpLocation (final LocalizedString aLocalizedString) {this.aLocalizedStringHelpLocation = aLocalizedString;}
	public void setProvider (final LocalizedString aLocalizedString) {this.aLocalizedStringProvider = aLocalizedString;}
	public void setParamLangName (final String sParamLangName) {this.sParamLangName = sParamLangName;}
	
	/* (non-Javadoc)
	 * @see org.w3c.unicorn.contract.Observer#setMethods(java.util.ArrayList)
	 */
	public void setListOfCallMethod (final List<CallMethod> listOfCallMethod) {
		this.listOfCallMethod = listOfCallMethod;
	}

	public String toString () {
		final int sbSize = 1000;
		final String sVariableSeparator = "\n";
		final StringBuffer aStringBuffer = new StringBuffer(sbSize);

		aStringBuffer.append("ID:").append(this.sID);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("Name:").append(this.aLocalizedStringName);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("Description:").append(this.aLocalizedStringDescription);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("HelpLocation:").append(this.aLocalizedStringHelpLocation);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("Provider:").append(this.aLocalizedStringProvider);
		aStringBuffer.append(sVariableSeparator);
		//aStringBuffer.append("Description:[").append(this.aObserverDescription).append("]");
		//aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("Methods:[").append(this.listOfCallMethod).append("]");
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("InputMethods:[").append(this.mapOfInputMethod).append("]");

		return aStringBuffer.toString();
	}

	public final Map<EnumInputMethod, InputMethod> getMapOfInputMethod() {
		return this.mapOfInputMethod;
	}

	public void setMapOfInputMethod (final Map<EnumInputMethod, InputMethod> mapOfInputMethod) {
		this.mapOfInputMethod = mapOfInputMethod;
	}

	public void addInputMethod (
			final EnumInputMethod aEnumInputMethod,
			final InputMethod aInputMethod) {
		this.mapOfInputMethod.put(aEnumInputMethod, aInputMethod);
	}

	/* (non-Javadoc)
	 * @see org.w3c.unicorn.contract.Observer#getInputMethod(org.w3c.unicorn.contract.methods.EnumInputMethod)
	 */
	public InputMethod getInputMethod (final EnumInputMethod aEnumInputMethod) {
		return this.mapOfInputMethod.get(aEnumInputMethod);
	}

	public CallMethod getCallMethod (final EnumInputMethod aEnumInputMethod) {
		return this.getInputMethod(aEnumInputMethod).getCallMethod();
	}

	public void addMimeType (
			final EnumInputMethod aEnumInputMethod,
			final MimeType aMimeType) {
		this.mapOfInputMethod.get(aEnumInputMethod).addMimeType(aMimeType);
	}

	public boolean canHandleMimeType (
			final MimeType aMimeType,
			final EnumInputMethod aEnumInputMethod) {
		final InputMethod aInputMethod = this.mapOfInputMethod.get(aEnumInputMethod);
		if (null == aInputMethod) {
			return false;
		}
		return aInputMethod.canHandleMimeType(aMimeType);	
	}

	/**
	 * Return a list of EnumInputMethod with this observer handler mime type
	 * given in parameter.
	 */
	public boolean canHandleMimeType (final MimeType aMimeType) {
		Observer.logger.trace("canHandleMimeType(MimeType)");
		if (Observer.logger.isDebugEnabled()) {
			Observer.logger.debug("Mime type : " + aMimeType + ".");
		}
		for (final EnumInputMethod aEnumInputMethod : EnumInputMethod.values()) {
			final InputMethod aInputMethod = this.mapOfInputMethod.get(aEnumInputMethod);
			if (null == aInputMethod) {
				Observer.logger.warn(
						"Input method of type " +
						aEnumInputMethod +
						" does not exist for observer " +
						this.getID() + ".");
				continue;
			}
			if (aInputMethod.canHandleMimeType(aMimeType)) {
				return true;
			}
		}
		return false;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

}
