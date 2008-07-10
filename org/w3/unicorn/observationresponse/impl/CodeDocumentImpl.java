/*
 * An XML document type.
 * Localname: code
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.CodeDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one code(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class CodeDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.CodeDocument
{
    
    public CodeDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODE$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "code");
    
    
    /**
     * Gets the "code" element
     */
    public org.w3.unicorn.observationresponse.CodeDocument.Code getCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.CodeDocument.Code target = null;
            target = (org.w3.unicorn.observationresponse.CodeDocument.Code)get_store().find_element_user(CODE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "code" element
     */
    public void setCode(org.w3.unicorn.observationresponse.CodeDocument.Code code)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.CodeDocument.Code target = null;
            target = (org.w3.unicorn.observationresponse.CodeDocument.Code)get_store().find_element_user(CODE$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.observationresponse.CodeDocument.Code)get_store().add_element_user(CODE$0);
            }
            target.set(code);
        }
    }
    
    /**
     * Appends and returns a new empty "code" element
     */
    public org.w3.unicorn.observationresponse.CodeDocument.Code addNewCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.CodeDocument.Code target = null;
            target = (org.w3.unicorn.observationresponse.CodeDocument.Code)get_store().add_element_user(CODE$0);
            return target;
        }
    }
    /**
     * An XML code(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public static class CodeImpl extends org.w3.unicorn.observationresponse.impl.InlineImpl implements org.w3.unicorn.observationresponse.CodeDocument.Code
    {
        
        public CodeImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        
    }
}
