package com.sino.soa.td.srv.valueinfo.srv;

import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.sy.sy.sb_sy_sy_pageinquiryvsetvalueinfosrv.*;
import com.sino.soa.td.srv.valueinfo.dto.SBSYSYTdInquiryVSetValueInfoDTO;
import com.sino.soa.util.XMLGregorianCalendarUtil;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-14
 * Time: 14:28:55
 * To change this template use File | Settings | File Templates.
 */
public final class SBSYSYTdInquiryVSetValueInfoSrv {

    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();
    private String flexValueName = "";
    private String endLastUpdateDate = "";
    private String startLastUpdateDate = "";

    private int pageSize = -1;
    private int totalRecord = -1;
    private int totalPage = -1;

    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/common/sy/SB_SY_SY_PageInquiryVSetValueInfoSrv", "SB_SY_SY_TDPageInquiryVSetValueInfoSrv");

    public SBSYSYTdInquiryVSetValueInfoSrv() {
    }

    public void execute() throws CalendarException, DTOException, DatatypeConfigurationException {

        URL wsdlURL = SBSYSYTDPageInquiryVSetValueInfoSrv.WSDL_LOCATION;
        SBSYSYTDPageInquiryVSetValueInfoSrv ss = new SBSYSYTDPageInquiryVSetValueInfoSrv(wsdlURL, SERVICE_NAME);
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
        header.setPROVINCECODE("");
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
                    SBSYSYTdInquiryVSetValueInfoDTO valueDTO = new SBSYSYTdInquiryVSetValueInfoDTO();
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
                    System.out.println(item.getFLEXVALUE());
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

    /**
     * @param flexValueName the flexValueName to set
     */
    public void setFlexValueName(String flexValueName) {
        this.flexValueName = flexValueName;
    }

    public static void main(String args[]) throws Exception {
        SBSYSYTdInquiryVSetValueInfoSrv srv = new SBSYSYTdInquiryVSetValueInfoSrv();
        srv.setFlexValueName("SXMC_COA_AC");
        srv.setStartLastUpdateDate("2009-07-01");
//		srv.setEndLastUpdateDate("2003-09-20");
//		srv.setStartLastUpdateDate("2004-08-08");
//		srv.setEndLastUpdateDate("2008-08-08");
        srv.execute();
    }
}