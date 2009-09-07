// $Id: ObserveAction.java,v 1.13 2009-09-07 16:33:49 tgambet Exp $
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.exceptions.UnsupportedMimeTypeException;
import org.w3c.unicorn.output.OutputFactory;
import org.w3c.unicorn.output.OutputFormater;
import org.w3c.unicorn.output.OutputModule;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.Property;

/**
 * FirstServlet<br />
 * Created: Jun 26, 2006 2:04:11 PM<br />
 * 
 * @author Jean-Guilhem ROUEL
 */
public class ObserveAction extends Action {
	
	private static final long serialVersionUID = -1375355420965607571L;
	
	private static Log logger = LogFactory.getLog(ObserveAction.class);
	
	private static DiskFileItemFactory factory;
	
	private static ServletFileUpload upload;
	
	@Override
	public void init(final ServletConfig aServletConfig) throws ServletException {
		logger.trace("Init ObserverAction");
		factory = new DiskFileItemFactory();
		factory.setRepository(new File(Property.get("UPLOADED_FILES_REPOSITORY")));
		upload = new ServletFileUpload(factory);
		logger.debug("Created a ServletFileUpload with repository set to: " + Property.get("UPLOADED_FILES_REPOSITORY"));
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		super.doGet(req, resp);
		
		Map<String, Object> mapOfStringObject = new LinkedHashMap<String, Object>();
		Map<String, String[]> mapOfSpecificParameter = new Hashtable<String, String[]>();
		Map<String, String> mapOfOutputParameter = new Hashtable<String, String>();
		ArrayList<Message> messages = new ArrayList<Message>();
		
		String paramPrefix = Property.get("UNICORN_PARAMETER_PREFIX");
		String lang = getLanguage(req, null);
		String task = getTask(req, lang, messages);
		String queryString = getQueryStringWithout(paramPrefix + "lang", req);
		
		mapOfStringObject.put("queryString", queryString);
		mapOfStringObject.put("messages", messages);
		
		mapOfOutputParameter.put("output", "simple");
		mapOfOutputParameter.put("format", "xhtml10");
		mapOfOutputParameter.put("charset", "UTF-8");
		mapOfOutputParameter.put("mimetype", "text/html");
		mapOfOutputParameter.put("lang", lang);
		
		UnicornCall aUnicornCall = new UnicornCall();
		String aLocale = convertEnumerationToString(req.getLocales());
		if (null == aLocale)
			aUnicornCall.setLang(lang + "," + Property.get("DEFAULT_LANGUAGE"));
		else
			aUnicornCall.setLang(lang + "," + aLocale);
		
		for (Object param : req.getParameterMap().keySet()) {
			String sParamName = (String) param;
			String[] tStringParamValue = req.getParameterValues(sParamName);
			addParameter(sParamName, tStringParamValue, aUnicornCall, mapOfSpecificParameter, mapOfOutputParameter);
		}
		
		addParameter(paramPrefix+ "task", task, aUnicornCall, mapOfSpecificParameter, mapOfOutputParameter);
		
		// POST operations
		FileItem aFileItemUploaded = null;
		if (req.getMethod().equals("POST") && ServletFileUpload.isMultipartContent(new ServletRequestContext(req))) {
			messages.clear();
			try {
				List<?> listOfItem = upload.parseRequest(req);
				// Process the uploaded items
				for (Iterator<?> aIterator = listOfItem.iterator(); aIterator.hasNext();) {
					FileItem aFileItem = (FileItem) aIterator.next();
					logger.error("TOM: " + aFileItem);
					if (aFileItem.isFormField()) {
						addParameter(aFileItem.getFieldName(), aFileItem.getString(),
								aUnicornCall, mapOfSpecificParameter, mapOfOutputParameter);
					} else if (aFileItem.getFieldName().equals(paramPrefix + "file")) {
						aFileItemUploaded = aFileItem;
						aUnicornCall.setDocumentName(aFileItemUploaded.getName());
						aUnicornCall.setInputParameterValue(aFileItemUploaded);
						aUnicornCall.setEnumInputMethod(EnumInputMethod.UPLOAD);
						break;
					}
				}
			} catch (final FileUploadException aFileUploadException) {
				logger.error("FileUploadException : " + aFileUploadException.getMessage(), aFileUploadException);
				//TODO
				Message mess = new Message();
				createError(req, resp, mess,mapOfSpecificParameter, mapOfOutputParameter);
			}
		}
		
		try {
			aUnicornCall.doTask();
			createOutput(req, resp, mapOfStringObject, aUnicornCall, mapOfSpecificParameter, mapOfOutputParameter);
		} catch (final UnsupportedMimeTypeException aException) {
			Message mess = new Message(Message.Level.ERROR, "$message_unsupported_mime_type", null);
			createError(req, resp, mess, mapOfSpecificParameter, mapOfOutputParameter);
		} catch (final Exception aException) {
			logger.error("Exception : " + aException.getMessage(), aException);
			
			String errorContent = "";
			errorContent += aException.getMessage() + "\n";
			for (StackTraceElement stackTraceElement : aException.getStackTrace()) {
				errorContent += stackTraceElement.toString() + "\n";
			}
			Message mess = new Message(Message.Level.ERROR, "$stack_trace_text", errorContent);
			createError(req, resp, mess, mapOfSpecificParameter, mapOfOutputParameter);
		} finally {
			if ("true".equals(Property.get("DELETE_UPLOADED_FILES"))
					&& aFileItemUploaded != null
					&& aFileItemUploaded instanceof FileItem) {
				aFileItemUploaded.delete();
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
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

		// Output specific parameter
		if (sParamName.startsWith(Property
				.get("UNICORN_PARAMETER_OUTPUT_PREFIX"))) {
			sParamName = sParamName.substring(Property.get(
					"UNICORN_PARAMETER_OUTPUT_PREFIX").length());
			mapOfSpecificParameter.put(sParamName, tStringParamValue);
			return;
		}
		
		// Unicorn parameter
		// TODO: Why is it here?
		sParamName = sParamName.substring(Property.get(
				"UNICORN_PARAMETER_PREFIX").length());

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

	private void createError(HttpServletRequest req, HttpServletResponse resp,
			Message mess, Map<String, String[]> mapOfSpecificParameter,
			Map<String, String> mapOfOutputParameter) throws IOException, ServletException {
		
		// If text/html is the mime-type the error will be displayed directly on index
		if (mapOfOutputParameter.get("mimetype").equals("text/html")) {
			req.setAttribute("unicorn_message", mess);
			(new IndexAction()).doGet(req, resp);
			return;
		}
		
		resp.setContentType(mapOfOutputParameter.get("mimetype") + "; charset=" + mapOfOutputParameter.get("charset"));
		OutputFormater aOutputFormater = OutputFactory
			.createOutputFormater(mapOfOutputParameter.get("format"),
								  mapOfOutputParameter.get("lang"),
								  mapOfOutputParameter.get("mimetype"));
		OutputModule aOutputModule = OutputFactory.createOutputModule(mapOfOutputParameter.get("output"));
		aOutputModule.produceError(aOutputFormater, mess, mapOfSpecificParameter, resp.getWriter());
	}

	private void createOutput(HttpServletRequest req, HttpServletResponse resp,
			Map<String, Object> mapOfStringObject, UnicornCall aUnicornCall,
			Map<String, String[]> mapOfSpecificParameter, Map<String, String> mapOfOutputParameter) throws IOException {
		
		resp.setContentType(mapOfOutputParameter.get("mimetype") + "; charset=" + mapOfOutputParameter.get("charset"));

		mapOfStringObject.put("unicorncall", aUnicornCall);

		logger.debug("Request output formater with parameters: " 
				+ mapOfOutputParameter.get("format") + " "
				+ mapOfOutputParameter.get("lang") + " "
				+ mapOfOutputParameter.get("mimetype"));
		
		OutputFormater aOutputFormater = OutputFactory
				.createOutputFormater(mapOfOutputParameter.get("format"),
									  mapOfOutputParameter.get("lang"),
									  mapOfOutputParameter.get("mimetype"));
		OutputModule aOutputModule = OutputFactory
				.createOutputModule(mapOfOutputParameter.get("output"));
		aOutputModule.produceOutput(aOutputFormater, mapOfStringObject, mapOfSpecificParameter, resp.getWriter());
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
