/*
 * An XML document type.
 * Localname: result
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.ResultDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one result(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class ResultDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ResultDocument
{
    
    public ResultDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName RESULT$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "result");
    
    
    /**
     * Gets the "result" element
     */
    public org.w3.unicorn.observationresponse.ResultDocument.Result getResult()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ResultDocument.Result target = null;
            target = (org.w3.unicorn.observationresponse.ResultDocument.Result)get_store().find_element_user(RESULT$0, 0);
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
            target = (org.w3.unicorn.observationresponse.ResultDocument.Result)get_store().find_element_user(RESULT$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.observationresponse.ResultDocument.Result)get_store().add_element_user(RESULT$0);
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
            target = (org.w3.unicorn.observationresponse.ResultDocument.Result)get_store().add_element_user(RESULT$0);
            return target;
        }
    }
    /**
     * An XML result(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public static class ResultImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ResultDocument.Result
    {
        
        public ResultImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ERRORS$0 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "errors");
        private static final javax.xml.namespace.QName WARNINGS$2 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "warnings");
        private static final javax.xml.namespace.QName INFORMATIONS$4 = 
            new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "informations");
        
        
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
         * True if has "errors" element
         */
        public boolean isSetErrors()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(ERRORS$0) != 0;
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
         * Unsets the "errors" element
         */
        public void unsetErrors()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(ERRORS$0, 0);
            }
        }
        
        /**
         * Gets the "warnings" element
         */
        public org.w3.unicorn.observationresponse.WarningsDocument.Warnings getWarnings()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.WarningsDocument.Warnings target = null;
                target = (org.w3.unicorn.observationresponse.WarningsDocument.Warnings)get_store().find_element_user(WARNINGS$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * True if has "warnings" element
         */
        public boolean isSetWarnings()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(WARNINGS$2) != 0;
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
                target = (org.w3.unicorn.observationresponse.WarningsDocument.Warnings)get_store().find_element_user(WARNINGS$2, 0);
                if (target == null)
                {
                    target = (org.w3.unicorn.observationresponse.WarningsDocument.Warnings)get_store().add_element_user(WARNINGS$2);
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
                target = (org.w3.unicorn.observationresponse.WarningsDocument.Warnings)get_store().add_element_user(WARNINGS$2);
                return target;
            }
        }
        
        /**
         * Unsets the "warnings" element
         */
        public void unsetWarnings()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(WARNINGS$2, 0);
            }
        }
        
        /**
         * Gets the "informations" element
         */
        public org.w3.unicorn.observationresponse.InformationsDocument.Informations getInformations()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.w3.unicorn.observationresponse.InformationsDocument.Informations target = null;
                target = (org.w3.unicorn.observationresponse.InformationsDocument.Informations)get_store().find_element_user(INFORMATIONS$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * True if has "informations" element
         */
        public boolean isSetInformations()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(INFORMATIONS$4) != 0;
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
                target = (org.w3.unicorn.observationresponse.InformationsDocument.Informations)get_store().find_element_user(INFORMATIONS$4, 0);
                if (target == null)
                {
                    target = (org.w3.unicorn.observationresponse.InformationsDocument.Informations)get_store().add_element_user(INFORMATIONS$4);
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
                target = (org.w3.unicorn.observationresponse.InformationsDocument.Informations)get_store().add_element_user(INFORMATIONS$4);
                return target;
            }
        }
        
        /**
         * Unsets the "informations" element
         */
        public void unsetInformations()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(INFORMATIONS$4, 0);
            }
        }
    }
}
