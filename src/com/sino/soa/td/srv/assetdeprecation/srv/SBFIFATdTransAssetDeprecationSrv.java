package com.sino.soa.td.srv.assetdeprecation.srv;

import com.sino.base.util.StrUtil;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_transassetdeprecationsrv.SBFIFATransAssetDeprecationSrvProcessRequest;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_transassetdeprecationsrv.SBFIFATDTransAssetDeprecationSrv;
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
 * Time: 16:48:37
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIFATdTransAssetDeprecationSrv {
    private SrvReturnMessage srvMessage = new SrvReturnMessage();

    private String periodName = "";
    private String envCode = "";

    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/fi/SB_FI_FA_TransAssetDeprecationSrv", "SB_FI_FA_TDTransAssetDeprecationSrv");

    public SBFIFATdTransAssetDeprecationSrv() {
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public SrvReturnMessage getSrvMessage() {
        return srvMessage;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public void excute() {
        URL wsdlURL = SBFIFATDTransAssetDeprecationSrv.WSDL_LOCATION;

        SBFIFATDTransAssetDeprecationSrv ss = new SBFIFATDTransAssetDeprecationSrv(wsdlURL, SERVICE_NAME);
        com.sino.soa.td.eip.fi.fa.sb_fi_fa_transassetdeprecationsrv.SBFIFATransAssetDeprecationSrv port = ss.getSBFIFATransAssetDeprecationSrvPort();

        Client client = ClientProxy.getClient(port);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();

        httpClientPolicy.setConnectionTimeout(5400000);//连接时间
        httpClientPolicy.setReceiveTimeout(5400000);//接收时间
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);

        {
            System.out.println("SB_FI_FA_TransAssetDeprecationSrv Invoking process...");
            SBFIFATransAssetDeprecationSrvProcessRequest _process_payload = null;

            _process_payload = new SBFIFATransAssetDeprecationSrvProcessRequest();
            _process_payload.setENVCODE(envCode);
            _process_payload.setPERIODNAME(periodName);
            long s = System.currentTimeMillis();
            com.sino.soa.td.eip.fi.fa.sb_fi_fa_transassetdeprecationsrv.SBFIFATransAssetDeprecationSrvProcessResponse _process__return = port.process(_process_payload);
            srvMessage.setErrorFlag(StrUtil.nullToString(_process__return.getERRORFLAG()));
            srvMessage.setErrorMessage(_process__return.getERRORMESSAGE());
            System.out.println("process.result=" + _process__return.getINSTANCEID() + "||" + _process__return.getERRORFLAG() + "||" + _process__return.getERRORMESSAGE());
            System.out.println("耗时" + (System.currentTimeMillis() - s) + "毫秒");

        }
    }

    /**
     * 测试函数
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        try {
            SBFIFATdTransAssetDeprecationSrv srv = new SBFIFATdTransAssetDeprecationSrv();
            srv.setPeriodName("APR-06");
            srv.setEnvCode("EAMS_TDTransAssetDeprecationSrv");
            srv.excute();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@ " + (System.currentTimeMillis() - a) / 1000);
        } catch (Exception e) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@ " + (System.currentTimeMillis() - a) / 1000);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}