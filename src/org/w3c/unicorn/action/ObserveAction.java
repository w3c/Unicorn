// $Id: ObserveAction.java,v 1.10 2009-09-03 12:28:49 tgambet Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.output.OutputFactory;
import org.w3c.unicorn.output.OutputFormater;
import org.w3c.unicorn.output.OutputModule;
import org.w3c.unicorn.util.Language;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.Property;

/**
 * FirstServlet<br />
 * Created: Jun 26, 2006 2:04:11 PM<br />
 * 
 * @author Jean-Guilhem ROUEL
 */
public class ObserveAction extends HttpServlet {

	private static final Log logger = LogFactory.getLog(ObserveAction.class);

	private static final long serialVersionUID = -1375355420965607571L;

	private static final DiskFileItemFactory factory = new DiskFileItemFactory();

	/**
	 * Creates a new file upload handler.
	 */
	private static final ServletFileUpload upload = new ServletFileUpload(
			ObserveAction.factory);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init(final ServletConfig aServletConfig)
			throws ServletException {
		ObserveAction.logger.trace("init");

		ObserveAction.factory.setRepository(new File(Property
				.get("UPLOADED_FILES_REPOSITORY")));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if (!Framework.isUcnInitialized) {
			resp.sendError(500, "Unicorn is not initialized properly. Check logs.");
			return;
		}
		
		Map<String, Object> mapOfStringObject = new LinkedHashMap<String, Object>();
		
		ObserveAction.logger.trace("doGet");

		// Language negotiation
		String langParameter = req.getParameter(Property.get("UNICORN_PARAMETER_PREFIX") + "lang");	
		if (langParameter == null || !Framework.getLanguageProperties().containsKey(langParameter))
			langParameter = Language.negociate(req.getLocales());
		
		logger.debug("Lang Parameter: " + langParameter);
		
		// Add query string
		
		
		String query = req.getQueryString();
		String queryString = "";
		if (query != null)
			queryString = query.replaceAll("&?ucn_lang=[^&]*", "");
		if (!queryString.equals(""))
			queryString += "&";
		mapOfStringObject.put("queryString", queryString);
		
		
		
		
		// Variables related to the output
		final Map<String, String[]> mapOfSpecificParameter = new Hashtable<String, String[]>();
		final Map<String, String> mapOfOutputParameter = new Hashtable<String, String>();
		mapOfOutputParameter.put("output", "simple");
		mapOfOutputParameter.put("format", "xhtml10");
		mapOfOutputParameter.put("charset", "UTF-8");
		mapOfOutputParameter.put("mimetype", "text/html");
		mapOfOutputParameter.put("lang", langParameter);
		
		final UnicornCall aUnicornCall = new UnicornCall();
		final String aLocale = convertEnumerationToString(req.getLocales());
		if (null == aLocale) {
			aUnicornCall.setLang(langParameter + "," + Property.get("DEFAULT_LANGUAGE"));
		} else {
			aUnicornCall.setLang(langParameter + "," + aLocale);
		}

		
		for (final Enumeration<?> aEnumParamName = req
				.getParameterNames(); aEnumParamName.hasMoreElements();) {
			final String sParamName = (String) aEnumParamName.nextElement();
			final String[] tStringParamValue = req
					.getParameterValues(sParamName);

			this.addParameter(sParamName, tStringParamValue, aUnicornCall,
					mapOfSpecificParameter, mapOfOutputParameter);
		}
		
		if (aUnicornCall.getTask() == null) {
			aUnicornCall.setTask(Framework.mapOfTask.getDefaultTaskId());
			Message mess = new Message(Message.Level.WARNING, "$message_no_task " + Framework.mapOfTask.get(Framework.mapOfTask.getDefaultTaskId()).getLongName(langParameter), null);
			ArrayList<Message> messages = new ArrayList<Message>();
			messages.add(mess);
			mapOfStringObject.put("messages", messages);
		}

		/*if (aUnicornCall.getTask() == null) {
			ObserveAction.logger.error("No task selected.");
			this.createError(resp, new NoTaskException(),
					mapOfSpecificParameter, mapOfOutputParameter);
			return;
		}*/
		
		
		try {
			aUnicornCall.doTask();

			this.createOutput(resp, aUnicornCall,
					mapOfSpecificParameter, mapOfOutputParameter, mapOfStringObject);
		} catch (final Exception aException) {
			ObserveAction.logger.error("Exception : " + aException.getMessage(),
					aException);
			
			if (mapOfOutputParameter.get("mimetype").equals("text/html")) {
				String errorContent = "";
				errorContent += aException.getMessage() + "\n";
				for (StackTraceElement stackTraceElement : aException.getStackTrace()) {
					errorContent += stackTraceElement.toString() + "\n";
				}
				Message mess = new Message(Message.Level.ERROR, "$stack_trace_text", errorContent);
				req.setAttribute("unicorn_message", mess);
				(new IndexAction()).doGet(req, resp);
				
			} else {
				this.createError(resp, aException, mapOfSpecificParameter, mapOfOutputParameter);
			}
			
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(final HttpServletRequest req,
			final HttpServletResponse resp)
			throws ServletException, IOException {
		ObserveAction.logger.trace("doPost");
		
		Map<String, Object> mapOfStringObject = new LinkedHashMap<String, Object>();
		
		// Check that we have a file upload request
		final boolean bIsMultipart = ServletFileUpload.isMultipartContent(new ServletRequestContext(req));

		if (!bIsMultipart) {
			this.doGet(req, resp);
			return;
		}
		
		// Language negotiation
		String langParameter = req.getParameter(Property.get("UNICORN_PARAMETER_PREFIX") + "lang");
		if (langParameter == null || !Framework.getLanguageProperties().containsKey(langParameter))
			langParameter = Language.negociate(req.getLocales());
		
		// Variables related to the output
		final Map<String, String> mapOfOutputParameter = new Hashtable<String, String>();
		mapOfOutputParameter.put("output", "simple");
		mapOfOutputParameter.put("format", "xhtml10");
		mapOfOutputParameter.put("charset", "UTF-8");
		mapOfOutputParameter.put("mimetype", "text/html");
		mapOfOutputParameter.put("lang", langParameter);
		
		final UnicornCall aUnicornCall = new UnicornCall();
		final String aLocale = convertEnumerationToString(req.getLocales());
		if (null == aLocale) {
			aUnicornCall.setLang(langParameter + "," + Property.get("DEFAULT_LANGUAGE"));
		} else {
			aUnicornCall.setLang(langParameter + "," + aLocale);
		}
		
		// Parse the request
		final List<?> listOfItem;

		ObserveAction.logger.trace("doPost");

		final Map<String, String[]> mapOfSpecificParameter = new Hashtable<String, String[]>();

		FileItem aFileItemUploaded = null;

		try {
			listOfItem = ObserveAction.upload.parseRequest(req);

			// Process the uploaded items
			for (final Iterator<?> aIterator = listOfItem.iterator(); aIterator
					.hasNext();) {
				final FileItem aFileItem = (FileItem) aIterator.next();
				if (aFileItem.isFormField()) {

					addParameter(aFileItem.getFieldName(), aFileItem
							.getString(), aUnicornCall, mapOfSpecificParameter,
							mapOfOutputParameter);

				} else if (aFileItem.getFieldName().equals(
						Property.get("UNICORN_PARAMETER_PREFIX") + "file")) {
					aFileItemUploaded = aFileItem;
					aUnicornCall.setDocumentName(aFileItemUploaded.getName());
					aUnicornCall.setInputParameterValue(aFileItemUploaded);
					aUnicornCall.setEnumInputMethod(EnumInputMethod.UPLOAD);
				}
			}
		}

		catch (final FileUploadException aFileUploadException) {
			ObserveAction.logger.error("FileUploadException : "
					+ aFileUploadException.getMessage(), aFileUploadException);
			this.createError(resp, aFileUploadException,
					mapOfSpecificParameter, mapOfOutputParameter);
		}

		if (aUnicornCall.getTask() == null) {
			aUnicornCall.setTask(Framework.mapOfTask.getDefaultTaskId());
			Message mess = new Message(Message.Level.WARNING, "$message_no_task " + Framework.mapOfTask.get(Framework.mapOfTask.getDefaultTaskId()).getLongName(langParameter), null);
			ArrayList<Message> messages = new ArrayList<Message>();
			messages.add(mess);
			mapOfStringObject.put("messages", messages);
		}
		
		try {
			aUnicornCall.doTask();

			this.createOutput(resp, aUnicornCall,
					mapOfSpecificParameter, mapOfOutputParameter, mapOfStringObject);
		} catch (final IOException aException) {
		
		} catch (final Exception aException) {
			ObserveAction.logger.error("Exception : " + aException.getMessage(),
					aException);
			
			if (mapOfOutputParameter.get("format").equals("xhtml10")) {
				(new IndexAction()).doGet(req, resp);
			} else {
				this.createError(resp, aException, mapOfSpecificParameter, mapOfOutputParameter);
			}
			
		} finally {
			if ("true".equals(Property.get("DELETE_UPLOADED_FILES"))
					&& aFileItemUploaded != null
					&& aFileItemUploaded instanceof FileItem) {
				aFileItemUploaded.delete();
			}
		}
	}

	/**
	 * Adds a parameter at the correct call.
	 * 
	 * @param sParamName
	 *            Name of the parameter.
	 * @param sParamValue
	 *            Value of the parameter.
	 * @param aUnicornCall
	 * @param mapOfSpecificParameter
	 * @param mapOfOutputParameter
	 */
	private void addParameter(final String sParamName,
			final String sParamValue, final UnicornCall aUnicornCall,
			final Map<String, String[]> mapOfSpecificParameter,
			final Map<String, String> mapOfOutputParameter) {
		final String[] tStringValues = { sParamValue };
		this.addParameter(sParamName, tStringValues, aUnicornCall,
				mapOfSpecificParameter, mapOfOutputParameter);
	}

	/**
	 * 
	 * @param sParamName
	 * @param tStringParamValue
	 * @param aUnicornCall
	 * @param mapOfSpecificParameter
	 * @param mapOfOutputParameter
	 */
	private void addParameter(String sParamName,
			final String[] tStringParamValue, final UnicornCall aUnicornCall,
			final Map<String, String[]> mapOfSpecificParameter,
			final Map<String, String> mapOfOutputParameter) {

		if (null == tStringParamValue || 0 == tStringParamValue.length) {
			// no value for this parameter
			// TODO log this info if necessary
			return;
		}

		if (!sParamName.startsWith(Property.get("UNICORN_PARAMETER_PREFIX"))) {
			// task parameter
			aUnicornCall.addParameter(sParamName, tStringParamValue);
			return;
		}

		// Unicorn parameter
		// TODO: Why is it here?
		sParamName = sParamName.substring(Property.get(
				"UNICORN_PARAMETER_PREFIX").length());

		// Output specific parameter
		if (sParamName.startsWith(Property
				.get("UNICORN_PARAMETER_OUTPUT_PREFIX"))) {
			sParamName = sParamName.substring(Property.get(
					"UNICORN_PARAMETER_OUTPUT_PREFIX").length());
			mapOfSpecificParameter.put(sParamName, tStringParamValue);
			return;
		}

		if (sParamName.equals("lang")) {
			aUnicornCall.addParameter(Property.get("UNICORN_PARAMETER_PREFIX")
					+ "lang", tStringParamValue);
		}

		// Global Unicorn parameter
		if (sParamName.equals("task")) {
			// FirstServlet.logger.debug("");
			aUnicornCall.setTask(tStringParamValue[0]);
		} else if (sParamName.equals("uri")) {
			aUnicornCall.setEnumInputMethod(EnumInputMethod.URI);
			// TODO First check that tStringParamValue[0] is at least 7 characters long
			if (tStringParamValue[0].length() < 7 || !tStringParamValue[0].substring(0, 7).equals("http://")) {
				ObserveAction.logger.info("URI missing protocol : "
						+ tStringParamValue[0]);
				tStringParamValue[0] = "http://" + tStringParamValue[0];
				ObserveAction.logger.info("URI modified to : "
						+ tStringParamValue[0]);
			}
			
			

			aUnicornCall.setDocumentName(tStringParamValue[0]);
			aUnicornCall.setInputParameterValue(tStringParamValue[0]);
		} else if (sParamName.equals("text")) {
			aUnicornCall.setEnumInputMethod(EnumInputMethod.DIRECT);
			aUnicornCall.setInputParameterValue(tStringParamValue[0]);
		}
		// TODO add upload handle when it work
		else if (sParamName.equals("output") || sParamName.equals("format")
				|| sParamName.equals("charset")
				|| sParamName.equals("mimetype") || sParamName.equals("lang")) {
			mapOfOutputParameter.put(sParamName, tStringParamValue[0]);
		} else if (sParamName.equals("text_mime")) {
			aUnicornCall.addParameter(Property.get("UNICORN_PARAMETER_PREFIX")
					+ "mime", tStringParamValue);
		}
	}

	private void createError(final HttpServletResponse aHttpServletResponse,
			final Exception aExceptionError,
			final Map<String, String[]> mapOfSpecificParameter,
			final Map<String, String> mapOfOutputParameter) throws IOException {
		aHttpServletResponse.setContentType(mapOfOutputParameter
				.get("mimetype")
				+ "; charset=" + mapOfOutputParameter.get("charset"));

		try {
			final OutputFormater aOutputFormater = OutputFactory
					.createOutputFormater(mapOfOutputParameter.get("format"),
							mapOfOutputParameter.get("lang"),
							mapOfOutputParameter.get("mimetype"));
			final OutputModule aOutputModule = OutputFactory
					.createOutputModule(mapOfOutputParameter.get("output"));
			aOutputModule.produceError(aOutputFormater, aExceptionError,
					mapOfSpecificParameter, aHttpServletResponse.getWriter());
		} catch (final ResourceNotFoundException e) {
			ObserveAction.logger.error("ResourceNotFoundException : "
					+ e.getMessage(), e);
			aHttpServletResponse.getWriter().println("<pre>");
			e.printStackTrace(aHttpServletResponse.getWriter());
			aHttpServletResponse.getWriter().println("</pre>");
		} catch (final ParseErrorException e) {
			ObserveAction.logger.error(
					"ParseErrorException : " + e.getMessage(), e);
			aHttpServletResponse.getWriter().println("<pre>");
			e.printStackTrace(aHttpServletResponse.getWriter());
			aHttpServletResponse.getWriter().println("</pre>");
		} catch (final Exception e) {
			ObserveAction.logger.error("Exception : " + e.getMessage(), e);
			aHttpServletResponse.getWriter().println("<pre>");
			e.printStackTrace(aHttpServletResponse.getWriter());
			aHttpServletResponse.getWriter().println("</pre>");
		}
	}

	private void createOutput(final HttpServletResponse aHttpServletResponse,
			final UnicornCall aUnicornCall,
			final Map<String, String[]> mapOfSpecificParameter,
			final Map<String, String> mapOfOutputParameter,
			Map<String, Object> mapOfStringObject) throws IOException {
		aHttpServletResponse.setContentType(mapOfOutputParameter
				.get("mimetype")
				+ "; charset=" + mapOfOutputParameter.get("charset"));
		try {
			//Map<String, Object> mapOfStringObject = new LinkedHashMap<String, Object>();
			mapOfStringObject.put("unicorncall", aUnicornCall);

			logger.debug("Request output formater with parameters: " 
					+ mapOfOutputParameter.get("format") + " "
					+ mapOfOutputParameter.get("lang") + " "
					+ mapOfOutputParameter.get("mimetype"));
			
			final OutputFormater aOutputFormater = OutputFactory
					.createOutputFormater(mapOfOutputParameter.get("format"),
							mapOfOutputParameter.get("lang"),
							mapOfOutputParameter.get("mimetype"));
			final OutputModule aOutputModule = OutputFactory
					.createOutputModule(mapOfOutputParameter.get("output"));
			aOutputModule.produceOutput(aOutputFormater, mapOfStringObject,
					mapOfSpecificParameter, aHttpServletResponse.getWriter());
		}

		catch (final ResourceNotFoundException e) {
			ObserveAction.logger.error("ResourceNotFoundException : "
					+ e.getMessage(), e);
			aHttpServletResponse.getWriter().println("<pre>");
			e.printStackTrace(aHttpServletResponse.getWriter());
			aHttpServletResponse.getWriter().println("</pre>");
		} catch (final ParseErrorException e) {
			ObserveAction.logger.error(
					"ParseErrorException : " + e.getMessage(), e);
			aHttpServletResponse.getWriter().println("<pre>");
			e.printStackTrace(aHttpServletResponse.getWriter());
			aHttpServletResponse.getWriter().println("</pre>");
		} catch (final Exception e) {
			ObserveAction.logger.error("Exception : " + e.getMessage(), e);
			aHttpServletResponse.getWriter().println("<pre>");
			e.printStackTrace(aHttpServletResponse.getWriter());
			aHttpServletResponse.getWriter().println("</pre>");
		}
	}

	/**
	 * Converts an Enumeration object to a string, the terms being separated by
	 * a coma.
	 * 
	 * @param myEnum
	 *            The enumeration to convert.
	 * @return The converted string.
	 */
	private String convertEnumerationToString(Enumeration<?> myEnum) {
		String ret = "";
		while (myEnum.hasMoreElements()) {
			ret += myEnum.nextElement().toString() + ",";
		}
		return ret.substring(0, ret.length() - 1);
	}

}
