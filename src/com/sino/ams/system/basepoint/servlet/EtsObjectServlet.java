package com.sino.ams.system.basepoint.servlet;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.basepoint.dao.EtsObjectAttributeDAO;
import com.sino.ams.system.basepoint.dao.EtsObjectDAO;
import com.sino.ams.system.basepoint.dto.EtsObjectAttributeDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.basepoint.model.EtsObjectModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsObjectServlet</p>
 * <p>Description:程序自动生成服务程序“EtsObjectServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsObjectServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        String workorderObjectNo = StrUtil.nullToString(req.getParameter("workorderObjectNo"));
        String[] workorderObjectNos = req.getParameterValues("workorderObjectNos");
        Enumeration e = req.getParameterNames();
        action = StrUtil.nullToString(action);
        Connection conn = null;
        String showMsg = "";
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            EtsObjectDTO dtoParameter = null;
            EtsObjectAttributeDTO dtoParameter2 = null;
            Request2DTO req2DTO = new Request2DTO();

            req2DTO.setDTOClassName(EtsObjectDTO.class.getName());
            dtoParameter = (EtsObjectDTO) req2DTO.getDTO(req);

            conn = getDBConnection(req);
            EtsObjectDAO etsObjectDAO = new EtsObjectDAO(user, dtoParameter, conn);
            OptionProducer optProducer = new OptionProducer(user, conn);
