
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv package.
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

    private final static QName _BImportAssetLocCombSrvInputCollection_QNAME = new QName("http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", "BImportAssetLocCombSrvInputCollection");
    private final static QName _BImportAssetLocCombSrvInputItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", "BImportAssetLocCombSrvInputItem");
    private final static QName _ErrorCollection_QNAME = new QName("http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", "ErrorCollection");
    private final static QName _GroupBImportAssetLocCombSrvInputCollection_QNAME = new QName("http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", "GroupBImportAssetLocCombSrvInputCollection");
    private final static QName _ErrorItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", "ErrorItem");
    private final static QName _ResponseItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", "ResponseItem");
    private final static QName _ResponseCollecion_QNAME = new QName("http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", "ResponseCollecion");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv
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
     * Create an instance of {@link GroupBImportAssetLocCombSrvInputCollection }
     * 
     */
    public GroupBImportAssetLocCombSrvInputCollection createGroupBImportAssetLocCombSrvInputCollection() {
        return new GroupBImportAssetLocCombSrvInputCollection();
    }

    /**
     * Create an instance of {@link ResponseCollecion }
     * 
     */
    public ResponseCollecion createResponseCollecion() {
        return new ResponseCollecion();
    }

    /**
     * Create an instance of {@link BImportAssetLocCombSrvInputCollection }
     * 
     */
    public BImportAssetLocCombSrvInputCollection createBImportAssetLocCombSrvInputCollection() {
        return new BImportAssetLocCombSrvInputCollection();
    }

    /**
     * Create an instance of {@link BImportAssetLocCombSrvInputItem }
     * 
     */
    public BImportAssetLocCombSrvInputItem createBImportAssetLocCombSrvInputItem() {
        return new BImportAssetLocCombSrvInputItem();
    }

    /**
     * Create an instance of {@link ErrorCollection }
     * 
     */
    public ErrorCollection createErrorCollection() {
        return new ErrorCollection();
    }

    /**
     * Create an instance of {@link BImportAssetLocCombSrvResponse }
     * 
     */
    public BImportAssetLocCombSrvResponse createBImportAssetLocCombSrvResponse() {
        return new BImportAssetLocCombSrvResponse();
    }

    /**
     * Create an instance of {@link BImportAssetLocCombSrvRequest }
     * 
     */
    public BImportAssetLocCombSrvRequest createBImportAssetLocCombSrvRequest() {
        return new BImportAssetLocCombSrvRequest();
    }

    /**
     * Create an instance of {@link ResponseItem }
     * 
     */
    public ResponseItem createResponseItem() {
        return new ResponseItem();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BImportAssetLocCombSrvInputCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", name = "BImportAssetLocCombSrvInputCollection")
    public JAXBElement<BImportAssetLocCombSrvInputCollection> createBImportAssetLocCombSrvInputCollection(BImportAssetLocCombSrvInputCollection value) {
        return new JAXBElement<BImportAssetLocCombSrvInputCollection>(_BImportAssetLocCombSrvInputCollection_QNAME, BImportAssetLocCombSrvInputCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BImportAssetLocCombSrvInputItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", name = "BImportAssetLocCombSrvInputItem")
    public JAXBElement<BImportAssetLocCombSrvInputItem> createBImportAssetLocCombSrvInputItem(BImportAssetLocCombSrvInputItem value) {
        return new JAXBElement<BImportAssetLocCombSrvInputItem>(_BImportAssetLocCombSrvInputItem_QNAME, BImportAssetLocCombSrvInputItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", name = "ErrorCollection")
    public JAXBElement<ErrorCollection> createErrorCollection(ErrorCollection value) {
        return new JAXBElement<ErrorCollection>(_ErrorCollection_QNAME, ErrorCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GroupBImportAssetLocCombSrvInputCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", name = "GroupBImportAssetLocCombSrvInputCollection")
    public JAXBElement<GroupBImportAssetLocCombSrvInputCollection> createGroupBImportAssetLocCombSrvInputCollection(GroupBImportAssetLocCombSrvInputCollection value) {
        return new JAXBElement<GroupBImportAssetLocCombSrvInputCollection>(_GroupBImportAssetLocCombSrvInputCollection_QNAME, GroupBImportAssetLocCombSrvInputCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", name = "ErrorItem")
    public JAXBElement<ErrorItem> createErrorItem(ErrorItem value) {
        return new JAXBElement<ErrorItem>(_ErrorItem_QNAME, ErrorItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", name = "ResponseItem")
    public JAXBElement<ResponseItem> createResponseItem(ResponseItem value) {
        return new JAXBElement<ResponseItem>(_ResponseItem_QNAME, ResponseItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseCollecion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", name = "ResponseCollecion")
    public JAXBElement<ResponseCollecion> createResponseCollecion(ResponseCollecion value) {
        return new JAXBElement<ResponseCollecion>(_ResponseCollecion_QNAME, ResponseCollecion.class, null, value);
    }

}
