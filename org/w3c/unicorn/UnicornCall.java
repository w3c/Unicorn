// $Id: UnicornCall.java,v 1.2 2006-09-01 14:30:17 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimeType;
import javax.xml.bind.JAXBException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.w3c.unicorn.contract.CallParameter;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.contract.InputMethod;
import org.w3c.unicorn.contract.Observer;
import org.w3c.unicorn.exceptions.EmptyDocumentException;
import org.w3c.unicorn.exceptions.NoDocumentException;
import org.w3c.unicorn.exceptions.NoMimeTypeException;
import org.w3c.unicorn.exceptions.UnsupportedMimeTypeException;
import org.w3c.unicorn.generated.observationresponse.Observationresponse;
import org.w3c.unicorn.generated.tasklist.TPriority;
import org.w3c.unicorn.input.InputFactory;
import org.w3c.unicorn.request.Request;
import org.w3c.unicorn.request.RequestList;
import org.w3c.unicorn.request.RequestListImpl;
import org.w3c.unicorn.tasklist.Observation;
import org.w3c.unicorn.tasklist.Task;
import org.w3c.unicorn.tasklist.parameters.Mapping;
import org.w3c.unicorn.tasklist.parameters.Parameter;
import org.w3c.unicorn.tasklist.parameters.Value;
import org.w3c.unicorn.util.Property;

/**
 * UnicornCall<br />
 * Created: Jun 29, 2006 2:44:12 PM<br />
 * @author Jean-Guilhem Rouel
 */
public class UnicornCall {

	private static final Log logger = Framework.logger;

	// Request
	private Task aTask = null;
	private EnumInputMethod aEnumInputMethod = null;
	private Object oInputParameterValue = null;
	private String sDocumentName = null;
	private String sLang = null;
	private RequestList aRequestList = null;
	private Map<String, String[]> mapOfStringParameter = null;

	// Results
	private Map<String, Observationresponse> mapOfResponseHigh;
	private Map<String, Observationresponse> mapOfResponseMedium;
	private Map<String, Observationresponse> mapOfResponseLow;

	private boolean bPassedHigh;
	private boolean bPassedMedium;
	private boolean bPassedLow;
/*
	private UnicornCall(
			final String sTask,
			final EnumInputMethod aEnumInputMethod,
			final String sDocumentName,
			final Map<String, String[]> mapOfStringParameter) {
		UnicornCall.logger.trace("Constructor(String, EnumInputMethod, String, Map<String, String[]>)");
		if (UnicornCall.logger.isDebugEnabled()) {
			UnicornCall.logger.debug("Task : "+sTask+".");
			UnicornCall.logger.debug("Input method : "+aEnumInputMethod+".");
			UnicornCall.logger.debug("Document name : "+sDocumentName+".");
			UnicornCall.logger.debug("Map of parameter : "+mapOfStringParameter+".");
		}

		this.setTask(sTask);
		this.aEnumInputMethod = aEnumInputMethod;
		this.mapOfStringParameter = mapOfStringParameter;
		this.sDocumentName = sDocumentName;

		this.mapOfResponseHigh = new LinkedHashMap<String, Observationresponse>();		
		this.mapOfResponseMedium = new LinkedHashMap<String, Observationresponse>();
		this.mapOfResponseLow = new LinkedHashMap<String, Observationresponse>();

		this.bPassedHigh = true;
		this.bPassedMedium = true;
		this.bPassedLow = true;
	}
*/
	/**
	 * Creates a new UnicornCall.
	 */
	public UnicornCall () {
		UnicornCall.logger.trace("Constructor()");

		this.mapOfStringParameter = new LinkedHashMap<String, String[]>();

		this.mapOfResponseHigh = new LinkedHashMap<String, Observationresponse>();		
		this.mapOfResponseMedium = new LinkedHashMap<String, Observationresponse>();
		this.mapOfResponseLow = new LinkedHashMap<String, Observationresponse>();

		this.bPassedHigh = true;
		this.bPassedMedium = true;
		this.bPassedLow = true;
	}

