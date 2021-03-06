
/*
 * 
 */

package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_inquiryassetbooksrv;

import com.sino.soa.common.SOAConstant;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.1.4
 * Mon Sep 05 19:24:26 CST 2011
 * Generated source version: 2.1.4
 * 
 */

@WebServiceClient(name = "SB_FI_FA_InquiryAssetBookSrv",
                  wsdlLocation = "http://"+ SOAConstant.SERVER_NAME+":"+SOAConstant.SERVER_PORT+"/orabpel/fi/SB_FI_FA_InquiryAssetBookSrv/1.0/SB_FI_FA_InquiryAssetBookSrv?wsdl",
                  targetNamespace = "http://eip.zte.com/common/fa/SB_FI_FA_InquiryAssetBookSrv")
public class SBFIFAInquiryAssetBookSrv_Service extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://eip.zte.com/common/fa/SB_FI_FA_InquiryAssetBookSrv", "SB_FI_FA_InquiryAssetBookSrv");
    public final static QName SBFIFAInquiryAssetBookSrvPort = new QName("http://eip.zte.com/common/fa/SB_FI_FA_InquiryAssetBookSrv", "SB_FI_FA_InquiryAssetBookSrvPort");
    static {
        URL url = null;
        try {
            url = new URL("http://"+ SOAConstant.SERVER_NAME+":"+ SOAConstant.SERVER_PORT+"/orabpel/fi/SB_FI_FA_InquiryAssetBookSrv/1.0/SB_FI_FA_InquiryAssetBookSrv?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from" + "http://"+ SOAConstant.SERVER_NAME+":"+SOAConstant.SERVER_PORT+"/orabpel/fi/SB_FI_FA_InquiryAssetBookSrv/1.0/SB_FI_FA_InquiryAssetBookSrv?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public SBFIFAInquiryAssetBookSrv_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SBFIFAInquiryAssetBookSrv_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SBFIFAInquiryAssetBookSrv_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns SBFIFAInquiryAssetBookSrv
     */
    @WebEndpoint(name = "SB_FI_FA_InquiryAssetBookSrvPort")
    public SBFIFAInquiryAssetBookSrv getSBFIFAInquiryAssetBookSrvPort() {
        return super.getPort(SBFIFAInquiryAssetBookSrvPort, SBFIFAInquiryAssetBookSrv.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SBFIFAInquiryAssetBookSrv
     */
    @WebEndpoint(name = "SB_FI_FA_InquiryAssetBookSrvPort")
    public SBFIFAInquiryAssetBookSrv getSBFIFAInquiryAssetBookSrvPort(WebServiceFeature... features) {
        return super.getPort(SBFIFAInquiryAssetBookSrvPort, SBFIFAInquiryAssetBookSrv.class, features);
    }

}
