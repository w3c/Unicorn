/*
 * An XML document type.
 * Localname: column
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.ColumnDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one column(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class ColumnDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ColumnDocument
{
    
    public ColumnDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName COLUMN$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "column");
    
    
    /**
     * Gets the "column" element
     */
    public java.math.BigInteger getColumn()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLUMN$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "column" element
     */
    public org.apache.xmlbeans.XmlNonNegativeInteger xgetColumn()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlNonNegativeInteger target = null;
            target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().find_element_user(COLUMN$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "column" element
     */
    public void setColumn(java.math.BigInteger column)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLUMN$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COLUMN$0);
            }
            target.setBigIntegerValue(column);
        }
    }
    
    /**
     * Sets (as xml) the "column" element
     */
    public void xsetColumn(org.apache.xmlbeans.XmlNonNegativeInteger column)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlNonNegativeInteger target = null;
            target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().find_element_user(COLUMN$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().add_element_user(COLUMN$0);
            }
            target.set(column);
        }
    }
}
