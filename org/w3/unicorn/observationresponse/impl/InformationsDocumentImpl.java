/*
 * An XML document type.
 * Localname: informations
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.InformationsDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one informations(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class InformationsDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.InformationsDocument
{
    
    public InformationsDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INFORMATIONS$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "informations");
    
    
    /**
     * Gets the "informations" element
     */
    public org.w3.unicorn.observationresponse.InformationsDocument.Informations getInformations()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.InformationsDocument.Informations target = null;
            target = (org.w3.unicorn.observationresponse.InformationsDocument.Informations)get_store().find_element_user(INFORMATIONS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "informations" element
     */
    public void setInformations(org.w3.unicorn.observationresponse.InformationsDocument.Informations informations)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.InformationsDocument.Informations target = null;
            target = (org.w3.unicorn.observationresponse.InformationsDocument.Informations)get_store().find_element_user(INFORMATIONS$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.observationresponse.InformationsDocument.Informations)get_store().add_element_user(INFORMATIONS$0);
            }
            target.set(informations);
        }
    }
    
    /**
     * Appends and returns a new empty "informations" element
     */
    public org.w3.unicorn.observationresponse.InformationsDocument.Informations addNewInformations()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.InformationsDocument.Informations target = null;
            target = (org.w3.unicorn.observationresponse.InformationsDocument.Informations)get_store().add_element_user(INFORMATIONS$0);
            return target;
        }
    }
    /**
     * An XML informations(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public static class InformationsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.InformationsDocument.Informations
    {
        
        public InformationsImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName INFOCOUNT$0 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "infocount");
        private static final javax.xml.namespace.QName INFOLIST$2 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "infolist");
        private static final javax.xml.namespace.QName LANG$4 = 
            new javax.xml.namespace.QName("http://www.w3.org/XML/1998/namespace", "lang");
        
        
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
         * True if has "infocount" element
         */
        public boolean isSetInfocount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(INFOCOUNT$0) != 0;
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
        
        /**
         * Unsets the "infocount" element
         */
        public void unsetInfocount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(INFOCOUNT$0, 0);
            }
        }
        
        /**
         * Gets array of all "infolist" elements
         */
        public org.w3.unicorn.observationresponse.InfolistDocument.Infolist[] getInfolistArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(INFOLIST$2, targetList);
                org.w3.unicorn.observationresponse.InfolistDocument.Infolist[] result = new org.w3.unicorn.observationresponse.InfolistDocument.Infolist[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "infolist" element
         */
        public org.w3.unicorn.observationresponse.InfolistDocument.Infolist getInfolistArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.InfolistDocument.Infolist target = null;
                target = (org.w3.unicorn.observationresponse.InfolistDocument.Infolist)get_store().find_element_user(INFOLIST$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Returns number of "infolist" element
         */
        public int sizeOfInfolistArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(INFOLIST$2);
            }
        }
        
        /**
         * Sets array of all "infolist" element
         */
        public void setInfolistArray(org.w3.unicorn.observationresponse.InfolistDocument.Infolist[] infolistArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(infolistArray, INFOLIST$2);
            }
        }
        
        /**
         * Sets ith "infolist" element
         */
        public void setInfolistArray(int i, org.w3.unicorn.observationresponse.InfolistDocument.Infolist infolist)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.InfolistDocument.Infolist target = null;
                target = (org.w3.unicorn.observationresponse.InfolistDocument.Infolist)get_store().find_element_user(INFOLIST$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(infolist);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "infolist" element
         */
        public org.w3.unicorn.observationresponse.InfolistDocument.Infolist insertNewInfolist(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.InfolistDocument.Infolist target = null;
                target = (org.w3.unicorn.observationresponse.InfolistDocument.Infolist)get_store().insert_element_user(INFOLIST$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "infolist" element
         */
        public org.w3.unicorn.observationresponse.InfolistDocument.Infolist addNewInfolist()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.InfolistDocument.Infolist target = null;
                target = (org.w3.unicorn.observationresponse.InfolistDocument.Infolist)get_store().add_element_user(INFOLIST$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "infolist" element
         */
        public void removeInfolist(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(INFOLIST$2, i);
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
