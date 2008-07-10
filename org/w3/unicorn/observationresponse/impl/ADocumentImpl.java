/*
 * An XML document type.
 * Localname: a
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.ADocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one a(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class ADocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ADocument
{
    
    public ADocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName A$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "a");
    
    
    /**
     * Gets the "a" element
     */
    public org.w3.unicorn.observationresponse.ADocument.A getA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ADocument.A target = null;
            target = (org.w3.unicorn.observationresponse.ADocument.A)get_store().find_element_user(A$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "a" element
     */
    public void setA(org.w3.unicorn.observationresponse.ADocument.A a)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ADocument.A target = null;
            target = (org.w3.unicorn.observationresponse.ADocument.A)get_store().find_element_user(A$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.observationresponse.ADocument.A)get_store().add_element_user(A$0);
            }
            target.set(a);
        }
    }
    
    /**
     * Appends and returns a new empty "a" element
     */
    public org.w3.unicorn.observationresponse.ADocument.A addNewA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ADocument.A target = null;
            target = (org.w3.unicorn.observationresponse.ADocument.A)get_store().add_element_user(A$0);
            return target;
        }
    }
    /**
     * An XML a(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public static class AImpl extends org.w3.unicorn.observationresponse.impl.InAImpl implements org.w3.unicorn.observationresponse.ADocument.A
    {
        
        public AImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName HREF$0 = 
            new javax.xml.namespace.QName("", "href");
        
        
        /**
         * Gets the "href" attribute
         */
        public java.lang.String getHref()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(HREF$0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "href" attribute
         */
        public org.apache.xmlbeans.XmlAnyURI xgetHref()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlAnyURI target = null;
                target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_attribute_user(HREF$0);
                return target;
            }
        }
        
        /**
         * True if has "href" attribute
         */
        public boolean isSetHref()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().find_attribute_user(HREF$0) != null;
            }
        }
        
        /**
         * Sets the "href" attribute
         */
        public void setHref(java.lang.String href)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(HREF$0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(HREF$0);
                }
                target.setStringValue(href);
            }
        }
        
        /**
         * Sets (as xml) the "href" attribute
         */
        public void xsetHref(org.apache.xmlbeans.XmlAnyURI href)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlAnyURI target = null;
                target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_attribute_user(HREF$0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlAnyURI)get_store().add_attribute_user(HREF$0);
                }
                target.set(href);
            }
        }
        
        /**
         * Unsets the "href" attribute
         */
        public void unsetHref()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_attribute(HREF$0);
            }
        }
    }
}
