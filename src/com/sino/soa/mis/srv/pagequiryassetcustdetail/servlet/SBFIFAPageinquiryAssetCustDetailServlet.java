package com.sino.soa.mis.srv.pagequiryassetcustdetail.servlet;

import com.sino.ams.bean.OrgOptionProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.zz.proj2mgr.mapping.bean.MappingUtility;
import com.sino.base.constant.db.QueryConstant;
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
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.common.SrvWebActionConstant;
import com.sino.soa.mis.srv.pagequiryassetcustdetail.dao.SBFIFAPageinquiryAssetCustDetailDAO;
import com.sino.soa.mis.srv.pagequiryassetcustdetail.dto.SBFIFAPageinquiryAssetCustDetailDTO;
import com.sino.soa.mis.srv.pagequiryassetcustdetail.srv.SBFIFAPageinquiryAssetCustDetailSrv;
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
 * Date: 2011-10-15
 * Time: 21:14:01
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFAPageinquiryAssetCustDetailServlet extends BaseServlet {
	
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        int count = 0;
        long resumeTime = 0;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);            
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SBFIFAPageinquiryAssetCustDetailDTO.class.getName());
            SBFIFAPageinquiryAssetCustDetailDTO dtoParameter = (SBFIFAPageinquiryAssetCustDetailDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            
            //String projects = MappingUtility.getAuthorizedProjects(user.getUserId(), conn);
            //req.setAttribute("projects", projects);
            
            SBFIFAPageinquiryAssetCustDetailDAO srvAssetCategoryDAO = new SBFIFAPageinquiryAssetCustDetailDAO(user, dtoParameter, conn);
            OrgOptionProducer optionProducer = new OrgOptionProducer(user, conn);
            String range = "N";//��TD
            String opt = optionProducer.getBookTypeCodeOption(dtoParameter.getBookTypeCode(), range);
            dtoParameter.setOrgOption(opt);
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            String projectNum=dtoParameter.getProjectNumber();
            if (action.equals("")) {
                forwardURL = SrvURLDefineList.PAGE_CUST_DETAIL_PAGE;
            } else if (action.equals(SrvWebActionConstant.QUERY_ACTION)) {
                SBFIFAPageinquiryAssetCustDetailSrv service = new SBFIFAPageinquiryAssetCustDetailSrv();
                String bookTypeCode = req.getParameter("bookTypeCode");
                service.execute();
                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag() != null && srm.getErrorFlag().equals("Y")) {
                    DTOSet ds = service.getDs();
                    req.setAttribute(SrvWebActionConstant.ASSETBOOKTRANSOU, ds);
                    forwardURL = SrvURLDefineList.PAGE_CUST_DETAIL_PAGE;
                }
            } else if (action.equals(SrvWebActionConstant.INFORSYN)) {
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_FA_PAGE_CUST_DETAIL);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("ͬ��MISת���嵥��ʼ");
                logUtil.synLog(logDTO, conn);

                SBFIFAPageinquiryAssetCustDetailSrv service = new SBFIFAPageinquiryAssetCustDetailSrv();
                service.setBookTypeCode(dtoParameter.getBookTypeCode());
                service.setProjectNumber(dtoParameter.getProjectNumber());
                service.execute();

                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag().equalsIgnoreCase("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_FA_PAGE_CUST_DETAIL, conn);
                    DTOSet ds = service.getDs();
                    srvAssetCategoryDAO.deleteImpData(projectNum);
                    srvAssetCategoryDAO.deleteLogData();
                    if (ds.getSize() > 0) {
                        count = srvAssetCategoryDAO.SavaAssetCategory(ds);
                    }
                    //��Ҫͬ��������(�ų�����Ҫ����ETS_ITEM_INFO����ʱ������)
                	String syncTotalCount = srvAssetCategoryDAO.getSyncTotalCount(projectNum);
                    srvAssetCategoryDAO.SyncEam();
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_PAGE_CUST_DETAIL);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("ͬ��MISת���嵥������");
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_FA_PAGE_CUST_DETAIL, conn);
                    String syncErrorCount = srvAssetCategoryDAO.getSyncErrorCount();
                    String syncSuccessCount  = String.valueOf(StrUtil.strToInt(syncTotalCount)-StrUtil.strToInt(syncErrorCount));
                    message = new Message();
                    if (syncErrorCount.equals("0")) {
                        if (syncTotalCount.equals("0")) {
                            message.setMessageValue("ͬ��MISת���嵥��ɣ���ͬ��"+syncTotalCount+"�����ݣ�");
                        } else {
                            message.setMessageValue("ͬ��MISת���嵥��ɣ���ͬ��"+syncTotalCount+"�����ݣ�ȫ��ͬ���ɹ���");
                        }
                    } else {
                        dtoParameter.setError("Y");
                        message.setMessageValue("ͬ��MISת���嵥��ɣ���ͬ��"+syncTotalCount+"�����ݣ��ɹ�"+syncSuccessCount+"����ʧ��"+syncErrorCount+"����ʧ����Ϣ������ѯ��ť��");
                    }
                }else{
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_PAGE_CUST_DETAIL);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("ͬ��MISת���嵥ʧ�ܣ��ʲ��˲���"+dtoParameter.getBookTypeCode()+", ��Ŀ���:"+dtoParameter.getProjectNumber()+", ������Ϣ:"+srm.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message = new Message();
                    message.setMessageValue("ͬ��MISת���嵥ʧ�ܣ��ʲ��˲���"+dtoParameter.getBookTypeCode()+", ��Ŀ���:"+dtoParameter.getProjectNumber()+", ������Ϣ:"+srm.getErrorMessage());
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.PAGE_CUST_DETAIL_PAGE;
            } else if (action.equals("DETAIL_ACTION")) {
                RowSet rs = srvAssetCategoryDAO.getErrorRow();
                req.setAttribute("ERROR_ROWS", rs);
                forwardURL = SrvURLDefineList.PAGE_CUST_DETAIL_PAGE_ERROR;
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message.setMessageValue("ͬ��ʧ��");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
        } catch (DTOException ex) {
            ex.printLog();
            message.setMessageValue("ͬ��ʧ��");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
        } catch (DatatypeConfigurationException ex1) {
            message.setMessageValue("ͬ��ʧ��");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
            ex1.printStackTrace();
        } catch (TimeException e) {
            message.setMessageValue("ͬ��ʧ��");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
            e.printStackTrace();
        } catch (Exception e) {
            message.setMessageValue("ͬ��ʧ��");
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