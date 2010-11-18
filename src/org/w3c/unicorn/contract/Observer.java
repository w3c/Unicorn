// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

import java.util.List;
import java.util.Map;

import javax.activation.MimeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.exceptions.UnknownParserException;
import org.w3c.unicorn.util.LocalizedString;
import org.w3c.unicorn.util.Property;

/**
 * Observer<br />
 * Created: May 22, 2006 2:56:07 PM<br />
 * 
 * @author Jean-Guilhem Rouel
 */
public class Observer {

	/**
	 * Use to log any information during use of this class.
	 */
	private static final Log logger = LogFactory.getLog(Observer.class);

	/**
	 * ID of the observer
	 */
	private String sID;

	/**
	 * Name of the observer
	 */
	private LocalizedString aLocalizedStringName = null;

	/**
	 * Description of the observer
	 */
	private LocalizedString aLocalizedStringDescription = null;

	/**
	 * An help for the location of the observer
	 */
	private LocalizedString aLocalizedStringHelpLocation = null;

	/**
	 * A string provider for the observer
	 */
	private LocalizedString aLocalizedStringProvider = null;

	/**
	 * The name of the language for the observer
	 */
	private String sParamLangName = null;
	
	private String sParamOutputName = null;

	/**
	 * The response type of the observer
	 */
	private String responseType = null;

	/**
	 * List of method who can be used to call this observer.
	 */
	private List<CallMethod> listOfCallMethod;

	/**
	 * Map of input method handle by this observer.
	 */
	private Map<EnumInputMethod, InputMethod> mapOfInputMethod;

	private List<MimeType> supportedMimeTypes;

	private String indexURI;
	
	/**
	 * Creates the observer
	 * 
	 */
	public Observer() {
		logger.trace("Constructor");
	}

	public String getID() {
		return this.sID;
	}

	public String getName(final String sLang) {
		return this.aLocalizedStringName.getLocalization(sLang);
	}

	public String getDescription(final String sLang) {
		return this.aLocalizedStringDescription.getLocalization(sLang);
	}

	public String getHelpLocation(final String sLang) {
		return this.aLocalizedStringHelpLocation.getLocalization(sLang);
	}

	public String getProvider(final String sLang) {
		return this.aLocalizedStringProvider.getLocalization(sLang);
	}

	public String getParamLangName() {
		return this.sParamLangName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.unicorn.contract.Observer#getMethods()
	 */
	public List<CallMethod> getListOfCallMethod() {
		return this.listOfCallMethod;
	}

	public void setID(final String sID) {
		this.sID = sID;
	}

	public void setName(final LocalizedString aLocalizedString) {
		this.aLocalizedStringName = aLocalizedString;
	}

	public void setDescription(final LocalizedString aLocalizedString) {
		this.aLocalizedStringDescription = aLocalizedString;
	}

	public void setHelpLocation(final LocalizedString aLocalizedString) {
		this.aLocalizedStringHelpLocation = aLocalizedString;
	}

	public void setProvider(final LocalizedString aLocalizedString) {
		this.aLocalizedStringProvider = aLocalizedString;
	}

	public void setParamLangName(final String sParamLangName) {
		this.sParamLangName = sParamLangName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.unicorn.contract.Observer#setMethods(java.util.ArrayList)
	 */
	public void setListOfCallMethod(final List<CallMethod> listOfCallMethod) {
		this.listOfCallMethod = listOfCallMethod;
	}

	/**
	 * Prints the object
	 */
	@Override
	public String toString() {
		final int sbSize = 1000;
		final String sVariableSeparator = "\n";
		final StringBuffer aStringBuffer = new StringBuffer(sbSize);

		aStringBuffer.append("ID:").append(this.sID);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("Name:").append(this.aLocalizedStringName);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("Description:").append(
				this.aLocalizedStringDescription);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("HelpLocation:").append(
				this.aLocalizedStringHelpLocation);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("Provider:").append(this.aLocalizedStringProvider);
		aStringBuffer.append(sVariableSeparator);
		// aStringBuffer.append("Description:[").append(this.aObserverDescription).append("]");
		// aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("Methods:[").append(this.listOfCallMethod).append(
				"]");
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("InputMethods:[").append(this.mapOfInputMethod)
				.append("]");

		return aStringBuffer.toString();
	}

	public final Map<EnumInputMethod, InputMethod> getMapOfInputMethod() {
		return this.mapOfInputMethod;
	}

	public void setMapOfInputMethod(
			final Map<EnumInputMethod, InputMethod> mapOfInputMethod) {
		this.mapOfInputMethod = mapOfInputMethod;
	}

	public void addInputMethod(final EnumInputMethod aEnumInputMethod,
			final InputMethod aInputMethod) {
		this.mapOfInputMethod.put(aEnumInputMethod, aInputMethod);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.unicorn.contract.Observer#getInputMethod(org.w3c.unicorn.contract.methods.EnumInputMethod)
	 */
	public InputMethod getInputMethod(final EnumInputMethod aEnumInputMethod) {
		return this.mapOfInputMethod.get(aEnumInputMethod);
	}

	public InputMethod getBestInputMethod(final EnumInputMethod preferred) {
		// First try to get what caller would like
		InputMethod im = getInputMethod(preferred);
		if (im != null) {
			return im;
		}

		// If not possible, grab another one
		for (final EnumInputMethod aEIM : EnumInputMethod.values()) {
			im = getInputMethod(aEIM);
			if (im != null) {
				return im;
			}
		}

		// we should not arrive here (that would mean an observer doesn't
		// have any input method
		return null;
	}

	public CallMethod getCallMethod(final EnumInputMethod aEnumInputMethod) {
		return this.getInputMethod(aEnumInputMethod).getCallMethod();
	}

	public void addMimeType(final MimeType aMimeType) {
		this.supportedMimeTypes.add(aMimeType);
	}

	/**
	 * Returns <code>true</code> if the mime-type is supported by this
	 * observer
	 * 
	 * @param aMimeType
	 *            mime-type to check
	 * @return whether or not the mime-type is handled
	 */
	public boolean canHandleMimeType(final MimeType aMimeType) {
		// return this.supportedMimeTypes.contains(aMimeType);
		// equals and thus contains doesn't work :(
		if (aMimeType == null)
			return true;
		for (final MimeType mt : this.supportedMimeTypes) {
			if (mt.match(aMimeType)) {
				return true;
			}
		}
		return false;
	}

	public void setSupportedMimeTypes(List<MimeType> mimeTypes) {
		this.supportedMimeTypes = mimeTypes;
	}

	public List<MimeType> getSupportedMimeTypes() {
		return this.supportedMimeTypes;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) throws UnknownParserException {
		if (!Property.getProps("responseImpl.properties").containsKey(responseType))
			throw new UnknownParserException("Unknown parser: " + responseType + ". Check observer contract or responseImpl.properties.");
			
		this.responseType = responseType;
	}

	public String getParamOutputName() {
		return sParamOutputName;
	}

	public void setParamOutputName(String sParamOutputName) {
		this.sParamOutputName = sParamOutputName;
	}

	public String getIndexURI() {
		return indexURI;
	}

	public void setIndexURI(String indexURI) {
		this.indexURI = indexURI;
	}

}
