/*
 * An XML document type.
 * Localname: errorcount
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.ErrorcountDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one errorcount(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class ErrorcountDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ErrorcountDocument
{
    
    public ErrorcountDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ERRORCOUNT$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "errorcount");
    
    
    /**
     * Gets the "errorcount" element
     */
    public java.math.BigInteger getErrorcount()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORCOUNT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "errorcount" element
     */
    public org.apache.xmlbeans.XmlInteger xgetErrorcount()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInteger target = null;
            target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(ERRORCOUNT$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "errorcount" element
     */
    public void setErrorcount(java.math.BigInteger errorcount)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORCOUNT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ERRORCOUNT$0);
            }
            target.setBigIntegerValue(errorcount);
        }
    }
    
    /**
     * Sets (as xml) the "errorcount" element
     */
    public void xsetErrorcount(org.apache.xmlbeans.XmlInteger errorcount)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInteger target = null;
            target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(ERRORCOUNT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInteger)get_store().add_element_user(ERRORCOUNT$0);
            }
            target.set(errorcount);
        }
    }
}
