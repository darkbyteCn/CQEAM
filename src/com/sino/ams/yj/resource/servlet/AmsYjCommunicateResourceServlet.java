package com.sino.ams.yj.resource.servlet;

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
import com.sino.base.constant.message.MsgKeyConstant;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

import com.sino.ams.yj.resource.dto.AmsYjCommunicateResourceDTO;
import com.sino.ams.yj.resource.model.AmsYjCommunicateResourceModel;
import com.sino.ams.yj.resource.dao.AmsYjCommunicateResourceDAO;
import com.sino.ams.yj.util.YjManagerUtil;
import com.sino.ams.yj.constant.YJWebAttribute;
import com.sino.ams.yj.constant.YJURLDefineList;
import com.sino.ams.yj.bean.YjOptionProducer;
import com.sino.ams.bean.OptionProducer;

/**
 * <p>Title: AmsYjCommunicateResourceServlet</p>
 * <p>Description:程序自动生成服务程序“AmsYjCommunicateResourceServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-战备应急通信资源
 */

public class AmsYjCommunicateResourceServlet extends BaseServlet {

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
            req2DTO.setDTOClassName(AmsYjCommunicateResourceDTO.class.getName());
            AmsYjCommunicateResourceDTO dtoParameter = (AmsYjCommunicateResourceDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            AmsYjCommunicateResourceDAO amsYjCommunicateResourceDAO = new AmsYjCommunicateResourceDAO(user, dtoParameter, conn);
            OptionProducer opp = new OptionProducer(user, conn);
            YjOptionProducer yop = new YjOptionProducer(user, conn);

            if (action.equals("")) {
                req.setAttribute(YJWebAttribute.ORG_OPTION, opp.getAllOrganizationYj(user.getOrganizationId(), true));
                forwardURL = YJURLDefineList.resourceList;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsYjCommunicateResourceModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(YJWebAttribute.ORG_OPTION, opp.getAllOrganizationYj(dtoParameter.getOrganizationId(), true));
                forwardURL = YJURLDefineList.resourceList;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                AmsYjCommunicateResourceDTO resourceDTO = new AmsYjCommunicateResourceDTO();
                resourceDTO.setOrgOpt(opp.getAllOrganizationYj(user.getOrganizationId(), true));
                resourceDTO.setEquipmentOpt(yop.getEquipmentOpt(resourceDTO.getEquipmentName(), true));
                req.setAttribute(YJWebAttribute.RESOURCE, resourceDTO);
                forwardURL = YJURLDefineList.resourceDetail;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                amsYjCommunicateResourceDAO.setDTOClassName(AmsYjCommunicateResourceDTO.class.getName());
                AmsYjCommunicateResourceDTO amsYjCommunicateResource = (AmsYjCommunicateResourceDTO) amsYjCommunicateResourceDAO.getDataByPrimaryKey();
                if (amsYjCommunicateResource == null) {
                    amsYjCommunicateResource = new AmsYjCommunicateResourceDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                amsYjCommunicateResource.setOrgOpt(opp.getAllOrganizationYj(amsYjCommunicateResource.getOrganizationId(), true));
                amsYjCommunicateResource.setEquipmentOpt(yop.getEquipmentOpt(amsYjCommunicateResource.getEquipmentName(), true));
                req.setAttribute(YJWebAttribute.RESOURCE, amsYjCommunicateResource);
                forwardURL = YJURLDefineList.resourceDetail;
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
                message = new Message();
                String[] resourceIds = req.getParameterValues("resourceIds");
                String Ids = amsYjCommunicateResourceDAO.appendIntComvanId(resourceIds, ","); 
                boolean flag = amsYjCommunicateResourceDAO.deleteAllData(Ids);
                if(flag){
                	message.setMessageValue("删除记录成功！");
                }else{
                	message.setMessageValue("删除记录失败！");
                	message.setIsError(true);
                }
                
                forwardURL = "/servlet/com.sino.ams.yj.resource.servlet.AmsYjCommunicateResourceServlet?act=" + WebActionConstant.QUERY_ACTION;
            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {
                boolean isNew = StrUtil.isEmpty(dtoParameter.getResourceId());
              	YjManagerUtil yjUtil= new YjManagerUtil(user, conn);
                if (isNew) {
                    dtoParameter.setResourceId(String.valueOf(yjUtil.getYjManagerMax("AMS_YJ_COMMUNICATE_RESOURCE_SEQ")));
                }
                boolean success = amsYjCommunicateResourceDAO.saveData(isNew);
                if (!success) {
                    dtoParameter.setResourceId("");
                }
                message = new Message();
                message.setIsError(!success);
                if (success) {
                    message.setMessageValue("信息保存成功！");
                } else {
                    message.setMessageValue("信息保存失败！");
                }
                req.setAttribute(YJWebAttribute.RESOURCE, dtoParameter);
                if (success) {
                    forwardURL = "/servlet/com.sino.ams.yj.resource.servlet.AmsYjCommunicateResourceServlet?act=" + WebActionConstant.DETAIL_ACTION + "&resourceId=" + dtoParameter.getResourceId();
                } else {
                    forwardURL = YJURLDefineList.resourceDetail;
                }
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {   //导出Excel
                File file = amsYjCommunicateResourceDAO.getExportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals("EXP")) {                //统计
                File file = amsYjCommunicateResourceDAO.getExpFile();
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
        } catch (WebFileDownException e) {
            e.printLog();
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataTransException e) {
            e.printLog();
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } /*catch (SQLException e) {
            Logger.logError(e);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } */catch (SQLModelException e) {
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