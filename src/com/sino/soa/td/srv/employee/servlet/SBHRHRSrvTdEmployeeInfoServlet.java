package com.sino.soa.td.srv.employee.servlet;

import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.td.srv.employee.dao.SBHRHRSrvTdEmpAssignDAO;
import com.sino.soa.td.srv.employee.dao.SBHRHRSrvTdEmpInfoDAO;
import com.sino.soa.td.srv.employee.dto.SBHRHRSrvTdEmployeeInfoDTO;
import com.sino.soa.td.srv.employee.srv.SBHRHRTdInquiryEmpAssignInfoSrv;
import com.sino.soa.td.srv.employee.srv.SBHRHRTdInquiryEmpBaseInfoSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-8
 * Time: 13:09:36
 * To change this template use File | Settings | File Templates.
 */
public class SBHRHRSrvTdEmployeeInfoServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = SrvURLDefineList.SRV_EMPLOYEE_PAGE;
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        boolean autoCommit = true;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SBHRHRSrvTdEmployeeInfoDTO.class.getName());
            SBHRHRSrvTdEmployeeInfoDTO dtoParameter = (SBHRHRSrvTdEmployeeInfoDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            String assetsType = StrUtil.nullToString(req.getParameter("assetsType"));
            dtoParameter.setAssetsType(assetsType);
            conn = getDBConnection(req);
            autoCommit = conn.getAutoCommit();
            int baseErrorCount = 0;
            int baseTotalCount = 0;
            int assignErrorCount = 0;
            int assignTotalCount = 0;
            long resumeTime = 0;
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            if (action.equals("")) {
                forwardURL = SrvURLDefineList.TD_SRV_EMPLOYEE_PAGE;
            } else if (action.equals(WebActionConstant.SYSCHRONIZE_ACTION)) {
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_TD_EMPLOYEE);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步TD员工信息开始");
                logUtil.synLog(logDTO, conn);
                //1.查询员工基本信息服务
                SBHRHRTdInquiryEmpBaseInfoSrv employeeInfoSrv = new SBHRHRTdInquiryEmpBaseInfoSrv();
                employeeInfoSrv.setStartLastUpdateDate(dtoParameter.getStartLastUpdateDate());
                employeeInfoSrv.setEndLastUpdateDate(dtoParameter.getEndLastUpdateDate());
//                employeeInfoSrv.setEmployeeNumber("81000410");
                employeeInfoSrv.excute();
                SrvReturnMessage srvMessage = employeeInfoSrv.getReturnMessage();
                if (srvMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_TD_EMPLOYEE, conn);
                    DTOSet ds = employeeInfoSrv.getDs();
                    for (int i = 0; i < ds.getSize(); i++) {
                        SBHRHRSrvTdEmployeeInfoDTO dto = (SBHRHRSrvTdEmployeeInfoDTO) ds.getDTO(i);
                        SBHRHRSrvTdEmpInfoDAO srvEmployeeInfoDAO = new SBHRHRSrvTdEmpInfoDAO(user, dto, conn);
                        try {
                            if (srvEmployeeInfoDAO.isServiceTypeExists(dto.getEmployeeNumber())) {
                                srvEmployeeInfoDAO.updateData();
                            } else {
                                srvEmployeeInfoDAO.createData();
                            }
                            baseTotalCount++;
                        } catch (Exception e) {
                            Logger.logError(e);
                            logDTO = new SynLogDTO();
                            logDTO.setSynType(SrvType.SRV_TD_EMPLOYEE);
                            logDTO.setCreatedBy(user.getUserId());
                            logDTO.setSynMsg(e.toString());
                            logUtil.synLog(logDTO, conn);
                            baseErrorCount++;
                        }
                    }
                    //2.查询员工分配(分页)服务
                    SBHRHRTdInquiryEmpAssignInfoSrv empAssignInfoSrv = new SBHRHRTdInquiryEmpAssignInfoSrv();
                    //注：此处时间向前提200天
//                    System.out.println("时间提前"+SynUpdateDateUtils.getDate3(dtoParameter.getStartLastUpdateDate()));
                    empAssignInfoSrv.setStartLastUpdateDate(SynUpdateDateUtils.getDate3(dtoParameter.getStartLastUpdateDate()));
                    empAssignInfoSrv.execute();
                    SrvReturnMessage empMessage = empAssignInfoSrv.getReturnMessage();
                    if (empMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                    	SBHRHRSrvTdEmpAssignDAO srvEmpAssignInfoDAO1 = new SBHRHRSrvTdEmpAssignDAO(user, dtoParameter, conn);
                        DTOSet dtos = empAssignInfoSrv.getDs();
                        for (int i = 0; i < dtos.getSize(); i++) {
                            SBHRHRSrvTdEmployeeInfoDTO dto = (SBHRHRSrvTdEmployeeInfoDTO) dtos.getDTO(i);
						    dto.setDeptCode(srvEmpAssignInfoDAO1.getEmpDeptCodeModel(dto.getOrganization()));  //HR_DEPT_NAME
						    dto.setCompanyCode(srvEmpAssignInfoDAO1.getEmpCompanyCodeModel(dto.getOrganization()));
                            SBHRHRSrvTdEmpAssignDAO srvEmpAssignInfoDAO = new SBHRHRSrvTdEmpAssignDAO(user, dto, conn);
                            try {
                                if (srvEmpAssignInfoDAO.isEmployeeExists(dto.getEmployeeNumber())) {
                                    srvEmpAssignInfoDAO.updateData();
                                    assignTotalCount++;
                                }
                            } catch (Exception e) {
                                Logger.logError(e);
                                logDTO = new SynLogDTO();
                                logDTO.setSynType(SrvType.SRV_TD_EMPLOYEE);
                                logDTO.setCreatedBy(user.getUserId());
                                logDTO.setSynMsg(e.toString());
                                logUtil.synLog(logDTO, conn);
                                assignErrorCount++;
                            }
                        }
                    }
                }
                SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_TD_EMPLOYEE, conn);
                resumeTime = System.currentTimeMillis() - start;
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_TD_EMPLOYEE);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步TD员工信息结束！");
                logUtil.synLog(logDTO, conn);
                message = new Message();
                message.setMessageValue("共同步"+(baseTotalCount+baseErrorCount+assignTotalCount+assignErrorCount)+"条记录，" +
                                        "员工基本信息同步成功"+baseTotalCount+"，失败"+baseErrorCount+"，" +
                                        "员工分配信息同步成功"+assignTotalCount+"，失败"+assignErrorCount+"，" +
                                        "耗时"+resumeTime+"毫秒");
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.TD_SRV_EMPLOYEE_PAGE;
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
        } catch (DataHandleException ex) {
            ex.printLog();
            message.setIsError(true);
            message.setMessageValue(ex.toString());
        } catch (SQLException e) {
            Logger.logError(e);
            message.setIsError(true);
            message.setMessageValue(e.toString());
        } catch (CalendarException e) {
            Logger.logError(e);
            message.setIsError(true);
            message.setMessageValue(e.toString());
        } catch (DatatypeConfigurationException e) {
            Logger.logError(e);
            message.setIsError(true);
            message.setMessageValue(e.toString());
        } catch (ContainerException e) {
            Logger.logError(e);
            message.setIsError(true);
            message.setMessageValue(e.toString());
        } catch (QueryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }

}