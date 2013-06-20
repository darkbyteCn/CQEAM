package com.sino.ams.system.object.servlet;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.object.dao.ImportSpareAssetsDAOJt;
import com.sino.ams.system.object.dto.ImportSpareAssetsDTOJt;
import com.sino.ams.system.object.model.ImportSpareAssetsModelJt;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-6-10
 * Time: 20:36:06
 * To change this template use File | Settings | File Templates.
 */
public class ImportSpareAssetsServletJt extends BaseServlet {
    private final static int startRowNum = 5;
    private final static int columnNum = 16;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = null;
        Connection conn = null;
        boolean validSuccess = false;
        String showMsg = "";
        try {
            conn = DBManager.getDBConnection();
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            ImportSpareAssetsDAOJt ImObDAO = new ImportSpareAssetsDAOJt(userAccount, null, conn);
            String action = req.getParameter("act");
            //if (action == null) {
            if (action.equals("UPLOAD_ACTION")) { 
                Logger.logInfo("Excel submit servlet begin....");
                String conFilePath = PDAUtil.getCurUploadFilePath(conn);
                DTOSet dtoSet = parseExcel2DTO(req, conFilePath);
                message = new Message();
                if(dtoSet != null){
                    //清除数据
                    ImObDAO.deleteImportModel();
                    ImObDAO.itemImportData(dtoSet);
                    dtoSet.clearData();
                    showMsg = ImObDAO.doVerifyData();
                    if (StrUtil.isEmpty(showMsg)) {//校验没有错误
                    	validSuccess = true ;
                        showMsg = ImObDAO.submitOrderDtl();
                        if(StrUtil.isEmpty(showMsg)) {
                            showMsg = "备品备件导入成功";
                            message.setMessageValue(showMsg);
                        } else {
                            message.setMessageValue(showMsg);
                            message.setIsError(true);
                        }
                        //forwardURL = MessageConstant.MSG_PRC_SERVLET;
                        forwardURL = "/servlet/com.sino.ams.system.object.servlet.ImportSpareAssetsServletJt?act=";
                    } else {
                        message.setMessageValue(showMsg);
                        message.setIsError(true);
                        RowSet rows = ImObDAO.getImportErrors();
                        req.setAttribute(WebAttrConstant.ETS_SPARE_DTO, rows);
                        forwardURL = "/system/object/importSpareErrorJt.jsp";
                    }
                } else {
                    showMsg = "不能从上载的Excel中解析出正确的数据，请确认上载Excel与模板格式一致！";
                    message.setMessageValue(showMsg);
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                }
            } else if (action == null || action.equals("")) {
            	forwardURL = "/system/object/importSpareAssetsJt.jsp";
            } else {
                File file = ImObDAO.getExportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);          
			ServletForwarder forwarder = new ServletForwarder(req, res);
            if (!StrUtil.isEmpty(forwardURL)) {
            	//!showMsg.equals("备品备件导入成功")
    			if (showMsg.equals("") || !validSuccess ) {
    				forwarder.forwardView(forwardURL);
    			} else {
    				forwarder.forwardView(forwardURL, showMsg);
    			}
            }
            
        }
    }

    private DTOSet parseExcel2DTO(HttpServletRequest req, String conFilePath) throws UploadException {
        DTOSet dtoSet = null;
        try {
            RequestParser reqPar = new RequestParser();
            reqPar.transData(req);
            UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
            uploadFileSaver.saveFiles(conFilePath);
            UploadRow uploadRow = uploadFileSaver.getRow();
            UploadFile[] upFiles = uploadRow.getFiles();
            if (upFiles != null) {
                if (upFiles.length == 1 && !upFiles[0].getFileName().equals("")) {
                    UploadFile uploadFile = upFiles[0];
                    String fileName = uploadFile.getAbsolutePath();
                    ReadSpareAssetsInfoJt xlsUtil = new ReadSpareAssetsInfoJt();
                    xlsUtil.setFileName(fileName);
                    xlsUtil.setNumberOfColumn(columnNum);
                    xlsUtil.setStartRowNum(startRowNum);
                    dtoSet = xlsUtil.readXls(0);
                }
            }
        } catch (IOException ex) {
            Logger.logError(ex);
            throw new UploadException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new UploadException(ex.getMessage());
        }
        return dtoSet;
    }


    /**
     * 功能：插入到接口表
     *
     * @param dtoSet      DTOSet
     * @param conn        Connection
     * @param userAccount SfUserDTO
     * @return boolean
     * @throws DataHandleException
     * @throws SQLModelException
     */
    private boolean itemImportData(DTOSet dtoSet, Connection conn, SfUserDTO userAccount) throws DataHandleException, SQLModelException {
        boolean operatorResult = false;
        if (dtoSet != null && dtoSet.getSize() > 0) {
            SQLModel sqlModel = new SQLModel();
            ImportSpareAssetsModelJt modelProducer = new ImportSpareAssetsModelJt(userAccount, null);
            for (int i = 0; i < dtoSet.getSize(); i++) {
                ImportSpareAssetsDTOJt eoDTO = (ImportSpareAssetsDTOJt) dtoSet.getDTO(i);
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
     * @param dtoSet      DTOSet
     * @param conn        Connection
     * @param userAccount SfUserDTO
     * @return boolean
     * @throws DataHandleException submitOrderDtl
     */
    private boolean submitOrderDtl(DTOSet dtoSet, Connection conn, SfUserDTO userAccount) throws DataHandleException {
        boolean operatorResult = false;
        try {
            if (dtoSet != null && dtoSet.getSize() > 0) {
                SQLModel sqlModel = new SQLModel();
                ImportSpareAssetsModelJt modelProducer = new ImportSpareAssetsModelJt(userAccount, null);
                for (int i = 0; i < dtoSet.getSize(); i++) {
                    ImportSpareAssetsDTOJt eoDTO = (ImportSpareAssetsDTOJt) dtoSet.getDTO(i);
                    //查找并获取ITEM_CODE，没有则新增
                    ImportSpareAssetsDAOJt ImObDAO = new ImportSpareAssetsDAOJt(userAccount, null, conn);
                    if (!StrUtil.isEmpty(eoDTO.getItemName()) && !StrUtil.isEmpty(eoDTO.getItemSpec())) {
                        String itemCode = ImObDAO.getItemCode(eoDTO.getItemName(), eoDTO.getItemSpec());
                        if (itemCode == null || itemCode.equals("")) {
                            itemCode = getNextItemCode(conn) + "";
                            ImObDAO.insertSystemItem(itemCode, eoDTO.getItemName(), eoDTO.getItemSpec(), eoDTO.getItemUnit());
                            ImObDAO.insertDistribute(itemCode, conn);
                        }
                        eoDTO.setItemCode(itemCode);
                    }
                    //获取ADDRESS_ID
                    if (!StrUtil.isEmpty(eoDTO.getWorkorderObjectCode())) {
                        String addressId = ImObDAO.getAddressId(eoDTO.getWorkorderObjectCode());
                        eoDTO.setAddressId(addressId);
                    }
                    //获取公司专业责任人EMPLOYEE_ID
                    if (!StrUtil.isEmpty(eoDTO.getSpecialityUser())) {
                        String employeeId = ImObDAO.getEmployeeId(eoDTO.getSpecialityUser());
                        eoDTO.setSpecialEmployeeId(employeeId);
                    }
                    //获取责任部门管理员EMPLOYEE_ID
                    if (!StrUtil.isEmpty(eoDTO.getResponsibilityUser())) {
                        String employeeId = ImObDAO.getEmployeeId(eoDTO.getResponsibilityUser());
                        eoDTO.setResponsEmployeeId(employeeId);
                    }
                    //获取责任部门编号
                    if (!StrUtil.isEmpty(eoDTO.getResponsibilityUser())) {
                        String deptCode = ImObDAO.getDeptCode(eoDTO.getResponsibilityUser());
                        eoDTO.setResponsibilityDept(deptCode);
                    }
                    //获取厂商ID
                    if (!StrUtil.isEmpty(eoDTO.getManufacturerId())) {
                        String manufacturerId = ImObDAO.getManufacturerId(eoDTO.getManufacturerId());
                        eoDTO.setManufacturerId(manufacturerId);
                    }
                    //eoDTO.setSystemid(getNextSystemId(conn));
                    modelProducer.setDTOParameter(eoDTO);

                    sqlModel = modelProducer.getDataCreateModel();
                    DBOperator.updateRecord(sqlModel, conn);
                }
            }
            operatorResult = true;
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        } catch (ContainerException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (QueryException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
        return operatorResult;
    }

    private int getNextSystemId(Connection conn) throws SQLException {
        SeqProducer seqProducer = new SeqProducer(conn);
        return seqProducer.getStrNextSeq("ETS_ITEM_INFO_S");
    }

    private int getNextItemCode(Connection conn) throws SQLException {
        SeqProducer seqProducer = new SeqProducer(conn);
        return seqProducer.getStrNextSeq("ETS_SYSTEM_ITEM_S");
	}
}
