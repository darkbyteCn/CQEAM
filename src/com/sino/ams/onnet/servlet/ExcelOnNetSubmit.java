package com.sino.ams.onnet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.onnet.dao.AmsItemOnNetDAO;
import com.sino.ams.onnet.dto.AmsItemOnNetDTO;
import com.sino.ams.onnet.model.AmsItemOnNetModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.FileSizeException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.UploadException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.PDAUtil;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class ExcelOnNetSubmit extends BaseServlet {
    private static final String m_sContentType = "text/xml; charset=GBK";
	private static int startRowNum = 1;
	private static int columnNum = 3;

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		String showMsg = "";
		try {
			conn = DBManager.getDBConnection();
			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
			res.setContentType(m_sContentType);
			PrintWriter resout = res.getWriter();
			Logger.logInfo("Excel submit servlet begin....");
			RequestParser reqPar = new RequestParser();
			reqPar.transData(req);
			resout.println("<?xml version=\"1.0\" ?> ");
			resout.println("<SubmitExcelOrder>");
			UploadFile[] upFiles = null;
			UploadRow uploadRow;
			boolean returnFlag = false;
			String conFilePath = PDAUtil.getCurUploadFilePath(conn);
			UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
			uploadFileSaver.saveFiles(conFilePath);
			uploadRow = uploadFileSaver.getRow();
			upFiles = uploadRow.getFiles();
			if (upFiles == null) {
				returnFlag = true;
				setResultValue(resout, false, "upload file " + SyBaseSQLUtil.isNull() + " ", "");
			} else {
				if (upFiles.length == 1) {
					if (upFiles[0].getFileName().equals("")) {
						Logger.logError("can't find upload xml files");
						returnFlag = true;
						setResultValue(resout, false, "can't find upload  files,please check the file.", "");
					}
				} else {
					Logger.logError("can't find any xml file");
					setResultValue(resout, false, "can't find any xml file", "");
					returnFlag = true;
				}
			}
			if (returnFlag) {
				return;
			}

			UploadFile uploadFile = upFiles[0];
			String fileName = uploadFile.getAbsolutePath();
            //=======================
			boolean autoCommit = conn.getAutoCommit();
			XlsOnNetReader xlsUtil = new XlsOnNetReader();
			xlsUtil.setFileName(fileName);
			xlsUtil.setNumberOfColumn(columnNum);
			xlsUtil.setStartRowNum(startRowNum);
			DTOSet dtoSet = xlsUtil.readXls(0);
			AmsItemOnNetDAO amsItemOnNetDAO = new AmsItemOnNetDAO(userAccount, null, conn);
			amsItemOnNetDAO.deleteImportModel();
			insertImportOnNet(dtoSet, conn,userAccount);    //导入到ams_on_net_import
			amsItemOnNetDAO.doVerifyData(dtoSet);
			AmsItemOnNetModel onNetModel = new AmsItemOnNetModel(userAccount, null);
			if (amsItemOnNetDAO.importHasError()) {
				SQLModel sqlModel = onNetModel.getQueryImportModel();
				SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
				simpleQuery.executeQuery();
				RowSet rows = simpleQuery.getSearchResult();		
				req.setAttribute(WebAttrConstant.ETS_SPARE_DTO, rows);
				forwardURL = "/spare/onnet/importError.jsp";
			} else {
				DTOSet dtoSetTemp = amsItemOnNetDAO.getImport();
				submitOrderDtl(dtoSetTemp, conn,userAccount);
				forwardURL = "/spare/onnet/importOnNet.jsp";
				showMsg = "上传成功！";
			}
			conn.commit();
			conn.setAutoCommit(autoCommit);
		} catch (PoolException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			Logger.logError(e);
		} catch (DTOException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			Logger.logError(e);
		} catch (SQLException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			Logger.logError(e);
		} catch (DataHandleException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			Logger.logError(e);
		} catch (QueryException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			 Logger.logError(e);
		} catch (ContainerException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			 Logger.logError(e);
		} catch (UploadException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			Logger.logError(e);
		} catch (FileSizeException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			Logger.logError(e);
		} catch (SQLModelException e) {
		   Logger.logError(e);
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
	 * 提交工单（数据导入正式表）
	 * @param dtoSet
	 * @param conn
	 * @return
	 */
	private boolean submitOrderDtl(DTOSet dtoSet, Connection conn ,SfUserDTO userAccount) throws QueryException, ContainerException, DataHandleException, SQLModelException {
		boolean hasSubmit = false;
		boolean operatorResult = false;
		if (dtoSet != null && dtoSet.getSize() > 0) {
			String workorderNo = "";
			List sqlModelList = new ArrayList();
			AmsItemOnNetDTO   dtoParameter = new AmsItemOnNetDTO();
			SQLModel sqlModel = new SQLModel();
			for (int i = 0; i < dtoSet.getSize(); i++) {
				hasSubmit =false;
				AmsItemOnNetDTO onNetDTO = (AmsItemOnNetDTO) dtoSet.getDTO(i);
				if (hasPartNo(onNetDTO.getPartNo(),onNetDTO.getOrganizationId(),conn)){
				   updatePartNO(onNetDTO.getQuantity(),onNetDTO.getPartNo(),onNetDTO.getOrganizationId(),conn);
				} else{
				   AmsItemOnNetModel   onNetModel = new AmsItemOnNetModel(userAccount, onNetDTO);
				   sqlModel = onNetModel.getDataCreateModel();
				   DBOperator.updateRecord(sqlModel, conn);
				}
			}
		}

		operatorResult=true;
		return operatorResult;

	}

	private void setResultValue(PrintWriter out, boolean b_flag, String errMsg, String m_workorder_no) {
		out.println("<result message=\"" + errMsg + "\" >" + String.valueOf(b_flag) + "</result>");
		out.println("<workorder id=\"" + m_workorder_no + "\" />");
		out.println("</SubmitExcelOrder>");
		out.close();
	}

    /**
     * 判断是否存在
     */
	private boolean hasPartNo(String partNo ,int  organizationId,Connection conn) throws QueryException {
		boolean isExist= false;
		SQLModel sqlModel = new SQLModel();
		List    sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1 FROM AMS_ITEM_ON_NET AION WHERE AION.PART_NO = ? AND AION.ORGANIZATION_ID = ? ";
		sqlArgs.add(partNo);
		sqlArgs.add(organizationId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel,conn);
		simpleQuery.executeQuery();
		if(simpleQuery.hasResult()){
			   isExist=true;
		}
		return isExist;
	}


   private boolean hasInCategory(String barcode,String  organizationId,Connection conn) throws QueryException {
	   boolean  has= false;
	   SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
	   String sqlStr= "SELECT 1 FROM AMS_SPARE_CATEGORY AMSC WHERE AMSC.BARCODE =? AND AMSC.ORGANIZATION_ID = ?";
	   sqlArgs.add(barcode);
	   sqlArgs.add(organizationId);
	   sqlModel.setSqlStr(sqlStr);
	   sqlModel.setArgs(sqlArgs);
	   SimpleQuery simpleQuery = new SimpleQuery(sqlModel,conn);
	   simpleQuery.executeQuery();
	   if(simpleQuery.hasResult()){
		   has = true;
	   }
	   return has;
   }

  private void  updatePartNO(int quantity,String partNo ,int  organizationId,Connection conn) throws QueryException, DataHandleException {
	 SQLModel sqlModel = new SQLModel();
	 List sqlArgs = new ArrayList();
	 String  sqlStr = " UPDATE AMS_ITEM_ON_NET\n" +
			         " SET QUANTITY = ?\n" +
			         " WHERE PART_NO = ? \n" +
                     " AND ORGANIZATION_ID = ? \n";
		sqlArgs.add(quantity) ;
		sqlArgs.add(partNo);
	    sqlArgs.add(organizationId);
	  sqlModel.setArgs(sqlArgs);
	  sqlModel.setSqlStr(sqlStr);
	  DBOperator.updateRecord(sqlModel,conn);
  }

  /**
	 * 功能：插入到现网量的接口表
	 */
	private boolean insertImportOnNet(DTOSet dtoSet, Connection conn ,SfUserDTO userAccount) throws QueryException, ContainerException, DataHandleException, SQLModelException {
		boolean hasSubmit = false;
		boolean operatorResult = false;
		if (dtoSet != null && dtoSet.getSize() > 0) {
			SQLModel sqlModel = new SQLModel();
			for (int i = 0; i < dtoSet.getSize(); i++) {
				hasSubmit =false;
				AmsItemOnNetDTO onNetDTO = (AmsItemOnNetDTO) dtoSet.getDTO(i);
			   AmsItemOnNetModel   onNetModel = new AmsItemOnNetModel(userAccount, onNetDTO);
			   sqlModel = onNetModel.insertImportModel();
			   DBOperator.updateRecord(sqlModel, conn);
			}
		}
		operatorResult=true;
		return operatorResult;

	}


}
