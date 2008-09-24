package org.w3c.unicorn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Title: Client HTTP Request class</p>
 * <p>Description: this class helps to send POST HTTP requests with various form data,
 * including files. Cookies can be added to be included in the request.</p>
 *
 * @author Vlad Patryshev
 * @version 1.0
 */
public class ClientHttpRequest {

	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.util");

	private static Random aRandom = new Random();

	private HttpURLConnection aURLConnection;
	private OutputStream aOutputStream = null;
	private Map<String, String> mapOfCookie = new HashMap<String, String>();

	private String sBoundary =
		"---------------------------" +
		ClientHttpRequest.randomString() +
		ClientHttpRequest.randomString() +
		ClientHttpRequest.randomString();

	
	/**
	 * Connects to the output stream of the URLConnection.
	 * @throws IOException
	 */
	private void connect () throws IOException {
		//ClientHttpRequest.logger.trace("connect");
		if (null == this.aOutputStream) {
			this.aOutputStream = this.aURLConnection.getOutputStream();
		}
	}

	/**
	 * Writes a single character on the output stream.
	 * @param c The character to write.
	 * @throws IOException
	 */
	private void write (final char c) throws IOException {
		this.connect();
		ClientHttpRequest.logger.debug(c);
		this.aOutputStream.write(c);
	}

	/**
	 * Writes a character string on the output stream.
	 * @param s The string to write.
	 * @throws IOException
	 */
	protected void write (final String s) throws IOException {
		this.connect();
		ClientHttpRequest.logger.debug(s);
		this.aOutputStream.write(s.getBytes());
	}

	/**
	 * Writes a new line on the output stream (carriage return).
	 * @throws IOException
	 */
	protected void newline () throws IOException {
		this.connect();
		this.write("\r\n");
	}

	/**
	 * Writes a string and a new line on the output stream.
	 * @param s The string to write before the new line.
	 * @throws IOException
	 */
	protected void writeln (final String s) throws IOException {
		this.connect();
		this.write(s);
		this.newline();
	}

	/**
	 * Computes a random string.
	 * @return A string containing a random long which radix is 36. 
	 */
	protected static String randomString () {
		return Long.toString(ClientHttpRequest.aRandom.nextLong(), 36);
	}

	/**
	 * Writes the sBoundary on the output, composed of three random strings.
	 * @throws IOException
	 */
	private void boundary () throws IOException {
    System.out.println("boundary() " + this.aURLConnection.getRequestMethod());
		this.write("--");
    System.out.println("boundary() " + this.aURLConnection.getRequestMethod());
		this.write(this.sBoundary);
    System.out.println("boundary() " + this.aURLConnection.getRequestMethod());
	}

	/**
	 * Creates a new multipart POST HTTP request on a freshly opened URLConnection
	 *
	 * @param aURLConnection an already open URL connection
	 * @throws IOException
	 */
	public ClientHttpRequest (
			final URLConnection aURLConnection) throws IOException {
		ClientHttpRequest.logger.trace("Constructor(URLConnection)");
		this.aURLConnection = (HttpURLConnection) aURLConnection;
		this.aURLConnection.setDoOutput(true);
    this.aURLConnection.setRequestMethod("POST");
		this.aURLConnection.setRequestProperty(
				"Content-Type",
				"multipart/form-data; boundary=" + this.sBoundary);
	}

	/**
	 * Creates a new multipart POST HTTP request for a specified URL
	 *
	 * @param aURL the URL to send request to
	 * @throws IOException
	 */
	public ClientHttpRequest (final URL aURL) throws IOException {
      this(aURL.openConnection());
		ClientHttpRequest.logger.trace("Constructor(URL)");
		if (ClientHttpRequest.logger.isDebugEnabled()) {
			ClientHttpRequest.logger.debug("URL : " + aURL + ".");
		}
	}

	/**
	 * Creates a new multipart POST HTTP request for a specified URL string
	 *
	 * @param sURL the string representation of the URL to send request to
	 * @throws IOException
	 */
	public ClientHttpRequest (
			final String sURL) throws IOException {
		this(new URL(sURL));
		ClientHttpRequest.logger.trace("Constructor(String)");
		if (ClientHttpRequest.logger.isDebugEnabled()) {
			ClientHttpRequest.logger.debug("String URL : " + sURL + ".");
		}
	}

	/**
	 * adds a cookie to the request
	 * @param sName cookie name
	 * @param sValue cookie value
	 * @throws IOException
	 */
	public void setCookie (
			final String sName,
			final String sValue) throws IOException {
		this.mapOfCookie.put(sName, sValue);
	}

	/**
	 * Adds cookies to the request.
	 * @param cookies the cookie "name-to-value" map
	 * @throws IOException
	 */
	public void setCookies (
			final Map<String, String> mapOfCookie) throws IOException {
		if (null == mapOfCookie) return;
		this.mapOfCookie.putAll(mapOfCookie);
	}