	public void doTask () throws Exception {
		UnicornCall.logger.trace("doTask.");
		if (UnicornCall.logger.isDebugEnabled()) {
			UnicornCall.logger.debug("String task id : " + this.aTask.getID() + ".");
			UnicornCall.logger.debug("EnumInputMethod : " + this.aEnumInputMethod + ".");
			UnicornCall.logger.debug("Document name : " + this.sDocumentName + ".");
			UnicornCall.logger.debug("Map of string parameter : " + this.mapOfStringParameter + ".");
		}

		// find mimetype of the document
		UnicornCall.logger.info("Check MimeType.");
		MimeType aMimeType = null;
		String sMimeType;
		switch (this.aEnumInputMethod) {
			case URI :
				sMimeType = (new URL(this.sDocumentName)).openConnection().getContentType();
				if (null == sMimeType || "".equals(sMimeType)) {
					UnicornCall.logger.error("No specified mimetype for upload.");
					throw new NoMimeTypeException("Mimetype not found");
				}
				if (UnicornCall.logger.isDebugEnabled()) {
					UnicornCall.logger.debug("URI MimeType : "+sMimeType+".");
				}
				sMimeType = sMimeType.split(";")[0];
				aMimeType = new MimeType(sMimeType);
				break;
			case UPLOAD :
				FileItem f = (FileItem) this.oInputParameterValue;
				if(f.getName() == null || f.getName().equals("")) {
					UnicornCall.logger.error("No document privided.");
					throw new NoDocumentException("No document provided");
				}
				if(f.getSize() == 0) {
					UnicornCall.logger.error("Empty document provided.");
					throw new EmptyDocumentException("Empty document provided");
				}
				
				sMimeType = ((FileItem) this.oInputParameterValue).getContentType();
				if (null == sMimeType || "".equals(sMimeType)) {
					UnicornCall.logger.error("No specified mimetype for upload.");
					throw new NoMimeTypeException("Mimetype not found");
				}
				aMimeType = new MimeType(sMimeType);
				break;
			case DIRECT :
				sMimeType = this.mapOfStringParameter.get("ucn_mime")[0];			
				if (null == sMimeType || "".equals(sMimeType)) {
					UnicornCall.logger.error("No mimetype specified for direct input.");
					throw new NoMimeTypeException("Mimetype not found.");
				}
				aMimeType = new MimeType(sMimeType);
				break;
		}
		if (UnicornCall.logger.isDebugEnabled()) {
			UnicornCall.logger.debug("MimeType : "+aMimeType.toString()+".");
		}
		// check if the mimetype is handle by the asked task
		// TODO redirect automatically to another input method if this one does
		// not support this mimetype
		if (!this.aTask.allowMimeType(aMimeType)) {
			UnicornCall.logger.error(
					"Task " + this.aTask.getID() +
					" does not support the mimetype " +
					aMimeType.toString() + ".");
			throw new UnsupportedMimeTypeException(aMimeType.toString()+".");
		}

		// create input method
		final InputFactory aInputFactory = new InputFactory(
				aMimeType,
				this.aEnumInputMethod,
				this.oInputParameterValue);

		// generate the list of request
		this.aRequestList = this.generateRequestList(
				aInputFactory,
				this.mapOfStringParameter);

		aInputFactory.dispose();

		if (UnicornCall.logger.isDebugEnabled()) {
			UnicornCall.logger.debug("RequestList : "+this.aRequestList+".");
		}
		
		// send requests to observer
		this.bPassedHigh = this.doRequests(TPriority.HIGH);
		if (!this.bPassedHigh) return;
		this.bPassedMedium = this.doRequests(TPriority.MEDIUM);
		if (!this.bPassedMedium) return;
		this.bPassedLow = this.doRequests(TPriority.LOW);
	}

	/**
	 * @param aTPriority
	 * @throws JAXBException 
	 * @throws IOException 
	 */
	private boolean doRequests (final TPriority aTPriority)
	throws IOException, JAXBException {
		UnicornCall.logger.trace("doRequest");
		if (UnicornCall.logger.isDebugEnabled()) {
			UnicornCall.logger.debug("Priority : "+aTPriority+".");
		}

		boolean bPassed = true;
		
		final Map<String, Observationresponse> mapOfObservationResponse;
		switch (aTPriority) {
			case HIGH:
				mapOfObservationResponse = this.mapOfResponseHigh;
				break;
			case LOW:
				mapOfObservationResponse = this.mapOfResponseLow;
				break;
			case MEDIUM: 
				mapOfObservationResponse = this.mapOfResponseMedium;
				break;
			default :
				mapOfObservationResponse = null;
		}

		final Map<String, Request> requests = this.aRequestList.getRequest(aTPriority);
		for (final String obsID : requests.keySet()) {
			final Request aRequest = requests.get(obsID);
			// send request to observer
			if (UnicornCall.logger.isDebugEnabled()) {
				UnicornCall.logger.debug("Request : "+aRequest.toString());
			}
			
			Observationresponse aObservationResponse;
			try {
				aObservationResponse = aRequest.doRequest();
			}
			catch (final JAXBException e) {
				UnicornCall.logger.error("JAXB Exception : "+e.getMessage(), e);
				aObservationResponse = (Observationresponse) Request.aUnmarshaller.unmarshal(
						new URL("file:" + Property.get("PATH_TO_ERROR_TEMPLATES") + "en_unmarshalling_error.vm"));
			}
			catch (final IOException e) {
				UnicornCall.logger.error("IO Exception : "+e.getMessage(), e);
				aObservationResponse = (Observationresponse) Request.aUnmarshaller.unmarshal(
						new URL("file:" + Property.get("PATH_TO_ERROR_TEMPLATES") + "en_io_error.vm"));
			}
			
			mapOfObservationResponse.put(obsID, aObservationResponse);
			
			bPassed = bPassed && aObservationResponse.isPassed();
		}
		return bPassed;
	}

