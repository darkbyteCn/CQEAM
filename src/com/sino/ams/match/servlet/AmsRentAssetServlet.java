package com.sino.ams.match.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.match.dao.AmsRentAssetDAO;
import com.sino.ams.match.dto.EtsItemMatchRecDTO;
import com.sino.ams.match.model.AmsRentAssetModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
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
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-6-10
 * Time: 16:01:48
 * To change this template use File | Settings | File Templates.
 */
public class AmsRentAssetServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String msg="";
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            EtsItemMatchRecDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsItemMatchRecDTO.class.getName());
            dtoParameter = (EtsItemMatchRecDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            AmsRentAssetDAO etsItemMatchRecDAO = new AmsRentAssetDAO(user, dtoParameter, conn);
            String matchType = req.getParameter("matchType");
            if (action.equals("")) {
                if (matchType.equals(WebAttrConstant.MATCH_MODE_RENT_RET)) {      // 撤销租赁资产确认
                    forwardURL = "/match/amsRentAssetCancleConfirm.jsp";
                } else {
                    forwardURL = "/match/amsRentAssetconfirm.jsp";
                }
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsRentAssetModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                 pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();

                   if (matchType.equals(WebAttrConstant.MATCH_MODE_RENT_RET)) {      // 撤销租赁资产确认
                    forwardURL = "/match/amsRentAssetCancleConfirm.jsp";
                } else {
                    forwardURL = "/match/amsRentAssetconfirm.jsp";
                }
            } else if (action.equals(AMSActionConstant.MATCH_ACTION)) {
                  DTOSet dtos = new DTOSet();
                String[] systemIds = req.getParameterValues("systemId");
                String []startDate=req.getParameterValues("startDate");
                String []barcodes=req.getParameterValues("barcode1");
                etsItemMatchRecDAO.confirmRentAssets(systemIds,matchType,startDate,barcodes);
                if(matchType.equals("11")){

                    forwardURL = "/match/amsRentAssetconfirm.jsp" + "?matchType=" + matchType;
                    msg="租赁资产确认成功！";
                }   else {
                       forwardURL = "/match/amsRentAssetCancleConfirm.jsp" + "?matchType=" + matchType;
                    msg="租赁资产撤销成功！";
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
        }catch(CalendarException e){
           e.printLog();
              message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }catch(SQLModelException e){
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
