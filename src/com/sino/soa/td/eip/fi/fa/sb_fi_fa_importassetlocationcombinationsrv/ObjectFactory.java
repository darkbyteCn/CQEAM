
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetlocationcombinationsrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetlocationcombinationsrv package.
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

    private final static QName _ErrorCollection_QNAME = new QName("http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv", "ErrorCollection");
    private final static QName _ErrorItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv", "ErrorItem");
    private final static QName _ResponseCollecion_QNAME = new QName("http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv", "ResponseCollecion");
    private final static QName _InputItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv", "InputItem");
    private final static QName _ResponseItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv", "ResponseItem");
    private final static QName _InputCollection_QNAME = new QName("http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv", "InputCollection");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetlocationcombinationsrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ErrorItem }
     * 
     */
    public ErrorItem createErrorItem() {
        return new ErrorItem();
    }

    /**
     * Create an instance of {@link ImportAssetLocationCombinationSrvInputItem }
     * 
     */
    public ImportAssetLocationCombinationSrvInputItem createImportAssetLocationCombinationSrvInputItem() {
        return new ImportAssetLocationCombinationSrvInputItem();
    }

    /**
     * Create an instance of {@link ImportAssetLocationCombinationSrvRequest }
     * 
     */
    public ImportAssetLocationCombinationSrvRequest createImportAssetLocationCombinationSrvRequest() {
        return new ImportAssetLocationCombinationSrvRequest();
    }

    /**
     * Create an instance of {@link ImportAssetLocationCombinationSrvInputCollection }
     * 
     */
    public ImportAssetLocationCombinationSrvInputCollection createImportAssetLocationCombinationSrvInputCollection() {
        return new ImportAssetLocationCombinationSrvInputCollection();
    }

    /**
     * Create an instance of {@link ResponseItem }
     * 
     */
    public ResponseItem createResponseItem() {
        return new ResponseItem();
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
     * Create an instance of {@link MsgHeader }
     * 
     */
    public MsgHeader createMsgHeader() {
        return new MsgHeader();
    }

    /**
     * Create an instance of {@link ImportAssetLocationCombinationSrvResponse }
     * 
     */
    public ImportAssetLocationCombinationSrvResponse createImportAssetLocationCombinationSrvResponse() {
        return new ImportAssetLocationCombinationSrvResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv", name = "ErrorCollection")
    public JAXBElement<ErrorCollection> createErrorCollection(ErrorCollection value) {
        return new JAXBElement<ErrorCollection>(_ErrorCollection_QNAME, ErrorCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv", name = "ErrorItem")
    public JAXBElement<ErrorItem> createErrorItem(ErrorItem value) {
        return new JAXBElement<ErrorItem>(_ErrorItem_QNAME, ErrorItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseCollecion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv", name = "ResponseCollecion")
    public JAXBElement<ResponseCollecion> createResponseCollecion(ResponseCollecion value) {
        return new JAXBElement<ResponseCollecion>(_ResponseCollecion_QNAME, ResponseCollecion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportAssetLocationCombinationSrvInputItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv", name = "InputItem")
    public JAXBElement<ImportAssetLocationCombinationSrvInputItem> createInputItem(ImportAssetLocationCombinationSrvInputItem value) {
        return new JAXBElement<ImportAssetLocationCombinationSrvInputItem>(_InputItem_QNAME, ImportAssetLocationCombinationSrvInputItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv", name = "ResponseItem")
    public JAXBElement<ResponseItem> createResponseItem(ResponseItem value) {
        return new JAXBElement<ResponseItem>(_ResponseItem_QNAME, ResponseItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportAssetLocationCombinationSrvInputCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv", name = "InputCollection")
    public JAXBElement<ImportAssetLocationCombinationSrvInputCollection> createInputCollection(ImportAssetLocationCombinationSrvInputCollection value) {
        return new JAXBElement<ImportAssetLocationCombinationSrvInputCollection>(_InputCollection_QNAME, ImportAssetLocationCombinationSrvInputCollection.class, null, value);
    }

}