	private RequestList generateRequestList (
			final InputFactory aInputFactory,
			final Map<String, String[]> mapOfArrayUseParameter) throws Exception {

		// Log information
		UnicornCall.logger.trace("generateRequestList");
		if (UnicornCall.logger.isDebugEnabled()) {
			UnicornCall.logger.debug("InputFactory : " + aInputFactory + ".");
			UnicornCall.logger.debug("Map of string parameter : " + mapOfArrayUseParameter + ".");
		}

		final MimeType aMimeType = aInputFactory.getMimeType();
		final EnumInputMethod aEnumInputMethod = aInputFactory.getDefaultInputModule().getEnumInputMethod();

		final RequestList aRequestList = new RequestListImpl(this.sLang);
		// Iterate over all observation of this task to build a basic
		// request list with only the url of observator and input parameter
		for (final Observation aObservation : this.aTask.getMapOfObservation().values()) {
			final Observer aObserver = aObservation.getObserver();
			final String sObserverID = aObserver.getID();
			// add only observer who handle the current mimetype
			if (!aObserver.canHandleMimeType(aMimeType, aEnumInputMethod)) {
				
				if (UnicornCall.logger.isDebugEnabled()) {
					UnicornCall.logger.debug(
							"Observer " +
							sObserverID +
							" not handle mime type " +
							aMimeType.toString() +
							" with input method " +
							aEnumInputMethod.toString() + ".");
				}
				
				for (final EnumInputMethod aEIM : EnumInputMethod.values()) {
					if (aEnumInputMethod == aEIM) {
						continue;
					}
					if (!aObserver.canHandleMimeType(aMimeType, aEIM)) {

						if (UnicornCall.logger.isDebugEnabled()) {
							UnicornCall.logger.debug(
									"Observer " +
									sObserverID +
									" not handle mime type " +
									aMimeType.toString() +
									" with input method " +
									aEIM.toString() + ".");
						}
						continue;
					}

					if (UnicornCall.logger.isDebugEnabled()) {
						UnicornCall.logger.debug(
								"Observer " +
								sObserverID +
								" handle mime type " +
								aMimeType.toString() +
								" with input method " +
								aEIM.toString() + ".");
					}
					
					final InputMethod aInputMethod =
						aObservation.getObserver().getInputMethod(aEIM);
					// create a new request with input parameter
					final Request aRequest = Request.createRequest(
							aInputFactory.getInputModule(aEIM),
							aInputMethod.getCallMethod().getURL().toString(),
							aInputMethod.getCallParameter().getName());
					// add this request to request list
					aRequestList.addRequest(
							aRequest,
							aObservation.getPriority(aMimeType),
							sObserverID);
					// log debug information
					if (UnicornCall.logger.isDebugEnabled()) {
						UnicornCall.logger.debug(
								"Redirect request " + aRequest +
								" from " + aEnumInputMethod +
								" to " + aEIM + " added to request list.");
					}
					// add required parameter
					for (final CallParameter aCallParameter : aObserver.getCallMethod(aEIM).getMapOfCallParameter().values()) {
						if (aCallParameter.isRequired() && aCallParameter.isFixed()) {
							aRequest.addParameter(aCallParameter.getName(), aCallParameter.getFixed());
						}
					}
					break;
				}
				
				// TODO check the code to redirect to an other
				// TODO input method if it handle mime type
				if (UnicornCall.logger.isDebugEnabled()) {
					UnicornCall.logger.debug(
							"Observation " +
							sObserverID +
							" does not handle mimetype " +
							aMimeType.toString() + " with any input method.");
				}
				continue;
			}
			final InputMethod aInputMethod = aObserver.getInputMethod(aEnumInputMethod);
			
			// create a new request with input parameter
			final Request aRequest = Request.createRequest(
					aInputFactory.getInputModule(aEnumInputMethod),
					aInputMethod.getCallMethod().getURL().toString(),
					aInputMethod.getCallParameter().getName());
			// add this request to request list
			aRequestList.addRequest(
					aRequest,
					aObservation.getPriority(aMimeType),
					sObserverID);
			// log debug information
			if (UnicornCall.logger.isDebugEnabled()) {
				UnicornCall.logger.debug("Request "+aRequest+" added to request list.");
			}
			// add required parameter
			for (final CallParameter aCallParameter : aObserver.getCallMethod(aEnumInputMethod).getMapOfCallParameter().values()) {
				if (aCallParameter.isRequired() && aCallParameter.isFixed()) {
					aRequest.addParameter(aCallParameter.getName(), aCallParameter.getFixed());
				}
			}
		} // foreach this.aTask.getMapOfObservation().values()

		// Iterate over all parameter of this task to add at the
		// request list the parameter input by the framework client
		for (final String sTaskParameterName : this.aTask.getMapOfParameter().keySet()) {
			final Parameter aTaskParameter =
				this.aTask.getMapOfParameter().get(sTaskParameterName);
			if (UnicornCall.logger.isDebugEnabled()) {
				UnicornCall.logger.debug("Parameter : " + sTaskParameterName + ".");
			}
			// check if this parameter have a given value
			String[] tStringUseParameterValue = mapOfArrayUseParameter.get(sTaskParameterName);
			if (null == tStringUseParameterValue) {
				if (UnicornCall.logger.isDebugEnabled()) {
					UnicornCall.logger.debug(
							"No value input for parameter " +
							sTaskParameterName + ".");
				}
				// check if this parameter have a default value
				final Map<String, Value> mapOfDefaultValue = aTaskParameter.getMapOfDefaultValue();
				if (null == mapOfDefaultValue || 0 == mapOfDefaultValue.size()) {
					UnicornCall.logger.warn(
							"Parameter " +
							sTaskParameterName +
							" has no value intput and no default value.");
					// TODO check if this parameter is required
					continue;
				}
				tStringUseParameterValue = new String[mapOfDefaultValue.keySet().size()];
				int i = 0;
				for (final String s : mapOfDefaultValue.keySet()) {
					tStringUseParameterValue[i++] = s;
				}
			}
			if (UnicornCall.logger.isDebugEnabled()) {
				for (final String sParameterValue : tStringUseParameterValue) {
					UnicornCall.logger.debug("Parameter Value : "+sParameterValue+".");
				}
			}
			final Map<String, Value> mapOfValue = aTaskParameter.getMapOfValue();
			// if there no value the parameter allow all string
			if (null == mapOfValue) {
				final Map<String, Value> mapOfDefaultValue = aTaskParameter.getMapOfDefaultValue();
				// Parameter allow all string !
				final Value aValue = mapOfDefaultValue.values().iterator().next();
				final Map<String, List<Mapping>> mapOfMapping = aValue.getMapOfMapping();
				for (final String sObserverName : mapOfMapping.keySet()) {
					final Observation aObservation = this.aTask.getMapOfObservation().get(sObserverName);
					final Request aRequest = aRequestList.getRequest(sObserverName);
					final TPriority aTPriority = aObservation.getPriority(aMimeType);
					if (null == aTPriority) {
						if (UnicornCall.logger.isDebugEnabled()) {
							UnicornCall.logger.debug(
									"Observator " + sObserverName +
									" unhandle mimetype " + aMimeType.getBaseType() +
									".");
						}
						continue;
					}
					for (final Mapping aMapping : mapOfMapping.get(sObserverName)) {
						final String sValue = aMapping.getValue();
						// check
						if (null == sValue || "".equals(sValue)) {
							aRequest.addParameter(
									aMapping.getParam(),
									tStringUseParameterValue[0]);
							continue;
						}
						aRequest.addParameter(
								aMapping.getParam(),
								sValue);
					}
				} // foreach mapOfMapping.keySet()
				continue;
			}
			for (final String sUseParameterValue : tStringUseParameterValue) {
				final Value aValue = mapOfValue.get(sUseParameterValue);
				final Map<String, List<Mapping>> mapOfMapping = aValue.getMapOfMapping();
				for (final String sObserverName : mapOfMapping.keySet()) {
					final Observation aObservation = this.aTask.getMapOfObservation().get(sObserverName);
					final Request aRequest = aRequestList.getRequest(sObserverName);
					final TPriority aTPriority = aObservation.getPriority(aMimeType);
					if (null == aTPriority) {
						if (UnicornCall.logger.isDebugEnabled()) {
							UnicornCall.logger.debug(
									"Observator " +
									sObserverName +
									" unhandle mimetype " +
									aMimeType.getBaseType() +
									".");
						}
						continue;
					}
					for (final Mapping aMapping : mapOfMapping.get(sObserverName)) {
						final String sValue = aMapping.getValue();
						// check
						if (null == sValue || "".equals(sValue)) {
							aRequest.addParameter(
									aMapping.getParam(),
									sUseParameterValue);
							continue;
						}
						aRequest.addParameter(
								aMapping.getParam(),
								aMapping.getValue());
					}
				} // foreach mapOfMapping.keySet()
			} // foreach sArrayParameterValue
		} // foreach this.parameters.values()
		return aRequestList;
	}

