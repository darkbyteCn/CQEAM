
package com.sino.soa.mis.eip.sc.pa.sb_sc_pa_pageinquirytaskinfosrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.mis.eip.sc.pa.sb_sc_pa_pageinquirytaskinfosrv package.
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

    private final static QName _PageInquiryTaskInfoSrvRequest_QNAME = new QName("http://eip.zte.com/SC/SB_SC_PA_PageInquiryTaskInfoSrv", "PageInquiryTaskInfoSrvRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.mis.eip.sc.pa.sb_sc_pa_pageinquirytaskinfosrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PageInquiryTaskInfoSrvResponse }
     * 
     */
    public PageInquiryTaskInfoSrvResponse createPageInquiryTaskInfoSrvResponse() {
        return new PageInquiryTaskInfoSrvResponse();
    }

    /**
     * Create an instance of {@link PageInquiryTaskInfoSrvOutputItem }
     * 
     */
    public PageInquiryTaskInfoSrvOutputItem createPageInquiryTaskInfoSrvOutputItem() {
        return new PageInquiryTaskInfoSrvOutputItem();
    }

    /**
     * Create an instance of {@link PageInquiryTaskInfoSrvRequest }
     * 
     */
    public PageInquiryTaskInfoSrvRequest createPageInquiryTaskInfoSrvRequest() {
        return new PageInquiryTaskInfoSrvRequest();
    }

    /**
     * Create an instance of {@link PageInquiryTaskInfoSrvOutputColection }
     * 
     */
    public PageInquiryTaskInfoSrvOutputColection createPageInquiryTaskInfoSrvOutputColection() {
        return new PageInquiryTaskInfoSrvOutputColection();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PageInquiryTaskInfoSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SC/SB_SC_PA_PageInquiryTaskInfoSrv", name = "PageInquiryTaskInfoSrvRequest")
    public JAXBElement<PageInquiryTaskInfoSrvRequest> createPageInquiryTaskInfoSrvRequest(PageInquiryTaskInfoSrvRequest value) {
        return new JAXBElement<PageInquiryTaskInfoSrvRequest>(_PageInquiryTaskInfoSrvRequest_QNAME, PageInquiryTaskInfoSrvRequest.class, null, value);
    }

}
