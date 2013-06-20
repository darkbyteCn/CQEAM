package com.sino.soa.mis.srv.assettransbtwcompanysrv.srv;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;

import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.SBFIFACreateAssetTransferIntercompanysSrv;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.SBFIFACreateAssetTransferIntercompanysSrv_Service;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.createassettransferintercompanyssrv.CreateAssetTransferIntercompanysSrvInputCollection;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.createassettransferintercompanyssrv.CreateAssetTransferIntercompanysSrvInputItem;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.createassettransferintercompanyssrv.CreateAssetTransferIntercompanysSrvRequest;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.importsrvresponse.ErrorItem;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.importsrvresponse.ImportSrvResponse;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.importsrvresponse.ResponseItem;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.msgheader.MsgHeader;
import com.sino.soa.util.XMLGregorianCalendarUtil;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-15
 * Time: 15:40:41
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIFAAssetsTransBtwCompanySrv {
    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private CreateAssetTransferIntercompanysSrvInputCollection collection=null;
	private static final QName SERVICE_NAME = new QName("http://eip.zte.com/common/fa/SB_FI_FA_CreateAssetTransferIntercompanysSrv", "SB_FI_FA_CreateAssetTransferIntercompanysSrv");
	private ResponseItem responseItem = null;
    private ErrorItem errorItem = null;
    private List<ErrorItem> errorItemList=null;
    public SBFIFAAssetsTransBtwCompanySrv() throws DatatypeConfigurationException {

    }

    public void excute() throws Exception {
    	URL wsdlURL = SBFIFACreateAssetTransferIntercompanysSrv_Service.WSDL_LOCATION;


    	SBFIFACreateAssetTransferIntercompanysSrv_Service ss = new SBFIFACreateAssetTransferIntercompanysSrv_Service(wsdlURL, SERVICE_NAME);
        SBFIFACreateAssetTransferIntercompanysSrv port = ss.getSBFIFACreateAssetTransferIntercompanysSrvPort();
        Client client = ClientProxy.getClient(port);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(1000000000);//连接时间
        httpClientPolicy.setReceiveTimeout(1000000000);//接收时间
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);

        {
            System.out.println("Invoking process...");
            CreateAssetTransferIntercompanysSrvRequest _process_payload = null;

            _process_payload=new CreateAssetTransferIntercompanysSrvRequest();
            MsgHeader msgHeader=new MsgHeader();
            _process_payload.setCreateAssetTransferIntercompanysSrvInputCollection(this.collection);
            _process_payload.setMsgHeader(msgHeader);
            ImportSrvResponse _process__return = port.process(_process_payload);
            System.out.println("process.result=" + "="+_process__return.getErrorFlag()+"||"+_process__return.getErrorMessage());
            if (_process__return.getErrorFlag() != null) {
                returnMessage.setErrorFlag(_process__return.getErrorFlag());
                returnMessage.setErrorMessage(_process__return.getErrorMessage());
                StringBuffer sb = new StringBuffer();
                sb.append("ErrorFlag: " + _process__return.getErrorFlag() + "\n");
                sb.append("ErrorMessage: " + _process__return.getErrorMessage() + "\n");
                if (_process__return.getErrorFlag().equals("Y")) {
                    List<ResponseItem> items = _process__return.getResponseCollecion().getResponseItem();
                    for (int i = 0; i < items.size(); i++) {
                        responseItem = items.get(i);
                        sb.append("RECORD_NUMBER: " + responseItem.getRECORDNUMBER() + "\n");
                    }
                } else {
                    List<ErrorItem> items = _process__return.getErrorCollection().getErrorItem();
                    for (int i = 0; i < items.size(); i++) {
                        errorItem = items.get(i);
                        sb.append("RECORD_NUMBER: " + errorItem.getRECORDNUMBER() + "=" + errorItem.getERRORMESSAGE() + "\n");
                    }
                    setErrorItemList(items);
                }
                String Message = sb.toString();
                System.out.println(Message);
            }

        }

    }

    public static void main(String[] args) throws Exception {
    	SBFIFAAssetsTransBtwCompanySrv srv = new SBFIFAAssetsTransBtwCompanySrv();
    	CreateAssetTransferIntercompanysSrvInputItem item=new CreateAssetTransferIntercompanysSrvInputItem();
    	CreateAssetTransferIntercompanysSrvInputCollection items=new CreateAssetTransferIntercompanysSrvInputCollection();
    	item.setPRIKEY("12345678");
        item.setFROMBOOKTYPECODE("SXMC_FA_4110");
        item.setTOBOOKTYPECODE("SXMC_FA_4125");
        item.setCOMPANYFROM("4110");
        item.setCOMPANYTO("4125");
        item.setCONCATENATEDSEGMENTS("01.07-01-01-01.0000");
        item.setTAGNUMBER("4110-10000021");
        item.setTRANSACTIONDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar("2011-09-20"));
        item.setLOCATIONCODE("252600.4125KH00000212.000");
        item.setEXPENSEDEPRNCODE("4125.252600.5414030101.000000.00000000.0000.000000");
        item.setASSETUNIT(new BigDecimal(1));
        item.setASSIGNEDNUMBER("41250217");
        item.setASSIGNEDTO("");
//        item.setCREATEDBY(new BigDecimal(1058));
        item.setEMPLOYEENUMBER("41250218");
        item.setTAGNUMBERNEW("4110-90000011");
        items.getCreateAssetTransferIntercompanysSrvInputItem().add(item);
    	srv.setCollection(items);
        srv.excute();

        System.exit(0);
    }

	/**
	 * @return the returnMessage
	 */
	public SrvReturnMessage getReturnMessage() {
		return returnMessage;
	}

	/**
	 * @param returnMessage the returnMessage to set
	 */
	public void setReturnMessage(SrvReturnMessage returnMessage) {
		this.returnMessage = returnMessage;
	}


	/**
	 * @return the collection
	 */
	public CreateAssetTransferIntercompanysSrvInputCollection getCollection() {
		return collection;
	}

	/**
	 * @param collection the collection to set
	 */
	public void setCollection(
			CreateAssetTransferIntercompanysSrvInputCollection collection) {
		this.collection = collection;
	}

	/**
	 * @return the responseItem
	 */
	public ResponseItem getResponseItem() {
		return responseItem;
	}

	/**
	 * @param responseItem the responseItem to set
	 */
	public void setResponseItem(ResponseItem responseItem) {
		this.responseItem = responseItem;
	}

	/**
	 * @return the errorItem
	 */
	public ErrorItem getErrorItem() {
		return errorItem;
	}

	/**
	 * @param errorItem the errorItem to set
	 */
	public void setErrorItem(ErrorItem errorItem) {
		this.errorItem = errorItem;
	}

	/**
	 * @return the errorItemList
	 */
	public List<ErrorItem> getErrorItemList() {
		return errorItemList;
	}

	/**
	 * @param errorItemList the errorItemList to set
	 */
	public void setErrorItemList(List<ErrorItem> errorItemList) {
		this.errorItemList = errorItemList;
	}

}