/*
 * An XML document type.
 * Localname: errorlist
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.ErrorlistDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one errorlist(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class ErrorlistDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ErrorlistDocument
{
    
    public ErrorlistDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ERRORLIST$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "errorlist");
    
    
    /**
     * Gets the "errorlist" element
     */
    public org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist getErrorlist()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist target = null;
            target = (org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist)get_store().find_element_user(ERRORLIST$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "errorlist" element
     */
    public void setErrorlist(org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist errorlist)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist target = null;
            target = (org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist)get_store().find_element_user(ERRORLIST$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist)get_store().add_element_user(ERRORLIST$0);
            }
            target.set(errorlist);
        }
    }
    
    /**
     * Appends and returns a new empty "errorlist" element
     */
    public org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist addNewErrorlist()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist target = null;
            target = (org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist)get_store().add_element_user(ERRORLIST$0);
            return target;
        }
    }
    /**
     * An XML errorlist(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public static class ErrorlistImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist
    {
        
        public ErrorlistImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName URI$0 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "uri");
        private static final javax.xml.namespace.QName ERRORCOUNT$2 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "errorcount");
        private static final javax.xml.namespace.QName ERROR$4 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "error");
        
        
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
         * Gets the "errorcount" element
         */
        public java.math.BigInteger getErrorcount()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORCOUNT$2, 0);
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
                target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(ERRORCOUNT$2, 0);
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
                return get_store().count_elements(ERRORCOUNT$2) != 0;
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORCOUNT$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ERRORCOUNT$2);
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
                target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(ERRORCOUNT$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInteger)get_store().add_element_user(ERRORCOUNT$2);
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
                get_store().remove_element(ERRORCOUNT$2, 0);
            }
        }
        
        /**
         * Gets array of all "error" elements
         */
        public org.w3.unicorn.observationresponse.ErrorDocument.Error[] getErrorArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(ERROR$4, targetList);
                org.w3.unicorn.observationresponse.ErrorDocument.Error[] result = new org.w3.unicorn.observationresponse.ErrorDocument.Error[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "error" element
         */
        public org.w3.unicorn.observationresponse.ErrorDocument.Error getErrorArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.ErrorDocument.Error target = null;
                target = (org.w3.unicorn.observationresponse.ErrorDocument.Error)get_store().find_element_user(ERROR$4, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Returns number of "error" element
         */
        public int sizeOfErrorArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(ERROR$4);
            }
        }
        
        /**
         * Sets array of all "error" element
         */
        public void setErrorArray(org.w3.unicorn.observationresponse.ErrorDocument.Error[] errorArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(errorArray, ERROR$4);
            }
        }
        
        /**
         * Sets ith "error" element
         */
        public void setErrorArray(int i, org.w3.unicorn.observationresponse.ErrorDocument.Error error)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.ErrorDocument.Error target = null;
                target = (org.w3.unicorn.observationresponse.ErrorDocument.Error)get_store().find_element_user(ERROR$4, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(error);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "error" element
         */
        public org.w3.unicorn.observationresponse.ErrorDocument.Error insertNewError(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.ErrorDocument.Error target = null;
                target = (org.w3.unicorn.observationresponse.ErrorDocument.Error)get_store().insert_element_user(ERROR$4, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "error" element
         */
        public org.w3.unicorn.observationresponse.ErrorDocument.Error addNewError()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.ErrorDocument.Error target = null;
                target = (org.w3.unicorn.observationresponse.ErrorDocument.Error)get_store().add_element_user(ERROR$4);
                return target;
            }
        }
        
        /**
         * Removes the ith "error" element
         */
        public void removeError(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(ERROR$4, i);
            }
        }
    }
}
