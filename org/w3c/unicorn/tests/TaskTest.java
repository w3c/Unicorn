/**
 * 
 */
package org.w3c.unicorn.tests;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.xmlbeans.XmlException;
import org.w3.unicorn.tasklist.TaskType;
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
			Map<String, Task> mapOfTask = new LinkedHashMap<String, Task>();
			for(TaskType myTask : tasklist.getTasklist().getTaskArray()){
				Task bTask=new Task(myTask);
				mapOfTask.put(bTask.getID(), bTask);
			}
			aTask.setRoot(aTask.expandNode(mapOfTask, aTask.getRoot()));
			aTask.displayTree(aTask.getRoot()); 
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
