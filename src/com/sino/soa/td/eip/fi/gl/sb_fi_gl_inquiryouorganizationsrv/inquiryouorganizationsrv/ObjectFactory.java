
package com.sino.soa.td.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.inquiryouorganizationsrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.td.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.inquiryouorganizationsrv package.
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

    private final static QName _InquiryOuOrganizationSrvResponse_QNAME = new QName("http://eip.zte.com/common/gl/InquiryOuOrganizationSrv", "InquiryOuOrganizationSrvResponse");
    private final static QName _InquiryOuOrganizationSrvRequest_QNAME = new QName("http://eip.zte.com/common/gl/InquiryOuOrganizationSrv", "InquiryOuOrganizationSrvRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.td.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.inquiryouorganizationsrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InquiryOuOrganizationSrvRequest }
     * 
     */
    public InquiryOuOrganizationSrvRequest createInquiryOuOrganizationSrvRequest() {
        return new InquiryOuOrganizationSrvRequest();
    }

    /**
     * Create an instance of {@link InquiryOuOrganizationSrvOutputCollection }
     * 
     */
    public InquiryOuOrganizationSrvOutputCollection createInquiryOuOrganizationSrvOutputCollection() {
        return new InquiryOuOrganizationSrvOutputCollection();
    }

    /**
     * Create an instance of {@link InquiryOuOrganizationSrvOutputItem }
     * 
     */
    public InquiryOuOrganizationSrvOutputItem createInquiryOuOrganizationSrvOutputItem() {
        return new InquiryOuOrganizationSrvOutputItem();
    }

    /**
     * Create an instance of {@link InquiryOuOrganizationSrvResponse }
     * 
     */
    public InquiryOuOrganizationSrvResponse createInquiryOuOrganizationSrvResponse() {
        return new InquiryOuOrganizationSrvResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InquiryOuOrganizationSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/gl/InquiryOuOrganizationSrv", name = "InquiryOuOrganizationSrvResponse")
    public JAXBElement<InquiryOuOrganizationSrvResponse> createInquiryOuOrganizationSrvResponse(InquiryOuOrganizationSrvResponse value) {
        return new JAXBElement<InquiryOuOrganizationSrvResponse>(_InquiryOuOrganizationSrvResponse_QNAME, InquiryOuOrganizationSrvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InquiryOuOrganizationSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/gl/InquiryOuOrganizationSrv", name = "InquiryOuOrganizationSrvRequest")
    public JAXBElement<InquiryOuOrganizationSrvRequest> createInquiryOuOrganizationSrvRequest(InquiryOuOrganizationSrvRequest value) {
        return new JAXBElement<InquiryOuOrganizationSrvRequest>(_InquiryOuOrganizationSrvRequest_QNAME, InquiryOuOrganizationSrvRequest.class, null, value);
    }

}
