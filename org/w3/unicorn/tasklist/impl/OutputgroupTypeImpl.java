/*
 * XML Type:  outputgroupType
 * Namespace: http://www.w3.org/unicorn/tasklist
 * Java type: org.w3.unicorn.tasklist.OutputgroupType
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.tasklist.impl;
/**
 * An XML outputgroupType(@http://www.w3.org/unicorn/tasklist).
 *
 * This is a complex type.
 */
public class OutputgroupTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.tasklist.OutputgroupType
{
    
    public OutputgroupTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TASKSORT$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "tasksort");
    private static final javax.xml.namespace.QName ID$2 = 
        new javax.xml.namespace.QName("", "id");
    
    
    /**
     * Gets array of all "tasksort" elements
     */
    public java.lang.String[] getTasksortArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(TASKSORT$0, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "tasksort" element
     */
    public java.lang.String getTasksortArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TASKSORT$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "tasksort" elements
     */
    public org.apache.xmlbeans.XmlString[] xgetTasksortArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(TASKSORT$0, targetList);
            org.apache.xmlbeans.XmlString[] result = new org.apache.xmlbeans.XmlString[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "tasksort" element
     */
    public org.apache.xmlbeans.XmlString xgetTasksortArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TASKSORT$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (org.apache.xmlbeans.XmlString)target;
        }
    }
    
    /**
     * Returns number of "tasksort" element
     */
    public int sizeOfTasksortArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TASKSORT$0);
        }
    }
    
    /**
     * Sets array of all "tasksort" element
     */
    public void setTasksortArray(java.lang.String[] tasksortArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(tasksortArray, TASKSORT$0);
        }
    }
    
    /**
     * Sets ith "tasksort" element
     */
    public void setTasksortArray(int i, java.lang.String tasksort)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TASKSORT$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(tasksort);
        }
    }
    
    /**
     * Sets (as xml) array of all "tasksort" element
     */
    public void xsetTasksortArray(org.apache.xmlbeans.XmlString[]tasksortArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(tasksortArray, TASKSORT$0);
        }
    }
    
    /**
     * Sets (as xml) ith "tasksort" element
     */
    public void xsetTasksortArray(int i, org.apache.xmlbeans.XmlString tasksort)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TASKSORT$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(tasksort);
        }
    }
    
    /**
     * Inserts the value as the ith "tasksort" element
     */
    public void insertTasksort(int i, java.lang.String tasksort)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(TASKSORT$0, i);
            target.setStringValue(tasksort);
        }
    }
    
    /**
     * Appends the value as the last "tasksort" element
     */
    public void addTasksort(java.lang.String tasksort)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TASKSORT$0);
            target.setStringValue(tasksort);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "tasksort" element
     */
    public org.apache.xmlbeans.XmlString insertNewTasksort(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(TASKSORT$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "tasksort" element
     */
    public org.apache.xmlbeans.XmlString addNewTasksort()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TASKSORT$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "tasksort" element
     */
    public void removeTasksort(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TASKSORT$0, i);
        }
    }
    
    /**
     * Gets the "id" attribute
     */
    public byte getId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ID$2);
            if (target == null)
            {
                return 0;
            }
            return target.getByteValue();
        }
    }
    
    /**
     * Gets (as xml) the "id" attribute
     */
    public org.apache.xmlbeans.XmlByte xgetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlByte target = null;
            target = (org.apache.xmlbeans.XmlByte)get_store().find_attribute_user(ID$2);
            return target;
        }
    }
    
    /**
     * True if has "id" attribute
     */
    public boolean isSetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ID$2) != null;
        }
    }
    
    /**
     * Sets the "id" attribute
     */
    public void setId(byte id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ID$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ID$2);
            }
            target.setByteValue(id);
        }
    }
    
    /**
     * Sets (as xml) the "id" attribute
     */
    public void xsetId(org.apache.xmlbeans.XmlByte id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlByte target = null;
            target = (org.apache.xmlbeans.XmlByte)get_store().find_attribute_user(ID$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlByte)get_store().add_attribute_user(ID$2);
            }
            target.set(id);
        }
    }
    
    /**
     * Unsets the "id" attribute
     */
    public void unsetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ID$2);
        }
    }
}
