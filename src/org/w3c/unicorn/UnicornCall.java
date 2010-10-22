// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.contract.CallParameter;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.contract.InputMethod;
import org.w3c.unicorn.contract.Observer;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.input.*;
import org.w3c.unicorn.request.Request;
import org.w3c.unicorn.request.RequestList;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.tasklist.Group;
import org.w3c.unicorn.tasklist.Task;
import org.w3c.unicorn.tasklist.parameters.Mapping;
import org.w3c.unicorn.tasklist.parameters.Parameter;
import org.w3c.unicorn.tasklist.parameters.Value;
import org.w3c.unicorn.tasklisttree.TLTExec;
import org.w3c.unicorn.tasklisttree.TLTIf;
import org.w3c.unicorn.tasklisttree.TLTNode;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.Property;

/**
 * UnicornCall Created: Jun 29, 2006 2:44:12 PM
 * 
 * @author Jean-Guilhem Rouel
 */
public class UnicornCall {

	/**
	 * Log Object to perform powerful logs
	 */
	private static final Log logger = LogFactory.getLog(UnicornCall.class);
	
	/**
	 * The task to call
	 */
	private Task aTask;

	private String sLang;
	
	private InputParameter inputParameter;

	private Map<String, String[]> mapOfStringParameter;
	
	/**
	 * Data Structure for the response
	 */
	private Map<String, Response> mapOfResponse;
	
	private LinkedHashMap<String, Response> observationMap;
	
	private ArrayList<Message> messages;

	private List<InputModule> inputModules;

	/**
	 * Creates a new UnicornCall.
	 */
	public UnicornCall() {
		logger.trace("Constructor()");
		mapOfStringParameter = new LinkedHashMap<String, String[]>();
		mapOfResponse = new LinkedHashMap<String, Response>();
		messages = new ArrayList<Message>();
		inputModules = new ArrayList<InputModule>();
	}

	
	public void check() throws UnicornException {
		inputParameter.check(messages);
	}
	
	/**
	 * Execute the task aTask
	 * 
	 * @throws Exception
	 */
	public void doTask() throws UnicornException {
		logger.trace("doTask.");
		logger.debug("String task id : " + aTask.getID() + ".");
		logger.debug("EnumInputMethod : " + inputParameter.getInputMethod() + ".");
		logger.debug("Document name : " + inputParameter.getDocumentName() + ".");
		logger.debug("Map of string parameter : " + mapOfStringParameter + ".");
		
		MimeType aMimeType = inputParameter.getMimeType();
		if (aMimeType != null && !aTask.getSupportedMimeTypes().contains(aMimeType.toString()))
			throw new UnicornException(Message.ERROR, "$message_unsupported_mime_type", null, aMimeType.toString());
		
		doNode(inputParameter, aTask.getTree());
	}

	/**
	 * Main function called to do the recursion over the Task tree to launch the
	 * requests
	 * 
	 * @param inputParameter
	 *            InputParameter used for the requests
	 * @param node
	 *            the current node that we're parsing in the Task tree
	 * @throws Exception
	 *             raised from generateRequestList and doRequest
	 */
	private void doNode(InputParameter inputParameter, TLTNode node) throws UnicornException {
		if (node != null) {
			RequestList aRequestList = generateRequestList(inputParameter, mapOfStringParameter, node);
			// send requests to observer
			doRequests(aRequestList);
			// browse the conditions to do the connection
			for (TLTIf iF : node.getIfList()) {
				if (iF.check(this)) {
					doNode(inputParameter, iF.getIfOk());
				} else {
					doNode(inputParameter, iF.getIfNotOk());
				}
			}
		}
	}
	
