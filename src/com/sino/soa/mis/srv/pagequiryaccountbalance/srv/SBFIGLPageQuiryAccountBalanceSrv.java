package com.sino.soa.mis.srv.pagequiryaccountbalance.srv;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;

import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;
import com.sino.config.SinoConfig;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.mis.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv.SBFIGLPageInquiryAccountBalanceSrv;
import com.sino.soa.mis.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv.SBFIGLPageInquiryAccountBalanceSrvOutputItem;
import com.sino.soa.mis.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv.SBFIGLPageInquiryAccountBalanceSrvRequest;
import com.sino.soa.mis.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv.SBFIGLPageInquiryAccountBalanceSrv_Service;
import com.sino.soa.mis.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv.msgheader.MsgHeader;
import com.sino.soa.mis.srv.pagequiryaccountbalance.dto.SBFIGLPageQuiryAccountBalanceDTO;
import com.sino.soa.util.XMLGregorianCalendarUtil;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-15
 * Time: 16:39:39
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIGLPageQuiryAccountBalanceSrv {

    private static final QName SERVICE_NAME = new QName("http://mss.cmcc.com/SB_FI_GL_PageInquiryAccountBalanceSrv", "SB_FI_GL_PageInquiryAccountBalanceSrv");

    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();
    private int pageSize = -1;
    private int totalRecord = -1;
    private int totalPage = -1;
    private String setOfBooks = "";
    private String periodNameFrom = "";
    private String periodNameTo = "";
    private String actualFlag = "";

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

    public String getSetOfBooks() {
        return setOfBooks;
    }

    public void setSetOfBooks(String setOfBooks) {
        this.setOfBooks = setOfBooks;
    }

    public String getPeriodNameFrom() {
        return periodNameFrom;
    }

    public void setPeriodNameFrom(String periodNameFrom) {
        this.periodNameFrom = periodNameFrom;
    }

    public String getPeriodNameTo() {
        return periodNameTo;
    }

    public void setPeriodNameTo(String periodNameTo) {
        this.periodNameTo = periodNameTo;
    }

    public String getActualFlag() {
        return actualFlag;
    }

    public void setActualFlag(String actualFlag) {
        this.actualFlag = actualFlag;
    }

    public SBFIGLPageQuiryAccountBalanceSrv() throws DatatypeConfigurationException {

    }


    public void execute() throws CalendarException, DTOException, DatatypeConfigurationException {
        URL wsdlURL = SBFIGLPageInquiryAccountBalanceSrv_Service.WSDL_LOCATION;
        SBFIGLPageInquiryAccountBalanceSrv_Service ss = new SBFIGLPageInquiryAccountBalanceSrv_Service(wsdlURL, SERVICE_NAME);
        SBFIGLPageInquiryAccountBalanceSrv port = ss.getSBFIGLPageInquiryAccountBalanceSrvPort();
        Client client = ClientProxy.getClient(port);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(1000000000);//连接时间
        httpClientPolicy.setReceiveTimeout(1000000000);//接收时间
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);
        {
            System.out.println("SB_FI_GL_PageInquiryAccountBalanceSrv Invoking process...");
            com.sino.soa.mis.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv.SBFIGLPageInquiryAccountBalanceSrvRequest _process_payload = null;
            MsgHeader msgHeader = new MsgHeader();
            msgHeader.setSOURCESYSTEMID("EAM");
            msgHeader.setSOURCESYSTEMNAME("EAM");
            msgHeader.setUSERID("IBM");
            msgHeader.setUSERNAME("IBM");
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String nowDate=formatter.format(cal.getTime());
            msgHeader.setSUBMITDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(nowDate));
            msgHeader.setPAGESIZE(new BigDecimal(500));
            msgHeader.setCURRENTPAGE(new BigDecimal(1));
            msgHeader.setTOTALRECORD(new BigDecimal(-1));
            msgHeader.setPROVINCECODE(SinoConfig.getProvinceCode());
            msgHeader.setENVIRONMENTNAME("");
            _process_payload = new SBFIGLPageInquiryAccountBalanceSrvRequest();
            _process_payload.setSETOFBOOKS(setOfBooks);
            _process_payload.setPERIODNAMEFROM(periodNameFrom);
            _process_payload.setPERIODNAMETO(periodNameTo);
            _process_payload.setACTUALFLAG(actualFlag);
            _process_payload.setMsgHeader(msgHeader);

            com.sino.soa.mis.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv.SBFIGLPageInquiryAccountBalanceSrvResponse _process__return = port.process(_process_payload);
            System.out.println("errorFlag=" + _process__return.getErrorFlag() + "||errorMessage=" + _process__return.getErrorMessage());
            System.out.println("totalRecord= " + _process__return.getTOTALRECORD() + "||pageSize=" + _process__return.getPAGESIZE() + "||totalPage=" + _process__return.getTOTALPAGE());
            returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getErrorFlag()));
            returnMessage.setErrorMessage(_process__return.getErrorMessage());

            if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                BigDecimal totalRecord = _process__return.getTOTALRECORD();
                BigDecimal pageSize = new BigDecimal(500);
                int totalPage = _process__return.getTOTALPAGE().intValue();
                for (int i = 1; i < totalPage + 1; i++) {
                    msgHeader = new MsgHeader();
                    msgHeader.setSOURCESYSTEMID("EAM");
                    msgHeader.setSOURCESYSTEMNAME("EAM");
                    msgHeader.setUSERID("IBM");
                    msgHeader.setUSERNAME("IBM");
                    msgHeader.setSUBMITDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(nowDate));
                    msgHeader.setPROVINCECODE(SinoConfig.getProvinceCode());
                    msgHeader.setENVIRONMENTNAME("");
                    msgHeader.setPAGESIZE(pageSize);
                    msgHeader.setTOTALRECORD(totalRecord);
                    msgHeader.setCURRENTPAGE(new BigDecimal(i));
                    _process_payload = new SBFIGLPageInquiryAccountBalanceSrvRequest();
                    _process_payload.setSETOFBOOKS(setOfBooks);
                    _process_payload.setPERIODNAMEFROM(periodNameFrom);
                    _process_payload.setPERIODNAMETO(periodNameTo);
                    _process_payload.setACTUALFLAG(actualFlag);
                    _process_payload.setMsgHeader(msgHeader);
                    _process__return = port.process(_process_payload);
                    if (_process__return.getErrorFlag().equalsIgnoreCase("Y")) {
                        List<SBFIGLPageInquiryAccountBalanceSrvOutputItem> itemList = _process__return.getSBFIGLPageInquiryAccountBalanceSrvOutputCollection().getSBFIGLPageInquiryAccountBalanceSrvOutputItem();
                        for (int k = 0; k < itemList.size(); k++) {
                            SBFIGLPageInquiryAccountBalanceSrvOutputItem item = itemList.get(k);
                            SBFIGLPageQuiryAccountBalanceDTO categoryDTO = new SBFIGLPageQuiryAccountBalanceDTO();
                            categoryDTO.setSetOfBooks(item.getSETOFBOOKS());
                            categoryDTO.setPeriodName(item.getPERIODNAME());
                            categoryDTO.setCurrencyCode(item.getCURRENCYCODE());
                            categoryDTO.setActualFlag(item.getACTUALFLAG());
                            String codeCombinationId = StrUtil.nullToString(item.getCODECOMBINATIONID());
                            if (codeCombinationId.equals("")) {
                                codeCombinationId = "0";
                            }
                            categoryDTO.setCodeCombinationId(codeCombinationId);
                            categoryDTO.setSegment1(item.getSEGMENT1());
                            categoryDTO.setSegment1Des(item.getSEGMENT1DES());
                            categoryDTO.setSegment2(item.getSEGMENT2());
                            categoryDTO.setSegment2Des(item.getSEGMENT2DES());
                            categoryDTO.setSegment3(item.getSEGMENT3());
                            categoryDTO.setSegment3Des(item.getSEGMENT3DES());
                            categoryDTO.setSegment4(item.getSEGMENT4());
                            categoryDTO.setSegment4Des(item.getSEGMENT4DES());
                            categoryDTO.setSegment5(item.getSEGMENT5());
                            categoryDTO.setSegment5Des(item.getSEGMENT5DES());
                            categoryDTO.setSegment6(item.getSEGMENT6());
                            categoryDTO.setSegment6Des(item.getSEGMENT6DES());
                            categoryDTO.setSegment7(item.getSEGMENT7());
                            categoryDTO.setSegment7Des(item.getSEGMENT7DES());
                            String beginBalanceDr = StrUtil.nullToString(item.getBEGINBALANCEDR());
                            if (beginBalanceDr.equals("")) {
                                beginBalanceDr = "0";
                            }
                            categoryDTO.setBeginBalanceDr(beginBalanceDr);
                            String beginBalanceCr = StrUtil.nullToString(item.getBEGINBALANCECR());
                            if (beginBalanceCr.equals("")) {
                                beginBalanceCr = "0";
                            }
                            categoryDTO.setBeginBalanceCr(beginBalanceCr);
                            String beginBalance = StrUtil.nullToString(item.getBEGINBALANCE());
                            if (beginBalance.equals("")) {
                                beginBalance = "0";
                            }
                            categoryDTO.setBeginBalance(beginBalance);
                            String periodNetDr = StrUtil.nullToString(item.getPERIODNETDR());
                            if (periodNetDr.equals("")) {
                                periodNetDr = "0";
                            }
                            categoryDTO.setPeriodNetDr(periodNetDr);
                            String periodNetCr = StrUtil.nullToString(item.getPERIODNETCR());
                            if (periodNetCr.equals("")) {
                                periodNetCr = "0";
                            }
                            categoryDTO.setPeriodNetCr(periodNetCr);
                            String periodNet = StrUtil.nullToString(item.getPERIODNET());
                            if (periodNet.equals("")) {
                                periodNet = "0";
                            }
                            categoryDTO.setPeriodNet(periodNet);
                            String endBalanceDr = StrUtil.nullToString(item.getENDBALANCEDR());
                            if (endBalanceDr.equals("")) {
                                endBalanceDr = "0";
                            }
                            categoryDTO.setEndBalanceDr(endBalanceDr);
                            String endBalanceCr = StrUtil.nullToString(item.getENDBALANCECR());
                            if (endBalanceCr.equals("")) {
                                endBalanceCr = "0";
                            }
                            categoryDTO.setEndBalanceCr(endBalanceCr);
                            String endBalance = StrUtil.nullToString(item.getENDBALANCE());
                            if (endBalance.equals("")) {
                                endBalance = "0";
                            }
                            categoryDTO.setEndBalance(endBalance);
                            categoryDTO.setStructuredHierarchyNameCom(item.getSTRUCTUREDHIERARCHYNAMECOM());
                            categoryDTO.setStructuredHierarchyNameCos(item.getSTRUCTUREDHIERARCHYNAMECOS());
                            categoryDTO.setContractNum(item.getCONTRACTNUM());
                            categoryDTO.setContractLineNum(item.getCONTRACTLINENUM());
                            ds.addDTO(categoryDTO);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        SBFIGLPageQuiryAccountBalanceSrv categorySrv = new SBFIGLPageQuiryAccountBalanceSrv();
        categorySrv.setSetOfBooks("COB_SX_CMCC");
        categorySrv.setPeriodNameFrom("AUG-04");
        categorySrv.setPeriodNameTo("AUG-04");
        categorySrv.setActualFlag("A");
        categorySrv.execute();
    }

}