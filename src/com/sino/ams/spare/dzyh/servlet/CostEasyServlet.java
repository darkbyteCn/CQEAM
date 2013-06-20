package com.sino.ams.spare.dzyh.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.dzyh.dao.CostEasyDAO;
import com.sino.ams.spare.dzyh.dto.CostEasyDTO;
import com.sino.ams.spare.dzyh.model.CostEasyModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.bean.AssetsOptProducer;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-4-7
 * Time: 16:32:17
 * Function；低值易耗维护。
 */
public class CostEasyServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        String showMsg = "";
        Message message =SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            CostEasyDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(CostEasyDTO.class.getName());
            dtoParameter = (CostEasyDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            CostEasyDAO intangDAO = new CostEasyDAO(user, dtoParameter, conn);
            OptionProducer optProducer = new OptionProducer(user, conn); //获取区县信息
            String cityOption = optProducer.getAllOrganization(dtoParameter.getOrganizationId(), true);
            req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
            dtoParameter.setItemCategory("DZYH");
            if (action.equals("")) {
                req.setAttribute(WebAttrConstant.DZYH_DTO, dtoParameter);
                forwardURL = "/spare/dzyh/costEasySearch.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {       //查询
                BaseSQLProducer sqlProducer = new CostEasyModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(WebAttrConstant.DZYH_DTO, dtoParameter);
                forwardURL = "/spare/dzyh/costEasySearch.jsp";
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
                File file = intangDAO.exportFile();
                intangDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {      //点新增操作
                AssetsOptProducer opt = new AssetsOptProducer(user, conn);
                String deptOpt = opt.getUserAsssetsDeptOption(dtoParameter.getResponsibilityDept());
                req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);
                String specialDepOpt = opt.getSpecialAsssetsDeptOption(dtoParameter.getSpecialityDept());
                req.setAttribute("DEPT_OPTIONS2", specialDepOpt);
                dtoParameter.setItemCategory("DZYH");
                dtoParameter.setItemCategoryDesc("低值易耗");
                req.setAttribute(WebAttrConstant.DZYH_DTO, dtoParameter);
                forwardURL = "/spare/dzyh/costEasyInfo.jsp";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {        //点明细操作
                intangDAO.setDTOClassName(CostEasyDTO.class.getName());
                CostEasyDTO etsObject = (CostEasyDTO) intangDAO.getDataByPrimaryKey();
                if (etsObject == null) {
                    etsObject = new CostEasyDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute(WebAttrConstant.DZYH_DTO, etsObject);
                forwardURL = "/spare/dzyh/costEasyInfo.jsp";
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {  //点save操作
//                intangDAO.createData();
                intangDAO.insertData();
                message = intangDAO.getMessage();
                req.setAttribute(WebAttrConstant.DZYH_DTO, dtoParameter);
                forwardURL = "/servlet/com.sino.ams.spare.dzyh.servlet.CostEasyServlet?act=" + WebActionConstant.QUERY_ACTION;
                showMsg = "新增数据成功！";
//                forwardURL = "/spare/dzyh/costEasySearch.jsp";
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {    //修改操作
                intangDAO.updateData();
                message = intangDAO.getMessage();
                forwardURL = "/servlet/com.sino.ams.spare.dzyh.servlet.CostEasyServlet?act=" + WebActionConstant.QUERY_ACTION;
                showMsg = "修改数据成功！";
//                forwardURL = "/spare/dzyh/costEasySearch.jsp";
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
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException e) {
            Logger.logError(e);
        } catch (SQLException e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
//            if (!forwardURL.equals("")) {
                if (showMsg.equals("")) {
                    forwarder.forwardView(forwardURL);
                } else {
                    forwarder.forwardOpenerView(forwardURL, showMsg);
                }
//            }
            //根据实际情况修改页面跳转代码。
        }
    }
}
