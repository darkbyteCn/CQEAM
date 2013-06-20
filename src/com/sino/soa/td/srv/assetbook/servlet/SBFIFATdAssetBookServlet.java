package com.sino.soa.td.srv.assetbook.servlet;

import com.sino.ams.bean.OrgOptionProducer;
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
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.td.srv.assetbook.dao.SBFIFATdAssetBookDAO;
import com.sino.soa.td.srv.assetbook.dto.SBFIFATdAssetBookDTO;
import com.sino.soa.td.srv.assetbook.srv.SBFIFATdInquiryAssetBookSrv;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.common.SrvWebActionConstant;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-6
 * Time: 12:26:09
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFATdAssetBookServlet extends BaseServlet {

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
            req2DTO.setDTOClassName(SBFIFATdAssetBookDTO.class.getName());
            SBFIFATdAssetBookDTO dtoParameter = (SBFIFATdAssetBookDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            SBFIFATdAssetBookDAO srvAssetBookDAO = new SBFIFATdAssetBookDAO(user, dtoParameter, conn);
            OrgOptionProducer optionProducer = new OrgOptionProducer(user, conn);
            String range = "Y";//TD
            String opt = optionProducer.getBookTypeCodeOption(dtoParameter.getBookTypeCode(), range);
            dtoParameter.setOrgOption(opt);
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            if (action.equals("")) {
                forwardURL = SrvURLDefineList.TD_SRV_ESSET_BOOK_PAGE;
            } else if (action.equals(SrvWebActionConstant.QUERY_ACTION)) {
                SBFIFATdInquiryAssetBookSrv service = new SBFIFATdInquiryAssetBookSrv();
                String bookTypeCode = req.getParameter("bookTypeCode");
                String lastUpdateDate = req.getParameter("lastUpdateDate");
                if (bookTypeCode != null && !bookTypeCode.equals(""))
                    service.setBookTypeCode(bookTypeCode);
                if (lastUpdateDate != null && !lastUpdateDate.equals(""))
                    service.setLastUpdateDate(lastUpdateDate);
                service.excute();
                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag() != null && srm.getErrorFlag().equals("Y")) {
                    DTOSet ds = service.getDs();
                    ds = filterDtoSet(ds);
                    req.setAttribute(SrvWebActionConstant.ASSETBOOKTRANSOU, ds);
                    forwardURL = SrvURLDefineList.TD_SRV_ESSET_BOOK_PAGE;
                } else {
                    message.setMessageValue("调用WebService服务失败：" + srm.getErrorMessage());
                    forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
                }
            } else if (action.equals(SrvWebActionConstant.INFORSYN)) {
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_TD_BOOK);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步TD资产帐簿开始");
                logUtil.synLog(logDTO, conn);

                SBFIFATdInquiryAssetBookSrv service = new SBFIFATdInquiryAssetBookSrv();
                String bookTypeCode = req.getParameter("bookTypeCode");
                String lastUpdateDate = req.getParameter("lastUpdateDate");
                service.setBookTypeCode(bookTypeCode);
                if (lastUpdateDate != null && !lastUpdateDate.equals("")) {
                    service.setLastUpdateDate(lastUpdateDate);
                }
                service.excute();
                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag().equalsIgnoreCase("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_TD_BOOK, conn);
                    DTOSet ds = service.getDs();
                    if (ds.getSize() > 0) {
                        ds = filterDtoSet(ds);
                        count = srvAssetBookDAO.synAssetBook(ds);
                    }
                    falsecount = srvAssetBookDAO.getErrorCount();
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_BOOK);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步TD资产帐簿结束, 同步" + (count + falsecount) + "条记录，成功" + count + "，失败" + (falsecount) + "，耗时" + resumeTime + "毫秒。资产账簿："+bookTypeCode);
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_TD_BOOK, conn);
                    message = new Message();
                    message.setMessageValue("同步TD资产帐簿结束,同步" + (count + falsecount) + "条记录，成功" + count + "，失败" + (falsecount) + "，耗时" + resumeTime + "毫秒");
                }else{
	               	 resumeTime = System.currentTimeMillis() - start;
	                 logDTO = new SynLogDTO();
	                 logDTO.setSynType(SrvType.SRV_BOOK);
	                 logDTO.setCreatedBy(user.getUserId());
	                 logDTO.setSynMsg("同步TD资产帐簿失败, 耗时" + resumeTime + "毫秒, 资产账簿："+bookTypeCode+"。 错误信息:"+srm.getErrorMessage());
	                 logUtil.synLog(logDTO, conn);
	                 message = new Message();
	                 message.setMessageValue("同步TD资产帐簿失败, 耗时" + resumeTime + "毫秒, 资产账簿："+bookTypeCode+"。 错误信息:"+srm.getErrorMessage());
	                }
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.TD_SRV_ESSET_BOOK_PAGE;
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message.setMessageValue("同步失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
        } catch (DTOException ex) {
            ex.printLog();
            message.setMessageValue("同步失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
        } catch (DatatypeConfigurationException ex1) {
            message.setMessageValue("同步失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
            ex1.printStackTrace();
        } catch (TimeException e) {
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
            SBFIFATdAssetBookDTO dto = (SBFIFATdAssetBookDTO) ds.getDTO(i);
            if (!dto.getBookTypeCode().equals("")) {
                if (dto.getBookTypeCode().indexOf("FA") != -1) {
                    try {
                        returnds.addDTO(dto);
                    } catch (DTOException e) {
                        e.printLog();
                    }
                }
            }
        }
        return returnds;
    }
}