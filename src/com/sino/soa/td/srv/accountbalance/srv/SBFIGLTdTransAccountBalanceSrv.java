package com.sino.soa.td.srv.accountbalance.srv;

import com.sino.base.util.StrUtil;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.fi.gl.sb_fi_gl_transaccountbalancesrv.SBFIGLTransAccountBalanceSrvProcessRequest;
import com.sino.soa.td.eip.fi.gl.sb_fi_gl_transaccountbalancesrv.SBFIGLTDTransAccountBalanceSrv;
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
 * Time: 11:32:12
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIGLTdTransAccountBalanceSrv {
    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private String envCode = "";
    private String setOfBooks = "";
    private String periodName = "";

    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/fi/SB_FI_GL_TransAccountBalanceSrv", "SB_FI_GL_TDTransAccountBalanceSrv");

    public SrvReturnMessage getReturnMessage() {
        return returnMessage;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getSetOfBooks() {
        return setOfBooks;
    }

    public void setSetOfBooks(String setOfBooks) {
        this.setOfBooks = setOfBooks;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }


    public SBFIGLTdTransAccountBalanceSrv() {
    }

    public void excute() throws Exception {
        URL wsdlURL = SBFIGLTDTransAccountBalanceSrv.WSDL_LOCATION;

        SBFIGLTDTransAccountBalanceSrv ss = new SBFIGLTDTransAccountBalanceSrv(wsdlURL, SERVICE_NAME);
        com.sino.soa.td.eip.fi.gl.sb_fi_gl_transaccountbalancesrv.SBFIGLTransAccountBalanceSrv port = ss.getSBFIGLTransAccountBalanceSrvPort();

        Client client = ClientProxy.getClient(port);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();

        httpClientPolicy.setConnectionTimeout(5400000);//连接时间
        httpClientPolicy.setReceiveTimeout(5400000);//接收时间
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);

        {
            System.out.println("SB_FI_GL_TransAccountBalanceSrv Invoking process...");
            com.sino.soa.td.eip.fi.gl.sb_fi_gl_transaccountbalancesrv.SBFIGLTransAccountBalanceSrvProcessRequest _process_payload = null;
            _process_payload = new SBFIGLTransAccountBalanceSrvProcessRequest();
            _process_payload.setENVCODE(getEnvCode());
            _process_payload.setSETOFBOOKS(getSetOfBooks());
            _process_payload.setPERIODNAME(getPeriodName());
            com.sino.soa.td.eip.fi.gl.sb_fi_gl_transaccountbalancesrv.SBFIGLTransAccountBalanceSrvProcessResponse _process__return = port.process(_process_payload);
            returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getERRORFLAG()));
            returnMessage.setErrorMessage(_process__return.getERRORMESSAGE());
            System.out.println("是否成功：---------" + returnMessage.getErrorFlag());
            System.out.println("错误信息：---------" + returnMessage.getErrorMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        SBFIGLTdTransAccountBalanceSrv srv = new SBFIGLTdTransAccountBalanceSrv();
        srv.setEnvCode("EAMS_TDTransAccountBalanceSrv");
//        srv.setSetOfBooks("SOB_SNMC");
        srv.setPeriodName("AUG-04");
        srv.excute();
    }

}