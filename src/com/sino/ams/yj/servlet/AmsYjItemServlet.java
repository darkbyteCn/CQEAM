package com.sino.ams.yj.servlet;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yj.dao.AmsYjItemDAO;
import com.sino.ams.yj.dto.AmsYjItemDTO;
import com.sino.ams.yj.model.AmsYjItemModel;
import com.sino.ams.yj.util.YjManagerUtil;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

/**
 * <p>Title: AmsYjItemServlet</p>
 * <p>Description:程序自动生成服务程序“AmsYjItemServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-应急保障装备名称维护   000
 */

public class AmsYjItemServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsYjItemDTO.class.getName());
            AmsYjItemDTO dtoParameter = (AmsYjItemDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            AmsYjItemDAO amsYjItemDAO = new AmsYjItemDAO(user, dtoParameter, conn);
            if (action.equals("")) {
                forwardURL = "/yj/yjItemSearch.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsYjItemModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                forwardURL = "/yj/yjItemSearch.jsp";
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                AmsYjItemDTO amsYjItem = new AmsYjItemDTO();
                req.setAttribute("AMS_YJ_ITEM", amsYjItem);
                forwardURL = "/yj/yjItemDetail.jsp";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                amsYjItemDAO.setDTOClassName(AmsYjItemDTO.class.getName());
                AmsYjItemDTO amsYjItem = (AmsYjItemDTO) amsYjItemDAO.getDataByPrimaryKey();
                if (amsYjItem == null) {
                    amsYjItem = new AmsYjItemDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("AMS_YJ_ITEM", amsYjItem);
                forwardURL = "/yj/yjItemDetail.jsp";
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
                boolean isNew = StrUtil.isEmpty(dtoParameter.getItemCode());
              	YjManagerUtil yjUtil= new YjManagerUtil(user, conn);
                if (isNew) {
                    dtoParameter.setItemCode(String.valueOf(yjUtil.getYjManagerMax("AMS_YJ_ITEM_SEQ")));
                }
                amsYjItemDAO.createData();
                forwardURL = "/servlet/com.sino.ams.yj.servlet.AmsYjItemServlet?act=" + WebActionConstant.QUERY_ACTION + "&itemCode=" + dtoParameter.getItemCode();
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
                amsYjItemDAO.updateData();
                forwardURL = "/servlet/com.sino.ams.yj.servlet.AmsYjItemServlet?act=" + WebActionConstant.QUERY_ACTION + "&itemCode=" + dtoParameter.getItemCode();
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {       //失效
                amsYjItemDAO.deleteData();
                forwardURL = "/servlet/com.sino.ams.yj.servlet.AmsYjItemServlet?act=" + WebActionConstant.QUERY_ACTION;
            } else if (action.equals(AMSActionConstant.INURE_ACTION)) {       //生效
                String amsYjItem = "";
                amsYjItemDAO.enableItem(amsYjItem);
                forwardURL = "/servlet/com.sino.ams.yj.servlet.AmsYjItemServlet?act=" + WebActionConstant.QUERY_ACTION;
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出到Excel
                File file = amsYjItemDAO.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals("verifyItemName")) {
                String itemName = req.getParameter("itemName");
                boolean success = amsYjItemDAO.doVerify(itemName);
                PrintWriter out = res.getWriter();
                if (success) {
                    out.print("Y");
                }
                out.flush();
                out.close();
            }else {
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
        } catch (WebFileDownException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }
    }
}