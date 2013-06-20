package com.sino.soa.mis.srv.assettransbtwcompanysrv.servlet;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.util.ArrUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.common.MessagePrint;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.common.SrvWebActionConstant;
import com.sino.soa.mis.srv.assettransbtwcompanysrv.dao.SBFIFAAssetsTransBtwCompanyDAO;
import com.sino.soa.mis.srv.assettransbtwcompanysrv.dto.SBFIFAAssetsTransBtwCompanyDTO;
import com.sino.soa.mis.srv.assettransbtwcompanysrv.model.SBFIFAAssetsTransBtwCompanyModel;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.importsrvresponse.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-15
 * Time: 15:41:50
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFAAssetsTransBtwCompanyServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		MessagePrint message=new MessagePrint();
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SBFIFAAssetsTransBtwCompanyDTO.class.getName());
			SBFIFAAssetsTransBtwCompanyDTO dto = (SBFIFAAssetsTransBtwCompanyDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			SBFIFAAssetsTransBtwCompanyDAO commitDAO = new SBFIFAAssetsTransBtwCompanyDAO(user, dto, conn);
			ServletConfigDTO servletConfig = getServletConfig(req);
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = SrvURLDefineList.ASSETS_BTW_COMMITS;
			} else if (action.equals(SrvWebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new SBFIFAAssetsTransBtwCompanyModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setServletConfig(servletConfig);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("SYSTEMID");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = SrvURLDefineList.ASSETS_BTW_COMMITS;
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) { //导出
	            File file = commitDAO.getExportFile();
	            WebFileDownload fileDown = new WebFileDownload(req, res);
	            fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
	            file.delete();
	        } else if (action.equals(SrvWebActionConstant.INFORSYN)) {
			    long startTime= System.currentTimeMillis();
			    String[] systemidss = req.getParameterValues("systemids");
                String systemids = arrToStr(systemidss, ",");
				String userRespExists = commitDAO.syschronizeAssets(systemids);
                if (userRespExists.equals("Y")) {
                    SrvReturnMessage returnMessage = commitDAO.getReturnMessage();
                    String msgValue = "";
                    if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                        msgValue = "资产调拨导入成功，耗时" + (System.currentTimeMillis() - startTime) + "毫秒";
                        msgValue+="\n"+returnMessage.getErrorMessage();
                        message.setMessageValue(msgValue);
                        message.setSuccess(true);
                    } else {
                        msgValue = "资产调拨导入失败，耗时" + (System.currentTimeMillis() - startTime) + "毫秒";
                        msgValue+="\n"+returnMessage.getErrorMessage();
                        message.setMessageValue(msgValue);
                        message.setSuccess(false);
                    }
                } else {
                    message.setMessageValue("请设置同步用户后再操作！");
                    message.setSuccess(false);
                }
                forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
			}
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message.setMessageValue("同步失败");
			message.setSuccess(false);
			forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
		} catch (DTOException ex) {
			ex.printLog();
			message.setMessageValue("同步失败");
			message.setSuccess(false);
			forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
		} catch (Exception e) {
			message.setMessageValue("同步失败");
			message.setSuccess(false);
			forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			req.setAttribute("MESSAGEPRINT", message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}

    private String arrToStr(String[] srcArr, String linkStr) {
        String retStr = "";
        if (srcArr != null && srcArr.length > 0) {
            boolean hasProcessed = false;
            for (int i = 0; i <= srcArr.length - 1; i++) {
                retStr += "'"+srcArr[i]+"'" + linkStr;
                hasProcessed = true;
            }
            if (hasProcessed) {
                retStr = retStr.substring(0, retStr.length() - linkStr.length());
            }
        }
        return retStr;
    }
}