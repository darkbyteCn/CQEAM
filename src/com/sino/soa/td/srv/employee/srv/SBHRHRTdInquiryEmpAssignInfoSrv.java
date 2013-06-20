package com.sino.soa.td.srv.employee.srv;

import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.hr.hr.sb_hr_hr_pageinquiryempassigninfosrv.*;
import com.sino.soa.td.srv.employee.dto.SBHRHRSrvTdEmployeeInfoDTO;
import com.sino.soa.util.XMLGregorianCalendarUtil;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import java.net.URL;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-8
 * Time: 13:08:53
 * To change this template use File | Settings | File Templates.
 */
public final class SBHRHRTdInquiryEmpAssignInfoSrv {
    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();
    private String businessGroup = "";
    private String startLastUpdateDate = "";
    private String employeeNumber = "";
    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/common/fi/SB_HR_HR_PageInquiryEmpAssignInfoSrv", "SB_HR_HR_TDPageInquiryEmpAssignInfoSrv");

    public SBHRHRTdInquiryEmpAssignInfoSrv() {

    }

    public void execute() throws CalendarException, DTOException {

        URL wsdlURL = SBHRHRTDPageInquiryEmpAssignInfoSrv.WSDL_LOCATION;

        SBHRHRTDPageInquiryEmpAssignInfoSrv ss = new SBHRHRTDPageInquiryEmpAssignInfoSrv(wsdlURL, SERVICE_NAME);
        SBHRHRPageInquiryEmpAssignInfoSrv port = ss.getSBHRHRPageInquiryEmpAssignInfoSrvPort();

        {
            System.out.println("TDPageInquiryEmpAssignInfoSrv Invoking process...");
            InquiryEmpAssignInfoSrvRequest _process_payload = null;
            _process_payload = new InquiryEmpAssignInfoSrvRequest();
            MsgHeader header = new MsgHeader();
            _process_payload.setMsgHeader(header);
            _process_payload.setBUSINESSGROUP("Setup Business Group");
            try {
                _process_payload.setLASTUPDATEDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar((startLastUpdateDate)));
                _process_payload.setEMPLOYEENUMBER(employeeNumber);
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
            InquiryEmpAssignInfoSrvResponse _process__return = port.process(_process_payload);
            returnMessage.setErrorMessage(StrUtil.nullToString(_process__return.getErrorFlag()));
            returnMessage.setErrorFlag(_process__return.getErrorFlag());
            if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                List<InquiryEmpAssignInfoSrvOutputItem> items = _process__return.getInquiryEmpAssignInfoSrvOutputCollection().getInquiryEmpAssignInfoSrvOutputItem();
                System.out.println("============" + items.size());
                for (int i = 0; i < items.size(); i++) {
                    SBHRHRSrvTdEmployeeInfoDTO srvEmployeeInfoDTO = new SBHRHRSrvTdEmployeeInfoDTO();
                    InquiryEmpAssignInfoSrvOutputItem item = items.get(i);
                    if( !item.getORGANIZATIONID().equals("")&&item.getORGANIZATIONID()!=null&&item.getPRIMARYFLAG().equals("Y")   ){
	                    srvEmployeeInfoDTO.setPersonId(item.getPERSONID().toString());
	                    srvEmployeeInfoDTO.setEffectiveStartDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getEFFECTIVESTARTDATE()));
	                    srvEmployeeInfoDTO.setEffectiveEndDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getEFFECTIVEENDDATE()));
	                    srvEmployeeInfoDTO.setEmployeeNumber(item.getEMPLOYEENUMBER());
	                    srvEmployeeInfoDTO.setOrganizationID(Integer.parseInt(item.getORGANIZATIONID().toString()));
	                    srvEmployeeInfoDTO.setOrganization(item.getORGANIZATION());
	                    srvEmployeeInfoDTO.setLastUpdateDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getLASTUPDATEDATE()));
	                    srvEmployeeInfoDTO.setPrimaryFlag(item.getPRIMARYFLAG());
	                    ds.addDTO(srvEmployeeInfoDTO);
                    }
                }
            }
        }
    }

    public static void main(String args[]) throws Exception {
        SBHRHRTdInquiryEmpAssignInfoSrv srv = new SBHRHRTdInquiryEmpAssignInfoSrv();
        srv.setStartLastUpdateDate("2009-04-03");
        srv.setStartLastUpdateDate("2009-10-03");
        //srv.setEmployeeNumber("81000410");
        srv.execute();
    } 
    
    public DTOSet getDs() {
        return ds;
    }

    public void setDs(DTOSet ds) {
        this.ds = ds;
    }

    public SrvReturnMessage getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(SrvReturnMessage returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getBusinessGroup() {
        return businessGroup;
    }

    public void setBusinessGroup(String businessGroup) {
        this.businessGroup = businessGroup;
    }

    public String getStartLastUpdateDate() {
        return startLastUpdateDate;
    }

    public void setStartLastUpdateDate(String startLastUpdateDate) {
        this.startLastUpdateDate = startLastUpdateDate;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

}