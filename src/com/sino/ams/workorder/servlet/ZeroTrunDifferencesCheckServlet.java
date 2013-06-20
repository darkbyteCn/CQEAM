package com.sino.ams.workorder.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;

import com.sino.ams.bean.OrgOptionProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.ZeroTurnDifferentCheckDAO;
import com.sino.ams.workorder.dto.ZeroTurnDifferentCheckDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.TimeException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.common.SrvWebActionConstant;
import com.sino.soa.mis.srv.pagequiryassetcustdetail.srv.SBFIFAPageinquiryAssetCustDetailSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;

public class ZeroTrunDifferencesCheckServlet extends BaseServlet{
	
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        int count = 0;
        long resumeTime = 0;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(ZeroTurnDifferentCheckDTO.class.getName());
            ZeroTurnDifferentCheckDTO dtoParameter = (ZeroTurnDifferentCheckDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            ZeroTurnDifferentCheckDAO srvAssetCategoryDAO = new ZeroTurnDifferentCheckDAO(user, dtoParameter, conn);
            OrgOptionProducer optionProducer = new OrgOptionProducer(user, conn);
            String range = "N",misProjectId = "",diffTypeCode = "";//非TD
            
            String bookTCode=dtoParameter.getBookTypeCode();     //资产账簿
            String procureCode = dtoParameter.getProcureCode();  //采购单号
            diffTypeCode = dtoParameter.getDiffTypeCode();       //差异类型
            
            //资产帐簿
            String opt = optionProducer.getBookTypeCodeOption(bookTCode, range);
            dtoParameter.setBookTypeCodeOption(opt);
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            String projectNum=dtoParameter.getProjectNumber();
            if (action.equals("")) {
            	forwardURL = "/workorder/zeroTrunDifferencesCheck.jsp";
            } else if (action.equals(SrvWebActionConstant.QUERY_ACTION)) {
            	if(!bookTCode.equals("") && !diffTypeCode.equals("") && !procureCode.equals("")){
            		RowSet rs = null;
            		rs=srvAssetCategoryDAO.getDataRow(bookTCode, procureCode, diffTypeCode);
            		req.setAttribute("DIVERSITYCHECK", rs);
            		forwardURL = "/workorder/zeroTrunDifferencesCheck.jsp";
            	}
            } else if (action.equals("checkEII")) {
					srvAssetCategoryDAO.checkEtsItemInfo(bookTCode, procureCode);
					req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
					forwardURL = "/workorder/zeroTrunDifferencesCheck.jsp";
            }else if (action.equals(WebActionConstant.EXPORT_ACTION)) { //导出
                File file = srvAssetCategoryDAO.getExportFile(bookTCode, procureCode, diffTypeCode);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message.setMessageValue("操作失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
        } catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DTOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WebFileDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }

}
