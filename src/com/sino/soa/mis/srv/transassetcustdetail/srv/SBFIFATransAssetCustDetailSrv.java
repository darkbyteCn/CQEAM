package com.sino.soa.mis.srv.transassetcustdetail.srv;

import com.sino.base.util.StrUtil;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_transassetcustdetailsrv.SBFIFATransAssetCustDetailSrvProcessRequest;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_transassetcustdetailsrv.SBFIFATransAssetCustDetailSrv_Service;
import com.sino.soa.util.XMLGregorianCalendarUtil;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.xml.namespace.QName;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-13
 * Time: 14:18:17
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIFATransAssetCustDetailSrv {
    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private String envCode = "";
    private String bookTypeCode = "";
    private String projectNumber = "";
    private String taskNum = "";
    private String capitalizedDateFrom = "";
    private String capitalizedDateTo = "";

    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/fi/SB_FI_FA_TransAssetCustDetailSrv", "SB_FI_FA_TransAssetCustDetailSrv");

    public SrvReturnMessage getReturnMessage() {
        return returnMessage;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
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

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
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

    public SBFIFATransAssetCustDetailSrv() {
    }

    public void excute() throws Exception {
        URL wsdlURL = SBFIFATransAssetCustDetailSrv_Service.WSDL_LOCATION;

        SBFIFATransAssetCustDetailSrv_Service ss = new SBFIFATransAssetCustDetailSrv_Service(wsdlURL, SERVICE_NAME);
        com.sino.soa.mis.eip.fi.fa.sb_fi_fa_transassetcustdetailsrv.SBFIFATransAssetCustDetailSrv port = ss.getSBFIFATransAssetCustDetailSrvPort();

        Client client = ClientProxy.getClient(port);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();

        httpClientPolicy.setConnectionTimeout(5400000);//连接时间
        httpClientPolicy.setReceiveTimeout(5400000);//接收时间
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);

        {
            System.out.println("SB_FI_FA_TransAssetCustDetailSrv Invoking process...");
            com.sino.soa.mis.eip.fi.fa.sb_fi_fa_transassetcustdetailsrv.SBFIFATransAssetCustDetailSrvProcessRequest _process_payload = null;
            _process_payload = new SBFIFATransAssetCustDetailSrvProcessRequest();
            _process_payload.setENVCODE(getEnvCode());
            _process_payload.setBOOKTYPECODE(getBookTypeCode());
            _process_payload.setPROJECTNUMBER(getProjectNumber());
            _process_payload.setTASKNUM(getTaskNum());
            _process_payload.setCAPITALIZEDDATEFROM(XMLGregorianCalendarUtil.getXMLGregorianCalendar(capitalizedDateFrom));
            _process_payload.setCAPITALIZEDDATETO(XMLGregorianCalendarUtil.getXMLGregorianCalendar(capitalizedDateTo));
            com.sino.soa.mis.eip.fi.fa.sb_fi_fa_transassetcustdetailsrv.SBFIFATransAssetCustDetailSrvProcessResponse _process__return = port.process(_process_payload);
            returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getERRORFLAG()));
            returnMessage.setErrorMessage(_process__return.getERRORMESSAGE());
            System.out.println("是否成功：---------" + returnMessage.getErrorFlag());
            System.out.println("错误信息：---------" + returnMessage.getErrorMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        SBFIFATransAssetCustDetailSrv srv = new SBFIFATransAssetCustDetailSrv();
        srv.setEnvCode("EAMS_TransAssetCustDetailSrv");
        srv.setBookTypeCode("SXMC_FA_4110");
        srv.setProjectNumber("B0941994");
//        srv.setTaskNum("10.001");
//        srv.setCapitalizedDateFrom("2010-2-24");
//        srv.setCapitalizedDateTo("2010-2-24");
        srv.excute();
    }

}