	/**
	 * @return Returns the responses of high priority observations.
	 */
	public Map<String, Observationresponse> getHighResponses() {
		return this.mapOfResponseHigh;
	}

	/**
	 * @return Returns the responses of high priority observations.
	 */
	public Map<String, Observationresponse> getMediumResponses() {
		return this.mapOfResponseMedium;
	}

	/**
	 * @return Returns the responses of high priority observations.
	 */
	public Map<String, Observationresponse> getLowResponses() {
		return this.mapOfResponseLow;
	}

	/**
	 * @return Returns the highPassed.
	 */
	public boolean haveHighPassed() {
		return this.bPassedHigh;
	}

	/**
	 * @return Returns the lowPassed.
	 */
	public boolean haveLowPassed() {
		return this.bPassedLow;
	}

	/**
	 * @return Returns the mediumPassed.
	 */
	public boolean haveMediumPassed() {
		return this.bPassedMedium;
	}		

	public void setTask (final String sTaskID) {
		if (null == sTaskID) {
			UnicornCall.logger.error("Call setTask with null argument.");
			return;
		}
		this.aTask = Framework.mapOfTask.get(sTaskID);
		
		if (null == this.aTask) {
			UnicornCall.logger.error("The task " + sTaskID + " does not exists.");
		}
	}

