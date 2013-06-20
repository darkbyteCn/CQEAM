
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetretirmentsrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetretirmentsrv package.
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

    private final static QName _ErrorItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_ImportAssetRetirmentSrv", "ErrorItem");
    private final static QName _ResponseItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_ImportAssetRetirmentSrv", "ResponseItem");
    private final static QName _ResponseCollecion_QNAME = new QName("http://eip.zte.com/SB_FI_FA_ImportAssetRetirmentSrv", "ResponseCollecion");
    private final static QName _ErrorCollection_QNAME = new QName("http://eip.zte.com/SB_FI_FA_ImportAssetRetirmentSrv", "ErrorCollection");
    private final static QName _ImportAssetRetirmentSrvItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_ImportAssetRetirmentSrv", "ImportAssetRetirmentSrvItem");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetretirmentsrv
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
     * Create an instance of {@link ErrorCollection }
     * 
     */
    public ErrorCollection createErrorCollection() {
        return new ErrorCollection();
    }

    /**
     * Create an instance of {@link ResponseCollecion }
     * 
     */
    public ResponseCollecion createResponseCollecion() {
        return new ResponseCollecion();
    }

    /**
     * Create an instance of {@link ImportAssetRetirmentSrvRequest }
     * 
     */
    public ImportAssetRetirmentSrvRequest createImportAssetRetirmentSrvRequest() {
        return new ImportAssetRetirmentSrvRequest();
    }

    /**
     * Create an instance of {@link ImportAssetRetirmentSrvInputItem }
     * 
     */
    public ImportAssetRetirmentSrvInputItem createImportAssetRetirmentSrvInputItem() {
        return new ImportAssetRetirmentSrvInputItem();
    }

    /**
     * Create an instance of {@link ImportAssetRetirmentSrvCollection }
     * 
     */
    public ImportAssetRetirmentSrvCollection createImportAssetRetirmentSrvCollection() {
        return new ImportAssetRetirmentSrvCollection();
    }

    /**
     * Create an instance of {@link ResponseItem }
     * 
     */
    public ResponseItem createResponseItem() {
        return new ResponseItem();
    }

    /**
     * Create an instance of {@link ImportAssetRetirmentSrvInputCollection }
     * 
     */
    public ImportAssetRetirmentSrvInputCollection createImportAssetRetirmentSrvInputCollection() {
        return new ImportAssetRetirmentSrvInputCollection();
    }

    /**
     * Create an instance of {@link ErrorItem }
     * 
     */
    public ErrorItem createErrorItem() {
        return new ErrorItem();
    }

    /**
     * Create an instance of {@link ImportAssetRetirmentSrvResponse }
     * 
     */
    public ImportAssetRetirmentSrvResponse createImportAssetRetirmentSrvResponse() {
        return new ImportAssetRetirmentSrvResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_ImportAssetRetirmentSrv", name = "ErrorItem")
    public JAXBElement<ErrorItem> createErrorItem(ErrorItem value) {
        return new JAXBElement<ErrorItem>(_ErrorItem_QNAME, ErrorItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_ImportAssetRetirmentSrv", name = "ResponseItem")
    public JAXBElement<ResponseItem> createResponseItem(ResponseItem value) {
        return new JAXBElement<ResponseItem>(_ResponseItem_QNAME, ResponseItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseCollecion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_ImportAssetRetirmentSrv", name = "ResponseCollecion")
    public JAXBElement<ResponseCollecion> createResponseCollecion(ResponseCollecion value) {
        return new JAXBElement<ResponseCollecion>(_ResponseCollecion_QNAME, ResponseCollecion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_ImportAssetRetirmentSrv", name = "ErrorCollection")
    public JAXBElement<ErrorCollection> createErrorCollection(ErrorCollection value) {
        return new JAXBElement<ErrorCollection>(_ErrorCollection_QNAME, ErrorCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportAssetRetirmentSrvInputItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_ImportAssetRetirmentSrv", name = "ImportAssetRetirmentSrvItem")
    public JAXBElement<ImportAssetRetirmentSrvInputItem> createImportAssetRetirmentSrvItem(ImportAssetRetirmentSrvInputItem value) {
        return new JAXBElement<ImportAssetRetirmentSrvInputItem>(_ImportAssetRetirmentSrvItem_QNAME, ImportAssetRetirmentSrvInputItem.class, null, value);
    }

}
