package com.sino.hn.todo.cxf.beans;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mochasoft.todo.beans package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CloseSysId_QNAME = new QName("http://beans.todo.mochasoft.com", "sys_id");
    private final static QName _CloseCloseTime_QNAME = new QName("http://beans.todo.mochasoft.com", "close_time");
    private final static QName _CloseSourceId_QNAME = new QName("http://beans.todo.mochasoft.com", "source_id");
    private final static QName _CloseWorkId_QNAME = new QName("http://beans.todo.mochasoft.com", "work_id");
    private final static QName _CloseUserId_QNAME = new QName("http://beans.todo.mochasoft.com", "user_id");
    private final static QName _CloseDocId_QNAME = new QName("http://beans.todo.mochasoft.com", "doc_id");
    private final static QName _OpenStartTime_QNAME = new QName("http://beans.todo.mochasoft.com", "start_time");
    private final static QName _OpenSender_QNAME = new QName("http://beans.todo.mochasoft.com", "sender");
    private final static QName _OpenTitle_QNAME = new QName("http://beans.todo.mochasoft.com", "title");
    private final static QName _OpenDocType_QNAME = new QName("http://beans.todo.mochasoft.com", "doc_type");
    private final static QName _OpenType_QNAME = new QName("http://beans.todo.mochasoft.com", "type");
    private final static QName _OpenUrl_QNAME = new QName("http://beans.todo.mochasoft.com", "url");
    private final static QName _OpenPri_QNAME = new QName("http://beans.todo.mochasoft.com", "pri");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mochasoft.todo.beans
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Close }
     * 
     */
    public Close createClose() {
        return new Close();
    }

    /**
     * Create an instance of {@link Cancel }
     * 
     */
    public Cancel createCancel() {
        return new Cancel();
    }

    /**
     * Create an instance of {@link ArrayOfClose }
     * 
     */
    public ArrayOfClose createArrayOfClose() {
        return new ArrayOfClose();
    }

    /**
     * Create an instance of {@link Open }
     * 
     */
    public Open createOpen() {
        return new Open();
    }

    /**
     * Create an instance of {@link ArrayOfCancel }
     * 
     */
    public ArrayOfCancel createArrayOfCancel() {
        return new ArrayOfCancel();
    }

    /**
     * Create an instance of {@link ArrayOfOpen }
     * 
     */
    public ArrayOfOpen createArrayOfOpen() {
        return new ArrayOfOpen();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "sys_id", scope = Close.class)
    public JAXBElement<String> createCloseSysId(String value) {
        return new JAXBElement<String>(_CloseSysId_QNAME, String.class, Close.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "close_time", scope = Close.class)
    public JAXBElement<String> createCloseCloseTime(String value) {
        return new JAXBElement<String>(_CloseCloseTime_QNAME, String.class, Close.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "source_id", scope = Close.class)
    public JAXBElement<String> createCloseSourceId(String value) {
        return new JAXBElement<String>(_CloseSourceId_QNAME, String.class, Close.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "work_id", scope = Close.class)
    public JAXBElement<String> createCloseWorkId(String value) {
        return new JAXBElement<String>(_CloseWorkId_QNAME, String.class, Close.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "user_id", scope = Close.class)
    public JAXBElement<String> createCloseUserId(String value) {
        return new JAXBElement<String>(_CloseUserId_QNAME, String.class, Close.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "doc_id", scope = Close.class)
    public JAXBElement<String> createCloseDocId(String value) {
        return new JAXBElement<String>(_CloseDocId_QNAME, String.class, Close.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "start_time", scope = Open.class)
    public JAXBElement<String> createOpenStartTime(String value) {
        return new JAXBElement<String>(_OpenStartTime_QNAME, String.class, Open.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "sys_id", scope = Open.class)
    public JAXBElement<String> createOpenSysId(String value) {
        return new JAXBElement<String>(_CloseSysId_QNAME, String.class, Open.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "sender", scope = Open.class)
    public JAXBElement<String> createOpenSender(String value) {
        return new JAXBElement<String>(_OpenSender_QNAME, String.class, Open.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "title", scope = Open.class)
    public JAXBElement<String> createOpenTitle(String value) {
        return new JAXBElement<String>(_OpenTitle_QNAME, String.class, Open.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "source_id", scope = Open.class)
    public JAXBElement<String> createOpenSourceId(String value) {
        return new JAXBElement<String>(_CloseSourceId_QNAME, String.class, Open.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "doc_type", scope = Open.class)
    public JAXBElement<String> createOpenDocType(String value) {
        return new JAXBElement<String>(_OpenDocType_QNAME, String.class, Open.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "work_id", scope = Open.class)
    public JAXBElement<String> createOpenWorkId(String value) {
        return new JAXBElement<String>(_CloseWorkId_QNAME, String.class, Open.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "user_id", scope = Open.class)
    public JAXBElement<String> createOpenUserId(String value) {
        return new JAXBElement<String>(_CloseUserId_QNAME, String.class, Open.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "doc_id", scope = Open.class)
    public JAXBElement<String> createOpenDocId(String value) {
        return new JAXBElement<String>(_CloseDocId_QNAME, String.class, Open.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "type", scope = Open.class)
    public JAXBElement<String> createOpenType(String value) {
        return new JAXBElement<String>(_OpenType_QNAME, String.class, Open.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "url", scope = Open.class)
    public JAXBElement<String> createOpenUrl(String value) {
        return new JAXBElement<String>(_OpenUrl_QNAME, String.class, Open.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "pri", scope = Open.class)
    public JAXBElement<String> createOpenPri(String value) {
        return new JAXBElement<String>(_OpenPri_QNAME, String.class, Open.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "sys_id", scope = Cancel.class)
    public JAXBElement<String> createCancelSysId(String value) {
        return new JAXBElement<String>(_CloseSysId_QNAME, String.class, Cancel.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "source_id", scope = Cancel.class)
    public JAXBElement<String> createCancelSourceId(String value) {
        return new JAXBElement<String>(_CloseSourceId_QNAME, String.class, Cancel.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "work_id", scope = Cancel.class)
    public JAXBElement<String> createCancelWorkId(String value) {
        return new JAXBElement<String>(_CloseWorkId_QNAME, String.class, Cancel.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "user_id", scope = Cancel.class)
    public JAXBElement<String> createCancelUserId(String value) {
        return new JAXBElement<String>(_CloseUserId_QNAME, String.class, Cancel.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://beans.todo.mochasoft.com", name = "doc_id", scope = Cancel.class)
    public JAXBElement<String> createCancelDocId(String value) {
        return new JAXBElement<String>(_CloseDocId_QNAME, String.class, Cancel.class, value);
    }

}
