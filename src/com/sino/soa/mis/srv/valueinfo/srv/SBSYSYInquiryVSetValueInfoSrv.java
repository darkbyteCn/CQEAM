package com.sino.soa.mis.srv.valueinfo.srv;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;
import com.sino.config.SinoConfig;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.mis.eip.sy.sy.sb_sy_sy_pageinquiryvsetvalueinfosrv.InquiryVSetValueInfoSrvOutputItem;
import com.sino.soa.mis.eip.sy.sy.sb_sy_sy_pageinquiryvsetvalueinfosrv.MsgHeader;
import com.sino.soa.mis.eip.sy.sy.sb_sy_sy_pageinquiryvsetvalueinfosrv.PageInquiryVSetValueInfoSrvRequest;
import com.sino.soa.mis.eip.sy.sy.sb_sy_sy_pageinquiryvsetvalueinfosrv.PageInquiryVSetValueInfoSrvResponse;
import com.sino.soa.mis.eip.sy.sy.sb_sy_sy_pageinquiryvsetvalueinfosrv.SBSYSYPageInquiryVSetValueInfoSrv;
import com.sino.soa.mis.eip.sy.sy.sb_sy_sy_pageinquiryvsetvalueinfosrv.SBSYSYPageInquiryVSetValueInfoSrv_Service;
import com.sino.soa.mis.srv.valueinfo.dto.SBSYSYInquiryVSetValueInfoDTO;
import com.sino.soa.util.XMLGregorianCalendarUtil;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-14
 * Time: 11:25:22
 * To change this template use File | Settings | File Templates.
 */
public final class SBSYSYInquiryVSetValueInfoSrv {

    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();
    private String flexValueName = "";
    private String endLastUpdateDate = "";
    private String startLastUpdateDate = "";

    private int pageSize = -1;
    private int totalRecord = -1;
    private int totalPage = -1;

    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/common/sy/SB_SY_SY_PageInquiryVSetValueInfoSrv", "SB_SY_SY_PageInquiryVSetValueInfoSrv");

    public SBSYSYInquiryVSetValueInfoSrv() {
    }

