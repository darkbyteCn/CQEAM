package com.sino.ams.workorder.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.WorkPersonDAO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.WorkPersonModel;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-1-18
 * Time: 10:03:45
 * To change this template use File | Settings | File Templates.
 */
public class WorkPersonServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        PrintWriter pw = null;
        JSONArray retArray = new JSONArray();
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            EtsWorkorderDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsWorkorderDTO.class.getName());
            dtoParameter = (EtsWorkorderDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            WorkPersonDAO workPersonDAO = new WorkPersonDAO(user, dtoParameter, conn);
            OptionProducer prd = new OptionProducer(user, conn);
            String status = prd.getDictOption(DictConstant.WORKORDER_STATUS, String.valueOf(dtoParameter.getWorkorderFlag()));
            req.setAttribute(WebAttrConstant.PLAN_STATUS_OPTION, status);
            if (action.equals("")) {
                forwardURL = URLDefineList.WOERK_PERSON_QUERY;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new WorkPersonModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                forwardURL = URLDefineList.WOERK_PERSON_QUERY;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
//				EtsWorkorderDTO amsRentDeadline = (EtsWorkorderDTO)req.getAttribute("详细数据属性，请根据实际情况修改");
//				if(amsRentDeadline == null){
//					amsRentDeadline= dtoParameter;
//				}
                dtoParameter = new EtsWorkorderDTO();
                req.setAttribute(WebAttrConstant.AMS_RENT_DEADLINE_DTO, dtoParameter);
                forwardURL = URLDefineList.WOERK_PERSON_QUERY;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                workPersonDAO.setDTOClassName(EtsWorkorderDTO.class.getName());
                EtsWorkorderDTO amsRentDeadline = (EtsWorkorderDTO) workPersonDAO.getDataByPrimaryKey();
                amsRentDeadline.setCalPattern(CalendarConstant.LINE_PATTERN);
                if (amsRentDeadline == null) {
                    amsRentDeadline = new EtsWorkorderDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute(WebAttrConstant.AMS_RENT_DEADLINE_DTO, amsRentDeadline);
                forwardURL = URLDefineList.WOERK_PERSON_QUERY;
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {      //do_save
                workPersonDAO.setServletConfig(getServletConfig(req));
                boolean operateResult = true;
//                boolean operateResult = amsRentDeadlineDAO.createData();
                workPersonDAO.createData();
                message = workPersonDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    forwardURL = URLDefineList.NOTE_QUERY_PAGE;
                } else {
                    req.setAttribute(WebAttrConstant.AMS_RENT_DEADLINE_DTO, dtoParameter);
                    forwardURL = URLDefineList.WOERK_PERSON_QUERY;
                }
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {    //进行重新分配执行人操作
                String[] systemids = req.getParameterValues("subCheck");
                String implementBy = StrUtil.nullToString(req.getParameter("implementBy"));
                WorkPersonModel workPersonModel = new WorkPersonModel(user, dtoParameter);
                SQLModel sqlModel = workPersonModel.getUpdateTmpDataModel(systemids, implementBy);
                DBOperator.updateRecord(sqlModel, conn);
                message = getMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
                message.addParameterValue("工单执行人");
                forwardURL = URLDefineList.DISTRI_AGAIN;
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
                boolean operateResult = true;
//                boolean operateResult = amsRentDeadlineDAO.deleteData();
                workPersonDAO.deleteData();
                message = workPersonDAO.getMessage();
                message.setIsError(!operateResult);
                forwardURL = URLDefineList.NOTE_QUERY_PAGE;
            } else if (action.equals("query")) {  //查询页面
                forwardURL = URLDefineList.WOERK_PERSON_QUERY;
            } else if (action.equals("query2")) {  //工单分配查询操作

                WorkPersonModel workPersonModel = new WorkPersonModel(user, dtoParameter);
                SQLModel sqlModel = workPersonModel.getPersonQueryModel();
                WebPageView webPageView = new WebPageView(req, conn);
                webPageView.produceWebData(sqlModel);
                forwardURL = URLDefineList.DISTRI_AGAIN;
            } else if (action.equals(WebActionConstant.CHECK_ACTION)) {  //选择部门，执行人页面
                String groupId = StrUtil.nullToString(req.getParameter("groupId"));
                OptionProducer op = new OptionProducer(user, conn);
                String groupAttr = op.getAllGroup(groupId, user.getOrganizationId(), false, true);
                String userOfGroup = op.getUsersOfGroup(StrUtil.strToInt(groupId), "");
                req.setAttribute("userOfGroup", userOfGroup);
                req.setAttribute("GROUPATTR", groupAttr);
                forwardURL = URLDefineList.CONFIRM_IMPLEMENT;
            } else if (action.equals("implement")) {
                String dept = StrUtil.nullToString(req.getParameter("dept"));
                res.setContentType("text/xml;charset=GBK");
                pw = res.getWriter();
                WorkPersonModel workPersonModel = new WorkPersonModel(user, dtoParameter);
                SQLModel sqlModel = workPersonModel.getUsersOfGroup(dept);
                SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
                simpleQuery.executeQuery();
                if (simpleQuery.hasResult()) {
                    RowSet rows = simpleQuery.getSearchResult();
                    Row row = null;
                    if (rows != null && rows.getSize() > 0) {
                        for (int i = 0; i < rows.getSize(); i++) {
                            row = rows.getRow(i);
                            retArray.put(i, row.getValue("USER_ID").toString() + "$" + row.getValue("USERNAME"));
                        }
                    }
                }
            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
                File file = workPersonDAO.getExportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
        } catch (JSONException e) {
            Logger.logError(e);
            retArray.put("ERROR");
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (ContainerException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.CONTAINER_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLModelException e) {
            e.printLog();
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException e) {
            e.printLog();
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (!forwardURL.equals("")) {
                forwarder.forwardView(forwardURL);
            } else {
                pw.print(retArray.toString());
                pw.flush();
                pw.close();
            }
        }
    }
}
