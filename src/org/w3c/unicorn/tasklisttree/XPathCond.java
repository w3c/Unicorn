package org.w3c.unicorn.tasklisttree;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.response.Response;

public class XPathCond extends TLTCond {

	//private static final Log logger = LogFactory.getLog(XPathCond.class);
	
	@Override
	public boolean check(UnicornCall unicornCall) {
		Response res = unicornCall.getResponses().get(observer.getID());
		// Testing if there is a matching response in the map and if it is passed
		if (res != null) {
			return res.evaluateXPath(value);
			
			/*String xmlStr = res.getXml().toString();
			DocumentBuilderFactory xmlFact = DocumentBuilderFactory.newInstance();
			xmlFact.setNamespaceAware(false);
			try {
				DocumentBuilder builder = xmlFact.newDocumentBuilder();
				Document doc = builder.parse(new java.io.ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
				String xpathStr = value;
				XPathFactory xpathFact = new XPathFactoryImpl();
				XPath xpath = xpathFact.newXPath();
				XPathExpression xpe = xpath.compile(xpathStr);
				return (Boolean) xpe.evaluate(doc, XPathConstants.BOOLEAN);
			} catch (ParserConfigurationException e) {
				logger.error(e.getMessage(), e);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			} catch (SAXException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			} catch (XPathExpressionException e) {
				logger.error(e.getMessage(), e);
			}*/
		}
		
		return false;
	}

}
