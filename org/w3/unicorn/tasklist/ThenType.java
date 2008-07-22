/*
 * XML Type:  thenType
 * Namespace: http://www.w3.org/unicorn/tasklist
 * Java type: org.w3.unicorn.tasklist.ThenType
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.tasklist;


/**
 * An XML thenType(@http://www.w3.org/unicorn/tasklist).
 *
 * This is a complex type.
 */
public interface ThenType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ThenType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3149679C42AE7ADEA40C6B779897203B").resolveHandle("thentype724etype");
    
    /**
     * Gets array of all "if" elements
     */
    org.w3.unicorn.tasklist.IfType[] getIfArray();
    
    /**
     * Gets ith "if" element
     */
    org.w3.unicorn.tasklist.IfType getIfArray(int i);
    
    /**
     * Returns number of "if" element
     */
    int sizeOfIfArray();
    
    /**
     * Sets array of all "if" element
     */
    void setIfArray(org.w3.unicorn.tasklist.IfType[] xifArray);
    
    /**
     * Sets ith "if" element
     */
    void setIfArray(int i, org.w3.unicorn.tasklist.IfType xif);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "if" element
     */
    org.w3.unicorn.tasklist.IfType insertNewIf(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "if" element
     */
    org.w3.unicorn.tasklist.IfType addNewIf();
    
    /**
     * Removes the ith "if" element
     */
    void removeIf(int i);
    
    /**
     * Gets array of all "exec" elements
     */
    org.w3.unicorn.tasklist.ExecType[] getExecArray();
    
    /**
     * Gets ith "exec" element
     */
    org.w3.unicorn.tasklist.ExecType getExecArray(int i);
    
    /**
     * Returns number of "exec" element
     */
    int sizeOfExecArray();
    
    /**
     * Sets array of all "exec" element
     */
    void setExecArray(org.w3.unicorn.tasklist.ExecType[] execArray);
    
    /**
     * Sets ith "exec" element
     */
    void setExecArray(int i, org.w3.unicorn.tasklist.ExecType exec);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "exec" element
     */
    org.w3.unicorn.tasklist.ExecType insertNewExec(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "exec" element
     */
    org.w3.unicorn.tasklist.ExecType addNewExec();
    
    /**
     * Removes the ith "exec" element
     */
    void removeExec(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static org.w3.unicorn.tasklist.ThenType newInstance() {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static org.w3.unicorn.tasklist.ThenType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static org.w3.unicorn.tasklist.ThenType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static org.w3.unicorn.tasklist.ThenType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static org.w3.unicorn.tasklist.ThenType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static org.w3.unicorn.tasklist.ThenType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static org.w3.unicorn.tasklist.ThenType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static org.w3.unicorn.tasklist.ThenType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static org.w3.unicorn.tasklist.ThenType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static org.w3.unicorn.tasklist.ThenType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static org.w3.unicorn.tasklist.ThenType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static org.w3.unicorn.tasklist.ThenType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static org.w3.unicorn.tasklist.ThenType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static org.w3.unicorn.tasklist.ThenType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static org.w3.unicorn.tasklist.ThenType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static org.w3.unicorn.tasklist.ThenType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.unicorn.tasklist.ThenType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.unicorn.tasklist.ThenType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.unicorn.tasklist.ThenType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
