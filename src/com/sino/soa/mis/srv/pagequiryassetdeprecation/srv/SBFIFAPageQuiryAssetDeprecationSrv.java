package com.sino.soa.mis.srv.pagequiryassetdeprecation.srv;

import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;

import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;
import com.sino.config.SinoConfig;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_pageinquiryassetdeprecationsrv.*;
import com.sino.soa.mis.srv.pagequiryassetdeprecation.dto.SBFIFAPageQuiryAssetDeprecationDTO;
import com.sino.soa.util.XMLGregorianCalendarUtil;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-14
 * Time: 10:20:05
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIFAPageQuiryAssetDeprecationSrv {

    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/common/fi/SB_FI_FA_PageInquiryAssetDeprecationSrv", "SB_FI_FA_PageInquiryAssetDeprecationSrv");

    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();
    private int pageSize = -1;
    private int totalRecord = -1;
    private int totalPage = -1;
    private String bookTypeCode = "";
    private String locationDep  = "";
    private String assetNumber = "";
    private String tagNumber = "";
    private String startLastUpdateDate = "";
    private String endLastUpdateDate = "";

    public String getBookTypeCode() {
        return bookTypeCode;
    }

    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
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

    public void setDs(DTOSet ds) {
        this.ds = ds;
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

    public String getLocationDep() {
        return locationDep;
    }

    public void setLocationDep(String locationDep) {
        this.locationDep = locationDep;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public String getStartLastUpdateDate() {
        return startLastUpdateDate;
    }

    public void setStartLastUpdateDate(String startLastUpdateDate) {
        this.startLastUpdateDate = startLastUpdateDate;
    }

    public String getEndLastUpdateDate() {
        return endLastUpdateDate;
    }

    public void setEndLastUpdateDate(String endLastUpdateDate) {
        this.endLastUpdateDate = endLastUpdateDate;
    }

    public SBFIFAPageQuiryAssetDeprecationSrv() throws DatatypeConfigurationException {

    }


    public void execute() throws CalendarException, DTOException, DatatypeConfigurationException {
        URL wsdlURL = SBFIFAPageInquiryAssetDeprecationSrv_Service.WSDL_LOCATION;
        SBFIFAPageInquiryAssetDeprecationSrv_Service ss = new SBFIFAPageInquiryAssetDeprecationSrv_Service(wsdlURL, SERVICE_NAME);
        SBFIFAPageInquiryAssetDeprecationSrv port = ss.getSBFIFAPageInquiryAssetDeprecationSrvPort();
        Client client = ClientProxy.getClient(port);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(5400000);//连接时间
        httpClientPolicy.setReceiveTimeout(5400000);//接收时间
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);
        {
            System.out.println("SB_FI_FA_PageInquiryAssetDeprecationSrv Invoking process...");
            com.sino.soa.mis.eip.fi.fa.sb_fi_fa_pageinquiryassetdeprecationsrv.InquiryAssetDeprecationSrvRequest _process_payload = null;
            MsgHeader msgHeader = new MsgHeader();
            msgHeader.setSOURCESYSTEMID("");
            msgHeader.setSOURCESYSTEMNAME("");
            msgHeader.setUSERID("");
            msgHeader.setUSERNAME("");
            msgHeader.setSUBMITDATE(null);
            msgHeader.setPAGESIZE(new BigDecimal(1000));
            msgHeader.setCURRENTPAGE(new BigDecimal(1));
            msgHeader.setTOTALRECORD(new BigDecimal(-1));
            msgHeader.setPROVINCECODE(SinoConfig.getProvinceCode());
            msgHeader.setENVIRONMENTNAME("");
            _process_payload = new InquiryAssetDeprecationSrvRequest();
            _process_payload.setBOOKTYPECODE(bookTypeCode);
            _process_payload.setLOCATIONDEP(locationDep);
            _process_payload.setASSETNUMBER(assetNumber);
            _process_payload.setTAGNUMBER(tagNumber);
            _process_payload.setSTARTLASTUPDATEDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(startLastUpdateDate));
            _process_payload.setENDLASTUPDATEDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(endLastUpdateDate));
            _process_payload.setMsgHeader(msgHeader);

            com.sino.soa.mis.eip.fi.fa.sb_fi_fa_pageinquiryassetdeprecationsrv.InquiryAssetDeprecationSrvResponse _process__return = port.process(_process_payload);
            System.out.println("errorFlag=" + _process__return.getErrorFlag() + "||errorMessage=" + _process__return.getErrorMessage());
            System.out.println("totalRecord= " + _process__return.getTOTALRECORD() + "||pageSize=" + _process__return.getPAGESIZE() + "||totalPage=" + _process__return.getTOTALPAGE());
            returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getErrorFlag()));
            returnMessage.setErrorMessage(_process__return.getErrorMessage());

            if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                BigDecimal totalRecord = _process__return.getTOTALRECORD();
                BigDecimal pageSize = new BigDecimal(1000);
                int totalPage = _process__return.getTOTALPAGE().intValue();
                Map map = new HashMap();
                for (int i = 1; i < totalPage + 1; i++) {
                    msgHeader = new MsgHeader();
                    msgHeader.setPAGESIZE(pageSize);
                    msgHeader.setTOTALRECORD(totalRecord);
                    msgHeader.setCURRENTPAGE(new BigDecimal(i));
                    _process_payload = new InquiryAssetDeprecationSrvRequest();
                    _process_payload.setBOOKTYPECODE(bookTypeCode);
                    _process_payload.setLOCATIONDEP(locationDep);
                    _process_payload.setASSETNUMBER(assetNumber);
                    _process_payload.setTAGNUMBER(tagNumber);
                    _process_payload.setSTARTLASTUPDATEDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(startLastUpdateDate));
                    _process_payload.setENDLASTUPDATEDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(endLastUpdateDate));
                    _process_payload.setMsgHeader(msgHeader);
                    _process__return = port.process(_process_payload);
                    if (_process__return.getErrorFlag().equalsIgnoreCase("Y")) {
                        List<InquiryAssetDeprecationSrvOutputItem> itemList = _process__return.getInquiryAssetDeprecationSrvOutputCollection().getInquiryAssetDeprecationSrvOutputItem();
                        for (int k = 0; k < itemList.size(); k++) {
                            InquiryAssetDeprecationSrvOutputItem item = itemList.get(k);
                            SBFIFAPageQuiryAssetDeprecationDTO categoryDTO = new SBFIFAPageQuiryAssetDeprecationDTO();
                            categoryDTO.setBookTypeCode(item.getBOOKTYPECODE());
                            categoryDTO.setAssetId(StrUtil.nullToString(item.getASSETID()));
                            categoryDTO.setTagNumber(item.getTAGNUMBER());
                            categoryDTO.setAssetNumber(item.getASSETNUMBER());
                            categoryDTO.setDescription(item.getDESCRIPTION());
                            categoryDTO.setSegment1(item.getSEGMENT1());
                            categoryDTO.setSegment2(item.getSEGMENT2());
                            String cost = item.getCOST().toString();
                            if (cost.equals("")) {
                                cost = "0";
                            }
                            categoryDTO.setCost(cost);
                            String netBookValue = StrUtil.nullToString(item.getNETBOOKVALUE());
                            if (netBookValue.equals("")) {
                                netBookValue = "0";
                            }
                            categoryDTO.setNetBookValue(netBookValue);
                            String ptdImpairment = StrUtil.nullToString(item.getPTDIMPAIRMENT());
                            if (ptdImpairment.equals("")) {
                                ptdImpairment = "0";
                            }
                            categoryDTO.setPtdImpairment(ptdImpairment);
                            String ytdImpairment = StrUtil.nullToString(item.getYTDIMPAIRMENT());
                            if (ytdImpairment.equals("")) {
                                ytdImpairment = "0";
                            }
                            categoryDTO.setYtdImpairment(ytdImpairment);
                            String impairmentReserve = StrUtil.nullToString(item.getIMPAIRMENTRESERVE());
                            if (impairmentReserve.equals("")) {
                                impairmentReserve = "0";
                            }
                            categoryDTO.setImpairmentReserve(impairmentReserve);
                            String ptdDeprn = StrUtil.nullToString(item.getPTDDEPRN());
                            if (ptdDeprn.equals("")) {
                                ptdDeprn = "0";
                            }
                            categoryDTO.setPtdDeprn(ptdDeprn);
                            String ytdDeprn = StrUtil.nullToString(item.getYTDDEPRN());
                            if (ytdDeprn.equals("")) {
                                ytdDeprn = "0";
                            }
                            categoryDTO.setYtdDeprn(ytdDeprn);
                            String deprnReserve = StrUtil.nullToString(item.getDEPRNRESERVE());
                            if (deprnReserve.equals("")) {
                                deprnReserve = "0";
                            }
                            categoryDTO.setDeprnReserve(deprnReserve);
                            categoryDTO.setPeriodName(item.getPERIODNAME());
                            String deprnLeftMonth = StrUtil.nullToString(item.getDEPRNLEFTMONTH());
                            if (deprnLeftMonth.equals("")) {
                                deprnLeftMonth = "0";
                            }
                            categoryDTO.setDeprnLeftMonth(deprnLeftMonth);
                            categoryDTO.setLastUpdateDate(StrUtil.nullToString(item.getLASTUPDATEDATE()));
                            String salvageValue = StrUtil.nullToString(item.getSALVAGEVALUE());
                            if (salvageValue.equals("")) {
                                salvageValue = "0";
                            }
                            categoryDTO.setSalvageValue(salvageValue);
                            String lifeYears = StrUtil.nullToString(item.getLIFEYEARS());
                            if (lifeYears.equals("")) {
                                lifeYears = "0";
                            }
                            categoryDTO.setLifeYears(lifeYears);
                            categoryDTO.setRetirementPendingFlag(item.getRETIREMENTPENDINGFLAG());
                            ds.addDTO(categoryDTO);
//                            System.out.println(categoryDTO.getBookTypeCode()+"--"+categoryDTO.getAssetId()+"--"+categoryDTO.getTagNumber()+"--"+categoryDTO.getDescription()
//                            +"--"+categoryDTO.getSegment1()+"--"+categoryDTO.getSegment2()+"--"+categoryDTO.getCost()+"--"+categoryDTO.getNetBookValue()+categoryDTO.getPtdImpairment()
//                            +"--"+categoryDTO.getYtdImpairment()+"--"+categoryDTO.getImpairmentReserve()+"--"+categoryDTO.getPtdDeprn()+"--"+categoryDTO.getYtdDeprn()
//                            +"--"+categoryDTO.getDeprnReserve()+"--"+categoryDTO.getPeriodName()+"--"+categoryDTO.getDeprnLeftMonth()+"--"+categoryDTO.getLastUpdateDate()
//                            +"--"+categoryDTO.getSalvageValue()+"--"+categoryDTO.getLifeYears()+"--"+categoryDTO.getRetirementPendingFlag());
                        }
                    }

                }
            }

        }
    }

    public static void main(String[] args) throws Exception {
        SBFIFAPageQuiryAssetDeprecationSrv categorySrv = new SBFIFAPageQuiryAssetDeprecationSrv();
        categorySrv.setBookTypeCode("SXMC_FA_4111");
        categorySrv.setLocationDep("");
        categorySrv.setAssetNumber("");
        categorySrv.setTagNumber("4111-10005751");
        categorySrv.setStartLastUpdateDate("");
        categorySrv.setEndLastUpdateDate("");
        categorySrv.execute();
    }

}