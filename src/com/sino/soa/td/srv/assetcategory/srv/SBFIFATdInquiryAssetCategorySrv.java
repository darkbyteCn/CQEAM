package com.sino.soa.td.srv.assetcategory.srv;

import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;
import com.sino.config.SinoConfig;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetcategorysrv.*;
import com.sino.soa.td.srv.assetcategory.dto.SBFIFATdSrvAssetCategoryDTO;
import com.sino.soa.util.XMLGregorianCalendarUtil;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-7
 * Time: 12:49:28
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIFATdInquiryAssetCategorySrv {
    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();

    private String bookTypeCode = "";
    private String segment1 = "";
    private String concatenatedSegments = "";
    private XMLGregorianCalendar lastUpdateDate = null;

    private int pageSize = -1;
    private int totalRecord = -1;
    private int totalPage = -1;

    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetCategorySrv", "SB_FI_FA_TDPageInquiryAssetCategorySrv");

    public SBFIFATdInquiryAssetCategorySrv() throws DatatypeConfigurationException {
        DatatypeFactory factory = DatatypeFactory.newInstance();
        lastUpdateDate = factory.newXMLGregorianCalendar();
    }


    /**
     * @param lastUpdateDate the lastUpdateDate to set
     */
    public void setLastUpdateDate(XMLGregorianCalendar lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }


    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    /**
     * @return the totalRecord
     */
    public int getTotalRecord() {
        return totalRecord;
    }


    /**
     * @param totalRecord the totalRecord to set
     */
    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }


    /**
     * @return the totalPage
     */
    public int getTotalPage() {
        return totalPage;
    }


    /**
     * @param totalPage the totalPage to set
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }


    /**
     * @return the bookTypeCode
     */
    public String getBookTypeCode() {
        return bookTypeCode;
    }


    /**
     * @return the segment1
     */
    public String getSegment1() {
        return segment1;
    }


    /**
     * @return the concatenatedSegments
     */
    public String getConcatenatedSegments() {
        return concatenatedSegments;
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


    public void execute() throws CalendarException, DTOException {
        URL wsdlURL = SBFIFATDPageInquiryAssetCategorySrv.WSDL_LOCATION;


        SBFIFATDPageInquiryAssetCategorySrv ss = new SBFIFATDPageInquiryAssetCategorySrv(wsdlURL, SERVICE_NAME);
        SBFIFAPageInquiryAssetCategorySrv port = ss.getSBFIFAPageInquiryAssetCategorySrvPort();

        {
            System.out.println("SB_FI_FA_PageInquiryAssetCategorySrv Invoking process...");
            com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetcategorysrv.PageInquiryAssetCategorySrvRequest _process_payload = null;
            MsgHeader msgHeader = new MsgHeader();
            msgHeader.setSOURCESYSTEMID("");
            msgHeader.setSOURCESYSTEMNAME("");
            msgHeader.setUSERID("");
            msgHeader.setUSERNAME("");
            msgHeader.setSUBMITDATE(null);
            msgHeader.setPAGESIZE(new BigDecimal(1000));
            msgHeader.setTOTALRECORD(new BigDecimal(-1));
            msgHeader.setPROVINCECODE(SinoConfig.getProvinceCode());
            msgHeader.setENVIRONMENTNAME("");
            _process_payload = new PageInquiryAssetCategorySrvRequest();
            _process_payload.setBOOKTYPECODE(bookTypeCode);
            _process_payload.setCONCATENATEDSEGMENTS(concatenatedSegments);
            _process_payload.setSEGMENT1(segment1);
            _process_payload.setLASTUPDATEDATE(lastUpdateDate);
            _process_payload.setMsgHeader(msgHeader);
            com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetcategorysrv.PageInquiryAssetCategorySrvResponse _process__return = port.process(_process_payload);

            System.out.println("process.result=" + _process__return.getErrorFlag() + "||" + _process__return.getErrorMessage());
            System.out.println("_process__return.getTOTALRECORD() = " + _process__return.getTOTALRECORD() + "=" + _process__return.getPAGESIZE() + "||" + _process__return.getTOTALPAGE());
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
                    _process_payload = new PageInquiryAssetCategorySrvRequest();
                    _process_payload.setBOOKTYPECODE(bookTypeCode);
                    _process_payload.setCONCATENATEDSEGMENTS(concatenatedSegments);
                    _process_payload.setSEGMENT1(segment1);
                    _process_payload.setLASTUPDATEDATE(lastUpdateDate);
                    _process_payload.setMsgHeader(msgHeader);
                    _process__return = port.process(_process_payload);
                    if (_process__return.getErrorFlag().equalsIgnoreCase("Y")) {
                        List<PageInquiryAssetCategorySrvOutputItem> itemList = _process__return.getPageInquiryAssetCategorySrvOutputCollection().getPageInquiryAssetCategorySrvOutputItem();
                        for (int k = 0; k < itemList.size(); k++) {
                            PageInquiryAssetCategorySrvOutputItem item = itemList.get(k);
                            SBFIFATdSrvAssetCategoryDTO categoryDTO = new SBFIFATdSrvAssetCategoryDTO();
                            categoryDTO.setCategoryId(item.getCATEGORYID().toString());
                            categoryDTO.setDescription(item.getDESCRIPTION());
                            categoryDTO.setCategoryType(item.getCATEGORYTYPE());
                            categoryDTO.setSegment1(item.getSEGMENT1());
                            categoryDTO.setSegment2(item.getSEGMENT2());
                            categoryDTO.setSegment3(item.getSEGMENT3());
                            categoryDTO.setAssetCostAccountCcid(item.getASSETCLEARINGACCOUNTCCID().toString());
                            categoryDTO.setReserveAccountCcid(item.getRESERVEACCOUNTCCID().toString());
                            categoryDTO.setAssetClearingAccountCcid(item.getASSETCLEARINGACCOUNTCCID().toString());
                            categoryDTO.setLifeInMonths(item.getLIFEINMONTHS().toString());
                            categoryDTO.setPercentSalvageValue(String.valueOf(item.getPERCENTSALVAGEVALUE()));
                            categoryDTO.setEnabledFlag(item.getENABLEDFLAG());
                            categoryDTO.setAttribute1(item.getATTRIBUTE1());
                            categoryDTO.setInventorial(item.getINVENTORIAL());
                            categoryDTO.setCapitalizeFlag(item.getCAPITALIZEFLAG());
                            categoryDTO.setBookTypeCode(item.getBOOKTYPECODE());
                            categoryDTO.setLastUpdateDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getLASTUPDATEDATE()));
                            if (!map.containsValue(item.getSEGMENT1()+"."+item.getSEGMENT2()+"."+item.getSEGMENT3())) {
                                map.put("category"+k, item.getSEGMENT1()+"."+item.getSEGMENT2()+"."+item.getSEGMENT3());
                                ds.addDTO(categoryDTO);
                            }
                        }
                    }

                }
            }

        }
    }

    public void cal() throws Exception {
        URL wsdlURL = SBFIFATDPageInquiryAssetCategorySrv.WSDL_LOCATION;


        SBFIFATDPageInquiryAssetCategorySrv ss = new SBFIFATDPageInquiryAssetCategorySrv(wsdlURL, SERVICE_NAME);
        SBFIFAPageInquiryAssetCategorySrv port = ss.getSBFIFAPageInquiryAssetCategorySrvPort();

        {
            System.out.println("Invoking process...");
            com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetcategorysrv.PageInquiryAssetCategorySrvRequest _process_payload = null;
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
            _process_payload = new PageInquiryAssetCategorySrvRequest();
            _process_payload.setBOOKTYPECODE(bookTypeCode);
            _process_payload.setCONCATENATEDSEGMENTS(concatenatedSegments);
            _process_payload.setSEGMENT1(segment1);
            _process_payload.setLASTUPDATEDATE(lastUpdateDate);
            _process_payload.setMsgHeader(msgHeader);
            com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetcategorysrv.PageInquiryAssetCategorySrvResponse _process__return = port.process(_process_payload);

            System.out.println("process.result=" + _process__return.getErrorFlag() + "||" + _process__return.getErrorMessage());
            System.out.println("_process__return.getTOTALRECORD() = " + _process__return.getTOTALRECORD() + "=" + _process__return.getPAGESIZE() + "||" + _process__return.getTOTALPAGE());
            if (_process__return.getErrorFlag().equalsIgnoreCase("Y")) {
                BigDecimal totalRecord = _process__return.getTOTALRECORD();
                BigDecimal pageSize = new BigDecimal(1000);
                int totalPage = _process__return.getTOTALPAGE().intValue();

                for (int i = 1; i < totalPage + 1; i++) {
                    msgHeader = new MsgHeader();
                    msgHeader.setPAGESIZE(pageSize);
                    msgHeader.setTOTALRECORD(totalRecord);
                    msgHeader.setCURRENTPAGE(new BigDecimal(i));
                    _process_payload = new PageInquiryAssetCategorySrvRequest();
                    _process_payload.setBOOKTYPECODE(bookTypeCode);
                    _process_payload.setCONCATENATEDSEGMENTS(concatenatedSegments);
                    _process_payload.setSEGMENT1(segment1);
                    _process_payload.setLASTUPDATEDATE(lastUpdateDate);
                    _process_payload.setMsgHeader(msgHeader);
                    _process__return = port.process(_process_payload);
                    if (_process__return.getErrorFlag().equalsIgnoreCase("Y")) {
                        List<PageInquiryAssetCategorySrvOutputItem> itemList = _process__return.getPageInquiryAssetCategorySrvOutputCollection().getPageInquiryAssetCategorySrvOutputItem();
                        for (int k = 0; k < itemList.size(); k++) {
                            if (k % 333 == 0) {
                                PageInquiryAssetCategorySrvOutputItem item = itemList.get(k);
                                System.out.print("-------第" + ((i - 1) * 1000 + k + 1) + "条数据");
                                System.out.print("||item.getDESCRIPTION() = " + item.getDESCRIPTION());
                                System.out.println("======================");
                            }
                        }
                    }

                }
            }

        }

    }

    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public void setSegment1(String segment1) {
        this.segment1 = segment1;
    }

    public void setConcatenatedSegments(String concatenatedSegments) {
        this.concatenatedSegments = concatenatedSegments;
    }

    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException, DatatypeConfigurationException {
        this.lastUpdateDate = XMLGregorianCalendarUtil.getXMLGregorianCalendar(lastUpdateDate);
    }

    /**
     * @return the returnMessage
     */
    public SrvReturnMessage getReturnMessage() {
        return returnMessage;
    }

    /**
     * 测试用
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SBFIFATdInquiryAssetCategorySrv categorySrv = new SBFIFATdInquiryAssetCategorySrv();
        categorySrv.cal();
    }

}