//            String cityOption1 = optProducer.getAllOrganization("",true);
//            req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption1);
//            String countyOption1= optProducer.getCountyOption(dtoParameter.getCountyCode());
//            req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption1);
            if (action.equals("")) {
                String cityOption = optProducer.getAllOrganization(0, true);
                req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
                String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                forwardURL = URLDefineList.BASEPOINT_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                String cityOption = optProducer.getAllOrganization(dtoParameter.getOrganizationId(), true);
                req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);

                String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                BaseSQLProducer sqlProducer = new EtsObjectModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                forwardURL = URLDefineList.BASEPOINT_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                String patrolOption = optProducer.getDictOption(DictConstant.CHECK_MODLE, StrUtil.nullToString(dtoParameter.getIsall()));
                req.setAttribute(WebAttrConstant.CHECK_OPTION, patrolOption);
                String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                dtoParameter = new EtsObjectDTO();
                req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dtoParameter);
                dtoParameter2 = new EtsObjectAttributeDTO();
                req.setAttribute(WebAttrConstant.ETS_OBJECT_ATTRIBUTE_DTO, dtoParameter2);
                forwardURL = URLDefineList.BASEPOINT_DETAIL_PAGE;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {         //明细

                etsObjectDAO.setDTOClassName(EtsObjectDTO.class.getName());
                EtsObjectDTO etsObject = (EtsObjectDTO) etsObjectDAO.getDataByPrimaryKey();


                dtoParameter2 = new EtsObjectAttributeDTO();
                dtoParameter2.setObjectNo(workorderObjectNo);


                EtsObjectAttributeDAO etsObjectAttributeDAO = new EtsObjectAttributeDAO(user, dtoParameter2, conn);
                etsObjectAttributeDAO.setDTOClassName(EtsObjectAttributeDTO.class.getName());
                EtsObjectAttributeDTO etsObjectAttribute = (EtsObjectAttributeDTO) etsObjectAttributeDAO.getDataByPrimaryKey();

                if (etsObject == null) {
                    etsObject = new EtsObjectDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                String countyOption = optProducer.getCountyOption(etsObject.getCountyCode());
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                String patrolOption = optProducer.getDictOption(DictConstant.CHECK_MODLE, StrUtil.nullToString(etsObject.getIsall()));
                req.setAttribute(WebAttrConstant.CHECK_OPTION, patrolOption);
                req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, etsObject);
                if (etsObjectAttribute == null) {
                    etsObjectAttribute = new EtsObjectAttributeDTO();
//					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
//					message.setIsError(true);
                }
                req.setAttribute(WebAttrConstant.ETS_OBJECT_ATTRIBUTE_DTO, etsObjectAttribute);
                forwardURL = URLDefineList.BASEPOINT_DETAIL_PAGE;
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {      //点save操作
                req2DTO.setDTOClassName(EtsObjectAttributeDTO.class.getName());
                EtsObjectAttributeDTO objAttibute = (EtsObjectAttributeDTO) req2DTO.getDTO(req);
                dtoParameter = (EtsObjectDTO) etsObjectDAO.getDTOParameter();

                boolean operateResult = etsObjectDAO.createData(objAttibute);
                message = etsObjectDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
                    req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                    showMsg = "基站地点信息创建成功!";
//                    forwardURL = URLDefineList.BASEPOINT_QUERY_PAGE;
                    forwardURL = URLDefineList.BASEPOINT_QUERY_PAGE + "?act=" + WebActionConstant.QUERY_ACTION;
                } else {
                    String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
                    req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                    req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dtoParameter);
                    forwardURL = URLDefineList.BASEPOINT_DETAIL_PAGE;
                }
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {       //保存修改
                req2DTO.setDTOClassName(EtsObjectAttributeDTO.class.getName());
                EtsObjectAttributeDTO objAttibute = (EtsObjectAttributeDTO) req2DTO.getDTO(req);
                boolean operateResult = etsObjectDAO.updateData(workorderObjectNo, objAttibute);
                message = etsObjectDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
                    req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                    showMsg = "基站地点信息修改成功!";
//                    forwardURL = URLDefineList.BASEPOINT_QUERY_PAGE;
                    forwardURL = URLDefineList.BASEPOINT_QUERY_PAGE + "?act=" + WebActionConstant.QUERY_ACTION;
                } else {
                    req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dtoParameter);
                    forwardURL = URLDefineList.BASEPOINT_DETAIL_PAGE;
                }
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {        //详细页面失效操作（单个失效）
                etsObjectDAO.deleteData();
                message = etsObjectDAO.getMessage();
                String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                String cityOption = optProducer.getAllOrganization(0, true);
                req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
                forwardURL = URLDefineList.BASEPOINT_QUERY_PAGE;
            } else if (action.equals(AMSActionConstant.INURE_ACTION)) {    // 详细页面生效操作 （单个生效）
                etsObjectDAO.inureData();
                String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                String cityOption = optProducer.getAllOrganization(0, true);
                req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
                forwardURL = URLDefineList.BASEPOINT_QUERY_PAGE;
            } else if (action.equals(AMSActionConstant.DISABLED_ACTION)) {    //查询页面 批量失效
                etsObjectDAO.disabledData(workorderObjectNos);
                message = etsObjectDAO.getMessage();
                String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                String cityOption = optProducer.getAllOrganization(0, true);
                req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
                forwardURL = URLDefineList.BASEPOINT_QUERY_PAGE;
            } else if (action.equals(AMSActionConstant.EFFICIENT_ACTION)) {      //查询页面 批量生效
                etsObjectDAO.efficientData(workorderObjectNos);
                message = etsObjectDAO.getMessage();
                String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                String cityOption = optProducer.getAllOrganization(0, true);
                req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
                forwardURL = URLDefineList.BASEPOINT_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出到Excel
                File file = etsObjectDAO.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals("verifyworkNo")) {                                          //验证barcode是否存在
                String workorderObjectCode = StrUtil.nullToString(req.getParameter("workorderObjectCode"));
                boolean success = etsObjectDAO.doVerifyWorkNo(workorderObjectCode);
                PrintWriter out = res.getWriter();
                if (success) {
                    out.print("Y");
                }
                out.flush();
                out.close();
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
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
//			DBManager.closeDBConnection(conn);
//			setHandleMessage(req, message);
//			ServletForwarder forwarder = new ServletForwarder(req, res);
//			forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!action.equals("verifyworkNo")) {
                if (!showMsg.equals("")) {
                    res.setContentType("text/html;charset=GBK");
                    PrintWriter out = res.getWriter();
                    out.print("<script language=\"javascript\">\n");
                    out.println("alert(\"" + showMsg + "\");");
                    out.println("window.close();\n");
                    out.print("</script>");
                } else {
                    ServletForwarder sforwarder = new ServletForwarder(req, res);
                    sforwarder.forwardView(forwardURL);
                }
            }
        }
    }
}
