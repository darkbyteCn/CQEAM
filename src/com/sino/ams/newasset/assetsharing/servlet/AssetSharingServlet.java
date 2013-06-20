package com.sino.ams.newasset.assetsharing.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.ams.newasset.assetsharing.constant.AssetSharingConstant;
import com.sino.ams.newasset.assetsharing.dao.AssetSharingDAO;
import com.sino.ams.newasset.assetsharing.dto.AssetSharingHeaderDTO;
import com.sino.ams.newasset.assetsharing.dto.AssetSharingLineDTO;
import com.sino.ams.newasset.assetsharing.model.AssetSharingModel;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.ApproveContentDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * 资源共享流程申请 SERVLET
 * @author xiaohua
 */
public class AssetSharingServlet extends BaseServlet {

    @Override
    public void performTask(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);

        Connection conn = null;
        boolean falg = false;
        Request2DTO req2DTO = new Request2DTO();
        AssetSharingDAO shareDao = null;
        try {
            req2DTO.setDTOClassName(AssetSharingHeaderDTO.class.getName());
            AssetSharingHeaderDTO headerDTO = (AssetSharingHeaderDTO) req2DTO.getDTO(req);
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            conn = getDBConnection(req);
            shareDao = new AssetSharingDAO(userAccount, headerDTO, conn);
            String action = headerDTO.getAct();

            AssetsOptProducer optProducer = new AssetsOptProducer(userAccount, conn);
            String emergentLevelOption = optProducer.getAmsEmergentLevel(headerDTO.getEmergentLevel());
            headerDTO.setEmergentLevelOption(emergentLevelOption);

            headerDTO.setTransType(AssetSharingConstant.ASSET_SHARE_CODE);

            if (AssetSharingConstant.SUBMIT_ACTION.equals(action)) {// 流程提交
                req2DTO.setDTOClassName(AssetSharingLineDTO.class.getName());
                req2DTO.setIgnoreFields(AssetSharingHeaderDTO.class);
                DTOSet lines = req2DTO.getDTOSet(req);
                shareDao.saveOrder(lines);
                message = shareDao.getMessage();
                forwardURL = AssetSharingConstant.ASSET_SHARE_SERVLET + "?act=VIEW_ACTION";
                forwardURL += "&transId=" + headerDTO.getTransId();

            } else if (AssetSharingConstant.SAVE_ACTION.equals(action)) {// 流程保存
                headerDTO.setTransStatus(AssetsDictConstant.SAVE_TEMP);
                DTOSet lines = null;
                if (AssetSharingConstant.ASSET_SHARE_TASK_NAME0.equals(headerDTO.getSf_taskname())) {// 填制资产共享申请单
                    req2DTO.setDTOClassName(AssetSharingLineDTO.class.getName());
                    req2DTO.setIgnoreFields(AssetSharingHeaderDTO.class);
                    lines = req2DTO.getDTOSet(req);
                    if (lines == null) {
                        lines = new DTOSet();
                    }
                }
                shareDao.saveOrder(lines);
                forwardURL = AssetSharingConstant.ASSET_SHARE_SERVLET + "?act=" + AssetSharingConstant.DETAIL_ACTION;
                forwardURL += "&transId=" + headerDTO.getTransId();
                message = shareDao.getMessage();
            } else if (AssetSharingConstant.ASSET_SHARE_DETAIL_ACTION.equals(action)) {// 流程查看
                if (headerDTO.getTransId().equals("") && !headerDTO.getApp_dataID().equals("")) {
                    headerDTO.setTransId(headerDTO.getApp_dataID());
                }
                shareDao.prodForm();
                if (message != null) {
                    shareDao.getMessage().setMessageValue(message.getMessageValue());
                }
                if (AssetSharingConstant.ASSET_SHARE_TASK_NAME0.equals(headerDTO.getSf_taskname())) {// 填制资产共享申请单
                    forwardURL = AssetSharingConstant.ASSET_SHARE_EDIT;// 编辑页面
                } else {
                    forwardURL = AssetSharingConstant.ASSET_SHARE_DETAIL;// 查看页面
                }
            } else if ("VIEW_ACTION".equals(action)) {// 流程查看
                if (headerDTO.getTransId().equals("") && !headerDTO.getApp_dataID().equals("")) {
                    headerDTO.setTransId(headerDTO.getApp_dataID());
                }
                shareDao.prodForm();
                if (message != null) {
                    shareDao.getMessage().setMessageValue(message.getMessageValue());
                }
                forwardURL = "/newasset/share/shareAssetsViewDetail.jsp";// 查看页面
            } else if (AssetSharingConstant.CANCEL_ACTION.equals(action)) {// 流程撤消
                headerDTO.setTransStatus(AssetsDictConstant.CANCELED);
                shareDao.cacelFlow();
                forwardURL = AssetSharingConstant.ASSET_SHARE_SERVLET + "?act=" + AssetSharingConstant.DETAIL_ACTION;
                forwardURL += "&transId=" + headerDTO.getTransId();
                message = shareDao.getMessage();
            } else if (AssetSharingConstant.REJECT_ACTION.equals(action)) {// 流程退回
                headerDTO.setTransStatus(AssetsDictConstant.REJECTED);
                shareDao.rejectFlow();
                message = shareDao.getMessage();
            } else if ("EDIT_ACTION".equals(action)) {// // 填制资产共享申请单
                if (!headerDTO.getApp_dataID().equals("")) {
                    headerDTO.setTransId(headerDTO.getApp_dataID());
                    shareDao.prodForm();
                } else {
                    shareDao.setInitInfo();
                }
                forwardURL = AssetSharingConstant.ASSET_SHARE_EDIT;

            } else if (AssetSharingConstant.QUERY_ACTION.equals(action)) {// 共享单据查询
                forwardURL = AssetSharingConstant.ASSET_SHARE_PAGEDQUERY;
                headerDTO.setTransStatus("");
                req.setAttribute("action", action);
                req.setAttribute("title", AssetSharingConstant.SHARE_TITLE1);
                pageQuery(req, conn, userAccount, headerDTO);

            } else if (AssetSharingConstant.QUERY_DETAIL.equals(action)) {// 共享单据查询明细
                shareDao.prodForm();
                String isPrint = req.getParameter("isPrint");
                req.setAttribute("isPrint", isPrint);
                forwardURL = AssetSharingConstant.ASSET_SHARE_PAGEDQUERY_DETAIL;
                req.setAttribute(AssetsWebAttributes.APPROVE_CONTENT,
                        ApproveContentDAO.getApproveContent(conn, headerDTO.getTransId(), AssetSharingConstant.ASSET_SHARE_TABLE_NAME));

            } else if (AssetSharingConstant.PRINT_QUERY_ACTION.equals(action)) {// 共享单据打印 查询
                forwardURL = AssetSharingConstant.ASSET_SHARE_PAGEDQUERY;
                req.setAttribute("action", action);
                req.setAttribute("isPrint", "true");
                headerDTO.setTransStatus("NOT_EMPTY");
                req.setAttribute("title", AssetSharingConstant.SHARE_TITLE2);
                pageQuery(req, conn, userAccount, headerDTO);
            } else if ("".equals(action)) {
                forwardURL = AssetSharingConstant.ASSET_SHARE_PAGEDQUERY;

                if (headerDTO.getTransStatus().equals("")) {
                    req.setAttribute("action", AssetSharingConstant.QUERY_ACTION);
                    req.setAttribute("title", AssetSharingConstant.SHARE_TITLE1);
                } else {
                    req.setAttribute("action", AssetSharingConstant.PRINT_QUERY_ACTION);
                    req.setAttribute("title", AssetSharingConstant.SHARE_TITLE2);
                }
                
            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
                File file = exportFile(userAccount, headerDTO, conn);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
                
            } else {

                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);

            }
            req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException e) {
            e.printLog();
        } catch (SQLException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
        } catch (QueryException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
        } catch (Exception e) {
            Logger.logError(e);
            message = getMessage("ERROR");
            message.setIsError(true);
        } finally {

            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);

            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (falg) {
                ;
            } else {
                if (!StrUtil.isEmpty(forwardURL)) {

                    forwarder.forwardView(forwardURL);
                } else {
                    forwarder.closeParent(forwardURL);
                }
            }
        }
    }

    private void pageQuery(HttpServletRequest req, Connection conn, SfUserDTO userAccount, AssetSharingHeaderDTO headerDTO) throws QueryException {
        BaseSQLProducer sqlProducer = new AssetSharingModel(userAccount, headerDTO);
        PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
        pageDAO.setCalPattern(LINE_PATTERN);
        pageDAO.produceWebData();
    }

    
    public File exportFile(SfUserDTO user, AssetSharingHeaderDTO dto, Connection conn) throws DataTransException {
        File file = null;
        try {
            DataTransfer transfer = null;
            BaseSQLProducer sqlProducer = new AssetSharingModel(user, dto); //OrderQueryModel
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);            
            String transType = StrUtil.nullToString(dto.getTransType());
            String fileName = "";
            if (transType.equals("ASS-SHARE")) {
            	fileName = "共享单据表.xls";
            }
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();

            if (transType.equals("ASS-SHARE")) {
                fieldMap.put("TRANS_NO", "共享单号");
                fieldMap.put("DEPT_NAME", "申请部门");
            }
        	fieldMap.put("TRANS_STATUS_DESC", "单据状态");
            fieldMap.put("USER_NAME", "申请人");
            fieldMap.put("CREATION_DATE", "创建日期");

            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle(fileName);
            custData.setReportPerson(user.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            //设置分页显示
            TransferFactory factory = new TransferFactory();
            transfer = factory.getTransfer(rule);
            transfer.transData();
            file = (File) transfer.getTransResult();
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataTransException(ex);
        }
        return file;
    }
}
