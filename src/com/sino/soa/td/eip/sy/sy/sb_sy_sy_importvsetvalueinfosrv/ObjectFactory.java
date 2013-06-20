
package com.sino.soa.td.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.td.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv package.
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

    private final static QName _ErrorItem_QNAME = new QName("http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", "ErrorItem");
    private final static QName _ResponseItem_QNAME = new QName("http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", "ResponseItem");
    private final static QName _ResponseCollecion_QNAME = new QName("http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", "ResponseCollecion");
    private final static QName _ErrorCollection_QNAME = new QName("http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", "ErrorCollection");
    private final static QName _ImportVSetValueInfoSrvInputItem_QNAME = new QName("http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", "ImportVSetValueInfoSrvInputItem");
    private final static QName _InputCollection_QNAME = new QName("http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", "InputCollection");
    private final static QName _InputItem_QNAME = new QName("http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", "InputItem");
    private final static QName _ImportVSetValueInfoSrvInputCollection_QNAME = new QName("http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", "ImportVSetValueInfoSrvInputCollection");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.td.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MsgHeader }
     * 
     */
    public MsgHeader createMsgHeader() {
        return new MsgHeader();
    }

    /**
     * Create an instance of {@link ErrorItem }
     * 
     */
    public ErrorItem createErrorItem() {
        return new ErrorItem();
    }

    /**
     * Create an instance of {@link ResponseItem }
     * 
     */
    public ResponseItem createResponseItem() {
        return new ResponseItem();
    }

    /**
     * Create an instance of {@link ImportVSetValueInfoSrvResponse }
     * 
     */
    public ImportVSetValueInfoSrvResponse createImportVSetValueInfoSrvResponse() {
        return new ImportVSetValueInfoSrvResponse();
    }

    /**
     * Create an instance of {@link ErrorCollection }
     * 
     */
    public ErrorCollection createErrorCollection() {
        return new ErrorCollection();
    }

    /**
     * Create an instance of {@link SEGMENTSITEM }
     * 
     */
    public SEGMENTSITEM createSEGMENTSITEM() {
        return new SEGMENTSITEM();
    }

    /**
     * Create an instance of {@link ResponseCollecion }
     * 
     */
    public ResponseCollecion createResponseCollecion() {
        return new ResponseCollecion();
    }

    /**
     * Create an instance of {@link ImportVSetValueInfoSrvInputCollection }
     * 
     */
    public ImportVSetValueInfoSrvInputCollection createImportVSetValueInfoSrvInputCollection() {
        return new ImportVSetValueInfoSrvInputCollection();
    }

    /**
     * Create an instance of {@link ImportVSetValueInfoSrvInputItem }
     * 
     */
    public ImportVSetValueInfoSrvInputItem createImportVSetValueInfoSrvInputItem() {
        return new ImportVSetValueInfoSrvInputItem();
    }

    /**
     * Create an instance of {@link ImportVSetValueInfoSrvRequest }
     * 
     */
    public ImportVSetValueInfoSrvRequest createImportVSetValueInfoSrvRequest() {
        return new ImportVSetValueInfoSrvRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", name = "ErrorItem")
    public JAXBElement<ErrorItem> createErrorItem(ErrorItem value) {
        return new JAXBElement<ErrorItem>(_ErrorItem_QNAME, ErrorItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", name = "ResponseItem")
    public JAXBElement<ResponseItem> createResponseItem(ResponseItem value) {
        return new JAXBElement<ResponseItem>(_ResponseItem_QNAME, ResponseItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseCollecion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", name = "ResponseCollecion")
    public JAXBElement<ResponseCollecion> createResponseCollecion(ResponseCollecion value) {
        return new JAXBElement<ResponseCollecion>(_ResponseCollecion_QNAME, ResponseCollecion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", name = "ErrorCollection")
    public JAXBElement<ErrorCollection> createErrorCollection(ErrorCollection value) {
        return new JAXBElement<ErrorCollection>(_ErrorCollection_QNAME, ErrorCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportVSetValueInfoSrvInputItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", name = "ImportVSetValueInfoSrvInputItem")
    public JAXBElement<ImportVSetValueInfoSrvInputItem> createImportVSetValueInfoSrvInputItem(ImportVSetValueInfoSrvInputItem value) {
        return new JAXBElement<ImportVSetValueInfoSrvInputItem>(_ImportVSetValueInfoSrvInputItem_QNAME, ImportVSetValueInfoSrvInputItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportVSetValueInfoSrvInputCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", name = "InputCollection")
    public JAXBElement<ImportVSetValueInfoSrvInputCollection> createInputCollection(ImportVSetValueInfoSrvInputCollection value) {
        return new JAXBElement<ImportVSetValueInfoSrvInputCollection>(_InputCollection_QNAME, ImportVSetValueInfoSrvInputCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportVSetValueInfoSrvInputItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", name = "InputItem")
    public JAXBElement<ImportVSetValueInfoSrvInputItem> createInputItem(ImportVSetValueInfoSrvInputItem value) {
        return new JAXBElement<ImportVSetValueInfoSrvInputItem>(_InputItem_QNAME, ImportVSetValueInfoSrvInputItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportVSetValueInfoSrvInputCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", name = "ImportVSetValueInfoSrvInputCollection")
    public JAXBElement<ImportVSetValueInfoSrvInputCollection> createImportVSetValueInfoSrvInputCollection(ImportVSetValueInfoSrvInputCollection value) {
        return new JAXBElement<ImportVSetValueInfoSrvInputCollection>(_ImportVSetValueInfoSrvInputCollection_QNAME, ImportVSetValueInfoSrvInputCollection.class, null, value);
    }

}
