package com.sino.soa.mis.srv.pagequiryassetcustdetail.srv;

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
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_pageinquiryassetcustdetailsrv.*;
import com.sino.soa.mis.srv.pagequiryassetcustdetail.dto.SBFIFAPageinquiryAssetCustDetailDTO;
import com.sino.soa.mis.srv.projectInfo.dto.SrvProjectInfoDTO;
import com.sino.soa.util.XMLGregorianCalendarUtil;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-15
 * Time: 21:13:45
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIFAPageinquiryAssetCustDetailSrv {

    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetCustDetailSrv", "SB_FI_FA_PageInquiryAssetCustDetailSrv");

    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private DTOSet ds = new DTOSet();
    private int pageSize = -1;
    private int totalRecord = -1;
    private int totalPage = -1;
    private String bookTypeCode = "";
    private String projectNumber = "";
    private String taskNumFrom = "";
    private String taskNumTo = "";
    private String capitalizedDateFrom = "";
    private String capitalizedDateTo = "";
    private String batch = "";

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

    public String getBookTypeCode() {
        return bookTypeCode;
    }

    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getTaskNumFrom() {
        return taskNumFrom;
    }

    public void setTaskNumFrom(String taskNumFrom) {
        this.taskNumFrom = taskNumFrom;
    }

    public String getTaskNumTo() {
        return taskNumTo;
    }

    public void setTaskNumTo(String taskNumTo) {
        this.taskNumTo = taskNumTo;
    }

    public String getCapitalizedDateFrom() {
        return capitalizedDateFrom;
    }

    public void setCapitalizedDateFrom(String capitalizedDateFrom) {
        this.capitalizedDateFrom = capitalizedDateFrom;
    }

    public String getCapitalizedDateTo() {
        return capitalizedDateTo;
    }

    public void setCapitalizedDateTo(String capitalizedDateTo) {
        this.capitalizedDateTo = capitalizedDateTo;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public SBFIFAPageinquiryAssetCustDetailSrv() throws DatatypeConfigurationException {

    }


    public void execute() throws CalendarException, DTOException, DatatypeConfigurationException {
        URL wsdlURL = SBFIFAPageInquiryAssetCustDetailSrv_Service.WSDL_LOCATION;
        SBFIFAPageInquiryAssetCustDetailSrv_Service ss = new SBFIFAPageInquiryAssetCustDetailSrv_Service(wsdlURL, SERVICE_NAME);
        SBFIFAPageInquiryAssetCustDetailSrv port = ss.getSBFIFAPageInquiryAssetCustDetailSrvPort();
        Client client = ClientProxy.getClient(port);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(1000000000);//连接时间
        httpClientPolicy.setReceiveTimeout(1000000000);//接收时间
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);
        {
            System.out.println("SB_FI_GL_PageinquiryAssetCustDetailSrv Invoking process...");
            com.sino.soa.mis.eip.fi.fa.sb_fi_fa_pageinquiryassetcustdetailsrv.PageInquiryAssetCustDetailSrvRequest _process_payload = null;
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
            msgHeader.setPROVINCECODE( SinoConfig.getProvinceCode() );
            msgHeader.setENVIRONMENTNAME("");
            _process_payload = new PageInquiryAssetCustDetailSrvRequest();
            _process_payload.setBOOKTYPECODE(bookTypeCode);
            _process_payload.setPROJRCTNUMBER(projectNumber);
            _process_payload.setBATCH(batch);
            _process_payload.setMsgHeader(msgHeader);

            com.sino.soa.mis.eip.fi.fa.sb_fi_fa_pageinquiryassetcustdetailsrv.PageInquiryAssetCustDetailSrvResponse _process__return = port.process(_process_payload);
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
                    msgHeader.setPROVINCECODE( SinoConfig.getProvinceCode() );
                    msgHeader.setENVIRONMENTNAME("");
                    msgHeader.setPAGESIZE(pageSize);
                    msgHeader.setTOTALRECORD(totalRecord);
                    msgHeader.setCURRENTPAGE(new BigDecimal(i));
                    _process_payload = new PageInquiryAssetCustDetailSrvRequest();
                    _process_payload.setBOOKTYPECODE(bookTypeCode);
                    _process_payload.setPROJRCTNUMBER(projectNumber);
                    _process_payload.setBATCH(batch);
                    _process_payload.setMsgHeader(msgHeader);
                    _process__return = port.process(_process_payload);
                    if (_process__return.getErrorFlag().equalsIgnoreCase("Y")) {
                        List<InquiryAssetCustDetailSrvOutputItem> itemList = _process__return.getInquiryAssetCustDetailSrvOutputCollection().getInquiryAssetCustDetailSrvOutputItem();
                        for (int k = 0; k < itemList.size(); k++) {
                            InquiryAssetCustDetailSrvOutputItem item = itemList.get(k);
                            SBFIFAPageinquiryAssetCustDetailDTO categoryDTO = new SBFIFAPageinquiryAssetCustDetailDTO();
                            categoryDTO.setBookTypeCode(item.getBOOKTYPECODE());
                            //Start_date 1
                            categoryDTO.setDatePlacedInService(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getDATEPLACEDINSERVICE())); // 
                            categoryDTO.setProjectNumber(item.getPROJRCTNUMBER());   //1*
                            categoryDTO.setTaskNumber(item.getTASKNUMBER());
                            categoryDTO.setTaskName(item.getTASKNAME());
                            categoryDTO.setProjectType(item.getPROJECTTYPE());
                            categoryDTO.setTagNumber(item.getTAGNUMBER());         //2*
                            categoryDTO.setAssetName(item.getASSETNAME());
                            categoryDTO.setAssetDescription(item.getASSETDESCRIPTION());
                            categoryDTO.setAssetNumber(item.getASSETNUMBER());
                            categoryDTO.setAssetCategory(item.getASSETCATEGORY());
                            categoryDTO.setAssetCategoryDesc(item.getASSETCATEGORYDESC());
                            categoryDTO.setAssetLocation(item.getASSETLOCATION());
                            String assetUnits = StrUtil.nullToString(item.getASSETUNITS());
                            if (assetUnits.equals("")) {
                                assetUnits = "0";
                            }
                            //ASSET_UNITS 2
                            categoryDTO.setAssetUnits(assetUnits);   //
                            categoryDTO.setUnitOfMeasure(item.getUNITOFMEASURE());
                            String capitalizedCost = StrUtil.nullToString(item.getCAPITALIZEDCOST());
                            if (capitalizedCost.equals("")) {
                                capitalizedCost = "0";
                            }
                            categoryDTO.setCapitalizedCost(capitalizedCost);
                            //categoryDTO.setCapitalizedDate(StrUtil.nullToString(item.getCAPITALIZEDDATE()));    //
                            categoryDTO.setCapitalizedDate(XMLGregorianCalendarUtil.getSimpleCalendarString(item.getCAPITALIZEDDATE()));
                            categoryDTO.setAssetKeyCcidDesc(item.getASSETKEYCCIDDESC());
                            String assetEmployee = StrUtil.nullToString(item.getASSETEMPLOYEE());
                            if (assetEmployee.equals("")) {
                                assetEmployee = "-1";
                            }
                            categoryDTO.setAssetEmployee(assetEmployee);         
                            categoryDTO.setEmployeeName(item.getEMPLOYEENAME());
                            categoryDTO.setEmployeeNumber(item.getEMPLOYEENUMBER()); //3*
                            String depreciationExpenseCcid = StrUtil.nullToString(item.getDEPRECIATIONEXPENSECCID());
                            if (depreciationExpenseCcid.equals("")) {
                                depreciationExpenseCcid = "-1";
                            }
                            categoryDTO.setDepreciationExpenseCcid(depreciationExpenseCcid);
                            categoryDTO.setModelNumber(item.getMODELNUMBER());
                            categoryDTO.setManufactorerName(item.getMANUFACTORERNAME());
                            categoryDTO.setFaPeriodName(item.getFAPERIODNAME());
                            String locationId = StrUtil.nullToString(item.getLOCATIONID());
                            if (locationId.equals("")) {
                                locationId = "-1";
                            }
                            categoryDTO.setLocationId(locationId);
                            categoryDTO.setLocationCode(item.getLOCATIONCODE());
                            String taskId = StrUtil.nullToString(item.getTASKID());
                            if (taskId.equals("")) {
                                taskId = "-1";
                            }
                            categoryDTO.setTaskId(taskId);
                            String projectId = StrUtil.nullToString(item.getPROJECTID());
                            if (projectId.equals("")) {
                                projectId = "-1";
                            }
                            categoryDTO.setProjectId(projectId);
                            String projectAssetId = StrUtil.nullToString(item.getPROJECTASSETID());
                            if (projectAssetId.equals("")) {
                                projectAssetId = "-1";
                            }
                            categoryDTO.setProjectAssetId(projectAssetId);
                            categoryDTO.setAttribute1(item.getATTRIBUTE1());
                            categoryDTO.setAttribute2(item.getATTRIBUTE2());
                            categoryDTO.setAttribute3(item.getATTRIBUTE3());
                            categoryDTO.setAttribute4(item.getATTRIBUTE4());
                            categoryDTO.setAttribute5(item.getATTRIBUTE5());
                            categoryDTO.setAttribute6(item.getATTRIBUTE6());
                            categoryDTO.setAttribute7(item.getATTRIBUTE7());
                            ds.addDTO(categoryDTO);
                        }
                    }
                }
            }
        }
    }
    public String toString() {
        String s = null;
        for (int i = 0; i < ds.getSize(); i++) {
        	SBFIFAPageinquiryAssetCustDetailDTO dto = (SBFIFAPageinquiryAssetCustDetailDTO) ds.getDTO(i);
            s = s + dto.getProjectNumber() + "  date1:" + dto.getDatePlacedInService() + "  date2:"+dto.getCapitalizedDate()+
                 " assetUnits:" + dto.getAssetUnits() +" assetEmployee:"+ dto.getAssetEmployee()  +
                 " assetEmployee_number:"+dto.getEmployeeNumber()+ "  "+dto.getAttribute4()+"  "+dto.getAttribute5()+"  "+dto.getAttribute6()+"  "+dto.getAttribute7()+"\n";
        }
        return s;
    }
    public static void main(String[] args) throws Exception {
        SBFIFAPageinquiryAssetCustDetailSrv categorySrv = new SBFIFAPageinquiryAssetCustDetailSrv();
        categorySrv.setBookTypeCode("SXMC_FA_4110");
        categorySrv.setProjectNumber("B0941994");
        categorySrv.execute();
        System.out.println("test:\n"+categorySrv.toString()); ;
    }

}