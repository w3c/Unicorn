/*
 * An XML document type.
 * Localname: tasklist
 * Namespace: http://www.w3.org/unicorn/tasklist
 * Java type: org.w3.unicorn.tasklist.TasklistDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.tasklist.impl;
/**
 * A document containing one tasklist(@http://www.w3.org/unicorn/tasklist) element.
 *
 * This is a complex type.
 */
public class TasklistDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.tasklist.TasklistDocument
{
    
    public TasklistDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TASKLIST$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "tasklist");
    
    
    /**
     * Gets the "tasklist" element
     */
    public org.w3.unicorn.tasklist.TasklistType getTasklist()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.TasklistType target = null;
            target = (org.w3.unicorn.tasklist.TasklistType)get_store().find_element_user(TASKLIST$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "tasklist" element
     */
    public void setTasklist(org.w3.unicorn.tasklist.TasklistType tasklist)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.TasklistType target = null;
            target = (org.w3.unicorn.tasklist.TasklistType)get_store().find_element_user(TASKLIST$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.tasklist.TasklistType)get_store().add_element_user(TASKLIST$0);
            }
            target.set(tasklist);
        }
    }
    
    /**
     * Appends and returns a new empty "tasklist" element
     */
    public org.w3.unicorn.tasklist.TasklistType addNewTasklist()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.TasklistType target = null;
            target = (org.w3.unicorn.tasklist.TasklistType)get_store().add_element_user(TASKLIST$0);
            return target;
        }
    }
}
