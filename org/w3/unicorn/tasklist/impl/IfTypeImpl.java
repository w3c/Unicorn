/*
 * XML Type:  ifType
 * Namespace: http://www.w3.org/unicorn/tasklist
 * Java type: org.w3.unicorn.tasklist.IfType
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.tasklist.impl;
/**
 * An XML ifType(@http://www.w3.org/unicorn/tasklist).
 *
 * This is a complex type.
 */
public class IfTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.tasklist.IfType
{
    
    public IfTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName THEN$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "then");
    private static final javax.xml.namespace.QName ELSE$2 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/tasklist", "else");
    private static final javax.xml.namespace.QName TEST$4 = 
        new javax.xml.namespace.QName("", "test");
    
    
    /**
     * Gets the "then" element
     */
    public org.w3.unicorn.tasklist.ThenType getThen()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ThenType target = null;
            target = (org.w3.unicorn.tasklist.ThenType)get_store().find_element_user(THEN$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "then" element
     */
    public void setThen(org.w3.unicorn.tasklist.ThenType then)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ThenType target = null;
            target = (org.w3.unicorn.tasklist.ThenType)get_store().find_element_user(THEN$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.tasklist.ThenType)get_store().add_element_user(THEN$0);
            }
            target.set(then);
        }
    }
    
    /**
     * Appends and returns a new empty "then" element
     */
    public org.w3.unicorn.tasklist.ThenType addNewThen()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ThenType target = null;
            target = (org.w3.unicorn.tasklist.ThenType)get_store().add_element_user(THEN$0);
            return target;
        }
    }
    
    /**
     * Gets the "else" element
     */
    public org.w3.unicorn.tasklist.ElseType getElse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ElseType target = null;
            target = (org.w3.unicorn.tasklist.ElseType)get_store().find_element_user(ELSE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "else" element
     */
    public void setElse(org.w3.unicorn.tasklist.ElseType xelse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ElseType target = null;
            target = (org.w3.unicorn.tasklist.ElseType)get_store().find_element_user(ELSE$2, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.tasklist.ElseType)get_store().add_element_user(ELSE$2);
            }
            target.set(xelse);
        }
    }
    
    /**
     * Appends and returns a new empty "else" element
     */
    public org.w3.unicorn.tasklist.ElseType addNewElse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.tasklist.ElseType target = null;
            target = (org.w3.unicorn.tasklist.ElseType)get_store().add_element_user(ELSE$2);
            return target;
        }
    }
    
    /**
     * Gets the "test" attribute
     */
    public java.lang.String getTest()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TEST$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "test" attribute
     */
    public org.apache.xmlbeans.XmlString xgetTest()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TEST$4);
            return target;
        }
    }
    
    /**
     * True if has "test" attribute
     */
    public boolean isSetTest()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TEST$4) != null;
        }
    }
    
    /**
     * Sets the "test" attribute
     */
    public void setTest(java.lang.String test)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TEST$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TEST$4);
            }
            target.setStringValue(test);
        }
    }
    
    /**
     * Sets (as xml) the "test" attribute
     */
    public void xsetTest(org.apache.xmlbeans.XmlString test)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TEST$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TEST$4);
            }
            target.set(test);
        }
    }
    
    /**
     * Unsets the "test" attribute
     */
    public void unsetTest()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TEST$4);
        }
    }
}
