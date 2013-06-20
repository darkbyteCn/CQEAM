package com.sino.soa.td.srv.projectInfo.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
/*import com.sino.soa.mis.srv.projectInfo.dao.SrvProjectInfoDAO;
import com.sino.soa.mis.srv.projectInfo.dto.SrvProjectInfoDTO;
import com.sino.soa.mis.srv.projectInfo.srv.InquiryProjectInfoSrv;*/

import com.sino.soa.td.srv.projectInfo.dao.TDSrvProjectInfoDAO;
import com.sino.soa.td.srv.projectInfo.dto.TDSrvProjectInfoDTO;
import com.sino.soa.td.srv.projectInfo.srv.TDInquiryProjectInfoSrv;

import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.bean.OrgOptionProducer;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.base.util.StrUtil;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SrvProjectInfoServlet</p>
 * <p>Description:程序自动生成服务程序“SrvProjectInfoServlet”</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author wangzhipeng
 * @version 1.0
 * DATE:2011-09
 * DES:查询项目信息服务_TD
 */

public class TDSrvProjectInfoServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = com.sino.soa.common.SrvURLDefineList.TD_SRV_PROJECT_PAGE;
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        boolean autoCommit = true;
        int errorCount = 0;
        int totalCount = 0;
        long resumeTime = 0;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(TDSrvProjectInfoDTO.class.getName());
            TDSrvProjectInfoDTO dtoParameter = (TDSrvProjectInfoDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            String assetsType = StrUtil.nullToString(req.getParameter("assetsType"));
            String orgCode = StrUtil.nullToString(req.getParameter("orgCode"));
            dtoParameter.setAssetsType(assetsType);
            conn = getDBConnection(req);
            autoCommit = conn.getAutoCommit();
            TDSrvProjectInfoDAO srvProjectInfoDAO = new TDSrvProjectInfoDAO(user, dtoParameter, conn);
            OrgOptionProducer producer = new OrgOptionProducer(user, conn);
            String range = assetsType.equals("TD") ? "Y" : "N";
            String orgOption = "";
            if (action.equals("")) {
                orgOption = producer.getCompanyOption(orgCode, range);
                dtoParameter.setOuOption(orgOption);
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.TD_SRV_PROJECT_PAGE;
            } else if (action.equals(WebActionConstant.SYSCHRONIZE_ACTION) && AssetsWebAttributes.TD_ASSETS_TYPE.equals(assetsType)) { //TD
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_TD_PA_PROJECT);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步TD项目信息开始");
                logUtil.synLog(logDTO, conn);
                TDInquiryProjectInfoSrv projectInfoSrv = new TDInquiryProjectInfoSrv();
                projectInfoSrv.setSegment1(dtoParameter.getSegment1());
                projectInfoSrv.setProjectClass(dtoParameter.getProjectClass());
                projectInfoSrv.setProjectType(dtoParameter.getProjectType());
                projectInfoSrv.setProjectStstus(dtoParameter.getProjectStatusCode());
                projectInfoSrv.setOrgName(dtoParameter.getOrganizationName());
                projectInfoSrv.setOrgCode(dtoParameter.getOrgCode());
                projectInfoSrv.execute();
                SrvReturnMessage srvMessage = projectInfoSrv.getReturnMessage();
                if (srvMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_TD_PA_PROJECT, conn);
                    DTOSet ds = projectInfoSrv.getDs();
                    for (int i = 0; i < ds.getSize(); i++) {
                        TDSrvProjectInfoDTO dto = (TDSrvProjectInfoDTO) ds.getDTO(i);
                        srvProjectInfoDAO = new TDSrvProjectInfoDAO(user, dto, conn);
                        try {
                            if (SynUpdateDateUtils.getBetweenDays(SynUpdateDateUtils.getLastUpdateDate(SrvType.SRV_TD_PA_PROJECT, conn), (dto.getLastUpdateDate().toString())) > 0) {
                                if (srvProjectInfoDAO.isProjecdtExists(dto.getSegment1())) {
                                    srvProjectInfoDAO.updateData();
                                } else {
                                    srvProjectInfoDAO.createData();
                                }
                                totalCount++;
                            }

                        } catch (DataHandleException e) {
                            Logger.logError(e);
                            logDTO = new SynLogDTO();
                            logDTO.setSynType(SrvType.SRV_TD_PA_PROJECT);
                            logDTO.setCreatedBy(user.getUserId());
                            logDTO.setSynMsg(e.toString());
                            logUtil.synLog(logDTO, conn);
                            errorCount++;
                        } catch (ContainerException e) {
                            Logger.logError(e);
                            e.printStackTrace();
                        } catch (ParseException e) {
                            Logger.logError(e);
                            e.printStackTrace();
                        }
                    }
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_TD_PA_PROJECT, conn);
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_PA_PROJECT);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步TD项目信息结束,同步" + (totalCount + errorCount) + "条记录，成功" + totalCount + "，失败" + errorCount + "，耗时" + resumeTime + "毫秒");
                    logUtil.synLog(logDTO, conn);
                    message = new Message();
                    message.setMessageValue("同步TD项目信息 " + (totalCount + errorCount) + "条记录，成功" + totalCount + "，失败" + errorCount + "，耗时" + resumeTime + "毫秒"); 
                } else {
                	resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_PA_PROJECT);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步TD项目信息失败, 耗时" + resumeTime + "毫秒, 错误信息："+srvMessage.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message = new Message();
                    message.setMessageValue("同步TD项目信息失败, 耗时" + resumeTime + "毫秒, 错误信息："+srvMessage.getErrorMessage()); 
                }
               orgOption = producer.getCompanyOption(orgCode);
                dtoParameter.setOuOption(orgOption);
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.TD_SRV_PROJECT_PAGE;
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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CalendarException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}