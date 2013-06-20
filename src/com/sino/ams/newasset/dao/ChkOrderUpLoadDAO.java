package com.sino.ams.newasset.dao;

import java.sql.Connection;
import java.util.List;

import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.log.Logger;
import com.sino.ams.log.dao.UserLoginDAO;
import com.sino.ams.newasset.bean.ChkOrderXMLProcessor;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.pda.dao.OrderUploadDAO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ChkOrderUpLoadDAO extends OrderUploadDAO{

	public ChkOrderUpLoadDAO(SfUserDTO userAccount, Connection conn) {
		super(userAccount, conn);
	}

	/**
	 * 功能：上载扫描资产后的盘点工单
	 * <B>由工单上载servlet WorkorderSubmit控制事务，与网络运维工单统一</B>
	 * @param filePath String
	 * @return boolean
	 */
	public boolean uploadOrders(String filePath) {
		boolean operateResult = false;
		try {
			ChkOrderXMLProcessor xmlParser = new ChkOrderXMLProcessor(conn);
			xmlParser.parseXML(filePath);

			List headerIds = xmlParser.getHeaderIds();
			String headerId = "";
			boolean itemExist = false;
			String scanStatus = "";

			AmsAssetsCheckHeaderDTO orderHeader = new AmsAssetsCheckHeaderDTO();
			ChkOrderHeaderUploadDAO headerDAO = new ChkOrderHeaderUploadDAO(userAccount, orderHeader, conn);
			AmsAssetsCheckLineDTO orderLine = new AmsAssetsCheckLineDTO();
			ChkOrderLineUploadDAO lineDAO = new ChkOrderLineUploadDAO(userAccount, orderLine, conn);

			for (int i = 0; i < headerIds.size(); i++) {
				headerId = (String) headerIds.get(i);
				orderHeader = xmlParser.getChkOrder(headerId);
				headerDAO.setDTOParameter(orderHeader);
//				headerDAO.uploadOrderHeader();

				orderBarcodes = headerDAO.getOrderBarcodes(true);//获取该单据下设备信息
				DTOSet chkLines = xmlParser.getChkLines(headerId);//获取本次工单提交的设备信息
				if (chkLines != null && !chkLines.isEmpty()) {
					for (int j = 0; j < chkLines.getSize(); j++) {
						orderLine = (AmsAssetsCheckLineDTO) chkLines.getDTO(j);
						scanStatus = orderLine.getScanStatus();
						if (scanStatus.equals(AssetsDictConstant.STATUS_NO)) { //未扫描到该资产
							continue;
						}
						itemExist = orderBarcodes.contains("barcode", orderLine.getBarcode());
						lineDAO.setDTOParameter(orderLine);
						lineDAO.uploadOrderLine(itemExist);
					}
//					lineDAO.updateLeftBarcodes();
				}
			}
			operateResult = true;
		} catch (Exception ex) {
			Logger.logError(ex);
		}
		return operateResult;
	}

	public static void main(String[] args) throws Exception {
		Connection conn = DBManager.getDBConnection();
		String loginName = "4022MAWEI";
		SfUserDTO userAccount = new SfUserDTO();
		userAccount.setLoginName(loginName);
		userAccount.setPassword("eam");
		UserLoginDAO loginDAO = new UserLoginDAO(userAccount, conn);
		if (loginDAO.isValidUser()) {
			userAccount = (SfUserDTO) loginDAO.getUserAccount();
			ChkOrderUpLoadDAO uploadDAO = new ChkOrderUpLoadDAO(userAccount, conn);
			uploadDAO.uploadOrders("c:\\outgoing_tmp.xml");
		}
		DBManager.closeDBConnection(conn);
		System.exit(0);
	}
}
