package org.w3c.unicorn.input;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.net.ssl.SSLException;

import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.Property;

public class URIInputParameter extends InputParameter {
	
	private String uri;
	
	private int connectTimeOut = Integer.parseInt(Property.get("DOCUMENT_CONNECT_TIMEOUT"));
	
	public URIInputParameter(String uri) {
		this.uri = uri;
	}
	
	@Override
	public void check() throws UnicornException {
		URL docUrl = null;
		try {
			docUrl = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) docUrl.openConnection();
			con.setConnectTimeout(connectTimeOut);
			con.connect();
			
			Message message;
			int responseCode = con.getResponseCode();
			switch (responseCode) {
			case HttpURLConnection.HTTP_UNAUTHORIZED:
				message = new Message(Message.Level.ERROR, "$message_unauthorized_access", null);
				throw new UnicornException(message);
			case HttpURLConnection.HTTP_NOT_FOUND:
				message = new Message(Message.Level.ERROR, "$message_document_not_found", null);
				throw new UnicornException(message);
			}
			
			String sMimeType = con.getContentType();
			sMimeType = sMimeType.split(";")[0];
			mimeType = new MimeType(sMimeType);
			inputModule = new URIInputModule(mimeType, uri);
		} catch (MalformedURLException e) {
			Message message = new Message(Message.Level.ERROR, "$message_invalid_url_syntax " + uri, null);
			throw new UnicornException(message);
		} catch (MimeTypeParseException e) {
			Message message = new Message(Message.Level.ERROR, "$message_invalid_mime_type", null);
			throw new UnicornException(message);
		} catch (UnknownHostException e) { 
			Message message = new Message(Message.Level.ERROR, "$message_unknown_host", null);
			throw new UnicornException(message);
		} catch (SSLException e) {
			Message message = new Message(Message.Level.ERROR, "$message_ssl_exception", null);
			throw new UnicornException(message);
		} catch (ConnectException e) {
			Message message = new Message(Message.Level.ERROR, "$message_connect_exception", null);
			throw new UnicornException(message);
		} catch (SocketTimeoutException e) {
			if (e.getMessage().contains("connect timed out")) {
				Message message = new Message(Message.Level.ERROR, "$message_connect_exception", null);
				throw new UnicornException(message);
			} else {
				Message message = new Message(e);
				throw new UnicornException(message);
			}
		} catch (IOException e) {
			Message message = new Message(e);
			throw new UnicornException(message);
		}
	}

	@Override
	public String getDocumentName() {
		return uri;
	}

	@Override
	public EnumInputMethod getInputMethod() {
		return EnumInputMethod.URI;
	}

}
