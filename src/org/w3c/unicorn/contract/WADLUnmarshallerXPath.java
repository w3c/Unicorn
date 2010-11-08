// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
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
import org.w3c.unicorn.util.LocalizedString;
import org.xml.sax.SAXException;

/**
 * WADLUnmarshallerXPath<br />
 * Created: May 22, 2006 6:01:14 PM<br />
 * 
 * @author Jean-Guilhem ROUEL
 */
public class WADLUnmarshallerXPath implements WADLUnmarshaller {

	/**
	 * Object for complex logging purpose
	 */
	private static final Log logger = LogFactory.getLog(WADLUnmarshaller.class);

	/**
	 * Namespace for the context
	 */
	private static NamespaceContext aNamespaceContext = new NamespaceContext() {
		public String getNamespaceURI(final String sPrefix) {
			if ("xs".equals(sPrefix)) {
				return "http://www.w3.org/2001/XMLSchema";
			} else if ("uco".equals(sPrefix)) {
				return "http://www.w3.org/unicorn/observationresponse";
			} else {
				return null;
			}
		}
		public String getPrefix(final String sNamespaceURI) {
			if ("http://www.w3.org/2001/XMLSchema".equals(sNamespaceURI)) {
				return "xs";
			} else if ("http://www.w3.org/unicorn/observationresponse"
					.equals(sNamespaceURI)) {
				return "uco";
			} else {
				return null;
			}
		}
		public Iterator<String> getPrefixes(final String sNamespaceURI) {
			return null;
		}
	};

	/**
	 * List of the different call method
	 */
	private List<CallMethod> listOfCallMethod = new ArrayList<CallMethod>();

	/**
	 * Factory to build Document
	 */
	private DocumentBuilderFactory aDocumentBuilderFactory;

	/**
	 * Document builder
	 */
	private DocumentBuilder aDocumentBuilder;

	/**
	 * Document XML
	 */
	private Document aDocument;

	/**
	 * Path for the document
	 */
	private XPath aXPath;

	/**
	 * Description of a observer to complete with information from a WADL file.
	 */
	private String sID = new String();

	private LocalizedString aLocalizedStringName = new LocalizedString();

	private LocalizedString aLocalizedStringDescription = new LocalizedString();

	private LocalizedString aLocalizedStringHelpLocation = new LocalizedString();

	private LocalizedString aLocalizedStringProvider = new LocalizedString();

	private List<MimeType> listOfMimeType = new ArrayList<MimeType>();

	private String responseType;

	/**
	 * name of parameter lang if observer has one
	 */
	private String nameOfLangParameter = null;
	
	private String nameOfOutputParameter = null;

	/**
	 * Map of different input method handle by the observer.
	 */
	private Map<EnumInputMethod, InputMethod> mapOfInputMethod = new LinkedHashMap<EnumInputMethod, InputMethod>();

	private String indexURI;
	
	/**
	 * Create the object to Unmarshall WADL with XPATH
	 * 
	 * @throws ParserConfigurationException
	 */
	public WADLUnmarshallerXPath() throws ParserConfigurationException {
		logger.trace("Constructor");

		this.aDocumentBuilderFactory = DocumentBuilderFactory.newInstance();

		this.aDocumentBuilder = this.aDocumentBuilderFactory
				.newDocumentBuilder();
		this.aXPath = XPathFactory.newInstance().newXPath();
		this.aXPath
				.setNamespaceContext(WADLUnmarshallerXPath.getNamespaceContext());
	}

