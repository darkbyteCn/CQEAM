package com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetheaderinfosrv;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.1.4
 * Fri Oct 14 14:43:03 CST 2011
 * Generated source version: 2.1.4
 * 
 */
 
@WebService(targetNamespace = "http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetHeaderInfoSrv", name = "SB_FI_FA_PageInquiryAssetHeaderInfoSrv")
@XmlSeeAlso({com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetheaderinfosrv.msgheader.ObjectFactory.class,ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SBFIFAPageInquiryAssetHeaderInfoSrv {

    @WebResult(name = "PageInquiryAssetHeaderInfoSrvResponse", targetNamespace = "http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetHeaderInfoSrv", partName = "payload")
    @WebMethod(action = "process")
    public PageInquiryAssetHeaderInfoSrvResponse process(
        @WebParam(partName = "payload", name = "PageInquiryAssetHeaderInfoSrvRequest", targetNamespace = "http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetHeaderInfoSrv")
        PageInquiryAssetHeaderInfoSrvRequest payload
    );
}
