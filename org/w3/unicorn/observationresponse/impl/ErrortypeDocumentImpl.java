/*
 * An XML document type.
 * Localname: errortype
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.ErrortypeDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one errortype(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class ErrortypeDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ErrortypeDocument
{
    
    public ErrortypeDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ERRORTYPE$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "errortype");
    
    
    /**
     * Gets the "errortype" element
     */
    public java.lang.String getErrortype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORTYPE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "errortype" element
     */
    public org.apache.xmlbeans.XmlString xgetErrortype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORTYPE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "errortype" element
     */
    public void setErrortype(java.lang.String errortype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORTYPE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ERRORTYPE$0);
            }
            target.setStringValue(errortype);
        }
    }
    
    /**
     * Sets (as xml) the "errortype" element
     */
    public void xsetErrortype(org.apache.xmlbeans.XmlString errortype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORTYPE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ERRORTYPE$0);
            }
            target.set(errortype);
        }
    }
}
