package com.sino.ams.spare.part.servlet;

import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.base.message.Message;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.log.Logger;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.ServletForwarder;
import com.sino.base.dto.DTOSet;
import com.sino.base.data.RowSet;
import com.sino.base.exception.*;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.spare.part.dao.ImportSpareCategoryDAO;
import com.sino.ams.spare.part.model.ImportSpareCategoryModel;
import com.sino.ams.spare.part.dto.ImportSpareCategoryDTO;
import com.sino.pda.PDAUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */

public class ImportSpareCategoryServlet extends BaseServlet {
	private static int startRowNum = 1;
	private static int columnNum = 6;

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = null;
		Connection conn = null;
		String showMsg = "";
		try {
			conn = DBManager.getDBConnection();
			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            ImportSpareCategoryDAO ImObDAO = new ImportSpareCategoryDAO(userAccount, null, conn);
            String action = req.getParameter("act");
            if (action == null) {
            Logger.logInfo("Excel submit servlet begin....");
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
				return;
			} else if (upFiles.length != 1 || upFiles[0].getFileName().equals("")) {
				return;
			}
			UploadFile uploadFile = upFiles[0];
			String fileName = uploadFile.getAbsolutePath();
//=========================================================
			boolean autoCommit = conn.getAutoCommit();
			ReadSpareCategoryInfo xlsUtil = new ReadSpareCategoryInfo();
			xlsUtil.setFileName(fileName);
			xlsUtil.setNumberOfColumn(columnNum);
			xlsUtil.setStartRowNum(startRowNum);
			DTOSet dtoSet = xlsUtil.readXls(0, conn);
            ImObDAO.deleteImportModel();
            itemImportData(dtoSet, conn, userAccount);
            ImObDAO.doVerifyData(dtoSet);
            ImportSpareCategoryModel onNetModel = new ImportSpareCategoryModel(userAccount, null);
			if (ImObDAO.importHasError()) {
				SQLModel sqlModel = onNetModel.getImportErrorModel();
				SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
				simpleQuery.executeQuery();
				RowSet rows = simpleQuery.getSearchResult();
				req.setAttribute(WebAttrConstant.ETS_SPARE_DTO, rows);
                forwardURL = "/spare/part/importSpareCategoryError.jsp";
            } else {
                DTOSet dtoSetTemp = ImObDAO.getImport();
				submitOrderDtl(dtoSetTemp, conn, userAccount);
                forwardURL = "/spare/part/importSpareCategory.jsp";
				showMsg = "备件设备分类导入成功！";
			}
            conn.commit();
			conn.setAutoCommit(autoCommit);
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

	/**
	 * 导入临时表
	 **/
	private boolean itemImportData(DTOSet dtoSet, Connection conn, SfUserDTO userAccount) throws  DataHandleException, SQLModelException {
		boolean operatorResult = false;
        if (dtoSet != null && dtoSet.getSize() > 0) {
			SQLModel sqlModel = new SQLModel();
			ImportSpareCategoryModel modelProducer = new ImportSpareCategoryModel(userAccount, null);
			for (int i = 0; i < dtoSet.getSize(); i++) {
				ImportSpareCategoryDTO eoDTO = (ImportSpareCategoryDTO) dtoSet.getDTO(i);
                modelProducer.setDTOParameter(eoDTO);
				sqlModel = modelProducer.insertImportModel();
				DBOperator.updateRecord(sqlModel, conn);
			}
		}
        operatorResult = true;
		return operatorResult;
	}

	/**
	 * 导入数据到正式表
	 * @param dtoSet
	 * @param conn
	 * @param userAccount
	 * @return
	 */
	private boolean submitOrderDtl(DTOSet dtoSet, Connection conn, SfUserDTO userAccount) throws DataHandleException {
		boolean operatorResult = false;
		try {
			if (dtoSet != null && dtoSet.getSize() > 0) {
				SQLModel sqlModel = new SQLModel();
				ImportSpareCategoryModel modelProducer = new ImportSpareCategoryModel(userAccount, null);
                for (int i = 0; i < dtoSet.getSize(); i++) {
					ImportSpareCategoryDTO dto = (ImportSpareCategoryDTO) dtoSet.getDTO(i);
                    dto.setBarcode(getNextCategoryBarcode(conn));
                    modelProducer.setDTOParameter(dto);
                    sqlModel = modelProducer.getDataCreateModel();
					DBOperator.updateRecord(sqlModel, conn);
				}
			}
			operatorResult = true;
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (SQLException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		}
		return operatorResult;
	}

    private String getNextCategoryBarcode(Connection conn) throws SQLException {
		SeqProducer seqProducer = new SeqProducer(conn);
		return String.valueOf(seqProducer.getStrNextSeq("AMS_SPARE_CATEGORY"));
//		return seqProducer.getGUID();
	}
}
