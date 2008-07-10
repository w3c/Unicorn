/*
 * An XML document type.
 * Localname: infocount
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.InfocountDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one infocount(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class InfocountDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.InfocountDocument
{
    
    public InfocountDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INFOCOUNT$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "infocount");
    
    
    /**
     * Gets the "infocount" element
     */
    public java.math.BigInteger getInfocount()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INFOCOUNT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "infocount" element
     */
    public org.apache.xmlbeans.XmlInteger xgetInfocount()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInteger target = null;
            target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(INFOCOUNT$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "infocount" element
     */
    public void setInfocount(java.math.BigInteger infocount)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INFOCOUNT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INFOCOUNT$0);
            }
            target.setBigIntegerValue(infocount);
        }
    }
    
    /**
     * Sets (as xml) the "infocount" element
     */
    public void xsetInfocount(org.apache.xmlbeans.XmlInteger infocount)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInteger target = null;
            target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(INFOCOUNT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInteger)get_store().add_element_user(INFOCOUNT$0);
            }
            target.set(infocount);
        }
    }
}
