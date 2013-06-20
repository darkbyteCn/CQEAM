package com.sino.soa.td.srv.AssetPeriodStatus.srv;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.SBFIFAInquiryAssetPeriodStatusSrv;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.SBFIFATDInquiryAssetPeriodStatusSrv;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.inquiryassetperiodstatussrv.InquiryAssetPeriodStatusSrvOutputItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.inquiryassetperiodstatussrv.InquiryAssetPeriodStatusSrvRequest;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.inquiryassetperiodstatussrv.InquiryAssetPeriodStatusSrvResponse;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.msgheader.MsgHeader;
import com.sino.soa.td.srv.AssetPeriodStatus.dto.TdSrvAssetPeriodStatusDTO;
import com.sino.soa.util.XMLGregorianCalendarUtil;

/**
 * DATE：Sep 15, 2011 3:45:32 PM
 * AUTHOR：wangzhipeng
 * FUNCTION： 资产会计状态_TD(接口实现类)
 * 
 */
public final class TDInquiryAssetPeriodStatusSrv {

	private SrvReturnMessage returnMessage = new SrvReturnMessage();
	private DTOSet ds = new DTOSet();
	private String bookTypeCode = "";
	private String periodName = "";

    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/common/fa/SB_FI_FA_InquiryAssetPeriodStatusSrv", "SB_FI_FA_TDInquiryAssetPeriodStatusSrv");
    public TDInquiryAssetPeriodStatusSrv() {
    	
    }

    public void execute() throws CalendarException, DTOException{
    	URL wsdlURL = SBFIFATDInquiryAssetPeriodStatusSrv.WSDL_LOCATION;
    	
    	SBFIFATDInquiryAssetPeriodStatusSrv ss = new SBFIFATDInquiryAssetPeriodStatusSrv(wsdlURL, SERVICE_NAME);
    	SBFIFAInquiryAssetPeriodStatusSrv port = ss.getSBFIFAInquiryAssetPeriodStatusSrvPort();  
    	MsgHeader header = new MsgHeader();
    	header.setSOURCESYSTEMID("AMS");
    	header.setSOURCESYSTEMNAME("AMS");
    	header.setUSERID("ADMIN");
    	header.setUSERNAME("ADMIN");
    	InquiryAssetPeriodStatusSrvRequest _process_payload = null;
    	_process_payload = new InquiryAssetPeriodStatusSrvRequest();
    	
    	_process_payload.setMsgHeader(header);
    	_process_payload.setBOOKTYPECODE(bookTypeCode);
    	_process_payload.setPERIODNAME(periodName);
    	
    	InquiryAssetPeriodStatusSrvResponse _process__return = port.process(_process_payload);
    	returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getErrorFlag()));
    	returnMessage.setErrorMessage(_process__return.getErrorMessage());

        System.out.println("执行结果："+returnMessage.getErrorFlag());
        System.out.println("返回详细信息："+returnMessage.getErrorMessage());
    	if(returnMessage.getErrorFlag().equalsIgnoreCase("Y")){
    		List<InquiryAssetPeriodStatusSrvOutputItem> items = _process__return.getInquiryAssetPeriodStatusSrvOutputCollection().getInquiryAssetPeriodStatusSrvOutputItem();
    		for(int i =0;i<items.size();i++){
    			InquiryAssetPeriodStatusSrvOutputItem item = items.get(i);
    			TdSrvAssetPeriodStatusDTO dto = new TdSrvAssetPeriodStatusDTO();
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
    
    public void setDs(DTOSet ds) {
		this.ds = ds;
	}

	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
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
    public String toString() {
        String s = null;
        for (int i = 0; i < ds.getSize(); i++) {
        	TdSrvAssetPeriodStatusDTO dto = (TdSrvAssetPeriodStatusDTO) ds.getDTO(i);
            
            s += "dto:" + dto.getBookTypeCode() + "  " + dto.getPeriodName() + " " + dto.getPeriodStatus() +"\n";
       
        }
        return s;
    }  
	
	
	public static void main(String args[]) throws Exception {
		TDInquiryAssetPeriodStatusSrv srv = new TDInquiryAssetPeriodStatusSrv();
		srv.setBookTypeCode("SXMC_FA_8110");
		//SXMC_FA_8110  JUN-11 OPEN
		srv.execute();
		
		System.out.println(srv.toString());
    }

}
