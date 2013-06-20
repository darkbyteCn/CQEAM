
package com.sino.soa.mis.eip.sc.po.sb_sc_pa_pageinquiryprojectinfosrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.mis.eip.sc.po.sb_sc_pa_pageinquiryprojectinfosrv package.
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

    private final static QName _PageInquiryProjectInfoSrvRequest_QNAME = new QName("http://eip.zte.com/SC/SB_SC_PA_PageInquiryProjectInfoSrv", "PageInquiryProjectInfoSrvRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.mis.eip.sc.po.sb_sc_pa_pageinquiryprojectinfosrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PageInquiryProjectInfoSrvOutputItem }
     * 
     */
    public PageInquiryProjectInfoSrvOutputItem createPageInquiryProjectInfoSrvOutputItem() {
        return new PageInquiryProjectInfoSrvOutputItem();
    }

    /**
     * Create an instance of {@link PageInquiryProjectInfoSrvOutputColection }
     * 
     */
    public PageInquiryProjectInfoSrvOutputColection createPageInquiryProjectInfoSrvOutputColection() {
        return new PageInquiryProjectInfoSrvOutputColection();
    }

    /**
     * Create an instance of {@link PageInquiryProjectInfoSrvResponse }
     * 
     */
    public PageInquiryProjectInfoSrvResponse createPageInquiryProjectInfoSrvResponse() {
        return new PageInquiryProjectInfoSrvResponse();
    }

    /**
     * Create an instance of {@link PageInquiryProjectInfoSrvRequest }
     * 
     */
    public PageInquiryProjectInfoSrvRequest createPageInquiryProjectInfoSrvRequest() {
        return new PageInquiryProjectInfoSrvRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PageInquiryProjectInfoSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SC/SB_SC_PA_PageInquiryProjectInfoSrv", name = "PageInquiryProjectInfoSrvRequest")
    public JAXBElement<PageInquiryProjectInfoSrvRequest> createPageInquiryProjectInfoSrvRequest(PageInquiryProjectInfoSrvRequest value) {
        return new JAXBElement<PageInquiryProjectInfoSrvRequest>(_PageInquiryProjectInfoSrvRequest_QNAME, PageInquiryProjectInfoSrvRequest.class, null, value);
    }

}
