/*
 * XML Type:  condsType
 * Namespace: http://www.w3.org/unicorn/tasklist
 * Java type: org.w3.unicorn.tasklist.CondsType
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.tasklist.impl;
/**
 * An XML condsType(@http://www.w3.org/unicorn/tasklist).
 *
 * This is a complex type.
 */
public class CondsTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.tasklist.CondsType
{
    
    public CondsTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName COND$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "cond");
    
    
    /**
     * Gets array of all "cond" elements
     */
    public org.w3.unicorn.tasklist.CondType[] getCondArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(COND$0, targetList);
            org.w3.unicorn.tasklist.CondType[] result = new org.w3.unicorn.tasklist.CondType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "cond" element
     */
    public org.w3.unicorn.tasklist.CondType getCondArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.CondType target = null;
            target = (org.w3.unicorn.tasklist.CondType)get_store().find_element_user(COND$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "cond" element
     */
    public int sizeOfCondArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COND$0);
        }
    }
    
    /**
     * Sets array of all "cond" element
     */
    public void setCondArray(org.w3.unicorn.tasklist.CondType[] condArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(condArray, COND$0);
        }
    }
    
    /**
     * Sets ith "cond" element
     */
    public void setCondArray(int i, org.w3.unicorn.tasklist.CondType cond)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.CondType target = null;
            target = (org.w3.unicorn.tasklist.CondType)get_store().find_element_user(COND$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(cond);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "cond" element
     */
    public org.w3.unicorn.tasklist.CondType insertNewCond(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.CondType target = null;
            target = (org.w3.unicorn.tasklist.CondType)get_store().insert_element_user(COND$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "cond" element
     */
    public org.w3.unicorn.tasklist.CondType addNewCond()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.CondType target = null;
            target = (org.w3.unicorn.tasklist.CondType)get_store().add_element_user(COND$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "cond" element
     */
    public void removeCond(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COND$0, i);
        }
    }
}
