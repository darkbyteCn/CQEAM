package com.sino.soa.td.srv.assettransbtwcompanysrv.srv;

import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.SBFIFACreateAssetTransferIntercompanysSrv;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.SBFIFATDCreateAssetTransferIntercompanysSrv;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.createassettransferintercompanyssrv.CreateAssetTransferIntercompanysSrvInputCollection;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.createassettransferintercompanyssrv.CreateAssetTransferIntercompanysSrvInputItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.createassettransferintercompanyssrv.CreateAssetTransferIntercompanysSrvRequest;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.importsrvresponse.ErrorItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.importsrvresponse.ImportSrvResponse;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.importsrvresponse.ResponseItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.msgheader.MsgHeader;
import com.sino.soa.util.XMLGregorianCalendarUtil;
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
 * Date: 2011-9-15
 * Time: 18:33:50
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIFATdAssetsTransBtwCompanySrv {
    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private CreateAssetTransferIntercompanysSrvInputCollection collection=null;
	private static final QName SERVICE_NAME = new QName("http://eip.zte.com/common/fa/SB_FI_FA_CreateAssetTransferIntercompanysSrv", "SB_FI_FA_TDCreateAssetTransferIntercompanysSrv");
	private ResponseItem responseItem = null;
    private ErrorItem errorItem = null;
    private List<ErrorItem> errorItemList=null;
    public SBFIFATdAssetsTransBtwCompanySrv() throws DatatypeConfigurationException {

    }

    public void excute() throws Exception {
    	URL wsdlURL = SBFIFATDCreateAssetTransferIntercompanysSrv.WSDL_LOCATION;


    	SBFIFATDCreateAssetTransferIntercompanysSrv ss = new SBFIFATDCreateAssetTransferIntercompanysSrv(wsdlURL, SERVICE_NAME);
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
    	SBFIFATdAssetsTransBtwCompanySrv srv = new SBFIFATdAssetsTransBtwCompanySrv();
    	CreateAssetTransferIntercompanysSrvInputItem item=new CreateAssetTransferIntercompanysSrvInputItem();
    	CreateAssetTransferIntercompanysSrvInputCollection items=new CreateAssetTransferIntercompanysSrvInputCollection();
    	item.setPRIKEY("12345678");
        item.setFROMBOOKTYPECODE("SXMC_FA_4110");
        item.setTOBOOKTYPECODE("SXMC_FA_4123");
        item.setCOMPANYFROM("4110");
        item.setCOMPANYTO("4123");
        item.setCONCATENATEDSEGMENTS("管理用.物业及配套设备－建筑物附属设施－防护设备－刺网.刺网");
        item.setTAGNUMBER("4110-00000264");
        item.setTRANSACTIONDATE(XMLGregorianCalendarUtil.getXMLGregorianCalendar("2011-09-23"));
        item.setLOCATIONCODE("890000.4110JZ00000010.000");
        item.setEXPENSEDEPRNCODE("4110.400016.5414030101.000000.00000000.0000.000000");
        item.setASSETUNIT(new BigDecimal(1));
        item.setASSIGNEDNUMBER("41000894");
        item.setEMPLOYEENUMBER("41000894");
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