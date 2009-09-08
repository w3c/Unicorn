// $Id: ObserveAction.java,v 1.19 2009-09-08 15:15:27 tgambet Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
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
import org.w3c.unicorn.Framework;

/**
 * ObserveAction
 * 
 * @author Jean-Guilhem ROUEL
 */
public class ObserveAction extends Action {
	
	private static final long serialVersionUID = -1375355420965607571L;
	
	private static Log logger = LogFactory.getLog(ObserveAction.class);
	
	private static DiskFileItemFactory factory;
	
	private static ServletFileUpload upload;
	
	private static ArrayList<String> outputParams;
	
	@Override
	public void init(final ServletConfig aServletConfig) throws ServletException {
		logger.trace("Init ObserverAction");
		factory = new DiskFileItemFactory();
		factory.setRepository(new File(Property.get("UPLOADED_FILES_REPOSITORY")));
		upload = new ServletFileUpload(factory);
		logger.debug("Created a ServletFileUpload with repository set to: " + Property.get("UPLOADED_FILES_REPOSITORY"));
		
		outputParams = new ArrayList<String>();
		outputParams.add("output");
		outputParams.add("format");
		outputParams.add("charset");
		outputParams.add("mimetype");
		outputParams.add("lang");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		super.doGet(req, resp);
		
		FileItem aFileItemUploaded = null;
		Map<String, Object> mapOfStringObject = new LinkedHashMap<String, Object>();
		Map<String, String> mapOfSpecificParameter = new Hashtable<String, String>();
		Map<String, String> mapOfOutputParameter = new Hashtable<String, String>();
		ArrayList<Message> messages = new ArrayList<Message>();
		UnicornCall aUnicornCall = new UnicornCall();
		
		mapOfOutputParameter.put("output", "simple");
		mapOfOutputParameter.put("format", "xhtml10");
		mapOfOutputParameter.put("charset", "UTF-8");
		mapOfOutputParameter.put("mimetype", "text/html");
		
		String paramPrefix = Property.get("UNICORN_PARAMETER_PREFIX");
		String outParamPrefix = Property.get("UNICORN_PARAMETER_OUTPUT_PREFIX");
		String queryString = getQueryStringWithout(paramPrefix + "lang", req);
		
		mapOfStringObject.put("queryString", queryString);
		mapOfStringObject.put("messages", messages);
		
		Map<String, Object> reqParams;
		try {
			reqParams = getRequestParameters(req);
		} catch (FileUploadException e) {
			createError(req, resp, new Message(e), mapOfSpecificParameter, mapOfOutputParameter);
			return;
		}
		
		for (String key : reqParams.keySet()) {
			if (!key.startsWith(paramPrefix) && !key.startsWith(outParamPrefix)) {
				logger.debug("UnicornCall parameter: " + key + " - " + (String) reqParams.get(key));
				aUnicornCall.addParameter(key, (String) reqParams.get(key)); 
				continue;
			}
			
			if (key.startsWith(outParamPrefix)) {
				logger.debug("Specific parameter: " + key + " - " + (String) reqParams.get(key));
				String paramName = key.substring(outParamPrefix.length());
				mapOfSpecificParameter.put(paramName, (String) reqParams.get(key));
				continue;
			}
			
			if (key.startsWith(paramPrefix)) {
				String paramName = key.substring(paramPrefix.length());
				
				if (paramName.equals("lang")) {
					logger.debug("Lang parameter: " + key + " - " + (String) reqParams.get(key));
					String lang = getLanguage((String) reqParams.get(key), req, null);
					mapOfOutputParameter.put(paramName, lang);
					String aLocale = convertEnumerationToString(req.getLocales());		
					if (null == aLocale)
						aUnicornCall.setLang(lang + "," + Property.get("DEFAULT_LANGUAGE"));
					else
						aUnicornCall.setLang(lang + "," + aLocale);
					if (!lang.equals(reqParams.get(key))) {
						logger.debug("Lang parameter unsupported. Resolved to: " + lang);
					}
				} else if (paramName.equals("task")) {
					logger.debug("Task parameter: " + key + " - " + (String) reqParams.get(key));
					String task = getTask((String) reqParams.get(key), messages);
					if (!task.equals(reqParams.get(key))) {
						mapOfStringObject.put("default_task", Framework.mapOfTask.get(Framework.mapOfTask.getDefaultTaskId()));
						logger.debug("Task parameter unsupported. Resolved to: " + task);
					}
					aUnicornCall.setTask(task);
				} else if (outputParams.contains(paramName)) {
					logger.debug("Output parameter: " + key + " - " + (String) reqParams.get(key));
					mapOfOutputParameter.put(paramName, (String) reqParams.get(key));
					continue;
				} else if (paramName.equals("uri")) {
					logger.debug("Uri parameter: " + key + " - " + (String) reqParams.get(key));
					aUnicornCall.setEnumInputMethod(EnumInputMethod.URI);
					String uri = (String) reqParams.get(key);
					if (uri.length() < 7 || !uri.substring(0, 7).equals("http://")) {
						uri = "http://" + uri;
					}
					aUnicornCall.setDocumentName(uri);
					aUnicornCall.setInputParameterValue(uri);
				} else if (paramName.equals("text")) {
					logger.debug("Text parameter: " + key + " - " + (String) reqParams.get(key));
					aUnicornCall.setEnumInputMethod(EnumInputMethod.DIRECT);
					aUnicornCall.setInputParameterValue((String) reqParams.get(key));
				} else if (paramName.equals("file")) {
					logger.debug("File parameter: " + key + " - " + reqParams.get(key).toString());
					Object object = reqParams.get(key);
					if (object instanceof FileItem) {
						aFileItemUploaded = (FileItem) object;
						aUnicornCall.setDocumentName(aFileItemUploaded.getName());
						aUnicornCall.setInputParameterValue(aFileItemUploaded);
						aUnicornCall.setEnumInputMethod(EnumInputMethod.UPLOAD);
					} else {
						// TODO log "ucn_file not an instance of FileItem ?"
					}
				} else if (paramName.equals("text_mime")) {
					aUnicornCall.addParameter(paramPrefix + "mime", (String) reqParams.get(key));
				} else {
					logger.debug("Unknown parameter: " + key + " - " + (String) reqParams.get(key) +". This parameter is added to aUnicornCall.");
					aUnicornCall.addParameter(key, (String) reqParams.get(key));
				}
			}
		}
		
		if (!reqParams.containsKey(paramPrefix + "lang")) {
			String lang = getLanguage(null, req, null);
			reqParams.put(paramPrefix + "lang", getLanguage(null, req, null));
			logger.debug("No language parameter found. Language negociation resolved language to: " + lang);
			mapOfOutputParameter.put("lang", lang);
			String aLocale = convertEnumerationToString(req.getLocales());		
			if (null == aLocale)
				aUnicornCall.setLang(lang + "," + Property.get("DEFAULT_LANGUAGE"));
			else
				aUnicornCall.setLang(lang + "," + aLocale);
		}
		
		if (!reqParams.containsKey(paramPrefix + "task")) {
			String task = getTask(null, messages);
			reqParams.put(paramPrefix + "task", task);
			logger.debug("No task parameter found. Task parameter is set to task id: " + task);
			mapOfStringObject.put("default_task", Framework.mapOfTask.get(Framework.mapOfTask.getDefaultTaskId()));
			aUnicornCall.setTask(task);
		}
		
		if (reqParams.containsKey(paramPrefix + "uri")) {
			if (reqParams.get(paramPrefix + "uri").equals("")) {
				Message mess = new Message(Message.Level.ERROR, "$message_empty_uri", null);
				createError(req, resp, mess, mapOfSpecificParameter, mapOfOutputParameter);
				return;
			}
		} else if (reqParams.containsKey(paramPrefix + "text")) {
			if (reqParams.get(paramPrefix + "text").equals("")) {
				Message mess = new Message(Message.Level.ERROR, "$message_empty_direct_input", null);
				createError(req, resp, mess, mapOfSpecificParameter, mapOfOutputParameter);
				return;
			} else if (!reqParams.containsKey(paramPrefix + "text_mime")) {
				Message mess = new Message(Message.Level.ERROR, "$message_missing_mime_type", null);
				createError(req, resp, mess, mapOfSpecificParameter, mapOfOutputParameter);
				return;
			}
		} else if (reqParams.containsKey(paramPrefix + "file")) {
			if (((FileItem) reqParams.get(paramPrefix + "file")).getSize() == 0) {
				Message mess = new Message(Message.Level.ERROR, "$message_no_or_empty_file", null);
				createError(req, resp, mess, mapOfSpecificParameter, mapOfOutputParameter);
				return;
			}
		} else {
			Message mess = new Message(Message.Level.ERROR, "$message_nothing_to_validate", null);
			createError(req, resp, mess, mapOfSpecificParameter, mapOfOutputParameter);
			return;
		}
		
		try {
			aUnicornCall.doTask();
			createOutput(req, resp, mapOfStringObject, aUnicornCall, mapOfSpecificParameter, mapOfOutputParameter);
		} catch (final UnsupportedMimeTypeException aException) {
			Message mess = new Message(Message.Level.ERROR, "$message_unsupported_mime_type", null);
			createError(req, resp, mess, mapOfSpecificParameter, mapOfOutputParameter);
		} catch (final Exception aException) {
			logger.error("Exception : " + aException.getMessage(), aException);
			createError(req, resp, new Message(aException), mapOfSpecificParameter, mapOfOutputParameter);
		} finally {
			if ("true".equals(Property.get("DELETE_UPLOADED_FILES"))
					&& aFileItemUploaded != null) {
				aFileItemUploaded.delete();
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	private Map<String, Object> getRequestParameters(HttpServletRequest req) throws FileUploadException {
		
		Hashtable<String, Object> params = new Hashtable<String, Object>();
		
		if (req.getMethod().equals("POST") && ServletFileUpload.isMultipartContent(new ServletRequestContext(req))) {
			List<?> listOfItem = upload.parseRequest(req);
			for (Object fileItem : listOfItem) {
				FileItem aFileItem = (FileItem) fileItem;
				if (aFileItem.isFormField()) {
					params.put(aFileItem.getFieldName(), aFileItem.getString());
				} else if (aFileItem.getFieldName().equals(Property.get("UNICORN_PARAMETER_PREFIX") + "file")) {
					params.put(aFileItem.getFieldName(), aFileItem);
				} else {
					// TODO log "unknown fileItem, ignored"
				}
			}
		} else {
			Enumeration<?> paramEnum = req.getParameterNames();
			while (paramEnum.hasMoreElements()) {
				
				Object key = paramEnum.nextElement();
				logger.debug("TOM: " + key);
				params.put(key.toString(), req.getParameter(key.toString()));
			}
		}
		
		String s = "Parameters: ";
		for (String key : params.keySet()) {
			s += "\n\t" + key + " - " + params.get(key);
		}
		logger.debug(s);
		
		return params;
	}
	
	private void createError(HttpServletRequest req, HttpServletResponse resp,
			Message mess, Map<String, String> mapOfSpecificParameter,
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
			Map<String, String> mapOfSpecificParameter, Map<String, String> mapOfOutputParameter) throws IOException {
		
		resp.setContentType(mapOfOutputParameter.get("mimetype") + "; charset=" + mapOfOutputParameter.get("charset"));

		mapOfStringObject.put("unicorncall", aUnicornCall);
		
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