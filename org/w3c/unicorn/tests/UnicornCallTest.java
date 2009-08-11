package org.w3c.unicorn.tests;

import java.io.InputStream;
import java.util.Iterator;

import javax.activation.MimeType;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.input.InputFactory;
import org.w3c.unicorn.input.InputModule;
import org.w3c.unicorn.request.Request;
import org.w3c.unicorn.response.Response;
import org.xml.sax.InputSource;

public class UnicornCallTest {

	public static Boolean evaluer(InputStream stream, String expression) {
		Boolean b = null;
		try {
			// cr�ation de la source
			InputSource source = new InputSource(stream);

			// cr�ation du XPath
			XPathFactory fabrique = XPathFactory.newInstance();

			XPath xpath = fabrique.newXPath();

			// test du namespace manuel

			NamespaceContext namespace = new NamespaceContext() {
				public String getNamespaceURI(String prefix) {
					if ("observationresponse".equals(prefix)) {
						return "http://www.w3.org/unicorn/observationresponse";
					} else {
						return null;
					}
				}

				public String getPrefix(String namespaceURI) {
					if ("http://www.w3.org/unicorn/observationresponse	"
							.equals(namespaceURI)) {
						return "observationresponse";
					} else {
						return null;
					}
				}

				public Iterator getPrefixes(String namespaceURI) {
					return null;
				}
			};
			xpath.setNamespaceContext(namespace);

			// �valuation de l'expression XPath
			XPathExpression exp = xpath.compile(expression);

			b = (Boolean) exp.evaluate(source, XPathConstants.BOOLEAN);
			System.out.println("namespace context : "
					+ xpath.getNamespaceContext());
		} catch (XPathExpressionException xpee) {
			xpee.printStackTrace();
		}
		return b;
	}

	public static void main(String[] args) {

		try {

			// First, the XML document

			/*
			 * String xmlStr =
			 *  "<?xml version=\"1.0\" ?>\n" +
			 *  "<Sales xmlns=\"http://www.davber.com/sales-format\">\n" +
			 *  "<Customer name=\"CostCo, Inc.\">\n" +
			 *  "<ord:Order xmlns:ord=\"http://www.davber.com/order-format\"
			 * price=\"12000\">\n" +
			 *  "<ord:Description>A bunch of stuff" +
			 *  "</ord:Description>\n" +
			 *  "</ord:Order>\n" +
			 *  "</Customer>\n" +
			 *  "</Sales>\n";
			 * 
			 */

			InputModule inputMod = InputFactory.createInputModule(
					(new MimeType()), EnumInputMethod.URI, "http://www.w3.org");
			Request req = Request.createRequest(inputMod,
					"http://validator.w3.org/check", "uri", false, "ucn");
			req.setLang("en");
			req.addParameter("output", "ucn");
			System.out.println("request created");
			System.out.println(req.getResponseType());
			Response res = req.doRequest();
			System.out.println("request done");

			DocumentBuilderFactory xmlFact = DocumentBuilderFactory
					.newInstance();
			xmlFact.setNamespaceAware(false);
			DocumentBuilder builder = xmlFact.newDocumentBuilder();
			Document doc = builder.parse(res.getXml().toString());

			// Now the XPath expression

			String xpathStr = "//false";

			XPathFactory xpathFact =

			XPathFactory.newInstance();

			XPath xpath = xpathFact.newXPath();

			String result = xpath.evaluate(xpathStr, doc);
			XPathExpression xpe = xpath.compile(xpathStr);
			boolean b = (Boolean) xpe.evaluate(doc, XPathConstants.BOOLEAN);
			System.out.println(b);
			System.out.println("xpath : " + (!result.equals("")));
			// System.out.println("XPath result is \"" +
			// result + "\"");

		}

		catch (Exception ex) {

			ex.printStackTrace();

		}

	}

}

/*
 * public static void main(String[] args) { try { System.out.println("Premier
 * test : URL"); // URL url = new //
 * URL("http://www.pms.ifi.lmu.de/forschung/xpath-eval.html");
 *  // String expression = "//category"; // String expression =
 * "//cadevraitetrefalse"; // (//doctype eq '-//W3C//DTD XHTML 1.0 Strict//EN') //
 * InputStream is = url.openStream(); // InputStreamReader isr = new
 * InputStreamReader(is); // BufferedReader br = new BufferedReader(isr); String
 * s = ""; // while ((s=br.readLine()) != null) // System.out.println(s); //
 * boolean b = evaluer(url.openStream(), expression); //
 * System.out.println(expression); // System.out.println("--> " + b);
 *  // System.out.println("Deuxi�me test : UPLOAD"); // Object fichier =
 * (Object) (new File("C:/w3.xht")); // FileItem f = (FileItem) fichier; //
 * expression = "//html";
 *  // � tester plus tard (voir firstservlet pour un exemple)
 *  // b = evaluer(f.getInputStream(),expression); // System.out.println("On
 * verra plus tard");
 *  // ystem.out.println("Troisi�me test : DIRECT");
 *  // System.out.println(b);
 *  // ObservationresponseDocument.Factory.parse();
 * 
 * //System.out.println("Premier test : URL");
 * 
 * String expr = "//result"; InputModule inputMod =
 * InputFactory.createInputModule( (new MimeType()), EnumInputMethod.URI,
 * "http://www.w3.org"); Request req = Request.createRequest(inputMod,
 * "http://validator.w3.org/check", "uri", false, "ucn"); req.setLang("en");
 * req.addParameter("output", "ucn"); System.out.println("request created");
 * System.out.println(req.getResponseType()); req.doRequest();
 * System.out.println("request done");
 *  // pour afficher le stream de response
 * 
 * InputStream resp = req.getResponseStream(); InputStreamReader isr = new
 * InputStreamReader(resp); BufferedReader br = new BufferedReader(isr); String
 * str = ""; while ((str=br.readLine()) != null) System.out.println(str);
 * 
 * 
 * br.close(); isr.close();resp.close();
 * 
 *  // trouver un moyen de // reset le responseStream de
 * 
 * //System.out.println(req.getResponseStream()); boolean xpathRes =
 * evaluer(req.getResponseStream(), expr); System.out.println(xpathRes);
 *  } catch (Exception e) { e.printStackTrace(); } }
 */

/*
 * public static void main(String[] args) throws Exception { Object ct; try { ct =
 * (new URL("http://www.w3.org")).openConnection().getContentType(); //XmlObject
 * bidule = XmlObject.Factory.parse("http://www.w3.org");
 * 
 * System.out.println(bidule); // System.out.println(ct);
 * //ct.selectPath(xPath); String xPath = "(//doctype eq '-//W3C//DTD XHTML 1.0
 * Strict//EN') or (//doctype eq '-//W3C//DTD HTML 4.01//EN')" ;
 * //System.out.println(ct.selectPath(xPath));
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  } catch (MalformedURLException e) { e.printStackTrace(); } catch
 * (IOException e) { e.printStackTrace(); }
 *  }
 */

