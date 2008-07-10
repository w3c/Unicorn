/*
 * An XML document type.
 * Localname: img
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.ImgDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse.impl;
/**
 * A document containing one img(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public class ImgDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ImgDocument
{
    
    public ImgDocumentImpl(org.apache.xmlbeans.SchemaType sType)
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
    /**
     * An XML img(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public static class ImgImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.unicorn.observationresponse.ImgDocument.Img
    {
        
        public ImgImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName SRC$0 = 
            new javax.xml.namespace.QName("", "src");
        private static final javax.xml.namespace.QName ALT$2 = 
            new javax.xml.namespace.QName("", "alt");
        private static final javax.xml.namespace.QName NAME$4 = 
            new javax.xml.namespace.QName("", "name");
        private static final javax.xml.namespace.QName LONGDESC$6 = 
            new javax.xml.namespace.QName("", "longdesc");
        private static final javax.xml.namespace.QName HEIGHT$8 = 
            new javax.xml.namespace.QName("", "height");
        private static final javax.xml.namespace.QName WIDTH$10 = 
            new javax.xml.namespace.QName("", "width");
        
        
        /**
         * Gets the "src" attribute
         */
        public java.lang.String getSrc()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SRC$0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "src" attribute
         */
        public org.apache.xmlbeans.XmlAnyURI xgetSrc()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlAnyURI target = null;
                target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_attribute_user(SRC$0);
                return target;
            }
        }
        
        /**
         * Sets the "src" attribute
         */
        public void setSrc(java.lang.String src)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SRC$0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SRC$0);
                }
                target.setStringValue(src);
            }
        }
        
        /**
         * Sets (as xml) the "src" attribute
         */
        public void xsetSrc(org.apache.xmlbeans.XmlAnyURI src)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlAnyURI target = null;
                target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_attribute_user(SRC$0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlAnyURI)get_store().add_attribute_user(SRC$0);
                }
                target.set(src);
            }
        }
        
        /**
         * Gets the "alt" attribute
         */
        public java.lang.String getAlt()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ALT$2);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "alt" attribute
         */
        public org.apache.xmlbeans.XmlString xgetAlt()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ALT$2);
                return target;
            }
        }
        
        /**
         * Sets the "alt" attribute
         */
        public void setAlt(java.lang.String alt)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ALT$2);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ALT$2);
                }
                target.setStringValue(alt);
            }
        }
        
        /**
         * Sets (as xml) the "alt" attribute
         */
        public void xsetAlt(org.apache.xmlbeans.XmlString alt)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ALT$2);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ALT$2);
                }
                target.set(alt);
            }
        }
        
        /**
         * Gets the "name" attribute
         */
        public java.lang.String getName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NAME$4);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "name" attribute
         */
        public org.apache.xmlbeans.XmlNMTOKEN xgetName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlNMTOKEN target = null;
                target = (org.apache.xmlbeans.XmlNMTOKEN)get_store().find_attribute_user(NAME$4);
                return target;
            }
        }
        
        /**
         * True if has "name" attribute
         */
        public boolean isSetName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().find_attribute_user(NAME$4) != null;
            }
        }
        
        /**
         * Sets the "name" attribute
         */
        public void setName(java.lang.String name)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NAME$4);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NAME$4);
                }
                target.setStringValue(name);
            }
        }
        
        /**
         * Sets (as xml) the "name" attribute
         */
        public void xsetName(org.apache.xmlbeans.XmlNMTOKEN name)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlNMTOKEN target = null;
                target = (org.apache.xmlbeans.XmlNMTOKEN)get_store().find_attribute_user(NAME$4);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlNMTOKEN)get_store().add_attribute_user(NAME$4);
                }
                target.set(name);
            }
        }
        
        /**
         * Unsets the "name" attribute
         */
        public void unsetName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_attribute(NAME$4);
            }
        }
        
        /**
         * Gets the "longdesc" attribute
         */
        public java.lang.String getLongdesc()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LONGDESC$6);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "longdesc" attribute
         */
        public org.apache.xmlbeans.XmlAnyURI xgetLongdesc()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlAnyURI target = null;
                target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_attribute_user(LONGDESC$6);
                return target;
            }
        }
        
        /**
         * True if has "longdesc" attribute
         */
        public boolean isSetLongdesc()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().find_attribute_user(LONGDESC$6) != null;
            }
        }
        
        /**
         * Sets the "longdesc" attribute
         */
        public void setLongdesc(java.lang.String longdesc)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LONGDESC$6);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LONGDESC$6);
                }
                target.setStringValue(longdesc);
            }
        }
        
        /**
         * Sets (as xml) the "longdesc" attribute
         */
        public void xsetLongdesc(org.apache.xmlbeans.XmlAnyURI longdesc)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlAnyURI target = null;
                target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_attribute_user(LONGDESC$6);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlAnyURI)get_store().add_attribute_user(LONGDESC$6);
                }
                target.set(longdesc);
            }
        }
        
        /**
         * Unsets the "longdesc" attribute
         */
        public void unsetLongdesc()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_attribute(LONGDESC$6);
            }
        }
        
        /**
         * Gets the "height" attribute
         */
        public java.math.BigInteger getHeight()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(HEIGHT$8);
                if (target == null)
                {
                    return null;
                }
                return target.getBigIntegerValue();
            }
        }
        
        /**
         * Gets (as xml) the "height" attribute
         */
        public org.apache.xmlbeans.XmlNonNegativeInteger xgetHeight()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlNonNegativeInteger target = null;
                target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().find_attribute_user(HEIGHT$8);
                return target;
            }
        }
        
        /**
         * True if has "height" attribute
         */
        public boolean isSetHeight()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().find_attribute_user(HEIGHT$8) != null;
            }
        }
        
        /**
         * Sets the "height" attribute
         */
        public void setHeight(java.math.BigInteger height)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(HEIGHT$8);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(HEIGHT$8);
                }
                target.setBigIntegerValue(height);
            }
        }
        
        /**
         * Sets (as xml) the "height" attribute
         */
        public void xsetHeight(org.apache.xmlbeans.XmlNonNegativeInteger height)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlNonNegativeInteger target = null;
                target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().find_attribute_user(HEIGHT$8);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().add_attribute_user(HEIGHT$8);
                }
                target.set(height);
            }
        }
        
        /**
         * Unsets the "height" attribute
         */
        public void unsetHeight()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_attribute(HEIGHT$8);
            }
        }
        
        /**
         * Gets the "width" attribute
         */
        public java.math.BigInteger getWidth()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(WIDTH$10);
                if (target == null)
                {
                    return null;
                }
                return target.getBigIntegerValue();
            }
        }
        
        /**
         * Gets (as xml) the "width" attribute
         */
        public org.apache.xmlbeans.XmlNonNegativeInteger xgetWidth()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlNonNegativeInteger target = null;
                target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().find_attribute_user(WIDTH$10);
                return target;
            }
        }
        
        /**
         * True if has "width" attribute
         */
        public boolean isSetWidth()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().find_attribute_user(WIDTH$10) != null;
            }
        }
        
        /**
         * Sets the "width" attribute
         */
        public void setWidth(java.math.BigInteger width)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(WIDTH$10);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(WIDTH$10);
                }
                target.setBigIntegerValue(width);
            }
        }
        
        /**
         * Sets (as xml) the "width" attribute
         */
        public void xsetWidth(org.apache.xmlbeans.XmlNonNegativeInteger width)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlNonNegativeInteger target = null;
                target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().find_attribute_user(WIDTH$10);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlNonNegativeInteger)get_store().add_attribute_user(WIDTH$10);
                }
                target.set(width);
            }
        }
        
        /**
         * Unsets the "width" attribute
         */
        public void unsetWidth()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_attribute(WIDTH$10);
            }
        }
    }
}
