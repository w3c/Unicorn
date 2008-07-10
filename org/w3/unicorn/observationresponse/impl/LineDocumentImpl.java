/*
 * An XML document type.
 * Localname: line
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.LineDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one line(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class LineDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.LineDocument
{
    
    public LineDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LINE$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "line");
    
    
    /**
     * Gets the "line" element
     */
    public java.math.BigInteger getLine()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LINE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "line" element
     */
    public org.apache.xmlbeans.XmlNonNegativeInteger xgetLine()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlNonNegativeInteger target = null;
            target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().find_element_user(LINE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "line" element
     */
    public void setLine(java.math.BigInteger line)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LINE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LINE$0);
            }
            target.setBigIntegerValue(line);
        }
    }
    
    /**
     * Sets (as xml) the "line" element
     */
    public void xsetLine(org.apache.xmlbeans.XmlNonNegativeInteger line)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlNonNegativeInteger target = null;
            target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().find_element_user(LINE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().add_element_user(LINE$0);
            }
            target.set(line);
        }
    }
}
