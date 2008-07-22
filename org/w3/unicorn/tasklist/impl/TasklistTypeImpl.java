/*
 * XML Type:  tasklistType
 * Namespace: http://www.w3.org/unicorn/tasklist
 * Java type: org.w3.unicorn.tasklist.TasklistType
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.tasklist.impl;
/**
 * An XML tasklistType(@http://www.w3.org/unicorn/tasklist).
 *
 * This is a complex type.
 */
public class TasklistTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.tasklist.TasklistType
{
    
    public TasklistTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TASK$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "task");
    
    
    /**
     * Gets array of all "task" elements
     */
    public org.w3.unicorn.tasklist.TaskType[] getTaskArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(TASK$0, targetList);
            org.w3.unicorn.tasklist.TaskType[] result = new org.w3.unicorn.tasklist.TaskType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "task" element
     */
    public org.w3.unicorn.tasklist.TaskType getTaskArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.TaskType target = null;
            target = (org.w3.unicorn.tasklist.TaskType)get_store().find_element_user(TASK$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "task" element
     */
    public int sizeOfTaskArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TASK$0);
        }
    }
    
    /**
     * Sets array of all "task" element
     */
    public void setTaskArray(org.w3.unicorn.tasklist.TaskType[] taskArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(taskArray, TASK$0);
        }
    }
    
    /**
     * Sets ith "task" element
     */
    public void setTaskArray(int i, org.w3.unicorn.tasklist.TaskType task)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.TaskType target = null;
            target = (org.w3.unicorn.tasklist.TaskType)get_store().find_element_user(TASK$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(task);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "task" element
     */
    public org.w3.unicorn.tasklist.TaskType insertNewTask(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.TaskType target = null;
            target = (org.w3.unicorn.tasklist.TaskType)get_store().insert_element_user(TASK$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "task" element
     */
    public org.w3.unicorn.tasklist.TaskType addNewTask()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.TaskType target = null;
            target = (org.w3.unicorn.tasklist.TaskType)get_store().add_element_user(TASK$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "task" element
     */
    public void removeTask(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TASK$0, i);
        }
    }
}
