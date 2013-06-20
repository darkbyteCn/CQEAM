
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetlocationsrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetlocationsrv package.
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

    private final static QName _PageInquiryAssetLocationSrvRequest_QNAME = new QName("http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetLocationSrv", "PageInquiryAssetLocationSrvRequest");
    private final static QName _PageInquiryAssetLocationSrvResponse_QNAME = new QName("http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetLocationSrv", "PageInquiryAssetLocationSrvResponse");
    private final static QName _MsgHeader_QNAME = new QName("http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetLocationSrv", "MsgHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetlocationsrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PageInquiryAssetLocationSrvOutputCollection }
     * 
     */
    public PageInquiryAssetLocationSrvOutputCollection createPageInquiryAssetLocationSrvOutputCollection() {
        return new PageInquiryAssetLocationSrvOutputCollection();
    }

    /**
     * Create an instance of {@link PageInquiryAssetLocationSrvRequest }
     * 
     */
    public PageInquiryAssetLocationSrvRequest createPageInquiryAssetLocationSrvRequest() {
        return new PageInquiryAssetLocationSrvRequest();
    }

    /**
     * Create an instance of {@link PageInquiryAssetLocationSrvResponse }
     * 
     */
    public PageInquiryAssetLocationSrvResponse createPageInquiryAssetLocationSrvResponse() {
        return new PageInquiryAssetLocationSrvResponse();
    }

    /**
     * Create an instance of {@link PageInquiryAssetLocationSrvOutputItem }
     * 
     */
    public PageInquiryAssetLocationSrvOutputItem createPageInquiryAssetLocationSrvOutputItem() {
        return new PageInquiryAssetLocationSrvOutputItem();
    }

    /**
     * Create an instance of {@link MsgHeader }
     * 
     */
    public MsgHeader createMsgHeader() {
        return new MsgHeader();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PageInquiryAssetLocationSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetLocationSrv", name = "PageInquiryAssetLocationSrvRequest")
    public JAXBElement<PageInquiryAssetLocationSrvRequest> createPageInquiryAssetLocationSrvRequest(PageInquiryAssetLocationSrvRequest value) {
        return new JAXBElement<PageInquiryAssetLocationSrvRequest>(_PageInquiryAssetLocationSrvRequest_QNAME, PageInquiryAssetLocationSrvRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PageInquiryAssetLocationSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetLocationSrv", name = "PageInquiryAssetLocationSrvResponse")
    public JAXBElement<PageInquiryAssetLocationSrvResponse> createPageInquiryAssetLocationSrvResponse(PageInquiryAssetLocationSrvResponse value) {
        return new JAXBElement<PageInquiryAssetLocationSrvResponse>(_PageInquiryAssetLocationSrvResponse_QNAME, PageInquiryAssetLocationSrvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MsgHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetLocationSrv", name = "MsgHeader")
    public JAXBElement<MsgHeader> createMsgHeader(MsgHeader value) {
        return new JAXBElement<MsgHeader>(_MsgHeader_QNAME, MsgHeader.class, null, value);
    }

}
