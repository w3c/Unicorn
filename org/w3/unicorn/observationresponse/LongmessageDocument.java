/*
 * An XML document type.
 * Localname: longmessage
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.LongmessageDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse;


/**
 * A document containing one longmessage(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public interface LongmessageDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(LongmessageDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9A9FB80DD3ED243833FD04B8954C2F93").resolveHandle("longmessage3cf4doctype");
    
    /**
     * Gets the "longmessage" element
     */
    org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage getLongmessage();
    
    /**
     * Sets the "longmessage" element
     */
    void setLongmessage(org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage longmessage);
    
    /**
     * Appends and returns a new empty "longmessage" element
     */
    org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage addNewLongmessage();
    
    /**
     * An XML longmessage(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public interface Longmessage extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Longmessage.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9A9FB80DD3ED243833FD04B8954C2F93").resolveHandle("longmessage36ebelemtype");
        
        /**
         * Gets array of all "a" elements
         */
        org.w3.unicorn.observationresponse.ADocument.A[] getAArray();
        
        /**
         * Gets ith "a" element
         */
        org.w3.unicorn.observationresponse.ADocument.A getAArray(int i);
        
        /**
         * Returns number of "a" element
         */
        int sizeOfAArray();
        
        /**
         * Sets array of all "a" element
         */
        void setAArray(org.w3.unicorn.observationresponse.ADocument.A[] aArray);
        
        /**
         * Sets ith "a" element
         */
        void setAArray(int i, org.w3.unicorn.observationresponse.ADocument.A a);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "a" element
         */
        org.w3.unicorn.observationresponse.ADocument.A insertNewA(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "a" element
         */
        org.w3.unicorn.observationresponse.ADocument.A addNewA();
        
        /**
         * Removes the ith "a" element
         */
        void removeA(int i);
        
        /**
         * Gets array of all "code" elements
         */
        org.w3.unicorn.observationresponse.CodeDocument.Code[] getCodeArray();
        
        /**
         * Gets ith "code" element
         */
        org.w3.unicorn.observationresponse.CodeDocument.Code getCodeArray(int i);
        
        /**
         * Returns number of "code" element
         */
        int sizeOfCodeArray();
        
        /**
         * Sets array of all "code" element
         */
        void setCodeArray(org.w3.unicorn.observationresponse.CodeDocument.Code[] codeArray);
        
        /**
         * Sets ith "code" element
         */
        void setCodeArray(int i, org.w3.unicorn.observationresponse.CodeDocument.Code code);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "code" element
         */
        org.w3.unicorn.observationresponse.CodeDocument.Code insertNewCode(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "code" element
         */
        org.w3.unicorn.observationresponse.CodeDocument.Code addNewCode();
        
        /**
         * Removes the ith "code" element
         */
        void removeCode(int i);
        
        /**
         * Gets array of all "img" elements
         */
        org.w3.unicorn.observationresponse.ImgDocument.Img[] getImgArray();
        
        /**
         * Gets ith "img" element
         */
        org.w3.unicorn.observationresponse.ImgDocument.Img getImgArray(int i);
        
        /**
         * Returns number of "img" element
         */
        int sizeOfImgArray();
        
        /**
         * Sets array of all "img" element
         */
        void setImgArray(org.w3.unicorn.observationresponse.ImgDocument.Img[] imgArray);
        
        /**
         * Sets ith "img" element
         */
        void setImgArray(int i, org.w3.unicorn.observationresponse.ImgDocument.Img img);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "img" element
         */
        org.w3.unicorn.observationresponse.ImgDocument.Img insertNewImg(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "img" element
         */
        org.w3.unicorn.observationresponse.ImgDocument.Img addNewImg();
        
        /**
         * Removes the ith "img" element
         */
        void removeImg(int i);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage newInstance() {
              return (org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static org.w3.unicorn.observationresponse.LongmessageDocument newInstance() {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static org.w3.unicorn.observationresponse.LongmessageDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.unicorn.observationresponse.LongmessageDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.unicorn.observationresponse.LongmessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
