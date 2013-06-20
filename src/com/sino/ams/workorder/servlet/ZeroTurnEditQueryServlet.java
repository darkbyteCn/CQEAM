package com.sino.ams.workorder.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.ZeroTurnEditQueryDAO;
import com.sino.ams.workorder.dao.ZeroTurnHeaderDAO;
import com.sino.ams.workorder.dto.ZeroTurnHeaderDTO;
import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.ams.workorder.model.ZeroTurnEditQueryModel;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
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
import com.sybase.jdbc3.a.a.e;

public class ZeroTurnEditQueryServlet extends BaseServlet{
	
	@Override
    public void performTask(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        String action = StrUtil.nullToString(req.getParameter("act"));
        try {
            conn = DBManager.getDBConnection();
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);// 从session中获取数据，根据实际情况自行修改。
            ZeroTurnLineDTO dtoParameter = getDTOFromReq(req);
            ZeroTurnEditQueryDAO dao=new ZeroTurnEditQueryDAO(user, dtoParameter, conn);
            req.setAttribute("OID",user.getOrganizationId());
            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = "/workorder/zeroTurnEditQuery.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                BaseSQLProducer sqlProducer = new ZeroTurnEditQueryModel(user,
                        dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                forwardURL = "/workorder/zeroTurnEditQuery.jsp";
            } else if (action.equals("update")) {
            	String oneBarcode=req.getParameter("oneBarcode");
            	RowSet rs = dao.getDate(oneBarcode);
            	req.setAttribute("ROW_SET", rs);
                forwardURL = "/workorder/zeroTurnEditDate.jsp";
            } else if (action.equals(AssetsActionConstant.SAVE_ACTION)) {
            	//ZeroTurnHeaderDTO dto=new ZeroTurnHeaderDTO();
            	//ZeroTurnHeaderDAO headerDAO = new ZeroTurnHeaderDAO(user, dto, conn);
        		//boolean bl=headerDAO.validateImport(ds);
        		//if(bl){
            	boolean isC=true;
            	String isChangeObjectCode=req.getParameter("isChangeObjectCode");
            	if (isChangeObjectCode.trim().equals("1")) {
    				 isC=dao.updateEtsIntemInfo(dtoParameter);
				}
        		boolean bool=dao.saveDate(dtoParameter);
        		req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
        		if(bool&&isC){
        			forwardURL = "/servlet/com.sino.ams.workorder.servlet.ZeroTurnEditQueryServlet";
        			forwardURL += "?act=" + WebActionConstant.QUERY_ACTION;
        		}
//        		}else{
//        			message = new Message();
//                    message.setMessageValue("输入数据有误。");
//                    message.setIsError(true);
//        		}
            }else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolException e) {
            message = getMessage(MsgKeyConstant.INVALID_REQ);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException e) {
            message = getMessage(MsgKeyConstant.INVALID_REQ);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException e) {
            message = getMessage(MsgKeyConstant.INVALID_REQ);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }

    }

    public ZeroTurnLineDTO getDTOFromReq(HttpServletRequest req)
            throws DTOException {
        Request2DTO req2DTO = new Request2DTO();
        req2DTO.setDTOClassName(ZeroTurnLineDTO.class.getName());
        return (ZeroTurnLineDTO) req2DTO.getDTO(req);
    }

}
