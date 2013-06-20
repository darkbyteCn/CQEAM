package com.sino.soa.mis.srv.accountbalance.srv;

import com.sino.base.util.StrUtil;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.mis.eip.fi.gl.sb_fi_gl_transaccountbalancesrv.SBFIGLTransAccountBalanceSrvProcessRequest;
import com.sino.soa.mis.eip.fi.gl.sb_fi_gl_transaccountbalancesrv.SBFIGLTransAccountBalanceSrv_Service;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.xml.namespace.QName;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-12
 * Time: 17:40:06
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIGLTransAccountBalanceSrv {
    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private String envCode = "";
    private String setOfBooks = "";
    private String periodName = "";

    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/fi/SB_FI_GL_TransAccountBalanceSrv", "SB_FI_GL_TransAccountBalanceSrv");

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


    public SBFIGLTransAccountBalanceSrv() {
    }

    public void excute() throws Exception {
        URL wsdlURL = SBFIGLTransAccountBalanceSrv_Service.WSDL_LOCATION;

        SBFIGLTransAccountBalanceSrv_Service ss = new SBFIGLTransAccountBalanceSrv_Service(wsdlURL, SERVICE_NAME);
        com.sino.soa.mis.eip.fi.gl.sb_fi_gl_transaccountbalancesrv.SBFIGLTransAccountBalanceSrv port = ss.getSBFIGLTransAccountBalanceSrvPort();

        Client client = ClientProxy.getClient(port);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();

        httpClientPolicy.setConnectionTimeout(5400000);//连接时间
        httpClientPolicy.setReceiveTimeout(5400000);//接收时间
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);

        {
            System.out.println("SB_FI_GL_TransAccountBalanceSrv Invoking process...");
            com.sino.soa.mis.eip.fi.gl.sb_fi_gl_transaccountbalancesrv.SBFIGLTransAccountBalanceSrvProcessRequest _process_payload = null;
            _process_payload = new SBFIGLTransAccountBalanceSrvProcessRequest();
            _process_payload.setENVCODE(getEnvCode());
            _process_payload.setSETOFBOOKS(getSetOfBooks());
            _process_payload.setPERIODNAME(getPeriodName());
            com.sino.soa.mis.eip.fi.gl.sb_fi_gl_transaccountbalancesrv.SBFIGLTransAccountBalanceSrvProcessResponse _process__return = port.process(_process_payload);
            returnMessage.setErrorFlag(StrUtil.nullToString(_process__return.getERRORFLAG()));
            returnMessage.setErrorMessage(_process__return.getERRORMESSAGE());
            System.out.println("是否成功：---------" + returnMessage.getErrorFlag());
            System.out.println("错误信息：---------" + returnMessage.getErrorMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        SBFIGLTransAccountBalanceSrv srv = new SBFIGLTransAccountBalanceSrv();
        srv.setEnvCode("EAMS_TransAccountBalanceSrv");
//        srv.setSetOfBooks("SOB_SNMC");
        srv.setPeriodName("AUG-04");
        srv.excute();
    }

}