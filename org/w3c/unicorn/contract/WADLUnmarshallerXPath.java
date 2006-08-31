// $Id: WADLUnmarshallerXPath.java,v 1.1.1.1 2006-08-31 09:09:21 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * WADLUnmarshallerXPath<br />
 * Created: May 22, 2006 6:01:14 PM<br />
 * @author Jean-Guilhem ROUEL
 */
public class WADLUnmarshallerXPath implements WADLUnmarshaller {

	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.contract");

	private static NamespaceContext aNamespaceContext;

	private Map<String, List<String>> mapOfListOfStringType;
	private List<CallMethod> listOfCallMethod;

	private DocumentBuilderFactory aDocumentBuilderFactory;
	private DocumentBuilder aDocumentBuilder;
	private Document aDocument;
	private XPath aXPath;

	public WADLUnmarshallerXPath () throws ParserConfigurationException {
		WADLUnmarshallerXPath.logger.trace("Constructor");

		this.mapOfListOfStringType = new LinkedHashMap<String, List<String>>();
		this.listOfCallMethod = new ArrayList<CallMethod>();

		this.aDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
		this.aDocumentBuilder = this.aDocumentBuilderFactory.newDocumentBuilder();
		this.aXPath = XPathFactory.newInstance().newXPath();

		this.aXPath.setNamespaceContext(WADLUnmarshallerXPath.aNamespaceContext);
	}

	public void addURL (final URL aURL) throws SAXException, IOException {
		WADLUnmarshallerXPath.logger.trace("addURL");
		if (WADLUnmarshallerXPath.logger.isDebugEnabled()) {
			WADLUnmarshallerXPath.logger.debug("URL : " + aURL + ".");
		}
		this.aDocument = this.aDocumentBuilder.parse(aURL.openStream());
	}

	/* (non-Javadoc)
	 * @see org.w3c.unicorn.contract.WADLUnmarshaller#unmarshal(java.net.URL)
	 */
	public void unmarshal () throws
	XPathExpressionException,
	ParserConfigurationException,
	SAXException,
	IOException {
		WADLUnmarshallerXPath.logger.trace("unmarshal");

		this.parseTypes();
		this.parseMethods();
	}

	private void parseMethods () throws
	ParserConfigurationException,
	SAXException,
	IOException,
	XPathExpressionException {
		WADLUnmarshallerXPath.logger.trace("parseMethods");

		// base uri
		final Node aNodeResource = this.aDocument.getElementsByTagName("resources").item(0);
		final String sBaseURI = aNodeResource.getAttributes().getNamedItem("base").getNodeValue();

		final NodeList aNodeListMethod = this.aDocument.getElementsByTagName("method");

		for (int i = 0; i < aNodeListMethod.getLength(); i++) {
			final Node aNodeMethod = aNodeListMethod.item(i);

			final Map<String, CallParameter> mapOfCallParameter;
			mapOfCallParameter = new LinkedHashMap<String, CallParameter>();

			// URI of the resource (will be appended to the base URI)
			final String sResourceURI;
			sResourceURI = aNodeMethod.getParentNode().getAttributes().getNamedItem("uri").getNodeValue();

			// Type : GET or POST and id of the method
			final NamedNodeMap aNamedNodeMapAttribute = aNodeMethod.getAttributes();
			final String sName = aNamedNodeMapAttribute.getNamedItem("name").getNodeValue();
			final boolean bPost = "POST".equals(sName.trim());
			final String sMethodID = aNamedNodeMapAttribute.getNamedItem("id").getNodeValue().trim();

			// Query variables
			final XPathExpression aXPathExpression;
			aXPathExpression = this.aXPath.compile("//method[@id='" + sMethodID + "']//query_variable");
			final NodeList aNodeListResult;
			aNodeListResult = (NodeList) aXPathExpression.evaluate(aNodeMethod, XPathConstants.NODESET);

			// iterate over query_variable list
			for (int j = 0; j < aNodeListResult.getLength(); j++) {
				final NamedNodeMap aNamedNodeMap = aNodeListResult.item(j).getAttributes();
				final CallParameter aCallParameter = new CallParameter();

				// iterate over attributes
				for (int k = 0; k < aNamedNodeMap.getLength(); k++) {
					final Node aNodeCurrentAttribute = aNamedNodeMap.item(k);

					final String sAttributeName = aNodeCurrentAttribute.getNodeName();
					final String sAttributeValue = aNodeCurrentAttribute.getNodeValue();

					if ("name".equals(sAttributeName)) {
						aCallParameter.setName(sAttributeValue);
					}
					else if ("type".equals(sAttributeName)) {						
						if (sAttributeValue.equals("xs:string")) {							
							aCallParameter.addValue("");
						}
						else {
							aCallParameter.setPossibleValues(
									this.mapOfListOfStringType.get(sAttributeValue));
						}
					}
					else if ("required".equals(sAttributeName)) {
						aCallParameter.setRequired("true".equals(sAttributeValue));
					}
					else if ("repeating".equals(sAttributeName)) {
						aCallParameter.setRepeating("true".equals(sAttributeValue));
					}
					else if ("fixed".equals(sAttributeName)) {
						aCallParameter.setFixed(sAttributeValue);        	    
					}        	
				} // iterate over attributes

				mapOfCallParameter.put(new String(aCallParameter.getName()), aCallParameter);
			} // iterate over query_variable list

			final CallMethod aCallMethod = new CallMethod(
					new URL(sBaseURI + sResourceURI),
					bPost,
					sName,
					sMethodID,
					mapOfCallParameter);
			this.listOfCallMethod.add(aCallMethod);
		}
	}

