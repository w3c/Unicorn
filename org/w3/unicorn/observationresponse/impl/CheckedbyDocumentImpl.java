/*
 * An XML document type.
 * Localname: checkedby
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.CheckedbyDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one checkedby(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class CheckedbyDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.CheckedbyDocument
{
    
    public CheckedbyDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CHECKEDBY$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "checkedby");
    
    
    /**
     * Gets the "checkedby" element
     */
    public java.lang.String getCheckedby()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHECKEDBY$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "checkedby" element
     */
    public org.apache.xmlbeans.XmlAnyURI xgetCheckedby()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlAnyURI target = null;
            target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_element_user(CHECKEDBY$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "checkedby" element
     */
    public void setCheckedby(java.lang.String checkedby)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHECKEDBY$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CHECKEDBY$0);
            }
            target.setStringValue(checkedby);
        }
    }
    
    /**
     * Sets (as xml) the "checkedby" element
     */
    public void xsetCheckedby(org.apache.xmlbeans.XmlAnyURI checkedby)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlAnyURI target = null;
            target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_element_user(CHECKEDBY$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlAnyURI)get_store().add_element_user(CHECKEDBY$0);
            }
            target.set(checkedby);
        }
    }
}
