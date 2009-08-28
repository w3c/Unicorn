/**
 * 
 */
package org.w3c.unicorn.tests;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.xmlbeans.XmlException;
import org.w3.unicorn.tasklist.TaskType;
import org.w3.unicorn.tasklist.TasklistDocument;
import org.w3c.unicorn.exceptions.UnknownObserverException;
import org.w3c.unicorn.tasklist.Task;
import org.w3c.unicorn.tasklist.TaskListUnmarshallerBeans;

/**
 * @author shenril
 * 
 */
public class TaskTest {

	/**
	 * @param args
	 * @throws UnknownObserverException 
	 */
	public static void main(String[] args) throws UnknownObserverException {
		try {
			TasklistDocument tasklist = TasklistDocument.Factory
					.parse(new File("./resources/tasklist/new-tasklist.xml"));
			TaskListUnmarshallerBeans unmarshaller = new TaskListUnmarshallerBeans();
			Task aTask = new Task();
			aTask.setTree(unmarshaller.expandTree(tasklist.getTasklist().getTaskArray(0),
												  tasklist.getTasklist().getTaskArray(0).getRoutine()));
			Map<String, Task> mapOfTask = new LinkedHashMap<String, Task>();
			for (TaskType myTask : tasklist.getTasklist().getTaskList()) {
				Task bTask = new Task();
				aTask.setTree(unmarshaller.expandTree(myTask, myTask.getRoutine()));
				mapOfTask.put(bTask.getID(), bTask);
			}
			aTask.setTree(aTask.expandNode(mapOfTask, aTask.getTree()));
			aTask.displayTree(aTask.getTree());
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnknownObserverException e) {
			e.printStackTrace();
		}
		
	}

}
