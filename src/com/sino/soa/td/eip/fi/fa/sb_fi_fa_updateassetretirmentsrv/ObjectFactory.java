
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_updateassetretirmentsrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.td.eip.fi.fa.sb_fi_fa_updateassetretirmentsrv package.
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

    private final static QName _UpdateAssetRetirmentSrvInputItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv", "UpdateAssetRetirmentSrvInputItem");
    private final static QName _ResponseItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv", "ResponseItem");
    private final static QName _GroupUpdateAssetRetirmentSrvInputCollection_QNAME = new QName("http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv", "GroupUpdateAssetRetirmentSrvInputCollection");
    private final static QName _ErrorCollection_QNAME = new QName("http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv", "ErrorCollection");
    private final static QName _UpdateAssetRetirmentSrvInputCollection_QNAME = new QName("http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv", "UpdateAssetRetirmentSrvInputCollection");
    private final static QName _ResponseCollecion_QNAME = new QName("http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv", "ResponseCollecion");
    private final static QName _ErrorItem_QNAME = new QName("http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv", "ErrorItem");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.td.eip.fi.fa.sb_fi_fa_updateassetretirmentsrv
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
     * Create an instance of {@link UpdateAssetRetirmentSrvInputItem }
     * 
     */
    public UpdateAssetRetirmentSrvInputItem createUpdateAssetRetirmentSrvInputItem() {
        return new UpdateAssetRetirmentSrvInputItem();
    }

    /**
     * Create an instance of {@link UpdateAssetRetirmentSrvRequest }
     * 
     */
    public UpdateAssetRetirmentSrvRequest createUpdateAssetRetirmentSrvRequest() {
        return new UpdateAssetRetirmentSrvRequest();
    }

    /**
     * Create an instance of {@link UpdateAssetRetirmentSrvResponse }
     * 
     */
    public UpdateAssetRetirmentSrvResponse createUpdateAssetRetirmentSrvResponse() {
        return new UpdateAssetRetirmentSrvResponse();
    }

    /**
     * Create an instance of {@link MsgHeader }
     * 
     */
    public MsgHeader createMsgHeader() {
        return new MsgHeader();
    }

    /**
     * Create an instance of {@link UpdateAssetRetirmentSrvInputCollection }
     * 
     */
    public UpdateAssetRetirmentSrvInputCollection createUpdateAssetRetirmentSrvInputCollection() {
        return new UpdateAssetRetirmentSrvInputCollection();
    }

    /**
     * Create an instance of {@link GroupUpdateAssetRetirmentSrvInputCollection }
     * 
     */
    public GroupUpdateAssetRetirmentSrvInputCollection createGroupUpdateAssetRetirmentSrvInputCollection() {
        return new GroupUpdateAssetRetirmentSrvInputCollection();
    }

    /**
     * Create an instance of {@link ResponseCollecion }
     * 
     */
    public ResponseCollecion createResponseCollecion() {
        return new ResponseCollecion();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAssetRetirmentSrvInputItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv", name = "UpdateAssetRetirmentSrvInputItem")
    public JAXBElement<UpdateAssetRetirmentSrvInputItem> createUpdateAssetRetirmentSrvInputItem(UpdateAssetRetirmentSrvInputItem value) {
        return new JAXBElement<UpdateAssetRetirmentSrvInputItem>(_UpdateAssetRetirmentSrvInputItem_QNAME, UpdateAssetRetirmentSrvInputItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv", name = "ResponseItem")
    public JAXBElement<ResponseItem> createResponseItem(ResponseItem value) {
        return new JAXBElement<ResponseItem>(_ResponseItem_QNAME, ResponseItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GroupUpdateAssetRetirmentSrvInputCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv", name = "GroupUpdateAssetRetirmentSrvInputCollection")
    public JAXBElement<GroupUpdateAssetRetirmentSrvInputCollection> createGroupUpdateAssetRetirmentSrvInputCollection(GroupUpdateAssetRetirmentSrvInputCollection value) {
        return new JAXBElement<GroupUpdateAssetRetirmentSrvInputCollection>(_GroupUpdateAssetRetirmentSrvInputCollection_QNAME, GroupUpdateAssetRetirmentSrvInputCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv", name = "ErrorCollection")
    public JAXBElement<ErrorCollection> createErrorCollection(ErrorCollection value) {
        return new JAXBElement<ErrorCollection>(_ErrorCollection_QNAME, ErrorCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAssetRetirmentSrvInputCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv", name = "UpdateAssetRetirmentSrvInputCollection")
    public JAXBElement<UpdateAssetRetirmentSrvInputCollection> createUpdateAssetRetirmentSrvInputCollection(UpdateAssetRetirmentSrvInputCollection value) {
        return new JAXBElement<UpdateAssetRetirmentSrvInputCollection>(_UpdateAssetRetirmentSrvInputCollection_QNAME, UpdateAssetRetirmentSrvInputCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseCollecion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv", name = "ResponseCollecion")
    public JAXBElement<ResponseCollecion> createResponseCollecion(ResponseCollecion value) {
        return new JAXBElement<ResponseCollecion>(_ResponseCollecion_QNAME, ResponseCollecion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv", name = "ErrorItem")
    public JAXBElement<ErrorItem> createErrorItem(ErrorItem value) {
        return new JAXBElement<ErrorItem>(_ErrorItem_QNAME, ErrorItem.class, null, value);
    }

}
