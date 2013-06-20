
package com.sino.soa.mis.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.pcbpel.adapter.db.empbaseinfosrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.mis.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.pcbpel.adapter.db.empbaseinfosrv package.
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

    private final static QName _InquiryEmpBaseInfoSrvResponse_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/EmpBaseInfoSrv", "InquiryEmpBaseInfoSrvResponse");
    private final static QName _InquiryEmpBaseInfoSrvRequest_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/EmpBaseInfoSrv", "InquiryEmpBaseInfoSrvRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.mis.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.pcbpel.adapter.db.empbaseinfosrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InquiryEmpBaseInfoSrvResponse }
     * 
     */
    public InquiryEmpBaseInfoSrvResponse createInquiryEmpBaseInfoSrvResponse() {
        return new InquiryEmpBaseInfoSrvResponse();
    }

    /**
     * Create an instance of {@link InquiryEmpBaseInfoSrvOutputItem }
     * 
     */
    public InquiryEmpBaseInfoSrvOutputItem createInquiryEmpBaseInfoSrvOutputItem() {
        return new InquiryEmpBaseInfoSrvOutputItem();
    }

    /**
     * Create an instance of {@link InquiryEmpBaseInfoSrvRequest }
     * 
     */
    public InquiryEmpBaseInfoSrvRequest createInquiryEmpBaseInfoSrvRequest() {
        return new InquiryEmpBaseInfoSrvRequest();
    }

    /**
     * Create an instance of {@link InquiryEmpBaseInfoSrvOutputCollection }
     * 
     */
    public InquiryEmpBaseInfoSrvOutputCollection createInquiryEmpBaseInfoSrvOutputCollection() {
        return new InquiryEmpBaseInfoSrvOutputCollection();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InquiryEmpBaseInfoSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/EmpBaseInfoSrv", name = "InquiryEmpBaseInfoSrvResponse")
    public JAXBElement<InquiryEmpBaseInfoSrvResponse> createInquiryEmpBaseInfoSrvResponse(InquiryEmpBaseInfoSrvResponse value) {
        return new JAXBElement<InquiryEmpBaseInfoSrvResponse>(_InquiryEmpBaseInfoSrvResponse_QNAME, InquiryEmpBaseInfoSrvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InquiryEmpBaseInfoSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/EmpBaseInfoSrv", name = "InquiryEmpBaseInfoSrvRequest")
    public JAXBElement<InquiryEmpBaseInfoSrvRequest> createInquiryEmpBaseInfoSrvRequest(InquiryEmpBaseInfoSrvRequest value) {
        return new JAXBElement<InquiryEmpBaseInfoSrvRequest>(_InquiryEmpBaseInfoSrvRequest_QNAME, InquiryEmpBaseInfoSrvRequest.class, null, value);
    }

}
