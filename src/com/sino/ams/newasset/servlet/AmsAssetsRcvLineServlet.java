package com.sino.ams.newasset.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.dao.AmsAssetsRcvLineDAO;
import com.sino.ams.newasset.dto.AmsAssetsRcvLineDTO;
import com.sino.ams.newasset.model.AmsAssetsRcvLineModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
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
 * <p>Title: AmsAssetsRcvLineServlet</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsRcvLineServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsAssetsRcvLineServlet extends BaseServlet {

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
            req2DTO.setDTOClassName(AmsAssetsRcvLineDTO.class.getName());
            AmsAssetsRcvLineDTO dtoParameter = (AmsAssetsRcvLineDTO) req2DTO.
                                               getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            AmsAssetsRcvLineDAO amsAssetsRcvLineDAO = new AmsAssetsRcvLineDAO(
                    user, dtoParameter, conn);
            if (action.equals("")) {
                forwardURL = "com.sino.ams.newasset.servlet.AmsAssetsRcvLineServlet的翻页查询JSP页面，一般在类似于URLDefineList的常量接口中定义";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsAssetsRcvLineModel(user,
                        dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                forwardURL = "com.sino.ams.newasset.servlet.AmsAssetsRcvLineServlet的翻页查询JSP页面，一般在类似于URLDefineList的常量接口中定义";
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                AmsAssetsRcvLineDTO amsAssetsRcvLine = (AmsAssetsRcvLineDTO)
                        req.getAttribute("获取因为失败而保持的数据，请根据实际情况修改");
                if (amsAssetsRcvLine == null) {
                    amsAssetsRcvLine = dtoParameter; //表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.newasset.dto.AmsAssetsRcvLineDTO的构造函数确定
                }
                req.setAttribute("详细数据属性，请根据实际情况修改", amsAssetsRcvLine);
                forwardURL = "com.sino.ams.newasset.servlet.AmsAssetsRcvLineServlet详细数据JSP页面，一般在类似于URLDefineList的常量接口中定义";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                amsAssetsRcvLineDAO.setDTOClassName(AmsAssetsRcvLineDTO.class.
                        getName());
                AmsAssetsRcvLineDTO amsAssetsRcvLine = (AmsAssetsRcvLineDTO)
                        amsAssetsRcvLineDAO.getDataByPrimaryKey();
                if (amsAssetsRcvLine == null) {
                    amsAssetsRcvLine = new AmsAssetsRcvLineDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("详细数据属性，请根据实际情况修改", amsAssetsRcvLine);
                forwardURL = "com.sino.ams.newasset.servlet.AmsAssetsRcvLineServlet详细数据JSP页面，一般在类似于URLDefineList的常量接口中定义";
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
                amsAssetsRcvLineDAO.createData();
                forwardURL = "可再次执行com.sino.ams.newasset.servlet.AmsAssetsRcvLineServlet的QUERY_ACTION，请根据实际情况确定";
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
                amsAssetsRcvLineDAO.updateData();
                forwardURL = "可再次执行com.sino.ams.newasset.servlet.AmsAssetsRcvLineServlet的QUERY_ACTION，请根据实际情况确定";
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
                amsAssetsRcvLineDAO.deleteData();
                forwardURL = "可再次执行com.sino.ams.newasset.servlet.AmsAssetsRcvLineServlet的QUERY_ACTION，请根据实际情况确定";
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
