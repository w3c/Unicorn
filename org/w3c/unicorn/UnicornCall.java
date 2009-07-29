// $Id: UnicornCall.java,v 1.27 2009-07-29 14:44:51 tgambet Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.MimeType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import org.w3c.dom.Document;

import org.w3c.unicorn.contract.CallParameter;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.contract.InputMethod;
import org.w3c.unicorn.contract.Observer;
import org.w3c.unicorn.exceptions.EmptyDocumentException;
import org.w3c.unicorn.exceptions.NoDocumentException;
import org.w3c.unicorn.exceptions.NoMimeTypeException;
import org.w3c.unicorn.index.IndexGenerator;
import org.w3c.unicorn.input.InputFactory;
import org.w3c.unicorn.request.Request;
import org.w3c.unicorn.request.RequestList;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.response.parser.ResponseParserFactory;
import org.w3c.unicorn.tasklist.Task;
import org.w3c.unicorn.tasklist.parameters.Mapping;
import org.w3c.unicorn.tasklist.parameters.Parameter;
import org.w3c.unicorn.tasklist.parameters.Value;
import org.w3c.unicorn.tasklisttree.EnumCondType;
import org.w3c.unicorn.tasklisttree.TLTCond;
import org.w3c.unicorn.tasklisttree.TLTExec;
import org.w3c.unicorn.tasklisttree.TLTIf;
import org.w3c.unicorn.tasklisttree.TLTNode;
import org.w3c.unicorn.util.TemplateHelper;
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
	private static final Log logger = Framework.logger;

	// Request
	/**
	 * The task to call
	 */
	private Task aTask = null;

	/**
	 * Check Method : Upload, URI , Direct...
	 */
	private EnumInputMethod aEnumInputMethod = null;

	private Object oInputParameterValue = null;

	private String sDocumentName = null;

	private String sLang = null;

	private RequestList aRequestList = null;

	private Map<String, String[]> mapOfStringParameter = null;

	/**
	 * Data Structure for the response
	 */
	private Map<String, Response> mapOfResponse;

	/**
	 * Active threads number in doRequests() method
	 */
	private int nbActiveThreads;

	/**
	 * Tells if all the checks passed
	 */
	private boolean bPassed;

	/**
	 * Creates a new UnicornCall.
	 */
	public UnicornCall() {
		UnicornCall.logger.trace("Constructor()");

		this.mapOfStringParameter = new LinkedHashMap<String, String[]>();

		this.mapOfResponse = new LinkedHashMap<String, Response>();
		this.nbActiveThreads = 0;
	}

	/**
	 * Execute the task aTask
	 *
	 * @throws Exception
	 */
	public void doTask() throws Exception {
		UnicornCall.logger.trace("doTask.");
		if (UnicornCall.logger.isDebugEnabled()) {
			UnicornCall.logger.debug("String task id : " + this.aTask.getID()
					+ ".");
			UnicornCall.logger.debug("EnumInputMethod : "
					+ this.aEnumInputMethod + ".");
			UnicornCall.logger.debug("Document name : " + this.sDocumentName
					+ ".");
			UnicornCall.logger.debug("Map of string parameter : "
					+ this.mapOfStringParameter + ".");
		}

		// find mimetype of the document
		MimeType aMimeType = this.getMimeType();

		// Create input method
		final InputFactory aInputFactory = new InputFactory(aMimeType,
				this.aEnumInputMethod, this.oInputParameterValue);

		this.doNode(aInputFactory, this.aTask.getTree());

		aInputFactory.dispose();
	}

	/**
	 * Main function called to do the recursion over the Task tree to launch the
	 * requests
	 *
	 * @param aInputFactory
	 *            InputFactory used for the resquests
	 * @param node
	 *            the current node that we're parsing in the Task tree
	 * @throws Exception
	 *             raised from generateRequestList and doRequest
	 */
	private void doNode(InputFactory aInputFactory, TLTNode node)
			throws Exception {
		// Generate the list of request
		UnicornCall.logger.trace("doNode.");
		if (UnicornCall.logger.isDebugEnabled()) {
			UnicornCall.logger.debug("InputFactory : " + aInputFactory + ".");
			UnicornCall.logger.debug("Current node : " + node + ".");
		}
		if (node != null) {
			this.aRequestList = this.generateRequestList(aInputFactory,
					this.mapOfStringParameter, node);

			if (UnicornCall.logger.isDebugEnabled()) {
				UnicornCall.logger.debug("RequestList : " + this.aRequestList
						+ ".");
			}

			// send requests to observer
			this.doRequests();

			UnicornCall.logger.info("Check the condition of the Ifs");
			// browse the conditions to do the connection
			for (TLTIf ifs : node.getIfList()) {
				if (this.checkCond(ifs))
					this.doNode(aInputFactory, ifs.getIfOk());
				else
					this.doNode(aInputFactory, ifs.getIfNotOk());
			}
		} else {
			// Inform if the node is null
			if (UnicornCall.logger.isDebugEnabled()) {
				UnicornCall.logger.debug("The node is null at this point.");
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
		UnicornCall.logger.trace("checkCond.");
		if (UnicornCall.logger.isDebugEnabled()) {
			UnicornCall.logger.debug("If node : " + ifs + ".");
		}
		boolean conditionOK = false;
		// boolean to manage the OR in the conditions, if the cond is false we
		// change the boolean to true , if not we don't care
		// that will simulate the OR
		for (TLTCond cond : ifs.getCondArray()) {
			if (this.checkCond(cond))
				conditionOK = true;

		}
		return conditionOK;

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
	 * Adds 1 to active threads number
	 */
	public synchronized void incCounter() {
		this.nbActiveThreads++;
	}

	/**
	 * Substracts 1 to active threads number
	 */
	public synchronized void decCounter() {
		this.nbActiveThreads--;
	}

	/**
	 * Change the value the boolean bPassed
	 *
	 * @param b
	 *            new value
	 */
	public void setbPassed(boolean b) {
		this.bPassed = b;
	}

	/**
	 * getter for bPassed
	 *
	 * @return the value of bPassed
	 */
	public boolean getBPassed() {
		return this.bPassed;
	}

	/**
	 * Execute the request depending on the priority
	 *
	 * @param aTPriority
	 *            priority of the request
	 * @throws IOException
	 *             Input/Output error
	 */
	private boolean doRequests() throws IOException {
		UnicornCall.logger.trace("doRequest");

		bPassed = true;

		final Map<String, Request> requests = this.aRequestList.getRequestMap();
		// Creation of the thread list
		ArrayList<Thread> threadsList = new ArrayList<Thread>();

		for (final String obsID : requests.keySet()) {
			// send request to observer
			if (UnicornCall.logger.isDebugEnabled()) {
				UnicornCall.logger.debug("Request : "
						+ requests.get(obsID).toString());
			}

			threadsList.add(new RequestThread(mapOfResponse, requests
					.get(obsID), obsID, this));
		}
		for (int i = 0; i < threadsList.size(); i++)
			threadsList.get(i).start();

		for (int i = 0; i < threadsList.size(); i++) {
			try {
				threadsList.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return bPassed;

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
	private RequestList generateRequestList(final InputFactory aInputFactory,
			final Map<String, String[]> mapOfArrayUseParameter, TLTNode node)
			throws Exception {

		// Log information
		UnicornCall.logger.trace("generateRequestList");
		if (UnicornCall.logger.isDebugEnabled()) {
			UnicornCall.logger.debug("InputFactory : " + aInputFactory + ".");
			UnicornCall.logger.debug("Map of string parameter : "
					+ mapOfArrayUseParameter + ".");
		}

		final MimeType aMimeType = aInputFactory.getMimeType();
		final EnumInputMethod aEnumInputMethod = aInputFactory
				.getDefaultInputModule().getEnumInputMethod();

		final RequestList aRequestList = new RequestList(this.sLang);
		// Iterate over all observation of this task to build a basic
		// request list with only the url of observator and input parameter
		// Il faut creer une list avec tous les exec et toutes les rencardeur de
		// ifs
		// Une liste d'Observer
		for (final Observer aObserver : this.createExecList(node).values()) {
			final String sObserverID = aObserver.getID();
			// add only observer who handle the current mimetype
			if (!aObserver.canHandleMimeType(aMimeType)) {
				if (UnicornCall.logger.isDebugEnabled()) {
					UnicornCall.logger.debug("Observer " + sObserverID
                      + " does not handle mime type " + aMimeType.toString());
				}
        continue;
      }
      // the best available observation method
      final InputMethod aInputMethod = aObserver.getBestInputMethod(aEnumInputMethod);

      // create a new request with input parameter
      final Request aRequest = Request.createRequest(
                                                     // the corresponding best input module
                                                     aInputFactory.getInputModule(aInputMethod.getMethod()),
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
      aRequestList.addRequest(aRequest, aObserver.getID());
			
      // log debug information
      if (UnicornCall.logger.isDebugEnabled()) {
          UnicornCall.logger.debug("Redirect request " + aRequest
                                   + " from " + aEnumInputMethod + " to " + aInputMethod.getMethod()
                                   + " added to request list.");
      }

    
			// Get value of ucn_lang parameter to associate it with parameter
			// lang of the observer (if it has one).
			// ucn_lang is defined in forms of index templates
			// (xx_index.html.vm)
			String[] valOfUcnLang = this.mapOfStringParameter.get(Property.get("UNICORN_PARAMETER_PREFIX") + "lang");

			// Get name of the lang parameter (defined in RDF file)
			String observerParamLangName = aObserver.getParamLangName();

			// If lang parameter exists, we add name and value in parameters of
			// the request.
			if (observerParamLangName != null && valOfUcnLang != null) {
				aRequest.addParameter(observerParamLangName, valOfUcnLang[0]);
			}

			// Add this request to request list
			aRequestList.addRequest(aRequest, aObserver.getID());

			// Log debug information
			if (UnicornCall.logger.isDebugEnabled()) {
				UnicornCall.logger.debug("Request " + aRequest
						+ " added to request list.");
			}
			
      // Add fixed parameter
      for (final CallParameter aCallParameter : aObserver
               .getCallMethod(aInputMethod.getMethod()).getMapOfCallParameter()
               .values()) {
          if (aCallParameter.isRequired()
              && aCallParameter.isFixed()) {
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
			if (UnicornCall.logger.isDebugEnabled()) {
				UnicornCall.logger.debug("Parameter : " + sTaskParameterName
						+ ".");
			}
			// check if this parameter have a given value
			String[] tStringUseParameterValue = mapOfArrayUseParameter
					.get(sTaskParameterName);
			if (null == tStringUseParameterValue) {
				if (UnicornCall.logger.isDebugEnabled()) {
					UnicornCall.logger.debug("No value input for parameter "
							+ sTaskParameterName + ".");
				}
				// check if this parameter have a default value
				final Map<String, Value> mapOfDefaultValue = aTaskParameter
						.getMapOfDefaultValue();
				if (null == mapOfDefaultValue || 0 == mapOfDefaultValue.size()) {
					UnicornCall.logger.warn("Parameter " + sTaskParameterName
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
			if (UnicornCall.logger.isDebugEnabled()) {
				for (final String sParameterValue : tStringUseParameterValue) {
					UnicornCall.logger.debug("Parameter Value : "
							+ sParameterValue + ".");
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
				final Map<String, List<Mapping>> mapOfMapping = aValue
						.getMapOfMapping();

				for (final String sObserverName : mapOfMapping.keySet()) {

					if (aRequestList.getRequest(sObserverName)!=null) {
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
	 * Returns the responses of low priority observations.
	 *
	 * @return responses of low priority observations.
	 */
	public Map<String, Response> getResponses() {
		return this.mapOfResponse;
	}

	/**
	 * Set the task to perform
	 *
	 * @param sTaskID
	 *            ID of the task to perform
	 */
	public void setTask(final String sTaskID) {
		if (null == sTaskID) {
			UnicornCall.logger.error("Call setTask with null argument.");
			return;
		}
		this.aTask = Framework.mapOfTask.get(sTaskID);

		if (null == this.aTask) {
			UnicornCall.logger.error("The task " + sTaskID
					+ " does not exists.");
		}
	}

	/**
	 * define the lang of the check
	 *
	 * @param sLang
	 *            defines the lang to configure
	 */
	public void setLang(final String sLang) {
		UnicornCall.logger.debug("setLang(" + sLang + ")");
		this.sLang = sLang;
	}

	/**
	 * Returns the document name
	 *
	 * @return Returns the documentName.
	 */
	public String getDocumentName() {
		return this.sDocumentName;
	}

	/**
	 * Set the name of the document
	 *
	 * @param sDocumentName
	 *            The documentName to set.
	 */
	public void setDocumentName(final String sDocumentName) {
		this.sDocumentName = sDocumentName;
	}

	/**
	 * Gives the list of the observations
	 *
	 * @return map of the observations of the check
	 */
	public Map<String, Response> getObservationList() {
		return mapOfResponse;
	}

	/**
	 * @return Returns the mapOfStringParameter.
	 */
	public Map<String, String[]> getMapOfStringParameter() {
		return mapOfStringParameter;
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

	/**
	 * Returns the current task
	 *
	 * @return Returns the current task.
	 */
	public Task getTask() {
		return this.aTask;
	}

	/**
	 * Set the aEnumInputMethod
	 *
	 * @param enumInputMethod
	 *            The aEnumInputMethod to set.
	 */
	public void setEnumInputMethod(final EnumInputMethod aEnumInputMethod) {
		this.aEnumInputMethod = aEnumInputMethod;
	}

	/**
	 * Set the uploadedFile
	 *
	 * @param uploadedFile
	 *            The uploadedFile to set.
	 */
	public void setInputParameterValue(final Object oInputParameterValue) {
		this.oInputParameterValue = oInputParameterValue;
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
	public boolean checkCond(TLTCond cond) throws Exception {
		UnicornCall.logger.trace("checkCond : ");
    UnicornCall.logger.trace(cond);
		UnicornCall.logger.trace("condId : " + cond.getId());
		UnicornCall.logger.trace("condType : " + cond.getType());
		UnicornCall.logger.trace("condValue : " + cond.getValue());

		boolean passed = false;

    if (cond.getType().equals(EnumCondType.MIMETYPE)) {
				passed = cond.getValue().equals(getMimeType().toString());
    }
    else if (cond.getType().equals(EnumCondType.XPATH)) {
        UnicornCall.logger.trace("condObserver : " + cond.getObserver().getID());
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
                                                                          xmlStr.getBytes()));

            String xpathStr = cond.getValue();

            XPathFactory xpathFact = new XPathFactoryImpl();

            XPath xpath = xpathFact.newXPath();
            XPathExpression xpe = xpath.compile(xpathStr);
            passed = (Boolean) xpe.evaluate(doc, XPathConstants.BOOLEAN);
        }
    }

		cond.setResult(passed);
		UnicornCall.logger.trace("cond result : " + passed);
		return passed;
	}

	/**
	 *
	 * @return The MimeType of the document
	 * @throws Exception
	 */
	private MimeType getMimeType() throws Exception {

		UnicornCall.logger.trace("getMimeType");
		MimeType aMimeType = null;
		String sMimeType;
		switch (this.aEnumInputMethod) {
		case URI:
			sMimeType = (new URL(this.sDocumentName)).openConnection()
					.getContentType();
			if (null == sMimeType || "".equals(sMimeType)) {
				UnicornCall.logger.error("No specified mimetype for upload.");
				throw new NoMimeTypeException("Mimetype not found");
			}
			if (UnicornCall.logger.isDebugEnabled()) {
				UnicornCall.logger.debug("URI MimeType : " + sMimeType + ".");
			}
			sMimeType = sMimeType.split(";")[0];
			aMimeType = new MimeType(sMimeType);
			break;
		case UPLOAD:
			FileItem f = (FileItem) this.oInputParameterValue;
			if (f.getName() == null || f.getName().equals("")) {
				UnicornCall.logger.error("No document provided.");
				throw new NoDocumentException("No document provided");
			}
			if (f.getSize() == 0) {
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
		case DIRECT:
			sMimeType = this.mapOfStringParameter.get(Property.get("UNICORN_PARAMETER_PREFIX") + "mime")[0];
			if (null == sMimeType || "".equals(sMimeType)) {
				UnicornCall.logger
						.error("No mimetype specified for direct input.");
				throw new NoMimeTypeException("Mimetype not found.");
			}
			aMimeType = new MimeType(sMimeType);
			break;
		}
		if (UnicornCall.logger.isDebugEnabled()) {
			UnicornCall.logger
					.debug("MimeType : " + aMimeType.toString() + ".");
		}

		return aMimeType;

	}

}

/**
 * Thread executing a request
 *
 * @author Damien Leroy
 *
 */
class RequestThread extends Thread {
	/**
	 * Used for complex logging purpose
	 */
	private static final Log logger = Framework.logger;

	/**
	 * Data Structure for the responses
	 */
	private Map<String, Response> mapOfResponse;

	/**
	 * The request to make
	 */
	private Request aRequest;

	/**
	 * ID of the Observer
	 */
	private String obsID;

	/**
	 * The call to perform
	 */
	private UnicornCall unicornCall;

	/**
	 * Initialize the thread by filling the properties
	 *
	 * @param mapOfResponse
	 *            the map of the responses
	 * @param aRequest
	 *            the request to make
	 * @param obsID
	 *            the ID of the observer
	 * @param unicorn
	 *            the unicorn call to make
	 */
	public RequestThread(Map<String, Response> mapOfResponse, Request aRequest,
			String obsID, UnicornCall unicorn) {
		this.mapOfResponse = mapOfResponse;
		this.aRequest = aRequest;
		this.obsID = obsID;
		this.unicornCall = unicorn;

	}

	/**
	 * Allow to launch the thread
	 */
	public void run() {
		this.unicornCall.incCounter();
		Response aResponse = null;
		try {
			// Uncomment/comment next lines to test io_error
			//throw new Exception("Message test de l'exception");
			aResponse = this.aRequest.doRequest();
		} catch (final Exception e) {
			RequestThread.logger.error("Exception : " + e.getMessage(), e);
			e.printStackTrace();
			try {
				StringBuilder builder = new StringBuilder();
				String lang = unicornCall.getMapOfStringParameter().get(Property.get("UNICORN_PARAMETER_PREFIX") + "lang")[0];
				
				// generateFileFromTemplate generates the error xml file if it doesn't exist already
				//String filePath = TemplateHelper.generateFileFromTemplate("io_error", lang, Property.get("PATH_TO_INDEX_OUTPUT"), "xml");
				
				VelocityContext context = new VelocityContext();
				context.put("exception", e.getMessage());
				Template temp = TemplateHelper.getInternationalizedTemplate("io_error", lang, context);
				
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				temp.merge(context, osw);		
				osw.close();
				
				//InputStreamReader isr = new InputStreamReader(new URL("file:" + filePath).openConnection().getInputStream());
				
				InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(os.toByteArray()));
				
				char[] chararray = new char[8192];
				int readLength = 0;
				while((readLength = isr.read(chararray, 0, 8192)) > -1) {
					builder.append(chararray, 0, readLength);
				}
				aResponse = ResponseParserFactory.parse(builder.toString(), this.aRequest.getResponseType());
				aResponse.setXml(builder);
			} catch (MalformedURLException e1) {
				RequestThread.logger.error("Exception : " + e1.getMessage(), e1);
				e1.printStackTrace();
			} catch (IOException e1) {
				RequestThread.logger.error("Exception : " + e1.getMessage(), e1);
				e1.printStackTrace();
			} catch (Exception e1) {
				RequestThread.logger.error("Exception : " + e1.getMessage(), e1);
				e1.printStackTrace();
			}
		}

		synchronized (mapOfResponse) {
			mapOfResponse.put(obsID, aResponse);
		}

		if (!aResponse.isPassed() && this.unicornCall.getBPassed())
			this.unicornCall.setbPassed(false);

		this.unicornCall.decCounter();

	}

}
