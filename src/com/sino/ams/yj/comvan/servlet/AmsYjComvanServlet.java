package com.sino.ams.yj.comvan.servlet;
import java.sql.Connection;
import java.io.IOException;
import java.io.File;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sino.base.constant.web.WebActionConstant;
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
import com.sino.ams.yj.comvan.dto.AmsYjComvanDTO;
import com.sino.ams.yj.comvan.model.AmsYjComvanModel;
import com.sino.ams.yj.comvan.dao.AmsYjComvanDAO;
import com.sino.ams.yj.constant.YJURLDefineList;
import com.sino.ams.yj.constant.YJWebAttribute;
import com.sino.ams.yj.util.YjManagerUtil;
import com.sino.ams.bean.OptionProducer;

/**
 * <p>Title: AmsYjComvanServlet</p>
 * <p>Description:程序自动生成服务程序“AmsYjComvanServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-应急通信车
 */

public class AmsYjComvanServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        boolean success = false;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsYjComvanDTO.class.getName());
            AmsYjComvanDTO dtoParameter = (AmsYjComvanDTO) req2DTO.getDTO(req);
//            String organizationId1 = req.getParameter("organizationId1");
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            AmsYjComvanDAO amsYjComvanDAO = new AmsYjComvanDAO(user, dtoParameter, conn);
            OptionProducer opp = new OptionProducer(user, conn);

            if (action.equals("")) {
                req.setAttribute(YJWebAttribute.ORG_OPTION, opp.getAllOrganizationYj(user.getOrganizationId(), true));
                forwardURL = YJURLDefineList.comvanList;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {    //查询
                req.setAttribute(YJWebAttribute.ORG_OPTION, opp.getAllOrganizationYj(dtoParameter.getOrganizationId(), true));
                BaseSQLProducer sqlProducer = new AmsYjComvanModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                forwardURL = YJURLDefineList.comvanList;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {      //新增
                AmsYjComvanDTO amsYjComvan = new AmsYjComvanDTO();
                amsYjComvan.setOrgOpt(opp.getAllOrganizationYj(user.getOrganizationId(), true));
                req.setAttribute(YJWebAttribute.COMVAN, amsYjComvan);
                forwardURL = YJURLDefineList.comvanDetail;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {   //详细信息
                amsYjComvanDAO.setDTOClassName(AmsYjComvanDTO.class.getName());
                AmsYjComvanDTO amsYjComvan = (AmsYjComvanDTO) amsYjComvanDAO.getDataByPrimaryKey();
                if (amsYjComvan == null) {
                    amsYjComvan = new AmsYjComvanDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                amsYjComvan.setOrgOpt(opp.getAllOrganizationYj(amsYjComvan.getOrganizationId() , true));
                req.setAttribute(YJWebAttribute.COMVAN, amsYjComvan);
                forwardURL = YJURLDefineList.comvanDetail;
            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {     //保存
            	boolean isNew= StrUtil.isEmpty(dtoParameter.getComvanId());
            	YjManagerUtil yjUtil= new YjManagerUtil(user, conn);
                if (isNew) {
                    dtoParameter.setComvanId(String.valueOf(yjUtil.getYjManagerMax("AMS_YJ_COMVAN_SEQ")));
                }
                success = amsYjComvanDAO.saveData(isNew);
                message = new Message();
                message.setIsError(!success);
                String orgnizationId1 = req.getParameter("orgnizationId1");
                if (success) {
                    message.setMessageValue("信息保存成功");
                } else {
                    message.setMessageValue("信息保存失败");
                }
                req.setAttribute(YJWebAttribute.COMVAN, dtoParameter);
                if (success) {                                                                                                         
                    forwardURL ="/servlet/com.sino.ams.yj.comvan.servlet.AmsYjComvanServlet?act=" + WebActionConstant.DETAIL_ACTION +"&comvanId="+dtoParameter.getComvanId();
                } else {
                    forwardURL = YJURLDefineList.comvanDetail;
                }
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {   //删除
                String[] comvanIds = req.getParameterValues("comvanIds");
                amsYjComvanDAO.deleteAllData(comvanIds);
                forwardURL = "/servlet/com.sino.ams.yj.comvan.servlet.AmsYjComvanServlet?act=" + WebActionConstant.QUERY_ACTION;
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {   //导出
                File file = amsYjComvanDAO.getExportFile();
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
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException e) {
            e.printLog();
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } /*catch (SQLException e) {
            Logger.logError(e);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }*/ finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }
    }
}