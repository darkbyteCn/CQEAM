package com.sino.soa.mis.srv.ouorganization.srv;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import com.sino.base.dto.DTOSet;
import com.sino.base.util.StrUtil;
import com.sino.soa.common.SOAUtil;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.mis.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.SBFIGLInquiryOuOrganizationSrv_Service;
import com.sino.soa.mis.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.inquiryouorganizationsrv.InquiryOuOrganizationSrvOutputItem;
import com.sino.soa.mis.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.inquiryouorganizationsrv.InquiryOuOrganizationSrvRequest;
import com.sino.soa.mis.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.inquiryouorganizationsrv.InquiryOuOrganizationSrvResponse;
import com.sino.soa.mis.srv.ouorganization.dto.SBFIGLOuOrganizationDTO;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-8
 * Time: 16:04:10
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIGLInquiryOuOrganizationSrv {

    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();
    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/common/gl/SB_FI_GL_InquiryOuOrganizationSrv", "SB_FI_GL_InquiryOuOrganizationSrv");

    public SBFIGLInquiryOuOrganizationSrv() {
    }


    public void excute() throws Exception {
        URL wsdlURL = SBFIGLInquiryOuOrganizationSrv_Service.WSDL_LOCATION;

        SBFIGLInquiryOuOrganizationSrv_Service ss = new SBFIGLInquiryOuOrganizationSrv_Service(wsdlURL, SERVICE_NAME);
        com.sino.soa.mis.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.SBFIGLInquiryOuOrganizationSrv port = ss.getSBFIGLInquiryOuOrganizationSrvPort();

        //增加权限验证
        Client client = ClientProxy.getClient(port);
        Endpoint cxfEndpoint = client.getEndpoint();

        WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(SOAUtil.getPropMap());
        ((InterceptorProvider) cxfEndpoint).getOutInterceptors().add(wssOut);


        {
            System.out.println("Invoking process...");
            InquiryOuOrganizationSrvRequest _process_payload = null;


            InquiryOuOrganizationSrvResponse _process__return = port.process(_process_payload);

            returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getErrorFlag()));
            returnMessage.setErrorMessage(_process__return.getErrorMessage());


            if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                List<InquiryOuOrganizationSrvOutputItem> items = _process__return.getInquiryOuOrganizationSrvOutputCollection().getInquiryOuOrganizationSrvOutputItem();
                for (int i = 0; i < items.size(); i++) {
                    SBFIGLOuOrganizationDTO organizationDTO = new SBFIGLOuOrganizationDTO();
                    InquiryOuOrganizationSrvOutputItem item = items.get(i);
                    organizationDTO.setOrgId(Integer.parseInt(item.getORGID().toString()));
                    organizationDTO.setOrgName(item.getORGNAME());
                    organizationDTO.setSetOfBooksId(item.getSETOFBOOKSNAME());
                    organizationDTO.setSetOfBooksName(item.getSETOFBOOKSNAME());
                    organizationDTO.setAttribute1(item.getATTRIBUTE1());
                    organizationDTO.setEnableFlag(item.getENABLEFLAG());
                    ds.addDTO(organizationDTO);
                    System.out.println("orgId= " + item.getORGID() + "--" + "OrgName=" + item.getORGNAME() + "--" + "attribute1=" + item.getATTRIBUTE1() + "--" + "enabled=" + item.getENABLEFLAG());
                }

            }
        }
    }

    public SrvReturnMessage getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(SrvReturnMessage returnMessage) {
        this.returnMessage = returnMessage;
    }

    public DTOSet getDs() {
        return ds;
    }

    public void setDs(DTOSet ds) {
        this.ds = ds;
    }

    public static void main(String[] args) throws Exception {
        SBFIGLInquiryOuOrganizationSrv srv = new SBFIGLInquiryOuOrganizationSrv();
        srv.excute();
    }
}