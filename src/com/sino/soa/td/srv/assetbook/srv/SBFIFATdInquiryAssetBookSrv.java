package com.sino.soa.td.srv.assetbook.srv;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.calen.SimpleDate;
import com.sino.base.constant.calen.DateConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DateException;
import com.sino.base.util.StrUtil;
import com.sino.soa.td.srv.assetbook.dto.SBFIFATdAssetBookDTO;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetbooksrv.InquiryAssetBookSrvOutputItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetbooksrv.InquiryAssetBookSrvRequest;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetbooksrv.InquiryAssetBookSrvResponse;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetbooksrv.SBFIFATDInquiryAssetBookSrv;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetbooksrv.msgheader.MsgHeader;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.util.XMLGregorianCalendarUtil;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.net.URL;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-6
 * Time: 12:25:01
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIFATdInquiryAssetBookSrv {
    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();

    private String bookTypeCode = "";
    private XMLGregorianCalendar lastUpdateDate = null;

    private DatatypeFactory factory = null;
    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/common/fa/SB_FI_FA_InquiryAssetBookSrv", "SB_FI_FA_TDInquiryAssetBookSrv");


    public SBFIFATdInquiryAssetBookSrv() throws DatatypeConfigurationException {
        factory = DatatypeFactory.newInstance();
        lastUpdateDate = factory.newXMLGregorianCalendar();
    }

    public void excute() throws CalendarException, DTOException {
        URL wsdlURL = SBFIFATDInquiryAssetBookSrv.WSDL_LOCATION;

        SBFIFATDInquiryAssetBookSrv ss = new SBFIFATDInquiryAssetBookSrv(wsdlURL, SERVICE_NAME);
        com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetbooksrv.SBFIFAInquiryAssetBookSrv port = ss.getSBFIFAInquiryAssetBookSrvPort();

        System.out.println("SB_FI_FA_InquiryAssetBookSrv Invoking process...");
        InquiryAssetBookSrvRequest _process_payload = null;
        MsgHeader msgHeader = new MsgHeader();
        _process_payload = new InquiryAssetBookSrvRequest();
        _process_payload.setBOOKTYPECODE(bookTypeCode);
        _process_payload.setLASTUPDATEDATE(lastUpdateDate);
        _process_payload.setMsgHeader(msgHeader);

        InquiryAssetBookSrvResponse _process__return = port.process(_process_payload);
        returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getErrorFlag()));
        returnMessage.setErrorMessage(_process__return.getErrorMessage());
        if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
            List<InquiryAssetBookSrvOutputItem> items = _process__return.getInquiryAssetBookSrvOutputCollection().getInquiryAssetBookSrvOutputItem();
            for (int i = 0; i < items.size(); i++) {
                InquiryAssetBookSrvOutputItem item = items.get(i);
                SBFIFATdAssetBookDTO assetBookDTO = new SBFIFATdAssetBookDTO();
                assetBookDTO.setBookTypeCode(item.getBOOKTYPECODE());
                assetBookDTO.setBookTypeName(item.getBOOKTYPENAME());
                assetBookDTO.setOrgId(Integer.parseInt(item.getORGID().toString()));
                assetBookDTO.setOrgName(item.getORGNAME());
                assetBookDTO.setSetOfBooksName(item.getSETOFBOOKSNAME());
                assetBookDTO.setProrateCalendar(item.getPRORATECALENDAR());
                assetBookDTO.setDateIneffective(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getDATEINEFFECTIVE()));
                assetBookDTO.setDeprnStatus(item.getDEPRNSTATUS());
                assetBookDTO.setLastDeprnRunDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getLASTDEPRNRUNDATE()));
                assetBookDTO.setLastUpdateDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getLASTUPDATEDATE()));

                ds.addDTO(assetBookDTO);
            }
        }

    }

    public SrvReturnMessage getReturnMessage() {
        return returnMessage;
    }


    public DTOSet getDs() {
        return ds;
    }


    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public void setLastUpdateDate(String lastUpdateDate) throws DateException, CalendarException, DatatypeConfigurationException {
        SimpleDate simpleDate = new SimpleCalendar(lastUpdateDate).getSimpleDate();
        this.lastUpdateDate = factory.newXMLGregorianCalendarDate(simpleDate.get(DateConstant.YEAR), simpleDate.get(DateConstant.MONTH), simpleDate.get(DateConstant.DATE), 480);
        this.lastUpdateDate = XMLGregorianCalendarUtil.getXMLGregorianCalendar(lastUpdateDate);
    }

    public String toString() {
        String s = null;
        for (int i = 0; i < ds.getSize(); i++) {
            SBFIFATdAssetBookDTO dto = (SBFIFATdAssetBookDTO) ds.getDTO(i);
            try {
                s = s + "dto:" + dto.getBookTypeCode() + "  TypeName:" + dto.getBookTypeName() + " " + dto.getLastUpdateDate() + "\n";
            } catch (CalendarException e) {
               e.printLog();
            }
        }
        return s;
    }

    /**
     * ²âÊÔÖ÷º¯Êý
     * @param args
     */
    public static void main(String args[]) {
        SBFIFATdInquiryAssetBookSrv srv = null;
        try {
            srv = new SBFIFATdInquiryAssetBookSrv();
            srv.excute();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(" " + srv.toString());
    }
}