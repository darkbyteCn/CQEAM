package com.sino.ams.newSite.servlet;

import com.sino.ams.newSite.dao.EamAddressAddHeaderDAO;
import com.sino.ams.newSite.dto.EamAddressAddHDTO;
import com.sino.ams.newSite.dto.EamAddressAddLDTO;
import com.sino.ams.newSite.model.EamAddressAddHModel;
import com.sino.ams.newSite.util.ReadAddressExcel;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 作者 :wangzhipeng
 * @version 创建时间：Apr 12, 2011 11:57:18 AM 类说明:新增地点流程
 */
public class EamAddressAddServlet extends BaseServlet {

    private static final int startRowNum = 1;
    private static final int columnNum = 8;

    public void performTask(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            RequestParser reqPar = new RequestParser();
            reqPar.transData(req);

            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EamAddressAddHDTO.class.getName());
            EamAddressAddHDTO dto = (EamAddressAddHDTO) req2DTO.getDTO(req);
            String action = dto.getAct();
            ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(req);
            conn = getDBConnection(req);
            if (!dto.getApp_dataID().equals("")) {
                dto.setTransId(dto.getApp_dataID());
            }
            EamAddressAddHeaderDAO headerDAO = new EamAddressAddHeaderDAO(user, dto, conn);
            headerDAO.setServletConfig(servletConfig);

            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String option = optProducer.getAmsEmergentLevel(dto.getEmergentLevel());
            dto.setEmergentLevelOption(option);

