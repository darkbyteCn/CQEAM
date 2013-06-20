package com.sino.soa.mis.srv.valueinfo.srv;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.mis.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv.*;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-14
 * Time: 15:30:19
 * To change this template use File | Settings | File Templates.
 */
public final class SBSYSYImportVSetValueInfoSrv {
    private List<ImportVSetValueInfoSrvInputItem> importVSetValueInfoSrvInputItems = null;

    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private List<ResponseItem> responseItemList = null;
    private List<ErrorItem> errorItemList = null;

    private static final QName SERVICE_NAME = new QName("http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", "SB_SY_SY_ImportVSetValueInfoSrv");

    public SBSYSYImportVSetValueInfoSrv() {
    }

    public void setImportVSetValueInfoSrvInputItems(List<ImportVSetValueInfoSrvInputItem> importVSetValueInfoSrvInputItems) {
        this.importVSetValueInfoSrvInputItems = importVSetValueInfoSrvInputItems;
    }

    public SrvReturnMessage getReturnMessage() {
        return returnMessage;
    }

    public List<ResponseItem> getResponseItemList() {
        return responseItemList;
    }

    public List<ErrorItem> getErrorItemList() {
        return errorItemList;
    }


    public void excute() {
        URL wsdlURL = SBSYSYImportVSetValueInfoSrv_Service.WSDL_LOCATION;

//        try {
//        	ClassLoader.getSystemClassLoader().loadClass( "org.apache.cxf.jaxws.spi.ProviderImpl" );
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
        SBSYSYImportVSetValueInfoSrv_Service ss = new SBSYSYImportVSetValueInfoSrv_Service(wsdlURL, SERVICE_NAME);
        com.sino.soa.mis.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv.SBSYSYImportVSetValueInfoSrv port = ss.getSBSYSYImportVSetValueInfoSrvPort();
 
        
        Client client = ClientProxy.getClient(port);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();

        httpClientPolicy.setConnectionTimeout(5400000);//连接时间
        httpClientPolicy.setReceiveTimeout(5400000);//接收时间
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);

        {
            System.out.println("SB_SY_SY_ImportVSetValueInfoSrv Invoking process...");
            ImportVSetValueInfoSrvRequest _process_payload = null;
            _process_payload = new ImportVSetValueInfoSrvRequest();
            ImportVSetValueInfoSrvInputCollection collection = new ImportVSetValueInfoSrvInputCollection();
            MsgHeader msgHeader = new MsgHeader();
            msgHeader.setSOURCESYSTEMID("");
            msgHeader.setSOURCESYSTEMNAME("");
            msgHeader.setUSERID("");
            msgHeader.setUSERNAME("");
            msgHeader.setSUBMITDATE(null);
//             106202.JF0002.000  西安网络管理中心传输室.无明细地址.000   r
            for (int i = 0; i < importVSetValueInfoSrvInputItems.size(); i++) {
                collection.getImportVSetValueInfoSrvInputItem().add(importVSetValueInfoSrvInputItems.get(i));
            }

            _process_payload.setMsgHeader(msgHeader);


            _process_payload.setImportVSetValueInfoSrvInputCollection(collection);
            ImportVSetValueInfoSrvResponse _process__return = port.process(_process_payload);
            System.out.println("process.result=" + _process__return.getErrorFlag() + "||" + _process__return.getErrorMessage());
            if (_process__return.getErrorFlag() != null) {
                returnMessage.setErrorFlag(_process__return.getErrorFlag());
                returnMessage.setErrorMessage(_process__return.getErrorMessage());
                StringBuffer sb = new StringBuffer();
                sb.append("ErrorFlag: " + _process__return.getErrorFlag() + "\n");
                sb.append("ErrorMessage: " + _process__return.getErrorMessage() + "\n");
                if (_process__return.getErrorFlag().equals("Y")) {
                    responseItemList = _process__return.getResponseCollecion().getResponseItem();
                    for (int i = 0; i < responseItemList.size(); i++) {
                        sb.append("RECORD_NUMBER: " + responseItemList.get(i).getRECORDNUMBER() + "\n");
                    }
                } else {
                    errorItemList = _process__return.getErrorCollection().getErrorItem();
                    for (int i = 0; i < errorItemList.size(); i++) {
                        sb.append("RECORD_NUMBER: " + errorItemList.get(i).getRECORDNUMBER() + "=" + errorItemList.get(i).getERRORMESSAGE() + "\n");
                    }
                }
                String Message = sb.toString();
                System.out.println(Message);
            }


        }
    }

    public static void main(String args[]) throws Exception {
        SBSYSYImportVSetValueInfoSrv srv = new SBSYSYImportVSetValueInfoSrv();
        List<ImportVSetValueInfoSrvInputItem> inputItems = new ArrayList<ImportVSetValueInfoSrvInputItem>();
        ImportVSetValueInfoSrvInputItem inputItem = new ImportVSetValueInfoSrvInputItem();
//            inputItem.setPRIKEY("001");
//            inputItem.setFLEXVALUESETNAME("CMCC_FA_LOC_1");
//            inputItem.setVALIDATIONTYPE("I");
//            inputItem.setFLEXVALUE("106202");
//            inputItem.setDESCRIPTION("西安网络管理中心传输室00");
//            inputItem.setENABLEDFLAG("Y");
//            inputItem.setSUMMARYFLAG("N");
//            inputItem.setCREATEDBY(new BigDecimal(1069));
//            inputItem.setEMPLOYEENUMBER("40000075");
//
//           inputItems.add(inputItem);
//            106300.XAJZ5054.000   西安维护优化中心本部.长安翠华山滑雪场XAM643.000
        inputItem = new ImportVSetValueInfoSrvInputItem();
        inputItem.setPRIKEY("002");
        inputItem.setFLEXVALUESETNAME("CMCC_FA_LOC_2");
        inputItem.setVALIDATIONTYPE("I");
        inputItem.setFLEXVALUE("2521JZ00061994");
        inputItem.setDESCRIPTION("2521JZ00061994");
        inputItem.setENABLEDFLAG("Y");
        inputItem.setSUMMARYFLAG("N");
        inputItem.setPARENTFLEXVALUE("252138");
//        inputItem.setPARENTFLEXVALUE("890000");
//        inputItem.setCREATEDBY(new BigDecimal(2750));
        inputItem.setEMPLOYEENUMBER("41000663");
//
        inputItems.add(inputItem);
//
//            inputItem=new ImportVSetValueInfoSrvInputItem();
//            inputItem.setPRIKEY("003");
//            inputItem.setFLEXVALUESETNAME("CMCC_FA_LOC_3");
//            inputItem.setVALIDATIONTYPE("I");
//            inputItem.setFLEXVALUE("444");
//            inputItem.setENABLEDFLAG("Y");
//            inputItem.setSUMMARYFLAG("N");
////            inputItem.setPARENTFLEXVALUE("JF9004");
//            inputItem.setCREATEDBY(new BigDecimal(1069));
//            inputItem.setEMPLOYEENUMBER("40000075");
//
//           inputItems.add(inputItem);
        srv.setImportVSetValueInfoSrvInputItems(inputItems);
        srv.excute();
        System.exit(0);
    }

}