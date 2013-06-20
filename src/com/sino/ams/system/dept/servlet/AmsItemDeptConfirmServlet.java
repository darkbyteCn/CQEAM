package com.sino.ams.system.dept.servlet;

import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.dto.Request2DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.exception.*;
import com.sino.base.db.conn.DBManager;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.dept.dao.AmsItemDeptConfirmDAO;
import com.sino.ams.system.dept.model.AmsItemDeptConfirmModel;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.bean.OptionProducer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2009-7-9
 * Time: 10:18:42
 * To change this template use File | Settings | File Templates.
 */
public class AmsItemDeptConfirmServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String msg="";
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            EtsItemInfoDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsItemInfoDTO.class.getName());
            dtoParameter = (EtsItemInfoDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            AmsItemDeptConfirmDAO etsItemMatchRecDAO = new AmsItemDeptConfirmDAO(user, dtoParameter, conn);
             	OptionProducer op = new OptionProducer(user, conn);
              	String allRoleOption = op.getAllOrganization(dtoParameter.getOrganizationId(),true);
				req.setAttribute(WebAttrConstant.ALL_ROLE_OPTION, allRoleOption);
//            String matchType = req.getParameter("matchType");
            if (action.equals("")) {

                    forwardURL = "/system/dept/itemDeptConfirm.jsp";

            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsItemDeptConfirmModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                 pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();


                    forwardURL = "/system/dept/itemDeptConfirm.jsp";

            } else if (action.equals(AMSActionConstant.MATCH_ACTION)) {
                  DTOSet dtos = new DTOSet();
                String ms="";
                String[] systemIds = req.getParameterValues("systemId");
                String []compyCode=req.getParameterValues("ouId");
                String []newDept=req.getParameterValues("newDept");
                String []deptCode=req.getParameterValues("deptCode");
                ms = etsItemMatchRecDAO.confirmRentAssets(systemIds,compyCode,newDept,deptCode);
                if(ms.equals("")){
                    forwardURL = "/servlet/com.sino.ams.system.dept.servlet.AmsItemDeptConfirmServlet?act="+WebActionConstant.QUERY_ACTION ;
                    msg="资产部门变更成功！";

                }else{
                   forwardURL = "/servlet/com.sino.ams.system.dept.servlet.AmsItemDeptConfirmServlet?act="+WebActionConstant.QUERY_ACTION ;
                    msg=ms+"资产部门变更未成功,请确认!";
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
        } catch (DataHandleException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }/*catch(CalendarException e){
           e.printLog();
              message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }*/catch(SQLModelException e){
            e.printLog();
              message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }  finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (msg.equals("")) {
                    forwarder.forwardView(forwardURL);
                } else {
                    forwarder.forwardView(forwardURL, msg);
                }
            //根据实际情况修改页面跳转代码。
        }
    }

}
