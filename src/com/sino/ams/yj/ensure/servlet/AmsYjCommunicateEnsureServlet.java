package com.sino.ams.yj.ensure.servlet;

import java.sql.Connection;
import java.io.IOException;
import java.io.File;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.*;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.yj.ensure.dto.AmsYjCommunicateEnsureDTO;
import com.sino.ams.yj.ensure.model.AmsYjCommunicateEnsureModel;
import com.sino.ams.yj.ensure.dao.AmsYjCommunicateEnsureDAO;
import com.sino.ams.yj.util.YjManagerUtil;
import com.sino.ams.yj.constant.YJURLDefineList;
import com.sino.ams.yj.constant.YJWebAttribute;
import com.sino.ams.bean.OptionProducer;

/**
 * <p>Title: AmsYjCommunicateEnsureServlet</p>
 * <p>Description:程序自动生成服务程序“AmsYjCommunicateEnsureServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-应急通信保障情况
 */

public class AmsYjCommunicateEnsureServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsYjCommunicateEnsureDTO.class.getName());
            AmsYjCommunicateEnsureDTO dtoParameter = (AmsYjCommunicateEnsureDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            AmsYjCommunicateEnsureDAO amsYjCommunicateEnsureDAO = new AmsYjCommunicateEnsureDAO(user, dtoParameter, conn);
            OptionProducer opp = new OptionProducer(user, conn);
            if (action.equals("")) {
                req.setAttribute(YJWebAttribute.ORG_OPTION, opp.getAllOrganizationYj(user.getOrganizationId(), true));
                forwardURL = YJURLDefineList.ensureList;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                req.setAttribute(YJWebAttribute.ORG_OPTION, opp.getAllOrganizationYj(dtoParameter.getOrganizationId(), true));
                BaseSQLProducer sqlProducer = new AmsYjCommunicateEnsureModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                forwardURL = YJURLDefineList.ensureList;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                AmsYjCommunicateEnsureDTO amsYjCommunicateEnsure = new AmsYjCommunicateEnsureDTO();
                amsYjCommunicateEnsure.setOrgOpt(opp.getAllOrganizationYj(user.getOrganizationId(), true));
                req.setAttribute("ENSURE", amsYjCommunicateEnsure);
                forwardURL = YJURLDefineList.ensureDetail;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                amsYjCommunicateEnsureDAO.setDTOClassName(AmsYjCommunicateEnsureDTO.class.getName());
                AmsYjCommunicateEnsureDTO amsYjCommunicateEnsure = (AmsYjCommunicateEnsureDTO) amsYjCommunicateEnsureDAO.getDataByPrimaryKey();
                if (amsYjCommunicateEnsure == null) {
                    amsYjCommunicateEnsure = new AmsYjCommunicateEnsureDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
//                    message.setIsError(true);
                }
                amsYjCommunicateEnsure.setOrgOpt(opp.getAllOrganizationYj(amsYjCommunicateEnsure.getOrganizationId(), true));
                req.setAttribute("ENSURE", amsYjCommunicateEnsure);
                forwardURL = YJURLDefineList.ensureDetail;
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
                amsYjCommunicateEnsureDAO.createData();
                forwardURL = "/servlet/com.sino.ams.yj.ensure.servlet.AmsYjCommunicateEnsureServlet?act=" + WebActionConstant.QUERY_ACTION;
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
                amsYjCommunicateEnsureDAO.updateData();
                forwardURL = "/servlet/com.sino.ams.yj.ensure.servlet.AmsYjCommunicateEnsureServlet?act=" + WebActionConstant.DETAIL_ACTION + "&communicateId=" + dtoParameter.getCommunicateId();
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
                String[] ids = req.getParameterValues("communicateIds");
                if (ids != null) {
                    String ComvanIds = amsYjCommunicateEnsureDAO.appendIntComvanId(ids, ","); 
                    amsYjCommunicateEnsureDAO.deleteAllData(ComvanIds);
                }
                message = new Message();
//                message.setIsError(true);
                message.setMessageValue("信息删除成功！");
                forwardURL = "/servlet/com.sino.ams.yj.ensure.servlet.AmsYjCommunicateEnsureServlet?act=" + WebActionConstant.QUERY_ACTION;
            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {
            	boolean isNew= StrUtil.isEmpty(dtoParameter.getCommunicateId());
            	YjManagerUtil yjUtil= new YjManagerUtil(user, conn);
                if (isNew) {
                    dtoParameter.setCommunicateId(String.valueOf(yjUtil.getYjManagerMax("AMS_YJ_COMMUNICATE_ENSURE_SEQ")));
                }
                boolean flag = amsYjCommunicateEnsureDAO.saveDate(isNew);
                if (!flag) {
                    dtoParameter.setCommunicateId("");
                }
                message = new Message();
                message.setIsError(!flag);   
                if (flag) {
                    message.setMessageValue("信息保存成功！");
                } else {
                    message.setMessageValue("信息保存失败！");
                }
                req.setAttribute("ENSURE", dtoParameter);
                if (flag) {
                    forwardURL = "/servlet/com.sino.ams.yj.ensure.servlet.AmsYjCommunicateEnsureServlet?act=" + WebActionConstant.DETAIL_ACTION + "&communicateId=" + dtoParameter.getCommunicateId();
                }else{
                    forwardURL = YJURLDefineList.ensureDetail; 
                }
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
                File file = amsYjCommunicateEnsureDAO.getExportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals("EXP")) {
                File file = amsYjCommunicateEnsureDAO.getExpFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
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
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException e) {
            e.printLog();
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLModelException e) {
            e.printLog();  //To change body of catch statement use File | Settings | File Templates.
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } /*catch (SQLException e) {
            Logger.logError(e);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } */finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }
    }
}