	/**
	 * Adds cookies to the request.
	 * @param tStringCookie Array of cookie names and values (cookies[2*i] is a name, cookies[2*i + 1] is a value).
	 * @throws IOException
	 */
	public void setCookies (final String[] tStringCookie) throws IOException {
		if (tStringCookie == null) return;
		for (int i = 0; i < tStringCookie.length - 1; i += 2) {
			this.setCookie(tStringCookie[i], tStringCookie[i+1]);
		}
	}

	/**
	 * Sets a new language.  
	 * @param sLang The new language chosen.
	 */
	public void setLang (final String sLang) {
		ClientHttpRequest.logger.debug("setLang("+sLang+")");
    System.out.println("setLang() " + this.aURLConnection.getRequestMethod());
		this.aURLConnection.setRequestProperty("Accept-Language", sLang);
    System.out.println("setLang() " + this.aURLConnection.getRequestMethod());
	}

	/**
	 * Writes a name in the appropriate format on the output.
	 * @param sName The name to write.
	 * @throws IOException
	 */
	private void writeName (final String sName) throws IOException {
		this.newline();
		this.write("Content-Disposition: form-data; name=\"");
		this.write(sName);
		this.write('"');
	}

	/**
	 * Adds a string parameter to the request.
	 * @param sName Parameter name.
	 * @param sValue Parameter value.
	 * @throws IOException
	 */
	public void setParameter (
			final String sName,
			final String sValue) throws IOException {
		ClientHttpRequest.logger.trace("setParameter(String, String)");
		if (ClientHttpRequest.logger.isDebugEnabled()) {
			ClientHttpRequest.logger.debug("Name : " + sName + ".");
			ClientHttpRequest.logger.debug("Value : " + sValue + ".");
		}
    System.out.println("setParameter() " + this.aURLConnection.getRequestMethod());
		this.boundary();
    System.out.println("setParameter() " + this.aURLConnection.getRequestMethod());
		this.writeName(sName);
    System.out.println("setParameter() " + this.aURLConnection.getRequestMethod());
		this.newline();
    System.out.println("setParameter() " + this.aURLConnection.getRequestMethod());
		this.newline();
    System.out.println("setParameter() " + this.aURLConnection.getRequestMethod());
		this.writeln(sValue);
    System.out.println("setParameter() " + this.aURLConnection.getRequestMethod());
	}

	/**
	 * 
	 * @param aInputStream
	 * @param aOutputStream
	 * @throws IOException
	 */
	private static void pipe (
			final InputStream aInputStream,
			final OutputStream aOutputStream) throws IOException {
		byte[] tByte = new byte[500000];
		int iNbRead;
		int iTotal = 0;
		synchronized (aInputStream) {
			while ((iNbRead = aInputStream.read(tByte, 0, tByte.length)) >= 0) {
				aOutputStream.write(tByte, 0, iNbRead);
				iTotal += iNbRead;
			}
		}
		aOutputStream.flush();
		tByte = null;
	}

	/**
	 * Adds a file parameter to the request.
	 * @param sName Parameter name.
	 * @param sFileName The name of the file.
	 * @param aInputStream input stream to read the contents of the file from
	 * @throws IOException
	 */
	public void setParameter (
			final String sName,
			final String sFileName,
			final InputStream aInputStream) throws IOException {
		ClientHttpRequest.logger.trace("setParameter(String, String, InputStream)");
		if (ClientHttpRequest.logger.isDebugEnabled()) {
			ClientHttpRequest.logger.debug("Name : " + sName + ".");
			ClientHttpRequest.logger.debug("File name : " + sFileName + ".");
			ClientHttpRequest.logger.debug("InputStream : " + aInputStream + ".");
		}
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
		this.boundary();
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
		this.writeName(sName);
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
		this.write("; filename=\"");
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
		this.write(sFileName);
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
		this.write('"');
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
		this.newline();
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
		this.write("Content-Type: ");
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
		String sType = URLConnection.guessContentTypeFromName(sFileName);
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
		if (sType == null) sType = "application/octet-stream";
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
		this.writeln(sType);
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
		this.newline();
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
		ClientHttpRequest.pipe(aInputStream, this.aOutputStream);
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
		this.newline();
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
	}

	/**
	 * adds a file parameter to the request
	 * @param sName parameter name
	 * @param aFile the file to upload
	 * @throws IOException
	 */
	public void setParameter (
			final String sName,
			final File aFile) throws IOException {
    System.out.println("setParameter3() " + this.aURLConnection.getRequestMethod());
		this.setParameter(sName, aFile.getPath(), new FileInputStream(aFile));
    System.out.println("setParameter2() " + this.aURLConnection.getRequestMethod());
	}

