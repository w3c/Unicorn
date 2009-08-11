// $Id: XMLBeansTest.java,v 1.6 2009-08-11 13:43:02 jean-gui Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tests;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.w3.unicorn.observationresponse.ObservationresponseDocument;

/**
 * XmlBeansTest<br />
 * Created: Jun 26, 2006 12:05:50 PM<br />
 * 
 * @author Batard Florent
 */
public class XMLBeansTest {

	public static void main(String[] args) throws XmlException, IOException {

		// Bind the instance to the generated XMLBeans types.
		ObservationresponseDocument ObsDoc = null;
		try {
			ObsDoc = ObservationresponseDocument.Factory
					.parse(new java.io.File("./target.xml"));
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ObservationresponseDocument.Observationresponse obs = ObsDoc
				.getObservationresponse();
		// Get and print pieces of the XML instance.

		System.out.println(obs.getResult().getErrors().getErrorlistArray(0)
				.getErrorArray(0).getLongmessageArray(0));
		System.out.println(obs.getResult().getErrors().getErrorlistArray(0)
				.getErrorArray(0).getMessageArray(0));
	}

}
