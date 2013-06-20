package com.sino.soa.td.srv.valueinfo.srv;

import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv.*;

import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-14
 * Time: 15:30:46
 * To change this template use File | Settings | File Templates.
 */
public final class SBSYSYTdImportVSetValueInfoSrv {
    private List<ImportVSetValueInfoSrvInputItem> importVSetValueInfoSrvInputItems = null;
    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private List<ResponseItem> responseItemList = null;
    private List<ErrorItem> errorItemList = null;

    private static final QName SERVICE_NAME = new QName("http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv", "SB_SY_SY_TDImportVSetValueInfoSrv");

    public SBSYSYTdImportVSetValueInfoSrv() {

    }

    public void execute() {
        URL wsdlURL = SBSYSYTDImportVSetValueInfoSrv.WSDL_LOCATION;

        SBSYSYTDImportVSetValueInfoSrv ss = new SBSYSYTDImportVSetValueInfoSrv(wsdlURL, SERVICE_NAME);
        SBSYSYImportVSetValueInfoSrv port = ss.getSBSYSYImportVSetValueInfoSrvPort();

        System.out.println("Invoking process...");
        ImportVSetValueInfoSrvInputCollection collection = new ImportVSetValueInfoSrvInputCollection();
        MsgHeader msgHeader = new MsgHeader();
        msgHeader.setSOURCESYSTEMID("");
        msgHeader.setSOURCESYSTEMNAME("");
        msgHeader.setSUBMITDATE(null);
        msgHeader.setUSERID("");
        msgHeader.setUSERNAME("");

        ImportVSetValueInfoSrvRequest _process_payload = null;
        _process_payload = new ImportVSetValueInfoSrvRequest();
        for (int i = 0; i < importVSetValueInfoSrvInputItems.size(); i++) {
            collection.getImportVSetValueInfoSrvInputItem().add(importVSetValueInfoSrvInputItems.get(i));
        }
        _process_payload.setImportVSetValueInfoSrvInputCollection(collection);
        _process_payload.setMsgHeader(msgHeader);

        ImportVSetValueInfoSrvResponse _process__return = port.process(_process_payload);
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
        System.out.println("process.result=" + _process__return.getErrorFlag() + "||" + _process__return.getErrorMessage());
    }

    public List<ErrorItem> getErrorItemList() {
        return errorItemList;
    }

    public List<ResponseItem> getResponseItemList() {
        return responseItemList;
    }

    public SrvReturnMessage getReturnMessage() {
        return returnMessage;
    }

    public void setImportVSetValueInfoSrvInputItems(List<ImportVSetValueInfoSrvInputItem> importVSetValueInfoSrvInputItems) {
        this.importVSetValueInfoSrvInputItems = importVSetValueInfoSrvInputItems;
    }

    public static void main(String args[]) throws Exception {
        SBSYSYTdImportVSetValueInfoSrv srv = new SBSYSYTdImportVSetValueInfoSrv();
        List<ImportVSetValueInfoSrvInputItem> inputItems = new ArrayList<ImportVSetValueInfoSrvInputItem>();
        ImportVSetValueInfoSrvInputItem inputItem ;

        inputItem = new ImportVSetValueInfoSrvInputItem();
        inputItem.setPRIKEY("002");
        inputItem.setFLEXVALUESETNAME("CMCC_FA_LOC_2");
        inputItem.setVALIDATIONTYPE("I");
        inputItem.setFLEXVALUE("XAJZXATD360");
        inputItem.setDESCRIPTION("高新一路1");
        inputItem.setENABLEDFLAG("Y");
        inputItem.setSUMMARYFLAG("N");
        inputItem.setPARENTFLEXVALUE("106306");
//        inputItem.setCREATEDBY(new BigDecimal(0));
        inputItem.setEMPLOYEENUMBER("52139");
        inputItems.add(inputItem);

        srv.setImportVSetValueInfoSrvInputItems(inputItems);
        srv.execute();
    }
}