	/**
	 * Get the document from an URL
	 * 
	 * @throws SAXException
	 */
	public void addURL(final URL aURL) throws IOException {

		logger.trace("addURL");
		logger.trace("URL : " + aURL + ".");

		try {
			this.aDocument = this.aDocumentBuilder.parse(aURL.openStream());
		} catch (SAXException e) {
			logger.error(
					"Parsing error with SAX in WADLUnmarshaller", e);
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.unicorn.contract.WADLUnmarshaller#unmarshal(java.net.URL)
	 */
	public void unmarshal() throws XPathExpressionException,
			ParserConfigurationException, SAXException, IOException,
			MimeTypeParseException {
		logger.trace("unmarshal");
		this.parseDocsHeader();
		this.parseMethods();
	}

	/**
	 * Parse the header of the WADL file
	 * 
	 * @throws XPathExpressionException
	 *             error in the XPATH browsing of the document
	 * @throws MimeTypeParseException
	 *             error in the mime-type of the document
	 */
	private void parseDocsHeader() throws XPathExpressionException,
			MimeTypeParseException {
		final Node aNodeResource = this.aDocument.getElementsByTagName(
				"resources").item(0);
		XPathExpression aXPathExpression = this.aXPath
				.compile("//resource/doc");
		NodeList aNodeListResult = (NodeList) aXPathExpression.evaluate(
				aNodeResource, XPathConstants.NODESET);
		for (int i = 0; i < aNodeListResult.getLength(); i++) {
			Node nodeDoc = aNodeListResult.item(i);

			String vText = nodeDoc.getTextContent();

			// browse the attributes of a document
			String vTitle = null;
			String vLang = null;
			NamedNodeMap nnm = nodeDoc.getAttributes();
			for (int j = 0; j < nnm.getLength(); j++) {
				String attrName = nnm.item(j).getNodeName();
				String attrValue = nnm.item(j).getNodeValue();
				if ("title".equals(attrName)) {
					vTitle = attrValue;
				} else if ("xml:lang".equals(attrName)) {
					vLang = attrValue;
				}
			}

			if ("name".equals(vTitle)) {
				aLocalizedStringName.addLocalization(vLang, vText);
			} else if ("description".equals(vTitle)) {
				aLocalizedStringDescription.addLocalization(vLang, vText);
			} else if ("help".equals(vTitle)) {
				aLocalizedStringHelpLocation.addLocalization(vLang, vText);
			} else if ("provider".equals(vTitle)) {
				aLocalizedStringProvider.addLocalization(vLang, vText);
			} else if ("paramLang".equals(vTitle)) {
				nameOfLangParameter = vText;
			} else if ("paramOutput".equals(vTitle)) {
				nameOfOutputParameter = vText;
			} else if ("mimetype".equals(vTitle)) {
				listOfMimeType.add(new MimeType(vText));
			} else if ("reference".equals(vTitle)) {
				sID = vText;
			} else if ("responseType".equals(vTitle)) {
				responseType = vText;
			}
		}
	}

	/**
	 * Parse all the methods of the document
	 * 
	 * @throws ParserConfigurationException
	 *             Error in the configuration of the parser
	 * @throws SAXException
	 *             error in the XML DOM
	 * @throws IOException
	 *             error in the input/output
	 * @throws XPathExpressionException
	 *             error in the evaluation of the XPATH query
	 */
	private void parseMethods() throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException {
		logger.trace("parseMethods");

		// base uri
		final Node aNodeResource = this.aDocument.getElementsByTagName(
				"resources").item(0);
		final String sBaseURI = aNodeResource.getAttributes().getNamedItem(
				"base").getNodeValue();
		
		indexURI = sBaseURI;

		final NodeList aNodeListMethod = this.aDocument
				.getElementsByTagName("method");

		for (int i = 0; i < aNodeListMethod.getLength(); i++) {
			final Node aNodeMethod = aNodeListMethod.item(i);

			ArrayList<CallParameter> callParameters = new ArrayList<CallParameter>();

			// URI of the resource (will be appended to the base URI)
			final String sResourceURI;
			sResourceURI = aNodeMethod.getParentNode().getAttributes()
					.getNamedItem("path").getNodeValue();

			// Type : GET/POST and id of the method
			final NamedNodeMap aNamedNodeMapAttribute = aNodeMethod
					.getAttributes();
			final String sName = aNamedNodeMapAttribute.getNamedItem("name")
					.getNodeValue();
			final boolean bPost = "POST".equals(sName.trim());
			final String sMethodID = aNamedNodeMapAttribute.getNamedItem("id")
					.getNodeValue().trim();

			// Query variables
			XPathExpression aXPathExpression = this.aXPath
					.compile("//method[@id='" + sMethodID + "']//param");
			NodeList aNodeListResult = (NodeList) aXPathExpression.evaluate(
					aNodeMethod, XPathConstants.NODESET);

			// iterate over param list
			for (int j = 0; j < aNodeListResult.getLength(); j++) {
				final NamedNodeMap aNamedNodeMap = aNodeListResult.item(j)
						.getAttributes();
				final CallParameter aCallParameter = new CallParameter();

				// iterate over attributes
				for (int k = 0; k < aNamedNodeMap.getLength(); k++) {
					final Node aNodeCurrentAttribute = aNamedNodeMap.item(k);

					final String sAttributeName = aNodeCurrentAttribute
							.getNodeName();
					final String sAttributeValue = aNodeCurrentAttribute
							.getNodeValue();

					if ("name".equals(sAttributeName)) {
						aCallParameter.setName(sAttributeValue);
					} else if ("required".equals(sAttributeName)) {
						aCallParameter.setRequired("true"
								.equals(sAttributeValue));
					} else if ("repeating".equals(sAttributeName)) {
						aCallParameter.setRepeating("true"
								.equals(sAttributeValue));
					} else if ("fixed".equals(sAttributeName)) {
						aCallParameter.setFixed(sAttributeValue);
					} else if ("style".equals(sAttributeName)) {
						aCallParameter.setStyle(sAttributeValue);
					} else if ("id".equals(sAttributeName)) {
						aCallParameter.setID(sAttributeValue);
					} else if ("path".equals(sAttributeName)) {
						aCallParameter.setPath(sAttributeValue);
					} else if ("default".equals(sAttributeName)) {
						aCallParameter.setDefaultValue(sAttributeValue);
					}
				} // iterate over attributes

				// read option type

				XPathExpression aOptionXPathExpression = this.aXPath
						.compile("//method[@id='" + sMethodID
								+ "']//request//param[@name='"
								+ aCallParameter.getName() + "']//option");
				NodeList aOptionNodeListResult = (NodeList) aOptionXPathExpression
						.evaluate(aNodeMethod, XPathConstants.NODESET);

				for (int k = 0; k < aOptionNodeListResult.getLength(); k++) {
					aCallParameter.addOption(new Option(aOptionNodeListResult.item(k)
							.getAttributes().item(0).getNodeValue(), aOptionNodeListResult.item(k).getTextContent()));
				}

				callParameters.add(aCallParameter);

			} // iterate over query_variable list

			final CallMethod aCallMethod = new CallMethod(new URL(sBaseURI
					+ sResourceURI), bPost, sName, sMethodID,
					callParameters);
			this.listOfCallMethod.add(aCallMethod);

			// fill mapOfInputMethod

			NodeList listChildMethod = aNodeMethod.getChildNodes();
			String sInputMethod = null;
			String sInputParamName = null;
			for (int j = 0; j < listChildMethod.getLength(); j++) {
				Node childMethod = listChildMethod.item(j);
				if ("doc".equals(childMethod.getNodeName())) {
					String firstAttrName = childMethod.getAttributes().item(0)
							.getNodeName();
					if ("title".equals(firstAttrName)) {
						String firstAttrValue = childMethod.getAttributes()
								.item(0).getNodeValue();
						if ("inputMethod".equals(firstAttrValue)) {
							sInputMethod = childMethod.getTextContent();
						} else if ("inputParamName".equals(firstAttrValue)) {
							sInputParamName = childMethod.getTextContent();
						}
					}
				}
			}

			InputMethod aInputMethod = new InputMethod(sInputMethod);
			aInputMethod.setCallMethod(aCallMethod);
			aInputMethod.setCallParameter(aCallMethod
					.getCallParameterByName(sInputParamName));
			if ("URI".equals(sInputMethod)) {
				this.mapOfInputMethod.put(EnumInputMethod.URI, aInputMethod);
			} else if ("UPLOAD".equals(sInputMethod)) {
				this.mapOfInputMethod.put(EnumInputMethod.UPLOAD, aInputMethod);
			} else if ("DIRECT".equals(sInputMethod)) {
				this.mapOfInputMethod.put(EnumInputMethod.DIRECT, aInputMethod);
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.unicorn.contract.WADLUnmarshaller#getMethods()
	 */
	public List<CallMethod> getListOfCallMethod() {
		return this.listOfCallMethod;
	}

	public LocalizedString getDescription() {
		return this.aLocalizedStringDescription;
	}

	public LocalizedString getHelpLocation() {
		return this.aLocalizedStringHelpLocation;
	}

	public String getID() {
		return this.sID;
	}

	public Map<EnumInputMethod, InputMethod> getMapOfInputMethod() {
		return this.mapOfInputMethod;
	}

	public LocalizedString getName() {
		return this.aLocalizedStringName;
	}

	public String getNameOfLangParameter() {
		return this.nameOfLangParameter;
	}

	public LocalizedString getProvider() {
		return this.aLocalizedStringProvider;
	}

	public String getResponseType() {
		return responseType;
	}

	public List<MimeType> getSupportedMimeTypes() {
		return this.listOfMimeType;
	}

	/**
	 * Main method of the class only for testing purpose
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		final WADLUnmarshaller t = new WADLUnmarshallerXPath();
		t.addURL(new URL("http://localhost/css.wadl"));
		t.unmarshal();
		System.out.println(t.getID());

		System.out.println(t.getMapOfInputMethod());
		System.out.println("***************************************");
		for (InputMethod im : t.getMapOfInputMethod().values()) {
			System.out.println(im.getCallParameter());
			System.out.println("---------------------------------");
		}
	}

	public static void setNamespaceContext(NamespaceContext aNamespaceContext) {
		WADLUnmarshallerXPath.aNamespaceContext = aNamespaceContext;
	}

	public static NamespaceContext getNamespaceContext() {
		return aNamespaceContext;
	}

	public String getNameOfOutputParameter() {
		return nameOfOutputParameter;
	}

	public void setNameOfOutputParameter(String nameOfOutputParameter) {
		this.nameOfOutputParameter = nameOfOutputParameter;
	}

	public String getIndexUri() {
		return indexURI;
	}

}
