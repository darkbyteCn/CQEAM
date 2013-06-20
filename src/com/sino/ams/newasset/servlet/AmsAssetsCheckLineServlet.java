package com.sino.ams.newasset.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dao.AmsAssetsCheckLineDAO;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.ams.newasset.model.AmsAssetsCheckLineModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsAssetsCheckLineServlet</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsCheckLineServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class AmsAssetsCheckLineServlet extends BaseServlet {

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
            req2DTO.setDTOClassName(AmsAssetsCheckLineDTO.class.getName());
            AmsAssetsCheckLineDTO dtoParameter = (AmsAssetsCheckLineDTO)
                                                 req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            AmsAssetsCheckLineDAO amsAssetsCheckLineDAO = new
                    AmsAssetsCheckLineDAO(user, dtoParameter, conn);
            if (action.equals("")) {
                forwardURL = "com.sino.ams.newasset.servlet.AmsAssetsCheckLineServlet的翻页查询JSP页面，一般在类似于URLDefineList的常量接口中定义";
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsAssetsCheckLineModel(user,
                        dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                forwardURL = "com.sino.ams.newasset.servlet.AmsAssetsCheckLineServlet的翻页查询JSP页面，一般在类似于URLDefineList的常量接口中定义";
            } else if (action.equals(AssetsActionConstant.NEW_ACTION)) {
                AmsAssetsCheckLineDTO amsAssetsCheckLine = (
                        AmsAssetsCheckLineDTO) req.getAttribute(
                        "获取因为失败而保持的数据，请根据实际情况修改");
                if (amsAssetsCheckLine == null) {
                    amsAssetsCheckLine = dtoParameter; //表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO的构造函数确定
                }
                req.setAttribute("详细数据属性，请根据实际情况修改", amsAssetsCheckLine);
                forwardURL = "com.sino.ams.newasset.servlet.AmsAssetsCheckLineServlet详细数据JSP页面，一般在类似于URLDefineList的常量接口中定义";
            } else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
                amsAssetsCheckLineDAO.setDTOClassName(AmsAssetsCheckLineDTO.class.
                        getName());
                AmsAssetsCheckLineDTO amsAssetsCheckLine = (
                        AmsAssetsCheckLineDTO) amsAssetsCheckLineDAO.
                        getDataByPrimaryKey();
                if (amsAssetsCheckLine == null) {
                    amsAssetsCheckLine = new AmsAssetsCheckLineDTO();
                    message = getMessage(AssetsMessageKeys.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("详细数据属性，请根据实际情况修改", amsAssetsCheckLine);
                forwardURL = "com.sino.ams.newasset.servlet.AmsAssetsCheckLineServlet详细数据JSP页面，一般在类似于URLDefineList的常量接口中定义";
            } else if (action.equals(AssetsActionConstant.CREATE_ACTION)) {
                amsAssetsCheckLineDAO.createData();
                forwardURL = "可再次执行com.sino.ams.newasset.servlet.AmsAssetsCheckLineServlet的QUERY_ACTION，请根据实际情况确定";
            } else if (action.equals(AssetsActionConstant.UPDATE_ACTION)) {
                amsAssetsCheckLineDAO.updateData();
                forwardURL = "可再次执行com.sino.ams.newasset.servlet.AmsAssetsCheckLineServlet的QUERY_ACTION，请根据实际情况确定";
            } else if (action.equals(AssetsActionConstant.DELETE_ACTION)) {
                amsAssetsCheckLineDAO.deleteData();
                forwardURL = "可再次执行com.sino.ams.newasset.servlet.AmsAssetsCheckLineServlet的QUERY_ACTION，请根据实际情况确定";
            } else {
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
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException ex) {
            ex.printLog();
            //请根据实际情况处理消息
            forwardURL = "保持界面录入的数据，返回到原页面，并显示上面给出的消息";
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }
    }
}
