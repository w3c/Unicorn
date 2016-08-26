// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.request.TrustAllManager;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.Property;

public class URIInputParameter extends InputParameter {
	
	private String uri;
	
	private int connectTimeOut;
	
	private static SSLContext sc;
	
	static {
		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[]{new TrustAllManager()}, new java.security.SecureRandom());
			HostnameVerifier hv = new HostnameVerifier() {
				@Override 
				public boolean verify(String hostname, SSLSession session) {return true;}
			};
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public URIInputParameter(String uri) {
		this.uri = uri;
		if (Property.get("DOCUMENT_CONNECT_TIMEOUT") != null)
			connectTimeOut = Integer.parseInt(Property.get("DOCUMENT_CONNECT_TIMEOUT"));
		else 
			connectTimeOut = 0;
	}
	
	@Override
	public void check(ArrayList<Message> messages) throws UnicornException {
		URL docUrl = null;
		try {
			if (uri == null || uri.equals(""))
				throw new UnicornException(Message.ERROR, "$message_empty_uri");
			
			if (uri.equals("referer"))
				throw new UnicornException();
			
			//Pattern urlPattern = Pattern.compile("^(https?)://([A-Z0-9][A-Z0-9_-]*)(\\.[A-Z0-9][A-Z0-9_-]*)+(:(\\d+))?([/#]\\p{ASCII}*)?", Pattern.CASE_INSENSITIVE);
			Pattern protocolPattern = Pattern.compile("^\\p{Alpha}*://.*");
			try {
				docUrl = new URL(uri);
			} catch (MalformedURLException e) {
				if (protocolPattern.matcher(uri).matches())
					throw e;
				else {
					uri = "http://" + uri;
					docUrl = new URL(uri);
				} 
			}
			
			if (!docUrl.getProtocol().equals("http") && !docUrl.getProtocol().equals("https"))
				throw new UnicornException(Message.ERROR, "$message_unsupported_protocol", null, StringEscapeUtils.escapeHtml(docUrl.getProtocol()));
			
			if (!"true".equals(Property.get("ACCEPT_LOCAL_ADDRESSES"))) {
				InetAddress add = InetAddress.getByName(docUrl.getHost());
				if (add.isSiteLocalAddress() || add.isLoopbackAddress())
					throw new UnicornException(Message.ERROR, "$message_local_address_provided");
			}
			
			HttpURLConnection con = (HttpURLConnection) docUrl.openConnection();
			con.setRequestProperty("User-agent", "W3C_Unicorn/1.0 (http://validator.w3.org/services)");
			con.setConnectTimeout(connectTimeOut);
			con.setRequestMethod("GET");
			try {
				con.connect();
			} catch (SSLException e) {
				((HttpsURLConnection) con).setSSLSocketFactory(sc.getSocketFactory());
				con.connect();
				Message message = new Message(Message.WARNING, "$message_ssl_warning", e.getClass() + "\n" + e.getMessage());
				message.setEvaluateContent(false);
				messages.add(message);
			}
			int responseCode = con.getResponseCode();
			switch (responseCode) {
			case HttpURLConnection.HTTP_UNAUTHORIZED:
				throw new UnicornException(Message.ERROR, "$message_unauthorized_access");
			case HttpURLConnection.HTTP_NOT_FOUND:
				throw new UnicornException(Message.ERROR, "$message_document_not_found");
			}
			String sMimeType = con.getContentType();
			if (sMimeType != null) {
				sMimeType = sMimeType.split(";")[0];
				mimeType = new MimeType(sMimeType);
			} else {
				messages.add(new Message(Message.WARNING, "$message_could_not_guess_mimetype"));
			}
			inputModule = new URIInputModule(mimeType, uri);
		} catch (MalformedURLException e) {
			throw new UnicornException(Message.ERROR, "$message_invalid_url_syntax", e.getMessage(), StringEscapeUtils.escapeHtml(uri));
		} catch (MimeTypeParseException e) {
			throw new UnicornException(Message.ERROR, "$message_invalid_mime_type");
		} catch (UnknownHostException e) { 
			throw new UnicornException(Message.ERROR, "$message_unknown_host" , null, StringEscapeUtils.escapeHtml(docUrl.getHost()));
		} catch (ConnectException e) {
			throw new UnicornException(Message.ERROR, "$message_connect_exception");
		} catch (SocketTimeoutException e) {
			if (e.getMessage().contains("connect timed out")) {
				throw new UnicornException(Message.ERROR, "$message_connect_exception");
			} else {
				throw new UnicornException(new Message(e));
			}
		} catch (SSLHandshakeException e) {
			throw new UnicornException(Message.ERROR, "$message_ssl_exception");
		} catch (IOException e) {
			throw new UnicornException(new Message(e));
		}
	}

	@Override
	public String getDocumentName() {
		return StringEscapeUtils.escapeHtml(uri);
	}

	@Override
	public EnumInputMethod getInputMethod() {
		return EnumInputMethod.URI;
	}

}
