package com.sino.soa.td.srv.ouorganization.srv;

import com.sino.base.dto.DTOSet;
import com.sino.base.util.StrUtil;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.SBFIGLTDInquiryOuOrganizationSrv;
import com.sino.soa.td.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.inquiryouorganizationsrv.InquiryOuOrganizationSrvOutputItem;
import com.sino.soa.td.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.inquiryouorganizationsrv.InquiryOuOrganizationSrvRequest;
import com.sino.soa.td.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.inquiryouorganizationsrv.InquiryOuOrganizationSrvResponse;
import com.sino.soa.td.srv.ouorganization.dto.SBFIGLTdOuOrganizationDTO;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-8
 * Time: 16:46:22
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIGLInquiryTdOuOrganizationSrv {

    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();
    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/common/gl/SB_FI_GL_InquiryOuOrganizationSrv", "SB_FI_GL_TDInquiryOuOrganizationSrv");

    public SBFIGLInquiryTdOuOrganizationSrv() {
    }


    public void excute() throws Exception {
        URL wsdlURL = SBFIGLTDInquiryOuOrganizationSrv.WSDL_LOCATION;

        SBFIGLTDInquiryOuOrganizationSrv ss = new SBFIGLTDInquiryOuOrganizationSrv(wsdlURL, SERVICE_NAME);
        com.sino.soa.td.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.SBFIGLInquiryOuOrganizationSrv port = ss.getSBFIGLInquiryOuOrganizationSrvPort();

        {
            System.out.println("Invoking process...");
            InquiryOuOrganizationSrvRequest _process_payload = null;

            InquiryOuOrganizationSrvResponse _process__return = port.process(_process_payload);

            returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getErrorFlag()));
            returnMessage.setErrorMessage(_process__return.getErrorMessage());


            if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                List<InquiryOuOrganizationSrvOutputItem> items = _process__return.getInquiryOuOrganizationSrvOutputCollection().getInquiryOuOrganizationSrvOutputItem();
                for (int i = 0; i < items.size(); i++) {
                    SBFIGLTdOuOrganizationDTO organizationDTO = new SBFIGLTdOuOrganizationDTO();
                    InquiryOuOrganizationSrvOutputItem item = items.get(i);
                    organizationDTO.setOrgId(Integer.parseInt(item.getORGID().toString()));
                    organizationDTO.setOrgName(item.getORGNAME());
                    organizationDTO.setSetOfBooksId(item.getSETOFBOOKSNAME());
                    organizationDTO.setSetOfBooksName(item.getSETOFBOOKSNAME());
                    organizationDTO.setAttribute1(item.getATTRIBUTE1());
                    organizationDTO.setEnableFlag(item.getENABLEFLAG());
                    ds.addDTO(organizationDTO);
                    System.out.println("orgId= " + item.getORGID()+"--"+"OrgName="+item.getORGNAME()+"--"+"attribute1="+item.getATTRIBUTE1()+"--"+"enabled="+item.getENABLEFLAG());
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
        SBFIGLInquiryTdOuOrganizationSrv srv=new SBFIGLInquiryTdOuOrganizationSrv();
        srv.excute();
    }
}