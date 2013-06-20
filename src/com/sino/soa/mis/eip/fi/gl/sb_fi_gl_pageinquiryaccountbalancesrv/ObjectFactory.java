
package com.sino.soa.mis.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.mis.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv package.
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

    private final static QName _SBFIGLPageInquiryAccountBalanceSrvRequest_QNAME = new QName("http://mss.cmcc.com/SB_FI_GL_PageInquiryAccountBalanceSrv", "SB_FI_GL_PageInquiryAccountBalanceSrvRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.mis.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SBFIGLPageInquiryAccountBalanceSrvRequest }
     * 
     */
    public SBFIGLPageInquiryAccountBalanceSrvRequest createSBFIGLPageInquiryAccountBalanceSrvRequest() {
        return new SBFIGLPageInquiryAccountBalanceSrvRequest();
    }

    /**
     * Create an instance of {@link SBFIGLPageInquiryAccountBalanceSrvResponse }
     * 
     */
    public SBFIGLPageInquiryAccountBalanceSrvResponse createSBFIGLPageInquiryAccountBalanceSrvResponse() {
        return new SBFIGLPageInquiryAccountBalanceSrvResponse();
    }

    /**
     * Create an instance of {@link SBFIGLPageInquiryAccountBalanceSrvOutputCollection }
     * 
     */
    public SBFIGLPageInquiryAccountBalanceSrvOutputCollection createSBFIGLPageInquiryAccountBalanceSrvOutputCollection() {
        return new SBFIGLPageInquiryAccountBalanceSrvOutputCollection();
    }

    /**
     * Create an instance of {@link SBFIGLPageInquiryAccountBalanceSrvOutputItem }
     * 
     */
    public SBFIGLPageInquiryAccountBalanceSrvOutputItem createSBFIGLPageInquiryAccountBalanceSrvOutputItem() {
        return new SBFIGLPageInquiryAccountBalanceSrvOutputItem();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBFIGLPageInquiryAccountBalanceSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mss.cmcc.com/SB_FI_GL_PageInquiryAccountBalanceSrv", name = "SB_FI_GL_PageInquiryAccountBalanceSrvRequest")
    public JAXBElement<SBFIGLPageInquiryAccountBalanceSrvRequest> createSBFIGLPageInquiryAccountBalanceSrvRequest(SBFIGLPageInquiryAccountBalanceSrvRequest value) {
        return new JAXBElement<SBFIGLPageInquiryAccountBalanceSrvRequest>(_SBFIGLPageInquiryAccountBalanceSrvRequest_QNAME, SBFIGLPageInquiryAccountBalanceSrvRequest.class, null, value);
    }

}
