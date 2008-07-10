/*
 * An XML document type.
 * Localname: warninglist
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.WarninglistDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one warninglist(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class WarninglistDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.WarninglistDocument
{
    
    public WarninglistDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName WARNINGLIST$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "warninglist");
    
    
    /**
     * Gets the "warninglist" element
     */
    public org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist getWarninglist()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist target = null;
            target = (org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist)get_store().find_element_user(WARNINGLIST$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "warninglist" element
     */
    public void setWarninglist(org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist warninglist)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist target = null;
            target = (org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist)get_store().find_element_user(WARNINGLIST$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist)get_store().add_element_user(WARNINGLIST$0);
            }
            target.set(warninglist);
        }
    }
    
    /**
     * Appends and returns a new empty "warninglist" element
     */
    public org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist addNewWarninglist()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist target = null;
            target = (org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist)get_store().add_element_user(WARNINGLIST$0);
            return target;
        }
    }
    /**
     * An XML warninglist(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public static class WarninglistImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist
    {
        
        public WarninglistImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName URI$0 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "uri");
        private static final javax.xml.namespace.QName WARNINGCOUNT$2 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "warningcount");
        private static final javax.xml.namespace.QName WARNING$4 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "warning");
        
        
        /**
         * Gets the "uri" element
         */
        public java.lang.String getUri()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(URI$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "uri" element
         */
        public org.apache.xmlbeans.XmlAnyURI xgetUri()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlAnyURI target = null;
                target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_element_user(URI$0, 0);
                return target;
            }
        }
        
        /**
         * Sets the "uri" element
         */
        public void setUri(java.lang.String uri)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(URI$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(URI$0);
                }
                target.setStringValue(uri);
            }
        }
        
        /**
         * Sets (as xml) the "uri" element
         */
        public void xsetUri(org.apache.xmlbeans.XmlAnyURI uri)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlAnyURI target = null;
                target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_element_user(URI$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlAnyURI)get_store().add_element_user(URI$0);
                }
                target.set(uri);
            }
        }
        
        /**
         * Gets the "warningcount" element
         */
        public java.math.BigInteger getWarningcount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WARNINGCOUNT$2, 0);
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
                target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(WARNINGCOUNT$2, 0);
                return target;
            }
        }
        
        /**
         * True if has "warningcount" element
         */
        public boolean isSetWarningcount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(WARNINGCOUNT$2) != 0;
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WARNINGCOUNT$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WARNINGCOUNT$2);
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
                target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(WARNINGCOUNT$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInteger)get_store().add_element_user(WARNINGCOUNT$2);
                }
                target.set(warningcount);
            }
        }
        
        /**
         * Unsets the "warningcount" element
         */
        public void unsetWarningcount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(WARNINGCOUNT$2, 0);
            }
        }
        
        /**
         * Gets array of all "warning" elements
         */
        public org.w3.unicorn.observationresponse.WarningDocument.Warning[] getWarningArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(WARNING$4, targetList);
                org.w3.unicorn.observationresponse.WarningDocument.Warning[] result = new org.w3.unicorn.observationresponse.WarningDocument.Warning[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "warning" element
         */
        public org.w3.unicorn.observationresponse.WarningDocument.Warning getWarningArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.WarningDocument.Warning target = null;
                target = (org.w3.unicorn.observationresponse.WarningDocument.Warning)get_store().find_element_user(WARNING$4, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Returns number of "warning" element
         */
        public int sizeOfWarningArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(WARNING$4);
            }
        }
        
        /**
         * Sets array of all "warning" element
         */
        public void setWarningArray(org.w3.unicorn.observationresponse.WarningDocument.Warning[] warningArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(warningArray, WARNING$4);
            }
        }
        
        /**
         * Sets ith "warning" element
         */
        public void setWarningArray(int i, org.w3.unicorn.observationresponse.WarningDocument.Warning warning)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.WarningDocument.Warning target = null;
                target = (org.w3.unicorn.observationresponse.WarningDocument.Warning)get_store().find_element_user(WARNING$4, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(warning);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "warning" element
         */
        public org.w3.unicorn.observationresponse.WarningDocument.Warning insertNewWarning(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.WarningDocument.Warning target = null;
                target = (org.w3.unicorn.observationresponse.WarningDocument.Warning)get_store().insert_element_user(WARNING$4, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "warning" element
         */
        public org.w3.unicorn.observationresponse.WarningDocument.Warning addNewWarning()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.WarningDocument.Warning target = null;
                target = (org.w3.unicorn.observationresponse.WarningDocument.Warning)get_store().add_element_user(WARNING$4);
                return target;
            }
        }
        
        /**
         * Removes the ith "warning" element
         */
        public void removeWarning(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(WARNING$4, i);
            }
        }
    }
}
