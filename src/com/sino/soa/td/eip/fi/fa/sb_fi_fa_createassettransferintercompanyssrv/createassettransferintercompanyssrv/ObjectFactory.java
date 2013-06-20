
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.createassettransferintercompanyssrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.importsrvresponse.ImportSrvResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.createassettransferintercompanyssrv package.
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

    private final static QName _CreateAssetTransferIntercompanysSrvResponse_QNAME = new QName("http://eip.zte.com/common/fa/CreateAssetTransferIntercompanysSrv", "CreateAssetTransferIntercompanysSrvResponse");
    private final static QName _CreateAssetTransferIntercompanysSrvInputCollection_QNAME = new QName("http://eip.zte.com/common/fa/CreateAssetTransferIntercompanysSrv", "CreateAssetTransferIntercompanysSrvInputCollection");
    private final static QName _CreateAssetTransferIntercompanysSrvInputItem_QNAME = new QName("http://eip.zte.com/common/fa/CreateAssetTransferIntercompanysSrv", "CreateAssetTransferIntercompanysSrvInputItem");
    private final static QName _CreateAssetTransferIntercompanysSrvRequest_QNAME = new QName("http://eip.zte.com/common/fa/CreateAssetTransferIntercompanysSrv", "CreateAssetTransferIntercompanysSrvRequest");
    private final static QName _CreateAssetTransferIntercompanysSrvGroupCollection_QNAME = new QName("http://eip.zte.com/common/fa/CreateAssetTransferIntercompanysSrv", "CreateAssetTransferIntercompanysSrvGroupCollection");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.createassettransferintercompanyssrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateAssetTransferIntercompanysSrvInputCollection }
     * 
     */
    public CreateAssetTransferIntercompanysSrvInputCollection createCreateAssetTransferIntercompanysSrvInputCollection() {
        return new CreateAssetTransferIntercompanysSrvInputCollection();
    }

    /**
     * Create an instance of {@link CreateAssetTransferIntercompanysSrvGroupCollection }
     * 
     */
    public CreateAssetTransferIntercompanysSrvGroupCollection createCreateAssetTransferIntercompanysSrvGroupCollection() {
        return new CreateAssetTransferIntercompanysSrvGroupCollection();
    }

    /**
     * Create an instance of {@link CreateAssetTransferIntercompanysSrvInputItem }
     * 
     */
    public CreateAssetTransferIntercompanysSrvInputItem createCreateAssetTransferIntercompanysSrvInputItem() {
        return new CreateAssetTransferIntercompanysSrvInputItem();
    }

    /**
     * Create an instance of {@link CreateAssetTransferIntercompanysSrvRequest }
     * 
     */
    public CreateAssetTransferIntercompanysSrvRequest createCreateAssetTransferIntercompanysSrvRequest() {
        return new CreateAssetTransferIntercompanysSrvRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/fa/CreateAssetTransferIntercompanysSrv", name = "CreateAssetTransferIntercompanysSrvResponse")
    public JAXBElement<ImportSrvResponse> createCreateAssetTransferIntercompanysSrvResponse(ImportSrvResponse value) {
        return new JAXBElement<ImportSrvResponse>(_CreateAssetTransferIntercompanysSrvResponse_QNAME, ImportSrvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAssetTransferIntercompanysSrvInputCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/fa/CreateAssetTransferIntercompanysSrv", name = "CreateAssetTransferIntercompanysSrvInputCollection")
    public JAXBElement<CreateAssetTransferIntercompanysSrvInputCollection> createCreateAssetTransferIntercompanysSrvInputCollection(CreateAssetTransferIntercompanysSrvInputCollection value) {
        return new JAXBElement<CreateAssetTransferIntercompanysSrvInputCollection>(_CreateAssetTransferIntercompanysSrvInputCollection_QNAME, CreateAssetTransferIntercompanysSrvInputCollection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAssetTransferIntercompanysSrvInputItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/fa/CreateAssetTransferIntercompanysSrv", name = "CreateAssetTransferIntercompanysSrvInputItem")
    public JAXBElement<CreateAssetTransferIntercompanysSrvInputItem> createCreateAssetTransferIntercompanysSrvInputItem(CreateAssetTransferIntercompanysSrvInputItem value) {
        return new JAXBElement<CreateAssetTransferIntercompanysSrvInputItem>(_CreateAssetTransferIntercompanysSrvInputItem_QNAME, CreateAssetTransferIntercompanysSrvInputItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAssetTransferIntercompanysSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/fa/CreateAssetTransferIntercompanysSrv", name = "CreateAssetTransferIntercompanysSrvRequest")
    public JAXBElement<CreateAssetTransferIntercompanysSrvRequest> createCreateAssetTransferIntercompanysSrvRequest(CreateAssetTransferIntercompanysSrvRequest value) {
        return new JAXBElement<CreateAssetTransferIntercompanysSrvRequest>(_CreateAssetTransferIntercompanysSrvRequest_QNAME, CreateAssetTransferIntercompanysSrvRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAssetTransferIntercompanysSrvGroupCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/fa/CreateAssetTransferIntercompanysSrv", name = "CreateAssetTransferIntercompanysSrvGroupCollection")
    public JAXBElement<CreateAssetTransferIntercompanysSrvGroupCollection> createCreateAssetTransferIntercompanysSrvGroupCollection(CreateAssetTransferIntercompanysSrvGroupCollection value) {
        return new JAXBElement<CreateAssetTransferIntercompanysSrvGroupCollection>(_CreateAssetTransferIntercompanysSrvGroupCollection_QNAME, CreateAssetTransferIntercompanysSrvGroupCollection.class, null, value);
    }

}
