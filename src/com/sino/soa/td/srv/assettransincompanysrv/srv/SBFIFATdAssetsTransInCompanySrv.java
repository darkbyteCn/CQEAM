package com.sino.soa.td.srv.assettransincompanysrv.srv;

import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_bcreateassettransincompanysrv.*;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-14
 * Time: 9:50:58
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIFATdAssetsTransInCompanySrv {
    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private BCreateAssetTransInCompanySrvInputCollection collection=null;
	private static final QName SERVICE_NAME = new QName("http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv", "SB_FI_FA_TDBCreateAssetTransInCompanySrv");
	private ResponseItem responseItem = null;
    private ErrorItem errorItem = null;
    private List<ErrorItem> errorItemList=null;
    public SBFIFATdAssetsTransInCompanySrv() throws DatatypeConfigurationException {

    }

    public void excute() throws Exception {
    	URL wsdlURL = SBFIFATDBCreateAssetTransInCompanySrv.WSDL_LOCATION;


    	SBFIFATDBCreateAssetTransInCompanySrv ss = new SBFIFATDBCreateAssetTransInCompanySrv(wsdlURL, SERVICE_NAME);
        SBFIFABCreateAssetTransInCompanySrv port = ss.getSBFIFABCreateAssetTransInCompanySrvPort();
        Client client = ClientProxy.getClient(port);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(1000000000);//连接时间
        httpClientPolicy.setReceiveTimeout(1000000000);//接收时间
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);

        {
            System.out.println("Invoking process...");
            BCreateAssetTransInCompanySrvRequest _process_payload = null;

            _process_payload=new BCreateAssetTransInCompanySrvRequest();
            MsgHeader msgHeader=new MsgHeader();
            _process_payload.setBCreateAssetTransInCompanySrvInputCollection(this.collection);
            _process_payload.setMsgHeader(msgHeader);
            BCreateAssetTransInCompanySrvResponse _process__return = port.process(_process_payload);
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
    	SBFIFATdAssetsTransInCompanySrv srv = new SBFIFATdAssetsTransInCompanySrv();
    	BCreateAssetTransInCompanySrvInputItem item=new BCreateAssetTransInCompanySrvInputItem();
    	BCreateAssetTransInCompanySrvInputCollection items=new BCreateAssetTransInCompanySrvInputCollection();
    	item.setPRIKEY("12345678");
    	item.setLINENUMBER(new BigDecimal("1"));
    	item.setASSETNUMBER("4010223455");
    	item.setTAGNUMBER("3488392832");
//    	item.setCREATEDBY(new BigDecimal("1"));
    	item.setEMPLOYEENUMBER("234324");
    	item.setRESPONSIBILITYID(new BigDecimal("1"));
    	item.setRESPONSIBILITYNAME("234324");
    	items.getBCreateAssetTransInCompanySrvInputItem().add(item);
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
	public BCreateAssetTransInCompanySrvInputCollection getCollection() {
		return collection;
	}

	/**
	 * @param collection the collection to set
	 */
	public void setCollection(
			BCreateAssetTransInCompanySrvInputCollection collection) {
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