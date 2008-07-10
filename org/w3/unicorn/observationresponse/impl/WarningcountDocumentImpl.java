/*
 * An XML document type.
 * Localname: warningcount
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.WarningcountDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one warningcount(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class WarningcountDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.WarningcountDocument
{
    
    public WarningcountDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName WARNINGCOUNT$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "warningcount");
    
    
    /**
     * Gets the "warningcount" element
     */
    public java.math.BigInteger getWarningcount()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WARNINGCOUNT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "warningcount" element
     */
    public org.apache.xmlbeans.XmlInteger xgetWarningcount()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInteger target = null;
            target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(WARNINGCOUNT$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "warningcount" element
     */
    public void setWarningcount(java.math.BigInteger warningcount)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WARNINGCOUNT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WARNINGCOUNT$0);
            }
            target.setBigIntegerValue(warningcount);
        }
    }
    
    /**
     * Sets (as xml) the "warningcount" element
     */
    public void xsetWarningcount(org.apache.xmlbeans.XmlInteger warningcount)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInteger target = null;
            target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(WARNINGCOUNT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInteger)get_store().add_element_user(WARNINGCOUNT$0);
            }
            target.set(warningcount);
        }
    }
}
