// $Id: XMLBeansTest.java,v 1.4 2008-08-29 12:19:09 fbatard Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tests;

import java.io.IOException;
import java.net.URL;

import org.apache.xmlbeans.XmlException;
import org.w3.unicorn.observationresponse.ObservationresponseDocument;


/**
 * XmlBeansTest<br />
 * Created: Jun 26, 2006 12:05:50 PM<br />
 * @author Batard Florent
 */
public class XMLBeansTest {

	public static void main(String[] args) throws XmlException, IOException {
		
		URL url = new URL("http://jigsaw.w3.org/css-validator/validator?uri=http%3A%2F%2Fshenril.free.fr%2Fmain.css&profile=css21&usermedium=all&warning=1&lang=fr&output=ucn");
		
		
//		 Bind the instance to the generated XMLBeans types.
		ObservationresponseDocument ObsDoc=null;
		try {
			ObsDoc = ObservationresponseDocument.Factory.parse(url.openStream());
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		ObservationresponseDocument.Observationresponse obs=ObsDoc.getObservationresponse();
//		 Get and print pieces of the XML instance.
		
			System.out.println(obs.getPassed()); 
	}
	
}
