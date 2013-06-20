package com.sino.ams.system.object.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.object.dao.ImportItemDAO;
import com.sino.ams.system.object.dto.EtsItemDTO;
import com.sino.ams.system.object.model.ImportItemModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.FileSizeException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.PDAUtil;

/**
 * 
 * @系统名称: 
 * @功能描述: 实物成批导入功能
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Dec 16, 2011
 */
public class ImportNewItemServlet extends BaseServlet {
	private static int startRowNum = 1;

	private static int columnNum = 18;

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = null;
		Connection conn = null;
		String showMsg = "";
		try {
			conn = DBManager.getDBConnection();
			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
			ImportItemDAO ImObDAO = new ImportItemDAO(userAccount, null, conn);
			String action = req.getParameter("act");
			if (action == null) {
				Logger.logInfo("Excel submit servlet begin....");
				
				DTOSet dtoSet = this.parseXSL(req, conn);
				if( null == dtoSet ){
					return;
				}
				
				boolean isSuccess =  this.prodImport(dtoSet, ImObDAO, userAccount, conn);
				if ( !isSuccess ) {
					ImportItemModel onNetModel = new ImportItemModel(userAccount,
							null);
					SQLModel sqlModel = onNetModel.getImportErrorModel();
					SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
					simpleQuery.executeQuery();
					RowSet rows = simpleQuery.getSearchResult();
					req.setAttribute(WebAttrConstant.ETS_SPARE_DTO, rows);
					forwardURL = "/system/object/importItemError.jsp?isNew=Y";
				} else {
					DTOSet dtoSetTemp = ImObDAO.getImport();
					submitOrderDtl(dtoSetTemp, conn, userAccount);
					forwardURL = "/system/object/importItem.jsp?isNew=Y";
					showMsg = "实物成批导入成功！";
				}
			} else {
				File file = ImObDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			}
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.SQL_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (FileSizeException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLModelException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if (showMsg.equals("")) {
				forwarder.forwardView(forwardURL);
			} else {
				forwarder.forwardView(forwardURL, showMsg);
			}
		}
	}

	public boolean prodImport(DTOSet dtoSet, ImportItemDAO ImObDAO,
			SfUserDTO userAccount, Connection conn) throws SQLModelException,
			QueryException, DataHandleException, ContainerException, SQLException {
		boolean autoCommit = conn.getAutoCommit();
		boolean isSuccess = false;
		try {
			ImObDAO.deleteImportModel();
			conn.commit();
			// 导入到接口表ams_item_import
			itemImportData(dtoSet, conn, userAccount);
			conn.commit();
//			conn.setAutoCommit( false );
			// 检查接口表数据的有效性。
			ImObDAO.doVerifyData_insert(dtoSet);
			conn.commit();
			if( ImObDAO.importHasError() ){
				isSuccess = false;
			}else{
				isSuccess = true;
			}
		} catch (SQLModelException e) {
			throw e;
		} catch (QueryException e) {
			throw e;
		} catch (DataHandleException e) {
			throw e;
		} catch (ContainerException e) {
			throw e;
		} finally {	
			return isSuccess;
		}

	}

	/**
	 * 功能：插入到接口表
	 * 
	 * @param dtoSet
	 *            DTOSet
	 * @param conn
	 *            Connection
	 * @param userAccount
	 *            SfUserDTO
	 * @return boolean
	 * @throws DataHandleException
	 * @throws SQLModelException
	 */
	private boolean itemImportData(DTOSet dtoSet, Connection conn,
			SfUserDTO userAccount) throws DataHandleException,
			SQLModelException {
		boolean operatorResult = false;
		if (dtoSet != null && dtoSet.getSize() > 0) {
			SQLModel sqlModel = new SQLModel();
			ImportItemModel modelProducer = new ImportItemModel(userAccount,
					null);
			for (int i = 0; i < dtoSet.getSize(); i++) {
				EtsItemDTO eoDTO = (EtsItemDTO) dtoSet.getDTO(i);
				modelProducer.setDTOParameter(eoDTO);
				sqlModel = modelProducer.insertImportModel();
				DBOperator.updateRecord(sqlModel, conn);
			}
		}
		operatorResult = true;
		return operatorResult;
	}

