
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bcreateassettransincompanysrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bcreateassettransincompanysrv package.
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

    private final static QName _ErrorItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", "ErrorItem");
    private final static QName _BCreateAssetTransInCompanySrvInputItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", "BCreateAssetTransInCompanySrvInputItem");
    private final static QName _ResponseItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", "ResponseItem");
    private final static QName _GroupBCreateAssetTransInCompanySrvInputCollection_QNAME = new QName("http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", "GroupBCreateAssetTransInCompanySrvInputCollection");
    private final static QName _ResponseCollecion_QNAME = new QName("http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", "ResponseCollecion");
    private final static QName _BCreateAssetTransInCompanySrvInputCollection_QNAME = new QName("http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", "BCreateAssetTransInCompanySrvInputCollection");
    private final static QName _ErrorCollection_QNAME = new QName("http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", "ErrorCollection");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bcreateassettransincompanysrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BCreateAssetTransInCompanySrvInputCollection }
     * 
     */
    public BCreateAssetTransInCompanySrvInputCollection createBCreateAssetTransInCompanySrvInputCollection() {
        return new BCreateAssetTransInCompanySrvInputCollection();
    }

    /**
     * Create an instance of {@link BCreateAssetTransInCompanySrvRequest }
     * 
     */
    public BCreateAssetTransInCompanySrvRequest createBCreateAssetTransInCompanySrvRequest() {
        return new BCreateAssetTransInCompanySrvRequest();
    }

    /**
     * Create an instance of {@link ErrorCollection }
     * 
     */
    public ErrorCollection createErrorCollection() {
        return new ErrorCollection();
    }

    /**
     * Create an instance of {@link BCreateAssetTransInCompanySrvResponse }
     * 
     */
    public BCreateAssetTransInCompanySrvResponse createBCreateAssetTransInCompanySrvResponse() {
        return new BCreateAssetTransInCompanySrvResponse();
    }

    /**
     * Create an instance of {@link ResponseCollecion }
     * 
     */
    public ResponseCollecion createResponseCollecion() {
        return new ResponseCollecion();
    }

    /**
     * Create an instance of {@link ErrorItem }
     * 
     */
    public ErrorItem createErrorItem() {
        return new ErrorItem();
    }

    /**
     * Create an instance of {@link BCreateAssetTransInCompanySrvInputItem }
     * 
     */
    public BCreateAssetTransInCompanySrvInputItem createBCreateAssetTransInCompanySrvInputItem() {
        return new BCreateAssetTransInCompanySrvInputItem();
    }

    /**
     * Create an instance of {@link ResponseItem }
     * 
     */
    public ResponseItem createResponseItem() {
        return new ResponseItem();
    }

    /**
     * Create an instance of {@link GroupBCreateAssetTransInCompanySrvInputCollection }
     * 
     */
    public GroupBCreateAssetTransInCompanySrvInputCollection createGroupBCreateAssetTransInCompanySrvInputCollection() {
        return new GroupBCreateAssetTransInCompanySrvInputCollection();
    }

    /**
     * Create an instance of {@link MsgHeader }
     * 
     */
    public MsgHeader createMsgHeader() {
        return new MsgHeader();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", name = "ErrorItem")
    public JAXBElement<ErrorItem> createErrorItem(ErrorItem value) {
        return new JAXBElement<ErrorItem>(_ErrorItem_QNAME, ErrorItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BCreateAssetTransInCompanySrvInputItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", name = "BCreateAssetTransInCompanySrvInputItem")
    public JAXBElement<BCreateAssetTransInCompanySrvInputItem> createBCreateAssetTransInCompanySrvInputItem(BCreateAssetTransInCompanySrvInputItem value) {
        return new JAXBElement<BCreateAssetTransInCompanySrvInputItem>(_BCreateAssetTransInCompanySrvInputItem_QNAME, BCreateAssetTransInCompanySrvInputItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", name = "ResponseItem")
    public JAXBElement<ResponseItem> createResponseItem(ResponseItem value) {
        return new JAXBElement<ResponseItem>(_ResponseItem_QNAME, ResponseItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GroupBCreateAssetTransInCompanySrvInputCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", name = "GroupBCreateAssetTransInCompanySrvInputCollection")
    public JAXBElement<GroupBCreateAssetTransInCompanySrvInputCollection> createGroupBCreateAssetTransInCompanySrvInputCollection(GroupBCreateAssetTransInCompanySrvInputCollection value) {
        return new JAXBElement<GroupBCreateAssetTransInCompanySrvInputCollection>(_GroupBCreateAssetTransInCompanySrvInputCollection_QNAME, GroupBCreateAssetTransInCompanySrvInputCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseCollecion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", name = "ResponseCollecion")
    public JAXBElement<ResponseCollecion> createResponseCollecion(ResponseCollecion value) {
        return new JAXBElement<ResponseCollecion>(_ResponseCollecion_QNAME, ResponseCollecion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BCreateAssetTransInCompanySrvInputCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", name = "BCreateAssetTransInCompanySrvInputCollection")
    public JAXBElement<BCreateAssetTransInCompanySrvInputCollection> createBCreateAssetTransInCompanySrvInputCollection(BCreateAssetTransInCompanySrvInputCollection value) {
        return new JAXBElement<BCreateAssetTransInCompanySrvInputCollection>(_BCreateAssetTransInCompanySrvInputCollection_QNAME, BCreateAssetTransInCompanySrvInputCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", name = "ErrorCollection")
    public JAXBElement<ErrorCollection> createErrorCollection(ErrorCollection value) {
        return new JAXBElement<ErrorCollection>(_ErrorCollection_QNAME, ErrorCollection.class, null, value);
    }

}
