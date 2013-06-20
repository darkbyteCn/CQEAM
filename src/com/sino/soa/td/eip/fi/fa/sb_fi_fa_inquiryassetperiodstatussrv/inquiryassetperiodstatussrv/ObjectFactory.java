
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.inquiryassetperiodstatussrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.inquiryassetperiodstatussrv package.
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

    private final static QName _InquiryAssetPeriodStatusSrvResponse_QNAME = new QName("http://eip.zte.com/common/InquiryAssetPeriodStatusSrv", "InquiryAssetPeriodStatusSrvResponse");
    private final static QName _InquiryAssetPeriodStatusSrvRequest_QNAME = new QName("http://eip.zte.com/common/InquiryAssetPeriodStatusSrv", "InquiryAssetPeriodStatusSrvRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.inquiryassetperiodstatussrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InquiryAssetPeriodStatusSrvRequest }
     * 
     */
    public InquiryAssetPeriodStatusSrvRequest createInquiryAssetPeriodStatusSrvRequest() {
        return new InquiryAssetPeriodStatusSrvRequest();
    }

    /**
     * Create an instance of {@link InquiryAssetPeriodStatusSrvOutputCollection }
     * 
     */
    public InquiryAssetPeriodStatusSrvOutputCollection createInquiryAssetPeriodStatusSrvOutputCollection() {
        return new InquiryAssetPeriodStatusSrvOutputCollection();
    }

    /**
     * Create an instance of {@link InquiryAssetPeriodStatusSrvOutputItem }
     * 
     */
    public InquiryAssetPeriodStatusSrvOutputItem createInquiryAssetPeriodStatusSrvOutputItem() {
        return new InquiryAssetPeriodStatusSrvOutputItem();
    }

    /**
     * Create an instance of {@link InquiryAssetPeriodStatusSrvResponse }
     * 
     */
    public InquiryAssetPeriodStatusSrvResponse createInquiryAssetPeriodStatusSrvResponse() {
        return new InquiryAssetPeriodStatusSrvResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InquiryAssetPeriodStatusSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/InquiryAssetPeriodStatusSrv", name = "InquiryAssetPeriodStatusSrvResponse")
    public JAXBElement<InquiryAssetPeriodStatusSrvResponse> createInquiryAssetPeriodStatusSrvResponse(InquiryAssetPeriodStatusSrvResponse value) {
        return new JAXBElement<InquiryAssetPeriodStatusSrvResponse>(_InquiryAssetPeriodStatusSrvResponse_QNAME, InquiryAssetPeriodStatusSrvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InquiryAssetPeriodStatusSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/InquiryAssetPeriodStatusSrv", name = "InquiryAssetPeriodStatusSrvRequest")
    public JAXBElement<InquiryAssetPeriodStatusSrvRequest> createInquiryAssetPeriodStatusSrvRequest(InquiryAssetPeriodStatusSrvRequest value) {
        return new JAXBElement<InquiryAssetPeriodStatusSrvRequest>(_InquiryAssetPeriodStatusSrvRequest_QNAME, InquiryAssetPeriodStatusSrvRequest.class, null, value);
    }

}
