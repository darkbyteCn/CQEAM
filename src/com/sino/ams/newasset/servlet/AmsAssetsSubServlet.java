/**
 * 
 */
package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.newasset.dto.AmsAssetsSubDTO;
import com.sino.ams.newasset.model.AmsAssetsSubSearchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * @author dell
 *
 */
public class AmsAssetsSubServlet extends BaseServlet{
	 public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
         String forwardURL = "";
       Message message = SessionUtil.getMessage(req);
       String action = req.getParameter("act");
       action = StrUtil.nullToString(action);
       Connection conn = null;
        try {
        	AmsAssetsSubDTO dtoParameter = null;
           SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
           Request2DTO req2DTO = new Request2DTO();
           req2DTO.setDTOClassName(AmsAssetsSubDTO.class.getName());
           dtoParameter = (AmsAssetsSubDTO) req2DTO.getDTO(req);
           conn= DBManager.getDBConnection();

           if(action.equals("")){
               req.setAttribute(QueryConstant.QUERY_DTO,dtoParameter);
               forwardURL= URLDefineList.ASSETS_SUB_SEARCH;
           } else if(action.equals(WebActionConstant.QUERY_ACTION)){
               BaseSQLProducer sqlProducer = new AmsAssetsSubSearchModel(user, dtoParameter);
               PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
               pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
               pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO,dtoParameter);
               forwardURL=URLDefineList.ASSETS_SUB_SEARCH;
           } else{
               message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
           }


       } catch (PoolException e) {
           e.printLog();
           message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
       } catch (DTOException e) {
           e.printLog();
           message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
       } catch (QueryException e) {
           e.printLog();
           message = getMessage(MsgKeyConstant.QUERY_ERROR);
           message.setIsError(true);
           forwardURL= MessageConstant.MSG_PRC_SERVLET;
       }  finally {
           DBManager.closeDBConnection(conn);
           setHandleMessage(req, message);
           ServletForwarder forwarder = new ServletForwarder(req, res);
           forwarder.forwardView(forwardURL);
       }
   }

}
