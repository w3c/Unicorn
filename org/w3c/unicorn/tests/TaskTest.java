/**
 * 
 */
package org.w3c.unicorn.tests;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.xmlbeans.XmlException;
import org.w3.unicorn.tasklist.TasklistDocument;
import org.w3c.unicorn.tasklist.Task;
import org.w3c.unicorn.tasklisttree.TLTExec;
/**
 * @author shenril
 *
 */
public class TaskTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TasklistDocument tasklist=TasklistDocument.Factory.parse(new File("resources/tasklist/new-tasklist.xml"));
			Task aTask=new Task(tasklist.getTasklist().getTaskArray(0));
			ArrayList<TLTExec> liste=new ArrayList<TLTExec>();
			aTask.getExecs(liste, aTask.getTree());
			for(TLTExec exec: liste){
				System.out.println(exec);
			}
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
