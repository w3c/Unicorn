/*
 * XML Type:  outputseqType
 * Namespace: http://www.w3.org/unicorn/tasklist
 * Java type: org.w3.unicorn.tasklist.OutputseqType
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.tasklist.impl;
/**
 * An XML outputseqType(@http://www.w3.org/unicorn/tasklist).
 *
 * This is a complex type.
 */
public class OutputseqTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.tasklist.OutputseqType
{
    
    public OutputseqTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName OUTPUTGROUP$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "outputgroup");
    
    
    /**
     * Gets array of all "outputgroup" elements
     */
    public org.w3.unicorn.tasklist.OutputgroupType[] getOutputgroupArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(OUTPUTGROUP$0, targetList);
            org.w3.unicorn.tasklist.OutputgroupType[] result = new org.w3.unicorn.tasklist.OutputgroupType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "outputgroup" element
     */
    public org.w3.unicorn.tasklist.OutputgroupType getOutputgroupArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.OutputgroupType target = null;
            target = (org.w3.unicorn.tasklist.OutputgroupType)get_store().find_element_user(OUTPUTGROUP$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "outputgroup" element
     */
    public int sizeOfOutputgroupArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OUTPUTGROUP$0);
        }
    }
    
    /**
     * Sets array of all "outputgroup" element
     */
    public void setOutputgroupArray(org.w3.unicorn.tasklist.OutputgroupType[] outputgroupArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(outputgroupArray, OUTPUTGROUP$0);
        }
    }
    
    /**
     * Sets ith "outputgroup" element
     */
    public void setOutputgroupArray(int i, org.w3.unicorn.tasklist.OutputgroupType outputgroup)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.OutputgroupType target = null;
            target = (org.w3.unicorn.tasklist.OutputgroupType)get_store().find_element_user(OUTPUTGROUP$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(outputgroup);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "outputgroup" element
     */
    public org.w3.unicorn.tasklist.OutputgroupType insertNewOutputgroup(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.OutputgroupType target = null;
            target = (org.w3.unicorn.tasklist.OutputgroupType)get_store().insert_element_user(OUTPUTGROUP$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "outputgroup" element
     */
    public org.w3.unicorn.tasklist.OutputgroupType addNewOutputgroup()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.OutputgroupType target = null;
            target = (org.w3.unicorn.tasklist.OutputgroupType)get_store().add_element_user(OUTPUTGROUP$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "outputgroup" element
     */
    public void removeOutputgroup(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OUTPUTGROUP$0, i);
        }
    }
}
