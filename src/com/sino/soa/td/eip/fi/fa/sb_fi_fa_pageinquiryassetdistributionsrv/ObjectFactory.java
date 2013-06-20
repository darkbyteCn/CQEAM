
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetdistributionsrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetdistributionsrv package.
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

    private final static QName _PageInquiryAssetDistributionSrvRequest_QNAME = new QName("http://eip.zte.com/SB_FI_FA_PageInquiryAssetDistributionSrv", "PageInquiryAssetDistributionSrvRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetdistributionsrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PageInquiryAssetDistributionSrvOutputCollection }
     * 
     */
    public PageInquiryAssetDistributionSrvOutputCollection createPageInquiryAssetDistributionSrvOutputCollection() {
        return new PageInquiryAssetDistributionSrvOutputCollection();
    }

    /**
     * Create an instance of {@link PageInquiryAssetDistributionSrvRequest }
     * 
     */
    public PageInquiryAssetDistributionSrvRequest createPageInquiryAssetDistributionSrvRequest() {
        return new PageInquiryAssetDistributionSrvRequest();
    }

    /**
     * Create an instance of {@link PageInquiryAssetDistributionSrvOutputItem }
     * 
     */
    public PageInquiryAssetDistributionSrvOutputItem createPageInquiryAssetDistributionSrvOutputItem() {
        return new PageInquiryAssetDistributionSrvOutputItem();
    }

    /**
     * Create an instance of {@link PageInquiryAssetDistributionSrvResponse }
     * 
     */
    public PageInquiryAssetDistributionSrvResponse createPageInquiryAssetDistributionSrvResponse() {
        return new PageInquiryAssetDistributionSrvResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PageInquiryAssetDistributionSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/SB_FI_FA_PageInquiryAssetDistributionSrv", name = "PageInquiryAssetDistributionSrvRequest")
    public JAXBElement<PageInquiryAssetDistributionSrvRequest> createPageInquiryAssetDistributionSrvRequest(PageInquiryAssetDistributionSrvRequest value) {
        return new JAXBElement<PageInquiryAssetDistributionSrvRequest>(_PageInquiryAssetDistributionSrvRequest_QNAME, PageInquiryAssetDistributionSrvRequest.class, null, value);
    }

}
