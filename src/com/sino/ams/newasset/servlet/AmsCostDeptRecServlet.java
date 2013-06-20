package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.dao.AmsCostDeptRecDAO;
import com.sino.ams.newasset.model.AmsCostDeptRecModel;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-8-26
 * Time: 16:50:00
 * To change this template use File | Settings | File Templates.
 */
public class AmsCostDeptRecServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsItemInfoDTO.class.getName());
            EtsItemInfoDTO dto = (EtsItemInfoDTO) req2DTO.getDTO(req);
            String action = dto.getAct();
            conn = getDBConnection(req);
            AmsCostDeptRecDAO costDeptRecDAO = new AmsCostDeptRecDAO(user, dto, conn);
            if (action.equals("")) {
                req.setAttribute(WebAttrConstant.ETS_ITEM_DTO, dto);
                forwardURL = AssetsURLList.COST_DEPT_REC_MATCH;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsCostDeptRecModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.setPageSize(100);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("COUNTY_CODE");
                checkProp.addDbField("DEPT_CODE");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.produceWebData();
                req.setAttribute(WebAttrConstant.ETS_ITEM_DTO, dto);
                forwardURL = AssetsURLList.COST_DEPT_REC_MATCH;
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
                File file = costDeptRecDAO.getExportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
                DTOSet checkedInfo = getCheckedInfo(req);
				costDeptRecDAO.delDTOs(checkedInfo);
                message = costDeptRecDAO.getMessage();
                forwardURL = "/servlet/com.sino.ams.newasset.servlet.AmsCostDeptRecServlet?act=QUERY_ACTION";
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
        } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (StrException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }

    private DTOSet getCheckedInfo(HttpServletRequest req) throws ServletException {
		DTOSet dtos = new DTOSet();
		try {
			RequestParser parser = new RequestParser();
			CheckBoxProp checkProp = new CheckBoxProp("subCheck");
			checkProp.setIgnoreOtherField(true);
			parser.setCheckBoxProp(checkProp);
			parser.transData(req);
			String[] countyCodes = parser.getParameterValues("countyCode");
			String[] deptCodes = parser.getParameterValues("deptCode");
            int checkCount = countyCodes.length;
            for (int i = 0; i<checkCount; i++) {
                EtsItemInfoDTO dto = new EtsItemInfoDTO();
                dto.setCountyCode(countyCodes[i]);
                dto.setResponsibilityDept(deptCodes[i]);
                dtos.addDTO(dto);
            }
		} catch (UploadException ex) {
			ex.printLog();
			throw new ServletException(ex);
		} catch (StrException ex) {
			ex.printLog();
			throw new ServletException(ex);
		} catch (DTOException ex) {
			ex.printLog();
			throw new ServletException(ex);
		}
		return dtos;
	}
}
