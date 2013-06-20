package com.sino.soa.td.srv.assetsinfoupdate.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.ams.bean.OrgOptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.td.srv.assetsinfoupdate.dao.TDAssetsUpdateDAO;
import com.sino.soa.td.srv.assetsinfoupdate.dto.TDSrvEamSyschronizeDTO;
import com.sino.soa.td.srv.assetsinfoupdate.model.TDAssetsUpdateModel;

/**
 * <p>Title: AssetsUpdateServlet</p>
 * <p>Description:导入资产基本信息服务类“AssetsUpdateServlet”</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-26
 * Function:资产基本信息修改_TD
 */
public class TDAssetsUpdateServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        long resumeTime = 0;
        try {
            conn = getDBConnection(req);
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            ServletConfigDTO servletConfig = getServletConfig(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(TDSrvEamSyschronizeDTO.class.getName());
            TDSrvEamSyschronizeDTO dto = (TDSrvEamSyschronizeDTO) req2DTO.getDTO(req);
            String action = dto.getAct();
            TDAssetsUpdateDAO dirSynchDAO = new TDAssetsUpdateDAO(user, dto, conn);
            dirSynchDAO.setServletConfig(servletConfig);
            OrgOptionProducer optionProducer = new OrgOptionProducer(user, conn);
//            String opt = optionProducer.getOrgnizationOption(dto.getOrganizationId(), "Y");     //TD
            String opt = optionProducer.getSynOrgnizationOption(dto.getOrganizationId(), "Y");     //TD
            dto.setOrganizationOpt(opt);
            if (action.equals("")) {
                req.setAttribute(WebAttrConstant.SYSCHRONIZE_DTO, dto);
                forwardURL = SrvURLDefineList.TD_SRV_ASSETS_UPDATE;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {      //查询
                BaseSQLProducer sqlProducer = new TDAssetsUpdateModel(user, dto);
                sqlProducer.setServletConfig(servletConfig);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("ASSET_ID");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.produceWebData();
                req.setAttribute(WebAttrConstant.SYSCHRONIZE_DTO, dto);
                forwardURL = SrvURLDefineList.TD_SRV_ASSETS_UPDATE;
            } else if (action.equals(WebActionConstant.SYSCHRONIZE_ACTION)) {
                BaseSQLProducer sqlProducer = new TDAssetsUpdateModel(user, dto);
                sqlProducer.setServletConfig(servletConfig);
                long start = System.currentTimeMillis();
                RequestParser parser = new RequestParser();
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.setIgnoreOtherField(true);
                parser.setCheckBoxProp(checkProp);
                parser.transData(req);
                String[] assetIds = parser.getParameterValues("assetId");
                String isRespExist = dirSynchDAO.syschronizeAssets(dto.getOrganizationId(), assetIds);
                String msgValue = "";              
    		if(isRespExist.equals("NO")){
				msgValue = "请设置同步用户后再操作！";	
            	message = new Message();
                message.setMessageValue(msgValue);
                message.setIsError(true);
			}else{
			   message = dirSynchDAO.getMessage();
                resumeTime = System.currentTimeMillis() - start;
                SrvReturnMessage returnMessage = dirSynchDAO.getReturnMessage();
                if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                    message = new Message();
                    message.setMessageValue("导入TD资产基本信息成功!" + "耗时" + resumeTime + "毫秒");
                } else {
                    message = new Message();
                    message.setMessageValue("导入TD资产基本信息失败!" + "耗时" + resumeTime + "毫秒");
                }
			  }
                forwardURL = "/servlet/com.sino.soa.td.srv.assetsinfoupdate.servlet.TDAssetsUpdateServlet?act=QUERY_ACTION";
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
        } catch (StrException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (UploadException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CalendarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
