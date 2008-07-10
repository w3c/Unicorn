/*
 * An XML document type.
 * Localname: errorlist
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.ErrorlistDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse;


/**
 * A document containing one errorlist(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public interface ErrorlistDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ErrorlistDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9A9FB80DD3ED243833FD04B8954C2F93").resolveHandle("errorlistac0fdoctype");
    
    /**
     * Gets the "errorlist" element
     */
    org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist getErrorlist();
    
    /**
     * Sets the "errorlist" element
     */
    void setErrorlist(org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist errorlist);
    
    /**
     * Appends and returns a new empty "errorlist" element
     */
    org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist addNewErrorlist();
    
    /**
     * An XML errorlist(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public interface Errorlist extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Errorlist.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9A9FB80DD3ED243833FD04B8954C2F93").resolveHandle("errorlist32a1elemtype");
        
        /**
         * Gets the "uri" element
         */
        java.lang.String getUri();
        
        /**
         * Gets (as xml) the "uri" element
         */
        org.apache.xmlbeans.XmlAnyURI xgetUri();
        
        /**
         * Sets the "uri" element
         */
        void setUri(java.lang.String uri);
        
        /**
         * Sets (as xml) the "uri" element
         */
        void xsetUri(org.apache.xmlbeans.XmlAnyURI uri);
        
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
         * Gets array of all "error" elements
         */
        org.w3.unicorn.observationresponse.ErrorDocument.Error[] getErrorArray();
        
        /**
         * Gets ith "error" element
         */
        org.w3.unicorn.observationresponse.ErrorDocument.Error getErrorArray(int i);
        
        /**
         * Returns number of "error" element
         */
        int sizeOfErrorArray();
        
        /**
         * Sets array of all "error" element
         */
        void setErrorArray(org.w3.unicorn.observationresponse.ErrorDocument.Error[] errorArray);
        
        /**
         * Sets ith "error" element
         */
        void setErrorArray(int i, org.w3.unicorn.observationresponse.ErrorDocument.Error error);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "error" element
         */
        org.w3.unicorn.observationresponse.ErrorDocument.Error insertNewError(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "error" element
         */
        org.w3.unicorn.observationresponse.ErrorDocument.Error addNewError();
        
        /**
         * Removes the ith "error" element
         */
        void removeError(int i);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist newInstance() {
              return (org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static org.w3.unicorn.observationresponse.ErrorlistDocument newInstance() {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorlistDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.unicorn.observationresponse.ErrorlistDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.unicorn.observationresponse.ErrorlistDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
