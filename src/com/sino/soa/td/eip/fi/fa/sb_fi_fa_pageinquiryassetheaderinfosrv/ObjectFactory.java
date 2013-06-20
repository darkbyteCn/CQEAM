
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetheaderinfosrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetheaderinfosrv package.
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

    private final static QName _PageInquiryAssetHeaderInfoSrvRequest_QNAME = new QName("http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetHeaderInfoSrv", "PageInquiryAssetHeaderInfoSrvRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetheaderinfosrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PageInquiryAssetHeaderInfoSrvOutputItem }
     * 
     */
    public PageInquiryAssetHeaderInfoSrvOutputItem createPageInquiryAssetHeaderInfoSrvOutputItem() {
        return new PageInquiryAssetHeaderInfoSrvOutputItem();
    }

    /**
     * Create an instance of {@link PageInquiryAssetHeaderInfoSrvOutputCollection }
     * 
     */
    public PageInquiryAssetHeaderInfoSrvOutputCollection createPageInquiryAssetHeaderInfoSrvOutputCollection() {
        return new PageInquiryAssetHeaderInfoSrvOutputCollection();
    }

    /**
     * Create an instance of {@link PageInquiryAssetHeaderInfoSrvRequest }
     * 
     */
    public PageInquiryAssetHeaderInfoSrvRequest createPageInquiryAssetHeaderInfoSrvRequest() {
        return new PageInquiryAssetHeaderInfoSrvRequest();
    }

    /**
     * Create an instance of {@link PageInquiryAssetHeaderInfoSrvResponse }
     * 
     */
    public PageInquiryAssetHeaderInfoSrvResponse createPageInquiryAssetHeaderInfoSrvResponse() {
        return new PageInquiryAssetHeaderInfoSrvResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PageInquiryAssetHeaderInfoSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetHeaderInfoSrv", name = "PageInquiryAssetHeaderInfoSrvRequest")
    public JAXBElement<PageInquiryAssetHeaderInfoSrvRequest> createPageInquiryAssetHeaderInfoSrvRequest(PageInquiryAssetHeaderInfoSrvRequest value) {
        return new JAXBElement<PageInquiryAssetHeaderInfoSrvRequest>(_PageInquiryAssetHeaderInfoSrvRequest_QNAME, PageInquiryAssetHeaderInfoSrvRequest.class, null, value);
    }

}
