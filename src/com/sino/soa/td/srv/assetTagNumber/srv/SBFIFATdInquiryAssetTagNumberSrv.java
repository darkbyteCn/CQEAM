package com.sino.soa.td.srv.assetTagNumber.srv;

import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassettagnumbersrv.SBFIFAInquiryAssetTagNumberSrvOutputItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassettagnumbersrv.SBFIFAInquiryAssetTagNumberSrvRequest;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassettagnumbersrv.SBFIFAInquiryAssetTagNumberSrvResponse;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassettagnumbersrv.SBFIFATDInquiryAssetTagNumberSrv;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassettagnumbersrv.msgheader.MsgHeader;
import com.sino.soa.td.srv.assetTagNumber.dto.SBFIFATdInquiryAssetTagNumberDTO;
import com.sino.soa.util.XMLGregorianCalendarUtil;

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
 * Date: 2011-10-18
 * Time: 19:12:22
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIFATdInquiryAssetTagNumberSrv {

    private static final QName SERVICE_NAME = new QName("http://mss.cmcc.com/SB_FI_FA_InquiryAssetTagNumberSrv", "SB_FI_FA_TDInquiryAssetTagNumberSrv");

    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();
    private String bookTypeCode = "";
    private int account = 1;
    private String segment1 = "";

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

    public String getBookTypeCode() {
        return bookTypeCode;
    }

    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getSegment1() {
        return segment1;
    }

    public void setSegment1(String segment1) {
        this.segment1 = segment1;
    }

    public SBFIFATdInquiryAssetTagNumberSrv() {

    }

    public void excute() throws CalendarException, DTOException, DatatypeConfigurationException {
        URL wsdlURL = SBFIFATDInquiryAssetTagNumberSrv.WSDL_LOCATION;
        SBFIFATDInquiryAssetTagNumberSrv ss = new SBFIFATDInquiryAssetTagNumberSrv(wsdlURL, SERVICE_NAME);
        com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassettagnumbersrv.SBFIFAInquiryAssetTagNumberSrv port = ss.getSBFIFAInquiryAssetTagNumberSrvPort();
        System.out.println("SB_FI_FA_InquiryAssetTagNumberSrv Invoking process...");
        SBFIFAInquiryAssetTagNumberSrvRequest _process_payload = null;
        MsgHeader msgHeader = new MsgHeader();
        msgHeader.setSOURCESYSTEMID("EAM");
        msgHeader.setSOURCESYSTEMNAME("EAM");
        msgHeader.setUSERID("IBM");
        msgHeader.setUSERNAME("IBM");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate=formatter.format(cal.getTime());
        msgHeader.setSUBMITDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar(nowDate));
        _process_payload = new SBFIFAInquiryAssetTagNumberSrvRequest();
        _process_payload.setBOOKTYPECODE(bookTypeCode);
        _process_payload.setACOUNT(new BigDecimal(account));
        _process_payload.setSEGMENT1(segment1);
        _process_payload.setMsgHeader(msgHeader);

        SBFIFAInquiryAssetTagNumberSrvResponse _process__return = port.process(_process_payload);
        returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getErrorFlag()));
        returnMessage.setErrorMessage(_process__return.getErrorMessage());
        System.out.println("errorMessage="+_process__return.getErrorMessage());
        if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
            List<SBFIFAInquiryAssetTagNumberSrvOutputItem> items = _process__return.getSBFIFAInquiryAssetTagNumberSrvOutputCollection().getSBFIFAInquiryAssetTagNumberSrvOutputItem();
            for (int i = 0; i < items.size(); i++) {
                SBFIFAInquiryAssetTagNumberSrvOutputItem item = items.get(i);
                SBFIFATdInquiryAssetTagNumberDTO orgStructureDTO = new SBFIFATdInquiryAssetTagNumberDTO();
                orgStructureDTO.setBookTypeCode(item.getBOOKTYPECODE());
                orgStructureDTO.setTagNumber(item.getTAGNUMBER());
                ds.addDTO(orgStructureDTO);
                System.out.println(item.getBOOKTYPECODE()+"---"+item.getTAGNUMBER());
            }
        }

    }

    public static void main(String args[]) {
        SBFIFATdInquiryAssetTagNumberSrv srv = null;
        try {
            srv = new SBFIFATdInquiryAssetTagNumberSrv();
            srv.setBookTypeCode("SXMC_FA_8110");
            srv.setAccount(1);
            srv.setSegment1("8110");
            srv.excute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}