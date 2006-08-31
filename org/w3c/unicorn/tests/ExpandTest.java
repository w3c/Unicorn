// $Id: ExpandTest.java,v 1.1.1.1 2006-08-31 09:09:28 dleroy Exp $
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
	
	public ExpandTest() {
		references = new ArrayList<String>();
	}
	
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
	
	public static void main(String[] args) {	
		ExpandTest test1 = new ExpandTest();
		//test1.references.add("test2");
		//test1.references.add("test5");
		test1.id = "css";
		testList.put("css", test1);
		
		ExpandTest test2 = new ExpandTest();
		test2.references.add("foo");
		test2.id = "link-checker";
		testList.put("link-checker", test2);
		
		ExpandTest test3 = new ExpandTest();
		test3.references.add("css");
		//test3.references.add("test6");
		test3.id = "foo";
		testList.put("foo", test3);
		/*
		ExpandTest test4 = new ExpandTest();
		test4.references.add("test1");	
		test4.id = "test4";
		testList.put("test4", test4);
		
		ExpandTest test5 = new ExpandTest();	
		test5.id = "test5";
		test5.references.add("test6");
		testList.put("test5", test5);
		
		ExpandTest test6 = new ExpandTest();	
		test6.id = "test6";
		testList.put("test6", test6);*/
		//test6.references.add("test4");
		
		for (ExpandTest test : testList.values()) {
			//System.out.println(test.id);
			test.expand();
		}
		
		for (ExpandTest test : testList.values()) {
			System.out.println(test);
		}
	}
	
}
