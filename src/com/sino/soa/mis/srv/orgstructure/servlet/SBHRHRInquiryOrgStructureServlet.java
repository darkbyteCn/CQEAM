package com.sino.soa.mis.srv.orgstructure.servlet;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.TimeException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.config.SinoConfig;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.common.SrvWebActionConstant;
import com.sino.soa.mis.srv.orgstructure.dao.SBHRHRInquiryOrgStructureDAO;
import com.sino.soa.mis.srv.orgstructure.dto.SBHRHRInquiryOrgStructureDTO;
import com.sino.soa.mis.srv.orgstructure.srv.SBHRHRInquiryOrgStructureSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-16
 * Time: 18:26:26
 * To change this template use File | Settings | File Templates.
 */
public class SBHRHRInquiryOrgStructureServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        int count = 0;
        int falsecount = 0;
        long resumeTime = 0;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SBHRHRInquiryOrgStructureDTO.class.getName());
            SBHRHRInquiryOrgStructureDTO dtoParameter = (SBHRHRInquiryOrgStructureDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            SBHRHRInquiryOrgStructureDAO dao = new SBHRHRInquiryOrgStructureDAO(user, dtoParameter, conn);
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            if (action.equals("")) {
                forwardURL = SrvURLDefineList.SRV_ORG_STRUCTURE_PAGE;
            } else if (action.equals(SrvWebActionConstant.INFORSYN)) {
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_ORG_STRUCTURE);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步MIS组织结构开始");
                logUtil.synLog(logDTO, conn);

                SBHRHRInquiryOrgStructureSrv service = new SBHRHRInquiryOrgStructureSrv();
                service.excute();
                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag().equalsIgnoreCase("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_ORG_STRUCTURE, conn);
                    DTOSet ds = service.getDs();
                    if (ds.getSize() > 0) {
                        ds = filterDtoSet(ds);
                        count = dao.synOrgStructure(ds);
                    }
                    falsecount = dao.getErrorCount();
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_ORG_STRUCTURE);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步MIS组织结构结束,同步" + (count + falsecount) + "条记录，成功" + count + "，失败" + (falsecount) + "，耗时" + resumeTime + "毫秒");
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_ORG_STRUCTURE, conn);
                    message = new Message();
                    message.setMessageValue("同步MIS组织结构" + (count + falsecount) + "条记录，成功" + count + "，失败" + (falsecount) + "，耗时" + resumeTime + "毫秒");
                }else{
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_ORG_STRUCTURE);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步MIS组织结构失败, 耗时" + resumeTime + "毫秒"+"。错误信息:"+srm.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message = new Message();
                    message.setMessageValue("同步MIS组织结构失败, 耗时" + resumeTime + "毫秒"+"。错误信息:"+srm.getErrorMessage());
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.SRV_ORG_STRUCTURE_PAGE;
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message.setMessageValue("同步失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
        } catch (DTOException ex) {
            ex.printLog();
            message.setMessageValue("同步失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
        } catch (TimeException e) {
            message.setMessageValue("同步失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
            e.printStackTrace();
        } catch (NullPointerException e) {
            message.setMessageValue("同步失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
            e.printStackTrace();
        } catch (Exception e) {
            message.setMessageValue("同步失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }

    public DTOSet filterDtoSet(DTOSet ds) {
        DTOSet returnds = new DTOSet();
        for (int i = 0; i < ds.getSize(); i++) {
            SBHRHRInquiryOrgStructureDTO dto = (SBHRHRInquiryOrgStructureDTO) ds.getDTO(i);
            if (dto.getOrganizationName().indexOf("OU_") < 0 && dto.getOrganizationName().indexOf("IO_") < 0 && dto.getOrganizationName().indexOf("IA_") < 0 && dto.getOrganizationName().indexOf("LA_") < 0) {
                try {
                    returnds.addDTO(dto);
                } catch (DTOException e) {
                    e.printLog();
                }
            }
        }
        return returnds;
    }
}