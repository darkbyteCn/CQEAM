package com.sino.ams.workorder.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.PersonOrderDAO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.PersonOrderModel;
import com.sino.ams.workorder.model.WorkPersonModel;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * User: zhoujs
 * Date: 2008-1-9
 * Time: 11:39:27
 * Function:个人工单清单签收、签收、重新分配执行人
 */
public class PersonOrderServlet extends BaseServlet {

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
            PersonOrderDAO personDAO = new PersonOrderDAO(user, dtoParameter, conn);
            
            if (action.equals("")) {
                forwardURL = "/workorder/personOrder.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {//查询个人工单
                BaseSQLProducer sqlProducer = new PersonOrderModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                forwardURL = "/workorder/personOrder.jsp";
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
//				EtsWorkorderDTO amsRentDeadline = (EtsWorkorderDTO)req.getAttribute("详细数据属性，请根据实际情况修改");
//				if(amsRentDeadline == null){
//					amsRentDeadline= dtoParameter;
//				}
                dtoParameter = new EtsWorkorderDTO();
                req.setAttribute(WebAttrConstant.AMS_RENT_DEADLINE_DTO, dtoParameter);
                forwardURL = URLDefineList.WOERK_PERSON_QUERY;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                personDAO.setDTOClassName(EtsWorkorderDTO.class.getName());
                EtsWorkorderDTO amsRentDeadline = (EtsWorkorderDTO) personDAO.getDataByPrimaryKey();
                amsRentDeadline.setCalPattern(CalendarConstant.LINE_PATTERN);
                if (amsRentDeadline == null) {
                    amsRentDeadline = new EtsWorkorderDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute(WebAttrConstant.AMS_RENT_DEADLINE_DTO, amsRentDeadline);
                forwardURL = URLDefineList.WOERK_PERSON_QUERY;
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {      //do_save
                personDAO.setServletConfig(getServletConfig(req));
                boolean operateResult = true;
//                boolean operateResult = amsRentDeadlineDAO.createData();
                personDAO.createData();
                message = personDAO.getMessage();
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
                personDAO.deleteData();
                message = personDAO.getMessage();
                message.setIsError(!operateResult);
                forwardURL = URLDefineList.NOTE_QUERY_PAGE;
            } else if (action.equals("query")) {  //查询页面
                forwardURL = URLDefineList.DISTRI_AGAIN;
            } else if (action.equals("query2")) {  //工单分配查询操作

                WorkPersonModel workPersonModel = new WorkPersonModel(user, dtoParameter);
                SQLModel sqlModel = workPersonModel.getPersonQueryModel();
                WebPageView webPageView = new WebPageView(req,conn);
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
            } else if (action.equals("sign")) { //签收工单
               String[] workorders=req.getParameterValues("workorders");
                boolean  operateResult= personDAO.signOrders(workorders);
                if(operateResult){
                      forwardURL="/servlet/com.sino.ams.workorder.servlet.PersonOrderServlet?act="+WebActionConstant.QUERY_ACTION;
                }
                
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
//        } catch (JSONException e) {
//            Logger.logError(e);
//            retArray.put("ERROR");
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
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
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