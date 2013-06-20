package com.sino.soa.td.srv.inquiryassetlocation.srv;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import com.sino.soa.common.SrvReturnMessage;
//import com.sino.soa.mis.srv.inquiryassetlocation.dto.SrvAssetLocationDTO;
import com.sino.soa.td.srv.inquiryassetlocation.dto.TdSrvAssetLocationDTO;
import com.sino.soa.util.XMLGregorianCalendarUtil;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetlocationsrv.MsgHeader;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetlocationsrv.PageInquiryAssetLocationSrvOutputItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetlocationsrv.PageInquiryAssetLocationSrvRequest;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetlocationsrv.PageInquiryAssetLocationSrvResponse;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetlocationsrv.SBFIFAPageInquiryAssetLocationSrv;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetlocationsrv.SBFIFATDPageInquiryAssetLocationSrv;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;

public final class TDSrvAssetLocationSrv {
	/**
	 * DATE：Sep 15, 2011 9:17:44 PM
	 * AUTHOR：wangzhipeng
	 * FUNCTION：资产地点接口_TD(实现类)  
	 */
	private SrvReturnMessage returnMessage = new SrvReturnMessage();
	private DTOSet ds = new DTOSet();
	private String segment1 = "";        //所属部门
    private String segment2 = "";        //地点代码
    private String segment3 = "";        //使用状态
	private String lastUpDate = "";      //最后更新开始日期  
	private String endLastUpDate = "";   //最后更新结束日期

    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetLocationSrv", "SB_FI_FA_TDPageInquiryAssetLocationSrv");

