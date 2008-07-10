/*
 * An XML document type.
 * Localname: observationresponse
 * Namespace: http://www.w3.org/unicorn/observationresponse
 * Java type: org.w3.unicorn.observationresponse.ObservationresponseDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.observationresponse;


/**
 * A document containing one observationresponse(@http://www.w3.org/unicorn/observationresponse) element.
 *
 * This is a complex type.
 */
public interface ObservationresponseDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ObservationresponseDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9A9FB80DD3ED243833FD04B8954C2F93").resolveHandle("observationresponse94d6doctype");
    
    /**
     * Gets the "observationresponse" element
     */
    org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse getObservationresponse();
    
    /**
     * Sets the "observationresponse" element
     */
    void setObservationresponse(org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse observationresponse);
    
    /**
     * Appends and returns a new empty "observationresponse" element
     */
    org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse addNewObservationresponse();
    
    /**
     * An XML observationresponse(@http://www.w3.org/unicorn/observationresponse).
     *
     * This is a complex type.
     */
    public interface Observationresponse extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Observationresponse.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9A9FB80DD3ED243833FD04B8954C2F93").resolveHandle("observationresponse792felemtype");
        
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
         * Gets the "checkedby" element
         */
        java.lang.String getCheckedby();
        
        /**
         * Gets (as xml) the "checkedby" element
         */
        org.apache.xmlbeans.XmlAnyURI xgetCheckedby();
        
        /**
         * True if has "checkedby" element
         */
        boolean isSetCheckedby();
        
        /**
         * Sets the "checkedby" element
         */
        void setCheckedby(java.lang.String checkedby);
        
        /**
         * Sets (as xml) the "checkedby" element
         */
        void xsetCheckedby(org.apache.xmlbeans.XmlAnyURI checkedby);
        
        /**
         * Unsets the "checkedby" element
         */
        void unsetCheckedby();
        
        /**
         * Gets the "version" element
         */
        java.lang.String getVersion();
        
        /**
         * Gets (as xml) the "version" element
         */
        org.apache.xmlbeans.XmlString xgetVersion();
        
        /**
         * True if has "version" element
         */
        boolean isSetVersion();
        
        /**
         * Sets the "version" element
         */
        void setVersion(java.lang.String version);
        
        /**
         * Sets (as xml) the "version" element
         */
        void xsetVersion(org.apache.xmlbeans.XmlString version);
        
        /**
         * Unsets the "version" element
         */
        void unsetVersion();
        
        /**
         * Gets the "date" element
         */
        java.util.Calendar getDate();
        
        /**
         * Gets (as xml) the "date" element
         */
        org.apache.xmlbeans.XmlDateTime xgetDate();
        
        /**
         * True if has "date" element
         */
        boolean isSetDate();
        
        /**
         * Sets the "date" element
         */
        void setDate(java.util.Calendar date);
        
        /**
         * Sets (as xml) the "date" element
         */
        void xsetDate(org.apache.xmlbeans.XmlDateTime date);
        
        /**
         * Unsets the "date" element
         */
        void unsetDate();
        
        /**
         * Gets the "passed" element
         */
        boolean getPassed();
        
        /**
         * Gets (as xml) the "passed" element
         */
        org.apache.xmlbeans.XmlBoolean xgetPassed();
        
        /**
         * True if has "passed" element
         */
        boolean isSetPassed();
        
        /**
         * Sets the "passed" element
         */
        void setPassed(boolean passed);
        
        /**
         * Sets (as xml) the "passed" element
         */
        void xsetPassed(org.apache.xmlbeans.XmlBoolean passed);
        
        /**
         * Unsets the "passed" element
         */
        void unsetPassed();
        
        /**
         * Gets the "result" element
         */
        org.w3.unicorn.observationresponse.ResultDocument.Result getResult();
        
        /**
         * Sets the "result" element
         */
        void setResult(org.w3.unicorn.observationresponse.ResultDocument.Result result);
        
        /**
         * Appends and returns a new empty "result" element
         */
        org.w3.unicorn.observationresponse.ResultDocument.Result addNewResult();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse newInstance() {
              return (org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument newInstance() {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.unicorn.observationresponse.ObservationresponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.unicorn.observationresponse.ObservationresponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
