package org.w3c.unicorn.response.parser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.unicorn.Framework;
import org.w3c.unicorn.response.Response;
import org.xml.sax.SAXException;

public class ResponseParserFactory {
	public static ResponseParser createResponseParser(String responseType) throws SAXException, ParserConfigurationException, IOException {
		ResponseParser rsp = Framework.mapOfReponseParser.get(responseType);
		if (rsp==null)
			rsp = new DefaultParser();
		return rsp;
	}
	
	public static Response parse(InputStream is, String responseType) {
		try {
			Response response = createResponseParser(responseType).parse(is);
			return response;
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	} 
}
