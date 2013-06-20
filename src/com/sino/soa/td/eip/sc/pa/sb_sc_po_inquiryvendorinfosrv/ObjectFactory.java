
package com.sino.soa.td.eip.sc.pa.sb_sc_po_inquiryvendorinfosrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.td.eip.sc.pa.sb_sc_po_inquiryvendorinfosrv package.
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

    private final static QName _InquiryVendorInfoSrvResponse_QNAME = new QName("http://xmlns.oracle.com/SB_SC_PO_InquiryVendorInfoSrv", "InquiryVendorInfoSrvResponse");
    private final static QName _LineItem_QNAME = new QName("http://xmlns.oracle.com/SB_SC_PO_InquiryVendorInfoSrv", "lineItem");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.td.eip.sc.pa.sb_sc_po_inquiryvendorinfosrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link VendorSiteInfoCollection }
     * 
     */
    public VendorSiteInfoCollection createVendorSiteInfoCollection() {
        return new VendorSiteInfoCollection();
    }

    /**
     * Create an instance of {@link InquiryVendorInfoSrvRequest }
     * 
     */
    public InquiryVendorInfoSrvRequest createInquiryVendorInfoSrvRequest() {
        return new InquiryVendorInfoSrvRequest();
    }

    /**
     * Create an instance of {@link VendorSiteInfoItem }
     * 
     */
    public VendorSiteInfoItem createVendorSiteInfoItem() {
        return new VendorSiteInfoItem();
    }

    /**
     * Create an instance of {@link InquiryVendorInfoSrvOutputCollection }
     * 
     */
    public InquiryVendorInfoSrvOutputCollection createInquiryVendorInfoSrvOutputCollection() {
        return new InquiryVendorInfoSrvOutputCollection();
    }

    /**
     * Create an instance of {@link InquiryVendorInfoSrvOutputItem }
     * 
     */
    public InquiryVendorInfoSrvOutputItem createInquiryVendorInfoSrvOutputItem() {
        return new InquiryVendorInfoSrvOutputItem();
    }

    /**
     * Create an instance of {@link InquiryVendorInfoSrvResponse }
     * 
     */
    public InquiryVendorInfoSrvResponse createInquiryVendorInfoSrvResponse() {
        return new InquiryVendorInfoSrvResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InquiryVendorInfoSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/SB_SC_PO_InquiryVendorInfoSrv", name = "InquiryVendorInfoSrvResponse")
    public JAXBElement<InquiryVendorInfoSrvResponse> createInquiryVendorInfoSrvResponse(InquiryVendorInfoSrvResponse value) {
        return new JAXBElement<InquiryVendorInfoSrvResponse>(_InquiryVendorInfoSrvResponse_QNAME, InquiryVendorInfoSrvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VendorSiteInfoCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/SB_SC_PO_InquiryVendorInfoSrv", name = "lineItem")
    public JAXBElement<VendorSiteInfoCollection> createLineItem(VendorSiteInfoCollection value) {
        return new JAXBElement<VendorSiteInfoCollection>(_LineItem_QNAME, VendorSiteInfoCollection.class, null, value);
    }

}
