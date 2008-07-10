/*
 * An XML document type.
 * Localname: errors
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.ErrorsDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse;


/**
 * A document containing one errors(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public interface ErrorsDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ErrorsDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9A9FB80DD3ED243833FD04B8954C2F93").resolveHandle("errors16a6doctype");
    
    /**
     * Gets the "errors" element
     */
    org.w3.unicorn.observationresponse.ErrorsDocument.Errors getErrors();
    
    /**
     * Sets the "errors" element
     */
    void setErrors(org.w3.unicorn.observationresponse.ErrorsDocument.Errors errors);
    
    /**
     * Appends and returns a new empty "errors" element
     */
    org.w3.unicorn.observationresponse.ErrorsDocument.Errors addNewErrors();
    
    /**
     * An XML errors(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public interface Errors extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Errors.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9A9FB80DD3ED243833FD04B8954C2F93").resolveHandle("errorsf4bfelemtype");
        
        /**
         * Gets the "errorcount" element
         */
        java.math.BigInteger getErrorcount();
        
        /**
         * Gets (as xml) the "errorcount" element
         */
        org.apache.xmlbeans.XmlInteger xgetErrorcount();
        
        /**
         * True if has "errorcount" element
         */
        boolean isSetErrorcount();
        
        /**
         * Sets the "errorcount" element
         */
        void setErrorcount(java.math.BigInteger errorcount);
        
        /**
         * Sets (as xml) the "errorcount" element
         */
        void xsetErrorcount(org.apache.xmlbeans.XmlInteger errorcount);
        
        /**
         * Unsets the "errorcount" element
         */
        void unsetErrorcount();
        
        /**
         * Gets array of all "errorlist" elements
         */
        org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist[] getErrorlistArray();
        
        /**
         * Gets ith "errorlist" element
         */
        org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist getErrorlistArray(int i);
        
        /**
         * Returns number of "errorlist" element
         */
        int sizeOfErrorlistArray();
        
        /**
         * Sets array of all "errorlist" element
         */
        void setErrorlistArray(org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist[] errorlistArray);
        
        /**
         * Sets ith "errorlist" element
         */
        void setErrorlistArray(int i, org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist errorlist);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "errorlist" element
         */
        org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist insertNewErrorlist(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "errorlist" element
         */
        org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist addNewErrorlist();
        
        /**
         * Removes the ith "errorlist" element
         */
        void removeErrorlist(int i);
        
        /**
         * Gets the "lang" attribute
         */
        java.lang.String getLang();
        
        /**
         * Gets (as xml) the "lang" attribute
         */
        org.apache.xmlbeans.XmlLanguage xgetLang();
        
        /**
         * Sets the "lang" attribute
         */
        void setLang(java.lang.String lang);
        
        /**
         * Sets (as xml) the "lang" attribute
         */
        void xsetLang(org.apache.xmlbeans.XmlLanguage lang);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static org.w3.unicorn.observationresponse.ErrorsDocument.Errors newInstance() {
              return (org.w3.unicorn.observationresponse.ErrorsDocument.Errors) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static org.w3.unicorn.observationresponse.ErrorsDocument.Errors newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (org.w3.unicorn.observationresponse.ErrorsDocument.Errors) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static org.w3.unicorn.observationresponse.ErrorsDocument newInstance() {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorsDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.unicorn.observationresponse.ErrorsDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.unicorn.observationresponse.ErrorsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
