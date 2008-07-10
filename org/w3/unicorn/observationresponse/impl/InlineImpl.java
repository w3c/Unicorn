/*
 * XML Type:  Inline
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.Inline
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * An XML Inline(@http://www.w3.org/unicorn/observationresponse).
 *
 * This is a complex type.
 */
public class InlineImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.Inline
{
    
    public InlineImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName A$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "a");
    private static final javax.xml.namespace.QName IMG$2 = 
        new javax.xml.namespace.QName("http://www.w3.org/unicorn/observationresponse", "img");
    
    
    /**
     * Gets array of all "a" elements
     */
    public org.w3.unicorn.observationresponse.ADocument.A[] getAArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(A$0, targetList);
            org.w3.unicorn.observationresponse.ADocument.A[] result = new org.w3.unicorn.observationresponse.ADocument.A[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "a" element
     */
    public org.w3.unicorn.observationresponse.ADocument.A getAArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ADocument.A target = null;
            target = (org.w3.unicorn.observationresponse.ADocument.A)get_store().find_element_user(A$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "a" element
     */
    public int sizeOfAArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(A$0);
        }
    }
    
    /**
     * Sets array of all "a" element
     */
    public void setAArray(org.w3.unicorn.observationresponse.ADocument.A[] aArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(aArray, A$0);
        }
    }
    
    /**
     * Sets ith "a" element
     */
    public void setAArray(int i, org.w3.unicorn.observationresponse.ADocument.A a)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ADocument.A target = null;
            target = (org.w3.unicorn.observationresponse.ADocument.A)get_store().find_element_user(A$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(a);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "a" element
     */
    public org.w3.unicorn.observationresponse.ADocument.A insertNewA(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ADocument.A target = null;
            target = (org.w3.unicorn.observationresponse.ADocument.A)get_store().insert_element_user(A$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "a" element
     */
    public org.w3.unicorn.observationresponse.ADocument.A addNewA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ADocument.A target = null;
            target = (org.w3.unicorn.observationresponse.ADocument.A)get_store().add_element_user(A$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "a" element
     */
    public void removeA(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(A$0, i);
        }
    }
    
    /**
     * Gets array of all "img" elements
     */
    public org.w3.unicorn.observationresponse.ImgDocument.Img[] getImgArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IMG$2, targetList);
            org.w3.unicorn.observationresponse.ImgDocument.Img[] result = new org.w3.unicorn.observationresponse.ImgDocument.Img[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "img" element
     */
    public org.w3.unicorn.observationresponse.ImgDocument.Img getImgArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ImgDocument.Img target = null;
            target = (org.w3.unicorn.observationresponse.ImgDocument.Img)get_store().find_element_user(IMG$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "img" element
     */
    public int sizeOfImgArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMG$2);
        }
    }
    
    /**
     * Sets array of all "img" element
     */
    public void setImgArray(org.w3.unicorn.observationresponse.ImgDocument.Img[] imgArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(imgArray, IMG$2);
        }
    }
    
    /**
     * Sets ith "img" element
     */
    public void setImgArray(int i, org.w3.unicorn.observationresponse.ImgDocument.Img img)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ImgDocument.Img target = null;
            target = (org.w3.unicorn.observationresponse.ImgDocument.Img)get_store().find_element_user(IMG$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(img);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "img" element
     */
    public org.w3.unicorn.observationresponse.ImgDocument.Img insertNewImg(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ImgDocument.Img target = null;
            target = (org.w3.unicorn.observationresponse.ImgDocument.Img)get_store().insert_element_user(IMG$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "img" element
     */
    public org.w3.unicorn.observationresponse.ImgDocument.Img addNewImg()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.unicorn.observationresponse.ImgDocument.Img target = null;
            target = (org.w3.unicorn.observationresponse.ImgDocument.Img)get_store().add_element_user(IMG$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "img" element
     */
    public void removeImg(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMG$2, i);
        }
    }
}
