/*
 * XML Type:  taskType
 * Namespace: http://www.w3.org/unicorn/tasklist
 * Java type: org.w3.unicorn.tasklist.TaskType
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.tasklist.impl;
/**
 * An XML taskType(@http://www.w3.org/unicorn/tasklist).
 *
 * This is a complex type.
 */
public class TaskTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.tasklist.TaskType
{
    
    public TaskTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CONDS$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "conds");
    private static final javax.xml.namespace.QName ROUTINE$2 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "routine");
    private static final javax.xml.namespace.QName OUTPUTSEQ$4 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "outputseq");
    private static final javax.xml.namespace.QName PARAMETERS$6 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "parameters");
    private static final javax.xml.namespace.QName ID$8 = 
        new javax.xml.namespace.QName("", "id");
    
    
    /**
     * Gets the "conds" element
     */
    public org.w3.unicorn.tasklist.CondsType getConds()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.CondsType target = null;
            target = (org.w3.unicorn.tasklist.CondsType)get_store().find_element_user(CONDS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "conds" element
     */
    public void setConds(org.w3.unicorn.tasklist.CondsType conds)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.CondsType target = null;
            target = (org.w3.unicorn.tasklist.CondsType)get_store().find_element_user(CONDS$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.tasklist.CondsType)get_store().add_element_user(CONDS$0);
            }
            target.set(conds);
        }
    }
    
    /**
     * Appends and returns a new empty "conds" element
     */
    public org.w3.unicorn.tasklist.CondsType addNewConds()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.CondsType target = null;
            target = (org.w3.unicorn.tasklist.CondsType)get_store().add_element_user(CONDS$0);
            return target;
        }
    }
    
    /**
     * Gets the "routine" element
     */
    public org.w3.unicorn.tasklist.RoutineType getRoutine()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.RoutineType target = null;
            target = (org.w3.unicorn.tasklist.RoutineType)get_store().find_element_user(ROUTINE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "routine" element
     */
    public void setRoutine(org.w3.unicorn.tasklist.RoutineType routine)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.RoutineType target = null;
            target = (org.w3.unicorn.tasklist.RoutineType)get_store().find_element_user(ROUTINE$2, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.tasklist.RoutineType)get_store().add_element_user(ROUTINE$2);
            }
            target.set(routine);
        }
    }
    
    /**
     * Appends and returns a new empty "routine" element
     */
    public org.w3.unicorn.tasklist.RoutineType addNewRoutine()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.RoutineType target = null;
            target = (org.w3.unicorn.tasklist.RoutineType)get_store().add_element_user(ROUTINE$2);
            return target;
        }
    }
    
    /**
     * Gets the "outputseq" element
     */
    public org.w3.unicorn.tasklist.OutputseqType getOutputseq()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.OutputseqType target = null;
            target = (org.w3.unicorn.tasklist.OutputseqType)get_store().find_element_user(OUTPUTSEQ$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "outputseq" element
     */
    public void setOutputseq(org.w3.unicorn.tasklist.OutputseqType outputseq)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.OutputseqType target = null;
            target = (org.w3.unicorn.tasklist.OutputseqType)get_store().find_element_user(OUTPUTSEQ$4, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.tasklist.OutputseqType)get_store().add_element_user(OUTPUTSEQ$4);
            }
            target.set(outputseq);
        }
    }
    
    /**
     * Appends and returns a new empty "outputseq" element
     */
    public org.w3.unicorn.tasklist.OutputseqType addNewOutputseq()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.OutputseqType target = null;
            target = (org.w3.unicorn.tasklist.OutputseqType)get_store().add_element_user(OUTPUTSEQ$4);
            return target;
        }
    }
    
    /**
     * Gets the "parameters" element
     */
    public org.w3.unicorn.tasklist.ParametersType getParameters()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ParametersType target = null;
            target = (org.w3.unicorn.tasklist.ParametersType)get_store().find_element_user(PARAMETERS$6, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "parameters" element
     */
    public void setParameters(org.w3.unicorn.tasklist.ParametersType parameters)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ParametersType target = null;
            target = (org.w3.unicorn.tasklist.ParametersType)get_store().find_element_user(PARAMETERS$6, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.tasklist.ParametersType)get_store().add_element_user(PARAMETERS$6);
            }
            target.set(parameters);
        }
    }
    
    /**
     * Appends and returns a new empty "parameters" element
     */
    public org.w3.unicorn.tasklist.ParametersType addNewParameters()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ParametersType target = null;
            target = (org.w3.unicorn.tasklist.ParametersType)get_store().add_element_user(PARAMETERS$6);
            return target;
        }
    }
    
    /**
     * Gets the "id" attribute
     */
    public java.lang.String getId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ID$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "id" attribute
     */
    public org.apache.xmlbeans.XmlString xgetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ID$8);
            return target;
        }
    }
    
    /**
     * Sets the "id" attribute
     */
    public void setId(java.lang.String id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ID$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ID$8);
            }
            target.setStringValue(id);
        }
    }
    
    /**
     * Sets (as xml) the "id" attribute
     */
    public void xsetId(org.apache.xmlbeans.XmlString id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ID$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ID$8);
            }
            target.set(id);
        }
    }
}