	/**
	 * Generate the list of the request for the call
	 * 
	 * @param aInputFactory
	 *            Input factory for the parameter
	 * @param mapOfArrayUseParameter
	 *            array of the parameter
	 * @param node
	 *            the current node that we are parsing
	 * @return the list of the request for the call
	 * @throws Exception
	 *             error occured during the process
	 */
	private RequestList generateRequestList(InputParameter inputParameter,
			Map<String, String[]> mapOfArrayUseParameter, TLTNode node) {

		MimeType aMimeType = inputParameter.getMimeType();
		EnumInputMethod aEnumInputMethod = inputParameter.getInputMethod();
		
		RequestList aRequestList = new RequestList(sLang);
		// Iterate over all observation of this task to build a basic
		// request list with only the url of observer and input parameter
		for (Observer aObserver : createExecList(node).values()) {
			String sObserverID = aObserver.getID();
			// add only observer who handle the current mimetype
			if (!aObserver.canHandleMimeType(aMimeType)) {
				logger.debug("Observer " + sObserverID + " does not handle mime type " + aMimeType.toString());
				continue;
			}
			// the best available observation method
			final InputMethod aInputMethod = aObserver
					.getBestInputMethod(aEnumInputMethod);
			
			if (aInputMethod.getMethod() != inputParameter.getInputMethod()) {
				messages.add(new Message(Message.WARNING, "$message_input_changed", "$message_input_changed_long", aObserver.getName(sLang), inputParameter.getInputMethod().toString().toLowerCase()));
			}
			
			InputModule inputModule = createInputModule(aInputMethod, inputParameter.getInputModule());
			inputModules.add(inputModule);
			
			// create a new request with input parameter
			final Request aRequest = Request.createRequest(
			// the corresponding best input module
					//InputFactory.getInputModule(aInputMethod.getMethod()),
					inputModule,
					// URL of the service to call
					aInputMethod.getCallMethod().getURL().toString(),
					// Name of the parameter holding resource information
					// (uri,url,txt,text,file,...)
					aInputMethod.getCallParameter().getName(),
					// Has a file been uploaded?
					aInputMethod.getCallMethod().isPost(),
					// Response format
					aObserver.getResponseType(),
					aObserver.getID());

			// add this request to request list
			//aRequestList.addRequest(aRequest, aObserver.getID());


			// Get value of ucn_lang parameter to associate it with parameter
			// lang of the observer (if it has one).
			// ucn_lang is defined in forms of index templates
			// (xx_index.html.vm)
			String[] valOfUcnLang = this.mapOfStringParameter.get(Property
					.get("UNICORN_PARAMETER_PREFIX")
					+ "lang");

			// Get name of the lang parameter (defined in RDF file)
			String observerParamLangName = aObserver.getParamLangName();

			// If lang parameter exists, we add name and value in parameters of
			// the request.
			if (observerParamLangName != null && valOfUcnLang != null) {
				aRequest.addParameter(observerParamLangName, valOfUcnLang[0]);
			}

			// Add this request to request list
			aRequestList.addRequest(aRequest, aObserver.getID());

			// Add fixed parameter
			for (final CallParameter aCallParameter : aObserver.getCallMethod(aInputMethod.getMethod())
					.getListOfCallParameter()) {
				if (aCallParameter.isRequired() && aCallParameter.isFixed()) {
					aRequest.addParameter(aCallParameter.getName(),
							aCallParameter.getFixed());
				}
			}

		} // foreach this.aTask.getMapOfObservation().values()

		// Iterate over all parameter of this task to add at the
		// request list the parameter input by the framework client
		for (final String sTaskParameterName : this.aTask.getMapOfParameter()
				.keySet()) {
			final Parameter aTaskParameter = this.aTask.getMapOfParameter()
					.get(sTaskParameterName);

			// check if this parameter have a given value
			String[] tStringUseParameterValue = mapOfArrayUseParameter
					.get(sTaskParameterName);
			if (null == tStringUseParameterValue) {

				// check if this parameter have a default value
				final Map<String, Value> mapOfDefaultValue = aTaskParameter
						.getMapOfDefaultValue();
				if (null == mapOfDefaultValue || 0 == mapOfDefaultValue.size()) {
					logger.warn("Parameter " + sTaskParameterName
							+ " has no value intput and no default value.");
					continue;
				}
				tStringUseParameterValue = new String[mapOfDefaultValue
						.keySet().size()];
				int i = 0;
				for (final String s : mapOfDefaultValue.keySet()) {
					tStringUseParameterValue[i++] = s;
				}
			}

			final Map<String, Value> mapOfValue = aTaskParameter
					.getMapOfValue();
			// if there no value the parameter allow all string
			if (null == mapOfValue) {
				final Map<String, Value> mapOfDefaultValue = aTaskParameter
						.getMapOfDefaultValue();
				// Parameter allow all string !
				final Value aValue = mapOfDefaultValue.values().iterator()
						.next();
				final Map<String, List<Mapping>> mapOfMapping = aValue
						.getMapOfMapping();

				for (final String sObserverName : mapOfMapping.keySet()) {

					final Request aRequest = aRequestList
							.getRequest(sObserverName);

					for (final Mapping aMapping : mapOfMapping
							.get(sObserverName)) {
						final String sValue = aMapping.getValue();
						// check
						if (null == sValue || "".equals(sValue)) {
							aRequest.addParameter(aMapping.getParam(),
									tStringUseParameterValue[0]);
							continue;
						}
						aRequest.addParameter(aMapping.getParam(), sValue);
					}

				} // foreach mapOfMapping.keySet()

				continue;
			}

			for (final String sUseParameterValue : tStringUseParameterValue) {
				final Value aValue = mapOfValue.get(sUseParameterValue);
				
				if (aValue == null) {
					logger.warn("unknown mapping for parameter value: " + sUseParameterValue);
					continue;
				}
				
				final Map<String, List<Mapping>> mapOfMapping = aValue
						.getMapOfMapping();

				for (final String sObserverName : mapOfMapping.keySet()) {

					if (aRequestList.getRequest(sObserverName) != null) {
						final Request aRequest = aRequestList
								.getRequest(sObserverName);
						for (final Mapping aMapping : mapOfMapping
								.get(sObserverName)) {

							final String sValue = aMapping.getValue();
							// check
							if (null == sValue || "".equals(sValue)) {
								aRequest.addParameter(aMapping.getParam(),
										sUseParameterValue);
								continue;
							}
							aRequest.addParameter(aMapping.getParam(), aMapping
									.getValue());
						}
					}
				} // foreach mapOfMapping.keySet()
			} // foreach sArrayParameterValue

		} // foreach this.parameters.values()

		return aRequestList;
	}
	
