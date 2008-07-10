/*
 * An XML document type.
 * Localname: observationresponse
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.ObservationresponseDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one observationresponse(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class ObservationresponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ObservationresponseDocument
{
    
    public ObservationresponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName OBSERVATIONRESPONSE$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "observationresponse");
    
    
    /**
     * Gets the "observationresponse" element
     */
    public org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse getObservationresponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse target = null;
            target = (org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse)get_store().find_element_user(OBSERVATIONRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "observationresponse" element
     */
    public void setObservationresponse(org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse observationresponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse target = null;
            target = (org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse)get_store().find_element_user(OBSERVATIONRESPONSE$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse)get_store().add_element_user(OBSERVATIONRESPONSE$0);
            }
            target.set(observationresponse);
        }
    }
    
    /**
     * Appends and returns a new empty "observationresponse" element
     */
    public org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse addNewObservationresponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse target = null;
            target = (org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse)get_store().add_element_user(OBSERVATIONRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML observationresponse(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public static class ObservationresponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse
    {
        
        public ObservationresponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName URI$0 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "uri");
        private static final javax.xml.namespace.QName CHECKEDBY$2 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "checkedby");
        private static final javax.xml.namespace.QName VERSION$4 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "version");
        private static final javax.xml.namespace.QName DATE$6 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "date");
        private static final javax.xml.namespace.QName PASSED$8 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "passed");
        private static final javax.xml.namespace.QName RESULT$10 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "result");
        
        
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
         * Gets the "checkedby" element
         */
        public java.lang.String getCheckedby()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHECKEDBY$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "checkedby" element
         */
        public org.apache.xmlbeans.XmlAnyURI xgetCheckedby()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlAnyURI target = null;
                target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_element_user(CHECKEDBY$2, 0);
                return target;
            }
        }
        
        /**
         * True if has "checkedby" element
         */
        public boolean isSetCheckedby()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(CHECKEDBY$2) != 0;
            }
        }
        
        /**
         * Sets the "checkedby" element
         */
        public void setCheckedby(java.lang.String checkedby)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHECKEDBY$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CHECKEDBY$2);
                }
                target.setStringValue(checkedby);
            }
        }
        
        /**
         * Sets (as xml) the "checkedby" element
         */
        public void xsetCheckedby(org.apache.xmlbeans.XmlAnyURI checkedby)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlAnyURI target = null;
                target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_element_user(CHECKEDBY$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlAnyURI)get_store().add_element_user(CHECKEDBY$2);
                }
                target.set(checkedby);
            }
        }
        
        /**
         * Unsets the "checkedby" element
         */
        public void unsetCheckedby()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(CHECKEDBY$2, 0);
            }
        }
        
        /**
         * Gets the "version" element
         */
        public java.lang.String getVersion()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VERSION$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "version" element
         */
        public org.apache.xmlbeans.XmlString xgetVersion()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VERSION$4, 0);
                return target;
            }
        }
        
        /**
         * True if has "version" element
         */
        public boolean isSetVersion()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(VERSION$4) != 0;
            }
        }
        
        /**
         * Sets the "version" element
         */
        public void setVersion(java.lang.String version)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VERSION$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VERSION$4);
                }
                target.setStringValue(version);
            }
        }
        
        /**
         * Sets (as xml) the "version" element
         */
        public void xsetVersion(org.apache.xmlbeans.XmlString version)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VERSION$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(VERSION$4);
                }
                target.set(version);
            }
        }
        
        /**
         * Unsets the "version" element
         */
        public void unsetVersion()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(VERSION$4, 0);
            }
        }
        
        /**
         * Gets the "date" element
         */
        public java.util.Calendar getDate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATE$6, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getCalendarValue();
            }
        }
        
        /**
         * Gets (as xml) the "date" element
         */
        public org.apache.xmlbeans.XmlDateTime xgetDate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlDateTime target = null;
                target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATE$6, 0);
                return target;
            }
        }
        
        /**
         * True if has "date" element
         */
        public boolean isSetDate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(DATE$6) != 0;
            }
        }
        
        /**
         * Sets the "date" element
         */
        public void setDate(java.util.Calendar date)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATE$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATE$6);
                }
                target.setCalendarValue(date);
            }
        }
        
        /**
         * Sets (as xml) the "date" element
         */
        public void xsetDate(org.apache.xmlbeans.XmlDateTime date)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlDateTime target = null;
                target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATE$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATE$6);
                }
                target.set(date);
            }
        }
        
        /**
         * Unsets the "date" element
         */
        public void unsetDate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(DATE$6, 0);
            }
        }
        
        /**
         * Gets the "passed" element
         */
        public boolean getPassed()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSED$8, 0);
                if (target == null)
                {
                    return false;
                }
                return target.getBooleanValue();
            }
        }
        
        /**
         * Gets (as xml) the "passed" element
         */
        public org.apache.xmlbeans.XmlBoolean xgetPassed()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(PASSED$8, 0);
                return target;
            }
        }
        
        /**
         * True if has "passed" element
         */
        public boolean isSetPassed()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(PASSED$8) != 0;
            }
        }
        
        /**
         * Sets the "passed" element
         */
        public void setPassed(boolean passed)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSED$8, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PASSED$8);
                }
                target.setBooleanValue(passed);
            }
        }
        
        /**
         * Sets (as xml) the "passed" element
         */
        public void xsetPassed(org.apache.xmlbeans.XmlBoolean passed)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(PASSED$8, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(PASSED$8);
                }
                target.set(passed);
            }
        }
        
        /**
         * Unsets the "passed" element
         */
        public void unsetPassed()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(PASSED$8, 0);
            }
        }
        
        /**
         * Gets the "result" element
         */
        public org.w3.unicorn.observationresponse.ResultDocument.Result getResult()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.ResultDocument.Result target = null;
                target = (org.w3.unicorn.observationresponse.ResultDocument.Result)get_store().find_element_user(RESULT$10, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "result" element
         */
        public void setResult(org.w3.unicorn.observationresponse.ResultDocument.Result result)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.ResultDocument.Result target = null;
                target = (org.w3.unicorn.observationresponse.ResultDocument.Result)get_store().find_element_user(RESULT$10, 0);
                if (target == null)
                {
                    target = (org.w3.unicorn.observationresponse.ResultDocument.Result)get_store().add_element_user(RESULT$10);
                }
                target.set(result);
            }
        }
        
        /**
         * Appends and returns a new empty "result" element
         */
        public org.w3.unicorn.observationresponse.ResultDocument.Result addNewResult()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.ResultDocument.Result target = null;
                target = (org.w3.unicorn.observationresponse.ResultDocument.Result)get_store().add_element_user(RESULT$10);
                return target;
            }
        }
    }
}
