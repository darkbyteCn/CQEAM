package com.sino.soa.td.srv.vendor.srv;

import java.net.URL;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.util.XMLGregorianCalendarUtil; 
import com.sino.soa.mis.srv.AssetPeriodStatus.dto.SrvAssetPeriodStatusDTO;
import com.sino.soa.td.srv.vendor.dto.TdSrvVendorInfoDTO;
import com.sino.soa.td.eip.sc.pa.sb_sc_po_inquiryvendorinfosrv.InquiryVendorInfoSrvOutputItem;
import com.sino.soa.td.eip.sc.pa.sb_sc_po_inquiryvendorinfosrv.InquiryVendorInfoSrvRequest;
import com.sino.soa.td.eip.sc.pa.sb_sc_po_inquiryvendorinfosrv.InquiryVendorInfoSrvResponse;
import com.sino.soa.td.eip.sc.pa.sb_sc_po_inquiryvendorinfosrv.msgheader.MsgHeader;
import com.sino.soa.td.eip.sc.pa.sb_sc_po_inquiryvendorinfosrv.SBSCPOInquiryVendorInfoSrv;
import com.sino.soa.td.eip.sc.pa.sb_sc_po_inquiryvendorinfosrv.SBSCPOTDInquiryVendorInfoSrv;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;

/**
 * date：2011-09-15
 * user：wangzhipeng
 * function：查询供应商信息_TD(SOA实现)
 * 
 */
public final class TDInquiryVendorInfoSrv {
	   private SrvReturnMessage returnMessage = new SrvReturnMessage();
	    private DTOSet ds = new DTOSet();
	    private String vendorNumber = "";
	    private String vendorName = "";
	    private String vendorTypeDisp = "";
	    private String vendorNameAlt = "";
	    private String vatFlag = "";
	    private String lastUpdateDate = "";
	    private static final QName SERVICE_NAME = new QName("http://xmlns.oracle.com/SB_SC_PO_InquiryVendorInfoSrv", "SB_SC_PO_TDInquiryVendorInfoSrv");

	    public TDInquiryVendorInfoSrv() {
	    }

	    public void execute() throws CalendarException, DTOException, DatatypeConfigurationException {
	        URL wsdlURL = SBSCPOTDInquiryVendorInfoSrv.WSDL_LOCATION;

	        SBSCPOTDInquiryVendorInfoSrv ss = new SBSCPOTDInquiryVendorInfoSrv(wsdlURL, SERVICE_NAME);
	        SBSCPOInquiryVendorInfoSrv port = ss.getSBSCPOInquiryVendorInfoSrvPort();

	        System.out.println("SB_SC_PO_TDInquiryVendorInfoSrv Invoking process...");
	        InquiryVendorInfoSrvRequest _process_payload = null;
	        _process_payload = new InquiryVendorInfoSrvRequest();
	        MsgHeader msgHeader = new MsgHeader();
	        _process_payload.setVENDORNUMBER(vendorNumber);
	        _process_payload.setVENDORNAME(vendorName);
	        _process_payload.setVENDORTYPEDISP(vendorTypeDisp);
	        _process_payload.setVALIDFLAG(vatFlag);
	        _process_payload.setSTARTLASTUPDATEDATE(getLastUpdateDate());
	        _process_payload.setMsgHeader(msgHeader);
	        InquiryVendorInfoSrvResponse _process__return = port.process(_process_payload);
	        returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getErrorFlag()));
	        returnMessage.setErrorMessage(_process__return.getErrorMessage());
	        if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
	            List<InquiryVendorInfoSrvOutputItem> items = _process__return.getInquiryVendorInfoSrvOutputCollection().getInquiryVendorInfoSrvOutputItem();
	            for (int i = 0; i < items.size(); i++) {
	                InquiryVendorInfoSrvOutputItem item = items.get(i);
	                TdSrvVendorInfoDTO srvVendorInfoDTO = new TdSrvVendorInfoDTO();
	                srvVendorInfoDTO.setVendorName(item.getVENDORNAME());
	                srvVendorInfoDTO.setVendorNameAlt(item.getVENDORNAMEALT());
	                srvVendorInfoDTO.setVendorNumber(item.getVENDORNUMBER());
	                srvVendorInfoDTO.setVatFlag(item.getVATFLAG());
	                srvVendorInfoDTO.setCreatedBy(item.getCREATEDBY().toString());
	                srvVendorInfoDTO.setVendorCreationDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getVENDORCREATIONDATE()));
	                srvVendorInfoDTO.setVendorTypeDisp(item.getVENDORTYPEDISP());
	                srvVendorInfoDTO.setLastUpdateDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getLASTUPDATEDATE()));
	                ds.addDTO(srvVendorInfoDTO);
	            }
	        }
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

	    public XMLGregorianCalendar getLastUpdateDate() throws CalendarException, DatatypeConfigurationException {
	        return XMLGregorianCalendarUtil.getXMLGregorianCalendar(this.lastUpdateDate);
	    }

	    public void setLastUpdateDate(String lastUpdateDate) {
	        this.lastUpdateDate = lastUpdateDate;
	    }

	    public String getVatFlag() {
	        return vatFlag;
	    }

	    public void setVatFlag(String vatFlag) {
	        this.vatFlag = vatFlag;
	    }

	    public String getVendorName() {
	        return vendorName;
	    }

	    public void setVendorName(String vendorName) {
	        this.vendorName = vendorName;
	    }

	    public String getVendorNameAlt() {
	        return vendorNameAlt;
	    }

	    public void setVendorNameAlt(String vendorNameAlt) {
	        this.vendorNameAlt = vendorNameAlt;
	    }

	    public String getVendorNumber() {
	        return vendorNumber;
	    }

	    public void setVendorNumber(String vendorNumber) {
	        this.vendorNumber = vendorNumber;
	    }

	    public String getVendorTypeDisp() {
	        return vendorTypeDisp;
	    }

	    public void setVendorTypeDisp(String vendorTypeDisp) {
	        this.vendorTypeDisp = vendorTypeDisp;
	    }
	    public String toString() {
	        String s = null;
	        for (int i = 0; i < ds.getSize(); i++) {
	        	TdSrvVendorInfoDTO dto = (TdSrvVendorInfoDTO) ds.getDTO(i);
	            
	            s += "dto:" + dto.getVendorId() + "  " + dto.getVendorName() + " " + dto.getVendorTypeDisp() +"\n";
	       
	        }
	        return s;
	    }  
	    
	    public static void main(String args[]) throws Exception {
	       TDInquiryVendorInfoSrv vendor= new TDInquiryVendorInfoSrv();
	    	//vendor.setVendorName("北京旭帆伟业科技发展有限公司");
	        vendor.setLastUpdateDate("2009-05-01 00:01:01");
	    	vendor.execute();
	    	System.out.println("SOA供应商信息_TD:");
	    	System.out.println(vendor.toString());
	    	
	    	
	    }

	
}
