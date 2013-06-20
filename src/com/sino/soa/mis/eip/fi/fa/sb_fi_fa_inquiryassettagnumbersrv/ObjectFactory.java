
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_inquiryassettagnumbersrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.mis.eip.fi.fa.sb_fi_fa_inquiryassettagnumbersrv package.
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

    private final static QName _SBFIFAInquiryAssetTagNumberSrvRequest_QNAME = new QName("http://mss.cmcc.com/SB_FI_FA_InquiryAssetTagNumberSrv", "SB_FI_FA_InquiryAssetTagNumberSrvRequest");
    private final static QName _SBFIFAInquiryAssetTagNumberSrvOutputItem_QNAME = new QName("http://mss.cmcc.com/SB_FI_FA_InquiryAssetTagNumberSrv", "SB_FI_FA_InquiryAssetTagNumberSrvOutputItem");
    private final static QName _SBFIFAInquiryAssetTagNumberSrvResponse_QNAME = new QName("http://mss.cmcc.com/SB_FI_FA_InquiryAssetTagNumberSrv", "SB_FI_FA_InquiryAssetTagNumberSrvResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.mis.eip.fi.fa.sb_fi_fa_inquiryassettagnumbersrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SBFIFAInquiryAssetTagNumberSrvOutputCollection }
     * 
     */
    public SBFIFAInquiryAssetTagNumberSrvOutputCollection createSBFIFAInquiryAssetTagNumberSrvOutputCollection() {
        return new SBFIFAInquiryAssetTagNumberSrvOutputCollection();
    }

    /**
     * Create an instance of {@link SBFIFAInquiryAssetTagNumberSrvResponse }
     * 
     */
    public SBFIFAInquiryAssetTagNumberSrvResponse createSBFIFAInquiryAssetTagNumberSrvResponse() {
        return new SBFIFAInquiryAssetTagNumberSrvResponse();
    }

    /**
     * Create an instance of {@link SBFIFAInquiryAssetTagNumberSrvOutputItem }
     * 
     */
    public SBFIFAInquiryAssetTagNumberSrvOutputItem createSBFIFAInquiryAssetTagNumberSrvOutputItem() {
        return new SBFIFAInquiryAssetTagNumberSrvOutputItem();
    }

    /**
     * Create an instance of {@link SBFIFAInquiryAssetTagNumberSrvRequest }
     * 
     */
    public SBFIFAInquiryAssetTagNumberSrvRequest createSBFIFAInquiryAssetTagNumberSrvRequest() {
        return new SBFIFAInquiryAssetTagNumberSrvRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBFIFAInquiryAssetTagNumberSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mss.cmcc.com/SB_FI_FA_InquiryAssetTagNumberSrv", name = "SB_FI_FA_InquiryAssetTagNumberSrvRequest")
    public JAXBElement<SBFIFAInquiryAssetTagNumberSrvRequest> createSBFIFAInquiryAssetTagNumberSrvRequest(SBFIFAInquiryAssetTagNumberSrvRequest value) {
        return new JAXBElement<SBFIFAInquiryAssetTagNumberSrvRequest>(_SBFIFAInquiryAssetTagNumberSrvRequest_QNAME, SBFIFAInquiryAssetTagNumberSrvRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBFIFAInquiryAssetTagNumberSrvOutputItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mss.cmcc.com/SB_FI_FA_InquiryAssetTagNumberSrv", name = "SB_FI_FA_InquiryAssetTagNumberSrvOutputItem")
    public JAXBElement<SBFIFAInquiryAssetTagNumberSrvOutputItem> createSBFIFAInquiryAssetTagNumberSrvOutputItem(SBFIFAInquiryAssetTagNumberSrvOutputItem value) {
        return new JAXBElement<SBFIFAInquiryAssetTagNumberSrvOutputItem>(_SBFIFAInquiryAssetTagNumberSrvOutputItem_QNAME, SBFIFAInquiryAssetTagNumberSrvOutputItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBFIFAInquiryAssetTagNumberSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mss.cmcc.com/SB_FI_FA_InquiryAssetTagNumberSrv", name = "SB_FI_FA_InquiryAssetTagNumberSrvResponse")
    public JAXBElement<SBFIFAInquiryAssetTagNumberSrvResponse> createSBFIFAInquiryAssetTagNumberSrvResponse(SBFIFAInquiryAssetTagNumberSrvResponse value) {
        return new JAXBElement<SBFIFAInquiryAssetTagNumberSrvResponse>(_SBFIFAInquiryAssetTagNumberSrvResponse_QNAME, SBFIFAInquiryAssetTagNumberSrvResponse.class, null, value);
    }

}