	public void setLang (final String sLang) {
		this.sLang = sLang;
	}

	/**
	 * @return Returns the documentName.
	 */
	public String getDocumentName () {
		return this.sDocumentName;
	}

	/**
	 * @param sDocumentName The documentName to set.
	 */
	public void setDocumentName (final String sDocumentName) {
		this.sDocumentName = sDocumentName;
	}

	public Map<String, Observationresponse> getObservationList () {
		final Map<String, Observationresponse> mapOfObservationResponse;
		mapOfObservationResponse = new LinkedHashMap<String, Observationresponse>();
		mapOfObservationResponse.putAll(this.mapOfResponseHigh);
		mapOfObservationResponse.putAll(this.mapOfResponseMedium);
		mapOfObservationResponse.putAll(this.mapOfResponseLow);
		return mapOfObservationResponse;
	}

	/**
	 * @return Returns the mapOfStringParameter.
	 */
	public Map<String, String[]> getMapOfStringParameter () {
		return mapOfStringParameter;
	}

	public void addParameter (
			final String sName,
			final String[] tStringValue) {
		final String[] tStringValueLocal = mapOfStringParameter.get(sName);
		if (tStringValueLocal != null) {
			int tValuesLength = tStringValueLocal.length;
			int newSize = tStringValue.length + tValuesLength;
			final String[] tStringValueNew = new String[newSize];
			for(int i = 0; i < tStringValueLocal.length; i++) {
				tStringValueNew[i] = tStringValueLocal[i];
			}
			for(int i = 0; i < tStringValue.length; i++) {
				tStringValueNew[tValuesLength + i] = tStringValue[i];
			}
			this.mapOfStringParameter.put(sName, tStringValueNew);
		}
		else {
			this.mapOfStringParameter.put(sName, tStringValue);
		}
	}
	
	/**
	 * @param mapOfStringParameter The mapOfStringParameter to set.
	 */
	public void setMapOfStringParameter (
			final Map<String, String[]> mapOfStringParameter) {
		this.mapOfStringParameter = mapOfStringParameter;
	}

	/**
	 * @return Returns the task.
	 */
	public Task getTask () {
		return this.aTask;
	}

	/**
	 * @param enumInputMethod The aEnumInputMethod to set.
	 */
	public void setEnumInputMethod (final EnumInputMethod aEnumInputMethod) {
		this.aEnumInputMethod = aEnumInputMethod;
	}

	/**
	 * @param uploadedFile The uploadedFile to set.
	 */
	public void setInputParameterValue (final Object oInputParameterValue) {
		this.oInputParameterValue = oInputParameterValue;
	}

}
