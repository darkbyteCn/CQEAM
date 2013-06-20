
package com.sino.soa.td.eip.hr.hr.sb_hr_hr_pageinquiryempassigninfosrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.td.eip.hr.hr.sb_hr_hr_pageinquiryempassigninfosrv package.
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

    private final static QName _InquiryEmpAssignInfoSrvResponse_QNAME = new QName("http://eip.zte.com/common/fi/SB_HR_HR_PageInquiryEmpAssignInfoSrv", "InquiryEmpAssignInfoSrvResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.td.eip.hr.hr.sb_hr_hr_pageinquiryempassigninfosrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InquiryEmpAssignInfoSrvOutputItem }
     * 
     */
    public InquiryEmpAssignInfoSrvOutputItem createInquiryEmpAssignInfoSrvOutputItem() {
        return new InquiryEmpAssignInfoSrvOutputItem();
    }

    /**
     * Create an instance of {@link InquiryEmpAssignInfoSrvResponse }
     * 
     */
    public InquiryEmpAssignInfoSrvResponse createInquiryEmpAssignInfoSrvResponse() {
        return new InquiryEmpAssignInfoSrvResponse();
    }

    /**
     * Create an instance of {@link InquiryEmpAssignInfoSrvRequest }
     * 
     */
    public InquiryEmpAssignInfoSrvRequest createInquiryEmpAssignInfoSrvRequest() {
        return new InquiryEmpAssignInfoSrvRequest();
    }

    /**
     * Create an instance of {@link InquiryEmpAssignInfoSrvOutputCollection }
     * 
     */
    public InquiryEmpAssignInfoSrvOutputCollection createInquiryEmpAssignInfoSrvOutputCollection() {
        return new InquiryEmpAssignInfoSrvOutputCollection();
    }

    /**
     * Create an instance of {@link MsgHeader }
     * 
     */
    public MsgHeader createMsgHeader() {
        return new MsgHeader();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InquiryEmpAssignInfoSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/common/fi/SB_HR_HR_PageInquiryEmpAssignInfoSrv", name = "InquiryEmpAssignInfoSrvResponse")
    public JAXBElement<InquiryEmpAssignInfoSrvResponse> createInquiryEmpAssignInfoSrvResponse(InquiryEmpAssignInfoSrvResponse value) {
        return new JAXBElement<InquiryEmpAssignInfoSrvResponse>(_InquiryEmpAssignInfoSrvResponse_QNAME, InquiryEmpAssignInfoSrvResponse.class, null, value);
    }

}
