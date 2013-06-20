package com.sino.soa.td.srv.employee.srv;

import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.SBHRHRTDInquiryEmpBaseInfoSrv;
import com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.pcbpel.adapter.db.empbaseinfosrv.InquiryEmpBaseInfoSrvOutputItem;
import com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.pcbpel.adapter.db.empbaseinfosrv.InquiryEmpBaseInfoSrvRequest;
import com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.msgheader.MsgHeader;
import com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.pcbpel.adapter.db.empbaseinfosrv.InquiryEmpBaseInfoSrvResponse;
import com.sino.soa.td.srv.employee.dto.SBHRHRSrvTdEmployeeInfoDTO;
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
 * Date: 2011-9-8
 * Time: 13:08:34
 * To change this template use File | Settings | File Templates.
 */
public final class SBHRHRTdInquiryEmpBaseInfoSrv {

    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();
    private String startLastUpdateDate = "";
    private String endLastUpdateDate = "";
    private String employeeNumber = "";
    private static final QName SERVICE_NAME = new QName("http://xmlns.oracle.com/SB_HR_HR_InquiryEmpBaseInfoSrv", "SB_HR_HR_TDInquiryEmpBaseInfoSrv");

    public SBHRHRTdInquiryEmpBaseInfoSrv() {
    }

    public void excute() throws CalendarException, DTOException, DatatypeConfigurationException {
        URL wsdlURL = SBHRHRTDInquiryEmpBaseInfoSrv.WSDL_LOCATION;
        SBHRHRTDInquiryEmpBaseInfoSrv ss = new SBHRHRTDInquiryEmpBaseInfoSrv(wsdlURL, SERVICE_NAME);
        com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.SBHRHRInquiryEmpBaseInfoSrv port = ss.getSBHRHRInquiryEmpBaseInfoSrvPort();
        {
            System.out.println("TDInquiryEmpBaseInfoSrv Invoking process...");
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
            if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                List<InquiryEmpBaseInfoSrvOutputItem> outItems = _process__return.getInquiryEmpBaseInfoSrvOutputCollection().get(0).getInquiryEmpBaseInfoSrvOutputItem();
                System.out.println("====共读取到TD员工记录数：" + outItems.size());
                for (int i = 0; i < outItems.size(); i++) {
                    SBHRHRSrvTdEmployeeInfoDTO employeeDTO = new SBHRHRSrvTdEmployeeInfoDTO();
                    InquiryEmpBaseInfoSrvOutputItem item = outItems.get(i);
                   if(!item.getEMPLOYEENUMBER().equals("")&&item.getEMPLOYEENUMBER()!=null){
	                    employeeDTO.setPersonId(item.getPERSONID().toString());
	                    employeeDTO.setFullName(item.getFULLNAME());
	                    employeeDTO.setEffectiveStartDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getEFFECTIVESTARTDATE()));
	                    employeeDTO.setEffectiveEndDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getEFFECTIVEENDDATE()));
	                    employeeDTO.setEmployeeNumber(item.getEMPLOYEENUMBER());
	                    employeeDTO.setLastUpdateDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getLASTUPDATEDATE()));
	                    String currDate = SynUpdateDateUtils.format(new Date(), "yyyy-MM-dd");
	                    try {
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

    public String toString() {
        String s = null;
        for (int i = 0; i < ds.getSize(); i++) {
        	SBHRHRSrvTdEmployeeInfoDTO dto = (SBHRHRSrvTdEmployeeInfoDTO) ds.getDTO(i);
            try {
				s = s + dto.getPersonId() + " " + dto.getLastUpdateDate()+" \n";
			} catch (CalendarException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return s;
    }
    public static void main(String args[]) throws Exception {
//        for (int i=1; i< 13; i++) {
//            SBHRHRTdInquiryEmpBaseInfoSrv srv = new SBHRHRTdInquiryEmpBaseInfoSrv();
//            srv.setStartLastUpdateDate("2010-"+i+"-03");
//            srv.excute();
//    	    System.out.println(i+"月："+srv.toString());
//        }
    	SBHRHRTdInquiryEmpBaseInfoSrv srv = new SBHRHRTdInquiryEmpBaseInfoSrv();
    	
    	srv.setStartLastUpdateDate("2009-02-25");
    	srv.setEndLastUpdateDate("2009-02-26");
    	srv.excute();
    	System.out.println(""+srv.toString());
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
    
}