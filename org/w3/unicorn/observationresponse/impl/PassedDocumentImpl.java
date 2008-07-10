/*
 * An XML document type.
 * Localname: passed
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.PassedDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one passed(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class PassedDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.PassedDocument
{
    
    public PassedDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PASSED$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "passed");
    
    
    /**
     * Gets the "passed" element
     */
    public boolean getPassed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSED$0, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "passed" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetPassed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(PASSED$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "passed" element
     */
    public void setPassed(boolean passed)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSED$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PASSED$0);
            }
            target.setBooleanValue(passed);
        }
    }
    
    /**
     * Sets (as xml) the "passed" element
     */
    public void xsetPassed(org.apache.xmlbeans.XmlBoolean passed)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(PASSED$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(PASSED$0);
            }
            target.set(passed);
        }
    }
}