	/**
	 * 
	 */
	private void parseTypes() {
		WADLUnmarshallerXPath.logger.trace("parseTypes");

		final NodeList aNodeList = this.aDocument.getElementsByTagName("xs:choice");

		for (int k = 0; k < aNodeList.getLength(); k++) {
			final Node aNode = aNodeList.item(k);
			final String sType = aNode.getParentNode().getParentNode().getAttributes().getNamedItem("name").getNodeValue();
			final NodeList aNodeListChildren = aNode.getChildNodes();

			// Values of the current type
			final ArrayList<String> listOfValue = new ArrayList<String>();	    
			for (int l = 0; l < aNodeListChildren.getLength(); l++) {
				final Node aNodeChild = aNodeListChildren.item(l);
				if (aNodeChild.getNodeType() == Node.ELEMENT_NODE) {
					listOfValue.add(aNodeChild.getAttributes().getNamedItem("name").getNodeValue());
				}
			}
			this.mapOfListOfStringType.put(sType, listOfValue);
		}
	}

	/* (non-Javadoc)
	 * @see org.w3c.unicorn.contract.WADLUnmarshaller#getMethods()
	 */
	public List<CallMethod> getListOfCallMethod() {
		return this.listOfCallMethod;
	}

	public static void main (final String[] args) throws Exception {
		final WADLUnmarshaller t = new WADLUnmarshallerXPath();
		t.addURL(new URL("http://w3cstag8/~jean/xml/css-validator.wadl"));
		t.unmarshal();
	}

	static {
		WADLUnmarshallerXPath.aNamespaceContext = new NamespaceContext() {
			public String getNamespaceURI(final String sPrefix) {
				if ("xs".equals(sPrefix)) {
					return "http://www.w3.org/2001/XMLSchema";
				}
				else if ("uco".equals(sPrefix)) {
					return "http://www.w3.org/unicorn/observationresponse";
				}
				else {
					return null;
				}
			}

			public String getPrefix (final String sNamespaceURI) {
				if ("http://www.w3.org/2001/XMLSchema"
						.equals(sNamespaceURI)) {
					return "xs";
				}
				else if ("http://www.w3.org/unicorn/observationresponse"
						.equals(sNamespaceURI)) {
					return "uco";
				}
				else {
					return null;
				}
			}

			public Iterator getPrefixes (final String sNamespaceURI) {
				return null;
			}

		};
	}
}