    public void execute() throws CalendarException, DTOException, DatatypeConfigurationException {

        URL wsdlURL = SBSYSYPageInquiryVSetValueInfoSrv_Service.WSDL_LOCATION;
        SBSYSYPageInquiryVSetValueInfoSrv_Service ss = new SBSYSYPageInquiryVSetValueInfoSrv_Service(wsdlURL, SERVICE_NAME);
        SBSYSYPageInquiryVSetValueInfoSrv port = ss.getSBSYSYPageInquiryVSetValueInfoSrvPort();

        System.out.println("PageInquiryVSetValueInfoSrv Invoking process...");
        MsgHeader header = new MsgHeader();
        header.setSOURCESYSTEMID("EAM");
        header.setSOURCESYSTEMNAME("EAM");
        header.setUSERID("IBM");
        header.setUSERNAME("IBM");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate=formatter.format(cal.getTime());
        header.setSUBMITDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(nowDate));
        header.setPAGESIZE(new BigDecimal(1000));
        header.setTOTALRECORD(new BigDecimal(-1));
//        header.setPROVINCECODE( SinoConfig.getProvinceCode()); 
        header.setPROVINCECODE(SinoConfig.getProvinceCode() );
        header.setENVIRONMENTNAME("");
        PageInquiryVSetValueInfoSrvRequest _process_payload = null;
        _process_payload = new PageInquiryVSetValueInfoSrvRequest();
        _process_payload.setMsgHeader(header);
        _process_payload.setFLEXVALUESETNAME(flexValueName);
        _process_payload.setSTARTLASTUPDATEDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(startLastUpdateDate));
        _process_payload.setENDLASTUPDATEDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(endLastUpdateDate));

        Client client = ClientProxy.getClient(port);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();

        httpClientPolicy.setConnectionTimeout(5400000);//连接时间
        httpClientPolicy.setReceiveTimeout(5400000);//接收时间
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);

        PageInquiryVSetValueInfoSrvResponse _process__return = port.process(_process_payload);
        returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getErrorFlag()));
        returnMessage.setErrorMessage(_process__return.getErrorMessage());
        System.out.println("_process__return.getTOTALRECORD() = " + _process__return.getTOTALRECORD() + "=" + _process__return.getPAGESIZE() + "||" + _process__return.getTOTALPAGE());
        if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
            BigDecimal totalRecord = _process__return.getTOTALRECORD();
            BigDecimal pageSize = new BigDecimal(1000);
            int totalPage = _process__return.getTOTALPAGE().intValue();
            for (int i = 1; i < totalPage + 1; i++) {
                header = new MsgHeader();
                header.setPAGESIZE(pageSize);
                header.setTOTALRECORD(totalRecord);
                header.setCURRENTPAGE(new BigDecimal(i));
                _process_payload = new PageInquiryVSetValueInfoSrvRequest();
                _process_payload.setFLEXVALUESETNAME(flexValueName);
                _process_payload.setSTARTLASTUPDATEDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(startLastUpdateDate));
                _process_payload.setMsgHeader(header);
                _process__return = port.process(_process_payload);

                List<InquiryVSetValueInfoSrvOutputItem> items = _process__return.getInquiryVSetValueInfoSrvOutputCollection().getInquiryVSetValueInfoSrvOutputItem();
                for (int k = 0; k < items.size(); k++) {
                    SBSYSYInquiryVSetValueInfoDTO valueDTO = new SBSYSYInquiryVSetValueInfoDTO();
                    InquiryVSetValueInfoSrvOutputItem item = items.get(k);
                    valueDTO.setFlexValueId(StrUtil.strToInt(item.getFLEXVALUEID().toString()));
                    valueDTO.setFlexValueSetId(StrUtil.strToInt(item.getFLEXVALUESETID().toString()));
                    valueDTO.setFlexValue(item.getFLEXVALUE());
                    valueDTO.setFlexValueMeaning(item.getFLEXVALUEMEARNING());
                    valueDTO.setDescription(item.getDESCRIPTION());
                    valueDTO.setParentFlexValueHigh(item.getPARENTFLEXVALUE());
                    valueDTO.setParentFlexValueLow(item.getPARENTFLEXVALUE());
                    valueDTO.setEnabledFlag(item.getENABLEDFLAG());
                    valueDTO.setSummaryFlag(item.getSUMMARYFLAG());
                    valueDTO.setStartDateActive(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getSTARTDATEACTIVE()));
                    if(item.getENABLEDFLAG().equals("Y")&& XMLGregorianCalendarUtil.getSimpleCalendarString(item.getENDDATEACTIVE()).equals("") ){
                    	valueDTO.setEndDateActive("2020-01-01");
                    }else{
                    	valueDTO.setEndDateActive(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getENDDATEACTIVE()));
                    }
                    valueDTO.setLastUpdateDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getLASTUPDATEDATE()));
                    System.out.println("FLEXVALUEID="+item.getFLEXVALUEID().toString()+"--"+"FLEXVALUESETID="+item.getFLEXVALUESETID().toString()+"--"+"FLEXVALUE="+item.getFLEXVALUE()+"--PPARENTFLEXVALUE="+item.getPARENTFLEXVALUE()+"--FELXVALUESETNAME="+item.getFLEXVALUESETNAME()+"--VALIDATETIONTYPE="+item.getVALIDATIONTYPE()+" "+item.getENABLEDFLAG()+" "+XMLGregorianCalendarUtil.getSimpleCalendarString(item.getSTARTDATEACTIVE())+" "+item.getDESCRIPTION());
                    ds.addDTO(valueDTO);
                }
            }
        }
    }

    /**
     * @return the ds
     */
    public DTOSet getDs() {
        return ds;
    }

    /**
     * @return the returnMessage
     */
    public SrvReturnMessage getReturnMessage() {
        return returnMessage;
    }

    public void setEndLastUpdateDate(String endLastUpdateDate) {
        this.endLastUpdateDate = endLastUpdateDate;
    }

    public void setStartLastUpdateDate(String startLastUpdateDate) {
        this.startLastUpdateDate = startLastUpdateDate;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * @param flexValueName the flexValueName to set
     */
    public void setFlexValueName(String flexValueName) {
        this.flexValueName = flexValueName;
    }

    public static void main(String args[]) throws Exception {
        SBSYSYInquiryVSetValueInfoSrv srv = new SBSYSYInquiryVSetValueInfoSrv();
        srv.setFlexValueName("CMCC_FA_LOC_2");
        srv.setStartLastUpdateDate("2009-07-01");
		srv.setEndLastUpdateDate("2009-07-30");
        srv.execute();
    }
}