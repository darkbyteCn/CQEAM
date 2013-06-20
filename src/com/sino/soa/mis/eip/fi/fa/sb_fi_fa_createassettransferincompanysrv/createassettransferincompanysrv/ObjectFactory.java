
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.createassettransferincompanysrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.importsrvresponse.ImportSrvResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.createassettransferincompanysrv package.
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

    private final static QName _CreateAssetTransferInCompanySrvResponse_QNAME = new QName("http://eip.zte.com/common/fa/CreateAssetTransferInCompanySrv", "CreateAssetTransferInCompanySrvResponse");
    private final static QName _CreateAssetTransferInCompanySrvGroupCollection_QNAME = new QName("http://eip.zte.com/common/fa/CreateAssetTransferInCompanySrv", "CreateAssetTransferInCompanySrvGroupCollection");
    private final static QName _CreateAssetTransferInCompanySrvInputCollection_QNAME = new QName("http://eip.zte.com/common/fa/CreateAssetTransferInCompanySrv", "CreateAssetTransferInCompanySrvInputCollection");
    private final static QName _CreateAssetTransferInCompanySrvRequest_QNAME = new QName("http://eip.zte.com/common/fa/CreateAssetTransferInCompanySrv", "CreateAssetTransferInCompanySrvRequest");
    private final static QName _CreateAssetTransferInCompanySrvInputItem_QNAME = new QName("http://eip.zte.com/common/fa/CreateAssetTransferInCompanySrv", "CreateAssetTransferInCompanySrvInputItem");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.createassettransferincompanysrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateAssetTransferInCompanySrvRequest }
     * 
     */
    public CreateAssetTransferInCompanySrvRequest createCreateAssetTransferInCompanySrvRequest() {
        return new CreateAssetTransferInCompanySrvRequest();
    }

    /**
     * Create an instance of {@link CreateAssetTransferInCompanySrvGroupCollection }
     * 
     */
    public CreateAssetTransferInCompanySrvGroupCollection createCreateAssetTransferInCompanySrvGroupCollection() {
        return new CreateAssetTransferInCompanySrvGroupCollection();
    }

    /**
     * Create an instance of {@link CreateAssetTransferInCompanySrvInputCollection }
     * 
     */
    public CreateAssetTransferInCompanySrvInputCollection createCreateAssetTransferInCompanySrvInputCollection() {
        return new CreateAssetTransferInCompanySrvInputCollection();
    }

    /**
     * Create an instance of {@link CreateAssetTransferInCompanySrvInputItem }
     * 
     */
    public CreateAssetTransferInCompanySrvInputItem createCreateAssetTransferInCompanySrvInputItem() {
        return new CreateAssetTransferInCompanySrvInputItem();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/fa/CreateAssetTransferInCompanySrv", name = "CreateAssetTransferInCompanySrvResponse")
    public JAXBElement<ImportSrvResponse> createCreateAssetTransferInCompanySrvResponse(ImportSrvResponse value) {
        return new JAXBElement<ImportSrvResponse>(_CreateAssetTransferInCompanySrvResponse_QNAME, ImportSrvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAssetTransferInCompanySrvGroupCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/fa/CreateAssetTransferInCompanySrv", name = "CreateAssetTransferInCompanySrvGroupCollection")
    public JAXBElement<CreateAssetTransferInCompanySrvGroupCollection> createCreateAssetTransferInCompanySrvGroupCollection(CreateAssetTransferInCompanySrvGroupCollection value) {
        return new JAXBElement<CreateAssetTransferInCompanySrvGroupCollection>(_CreateAssetTransferInCompanySrvGroupCollection_QNAME, CreateAssetTransferInCompanySrvGroupCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAssetTransferInCompanySrvInputCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/fa/CreateAssetTransferInCompanySrv", name = "CreateAssetTransferInCompanySrvInputCollection")
    public JAXBElement<CreateAssetTransferInCompanySrvInputCollection> createCreateAssetTransferInCompanySrvInputCollection(CreateAssetTransferInCompanySrvInputCollection value) {
        return new JAXBElement<CreateAssetTransferInCompanySrvInputCollection>(_CreateAssetTransferInCompanySrvInputCollection_QNAME, CreateAssetTransferInCompanySrvInputCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAssetTransferInCompanySrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/fa/CreateAssetTransferInCompanySrv", name = "CreateAssetTransferInCompanySrvRequest")
    public JAXBElement<CreateAssetTransferInCompanySrvRequest> createCreateAssetTransferInCompanySrvRequest(CreateAssetTransferInCompanySrvRequest value) {
        return new JAXBElement<CreateAssetTransferInCompanySrvRequest>(_CreateAssetTransferInCompanySrvRequest_QNAME, CreateAssetTransferInCompanySrvRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAssetTransferInCompanySrvInputItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/fa/CreateAssetTransferInCompanySrv", name = "CreateAssetTransferInCompanySrvInputItem")
    public JAXBElement<CreateAssetTransferInCompanySrvInputItem> createCreateAssetTransferInCompanySrvInputItem(CreateAssetTransferInCompanySrvInputItem value) {
        return new JAXBElement<CreateAssetTransferInCompanySrvInputItem>(_CreateAssetTransferInCompanySrvInputItem_QNAME, CreateAssetTransferInCompanySrvInputItem.class, null, value);
    }

}
