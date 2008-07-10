/*
 * An XML document type.
 * Localname: level
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.LevelDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one level(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class LevelDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.LevelDocument
{
    
    public LevelDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LEVEL$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "level");
    
    
    /**
     * Gets the "level" element
     */
    public int getLevel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LEVEL$0, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "level" element
     */
    public org.w3.unicorn.observationresponse.TWarningLevels xgetLevel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.TWarningLevels target = null;
            target = (org.w3.unicorn.observationresponse.TWarningLevels)get_store().find_element_user(LEVEL$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "level" element
     */
    public void setLevel(int level)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LEVEL$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LEVEL$0);
            }
            target.setIntValue(level);
        }
    }
    
    /**
     * Sets (as xml) the "level" element
     */
    public void xsetLevel(org.w3.unicorn.observationresponse.TWarningLevels level)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.TWarningLevels target = null;
            target = (org.w3.unicorn.observationresponse.TWarningLevels)get_store().find_element_user(LEVEL$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.observationresponse.TWarningLevels)get_store().add_element_user(LEVEL$0);
            }
            target.set(level);
        }
    }
}
