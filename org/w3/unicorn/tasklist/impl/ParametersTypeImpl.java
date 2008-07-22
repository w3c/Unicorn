/*
 * XML Type:  parametersType
 * Namespace: http://www.w3.org/unicorn/tasklist
 * Java type: org.w3.unicorn.tasklist.ParametersType
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.tasklist.impl;
/**
 * An XML parametersType(@http://www.w3.org/unicorn/tasklist).
 *
 * This is a complex type.
 */
public class ParametersTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.tasklist.ParametersType
{
    
    public ParametersTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PARAMETER$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "parameter");
    
    
    /**
     * Gets array of all "parameter" elements
     */
    public org.w3.unicorn.tasklist.ParameterType[] getParameterArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PARAMETER$0, targetList);
            org.w3.unicorn.tasklist.ParameterType[] result = new org.w3.unicorn.tasklist.ParameterType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "parameter" element
     */
    public org.w3.unicorn.tasklist.ParameterType getParameterArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ParameterType target = null;
            target = (org.w3.unicorn.tasklist.ParameterType)get_store().find_element_user(PARAMETER$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "parameter" element
     */
    public int sizeOfParameterArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PARAMETER$0);
        }
    }
    
    /**
     * Sets array of all "parameter" element
     */
    public void setParameterArray(org.w3.unicorn.tasklist.ParameterType[] parameterArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(parameterArray, PARAMETER$0);
        }
    }
    
    /**
     * Sets ith "parameter" element
     */
    public void setParameterArray(int i, org.w3.unicorn.tasklist.ParameterType parameter)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ParameterType target = null;
            target = (org.w3.unicorn.tasklist.ParameterType)get_store().find_element_user(PARAMETER$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(parameter);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "parameter" element
     */
    public org.w3.unicorn.tasklist.ParameterType insertNewParameter(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ParameterType target = null;
            target = (org.w3.unicorn.tasklist.ParameterType)get_store().insert_element_user(PARAMETER$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "parameter" element
     */
    public org.w3.unicorn.tasklist.ParameterType addNewParameter()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ParameterType target = null;
            target = (org.w3.unicorn.tasklist.ParameterType)get_store().add_element_user(PARAMETER$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "parameter" element
     */
    public void removeParameter(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PARAMETER$0, i);
        }
    }
}
