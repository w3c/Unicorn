/*
 * An XML document type.
 * Localname: context
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.ContextDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one context(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class ContextDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ContextDocument
{
    
    public ContextDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CONTEXT$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "context");
    
    
    /**
     * Gets the "context" element
     */
    public java.lang.String getContext()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONTEXT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "context" element
     */
    public org.apache.xmlbeans.XmlString xgetContext()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTEXT$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "context" element
     */
    public void setContext(java.lang.String context)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONTEXT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CONTEXT$0);
            }
            target.setStringValue(context);
        }
    }
    
    /**
     * Sets (as xml) the "context" element
     */
    public void xsetContext(org.apache.xmlbeans.XmlString context)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTEXT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CONTEXT$0);
            }
            target.set(context);
        }
    }
}
