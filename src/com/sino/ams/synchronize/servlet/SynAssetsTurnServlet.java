package com.sino.ams.synchronize.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.synchronize.dao.SynAssetsTurnDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-11-18
 * Time: 15:24:11
 * To change this template use File | Settings | File Templates.
 */
public class SynAssetsTurnServlet extends BaseServlet {
    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        boolean synSuccess = false;
        String totalCount = "0";
        long resumeTime = 0;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
            AmsAssetsAddressVDTO dtoParameter = (AmsAssetsAddressVDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            SynAssetsTurnDAO dao = new SynAssetsTurnDAO(user, dtoParameter, conn);
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String opt = optProducer.getAllOu(dtoParameter.getOrganizationId());
            dtoParameter.setOrgOption(opt);
            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = AssetsURLList.ASSETS_TURN_PKG;
            } else if (action.equals(WebActionConstant.SYSCHRONIZE_ACTION)) {
                long start = System.currentTimeMillis();
                String orgId = StrUtil.nullToString(dtoParameter.getOrganizationId());
                String projectNum = dtoParameter.getSegment1();
                totalCount = dao.getDataCount(orgId, projectNum);
                synSuccess = dao.synTurnInfo(orgId, projectNum);                
                resumeTime = System.currentTimeMillis() - start;
                message=new Message();
                if (synSuccess) {
                    message.setMessageValue("同步"+(totalCount)+"条记录，耗时"+resumeTime+"毫秒");
                } else {
                    message.setMessageValue("同步失败");
                }

                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = AssetsURLList.ASSETS_TURN_PKG;
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
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
        } catch (SQLModelException ex) {
			ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException ex) {
			ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);

//            DBManager.closeDBConnection(conn);
//            setHandleMessage(req, message);
//            res.setContentType("text/html;charset=GBK");
//            PrintWriter out = res.getWriter();
//            String outPutStr = "对不起,匹配过程中出错了,请通知相关人员,谢谢!";
//            if (synSuccess) {
//                outPutStr = "一共匹配了<font color='red'>" + "111" + "</font>条记录!";
//            }
//            out.println("<input type=\"hidden\" name=\"retMsg\" value=\"" + outPutStr + "\">");
//            out.flush();
//            out.close();
        }
    }
}
