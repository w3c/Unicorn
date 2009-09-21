package org.w3c.unicorn.tasklisttree;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.response.Response;

import com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl;

public class XPathCond extends TLTCond {

	@Override
	public boolean check(UnicornCall unicornCall) throws Exception {
		Response res = unicornCall.getResponses().get(observer.getID());
		// Testing if there is a matching response in the map and if it is passed
		if (res != null) {
			String xmlStr = res.getXml().toString();
			DocumentBuilderFactory xmlFact = DocumentBuilderFactory.newInstance();
			xmlFact.setNamespaceAware(false);
			DocumentBuilder builder = xmlFact.newDocumentBuilder();
			Document doc = builder.parse(new java.io.ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
			String xpathStr = value;
			XPathFactory xpathFact = new XPathFactoryImpl();
			XPath xpath = xpathFact.newXPath();
			XPathExpression xpe = xpath.compile(xpathStr);
			return (Boolean) xpe.evaluate(doc, XPathConstants.BOOLEAN);
		}
		
		return false;
	}

}
