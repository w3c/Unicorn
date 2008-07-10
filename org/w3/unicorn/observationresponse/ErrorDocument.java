/*
 * An XML document type.
 * Localname: error
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.ErrorDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse;


/**
 * A document containing one error(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public interface ErrorDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ErrorDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9A9FB80DD3ED243833FD04B8954C2F93").resolveHandle("error6f71doctype");
    
    /**
     * Gets the "error" element
     */
    org.w3.unicorn.observationresponse.ErrorDocument.Error getError();
    
    /**
     * Sets the "error" element
     */
    void setError(org.w3.unicorn.observationresponse.ErrorDocument.Error error);
    
    /**
     * Appends and returns a new empty "error" element
     */
    org.w3.unicorn.observationresponse.ErrorDocument.Error addNewError();
    
    /**
     * An XML error(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public interface Error extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Error.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9A9FB80DD3ED243833FD04B8954C2F93").resolveHandle("error78e5elemtype");
        
        /**
         * Gets the "line" element
         */
        java.math.BigInteger getLine();
        
        /**
         * Gets (as xml) the "line" element
         */
        org.apache.xmlbeans.XmlNonNegativeInteger xgetLine();
        
        /**
         * True if has "line" element
         */
        boolean isSetLine();
        
        /**
         * Sets the "line" element
         */
        void setLine(java.math.BigInteger line);
        
        /**
         * Sets (as xml) the "line" element
         */
        void xsetLine(org.apache.xmlbeans.XmlNonNegativeInteger line);
        
        /**
         * Unsets the "line" element
         */
        void unsetLine();
        
        /**
         * Gets the "column" element
         */
        java.math.BigInteger getColumn();
        
        /**
         * Gets (as xml) the "column" element
         */
        org.apache.xmlbeans.XmlNonNegativeInteger xgetColumn();
        
        /**
         * True if has "column" element
         */
        boolean isSetColumn();
        
        /**
         * Sets the "column" element
         */
        void setColumn(java.math.BigInteger column);
        
        /**
         * Sets (as xml) the "column" element
         */
        void xsetColumn(org.apache.xmlbeans.XmlNonNegativeInteger column);
        
        /**
         * Unsets the "column" element
         */
        void unsetColumn();
        
        /**
         * Gets the "errortype" element
         */
        java.lang.String getErrortype();
        
        /**
         * Gets (as xml) the "errortype" element
         */
        org.apache.xmlbeans.XmlString xgetErrortype();
        
        /**
         * True if has "errortype" element
         */
        boolean isSetErrortype();
        
        /**
         * Sets the "errortype" element
         */
        void setErrortype(java.lang.String errortype);
        
        /**
         * Sets (as xml) the "errortype" element
         */
        void xsetErrortype(org.apache.xmlbeans.XmlString errortype);
        
        /**
         * Unsets the "errortype" element
         */
        void unsetErrortype();
        
        /**
         * Gets the "context" element
         */
        java.lang.String getContext();
        
        /**
         * Gets (as xml) the "context" element
         */
        org.apache.xmlbeans.XmlString xgetContext();
        
        /**
         * True if has "context" element
         */
        boolean isSetContext();
        
        /**
         * Sets the "context" element
         */
        void setContext(java.lang.String context);
        
        /**
         * Sets (as xml) the "context" element
         */
        void xsetContext(org.apache.xmlbeans.XmlString context);
        
        /**
         * Unsets the "context" element
         */
        void unsetContext();
        
        /**
         * Gets array of all "message" elements
         */
        java.lang.String[] getMessageArray();
        
        /**
         * Gets ith "message" element
         */
        java.lang.String getMessageArray(int i);
        
        /**
         * Gets (as xml) array of all "message" elements
         */
        org.apache.xmlbeans.XmlString[] xgetMessageArray();
        
        /**
         * Gets (as xml) ith "message" element
         */
        org.apache.xmlbeans.XmlString xgetMessageArray(int i);
        
        /**
         * Returns number of "message" element
         */
        int sizeOfMessageArray();
        
        /**
         * Sets array of all "message" element
         */
        void setMessageArray(java.lang.String[] messageArray);
        
        /**
         * Sets ith "message" element
         */
        void setMessageArray(int i, java.lang.String message);
        
        /**
         * Sets (as xml) array of all "message" element
         */
        void xsetMessageArray(org.apache.xmlbeans.XmlString[] messageArray);
        
        /**
         * Sets (as xml) ith "message" element
         */
        void xsetMessageArray(int i, org.apache.xmlbeans.XmlString message);
        
        /**
         * Inserts the value as the ith "message" element
         */
        void insertMessage(int i, java.lang.String message);
        
        /**
         * Appends the value as the last "message" element
         */
        void addMessage(java.lang.String message);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "message" element
         */
        org.apache.xmlbeans.XmlString insertNewMessage(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "message" element
         */
        org.apache.xmlbeans.XmlString addNewMessage();
        
        /**
         * Removes the ith "message" element
         */
        void removeMessage(int i);
        
        /**
         * Gets array of all "longmessage" elements
         */
        org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage[] getLongmessageArray();
        
        /**
         * Gets ith "longmessage" element
         */
        org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage getLongmessageArray(int i);
        
        /**
         * Returns number of "longmessage" element
         */
        int sizeOfLongmessageArray();
        
        /**
         * Sets array of all "longmessage" element
         */
        void setLongmessageArray(org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage[] longmessageArray);
        
        /**
         * Sets ith "longmessage" element
         */
        void setLongmessageArray(int i, org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage longmessage);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "longmessage" element
         */
        org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage insertNewLongmessage(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "longmessage" element
         */
        org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage addNewLongmessage();
        
        /**
         * Removes the ith "longmessage" element
         */
        void removeLongmessage(int i);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static org.w3.unicorn.observationresponse.ErrorDocument.Error newInstance() {
              return (org.w3.unicorn.observationresponse.ErrorDocument.Error) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static org.w3.unicorn.observationresponse.ErrorDocument.Error newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (org.w3.unicorn.observationresponse.ErrorDocument.Error) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static org.w3.unicorn.observationresponse.ErrorDocument newInstance() {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.unicorn.observationresponse.ErrorDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.unicorn.observationresponse.ErrorDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
