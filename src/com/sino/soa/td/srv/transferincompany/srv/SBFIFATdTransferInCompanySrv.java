package com.sino.soa.td.srv.transferincompany.srv;

import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.SBFIFACreateAssetTransferInCompanySrv;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.SBFIFATDCreateAssetTransferInCompanySrv;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.createassettransferincompanysrv.CreateAssetTransferInCompanySrvInputCollection;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.createassettransferincompanysrv.CreateAssetTransferInCompanySrvInputItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.createassettransferincompanysrv.CreateAssetTransferInCompanySrvRequest;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.importsrvresponse.ErrorItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.importsrvresponse.ResponseItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.msgheader.MsgHeader;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-16
 * Time: 23:34:46
 * To change this template use File | Settings | File Templates.
 */
public final class SBFIFATdTransferInCompanySrv {
    private SrvReturnMessage returnMessage = new SrvReturnMessage();
    private CreateAssetTransferInCompanySrvInputCollection collection=null;
	private static final QName SERVICE_NAME = new QName("http://eip.zte.com/common/fa/SB_FI_FA_CreateAssetTransferInCompanySrv", "SB_FI_FA_TDCreateAssetTransferInCompanySrv");
	private ResponseItem responseItem = null;
    private ErrorItem errorItem = null;
    private List<ErrorItem> errorItemList=null;
    public SBFIFATdTransferInCompanySrv() throws DatatypeConfigurationException {

    }

    public void excute() throws Exception {
    	URL wsdlURL = SBFIFATDCreateAssetTransferInCompanySrv.WSDL_LOCATION;
    	SBFIFATDCreateAssetTransferInCompanySrv ss = new SBFIFATDCreateAssetTransferInCompanySrv(wsdlURL, SERVICE_NAME);
        SBFIFACreateAssetTransferInCompanySrv port = ss.getSBFIFACreateAssetTransferInCompanySrvPort();
        {
            System.out.println("Invoking process...");
            CreateAssetTransferInCompanySrvRequest _process_payload = null;

            _process_payload=new CreateAssetTransferInCompanySrvRequest();
            MsgHeader msgHeader=new MsgHeader();
            _process_payload.setCreateAssetTransferInCompanySrvInputCollection(this.collection);
            _process_payload.setMsgHeader(msgHeader);
            com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.importsrvresponse.ImportSrvResponse _process__return = port.process(_process_payload);
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
    	SBFIFATdTransferInCompanySrv srv = new SBFIFATdTransferInCompanySrv();
    	CreateAssetTransferInCompanySrvInputItem item=new CreateAssetTransferInCompanySrvInputItem();
    	CreateAssetTransferInCompanySrvInputCollection items=new CreateAssetTransferInCompanySrvInputCollection();
    	item.setPRIKEY("111");
    	item.setASSETID(new BigDecimal(10258300));
        item.setLOCATIONCOMBINATIONCODE("890000.550000.000");
        item.setEXPENSEDEPRNCODE("4110.400005.5414030101.000000.00000000.0000.000000");
    	item.setASSETUNIT(new BigDecimal(1));
        item.setASSIGNEDTO(new BigDecimal(41001016));
    	item.setCREATEDBY(new BigDecimal(0));
    	items.getCreateAssetTransferInCompanySrvInputItem().add(item);
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
	public CreateAssetTransferInCompanySrvInputCollection getCollection() {
		return collection;
	}

	/**
	 * @param collection the collection to set
	 */
	public void setCollection(
			CreateAssetTransferInCompanySrvInputCollection collection) {
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