	/**
	 * 功能：导入到表ets_item_info提交工单
	 * 
	 * @param dtoSet
	 *            DTOSet
	 * @param conn
	 *            Connection
	 * @param userAccount
	 *            SfUserDTO
	 * @return boolean
	 * @throws DataHandleException
	 * @throws CalendarException
	 */
	private boolean submitOrderDtl(DTOSet dtoSet, Connection conn,
			SfUserDTO userAccount) throws DataHandleException {
		boolean operatorResult = false;
		try {
			if (dtoSet != null && dtoSet.getSize() > 0) {
				SQLModel sqlModel = new SQLModel();
				ImportItemModel modelProducer = new ImportItemModel(
						userAccount, null);

				for (int i = 0; i < dtoSet.getSize(); i++) {
					EtsItemDTO eoDTO = (EtsItemDTO) dtoSet.getDTO(i);
					ImportItemDAO ImObDAO = new ImportItemDAO(userAccount,
							null, conn);

					if (!StrUtil.isEmpty(eoDTO.getNewItemName())
							&& !StrUtil.isEmpty(eoDTO.getNewItemSpec())) {// 获取ITEM_CODE
						String itemCode = ImObDAO.getItemCode(eoDTO
								.getNewItemName(), eoDTO.getNewItemSpec());
						eoDTO.setItemCode(itemCode);
					}
					if (!StrUtil.isEmpty(eoDTO.getNewObjectCode())) {// 获取ADDRESS_ID
						String addressId = ImObDAO.getAddressId(eoDTO
								.getNewObjectCode(), eoDTO.getBookTypeCode());
						eoDTO.setAddressId(addressId);
					}
					if (!StrUtil.isEmpty(eoDTO.getNewEmployeeNumber())) {// 获取EMPLOYEE_ID
						String employeeId = ImObDAO.getEmployeeId(eoDTO
								.getNewEmployeeNumber());
						eoDTO.setEmployeeId(employeeId);
					}
					if (ImObDAO.validateBarcode(eoDTO.getBarcode())) {
						modelProducer.setDTOParameter(eoDTO);
						sqlModel = modelProducer.getDataUpdateModel();
					} else {
						eoDTO.setFinanceProp("UNKNOW");
						eoDTO.setRemark("实物成批更新导入新增资产");
						eoDTO.setItemQty(1);
						modelProducer.setDTOParameter(eoDTO);
						sqlModel = modelProducer.getDataInsertModel();
					}
					DBOperator.updateRecord(sqlModel, conn);
				}
			}
			operatorResult = true;
			// 插入变动历史
			if (dtoSet != null && dtoSet.getSize() > 0) {
				for (int i = 0; i < dtoSet.getSize(); i++) {
					EtsItemDTO eoDTO = (EtsItemDTO) dtoSet.getDTO(i);
					ImportItemDAO ImObDAO = new ImportItemDAO(userAccount,
							null, conn);
					ImObDAO.logBarcodeHistory(eoDTO);
				}

			}
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (ContainerException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		} catch (QueryException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		}
		return operatorResult;
	}
	
	/**
	 * 解析Excel
	 * @param req
	 * @param conn
	 * @return
	 * @throws UploadException
	 * @throws FileSizeException
	 * @throws ContainerException
	 * @throws IOException
	 * @throws DTOException
	 */
	private DTOSet parseXSL( HttpServletRequest req, Connection conn ) throws UploadException, FileSizeException, ContainerException, IOException, DTOException{
		DTOSet retSet = null;
		
		RequestParser reqPar = new RequestParser();
		reqPar.transData(req);
		UploadFile[] upFiles = null;
		UploadRow uploadRow;
		String conFilePath = PDAUtil.getCurUploadFilePath(conn);
		UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
		uploadFileSaver.saveFiles(conFilePath);
		uploadRow = uploadFileSaver.getRow();
		upFiles = uploadRow.getFiles();
		if (upFiles == null) {
			retSet = null;
		} else if (upFiles.length != 1
				|| upFiles[0].getFileName().equals("")) {
			retSet = null;
		}else{
			UploadFile uploadFile = upFiles[0];
			String fileName = uploadFile.getAbsolutePath();
			ReadItemInfo xlsUtil = new ReadItemInfo();
			xlsUtil.setFileName(fileName);
			xlsUtil.setNumberOfColumn(columnNum);
			xlsUtil.setStartRowNum(startRowNum);
			retSet = xlsUtil.readXls(0, true );
		}
		return retSet;
	}
}
