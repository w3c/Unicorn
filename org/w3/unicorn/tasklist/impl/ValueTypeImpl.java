/*
 * XML Type:  valueType
 * Namespace: http://www.w3.org/unicorn/tasklist
 * Java type: org.w3.unicorn.tasklist.ValueType
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.tasklist.impl;
/**
 * An XML valueType(@http://www.w3.org/unicorn/tasklist).
 *
 * This is a complex type.
 */
public class ValueTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.tasklist.ValueType
{
    
    public ValueTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MAPPED$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "mapped");
    private static final javax.xml.namespace.QName NAME$2 = 
        new javax.xml.namespace.QName("", "name");
    
    
    /**
     * Gets array of all "mapped" elements
     */
    public org.w3.unicorn.tasklist.MappedType[] getMappedArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(MAPPED$0, targetList);
            org.w3.unicorn.tasklist.MappedType[] result = new org.w3.unicorn.tasklist.MappedType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "mapped" element
     */
    public org.w3.unicorn.tasklist.MappedType getMappedArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.MappedType target = null;
            target = (org.w3.unicorn.tasklist.MappedType)get_store().find_element_user(MAPPED$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "mapped" element
     */
    public int sizeOfMappedArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MAPPED$0);
        }
    }
    
    /**
     * Sets array of all "mapped" element
     */
    public void setMappedArray(org.w3.unicorn.tasklist.MappedType[] mappedArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(mappedArray, MAPPED$0);
        }
    }
    
    /**
     * Sets ith "mapped" element
     */
    public void setMappedArray(int i, org.w3.unicorn.tasklist.MappedType mapped)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.MappedType target = null;
            target = (org.w3.unicorn.tasklist.MappedType)get_store().find_element_user(MAPPED$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(mapped);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "mapped" element
     */
    public org.w3.unicorn.tasklist.MappedType insertNewMapped(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.MappedType target = null;
            target = (org.w3.unicorn.tasklist.MappedType)get_store().insert_element_user(MAPPED$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "mapped" element
     */
    public org.w3.unicorn.tasklist.MappedType addNewMapped()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.MappedType target = null;
            target = (org.w3.unicorn.tasklist.MappedType)get_store().add_element_user(MAPPED$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "mapped" element
     */
    public void removeMapped(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MAPPED$0, i);
        }
    }
    
    /**
     * Gets the "name" attribute
     */
    public java.lang.String getName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NAME$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "name" attribute
     */
    public org.apache.xmlbeans.XmlString xgetName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(NAME$2);
            return target;
        }
    }
    
    /**
     * True if has "name" attribute
     */
    public boolean isSetName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NAME$2) != null;
        }
    }
    
    /**
     * Sets the "name" attribute
     */
    public void setName(java.lang.String name)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NAME$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NAME$2);
            }
            target.setStringValue(name);
        }
    }
    
    /**
     * Sets (as xml) the "name" attribute
     */
    public void xsetName(org.apache.xmlbeans.XmlString name)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(NAME$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(NAME$2);
            }
            target.set(name);
        }
    }
    
    /**
     * Unsets the "name" attribute
     */
    public void unsetName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NAME$2);
        }
    }
}
