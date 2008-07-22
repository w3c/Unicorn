/*
 * XML Type:  tParamType
 * Namespace: http://www.w3.org/unicorn/tasklist
 * Java type: org.w3.unicorn.tasklist.TParamType
 *
 * Automatically generated - do not modify.
 */
package org.w3.unicorn.tasklist;


/**
 * An XML tParamType(@http://www.w3.org/unicorn/tasklist).
 *
 * This is an atomic type that is a restriction of org.w3.unicorn.tasklist.TParamType.
 */
public interface TParamType extends org.apache.xmlbeans.XmlString
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(TParamType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3149679C42AE7ADEA40C6B779897203B").resolveHandle("tparamtype1eb2type");
    
    org.apache.xmlbeans.StringEnumAbstractBase enumValue();
    void set(org.apache.xmlbeans.StringEnumAbstractBase e);
    
    static final Enum CHECKBOX = Enum.forString("checkbox");
    static final Enum CHECKBOXLIST = Enum.forString("checkboxlist");
    static final Enum DROPDOWN = Enum.forString("dropdown");
    static final Enum TEXTAREA = Enum.forString("textarea");
    static final Enum TEXTFIELD = Enum.forString("textfield");
    static final Enum RADIO = Enum.forString("radio");
    
    static final int INT_CHECKBOX = Enum.INT_CHECKBOX;
    static final int INT_CHECKBOXLIST = Enum.INT_CHECKBOXLIST;
    static final int INT_DROPDOWN = Enum.INT_DROPDOWN;
    static final int INT_TEXTAREA = Enum.INT_TEXTAREA;
    static final int INT_TEXTFIELD = Enum.INT_TEXTFIELD;
    static final int INT_RADIO = Enum.INT_RADIO;
    
    /**
     * Enumeration value class for org.w3.unicorn.tasklist.TParamType.
     * These enum values can be used as follows:
     * <pre>
     * enum.toString(); // returns the string value of the enum
     * enum.intValue(); // returns an int value, useful for switches
     * // e.g., case Enum.INT_CHECKBOX
     * Enum.forString(s); // returns the enum value for a string
     * Enum.forInt(i); // returns the enum value for an int
     * </pre>
     * Enumeration objects are immutable singleton objects that
     * can be compared using == object equality. They have no
     * public constructor. See the constants defined within this
     * class for all the valid values.
     */
    static final class Enum extends org.apache.xmlbeans.StringEnumAbstractBase
    {
        /**
         * Returns the enum value for a string, or null if none.
         */
        public static Enum forString(java.lang.String s)
            { return (Enum)table.forString(s); }
        /**
         * Returns the enum value corresponding to an int, or null if none.
         */
        public static Enum forInt(int i)
            { return (Enum)table.forInt(i); }
        
        private Enum(java.lang.String s, int i)
            { super(s, i); }
        
        static final int INT_CHECKBOX = 1;
        static final int INT_CHECKBOXLIST = 2;
        static final int INT_DROPDOWN = 3;
        static final int INT_TEXTAREA = 4;
        static final int INT_TEXTFIELD = 5;
        static final int INT_RADIO = 6;
        
        public static final org.apache.xmlbeans.StringEnumAbstractBase.Table table =
            new org.apache.xmlbeans.StringEnumAbstractBase.Table
        (
            new Enum[]
            {
                new Enum("checkbox", INT_CHECKBOX),
                new Enum("checkboxlist", INT_CHECKBOXLIST),
                new Enum("dropdown", INT_DROPDOWN),
                new Enum("textarea", INT_TEXTAREA),
                new Enum("textfield", INT_TEXTFIELD),
                new Enum("radio", INT_RADIO),
            }
        );
        private static final long serialVersionUID = 1L;
        private java.lang.Object readResolve() { return forInt(intValue()); } 
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static org.w3.unicorn.tasklist.TParamType newValue(java.lang.Object obj) {
          return (org.w3.unicorn.tasklist.TParamType) type.newValue( obj ); }
        
        public static org.w3.unicorn.tasklist.TParamType newInstance() {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static org.w3.unicorn.tasklist.TParamType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static org.w3.unicorn.tasklist.TParamType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static org.w3.unicorn.tasklist.TParamType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static org.w3.unicorn.tasklist.TParamType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static org.w3.unicorn.tasklist.TParamType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static org.w3.unicorn.tasklist.TParamType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static org.w3.unicorn.tasklist.TParamType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static org.w3.unicorn.tasklist.TParamType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static org.w3.unicorn.tasklist.TParamType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static org.w3.unicorn.tasklist.TParamType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static org.w3.unicorn.tasklist.TParamType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static org.w3.unicorn.tasklist.TParamType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static org.w3.unicorn.tasklist.TParamType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static org.w3.unicorn.tasklist.TParamType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static org.w3.unicorn.tasklist.TParamType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.unicorn.tasklist.TParamType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.unicorn.tasklist.TParamType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.unicorn.tasklist.TParamType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
