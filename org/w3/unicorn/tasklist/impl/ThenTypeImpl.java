/*
 * XML Type:  thenType
 * Namespace: http://www.w3.org/unicorn/tasklist
 * Java type: org.w3.unicorn.tasklist.ThenType
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.tasklist.impl;
/**
 * An XML thenType(@http://www.w3.org/unicorn/tasklist).
 *
 * This is a complex type.
 */
public class ThenTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.tasklist.ThenType
{
    
    public ThenTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IF$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "if");
    private static final javax.xml.namespace.QName EXEC$2 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "exec");
    
    
    /**
     * Gets array of all "if" elements
     */
    public org.w3.unicorn.tasklist.IfType[] getIfArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IF$0, targetList);
            org.w3.unicorn.tasklist.IfType[] result = new org.w3.unicorn.tasklist.IfType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "if" element
     */
    public org.w3.unicorn.tasklist.IfType getIfArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.IfType target = null;
            target = (org.w3.unicorn.tasklist.IfType)get_store().find_element_user(IF$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "if" element
     */
    public int sizeOfIfArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IF$0);
        }
    }
    
    /**
     * Sets array of all "if" element
     */
    public void setIfArray(org.w3.unicorn.tasklist.IfType[] xifArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(xifArray, IF$0);
        }
    }
    
    /**
     * Sets ith "if" element
     */
    public void setIfArray(int i, org.w3.unicorn.tasklist.IfType xif)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.IfType target = null;
            target = (org.w3.unicorn.tasklist.IfType)get_store().find_element_user(IF$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(xif);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "if" element
     */
    public org.w3.unicorn.tasklist.IfType insertNewIf(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.IfType target = null;
            target = (org.w3.unicorn.tasklist.IfType)get_store().insert_element_user(IF$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "if" element
     */
    public org.w3.unicorn.tasklist.IfType addNewIf()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.IfType target = null;
            target = (org.w3.unicorn.tasklist.IfType)get_store().add_element_user(IF$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "if" element
     */
    public void removeIf(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IF$0, i);
        }
    }
    
    /**
     * Gets array of all "exec" elements
     */
    public org.w3.unicorn.tasklist.ExecType[] getExecArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(EXEC$2, targetList);
            org.w3.unicorn.tasklist.ExecType[] result = new org.w3.unicorn.tasklist.ExecType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "exec" element
     */
    public org.w3.unicorn.tasklist.ExecType getExecArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ExecType target = null;
            target = (org.w3.unicorn.tasklist.ExecType)get_store().find_element_user(EXEC$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "exec" element
     */
    public int sizeOfExecArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EXEC$2);
        }
    }
    
    /**
     * Sets array of all "exec" element
     */
    public void setExecArray(org.w3.unicorn.tasklist.ExecType[] execArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(execArray, EXEC$2);
        }
    }
    
    /**
     * Sets ith "exec" element
     */
    public void setExecArray(int i, org.w3.unicorn.tasklist.ExecType exec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ExecType target = null;
            target = (org.w3.unicorn.tasklist.ExecType)get_store().find_element_user(EXEC$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(exec);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "exec" element
     */
    public org.w3.unicorn.tasklist.ExecType insertNewExec(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ExecType target = null;
            target = (org.w3.unicorn.tasklist.ExecType)get_store().insert_element_user(EXEC$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "exec" element
     */
    public org.w3.unicorn.tasklist.ExecType addNewExec()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ExecType target = null;
            target = (org.w3.unicorn.tasklist.ExecType)get_store().add_element_user(EXEC$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "exec" element
     */
    public void removeExec(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EXEC$2, i);
        }
    }
}
