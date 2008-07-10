/*
 * An XML document type.
 * Localname: info
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.InfoDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one info(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class InfoDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.InfoDocument
{
    
    public InfoDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INFO$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "info");
    
    
    /**
     * Gets the "info" element
     */
    public org.w3.unicorn.observationresponse.InfoDocument.Info getInfo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.InfoDocument.Info target = null;
            target = (org.w3.unicorn.observationresponse.InfoDocument.Info)get_store().find_element_user(INFO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "info" element
     */
    public void setInfo(org.w3.unicorn.observationresponse.InfoDocument.Info info)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.InfoDocument.Info target = null;
            target = (org.w3.unicorn.observationresponse.InfoDocument.Info)get_store().find_element_user(INFO$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.observationresponse.InfoDocument.Info)get_store().add_element_user(INFO$0);
            }
            target.set(info);
        }
    }
    
    /**
     * Appends and returns a new empty "info" element
     */
    public org.w3.unicorn.observationresponse.InfoDocument.Info addNewInfo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.InfoDocument.Info target = null;
            target = (org.w3.unicorn.observationresponse.InfoDocument.Info)get_store().add_element_user(INFO$0);
            return target;
        }
    }
    /**
     * An XML info(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public static class InfoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.InfoDocument.Info
    {
        
        public InfoImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName LINE$0 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "line");
        private static final javax.xml.namespace.QName COLUMN$2 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "column");
        private static final javax.xml.namespace.QName CONTEXT$4 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "context");
        private static final javax.xml.namespace.QName MESSAGE$6 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "message");
        private static final javax.xml.namespace.QName LONGMESSAGE$8 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "longmessage");
        
        
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
         * True if has "line" element
         */
        public boolean isSetLine()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(LINE$0) != 0;
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
        
        /**
         * Unsets the "line" element
         */
        public void unsetLine()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(LINE$0, 0);
            }
        }
        
        /**
         * Gets the "column" element
         */
        public java.math.BigInteger getColumn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLUMN$2, 0);
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
                target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().find_element_user(COLUMN$2, 0);
                return target;
            }
        }
        
        /**
         * True if has "column" element
         */
        public boolean isSetColumn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(COLUMN$2) != 0;
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLUMN$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COLUMN$2);
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
                target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().find_element_user(COLUMN$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().add_element_user(COLUMN$2);
                }
                target.set(column);
            }
        }
        
        /**
         * Unsets the "column" element
         */
        public void unsetColumn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(COLUMN$2, 0);
            }
        }
        
        /**
         * Gets the "context" element
         */
        public java.lang.String getContext()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONTEXT$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "context" element
         */
        public org.apache.xmlbeans.XmlString xgetContext()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTEXT$4, 0);
                return target;
            }
        }
        
        /**
         * True if has "context" element
         */
        public boolean isSetContext()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(CONTEXT$4) != 0;
            }
        }
        
        /**
         * Sets the "context" element
         */
        public void setContext(java.lang.String context)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONTEXT$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CONTEXT$4);
                }
                target.setStringValue(context);
            }
        }
        
        /**
         * Sets (as xml) the "context" element
         */
        public void xsetContext(org.apache.xmlbeans.XmlString context)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTEXT$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CONTEXT$4);
                }
                target.set(context);
            }
        }
        
        /**
         * Unsets the "context" element
         */
        public void unsetContext()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(CONTEXT$4, 0);
            }
        }
        
        /**
         * Gets array of all "message" elements
         */
        public java.lang.String[] getMessageArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(MESSAGE$6, targetList);
                java.lang.String[] result = new java.lang.String[targetList.size()];
                for (int i = 0, len = targetList.size() ; i < len ; i++)
                    result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
                return result;
            }
        }
        
        /**
         * Gets ith "message" element
         */
        public java.lang.String getMessageArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGE$6, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) array of all "message" elements
         */
        public org.apache.xmlbeans.XmlString[] xgetMessageArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(MESSAGE$6, targetList);
                org.apache.xmlbeans.XmlString[] result = new org.apache.xmlbeans.XmlString[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets (as xml) ith "message" element
         */
        public org.apache.xmlbeans.XmlString xgetMessageArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$6, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return (org.apache.xmlbeans.XmlString)target;
            }
        }
        
        /**
         * Returns number of "message" element
         */
        public int sizeOfMessageArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(MESSAGE$6);
            }
        }
        
        /**
         * Sets array of all "message" element
         */
        public void setMessageArray(java.lang.String[] messageArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(messageArray, MESSAGE$6);
            }
        }
        
        /**
         * Sets ith "message" element
         */
        public void setMessageArray(int i, java.lang.String message)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGE$6, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setStringValue(message);
            }
        }
        
        /**
         * Sets (as xml) array of all "message" element
         */
        public void xsetMessageArray(org.apache.xmlbeans.XmlString[]messageArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(messageArray, MESSAGE$6);
            }
        }
        
        /**
         * Sets (as xml) ith "message" element
         */
        public void xsetMessageArray(int i, org.apache.xmlbeans.XmlString message)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$6, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(message);
            }
        }
        
        /**
         * Inserts the value as the ith "message" element
         */
        public void insertMessage(int i, java.lang.String message)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = 
                    (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(MESSAGE$6, i);
                target.setStringValue(message);
            }
        }
        
        /**
         * Appends the value as the last "message" element
         */
        public void addMessage(java.lang.String message)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MESSAGE$6);
                target.setStringValue(message);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "message" element
         */
        public org.apache.xmlbeans.XmlString insertNewMessage(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(MESSAGE$6, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "message" element
         */
        public org.apache.xmlbeans.XmlString addNewMessage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MESSAGE$6);
                return target;
            }
        }
        
        /**
         * Removes the ith "message" element
         */
        public void removeMessage(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(MESSAGE$6, i);
            }
        }
        
        /**
         * Gets array of all "longmessage" elements
         */
        public org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage[] getLongmessageArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(LONGMESSAGE$8, targetList);
                org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage[] result = new org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "longmessage" element
         */
        public org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage getLongmessageArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage target = null;
                target = (org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage)get_store().find_element_user(LONGMESSAGE$8, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Returns number of "longmessage" element
         */
        public int sizeOfLongmessageArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(LONGMESSAGE$8);
            }
        }
        
        /**
         * Sets array of all "longmessage" element
         */
        public void setLongmessageArray(org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage[] longmessageArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(longmessageArray, LONGMESSAGE$8);
            }
        }
        
        /**
         * Sets ith "longmessage" element
         */
        public void setLongmessageArray(int i, org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage longmessage)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage target = null;
                target = (org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage)get_store().find_element_user(LONGMESSAGE$8, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(longmessage);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "longmessage" element
         */
        public org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage insertNewLongmessage(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage target = null;
                target = (org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage)get_store().insert_element_user(LONGMESSAGE$8, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "longmessage" element
         */
        public org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage addNewLongmessage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage target = null;
                target = (org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage)get_store().add_element_user(LONGMESSAGE$8);
                return target;
            }
        }
        
        /**
         * Removes the ith "longmessage" element
         */
        public void removeLongmessage(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(LONGMESSAGE$8, i);
            }
        }
    }
}
