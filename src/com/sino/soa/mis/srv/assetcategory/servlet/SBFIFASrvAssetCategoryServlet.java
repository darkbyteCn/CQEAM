package com.sino.soa.mis.srv.assetcategory.servlet;

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
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.mis.srv.assetcategory.dao.SBFIFASrvAssetCategoryDAO;
import com.sino.soa.mis.srv.assetcategory.dto.SBFIFASrvAssetCategoryDTO;
import com.sino.soa.mis.srv.assetcategory.srv.SBFIFAInquiryAssetCategorySrv;
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
 * Time: 17:15:06
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFASrvAssetCategoryServlet extends BaseServlet {

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
            req2DTO.setDTOClassName(SBFIFASrvAssetCategoryDTO.class.getName());
            SBFIFASrvAssetCategoryDTO dtoParameter = (SBFIFASrvAssetCategoryDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            String assetsType = StrUtil.nullToString(req.getParameter("assetsType"));
            dtoParameter.setAssetsType(assetsType);
            conn = getDBConnection(req);
            SBFIFASrvAssetCategoryDAO srvAssetCategoryDAO = new SBFIFASrvAssetCategoryDAO(user, dtoParameter, conn);
            OrgOptionProducer optionProducer = new OrgOptionProducer(user, conn);
            String range = "N";//非TD
            String opt = optionProducer.getBookTypeCodeOption(dtoParameter.getBookTypeCode(), range);
            dtoParameter.setOrgOption(opt);
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            if (action.equals("")) {
                forwardURL = SrvURLDefineList.SRV_ESSET_CATEGORY_PAGE;
            } else if (action.equals(SrvWebActionConstant.QUERY_ACTION)) {
                SBFIFAInquiryAssetCategorySrv service = new SBFIFAInquiryAssetCategorySrv();
                String bookTypeCode = req.getParameter("bookTypeCode");
                String lastUpdateDate = req.getParameter("lastUpdateDate");
                String segment1 = req.getParameter("segment1");
                String concatenatedSegments = req.getParameter("concatenatedSegments");
                if (StrUtil.isNotEmpty(bookTypeCode)) {
                    service.setBookTypeCode(bookTypeCode);
                }
                if (StrUtil.isNotEmpty(lastUpdateDate)) {
                    service.setSegment1(segment1);
                }
                if (StrUtil.isNotEmpty(concatenatedSegments)) {
                    service.setConcatenatedSegments(concatenatedSegments);
                }
                service.execute();
                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag() != null && srm.getErrorFlag().equals("Y")) {
                    DTOSet ds = service.getDs();
                    req.setAttribute(SrvWebActionConstant.ASSETBOOKTRANSOU, ds);
                    forwardURL = SrvURLDefineList.SRV_ESSET_CATEGORY_PAGE;
                }
            } else if (action.equals(SrvWebActionConstant.INFORSYN)) {
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_FA_CATEGORY);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步资产类别开始");
                logUtil.synLog(logDTO, conn);

                SBFIFAInquiryAssetCategorySrv service = new SBFIFAInquiryAssetCategorySrv();
                String lastUpdateDate = req.getParameter("lastUpdateDate");
                service.setSegment1(dtoParameter.getSegment1());
                service.setConcatenatedSegments(dtoParameter.getConcatenatedSegments());
                service.setBookTypeCode(dtoParameter.getBookTypeCode());
                service.setLastUpdateDate(lastUpdateDate);
                service.execute();
                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag().equalsIgnoreCase("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_FA_CATEGORY, conn);
                    DTOSet ds = service.getDs();
                    if (ds.getSize() > 0) {
                        count = srvAssetCategoryDAO.SavaAssetCategory(ds);
                    }
                    falsecount = srvAssetCategoryDAO.getErrorCount();
                    int totalCont = count+falsecount;
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_CATEGORY);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步资产类别结束, 同步" + totalCont + "条记录，成功" + count + "，失败" + falsecount + "，耗时" + resumeTime + "毫秒,资产账簿："+dtoParameter.getBookTypeCode());
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_FA_CATEGORY, conn);
                    message = new Message();
                    message.setMessageValue("同步资产类别 " + totalCont + "条记录，成功" + count + "，失败" + falsecount + "，耗时" + resumeTime + "毫秒");
                }else{
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_CATEGORY);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步资产类别失败,耗时" + resumeTime + "毫秒,资产账簿："+dtoParameter.getBookTypeCode()+"。错误信息："+srm.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message = new Message();
                    message.setMessageValue("同步资产类别失败,耗时" + resumeTime + "毫秒,资产账簿："+dtoParameter.getBookTypeCode()+"。错误信息："+srm.getErrorMessage());
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.SRV_ESSET_CATEGORY_PAGE;

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
}