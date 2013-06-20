package com.sino.soa.td.srv.orgstructure.srv;

import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryorgstructuresrv.InquiryOrgStructureSrvOutputItem;
import com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryorgstructuresrv.InquiryOrgStructureSrvRequest;
import com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryorgstructuresrv.InquiryOrgStructureSrvResponse;
import com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryorgstructuresrv.SBHRHRTDInquiryOrgStructureSrv;
import com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryorgstructuresrv.msgheader.MsgHeader;
import com.sino.soa.td.srv.orgstructure.dto.SBHRHRTdInquiryOrgStructureDTO;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-16
 * Time: 21:40:44
 * To change this template use File | Settings | File Templates.
 */
public final class SBHRHRTdInquiryOrgStructureSrv {

    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/hr/SB_HR_HR_InquiryOrgStructureSrv", "SB_HR_HR_TDInquiryOrgStructureSrv");

    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();

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

    public SBHRHRTdInquiryOrgStructureSrv() {

    }

    public void excute() throws CalendarException, DTOException {
        URL wsdlURL = SBHRHRTDInquiryOrgStructureSrv.WSDL_LOCATION;
        SBHRHRTDInquiryOrgStructureSrv ss = new SBHRHRTDInquiryOrgStructureSrv(wsdlURL, SERVICE_NAME);
        com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryorgstructuresrv.SBHRHRInquiryOrgStructureSrv port = ss.getSBHRHRInquiryOrgStructureSrvPort();
        System.out.println("SB_FI_FA_TDInquiryOrgStructureSrv Invoking process...");
        InquiryOrgStructureSrvRequest _process_payload = null;
        MsgHeader msgHeader = new MsgHeader();
        _process_payload = new InquiryOrgStructureSrvRequest();
        _process_payload.setMsgHeader(msgHeader);

        InquiryOrgStructureSrvResponse _process__return = port.process(_process_payload);
        returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getErrorFlag()));
        returnMessage.setErrorMessage(_process__return.getErrorMessage());
        if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
            List<InquiryOrgStructureSrvOutputItem> items = _process__return.getInquiryOrgStructureSrvOutputCollection().getInquiryOrgStructureSrvOutputItem();
            for (int i = 0; i < items.size(); i++) {
                InquiryOrgStructureSrvOutputItem item = items.get(i);
                SBHRHRTdInquiryOrgStructureDTO orgStructureDTO = new SBHRHRTdInquiryOrgStructureDTO();
                orgStructureDTO.setBusinessGroupId(StrUtil.nullToString(item.getBUSINESSGROUPID()));
                orgStructureDTO.setBusinessGroupName(item.getBUSINESSGROUPNAME());
                orgStructureDTO.setOrganizationStructureName(item.getORGANIZATIONSTRUCTURENAME());
                orgStructureDTO.setOrganizationStructureId(StrUtil.nullToString(item.getORGANIZATIONSTRUCTUREID()));
                orgStructureDTO.setOrgStructureVersionId(StrUtil.nullToString(item.getORGSTRUCTUREVERSIONID()));
                orgStructureDTO.setVersionNumber(StrUtil.nullToString(item.getVERSIONNUMBER()));
                orgStructureDTO.setOrganizationId(StrUtil.nullToString(item.getORGANIZATIONID()));
                orgStructureDTO.setOrganizationCode(item.getORGANIZATIONCODE());
                orgStructureDTO.setOrganizationName(item.getORGANIZATIONNAME());
                orgStructureDTO.setOrganizationType(item.getORGANIZATIONTYPE());
                orgStructureDTO.setOrganizationClass(item.getORGANIZATIONCLASS());
                orgStructureDTO.setStatus(StrUtil.nullToString(item.getSTATUS()));
                orgStructureDTO.setParentOrganizationId(StrUtil.nullToString(item.getPARENTORGANIZATIONID()));
                orgStructureDTO.setParentOrgCode(item.getPARENTORGCODE());
                orgStructureDTO.setParentOrgName(item.getPARENTORGNAME());
                orgStructureDTO.setStructureStatus(item.getSTRUCTURESTATUS());
                orgStructureDTO.setLastUpdateDate(StrUtil.nullToString(item.getLASTUPDATEDATE()));
                orgStructureDTO.setOrgShortName(item.getORGSHORTNAME());
                orgStructureDTO.setOrgDescription(item.getORGDESCRIPTION());
                orgStructureDTO.setOrgSupervisor(item.getORGSUPERVISOR());
                orgStructureDTO.setManager(item.getMANAGER());
                orgStructureDTO.setManagerEmployeeNumber(item.getMANAGEREMPLOYEENUMBER());
                orgStructureDTO.setViceManager(item.getVICEMANAGER());
                orgStructureDTO.setAdministrator(item.getADMINISTRATOR());
                orgStructureDTO.setListNo(item.getLISTNO());
//                if (orgStructureDTO.getOrganizationStructureName().equals("山西移动通信公司组织架构")) {
//                    System.out.println(orgStructureDTO.getBusinessGroupId()+"--"+orgStructureDTO.getBusinessGroupName()+"--"+orgStructureDTO.getOrganizationStructureName()+"--"+orgStructureDTO.getOrganizationStructureId()
//                    +"--"+orgStructureDTO.getOrgStructureVersionId()+"--"+orgStructureDTO.getVersionNumber()+"--"+orgStructureDTO.getOrganizationId()+"--"+orgStructureDTO.getOrganizationCode()+"--"+orgStructureDTO.getOrganizationName()
//                    +"--"+orgStructureDTO.getOrganizationType()+"--"+orgStructureDTO.getOrganizationClass()+"--"+orgStructureDTO.getStatus()+"--"+orgStructureDTO.getParentOrganizationId()
//                    +"--"+orgStructureDTO.getParentOrgCode()+"--"+orgStructureDTO.getParentOrgName()+"--"+orgStructureDTO.getStructureStatus()+"--"+orgStructureDTO.getLastUpdateDate()
//                    +"--"+orgStructureDTO.getOrgShortName()+"--"+orgStructureDTO.getOrgDescription()+"--"+orgStructureDTO.getOrgSupervisor()+"--"+orgStructureDTO.getManager()
//                    +"--"+orgStructureDTO.getManagerEmployeeNumber()+"--"+orgStructureDTO.getViceManager()+"--"+orgStructureDTO.getAdministrator()+"--"+orgStructureDTO.getListNo());
//                }
                ds.addDTO(orgStructureDTO);
            }
        }

    }

    public static void main(String args[]) {
        SBHRHRTdInquiryOrgStructureSrv srv = null;
        try {
            srv = new SBHRHRTdInquiryOrgStructureSrv();
            srv.excute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}