	/**
	 * adds a parameter to the request; if the parameter is a File, the file is uploaded, otherwise the string value of the parameter is passed in the request
	 * @param sName parameter name
	 * @param object parameter value, a File or anything else that can be stringified
	 * @throws IOException
	 */
	public void setParameter (
			final String sName,
			final Object aObject) throws IOException {
		if (aObject instanceof File) {
			this.setParameter(sName, (File) aObject);
		} else {
			this.setParameter(sName, aObject.toString());
		}
	}

	/**
	 * adds parameters to the request
	 * @param mapOfParameter "name-to-value" map of parameters; if a value is a file, the file is uploaded, otherwise it is stringified and sent in the request
	 * @throws IOException
	 */
	public void setParameters (final Map mapOfParameter) throws IOException {
		if (mapOfParameter == null) return;
		for (final Iterator aIterator = mapOfParameter.entrySet().iterator(); aIterator.hasNext();) {
			final Map.Entry entry = (Map.Entry) aIterator.next();
			this.setParameter(entry.getKey().toString(), entry.getValue());
		}
	}

	/**
	 * adds parameters to the request
	 * @param tObjectParameter array of parameter names and values (parameters[2*i] is a name, parameters[2*i + 1] is a value); if a value is a file, the file is uploaded, otherwise it is stringified and sent in the request
	 * @throws IOException
	 */
	public void setParameters (
			final Object[] tObjectParameter) throws IOException {
		if (tObjectParameter == null) return;
		for (int i = 0; i < tObjectParameter.length - 1; i += 2) {
			this.setParameter(tObjectParameter[i].toString(), tObjectParameter[i + 1]);
		}
	}

	/**
	 * posts the requests to the server, with all the cookies and parameters that were added
	 * @return input stream with the server response
	 * @throws IOException
	 */
	public InputStream post () throws IOException {
    System.out.println("post() " + this.aURLConnection.getRequestMethod());
		this.boundary();
		this.writeln("--");
    System.out.println("post() " + this.aURLConnection.getRequestMethod());
		this.aOutputStream.close();
    System.out.println("post() " + this.aURLConnection.getRequestMethod());

		return this.aURLConnection.getInputStream();
	}

	/**
	 * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with parameters that are passed in the argument
	 * @param mapOfParameter request parameters
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameters
	 */
	public InputStream post (final Map mapOfParameter) throws IOException {
		this.setParameters(mapOfParameter);
		return this.post();
	}

	/**
	 * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with parameters that are passed in the argument
	 * @param tObjectParameter request parameters
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameters
	 */
	public InputStream post (final Object[] tObjectParameter) throws IOException {
		this.setParameters(tObjectParameter);
		return this.post();
	}

	/**
	 * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with cookies and parameters that are passed in the arguments
	 * @param mapOfCookie request cookies
	 * @param mapOfParameter request parameters
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameters
	 * @see setCookies
	 */
	public InputStream post (
			final Map<String, String> mapOfCookie,
			final Map mapOfParameter) throws IOException {
		this.setCookies(mapOfCookie);
		this.setParameters(mapOfParameter);
		return this.post();
	}

	/**
	 * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with cookies and parameters that are passed in the arguments
	 * @param tStringCookie request cookies
	 * @param tObjectParameter request parameters
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameters
	 * @see setCookies
	 */
	public InputStream post (
			final String[] tStringCookie,
			final Object[] tObjectParameter) throws IOException {
		this.setCookies(tStringCookie);
		this.setParameters(tObjectParameter);
		return this.post();
	}

	/**
	 * post the POST request to the server, with the specified parameter
	 * @param sName parameter name
	 * @param oValue parameter value
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameter
	 */
	public InputStream post (
			final String sName,
			final Object oValue) throws IOException {
		this.setParameter(sName, oValue);
		return this.post();
	}

	/**
	 * post the POST request to the server, with the specified parameters
	 * @param sName1 first parameter name
	 * @param oValue1 first parameter value
	 * @param sName2 second parameter name
	 * @param oValue2 second parameter value
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameter
	 */
	public InputStream post (
			final String sName1,
			final Object oValue1,
			final String sName2,
			final Object oValue2) throws IOException {
		this.setParameter(sName1, oValue1);
		return this.post(sName2, oValue2);
	}

	/**
	 * post the POST request to the server, with the specified parameters
	 * @param sName1 first parameter name
	 * @param oValue1 first parameter value
	 * @param sName2 second parameter name
	 * @param oValue2 second parameter value
	 * @param sName3 third parameter name
	 * @param oValue3 third parameter value
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameter
	 */
	public InputStream post (
			final String sName1,
			final Object oValue1,
			final String sName2,
			final Object oValue2,
			final String sName3,
			final Object oValue3) throws IOException {
		this.setParameter(sName1, oValue1);
		return this.post(sName2, oValue2, sName3, oValue3);
	}

