package com.sino.soa.mis.srv.dealProjectDiversity.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;

import com.sino.ams.bean.OrgOptionProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.TimeException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.common.SrvWebActionConstant;
import com.sino.soa.mis.srv.dealProjectDiversity.dao.DealProjectDiversityCheckDAO;
import com.sino.soa.mis.srv.dealProjectDiversity.dto.DealProjectDiversityCheckDTO; 
import com.sino.soa.mis.srv.dealProjectDiversity.model.DealProjectDiversityCheckModel;
import com.sino.soa.mis.srv.pagequiryassetcustdetail.srv.SBFIFAPageinquiryAssetCustDetailSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;

	/**
	 * Created by IntelliJ IDEA.
	 * User: admin
	 * Date: 2012-2-13
	 * To change this template use File | Settings | File Templates.
	 */
public class DealProjectDiversityCheckServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        int count = 0;
        long resumeTime = 0;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(DealProjectDiversityCheckDTO.class.getName());
            DealProjectDiversityCheckDTO dtoParameter = (DealProjectDiversityCheckDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            DealProjectDiversityCheckDAO srvAssetCategoryDAO = new DealProjectDiversityCheckDAO(user, dtoParameter, conn);
            OrgOptionProducer optionProducer = new OrgOptionProducer(user, conn);
            String range = "N",misProjectId = "",diffTypeCode = "";//非TD
            String bookTCode=dtoParameter.getBookTypeCode();
//            misProjectId = req.getParameter("misProjectId");       //项目编号
//            diffTypeCode = req.getParameter("diffTypeCode");       //差异类型
            misProjectId = dtoParameter.getMisProjectId();       //项目编号
            diffTypeCode = dtoParameter.getDiffTypeCode();       //差异类型
            //资产帐簿
            String opt = optionProducer.getBookTypeCodeOption(bookTCode, range);
            dtoParameter.setBookTypeCodeOption(opt);
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            String projectNum=dtoParameter.getProjectNumber2();
            projectNum= projectNum.replace("'", "");
            if (action.equals("")) {
            	forwardURL = "/srv/dealProjectDiversity/dealProjectDiversityCheck.jsp";
            } else if (action.equals(SrvWebActionConstant.QUERY_ACTION)) {
            	if(!misProjectId.equals("") && !diffTypeCode.equals("")){
            		BaseSQLProducer sqlProducer = new DealProjectDiversityCheckModel(user,dtoParameter);
                    PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                 	pageDAO.setCalPattern(LINE_PATTERN);
    				pageDAO.produceWebData(); 
            		forwardURL = "/srv/dealProjectDiversity/dealProjectDiversityCheck.jsp";
            	}
            }else if (action.equals(WebActionConstant.EXPORT_ACTION)) { //导出
                File file = srvAssetCategoryDAO.getExportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals(SrvWebActionConstant.INFORSYN)) {
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_FA_PAGE_CUST_DETAIL);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步MIS转资清单开始");
                logUtil.synLog(logDTO, conn);

                SBFIFAPageinquiryAssetCustDetailSrv service = new SBFIFAPageinquiryAssetCustDetailSrv();
                service.setBookTypeCode(bookTCode);
                service.setProjectNumber(projectNum);
                service.execute();

                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag().equalsIgnoreCase("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_FA_PAGE_CUST_DETAIL, conn);
                    DTOSet ds = service.getDs();
//                    DELETE FROM ETS_FA_CUST_DETAIL_IMP2
                    srvAssetCategoryDAO.deleteImpData(projectNum,bookTCode);
                    srvAssetCategoryDAO.deleteLogData();
                    if (ds.getSize() > 0) {
                        count = srvAssetCategoryDAO.SavaAssetCategory(ds);
                    }
                    //需要同步的数量(排除不需要插入ETS_ITEM_INFO临时表数据)
                	String syncTotalCount = srvAssetCategoryDAO.getSyncTotalCount(projectNum);
                	//调用存储过程进行差异审核
                    srvAssetCategoryDAO.SyncEam();
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_PAGE_CUST_DETAIL);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步MIS转资清单结束！");
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_FA_PAGE_CUST_DETAIL, conn);
                    String syncErrorCount = srvAssetCategoryDAO.getSyncErrorCount();
                    String syncSuccessCount  = String.valueOf(StrUtil.strToInt(syncTotalCount)-StrUtil.strToInt(syncErrorCount));
                    message = new Message();
                    if (syncErrorCount.equals("0")) {
                        if (syncTotalCount.equals("0")) {
                            message.setMessageValue("同步MIS转资清单完成，共同步"+syncTotalCount+"条数据！");
                        } else {
                            message.setMessageValue("同步MIS转资清单完成，共同步"+syncTotalCount+"条数据，全部同步成功！");
                        }
                    } else {
                        dtoParameter.setError("Y");
                        message.setMessageValue("同步MIS转资清单完成，共同步"+syncTotalCount+"条数据，成功"+syncSuccessCount+"条，失败"+syncErrorCount+"条，失败信息请点击查询按钮！");
                    }
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = "/srv/dealProjectDiversity/dealProjectDiversityCheck.jsp";
            } else if (action.equals("DETAIL_ACTION")) {
                RowSet rs = srvAssetCategoryDAO.getErrorRow();
                req.setAttribute("ERROR_ROWS", rs);
                forwardURL = SrvURLDefineList.PAGE_CUST_DETAIL_PAGE_ERROR;
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
            message.setMessageValue("系统错误！");
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

