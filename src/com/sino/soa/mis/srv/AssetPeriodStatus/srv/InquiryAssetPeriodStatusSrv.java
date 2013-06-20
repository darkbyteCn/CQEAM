package com.sino.soa.mis.srv.AssetPeriodStatus.srv;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.SBFIFAInquiryAssetPeriodStatusSrv;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.SBFIFAInquiryAssetPeriodStatusSrv_Service;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.inquiryassetperiodstatussrv.InquiryAssetPeriodStatusSrvOutputItem;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.inquiryassetperiodstatussrv.InquiryAssetPeriodStatusSrvRequest;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.inquiryassetperiodstatussrv.InquiryAssetPeriodStatusSrvResponse;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.msgheader.MsgHeader;
import com.sino.soa.mis.srv.AssetPeriodStatus.dto.SrvAssetPeriodStatusDTO;
import com.sino.soa.util.XMLGregorianCalendarUtil;

public class InquiryAssetPeriodStatusSrv {
	/**
	 * 资产会计状态接口
	 * DES: 资产会计状态(接口实现类)
	 * user:wangzhipeng
	 */

    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();
    private String bookTypeCode = "";
    private String periodName = "";

    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/common/fa/SB_FI_FA_InquiryAssetPeriodStatusSrv", "SB_FI_FA_InquiryAssetPeriodStatusSrv");
    
    public InquiryAssetPeriodStatusSrv() {
    }

    public void execute() throws CalendarException, DTOException {
        URL wsdlURL = SBFIFAInquiryAssetPeriodStatusSrv_Service.WSDL_LOCATION;
      
        SBFIFAInquiryAssetPeriodStatusSrv_Service ss = new SBFIFAInquiryAssetPeriodStatusSrv_Service(wsdlURL, SERVICE_NAME);
        SBFIFAInquiryAssetPeriodStatusSrv port = ss.getSBFIFAInquiryAssetPeriodStatusSrvPort();
        
     
        InquiryAssetPeriodStatusSrvRequest _process_payload = null;
        _process_payload = new InquiryAssetPeriodStatusSrvRequest();
        
        MsgHeader msgHeader = new MsgHeader();
        msgHeader.setSOURCESYSTEMID("AMS");
        msgHeader.setSOURCESYSTEMNAME("AMS");
        msgHeader.setUSERID("ADMIN");
        msgHeader.setUSERNAME("ADMIN");
      
        _process_payload.setBOOKTYPECODE(bookTypeCode);//资产账簿
        _process_payload.setPERIODNAME(periodName);    //期间名称
        _process_payload.setMsgHeader(msgHeader);
        
        InquiryAssetPeriodStatusSrvResponse _process__return = port.process(_process_payload);
        returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getErrorFlag()));
        returnMessage.setErrorMessage(_process__return.getErrorMessage());
        if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
            List<InquiryAssetPeriodStatusSrvOutputItem> items = _process__return.getInquiryAssetPeriodStatusSrvOutputCollection().getInquiryAssetPeriodStatusSrvOutputItem();
            for (int i = 0; i < items.size(); i++) {
                InquiryAssetPeriodStatusSrvOutputItem item = items.get(i);
                SrvAssetPeriodStatusDTO dto = new SrvAssetPeriodStatusDTO();
                dto.setBookTypeCode(item.getBOOKTYPECODE());
                dto.setPeriodName(XMLGregorianCalendarUtil.getPeriodName(item.getPERIODNAME()));
                dto.setMisPeriodName(item.getPERIODNAME());
                dto.setStartDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getSTARTDATE()));
                dto.setEndDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getENDDATE()));
                dto.setPeriodOpenDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getPERIODOPENDATE()));
                dto.setPeriodCloseDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getPERIODCLOSEDATE()));
                dto.setGlTransferFlag(item.getGLTRANSFERFLAG());
                dto.setPeriodStatus(item.getPERIODSTATUS());
                ds.addDTO(dto);
            }
        }
    }


    /**
     * @return the returnMessage
     */
    public SrvReturnMessage getReturnMessage() {
        return returnMessage;
    }

    /**
     * @param returnMessage the returnMessage to set
     */
    public void setReturnMessage(SrvReturnMessage returnMessage) {
        this.returnMessage = returnMessage;
    }

    /**
     * @return the ds
     */
    public DTOSet getDs() {
        return ds;
    }

    /**
     * @param ds the ds to set
     */
    public void setDs(DTOSet ds) {
        this.ds = ds;
    }

    /**
     * @return the bookTypeCode
     */
    public String getBookTypeCode() {
        return bookTypeCode;
    }

    /**
     * @param bookTypeCode the bookTypeCode to set
     */
    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    /**
     * @return the periodName
     */
    public String getPeriodName() {
        return periodName;
    }

    /**
     * @param periodName the periodName to set
     */
    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String toString() {
        String s = null;
        for (int i = 0; i < ds.getSize(); i++) {
        	SrvAssetPeriodStatusDTO dto = (SrvAssetPeriodStatusDTO) ds.getDTO(i);
            
            s += "dto:" + dto.getBookTypeCode() + "  " + dto.getPeriodName() + " " + dto.getPeriodStatus() +"\n";
       
        }
        return s;
    }  
    

    public static void main(String[] args) throws Exception {
        InquiryAssetPeriodStatusSrv srv = new InquiryAssetPeriodStatusSrv();
        srv.setBookTypeCode("SXMC_FA_4188");  //SXMC_FA_4188
        srv.execute();
        System.out.println(" " + srv.toString());
        

    }
    
    
}
