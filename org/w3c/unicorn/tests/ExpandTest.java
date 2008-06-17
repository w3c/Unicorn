// $Id: ExpandTest.java,v 1.2 2008-06-17 13:45:31 jbarouh Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * ExpandTest<br />
 * Created: Jun 7, 2006 5:28:51 PM<br />
 * Class used to test expand and merge methods used with referenced tasks
 */
public class ExpandTest {
	
	public static Map<String, ExpandTest> testList = new TreeMap<String, ExpandTest>();
	
	private boolean enCours = false;
	
	private String id;
	private List<String> references;
	
	/**
	 * Default constructor for ExpandTest.
	 *
	 */
	public ExpandTest() {
		references = new ArrayList<String>();
	}
	
	/**
	 * Removes the old references and updates the oldRefs list with the 
	 * current references before adding the new references. 
	 *
	 */
	public void expand() {
		enCours = true;
		List<String> oldRefs = new ArrayList<String>();
		for (String string : references) {
			oldRefs.add(string);
		}
		
		for (String ref : oldRefs) {
			ExpandTest reference = testList.get(ref);
			if(!reference.enCours) reference.expand();
			for (String newRef : reference.references) {
				if(!references.contains(newRef) && !id.equals(newRef)) {		    
					references.add(newRef);
				}
			}
		}
	}
	
	public String toString() {
		return id + "=" + references;
	}
	
	/**
	 * Tests the expand process.
	 * @param args
	 */
	public static void main(String[] args) {	
		ExpandTest test1 = new ExpandTest();
		test1.id = "css";
		testList.put("css", test1);
		
		ExpandTest test2 = new ExpandTest();
		test2.references.add("foo");
		test2.id = "link-checker";
		testList.put("link-checker", test2);
		
		ExpandTest test3 = new ExpandTest();
		test3.references.add("css");
		test3.id = "foo";
		testList.put("foo", test3);
		
		for (ExpandTest test : testList.values()) {
			test.expand();
		}
		
		for (ExpandTest test : testList.values()) {
			System.out.println(test);
		}
	}
	
}
