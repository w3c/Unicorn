/*
 * XML Type:  inA
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.InA
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * An XML inA(@http://www.w3.org/unicorn/observationresponse).
 *
 * This is a complex type.
 */
public class InAImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.InA
{
    
    public InAImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IMG$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "img");
    
    
    /**
     * Gets the "img" element
     */
    public org.w3.unicorn.observationresponse.ImgDocument.Img getImg()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ImgDocument.Img target = null;
            target = (org.w3.unicorn.observationresponse.ImgDocument.Img)get_store().find_element_user(IMG$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "img" element
     */
    public void setImg(org.w3.unicorn.observationresponse.ImgDocument.Img img)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ImgDocument.Img target = null;
            target = (org.w3.unicorn.observationresponse.ImgDocument.Img)get_store().find_element_user(IMG$0, 0);
            if (target == null)
            {
                target = (org.w3.unicorn.observationresponse.ImgDocument.Img)get_store().add_element_user(IMG$0);
            }
            target.set(img);
        }
    }
    
    /**
     * Appends and returns a new empty "img" element
     */
    public org.w3.unicorn.observationresponse.ImgDocument.Img addNewImg()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ImgDocument.Img target = null;
            target = (org.w3.unicorn.observationresponse.ImgDocument.Img)get_store().add_element_user(IMG$0);
            return target;
        }
    }
}
