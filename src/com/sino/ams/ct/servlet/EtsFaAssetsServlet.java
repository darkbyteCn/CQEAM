package com.sino.ams.ct.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.ct.dao.EtsFaAssetsDAO;
import com.sino.ams.ct.dto.EtsFaAssetsDTO;
import com.sino.ams.ct.model.EtsFaAssetsModel;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 于士博
 * @version 1.0
 */
public class EtsFaAssetsServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            EtsFaAssetsDTO dtoParameter = new EtsFaAssetsDTO();
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsFaAssetsDTO.class.getName()); 
            dtoParameter = (EtsFaAssetsDTO)req2DTO.getDTO(req);
            conn = getDBConnection(req);
            EtsFaAssetsDAO etsFaAssetsDAO = new EtsFaAssetsDAO(user, dtoParameter, conn);
            /*
            OptionProducer optProducer = new OptionProducer(user, conn); //获取资产状态
            String assetsStatus = optProducer.getAllEtsFaAssets(dtoParameter.getAssetsStatus(), true);
            req.setAttribute(WebAttrConstant.ASSETSSTATUS_OPTION, assetsStatus);
            */

            if (action.equals("")) {
               /*
               forwardURL = URLDefineList.QRY_BY_ETS_FA_ASSETS_PAGE + "?qryType=" + dtoParameter.getQryType();
               */
            	
            	forwardURL = URLDefineList.QRY_BY_ETS_FA_ASSETS_PAGE ;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
            	
                BaseSQLProducer sqlProducer = new EtsFaAssetsModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                forwardURL = URLDefineList.QRY_BY_ETS_FA_ASSETS_PAGE ;
                
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
            	
                File file = etsFaAssetsDAO.exportFile();
                etsFaAssetsDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();

            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
            	
            	
            	etsFaAssetsDAO.setDTOClassName(EtsFaAssetsDTO.class.getName());
            	etsFaAssetsDAO.setCalPattern(LINE_PATTERN); 
            	dtoParameter = (EtsFaAssetsDTO) etsFaAssetsDAO.getDataByPrimaryKey();
                if (dtoParameter == null) {
                	dtoParameter = new EtsFaAssetsDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                
                req.setAttribute(AssetsWebAttributes.ASSETS_DATA, dtoParameter);
                
                forwardURL = URLDefineList.QRY_BY_ETS_FA_ASSETS_DETAIL_PAGE;
                
                
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
        } catch (DataTransException e) {
            e.printLog();
            Logger.logError(e.toString());
        } catch (WebFileDownException e) {
            e.printLog();
            Logger.logError(e.toString());
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
            //根据实际情况修改页面跳转代码。
        }
	}

}
