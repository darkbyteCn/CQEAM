package com.sino.ams.yearchecktaskmanager.servlet;



import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dao.EtsFaAssetsDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dao.AssetsYearCheckQueryDAO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskQueryDTO;
import com.sino.ams.yearchecktaskmanager.model.AssetsYearCheckQueryModel;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: EtsFaAssetsServlet</p>
 * <p>Description:程序自动生成服务程序“EtsFaAssetsServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AssetsYearCheckQueryServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AssetsYearCheckTaskQueryDTO.class.getName());
            AssetsYearCheckTaskQueryDTO dto = (AssetsYearCheckTaskQueryDTO) req2DTO.getDTO(req);
            String action = dto.getAct();
            conn = getDBConnection(req);
            AssetsYearCheckQueryDAO assetsDAO = new AssetsYearCheckQueryDAO(user, dto, conn);
            assetsDAO.setServletConfig(getServletConfig(req));
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String taskType=req.getParameter("TaskType");
            if(taskType==null){
            	taskType="";
            }
            if(!taskType.isEmpty()){
            	dto.setTaskType(taskType);
            }
            if (action.equals("")) {
                dto.setCalPattern(LINE_PATTERN);
                AssetsYearCheckQueryModel sqlProducer = new AssetsYearCheckQueryModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setPageSize(21);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.setCountPages(true);
                pageDAO.produceWebData();
                dto.setCalPattern(LINE_PATTERN);
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/yearchecktaskmanager/assetsYearCheckQuery.jsp";
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
            	AssetsYearCheckQueryModel sqlProducer = new AssetsYearCheckQueryModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setPageSize(21);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.setCountPages(true);
                pageDAO.produceWebData();
                dto.setCalPattern(LINE_PATTERN);
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/yearchecktaskmanager/assetsYearCheckQuery.jsp";
            }  else {
                message = getMessage(AssetsMessageKeys.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (ContainerException e) {
            e.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
        }
    }
}
