package com.sino.ams.system.object.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.object.dao.ImportObjectDAOSn;
import com.sino.ams.system.object.dto.ImportObjectDTOSn;
import com.sino.ams.system.object.model.ImportObjectModelSn;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
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
import com.sino.base.util.StrUtil;
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
 * Date: 2011-6-1
 * Time: 17:46:51
 * To change this template use File | Settings | File Templates.
 */
public class ImportObjectServletSn extends BaseServlet {
	private static int startRowNum = 20;
	private static int columnNum = 8;

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = null;
		Connection conn = null;
		String showMsg = "";
		try {
			conn = DBManager.getDBConnection();
			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
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
            //=======================
			boolean autoCommit = conn.getAutoCommit();
			ReadObjectInfoSn xlsUtil = new ReadObjectInfoSn();
			xlsUtil.setFileName(fileName);
			xlsUtil.setNumberOfColumn(columnNum);
			xlsUtil.setStartRowNum(startRowNum);
			DTOSet dtoSet = xlsUtil.readXls(0);
			ImportObjectDAOSn ImObDAO = new ImportObjectDAOSn(userAccount, null, conn);
            ImObDAO.deleteImportModel();
            objectImportOnNet(dtoSet, conn, userAccount);
            addObjectInFo(dtoSet, conn, userAccount);
            ImObDAO.doVerifyData(dtoSet);
			ImportObjectModelSn onNetModel = new ImportObjectModelSn(userAccount, null);
			if (ImObDAO.importHasError()) {
				SQLModel sqlModel = onNetModel.getImportErrorModel();
				SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
				simpleQuery.executeQuery();
				RowSet rows = simpleQuery.getSearchResult();
				req.setAttribute(WebAttrConstant.ETS_SPARE_DTO, rows);
				forwardURL = "/system/object/importObjectErrorSn.jsp";
			} else {
				DTOSet dtoSetTemp = ImObDAO.getImport();
				submitOrderDtl(dtoSetTemp, conn, userAccount);
				forwardURL = "/system/object/importObjectSn.jsp";
				showMsg = "地点信息导入成功！";
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

    private void addObjectInFo(DTOSet dtoSet, Connection conn, SfUserDTO userAccount) throws DataHandleException {
        try {
            if (dtoSet != null && dtoSet.getSize() > 0) {
                SQLModel sqlModel = new SQLModel();
                ImportObjectModelSn modelProducer = new ImportObjectModelSn(userAccount, null);
                for (int i = 0; i < dtoSet.getSize(); i++) {
                    ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoSet.getDTO(i);
                    String countyCode = eoDTO.getCountyCode();
                    String locationCode = eoDTO.getLocationCode();
                    String location = eoDTO.getLocation();
                    eoDTO.setWorkorderObjectCode(countyCode + "." + locationCode + ".000");
                    sqlModel = modelProducer.getUpdateLocationCodeModel(eoDTO);
                    DBOperator.updateRecord(sqlModel, conn);
                    sqlModel = modelProducer.getCountyNameModel(countyCode);
                    SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
                    simpleQuery.executeQuery();
                    RowSet rs = simpleQuery.getSearchResult();
                    String countyName = "";
                    if (rs != null && rs.getSize() > 0) {
                        Row row = rs.getRow(0);
                        countyName = StrUtil.nullToString(row.getValue("COUNTY_NAME"));
                    }
                    eoDTO.setWorkorderObjectName(countyName + "." + eoDTO.getCity() + eoDTO.getCounty() + location + ".000");
                    sqlModel = modelProducer.getUpdateLocationModel(eoDTO);
                    DBOperator.updateRecord(sqlModel, conn);
                }
            }
        } catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (ContainerException ex) {
			ex.printLog();
            throw new DataHandleException(ex);
		}
    }

	private boolean submitOrderDtl(DTOSet dtoSet, Connection conn, SfUserDTO userAccount) throws DataHandleException {
		boolean operatorResult = false;
		try {
			if (dtoSet != null && dtoSet.getSize() > 0) {
				SQLModel sqlModel = new SQLModel();
				ImportObjectModelSn modelProducer = new ImportObjectModelSn(userAccount, null);
				for (int i = 0; i < dtoSet.getSize(); i++) {
					ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoSet.getDTO(i);
					eoDTO.setWorkorderObjectNo(StrUtil.nullToString(getNextWorkorderObjectNo(conn)));
                    int orgId = getOrgId(eoDTO.getCompanyCode(), userAccount, conn);
                    eoDTO.setOrganizationId(orgId);
                    String cityCode = getCityCode(eoDTO.getCounty(), userAccount, conn);
                    eoDTO.setCityCode(cityCode);
                    String counyCode2 = getCountyCode2(eoDTO.getCounty(), userAccount, conn);
                    eoDTO.setCountyCode2(counyCode2);
                    modelProducer.setDTOParameter(eoDTO);
					sqlModel = modelProducer.getDataCreateModel();
					DBOperator.updateRecord(sqlModel, conn);
					sqlModel = modelProducer.getAOACreateModel();
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
		} catch (ContainerException ex) {
			ex.printLog();
            throw new DataHandleException(ex);
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
		return operatorResult;
	}

   private int getNextWorkorderObjectNo(Connection conn) throws SQLException {
		SeqProducer seqProducer = new SeqProducer(conn);
		return seqProducer.getStrNextSeq("ETS_OBJECT_S");
	}

	private boolean objectImportOnNet(DTOSet dtoSet, Connection conn, SfUserDTO userAccount) throws QueryException, ContainerException, DataHandleException, SQLModelException {
		boolean operatorResult = false;
		if (dtoSet != null && dtoSet.getSize() > 0) {
			SQLModel sqlModel = new SQLModel();
			ImportObjectModelSn modelProducer = new ImportObjectModelSn(userAccount, null);
			for (int i = 0; i < dtoSet.getSize(); i++) {
				ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoSet.getDTO(i);
				modelProducer.setDTOParameter(eoDTO);
				sqlModel = modelProducer.insertImportModel();
				DBOperator.updateRecord(sqlModel, conn);
			}
		}
		operatorResult = true;
		return operatorResult;
	}

    private int getOrgId(String companyCode, SfUserDTO userAccount, Connection conn) throws QueryException, ContainerException, SQLModelException {
        int orgId = -1;
        ImportObjectModelSn modelProducer = new ImportObjectModelSn(userAccount, null);
        SQLModel sqlModel = modelProducer.getOrgIdModel(companyCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            orgId = Integer.parseInt(row.getStrValue("ORGANIZATION_ID"));
        }
        return orgId;

    }

    private String getCityCode(String county, SfUserDTO userAccount, Connection conn) throws QueryException, ContainerException, SQLModelException {
        String city = "";
        ImportObjectModelSn modelProducer = new ImportObjectModelSn(userAccount, null);
        SQLModel sqlModel = modelProducer.getCityCodeModel(county);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            city = StrUtil.nullToString(row.getValue("CITY_CODE"));
        }
        return city;

    }

    private String getCountyCode2(String county, SfUserDTO userAccount, Connection conn) throws QueryException, ContainerException, SQLModelException {
        String city = "";
        ImportObjectModelSn modelProducer = new ImportObjectModelSn(userAccount, null);
        SQLModel sqlModel = modelProducer.getCountyCode2Model(county);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            city = StrUtil.nullToString(row.getValue("COUNTY_CODE"));
        }
        return city;
    }
}