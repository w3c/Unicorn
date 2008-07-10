/*
 * An XML document type.
 * Localname: infolist
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.InfolistDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one infolist(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class InfolistDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.InfolistDocument
{
    
    public InfolistDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INFOLIST$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "infolist");
    
    
    /**
     * Gets the "infolist" element
     */
    public org.w3.unicorn.observationresponse.InfolistDocument.Infolist getInfolist()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.InfolistDocument.Infolist target = null;
            target = (org.w3.unicorn.observationresponse.InfolistDocument.Infolist)get_store().find_element_user(INFOLIST$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "infolist" element
     */
    public void setInfolist(org.w3.unicorn.observationresponse.InfolistDocument.Infolist infolist)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.InfolistDocument.Infolist target = null;
            target = (org.w3.unicorn.observationresponse.InfolistDocument.Infolist)get_store().find_element_user(INFOLIST$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.observationresponse.InfolistDocument.Infolist)get_store().add_element_user(INFOLIST$0);
            }
            target.set(infolist);
        }
    }
    
    /**
     * Appends and returns a new empty "infolist" element
     */
    public org.w3.unicorn.observationresponse.InfolistDocument.Infolist addNewInfolist()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.InfolistDocument.Infolist target = null;
            target = (org.w3.unicorn.observationresponse.InfolistDocument.Infolist)get_store().add_element_user(INFOLIST$0);
            return target;
        }
    }
    /**
     * An XML infolist(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public static class InfolistImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.InfolistDocument.Infolist
    {
        
        public InfolistImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName URI$0 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "uri");
        private static final javax.xml.namespace.QName INFOCOUNT$2 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "infocount");
        private static final javax.xml.namespace.QName INFO$4 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "info");
        
        
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
         * Gets the "infocount" element
         */
        public java.math.BigInteger getInfocount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INFOCOUNT$2, 0);
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
                target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(INFOCOUNT$2, 0);
                return target;
            }
        }
        
        /**
         * True if has "infocount" element
         */
        public boolean isSetInfocount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(INFOCOUNT$2) != 0;
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INFOCOUNT$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INFOCOUNT$2);
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
                target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(INFOCOUNT$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInteger)get_store().add_element_user(INFOCOUNT$2);
                }
                target.set(infocount);
            }
        }
        
        /**
         * Unsets the "infocount" element
         */
        public void unsetInfocount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(INFOCOUNT$2, 0);
            }
        }
        
        /**
         * Gets array of all "info" elements
         */
        public org.w3.unicorn.observationresponse.InfoDocument.Info[] getInfoArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(INFO$4, targetList);
                org.w3.unicorn.observationresponse.InfoDocument.Info[] result = new org.w3.unicorn.observationresponse.InfoDocument.Info[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "info" element
         */
        public org.w3.unicorn.observationresponse.InfoDocument.Info getInfoArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.InfoDocument.Info target = null;
                target = (org.w3.unicorn.observationresponse.InfoDocument.Info)get_store().find_element_user(INFO$4, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Returns number of "info" element
         */
        public int sizeOfInfoArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(INFO$4);
            }
        }
        
        /**
         * Sets array of all "info" element
         */
        public void setInfoArray(org.w3.unicorn.observationresponse.InfoDocument.Info[] infoArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(infoArray, INFO$4);
            }
        }
        
        /**
         * Sets ith "info" element
         */
        public void setInfoArray(int i, org.w3.unicorn.observationresponse.InfoDocument.Info info)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.InfoDocument.Info target = null;
                target = (org.w3.unicorn.observationresponse.InfoDocument.Info)get_store().find_element_user(INFO$4, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(info);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "info" element
         */
        public org.w3.unicorn.observationresponse.InfoDocument.Info insertNewInfo(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.InfoDocument.Info target = null;
                target = (org.w3.unicorn.observationresponse.InfoDocument.Info)get_store().insert_element_user(INFO$4, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "info" element
         */
        public org.w3.unicorn.observationresponse.InfoDocument.Info addNewInfo()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.InfoDocument.Info target = null;
                target = (org.w3.unicorn.observationresponse.InfoDocument.Info)get_store().add_element_user(INFO$4);
                return target;
            }
        }
        
        /**
         * Removes the ith "info" element
         */
        public void removeInfo(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(INFO$4, i);
            }
        }
    }
}
