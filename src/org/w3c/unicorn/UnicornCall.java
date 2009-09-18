// $Id: UnicornCall.java,v 1.17 2009-09-18 15:01:43 tgambet Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimeType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.unicorn.contract.CallParameter;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.contract.InputMethod;
import org.w3c.unicorn.contract.Observer;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.input.InputParameter;
import org.w3c.unicorn.request.Request;
import org.w3c.unicorn.request.RequestList;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.tasklist.Group;
import org.w3c.unicorn.tasklist.Task;
import org.w3c.unicorn.tasklist.parameters.Mapping;
import org.w3c.unicorn.tasklist.parameters.Parameter;
import org.w3c.unicorn.tasklist.parameters.Value;
import org.w3c.unicorn.tasklisttree.EnumCondType;
import org.w3c.unicorn.tasklisttree.TLTCond;
import org.w3c.unicorn.tasklisttree.TLTExec;
import org.w3c.unicorn.tasklisttree.TLTIf;
import org.w3c.unicorn.tasklisttree.TLTNode;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.Property;

import com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl;

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

	/**
	 * Creates a new UnicornCall.
	 */
	public UnicornCall() {
		logger.trace("Constructor()");
		mapOfStringParameter = new LinkedHashMap<String, String[]>();
		mapOfResponse = new LinkedHashMap<String, Response>();
	}

	/**
	 * Execute the task aTask
	 * 
	 * @throws Exception
	 */
	public void doTask() throws UnicornException, Exception {
		logger.trace("doTask.");
		logger.debug("String task id : " + aTask.getID() + ".");
		logger.debug("EnumInputMethod : " + inputParameter.getInputMethod() + ".");
		logger.debug("Document name : " + inputParameter.getDocumentName() + ".");
		logger.debug("Map of string parameter : " + mapOfStringParameter + ".");
		
		inputParameter.check();
		
		MimeType aMimeType = inputParameter.getMimeType();
		if (!aTask.getSupportedMimeTypes().contains(aMimeType.toString()))
			throw new UnicornException(Message.Level.ERROR, "$message_unsupported_mime_type", null);
		
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
	private void doNode(InputParameter inputParameter, TLTNode node) throws UnicornException, Exception {
		if (node != null) {
			RequestList aRequestList = generateRequestList(inputParameter, mapOfStringParameter, node);
			// send requests to observer
			doRequests(aRequestList);
			// browse the conditions to do the connection
			for (TLTIf iF : node.getIfList()) {
				if (checkCond(iF)) {
					doNode(inputParameter, iF.getIfOk());
				} else {
					doNode(inputParameter, iF.getIfNotOk());
				}
			}
		}
	}

	/**
	 * Check the conditions of the if branch it makes a OR between all
	 * conditions
	 * 
	 * @param ifs
	 *            the if branch to check
	 * @return whether or not the conditions are true
	 * @throws Exception
	 */
	private boolean checkCond(TLTIf ifs) throws Exception {
		logger.trace("checkCond.");
		logger.debug("If node : " + ifs + ".");
		
		boolean conditionOK = false;
		for (TLTCond cond : ifs.getCondArray()) {
			if (checkCond(cond)) {
				conditionOK = true;
			}
		}
		return conditionOK;
	}
	
	/**
	 * Giving a TLTCond, checks in the map of response if the condition passes
	 * or fails and consequently returns a boolean.
	 * 
	 * @param cond
	 *            The condition to check
	 * @return true if there is a matching response and if the condition passes
	 *         else false
	 */
	private boolean checkCond(TLTCond cond) throws Exception {
		logger.trace("checkCond : ");
		logger.trace(cond);
		logger.trace("condId : " + cond.getId());
		logger.trace("condType : " + cond.getType());
		logger.trace("condValue : " + cond.getValue());

		boolean passed = false;

		if (cond.getType().equals(EnumCondType.MIMETYPE)) {
			passed = cond.getValue().equals(inputParameter.getMimeType().toString());
		} else if (cond.getType().equals(EnumCondType.XPATH)) {
			logger.trace("condObserver : " + cond.getObserver().getID());
			Response res = mapOfResponse.get(cond.getObserver().getID());
			// Testing if there is a matching response in the map
			// and if it is passed
			if (res != null) {
				String xmlStr = res.getXml().toString();

				DocumentBuilderFactory xmlFact = DocumentBuilderFactory
						.newInstance();

				// namespace awareness is escaped since we don't use it
				// for the moment
				xmlFact.setNamespaceAware(false);

				DocumentBuilder builder = xmlFact.newDocumentBuilder();

				Document doc = builder.parse(new java.io.ByteArrayInputStream(
						xmlStr.getBytes("UTF-8")));

				String xpathStr = cond.getValue();

				XPathFactory xpathFact = new XPathFactoryImpl();

				XPath xpath = xpathFact.newXPath();
				XPathExpression xpe = xpath.compile(xpathStr);
				passed = (Boolean) xpe.evaluate(doc, XPathConstants.BOOLEAN);
			}
		} else if (cond.getType().equals(EnumCondType.PARAMETER)) {
			passed = false;
			if (!mapOfStringParameter.containsKey(cond.getParameter())) {
				cond.setResult(passed);
				return passed;
			}
			
			String[] parameterValues = mapOfStringParameter.get(cond.getParameter());
			for (int i=0; i<parameterValues.length; i++)
				if (parameterValues[i].equals(cond.getValue()))
					passed = true;
			
		}

		cond.setResult(passed);
		logger.trace("cond result : " + passed);
		return passed;
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
				
				//TODO Add messaged here
				logger.debug("Observer " + sObserverID + " does not handle mime type "
						+ aMimeType.toString());
				continue;
			}
			// the best available observation method
			final InputMethod aInputMethod = aObserver
					.getBestInputMethod(aEnumInputMethod);

			// create a new request with input parameter
			final Request aRequest = Request.createRequest(
			// the corresponding best input module
					//aInputFactory.getInputModule(aInputMethod.getMethod()),
					inputParameter.getInputModule(),
					// URL of the service to call
					aInputMethod.getCallMethod().getURL().toString(),
					// Name of the parameter holding resource information
					// (uri,url,txt,text,file,...)
					aInputMethod.getCallParameter().getName(),
					// Has a file been uploaded?
					aInputMethod.getCallMethod().isPost(),
					// Response format
					aObserver.getResponseType());

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
			for (final CallParameter aCallParameter : aObserver.getCallMethod(
					aInputMethod.getMethod()).getMapOfCallParameter().values()) {
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
	private void doRequests(RequestList requestList) throws IOException {
		logger.trace("doRequests");

		final Map<String, Request> requests = requestList.getRequestMap();
		// Creation of the thread list
		ArrayList<Thread> threadsList = new ArrayList<Thread>();

		for (final String obsID : requests.keySet()) {
			// send request to observer
			threadsList.add(new RequestThread(mapOfResponse, requests
					.get(obsID), obsID, this));
			logger.debug("Request " + requests.get(obsID) + " added to threadsList");
		}
		for (int i = 0; i < threadsList.size(); i++) {
			threadsList.get(i).start();
			logger.debug("Request " + ((RequestThread)threadsList.get(i)).getObsID() + " started");
		}

		for (int i = 0; i < threadsList.size(); i++) {
			try {
				threadsList.get(i).join();
				logger.debug("Request " + ((RequestThread)threadsList.get(i)).getObsID() + " terminated");
			} catch (InterruptedException e) {
				e.printStackTrace();
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
	
	public void addParameter(final String sName, final String tStringValue) {
		String[] tab = {tStringValue};
		addParameter(sName, tab);
	}
	
	public String getObserverName(String observer, String lang) {
		return Framework.mapOfObserver.get(observer).getName(lang);
	}
	
	public String getInputMethod() {
		return inputParameter.getInputMethod().toString();
	}
	
	public LinkedHashMap<String, Response> getObservationList() {
		if (observationMap == null) {
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
									logger.error("unknown observer id (" + observerId + ") in output group of task: " + this.getTask().getID());
									continue;
								}
								if (mapOfResponse.get(observerId).isPassed()) {
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
		}
		return observationMap;
	}
	
	public boolean isPassed() {
		boolean passed = true;
		for (String key : getObservationList().keySet()) {
			if (!observationMap.get(key).isPassed()) {
				passed = false;
			}
		}
		return passed;
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
		logger.debug("setLang(" + sLang + ")");
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
}