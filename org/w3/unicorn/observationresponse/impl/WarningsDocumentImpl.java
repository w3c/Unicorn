/*
 * An XML document type.
 * Localname: warnings
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.WarningsDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one warnings(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class WarningsDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.WarningsDocument
{
    
    public WarningsDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName WARNINGS$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "warnings");
    
    
    /**
     * Gets the "warnings" element
     */
    public org.w3.unicorn.observationresponse.WarningsDocument.Warnings getWarnings()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.WarningsDocument.Warnings target = null;
            target = (org.w3.unicorn.observationresponse.WarningsDocument.Warnings)get_store().find_element_user(WARNINGS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "warnings" element
     */
    public void setWarnings(org.w3.unicorn.observationresponse.WarningsDocument.Warnings warnings)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.WarningsDocument.Warnings target = null;
            target = (org.w3.unicorn.observationresponse.WarningsDocument.Warnings)get_store().find_element_user(WARNINGS$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.observationresponse.WarningsDocument.Warnings)get_store().add_element_user(WARNINGS$0);
            }
            target.set(warnings);
        }
    }
    
    /**
     * Appends and returns a new empty "warnings" element
     */
    public org.w3.unicorn.observationresponse.WarningsDocument.Warnings addNewWarnings()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.WarningsDocument.Warnings target = null;
            target = (org.w3.unicorn.observationresponse.WarningsDocument.Warnings)get_store().add_element_user(WARNINGS$0);
            return target;
        }
    }
    /**
     * An XML warnings(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public static class WarningsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.WarningsDocument.Warnings
    {
        
        public WarningsImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName WARNINGCOUNT$0 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "warningcount");
        private static final javax.xml.namespace.QName WARNINGLIST$2 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "warninglist");
        private static final javax.xml.namespace.QName LANG$4 = 
            new javax.xml.namespace.QName("http://www.w3.org/XML/1998/namespace", "lang");
        
        
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
         * True if has "warningcount" element
         */
        public boolean isSetWarningcount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(WARNINGCOUNT$0) != 0;
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
        
        /**
         * Unsets the "warningcount" element
         */
        public void unsetWarningcount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(WARNINGCOUNT$0, 0);
            }
        }
        
        /**
         * Gets array of all "warninglist" elements
         */
        public org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist[] getWarninglistArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(WARNINGLIST$2, targetList);
                org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist[] result = new org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "warninglist" element
         */
        public org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist getWarninglistArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist target = null;
                target = (org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist)get_store().find_element_user(WARNINGLIST$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Returns number of "warninglist" element
         */
        public int sizeOfWarninglistArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(WARNINGLIST$2);
            }
        }
        
        /**
         * Sets array of all "warninglist" element
         */
        public void setWarninglistArray(org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist[] warninglistArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(warninglistArray, WARNINGLIST$2);
            }
        }
        
        /**
         * Sets ith "warninglist" element
         */
        public void setWarninglistArray(int i, org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist warninglist)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist target = null;
                target = (org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist)get_store().find_element_user(WARNINGLIST$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(warninglist);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "warninglist" element
         */
        public org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist insertNewWarninglist(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist target = null;
                target = (org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist)get_store().insert_element_user(WARNINGLIST$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "warninglist" element
         */
        public org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist addNewWarninglist()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist target = null;
                target = (org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist)get_store().add_element_user(WARNINGLIST$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "warninglist" element
         */
        public void removeWarninglist(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(WARNINGLIST$2, i);
            }
        }
        
        /**
         * Gets the "lang" attribute
         */
        public java.lang.String getLang()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LANG$4);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "lang" attribute
         */
        public org.apache.xmlbeans.XmlLanguage xgetLang()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlLanguage target = null;
                target = (org.apache.xmlbeans.XmlLanguage)get_store().find_attribute_user(LANG$4);
                return target;
            }
        }
        
        /**
         * Sets the "lang" attribute
         */
        public void setLang(java.lang.String lang)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LANG$4);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LANG$4);
                }
                target.setStringValue(lang);
            }
        }
        
        /**
         * Sets (as xml) the "lang" attribute
         */
        public void xsetLang(org.apache.xmlbeans.XmlLanguage lang)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlLanguage target = null;
                target = (org.apache.xmlbeans.XmlLanguage)get_store().find_attribute_user(LANG$4);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlLanguage)get_store().add_attribute_user(LANG$4);
                }
                target.set(lang);
            }
        }
    }
}
