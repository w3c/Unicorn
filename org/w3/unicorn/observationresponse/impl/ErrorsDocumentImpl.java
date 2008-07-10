/*
 * An XML document type.
 * Localname: errors
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.ErrorsDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one errors(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class ErrorsDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ErrorsDocument
{
    
    public ErrorsDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ERRORS$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "errors");
    
    
    /**
     * Gets the "errors" element
     */
    public org.w3.unicorn.observationresponse.ErrorsDocument.Errors getErrors()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ErrorsDocument.Errors target = null;
            target = (org.w3.unicorn.observationresponse.ErrorsDocument.Errors)get_store().find_element_user(ERRORS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "errors" element
     */
    public void setErrors(org.w3.unicorn.observationresponse.ErrorsDocument.Errors errors)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ErrorsDocument.Errors target = null;
            target = (org.w3.unicorn.observationresponse.ErrorsDocument.Errors)get_store().find_element_user(ERRORS$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.observationresponse.ErrorsDocument.Errors)get_store().add_element_user(ERRORS$0);
            }
            target.set(errors);
        }
    }
    
    /**
     * Appends and returns a new empty "errors" element
     */
    public org.w3.unicorn.observationresponse.ErrorsDocument.Errors addNewErrors()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ErrorsDocument.Errors target = null;
            target = (org.w3.unicorn.observationresponse.ErrorsDocument.Errors)get_store().add_element_user(ERRORS$0);
            return target;
        }
    }
    /**
     * An XML errors(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public static class ErrorsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ErrorsDocument.Errors
    {
        
        public ErrorsImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ERRORCOUNT$0 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "errorcount");
        private static final javax.xml.namespace.QName ERRORLIST$2 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "errorlist");
        private static final javax.xml.namespace.QName LANG$4 = 
            new javax.xml.namespace.QName("http://www.w3.org/XML/1998/namespace", "lang");
        
        
        /**
         * Gets the "errorcount" element
         */
        public java.math.BigInteger getErrorcount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORCOUNT$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getBigIntegerValue();
            }
        }
        
        /**
         * Gets (as xml) the "errorcount" element
         */
        public org.apache.xmlbeans.XmlInteger xgetErrorcount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInteger target = null;
                target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(ERRORCOUNT$0, 0);
                return target;
            }
        }
        
        /**
         * True if has "errorcount" element
         */
        public boolean isSetErrorcount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(ERRORCOUNT$0) != 0;
            }
        }
        
        /**
         * Sets the "errorcount" element
         */
        public void setErrorcount(java.math.BigInteger errorcount)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORCOUNT$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ERRORCOUNT$0);
                }
                target.setBigIntegerValue(errorcount);
            }
        }
        
        /**
         * Sets (as xml) the "errorcount" element
         */
        public void xsetErrorcount(org.apache.xmlbeans.XmlInteger errorcount)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInteger target = null;
                target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(ERRORCOUNT$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInteger)get_store().add_element_user(ERRORCOUNT$0);
                }
                target.set(errorcount);
            }
        }
        
        /**
         * Unsets the "errorcount" element
         */
        public void unsetErrorcount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(ERRORCOUNT$0, 0);
            }
        }
        
        /**
         * Gets array of all "errorlist" elements
         */
        public org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist[] getErrorlistArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(ERRORLIST$2, targetList);
                org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist[] result = new org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "errorlist" element
         */
        public org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist getErrorlistArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist target = null;
                target = (org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist)get_store().find_element_user(ERRORLIST$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Returns number of "errorlist" element
         */
        public int sizeOfErrorlistArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(ERRORLIST$2);
            }
        }
        
        /**
         * Sets array of all "errorlist" element
         */
        public void setErrorlistArray(org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist[] errorlistArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(errorlistArray, ERRORLIST$2);
            }
        }
        
        /**
         * Sets ith "errorlist" element
         */
        public void setErrorlistArray(int i, org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist errorlist)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist target = null;
                target = (org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist)get_store().find_element_user(ERRORLIST$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(errorlist);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "errorlist" element
         */
        public org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist insertNewErrorlist(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist target = null;
                target = (org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist)get_store().insert_element_user(ERRORLIST$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "errorlist" element
         */
        public org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist addNewErrorlist()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist target = null;
                target = (org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist)get_store().add_element_user(ERRORLIST$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "errorlist" element
         */
        public void removeErrorlist(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(ERRORLIST$2, i);
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