            if (action.equals("") || action.equals(AssetsActionConstant.NEW_ACTION)) { // 0.审批页
                headerDAO.setDTOClassName(EamAddressAddHDTO.class.getName());
                EamAddressAddHDTO headerDTO = (EamAddressAddHDTO) headerDAO.getDataByPrimaryKey();
                if (headerDTO == null) {
                    headerDTO = dto;
                    headerDTO = fillData(headerDTO, user, conn);
                }
                headerDTO.setEmergentLevelOption(optProducer.getAmsEmergentLevel(headerDTO.getEmergentLevel()));
                headerDTO.setCalPattern(LINE_PATTERN);
                DTOSet lineDs = headerDAO.getLineData();
                req.setAttribute(AssetsWebAttributes.ADDRESS_HEAD_DATA, headerDTO); // 头信息
                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, lineDs);
                forwardURL = "/newasset/newSite/eamAddressAddEdit.jsp";
            } else if (action.equals(AssetsActionConstant.IMPORT_ACTION)) {
                dto.setTransStatus(AssetsDictConstant.SAVE_TEMP);
                String excel2 = req.getParameter("excel");
                DTOSet dtoSet = null;
                boolean parseError = false;
                try {
                    dto.setExcel(excel2);
                    dtoSet = readDataFrmExcel(req);
                } catch (Throwable ex) {
                    message = new Message();
                    message.setMessageValue("解析Excel数据失败");
                    message.setIsError(true);
                    parseError = true;
                }
                if(!parseError){
                    headerDAO.setDTOParameter(dto);
                    headerDAO.importOrder(dtoSet);                //暂存表单信息
                    message = headerDAO.getMessage();
                    dto = (EamAddressAddHDTO) headerDAO.getDTOParameter();
                    dtoSet = headerDAO.getImpDS(dto.getTransId());
                }
                req.setAttribute(AssetsWebAttributes.ADDRESS_HEAD_DATA, dto); // 头信息
                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, dtoSet);
                forwardURL = "/newasset/newSite/eamAddressAddEdit.jsp";
            } else if (action.equals("EMPTY_QUERY_ACTION")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/newasset/newSite/eamAddressAddList.jsp";
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new EamAddressAddHModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/newasset/newSite/eamAddressAddList.jsp";

            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
                File file = exportFile(user, dto, conn);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();

            } else if (action.equals(AssetsActionConstant.SUBMIT_ACTION)) { // 1.提交表单
                req2DTO.setDTOClassName(EamAddressAddLDTO.class.getName());
                req2DTO.setIgnoreFields(EamAddressAddHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
                headerDAO.setDTOParameter(dto);
                boolean Smsg = headerDAO.submitOrder(lineSet);
                message = headerDAO.getMessage();
                dto = (EamAddressAddHDTO) headerDAO.getDTOParameter();
                if (Smsg) {
                    forwardURL = "/servlet/com.sino.ams.newSite.servlet.EamAddressAddServlet?act="
                            + AssetsActionConstant.DETAIL_ACTION;
                    forwardURL += "&transId=" + dto.getTransId();
                } else {
                    forwardURL = "/servlet/com.sino.ams.newSite.servlet.EamAddressAddServlet?act="
                            + AssetsActionConstant.NEW_ACTION;
                }
            } else if (action.equals(AssetsActionConstant.SAVE_ACTION)) {     //1.保存表单
                req2DTO.setDTOClassName(EamAddressAddLDTO.class.getName());
                req2DTO.setIgnoreFields(EamAddressAddHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
                headerDAO.setDTOParameter(dto);
                boolean Smsg = headerDAO.saveOrder(lineSet);
                dto = (EamAddressAddHDTO) headerDAO.getDTOParameter();
                if (Smsg) {
                    forwardURL = "/servlet/com.sino.ams.newSite.servlet.EamAddressAddServlet?act=";
                    //+ AssetsActionConstant.DETAIL_ACTION;
                    forwardURL += "&transId=" + dto.getTransId();
                } else {
                    forwardURL = "/servlet/com.sino.ams.newSite.servlet.EamAddressAddServlet?act="
                            + AssetsActionConstant.NEW_ACTION;
                }
            } else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) { // 2.表单提交成功后显示页面
                headerDAO.setDTOClassName(EamAddressAddHDTO.class.getName());
                EamAddressAddHDTO headerDTO = (EamAddressAddHDTO) headerDAO.getDataByPrimaryKey();
                headerDTO.setCalPattern(LINE_PATTERN);
                DTOSet lineDs = headerDAO.getLineData();
                req.setAttribute(AssetsWebAttributes.ADDRESS_HEAD_DATA, headerDTO); // 头信息
                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, lineDs);
                forwardURL = "/newasset/newSite/eamAddressAddQuery.jsp";
            } else if (action.equals("REJECT_ACTION")) { //退回流程
                headerDAO.rejectOrder();
                message = headerDAO.getMessage();
                forwardURL = "/servlet/com.sino.ams.newSite.servlet.EamAddressAddServlet";
                forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                forwardURL += "&transId=" + dto.getTransId();
            } else if (action.equals(AssetsActionConstant.CANCEL_ACTION)) { //撤销暂存的单据
                boolean operateResult = headerDAO.cancelOrder();
                message = headerDAO.getMessage();
                forwardURL = "/servlet/com.sino.ams.newSite.servlet.EamAddressAddServlet";
                if (operateResult) {
                    forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                } else {
                    forwardURL += "?act=";
                }
                forwardURL += "&transId=" + dto.getTransId();
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (Throwable e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
            this.setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }

    /**
     * 功能:填充DTO数据 地址信息对象
     */
    private EamAddressAddHDTO fillData(EamAddressAddHDTO dto, SfUserDTO user, Connection conn) throws DTOException, QueryException,
            CalendarException {
        dto.setTransNo(AssetsWebAttributes.ORDER_AUTO_PROD); // 设置单据号
        dto.setCreatedBy(user.getUserId()); // 设置创建人Id
        dto.setCreatedByName(user.getUsername());
        dto.setDept(user.getDeptCode());
        dto.setDeptName(user.getDeptName()); // 部门名称
        dto.setTransType("地点维护单"); // 单据类型
        dto.setTransStatus(""); // 单据状态
        dto.setOrganizationId(StrUtil.strToInt(user.getCompanyCode())); // 公司Id
        dto.setOrganizationName(user.getCompany()); // 公司名称
        dto.setCurrCreationDate();
        return dto;
    }


    /**
     * 功能：读取Excel数据到DTOSet
     *
     * @param req 页面请求对象
     * @return DTOSet对象
     * @throws DTOException
     */
    private DTOSet readDataFrmExcel(HttpServletRequest req) throws DTOException {
        DTOSet dtoSet = null;
        try {
            String fileName = req.getParameter("excelPath");     //文件路径
            ReadAddressExcel xlsUtil = new ReadAddressExcel();
            xlsUtil.setFileName(fileName);
            xlsUtil.setNumberOfColumn(columnNum);      //设置列数8
            xlsUtil.setStartRowNum(startRowNum);       //开始行
            dtoSet = xlsUtil.readXls(0);
        } catch (IOException ex) {
            Logger.logError(ex);
            throw new DTOException(ex);
        }
        return dtoSet;
    }

    public File exportFile(SfUserDTO user, EamAddressAddHDTO dto, Connection conn) throws DataTransException {
        File file = null;
        try {
            DataTransfer transfer = null;
            BaseSQLProducer sqlProducer = new EamAddressAddHModel(user, dto); //OrderQueryModel
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String transType = StrUtil.nullToString(dto.getTransType());
            String fileName = "";
            fileName = "地点维护单据表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();

            fieldMap.put("TRANS_NO", "地点维护单号");
            fieldMap.put("TRANS_STATUS_DESC", "单据状态");
            fieldMap.put("ORGANIZATION_NAME", "地点维护公司");
            fieldMap.put("DEPT_NAME", "地点维护部门");
            fieldMap.put("CREATED_BY_NAME", "申请人");
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
