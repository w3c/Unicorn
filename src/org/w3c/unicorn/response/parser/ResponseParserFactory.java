package org.w3c.unicorn.response.parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.unicorn.Framework;
import org.w3c.unicorn.response.Response;
import org.xml.sax.SAXException;

/**
 * Factory to create ResponseParsers
 * 
 */
public class ResponseParserFactory {

	/**
	 * Creates a parser corresponding to the type of the response.
	 * 
	 * @param responseType
	 *            The type of the response.
	 * @return The created parser.
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	public static ResponseParser createResponseParser(String responseType)
			throws SAXException, ParserConfigurationException, IOException {
		ResponseParser rsp = Framework.mapOfReponseParser.get(responseType);
		if (rsp == null) {
			rsp = new DefaultParser();
		}
		return rsp;
	}

	/**
	 * Parses the string thanks to a response parser and returns the response.
	 * 
	 * @param r
	 *            the string
	 * @param responseType
	 *            The type of the response.
	 * @return The response parsed corresponding to the input.
	 * @throws Exception 
	 */
	public static Response parse(String r, String responseType) throws Exception {
		try {
			Response response = createResponseParser(responseType).parse(r);
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
