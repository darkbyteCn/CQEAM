package com.sino.ams.newasset.urgenttrans.pda.dao;

import java.sql.Connection;
import java.util.List;

import com.sino.ams.newasset.urgenttrans.constant.UrgentAppConstant;
import com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO;
import com.sino.ams.newasset.urgenttrans.dto.UrgentLineDTO;
import com.sino.ams.newasset.urgenttrans.pda.model.UrgentUploadModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.pda.dao.OrderUploadDAO;

public class UrgentOrderOutUploadDAO extends OrderUploadDAO {

	public UrgentOrderOutUploadDAO(SfUserDTO userAccount, Connection conn) {
		super(userAccount, conn);
	}

	@Override
	public boolean uploadOrders(String filePath) {
		boolean operateResult = false;
		try { 
			UrgentOrderXMLProcessor xmlParser = new UrgentOrderXMLProcessor(
					conn);
			xmlParser.parseXML(filePath); 
			List<String> headerIds = xmlParser.getHeaderIds();
			String headerId = "";

			UrgentHeaderDTO orderHeader = new UrgentHeaderDTO();
  
			UrgentLineDTO orderLine = new UrgentLineDTO();
//			ChkOrderLineUploadDAO lineDAO = new ChkOrderLineUploadDAO(
//					userAccount, orderLine, conn);

			for (int i = 0; i < headerIds.size(); i++) {
				headerId = (String) headerIds.get(i);
				orderHeader = xmlParser.getHeader(headerId);
				
				if( !orderHeader.getOrderType().equals( UrgentAppConstant.OUT_ORDER_NAME )){
					continue;
				}
				orderHeader.setTransStatus( UrgentAppConstant.STATUS_TRANS_OUT );
				this.updateHeaderStatus( orderHeader );
				//TODO SJ ADD 
				this.deleteLine( orderHeader.getTransId() );
				this.deleteReserved( orderHeader.getTransId() );
				
				DTOSet chkLines = xmlParser.getLines(headerId);// 获取本次工单提交的设备信息
				if (chkLines != null && !chkLines.isEmpty()) {
					for (int j = 0; j < chkLines.getSize(); j++) {
						orderLine = (UrgentLineDTO) chkLines.getDTO(j);
						orderLine.setTransId( headerId );
						createLine( orderLine ); 
						createReserved( headerId , orderLine.getBarcode() );
					}
				}
			}
			operateResult = true;
		} catch (Exception ex) {
			Logger.logError(ex);
		}
		return operateResult;
	}
 
	/**
	 * 功能：更新单据头状态为已调出。
	 * 
	 * @throws DataHandleException
	 */
	public void updateHeaderStatus(UrgentHeaderDTO headerDTO)
			throws DataHandleException {
		UrgentUploadModel model = new UrgentUploadModel(userAccount, headerDTO);
		SQLModel sqlModel = model.updateHeaderStatusModel(headerDTO);
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 功能：保存行 -- 调出上传
	 * 
	 * @throws DataHandleException
	 */
	public void createLine( UrgentLineDTO lineDTO )
			throws DataHandleException {
		UrgentUploadModel model = new UrgentUploadModel(userAccount, null );
		SQLModel sqlModel = model.createLineModel( lineDTO );
		DBOperator.updateRecord(sqlModel, conn);
	}
	
	/**
	 * 功能：删除行 -- 调出上传
	 * 
	 * @throws DataHandleException
	 */
	public void deleteLine( String transId )
			throws DataHandleException {
		UrgentUploadModel model = new UrgentUploadModel(userAccount, null );
		SQLModel sqlModel = model.deleteLineModel( transId );
		DBOperator.updateRecord(sqlModel, conn);
	}
	
	/**
	 * 功能：删除保留行 -- 
	 * 
	 * @throws DataHandleException
	 */
	public void deleteReserved( String transId )
			throws DataHandleException {
		UrgentUploadModel model = new UrgentUploadModel(userAccount, null );
		SQLModel sqlModel = model.deleteReservedModel( transId );
		DBOperator.updateRecord(sqlModel, conn);
	}
	
	/**
	 * 功能：保存行 -- 调出上传
	 * 
	 * @throws DataHandleException
	 */
	public void createReserved( String transId , String barcode  )
			throws DataHandleException {
		UrgentUploadModel model = new UrgentUploadModel(userAccount, null );
		SQLModel sqlModel = model.createReservedModel(transId, barcode) ;
		DBOperator.updateRecord(sqlModel, conn);
	}
}