	/**
	 * post the POST request to the server, with the specified parameters
	 * @param sName1 first parameter name
	 * @param oValue1 first parameter value
	 * @param sName2 second parameter name
	 * @param oValue2 second parameter value
	 * @param sName3 third parameter name
	 * @param oValue3 third parameter value
	 * @param sName4 fourth parameter name
	 * @param oValue4 fourth parameter value
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameter
	 */
	public InputStream post (
			final String sName1,
			final Object oValue1,
			final String sName2,
			final Object oValue2,
			final String sName3,
			final Object oValue3,
			final String sName4,
			final Object oValue4) throws IOException {
		this.setParameter(sName1, oValue1);
		return this.post(sName2, oValue2, sName3, oValue3, sName4, oValue4);
	}

	/**
	 * posts a new request to specified URL, with parameters that are passed in the argument
	 * @param mapOfParameter request parameters
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameters
	 */
	public static InputStream post (
			final URL aURL,
			final Map mapOfParameter) throws IOException {
		return new ClientHttpRequest(aURL).post(mapOfParameter);
	}

	/**
	 * posts a new request to specified URL, with parameters that are passed in the argument
	 * @param tObjectParameter request parameters
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameters
	 */
	public static InputStream post (
			final URL aURL,
			final Object[] tObjectParameter) throws IOException {
		return new ClientHttpRequest(aURL).post(tObjectParameter);
	}

	/**
	 * posts a new request to specified URL, with cookies and parameters that are passed in the argument
	 * @param mapOfCookie request cookies
	 * @param mapOfParameter request parameters
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setCookies
	 * @see setParameters
	 */
	public static InputStream post (
			final URL aURL,
			final Map<String, String> mapOfCookie,
			final Map mapOfParameter) throws IOException {
		return new ClientHttpRequest(aURL).post(mapOfCookie, mapOfParameter);
	}

	/**
	 * posts a new request to specified URL, with cookies and parameters that are passed in the argument
	 * @param tStringCookie request cookies
	 * @param tObjectParameter request parameters
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setCookies
	 * @see setParameters
	 */
	public static InputStream post (
			final URL aURL,
			final String[] tStringCookie,
			final Object[] tObjectParameter) throws IOException {
		return new ClientHttpRequest(aURL).post(tStringCookie, tObjectParameter);
	}

	/**
	 * post the POST request specified URL, with the specified parameter
	 * @param name parameter name
	 * @param value parameter value
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameter
	 */
	public static InputStream post (
			final URL aURL,
			final String sName,
			final Object oValue) throws IOException {
		return new ClientHttpRequest(aURL).post(sName, oValue);
	}

	/**
	 * post the POST request to specified URL, with the specified parameters
	 * @param sName1 first parameter name
	 * @param oValue1 first parameter value
	 * @param sName2 second parameter name
	 * @param oValue2 second parameter value
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameter
	 */
	public static InputStream post (
			final URL aURL,
			final String sName1,
			final Object oValue1,
			final String sName2,
			final Object oValue2) throws IOException {
		return new ClientHttpRequest(aURL).post(sName1, oValue1, sName2, oValue2);
	}

	/**
	 * post the POST request to specified URL, with the specified parameters
	 * @param sName1 first parameter name
	 * @param oValue1 first parameter value
	 * @param sName2 second parameter name
	 * @param oValue2 second parameter value
	 * @param sName3 third parameter name
	 * @param oValue3 third parameter value
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameter
	 */
	public static InputStream post (
			final URL aURL,
			final String sName1,
			final Object oValue1,
			final String sName2,
			final Object oValue2,
			final String sName3,
			final Object oValue3) throws IOException {
		return new ClientHttpRequest(aURL).post(
				sName1, oValue1,
				sName2, oValue2,
				sName3, oValue3);
	}

	/**
	 * post the POST request to specified URL, with the specified parameters
	 * @param name1 first parameter name
	 * @param value1 first parameter value
	 * @param name2 second parameter name
	 * @param value2 second parameter value
	 * @param name3 third parameter name
	 * @param value3 third parameter value
	 * @param sName4 fourth parameter name
	 * @param oValue4 fourth parameter value
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameter
	 */
	public static InputStream post (
			final URL aURL,
			final String sName1,
			final Object oValue1,
			final String sName2,
			final Object oValue2,
			final String sName3,
			final Object oValue3,
			final String sName4,
			final Object oValue4) throws IOException {
		return new ClientHttpRequest(aURL).post(
				sName1, oValue1,
				sName2, oValue2,
				sName3, oValue3,
				sName4, oValue4);
	}
}
