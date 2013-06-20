package com.sino.soa.mis.srv.employee.srv;

import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;
import com.sino.soa.common.SOAConstant;
import com.sino.soa.common.SOAVersion;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.mis.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.*;
import com.sino.soa.mis.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.msgheader.*;
import com.sino.soa.mis.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.pcbpel.adapter.db.empbaseinfosrv.*;
import com.sino.soa.mis.srv.employee.dto.SBHRHRSrvEmployeeInfoDTO;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.XMLGregorianCalendarUtil;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-7
 * Time: 16:38:25
 * To change this template use File | Settings | File Templates.
 */
public final class SBHRHRInquiryEmpBaseInfoSrv {

    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();
    private String startLastUpdateDate = "";
    private String endLastUpdateDate = "";
    private String employeeNumber = "";
    private static final QName SERVICE_NAME = new QName("http://xmlns.oracle.com/SB_HR_HR_InquiryEmpBaseInfoSrv", "SB_HR_HR_InquiryEmpBaseInfoSrv");

    public SBHRHRInquiryEmpBaseInfoSrv() {
    }

    public void excute() throws CalendarException, DTOException, DatatypeConfigurationException {
        URL wsdlURL = SBHRHRInquiryEmpBaseInfoSrv_Service.WSDL_LOCATION;

        SBHRHRInquiryEmpBaseInfoSrv_Service ss = new SBHRHRInquiryEmpBaseInfoSrv_Service(wsdlURL, SERVICE_NAME);
        com.sino.soa.mis.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.SBHRHRInquiryEmpBaseInfoSrv port = ss.getSBHRHRInquiryEmpBaseInfoSrvPort();

        {
            System.out.println("InquiryEmpBaseInfoSrv Invoking process...");
            InquiryEmpBaseInfoSrvRequest _process_payload = null;
            _process_payload = new InquiryEmpBaseInfoSrvRequest();
            MsgHeader msgHesder = new MsgHeader();
            msgHesder.setSOURCESYSTEMID("EAM");
            msgHesder.setSOURCESYSTEMNAME("EAM");
            msgHesder.setUSERID("IBM");
            msgHesder.setUSERNAME("IBM");
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String nowDate=formatter.format(cal.getTime());
            msgHesder.setSUBMITDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(nowDate));
            _process_payload.setMsgHeader(msgHesder);
            _process_payload.setBUSINESSGROUP("Setup Business Group");
            _process_payload.setSTARTLASTUPDATEDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(startLastUpdateDate));
            _process_payload.setENDLASTUPDATEDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(endLastUpdateDate));
            _process_payload.setEMPLOYEENUMBER(employeeNumber);
            InquiryEmpBaseInfoSrvResponse _process__return = port.process(_process_payload);

            returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getErrorFlag()));
            returnMessage.setErrorMessage(_process__return.getErrorMessage());
            System.out.println(_process__return.getErrorMessage());
            if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                List<InquiryEmpBaseInfoSrvOutputItem> outItems = _process__return.getInquiryEmpBaseInfoSrvOutputCollection().get(0).getInquiryEmpBaseInfoSrvOutputItem();
                System.out.println("====共读取到员工记录数：" + outItems.size());
                for (int i = 0; i < outItems.size(); i++) {
                    SBHRHRSrvEmployeeInfoDTO employeeDTO = new SBHRHRSrvEmployeeInfoDTO();
                    InquiryEmpBaseInfoSrvOutputItem item = outItems.get(i);
                    if(!item.getEMPLOYEENUMBER().equals("")&&item.getEMPLOYEENUMBER()!=null){
                    	 employeeDTO.setPersonId(item.getPERSONID().toString());//
                         employeeDTO.setFullName(item.getFULLNAME());  //
                         employeeDTO.setEffectiveStartDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getEFFECTIVESTARTDATE()));
                         employeeDTO.setEffectiveEndDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getEFFECTIVEENDDATE()));
                         employeeDTO.setEmployeeNumber(item.getEMPLOYEENUMBER()); //
                         employeeDTO.setLastUpdateDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getLASTUPDATEDATE())); //
                         String currDate = SynUpdateDateUtils.format(new Date(), "yyyy-MM-dd");
                         try {   //
                         	if(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getEFFECTIVEENDDATE()).equals("")||XMLGregorianCalendarUtil.getSimpleCalendarString(item.getEFFECTIVEENDDATE())==null ){
                         		  employeeDTO.setIsEndbled("Y");
                         	}else{
     	                        if (SynUpdateDateUtils.getBetweenDays(currDate, XMLGregorianCalendarUtil.getSimpleCalendarString(item.getEFFECTIVEENDDATE())) > 0) {
     	                            employeeDTO.setIsEndbled("Y");
     	                        } else {
     	                            employeeDTO.setIsEndbled("N");
     	                        }
                         	}
                         } catch (ParseException e) {
                             e.printStackTrace();
                         }
                         ds.addDTO(employeeDTO);
                      }
                   
                }
            }
        }

    }

    public DTOSet getDs() {
        return ds;
    }

    public SrvReturnMessage getReturnMessage() {
        return returnMessage;
    }

    public void setStartLastUpdateDate(String startLastUpdateDate) {
        this.startLastUpdateDate = startLastUpdateDate;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEndLastUpdateDate() {
        return endLastUpdateDate;
    }

    public void setEndLastUpdateDate(String endLastUpdateDate) {
        this.endLastUpdateDate = endLastUpdateDate;
    }

    public static void main(String args[]) throws Exception {
        SBHRHRInquiryEmpBaseInfoSrv srv = new SBHRHRInquiryEmpBaseInfoSrv();
        srv.setStartLastUpdateDate("2011-04-20");
        srv.setEndLastUpdateDate("2011-04-21");
//        srv.setEmployeeNumber("88000059");
        srv.excute();
    }
}