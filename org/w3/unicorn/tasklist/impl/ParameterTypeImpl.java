/*
 * XML Type:  parameterType
 * Namespace: http://www.w3.org/unicorn/tasklist
 * Java type: org.w3.unicorn.tasklist.ParameterType
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.tasklist.impl;
/**
 * An XML parameterType(@http://www.w3.org/unicorn/tasklist).
 *
 * This is a complex type.
 */
public class ParameterTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.tasklist.ParameterType
{
    
    public ParameterTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VALUE$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "value");
    private static final javax.xml.namespace.QName NAME$2 = 
        new javax.xml.namespace.QName("", "name");
    private static final javax.xml.namespace.QName TYPE$4 = 
        new javax.xml.namespace.QName("", "type");
    private static final javax.xml.namespace.QName DEFAULT$6 = 
        new javax.xml.namespace.QName("", "default");
    private static final javax.xml.namespace.QName UI$8 = 
        new javax.xml.namespace.QName("", "ui");
    private static final javax.xml.namespace.QName OBSERVER$10 = 
        new javax.xml.namespace.QName("", "observer");
    
    
    /**
     * Gets array of all "value" elements
     */
    public org.w3.unicorn.tasklist.ValueType[] getValueArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(VALUE$0, targetList);
            org.w3.unicorn.tasklist.ValueType[] result = new org.w3.unicorn.tasklist.ValueType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "value" element
     */
    public org.w3.unicorn.tasklist.ValueType getValueArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ValueType target = null;
            target = (org.w3.unicorn.tasklist.ValueType)get_store().find_element_user(VALUE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "value" element
     */
    public int sizeOfValueArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VALUE$0);
        }
    }
    
    /**
     * Sets array of all "value" element
     */
    public void setValueArray(org.w3.unicorn.tasklist.ValueType[] valueArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(valueArray, VALUE$0);
        }
    }
    
    /**
     * Sets ith "value" element
     */
    public void setValueArray(int i, org.w3.unicorn.tasklist.ValueType value)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ValueType target = null;
            target = (org.w3.unicorn.tasklist.ValueType)get_store().find_element_user(VALUE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(value);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "value" element
     */
    public org.w3.unicorn.tasklist.ValueType insertNewValue(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ValueType target = null;
            target = (org.w3.unicorn.tasklist.ValueType)get_store().insert_element_user(VALUE$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "value" element
     */
    public org.w3.unicorn.tasklist.ValueType addNewValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ValueType target = null;
            target = (org.w3.unicorn.tasklist.ValueType)get_store().add_element_user(VALUE$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "value" element
     */
    public void removeValue(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VALUE$0, i);
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
    public org.apache.xmlbeans.XmlNCName xgetName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlNCName target = null;
            target = (org.apache.xmlbeans.XmlNCName)get_store().find_attribute_user(NAME$2);
            return target;
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
    public void xsetName(org.apache.xmlbeans.XmlNCName name)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlNCName target = null;
            target = (org.apache.xmlbeans.XmlNCName)get_store().find_attribute_user(NAME$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlNCName)get_store().add_attribute_user(NAME$2);
            }
            target.set(name);
        }
    }
    
    /**
     * Gets the "type" attribute
     */
    public org.w3.unicorn.tasklist.TParamType.Enum getType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TYPE$4);
            if (target == null)
            {
                return null;
            }
            return (org.w3.unicorn.tasklist.TParamType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "type" attribute
     */
    public org.w3.unicorn.tasklist.TParamType xgetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.TParamType target = null;
            target = (org.w3.unicorn.tasklist.TParamType)get_store().find_attribute_user(TYPE$4);
            return target;
        }
    }
    
    /**
     * Sets the "type" attribute
     */
    public void setType(org.w3.unicorn.tasklist.TParamType.Enum type)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TYPE$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TYPE$4);
            }
            target.setEnumValue(type);
        }
    }
    
    /**
     * Sets (as xml) the "type" attribute
     */
    public void xsetType(org.w3.unicorn.tasklist.TParamType type)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.TParamType target = null;
            target = (org.w3.unicorn.tasklist.TParamType)get_store().find_attribute_user(TYPE$4);
            if (target == null)
            {
                target = (org.w3.unicorn.tasklist.TParamType)get_store().add_attribute_user(TYPE$4);
            }
            target.set(type);
        }
    }
    
    /**
     * Gets the "default" attribute
     */
    public java.lang.String getDefault()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DEFAULT$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "default" attribute
     */
    public org.apache.xmlbeans.XmlString xgetDefault()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DEFAULT$6);
            return target;
        }
    }
    
    /**
     * True if has "default" attribute
     */
    public boolean isSetDefault()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DEFAULT$6) != null;
        }
    }
    
    /**
     * Sets the "default" attribute
     */
    public void setDefault(java.lang.String xdefault)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DEFAULT$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DEFAULT$6);
            }
            target.setStringValue(xdefault);
        }
    }
    
    /**
     * Sets (as xml) the "default" attribute
     */
    public void xsetDefault(org.apache.xmlbeans.XmlString xdefault)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DEFAULT$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(DEFAULT$6);
            }
            target.set(xdefault);
        }
    }
    
    /**
     * Unsets the "default" attribute
     */
    public void unsetDefault()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DEFAULT$6);
        }
    }
    
    /**
     * Gets the "ui" attribute
     */
    public org.w3.unicorn.tasklist.TUi.Enum getUi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(UI$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_default_attribute_value(UI$8);
            }
            if (target == null)
            {
                return null;
            }
            return (org.w3.unicorn.tasklist.TUi.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ui" attribute
     */
    public org.w3.unicorn.tasklist.TUi xgetUi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.TUi target = null;
            target = (org.w3.unicorn.tasklist.TUi)get_store().find_attribute_user(UI$8);
            if (target == null)
            {
                target = (org.w3.unicorn.tasklist.TUi)get_default_attribute_value(UI$8);
            }
            return target;
        }
    }
    
    /**
     * True if has "ui" attribute
     */
    public boolean isSetUi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(UI$8) != null;
        }
    }
    
    /**
     * Sets the "ui" attribute
     */
    public void setUi(org.w3.unicorn.tasklist.TUi.Enum ui)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(UI$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(UI$8);
            }
            target.setEnumValue(ui);
        }
    }
    
    /**
     * Sets (as xml) the "ui" attribute
     */
    public void xsetUi(org.w3.unicorn.tasklist.TUi ui)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.TUi target = null;
            target = (org.w3.unicorn.tasklist.TUi)get_store().find_attribute_user(UI$8);
            if (target == null)
            {
                target = (org.w3.unicorn.tasklist.TUi)get_store().add_attribute_user(UI$8);
            }
            target.set(ui);
        }
    }
    
    /**
     * Unsets the "ui" attribute
     */
    public void unsetUi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(UI$8);
        }
    }
    
    /**
     * Gets the "observer" attribute
     */
    public java.lang.String getObserver()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OBSERVER$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "observer" attribute
     */
    public org.apache.xmlbeans.XmlNCName xgetObserver()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlNCName target = null;
            target = (org.apache.xmlbeans.XmlNCName)get_store().find_attribute_user(OBSERVER$10);
            return target;
        }
    }
    
    /**
     * True if has "observer" attribute
     */
    public boolean isSetObserver()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(OBSERVER$10) != null;
        }
    }
    
    /**
     * Sets the "observer" attribute
     */
    public void setObserver(java.lang.String observer)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OBSERVER$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OBSERVER$10);
            }
            target.setStringValue(observer);
        }
    }
    
    /**
     * Sets (as xml) the "observer" attribute
     */
    public void xsetObserver(org.apache.xmlbeans.XmlNCName observer)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlNCName target = null;
            target = (org.apache.xmlbeans.XmlNCName)get_store().find_attribute_user(OBSERVER$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlNCName)get_store().add_attribute_user(OBSERVER$10);
            }
            target.set(observer);
        }
    }
    
    /**
     * Unsets the "observer" attribute
     */
    public void unsetObserver()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(OBSERVER$10);
        }
    }
}
