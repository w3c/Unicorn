package org.w3c.unicorn.input;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.net.ssl.SSLException;

import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.exceptions.UnicornException;
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
			URLConnection con = docUrl.openConnection();
			con.setConnectTimeout(connectTimeOut);
			String sMimeType = con.getContentType();
			sMimeType = sMimeType.split(";")[0];
			mimeType = new MimeType(sMimeType);
			inputModule = new URIInputModule(mimeType, uri);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (MimeTypeParseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) { 
			e.printStackTrace();
		} catch (SSLException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			if (e.getMessage().contains("Read timed out"))
				System.out.println("a");
			if (e.getMessage().contains("connect timed out"))
				System.out.println("b");
		} catch (IOException e) {
			try {
				int responseCode;
				if (docUrl != null) {
					responseCode = ((HttpURLConnection) docUrl.openConnection()).getResponseCode();
					switch (responseCode) {
					case HttpURLConnection.HTTP_UNAUTHORIZED:
						System.out.println("HTTP_UNAUTHORIZED");
						break;
					}
				}
			} catch (Exception e2) {
				System.out.println("e2");
				e2.printStackTrace();
			}
			e.printStackTrace();
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