    public TDSrvAssetLocationSrv() {
    	
    }
    public void execute() throws CalendarException, DatatypeConfigurationException, DTOException{
    	URL wsdlURL = SBFIFATDPageInquiryAssetLocationSrv.WSDL_LOCATION;
    	SBFIFATDPageInquiryAssetLocationSrv ss = new SBFIFATDPageInquiryAssetLocationSrv(wsdlURL, SERVICE_NAME);
    	SBFIFAPageInquiryAssetLocationSrv port = ss.getSBFIFAPageInquiryAssetLocationSrvPort();  
    	PageInquiryAssetLocationSrvRequest _process_payload = null;
    	_process_payload = new PageInquiryAssetLocationSrvRequest();
    	
    	MsgHeader msgHeader = new MsgHeader();
	        msgHeader.setSOURCESYSTEMID("EAM");
	        msgHeader.setSOURCESYSTEMNAME("EAM");
	        msgHeader.setUSERID("IBM");
	        msgHeader.setUSERNAME("IBM");
	        msgHeader.setPAGESIZE(new BigDecimal(1000));
	        msgHeader.setCURRENTPAGE(new BigDecimal(1));
	        msgHeader.setTOTALRECORD(new BigDecimal(-1));
        _process_payload.setSEGMENT1(this.segment1);
        _process_payload.setSEGMENT2(this.segment2);
        _process_payload.setSEGMENT3(this.segment3); 
        _process_payload.setSTARTLASTUPDATEDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(lastUpDate));  
        _process_payload.setENDLASTUPDATEDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(endLastUpDate));
        _process_payload.setMsgHeader(msgHeader);
         
    	PageInquiryAssetLocationSrvResponse _process__return = port.process(_process_payload);
    	System.out.println("process.result=" + "=" + _process__return.getErrorFlag() + "||" + _process__return.getErrorMessage());
    	returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getErrorFlag()));
    	returnMessage.setErrorMessage(_process__return.getErrorMessage());
    	System.out.println("总记录数="+_process__return.getTOTALRECORD()+" 总页数:"+_process__return.getTOTALPAGE()+" 每页记录数="+_process__return.getPAGESIZE());
        if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
            BigDecimal totalRecord = _process__return.getTOTALRECORD();
            BigDecimal pageSize = new BigDecimal(1000);
            int totalPage = _process__return.getTOTALPAGE().intValue();
            for (int i = 1; i < totalPage + 1; i++) {
                msgHeader = new MsgHeader();
	            msgHeader.setSOURCESYSTEMID("EAM");
	            msgHeader.setSOURCESYSTEMNAME("EAM");
	            msgHeader.setUSERID("IBM");
	            msgHeader.setUSERNAME("IBM");
                msgHeader.setPAGESIZE(pageSize);
                msgHeader.setTOTALRECORD(totalRecord);
                msgHeader.setCURRENTPAGE(new BigDecimal(i));
                _process_payload.setSEGMENT1(this.segment1);
                _process_payload.setSEGMENT2(this.segment2);
                _process_payload.setSEGMENT3(this.segment3);
                _process_payload.setSTARTLASTUPDATEDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(lastUpDate));
                _process_payload.setENDLASTUPDATEDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(endLastUpDate));
                _process_payload.setMsgHeader(msgHeader);
                _process__return = port.process(_process_payload);       
    	  if (_process__return.getErrorFlag().equalsIgnoreCase("Y")) {
    		  List<PageInquiryAssetLocationSrvOutputItem> items = _process__return.getPageInquiryAssetLocationSrvOutputCollection().getPageInquiryAssetLocationSrvOutputItem();
              for (int k = 0; k < items.size(); k++) {
                  PageInquiryAssetLocationSrvOutputItem item = items.get(k);
                  TdSrvAssetLocationDTO TdsrvAssetLocationDTO = new TdSrvAssetLocationDTO();
                  TdsrvAssetLocationDTO.setLocationId(StrUtil.nullToString(item.getLOCATIONID())) ;     //资产地点ID
                   TdsrvAssetLocationDTO.setLocationCombinationCode(item.getLOCATIONCOMBINATIONCODE());
                  String temp = item.getLOCATIONCOMBINATIONNAME();
                  String s = "";
                  if (temp != null && !"".equals(temp) && temp.indexOf("缺省") != -1) {
                      s = temp.substring(0, temp.indexOf("缺省"));
                      s = s + "000";
                      TdsrvAssetLocationDTO.setLocationCombinationName(s);
                  } else{
                	  TdsrvAssetLocationDTO.setLocationCombinationName(item.getLOCATIONCOMBINATIONNAME());
                  }
                  TdsrvAssetLocationDTO.setEnabledFlag(item.getENABLEDFLAG());                                      //是否启用
                  TdsrvAssetLocationDTO.setStartDateActive(StrUtil.nullToString(item.getSTARTDATEACTIVE())) ;            //启用日期自
                  TdsrvAssetLocationDTO.setEndDateActive(StrUtil.nullToString(item.getENDDATEACTIVE())) ;  ;           //启用日期至
                  TdsrvAssetLocationDTO.setLastUpdateDate(StrUtil.nullToString(item.getLASTUPDATEDATE())) ; ;           //最后更新时间   
                  ds.addDTO(TdsrvAssetLocationDTO);
              } 		    
            }
          }
        }
    }
    
 
    public String toString() {
        String s = null;
        for (int i = 0; i < ds.getSize(); i++) {
        	TdSrvAssetLocationDTO dto = (TdSrvAssetLocationDTO) ds.getDTO(i);
            
            s += "dto:" + dto.getSegment1() + " " + dto.getLocationCombinationCode() +" "+dto.getLocationCombinationName()+"\n";
       
        }
        return s;
    }  
	
	public static void main(String args[]) throws Exception { 
		TDSrvAssetLocationSrv srv = new TDSrvAssetLocationSrv();
		srv.setSegment1("106300");
		srv.setLastUpDate("2009-06-01");
		srv.execute();
		System.out.println(" "+srv.toString());
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

		public String getEndLastUpDate() {
			return endLastUpDate;
		}

		public void setEndLastUpDate(String endLastUpDate) {
			this.endLastUpDate = endLastUpDate;
		}

		public String getLastUpDate() {
			return lastUpDate;
		}

		public void setLastUpDate(String lastUpDate) {
			this.lastUpDate = lastUpDate;
		}

		public String getSegment1() {
			return segment1;
		}

		public void setSegment1(String segment1) {
			this.segment1 = segment1;
		}
		
	public String getSegment2() {
		return segment2;
	}

	public void setSegment2(String segment2) {
		this.segment2 = segment2;
	}

	public String getSegment3() {
		return segment3;
	}

	public void setSegment3(String segment3) {
		this.segment3 = segment3;
	}
	
}
