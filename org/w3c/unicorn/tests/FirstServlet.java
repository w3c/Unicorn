// $Id: FirstServlet.java,v 1.13 2008-09-23 13:53:58 jean-gui Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tests;

import java.io.File;
import java.io.IOException;
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
import org.w3c.unicorn.exceptions.NoTaskException;
import org.w3c.unicorn.index.IndexGenerator;
import org.w3c.unicorn.output.OutputFactory;
import org.w3c.unicorn.output.OutputFormater;
import org.w3c.unicorn.output.OutputModule;
import org.w3c.unicorn.util.LocalizedString;
import org.w3c.unicorn.util.Property;

/**
 * FirstServlet<br />
 * Created: Jun 26, 2006 2:04:11 PM<br />
 * @author Jean-Guilhem ROUEL
 */
public class FirstServlet extends HttpServlet {

	private static final Log logger = LogFactory.getLog("org.w3c.unicorn");

	private static final long	serialVersionUID	= -1375355420965607571L;

	private static final DiskFileItemFactory factory = new DiskFileItemFactory();

	/**
	 * Creates a new file upload handler.
	 */
	private static final ServletFileUpload upload = new ServletFileUpload(FirstServlet.factory);

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init (final ServletConfig aServletConfig) throws ServletException {
		FirstServlet.logger.trace("init");

		FirstServlet.factory.setRepository(
				new File(Property.get("UPLOADED_FILES_REPOSITORY")));

		try {
        IndexGenerator.generateIndexes();
		}
		catch (final ResourceNotFoundException e) {
			FirstServlet.logger.error("ResourceNotFoundException : "+e.getMessage(), e);
			e.printStackTrace();
		}
		catch (final ParseErrorException e) {
			FirstServlet.logger.error("ParseErrorException : "+e.getMessage(), e);
			e.printStackTrace();
		}
		catch (final Exception e) {
			FirstServlet.logger.error("Exception : "+e.getMessage(), e);
			e.printStackTrace();
		}
		FirstServlet.logger.info("End of initialisation of servlet.");

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet (
			final HttpServletRequest aHttpServletRequest,
			final HttpServletResponse aHttpServletResponse)
	throws ServletException, IOException {
		FirstServlet.logger.trace("doGet");

		// Variables related to the output
		final Map<String, String[]> mapOfSpecificParameter = new Hashtable<String, String[]>();
		final Map<String, String> mapOfOutputParameter = new Hashtable<String, String>();
		mapOfOutputParameter.put("output", "simple");
		mapOfOutputParameter.put("format", "xhtml10");
		mapOfOutputParameter.put("charset", "UTF-8");
		mapOfOutputParameter.put("mimetype", "text/html");

		// Returns the preferred Locale that the client will accept content in,
		// based on the Accept-Language header
		final String aLocale = convertEnumerationToString(aHttpServletRequest.getLocales());
		final UnicornCall aUnicornCall = new UnicornCall();

		// Language of the template
		// ucn_lang is a parameter which is define in xx_index.html.vm.
		// It is an hidden parameter of a form.
		String templateLang = null;
		if (aHttpServletRequest.getParameterValues("ucn_lang") != null){
			templateLang = aHttpServletRequest.getParameterValues("ucn_lang")[0];
		}
		else {
			templateLang = chooseTemplateLang(aLocale);
		}

		mapOfOutputParameter.put("lang", templateLang);

		if (null == aLocale) {
			aUnicornCall.setLang(LocalizedString.DEFAULT_LANGUAGE);
		} else {
			aUnicornCall.setLang(templateLang + "," + aLocale);
		}

		for (
				final Enumeration aEnumParamName = aHttpServletRequest.getParameterNames();
				aEnumParamName.hasMoreElements();) {
			final String sParamName = (String) aEnumParamName.nextElement();
			final String[] tStringParamValue = aHttpServletRequest.getParameterValues(sParamName);

			this.addParameter(
					sParamName,
					tStringParamValue,
					aUnicornCall,
					mapOfSpecificParameter,
					mapOfOutputParameter);
		} // For

		if (aUnicornCall.getTask() == null) {
			FirstServlet.logger.error("No task selected.");
			this.createError(
					aHttpServletResponse,
					new NoTaskException(),
					mapOfSpecificParameter,
					mapOfOutputParameter);
			return;
		}

		try {
			aUnicornCall.doTask();

			this.createOutput(
					aHttpServletResponse,
					aUnicornCall,
					mapOfSpecificParameter,
					mapOfOutputParameter);
		}
		catch (final Exception aException) {
			FirstServlet.logger.error("Exception : "+aException.getMessage(), aException);
			this.createError(
					aHttpServletResponse,
					aException,
					mapOfSpecificParameter,
					mapOfOutputParameter);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override


	protected void doPost (
			final HttpServletRequest aHttpServletRequest,
			final HttpServletResponse aHttpServletResponse)
	throws ServletException, IOException {
		FirstServlet.logger.trace("doPost");

		// Check that we have a file upload request
		final boolean bIsMultipart = ServletFileUpload.isMultipartContent(
				new ServletRequestContext(aHttpServletRequest));

		if (!bIsMultipart) {
			this.doGet(aHttpServletRequest, aHttpServletResponse);
			return;
		}

		// Parse the request
		final List listOfItem;

		final UnicornCall aUnicornCall = new UnicornCall();

		// Variables related to the output
		final Map<String, String> mapOfOutputParameter = new Hashtable<String, String>();
		mapOfOutputParameter.put("output", "simple");
		mapOfOutputParameter.put("format", "xhtml10");
		mapOfOutputParameter.put("charset", "UTF-8");
		mapOfOutputParameter.put("mimetype", "text/html");

		// Returns the preferred Locale that the client will accept content in,
		// based on the Accept-Language header
		final String aLocale = convertEnumerationToString(aHttpServletRequest.getLocales());


		// Language of the template
		// ucn_lang is a parameter which is define in xx_index.html.vm.
		// It is an hidden parameter of a form.
		String templateLang = null;

		FirstServlet.logger.trace("doPost");




		final Map<String, String[]> mapOfSpecificParameter = new Hashtable<String, String[]>();

		FileItem aFileItemUploaded = null;


		try {
			listOfItem = FirstServlet.upload.parseRequest(aHttpServletRequest);


			// Process the uploaded items
			for (final Iterator aIterator = listOfItem.iterator(); aIterator.hasNext();) {
				final FileItem aFileItem = (FileItem) aIterator.next();
				if (aFileItem.isFormField()) {

					if(aFileItem.getFieldName().equals("ucn_lang")) {
						templateLang = aFileItem.getString();
					}


					addParameter(aFileItem.getFieldName(), aFileItem.getString(),
							aUnicornCall, mapOfSpecificParameter, mapOfOutputParameter);

				} else if(aFileItem.getFieldName().equals("ucn_file")) {
					aFileItemUploaded = aFileItem;
					aUnicornCall.setDocumentName(aFileItemUploaded.getName());
					aUnicornCall.setInputParameterValue(aFileItemUploaded);
					aUnicornCall.setEnumInputMethod(EnumInputMethod.UPLOAD);
				}
			}

			if (templateLang == null)
				templateLang = chooseTemplateLang(aLocale);

			if (null == aLocale) {
				aUnicornCall.setLang(LocalizedString.DEFAULT_LANGUAGE);
			} else {
				aUnicornCall.setLang(templateLang + "," + aLocale);
			}
			mapOfOutputParameter.put("lang", templateLang);

		}

		catch (final FileUploadException aFileUploadException) {
			FirstServlet.logger.error(
					"FileUploadException : "+aFileUploadException.getMessage(),
					aFileUploadException);
			this.createError(
					aHttpServletResponse,
					aFileUploadException,
					mapOfSpecificParameter,
					mapOfOutputParameter);
		}

		try {
			aUnicornCall.doTask();


			this.createOutput(
					aHttpServletResponse,
					aUnicornCall,
					mapOfSpecificParameter,
					mapOfOutputParameter);
		}
		catch (final Exception aException) {
			FirstServlet.logger.error("Exception : "+aException.getMessage(), aException);
			this.createError(
					aHttpServletResponse,
					aException,
					mapOfSpecificParameter,
					mapOfOutputParameter);
		}
		finally {
			if("true".equals(Property.get("DELETE_UPLOADED_FILES")) &&
					aFileItemUploaded != null && aFileItemUploaded instanceof FileItem) {
				aFileItemUploaded.delete();
			}
		}
	}

	/**
	 * Adds a parameter at the correct call.
	 * @param sParamName Name of the parameter.
	 * @param sParamValue Value of the parameter.
	 * @param aUnicornCall
	 * @param mapOfSpecificParameter
	 * @param mapOfOutputParameter
	 */
	private void addParameter (
			final String sParamName,
			final String sParamValue,
			final UnicornCall aUnicornCall,
			final Map<String, String[]> mapOfSpecificParameter,
			final Map<String, String> mapOfOutputParameter) {
		final String[] tStringValues = {sParamValue};
		this.addParameter(sParamName, tStringValues, aUnicornCall, mapOfSpecificParameter, mapOfOutputParameter);
	}

	/**
	 *
	 * @param sParamName
	 * @param tStringParamValue
	 * @param aUnicornCall
	 * @param mapOfSpecificParameter
	 * @param mapOfOutputParameter
	 */
	private void addParameter (
			String sParamName,
			final String[] tStringParamValue,
			final UnicornCall aUnicornCall,
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
		sParamName = sParamName.substring(4);


		// Output specific parameter
		if (sParamName.startsWith(Property.get("UNICORN_PARAMETER_OUTPUT_PREFIX"))) {
			sParamName = sParamName.substring(4);
			mapOfSpecificParameter.put(sParamName, tStringParamValue);
			return;
		}

		if (sParamName.equals("lang")) {
			aUnicornCall.addParameter("ucn_lang", tStringParamValue);
		}

		// Global Unicorn parameter
		if (sParamName.equals("task")) {
			//FirstServlet.logger.debug("");
			aUnicornCall.setTask(tStringParamValue[0]);
		}
		else if (sParamName.equals("uri")) {
			aUnicornCall.setEnumInputMethod(EnumInputMethod.URI);
			aUnicornCall.setDocumentName(tStringParamValue[0]);
			aUnicornCall.setInputParameterValue(tStringParamValue[0]);
		}
		else if (sParamName.equals("text")) {
			aUnicornCall.setEnumInputMethod(EnumInputMethod.DIRECT);
			aUnicornCall.setDocumentName(tStringParamValue[0]);
			aUnicornCall.setInputParameterValue(tStringParamValue[0]);
		}
		// TODO add upload handle when it work
		else if (sParamName.equals("output") || sParamName.equals("format") ||
				sParamName.equals("charset") || sParamName.equals("mimetype") ||
				sParamName.equals("lang")) {
			mapOfOutputParameter.put(sParamName, tStringParamValue[0]);
		}
		else if (sParamName.equals("text_mime")) {
			aUnicornCall.addParameter("ucn_mime", tStringParamValue);
		}
	}

	private void createError (
			final HttpServletResponse aHttpServletResponse,
			final Exception aExceptionError,
			final Map<String, String[]> mapOfSpecificParameter,
			final Map<String, String> mapOfOutputParameter)
	throws IOException {
      aHttpServletResponse.setContentType(mapOfOutputParameter.get("mimetype") + "; charset=" + mapOfOutputParameter.get("charset"));

		try {
			final OutputFormater aOutputFormater = OutputFactory.getOutputFormater(
					mapOfOutputParameter.get("format"),
					mapOfOutputParameter.get("lang"),
					mapOfOutputParameter.get("mimetype"));
			final OutputModule aOutputModule = OutputFactory.getOutputModule(
					mapOfOutputParameter.get("output"));
			aOutputModule.produceError(
					aOutputFormater,
					aExceptionError,
					mapOfSpecificParameter,
					aHttpServletResponse.getWriter());
		}
		catch (final ResourceNotFoundException e) {
			FirstServlet.logger.error("ResourceNotFoundException : "+e.getMessage(), e);
			aHttpServletResponse.getWriter().println("<pre>");
			e.printStackTrace(aHttpServletResponse.getWriter());
			aHttpServletResponse.getWriter().println("</pre>");
		}
		catch (final ParseErrorException e) {
			FirstServlet.logger.error("ParseErrorException : "+e.getMessage(), e);
			aHttpServletResponse.getWriter().println("<pre>");
			e.printStackTrace(aHttpServletResponse.getWriter());
			aHttpServletResponse.getWriter().println("</pre>");
		}
		catch (final Exception e) {
			FirstServlet.logger.error("Exception : "+e.getMessage(), e);
			aHttpServletResponse.getWriter().println("<pre>");
			e.printStackTrace(aHttpServletResponse.getWriter());
			aHttpServletResponse.getWriter().println("</pre>");
		}
	}

	private void createOutput (
			final HttpServletResponse aHttpServletResponse,
			final UnicornCall aUnicornCall,
			final Map<String, String[]> mapOfSpecificParameter,
			final Map<String, String> mapOfOutputParameter)
	throws IOException {
      aHttpServletResponse.setContentType(mapOfOutputParameter.get("mimetype") + "; charset=" + mapOfOutputParameter.get("charset"));
		try {
			Map<String, Object> mapOfStringObject = new LinkedHashMap<String, Object>();
			mapOfStringObject.put("unicorncall", aUnicornCall);

			final OutputFormater aOutputFormater = OutputFactory.getOutputFormater(
					mapOfOutputParameter.get("format"),
					mapOfOutputParameter.get("lang"),
					mapOfOutputParameter.get("mimetype"));
			final OutputModule aOutputModule = OutputFactory.getOutputModule(
					mapOfOutputParameter.get("output"));
			aOutputModule.produceOutput(
					aOutputFormater,
					mapOfStringObject,
					mapOfSpecificParameter,
					aHttpServletResponse.getWriter());
		}

		catch (final ResourceNotFoundException e) {
			FirstServlet.logger.error("ResourceNotFoundException : "+e.getMessage(), e);
			aHttpServletResponse.getWriter().println("<pre>");
			e.printStackTrace(aHttpServletResponse.getWriter());
			aHttpServletResponse.getWriter().println("</pre>");
		}
		catch (final ParseErrorException e) {
			FirstServlet.logger.error("ParseErrorException : "+e.getMessage(), e);
			aHttpServletResponse.getWriter().println("<pre>");
			e.printStackTrace(aHttpServletResponse.getWriter());
			aHttpServletResponse.getWriter().println("</pre>");
		}
		catch (final Exception e) {
			FirstServlet.logger.error("Exception : "+e.getMessage(), e);
			aHttpServletResponse.getWriter().println("<pre>");
			e.printStackTrace(aHttpServletResponse.getWriter());
			aHttpServletResponse.getWriter().println("</pre>");
		}
	}

	/**
	 * This method returns the first language of the accept language list
	 * which is equal to one of available index template language
	 *
	 * @param aLocale
	 * @return The selected language or the default language.
	 */
	private String chooseTemplateLang(String aLocale){
		String[] tabLang = aLocale.split(";|,");
		for (int i=0; i<tabLang.length; i++){
			if (Framework.outputLang.contains(tabLang[i]))
				return tabLang[i];
			else if (Framework.outputLang.contains(tabLang[i].split("-")[0]))
				return tabLang[i].split("-")[0];
		}

		return LocalizedString.DEFAULT_LANGUAGE;
	}


	/**
	 * Converts an Enumeration object to a string, the terms being
	 * separated by a coma.
	 * @param myEnum The enumeration to convert.
	 * @return The converted string.
	 */
	private String convertEnumerationToString(Enumeration myEnum){
		String ret = "";
		while (myEnum.hasMoreElements()){
			ret += myEnum.nextElement().toString() + ",";
		}
		return ret.substring(0,ret.length()-1);
	}

}
