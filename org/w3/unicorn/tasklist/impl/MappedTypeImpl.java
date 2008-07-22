/*
 * XML Type:  mappedType
 * Namespace: http://www.w3.org/unicorn/tasklist
 * Java type: org.w3.unicorn.tasklist.MappedType
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.tasklist.impl;
/**
 * An XML mappedType(@http://www.w3.org/unicorn/tasklist).
 *
 * This is an atomic type that is a restriction of org.w3.unicorn.tasklist.MappedType.
 */
public class MappedTypeImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements org.w3.unicorn.tasklist.MappedType
{
    
    public MappedTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType, true);
    }
    
    protected MappedTypeImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
    {
        super(sType, b);
    }
    
    private static final javax.xml.namespace.QName PARAM$0 = 
        new javax.xml.namespace.QName("", "param");
    private static final javax.xml.namespace.QName OBSERVER$2 = 
        new javax.xml.namespace.QName("", "observer");
    private static final javax.xml.namespace.QName VALUE$4 = 
        new javax.xml.namespace.QName("", "value");
    private static final javax.xml.namespace.QName INPUTMETHOD$6 = 
        new javax.xml.namespace.QName("", "inputmethod");
    
    
    /**
     * Gets the "param" attribute
     */
    public java.lang.String getParam()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PARAM$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "param" attribute
     */
    public org.apache.xmlbeans.XmlNCName xgetParam()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlNCName target = null;
            target = (org.apache.xmlbeans.XmlNCName)get_store().find_attribute_user(PARAM$0);
            return target;
        }
    }
    
    /**
     * Sets the "param" attribute
     */
    public void setParam(java.lang.String param)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PARAM$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PARAM$0);
            }
            target.setStringValue(param);
        }
    }
    
    /**
     * Sets (as xml) the "param" attribute
     */
    public void xsetParam(org.apache.xmlbeans.XmlNCName param)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlNCName target = null;
            target = (org.apache.xmlbeans.XmlNCName)get_store().find_attribute_user(PARAM$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlNCName)get_store().add_attribute_user(PARAM$0);
            }
            target.set(param);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OBSERVER$2);
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
            target = (org.apache.xmlbeans.XmlNCName)get_store().find_attribute_user(OBSERVER$2);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OBSERVER$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OBSERVER$2);
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
            target = (org.apache.xmlbeans.XmlNCName)get_store().find_attribute_user(OBSERVER$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlNCName)get_store().add_attribute_user(OBSERVER$2);
            }
            target.set(observer);
        }
    }
    
    /**
     * Gets the "value" attribute
     */
    public java.lang.String getValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALUE$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "value" attribute
     */
    public org.apache.xmlbeans.XmlString xgetValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(VALUE$4);
            return target;
        }
    }
    
    /**
     * True if has "value" attribute
     */
    public boolean isSetValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(VALUE$4) != null;
        }
    }
    
    /**
     * Sets the "value" attribute
     */
    public void setValue(java.lang.String value)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALUE$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(VALUE$4);
            }
            target.setStringValue(value);
        }
    }
    
    /**
     * Sets (as xml) the "value" attribute
     */
    public void xsetValue(org.apache.xmlbeans.XmlString value)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(VALUE$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(VALUE$4);
            }
            target.set(value);
        }
    }
    
    /**
     * Unsets the "value" attribute
     */
    public void unsetValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(VALUE$4);
        }
    }
    
    /**
     * Gets the "inputmethod" attribute
     */
    public java.util.List getInputmethod()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INPUTMETHOD$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_default_attribute_value(INPUTMETHOD$6);
            }
            if (target == null)
            {
                return null;
            }
            return target.getListValue();
        }
    }
    
    /**
     * Gets (as xml) the "inputmethod" attribute
     */
    public org.w3.unicorn.tasklist.MappedType.Inputmethod xgetInputmethod()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.MappedType.Inputmethod target = null;
            target = (org.w3.unicorn.tasklist.MappedType.Inputmethod)get_store().find_attribute_user(INPUTMETHOD$6);
            if (target == null)
            {
                target = (org.w3.unicorn.tasklist.MappedType.Inputmethod)get_default_attribute_value(INPUTMETHOD$6);
            }
            return target;
        }
    }
    
    /**
     * True if has "inputmethod" attribute
     */
    public boolean isSetInputmethod()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(INPUTMETHOD$6) != null;
        }
    }
    
    /**
     * Sets the "inputmethod" attribute
     */
    public void setInputmethod(java.util.List inputmethod)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INPUTMETHOD$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INPUTMETHOD$6);
            }
            target.setListValue(inputmethod);
        }
    }
    
    /**
     * Sets (as xml) the "inputmethod" attribute
     */
    public void xsetInputmethod(org.w3.unicorn.tasklist.MappedType.Inputmethod inputmethod)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.MappedType.Inputmethod target = null;
            target = (org.w3.unicorn.tasklist.MappedType.Inputmethod)get_store().find_attribute_user(INPUTMETHOD$6);
            if (target == null)
            {
                target = (org.w3.unicorn.tasklist.MappedType.Inputmethod)get_store().add_attribute_user(INPUTMETHOD$6);
            }
            target.set(inputmethod);
        }
    }
    
    /**
     * Unsets the "inputmethod" attribute
     */
    public void unsetInputmethod()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(INPUTMETHOD$6);
        }
    }
    /**
     * An XML inputmethod(@).
     *
     * This is a list type whose items are org.w3.unicorn.tasklist.TInputMethod.
     */
    public static class InputmethodImpl extends org.apache.xmlbeans.impl.values.XmlListImpl implements org.w3.unicorn.tasklist.MappedType.Inputmethod
    {
        
        public InputmethodImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected InputmethodImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