	/**
	 * Creates the map of all the Observer to call in the current node
	 * 
	 * @param node
	 *            the current node of the Task tree we are parsing
	 */
	private Map<String, Observer> createExecList(TLTNode node) {
		Map<String, Observer> mapOfCurrentNodeObserver = new LinkedHashMap<String, Observer>();
		for (TLTExec exec : node.getExecutionList()) {
			mapOfCurrentNodeObserver.put(exec.getValue(), exec.getObserver());
		}
		return mapOfCurrentNodeObserver;
	}
	
	/**
	 * Execute the request depending on the priority
	 * 
	 * @param aTPriority
	 *            priority of the request
	 * @throws IOException
	 *             Input/Output error
	 */
	private void doRequests(RequestList requestList) {
		logger.trace("doRequests");

		final Map<String, Request> requests = requestList.getRequestMap();
		// Creation of the thread list
		ArrayList<RequestThread> threadsList = new ArrayList<RequestThread>();

		for (final String obsID : requests.keySet()) {
			// send request to observer
			threadsList.add(new RequestThread(requests.get(obsID), obsID));
			logger.debug("Request " + requests.get(obsID) + " added to threadsList");
		}
		for (RequestThread thread : threadsList) {
			thread.start();
			logger.debug("Request " + thread.getObsID() + " started");
		}
		for (RequestThread thread : threadsList) {
			try {
				thread.join();
				messages.addAll(thread.getMessages());
				if (thread.getResponse() != null)
					mapOfResponse.put(thread.getObsID(), thread.getResponse());
				logger.debug("Request " + thread.getObsID() + " terminated");
			} catch (InterruptedException e) {
				messages.add(new Message(e));
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * Enter a new parameter in the list
	 * 
	 * @param sName
	 *            Name of the parameter
	 * @param tStringValue
	 *            value of the parameter
	 */
	public void addParameter(final String sName, final String[] tStringValue) {

		final String[] tStringValueLocal = mapOfStringParameter.get(sName);
		if (tStringValueLocal != null) {
			int tValuesLength = tStringValueLocal.length;
			int newSize = tStringValue.length + tValuesLength;
			final String[] tStringValueNew = new String[newSize];
			for (int i = 0; i < tStringValueLocal.length; i++) {
				tStringValueNew[i] = tStringValueLocal[i];
			}
			for (int i = 0; i < tStringValue.length; i++) {
				tStringValueNew[tValuesLength + i] = tStringValue[i];
			}
			this.mapOfStringParameter.put(sName, tStringValueNew);
		} else {
			this.mapOfStringParameter.put(sName, tStringValue);
		}
	}
	
	public void addParameter(final String name, final String value) {
		String[] tab = {value};
		addParameter(name, tab);
	}
	
	public String getDocumentName() {
		return inputParameter.getDocumentName();
	}
	
	public String getObserverName(String observer, String lang) {
		if (Framework.mapOfObserver.get(observer).getName(lang) == null)
			return Framework.mapOfObserver.get(observer).getID();
		return Framework.mapOfObserver.get(observer).getName(lang);
	}
	
	public String getInputMethod() {
		return inputParameter.getInputMethod().toString();
	}
	
	public LinkedHashMap<String, Response> getObservationList() {
		if (observationMap != null)
			return observationMap;
		
		observationMap = new LinkedHashMap<String, Response>();
		for (Group group : aTask.getOutput().getGroupList()) {
			if (!group.isSetType()) {
				for (String observerId : group.getObservationList()) {
					if(mapOfResponse.get(observerId) != null) {
						observationMap.put(observerId, mapOfResponse.get(observerId));
					}
				}
			} else {
				switch (group.getType()) {
					case FIRSTPASSED:
						String passedId = null;
						for (String observerId : group.getObservationList()) {
							if (mapOfResponse.get(observerId) == null) {
								logger.warn("unknown observer id (" + observerId + ") in output group of task: " + this.getTask().getID() + ". It is possible that this observation failed, or that the tasklist is not valid.");
								continue;
							}
							if (mapOfResponse.get(observerId).getStatus() == Response.PASSED) {
								passedId = observerId;
								break;
							}
						}
						if (passedId == null) {
							Response resp = mapOfResponse.get(group.getObservationList().get(0));
							if (resp != null)
								observationMap.put(group.getObservationList().get(0), resp);
						}
						else 
							observationMap.put(passedId, mapOfResponse.get(passedId));
				}
			}
		}
		return observationMap;
	}
	
	public boolean isPassed() {
		boolean passed = true;
		for (String key : getObservationList().keySet()) {
			if (observationMap.get(key).getStatus() != Response.PASSED) {
				passed = false;
			}
		}
		return passed;
	}
	
	public String getStatus() {
		boolean undef = true;
		for (String key : getObservationList().keySet()) {
			switch (observationMap.get(key).getStatus()) {
			case Response.FAILED:
				return "failed";
			case Response.PASSED:
				undef = false;
			}	
		}
		if (undef)
			return "undef";
		return "passed";
	}

	public String getLang() {
		return sLang.split(",")[0];
	}
	
	/**
	 * Returns the current task
	 * 
	 * @return Returns the current task.
	 */
	public Task getTask() {
		return this.aTask;
	}
	
	/**
	 * Returns the responses of low priority observations.
	 * 
	 * @return responses of low priority observations.
	 */
	public Map<String, Response> getResponses() {
		return this.mapOfResponse;
	}
	
	/**
	 * @return Returns the mapOfStringParameter.
	 */
	public Map<String, String[]> getMapOfStringParameter() {
		return mapOfStringParameter;
	}
	
	public InputParameter getInputParameter() {
		return inputParameter;
	}

	/**
	 * Set the task to perform
	 * 
	 * @param sTaskID
	 *            ID of the task to perform
	 */
	public void setTask(final String sTaskID) {
		if (null == sTaskID) {
			logger.error("Call setTask with null argument.");
			return;
		}
		this.aTask = Framework.mapOfTask.get(sTaskID);

		if (null == this.aTask) {
			logger.error("The task " + sTaskID + " does not exists.");
		}
	}

	/**
	 * define the lang of the check
	 * 
	 * @param sLang
	 *            defines the lang to configure
	 */
	public void setLang(final String sLang) {
		this.sLang = sLang;
	}

	/**
	 * Set the map of String Parameter
	 * 
	 * @param mapOfStringParameter
	 *            The mapOfStringParameter to set.
	 */
	public void setMapOfStringParameter(
			final Map<String, String[]> mapOfStringParameter) {
		this.mapOfStringParameter = mapOfStringParameter;
	}

	public void setInputParameter(final InputParameter inputParameter) {
		this.inputParameter = inputParameter;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	
	public InputModule createInputModule(InputMethod aInputMethod, InputModule inputModule) {
		if (aInputMethod.getMethod() == inputModule.getEnumInputMethod()) {
			return inputModule;
		}
		
		try {
			switch (aInputMethod.getMethod()) {
			case DIRECT:
				logger.debug("Creating DirectInputModule");
				return new DirectInputModule(inputModule);
			case UPLOAD:
				logger.debug("Creating FakeUploadInputModule");
				return new FakeUploadInputModule(inputModule);
			case URI:
				logger.debug("Creating URIInputModule");
				return new URIInputModule(inputModule);
			default:
				return null;
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public void dispose() {
		inputParameter.dispose();
		for (InputModule inputModule : inputModules)
			inputModule.dispose();
	}
	
	public String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
		return format.format(new Date());
	}
	
}
