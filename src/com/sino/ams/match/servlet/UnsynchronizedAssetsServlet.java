package com.sino.ams.match.servlet;

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
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.synchronize.dao.AssetsUpdateDAO;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.synchronize.model.AssetsUpdateModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

    /**
     * Created by IntelliJ IDEA.
     * User: jialongchuan
     * Date: 2008-9-7
     * Time: 19:45:16
     * To 未同步资产清单
     */
    public class UnsynchronizedAssetsServlet extends BaseServlet {
        public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
            String forwardURL = "";
            Message message = SessionUtil.getMessage(req);
            String action = req.getParameter("act");
            action = StrUtil.nullToString(action);
            Connection conn = null;
            try {
                SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
                Request2DTO req2DTO = new Request2DTO();
                req2DTO.setDTOClassName(EamSyschronizeDTO.class.getName());
                EamSyschronizeDTO dto = (EamSyschronizeDTO) req2DTO.getDTO(req);
                conn = getDBConnection(req);
                AssetsUpdateDAO assetsUpdateDAO = new AssetsUpdateDAO(user, dto, conn);
                dto.getProjectNumber();
                OptionProducer optProducer = new OptionProducer(user, conn);
                ServletConfigDTO configDTO = getServletConfig(req);
                if (action.equals("")) {
                    String opt = optProducer.getDeptOption(dto.getDeptNameOption());
                    dto.setDeptNameOption(opt);
                    req.setAttribute(WebAttrConstant.SYSCHRONIZE_DTO, dto);
                    forwardURL = URLDefineList.UNSYNCHRONIZED_ASSETS;
                } else if (action.equals(WebActionConstant.QUERY_ACTION)) {  //查询操作
                    BaseSQLProducer sqlProducer = new AssetsUpdateModel(user, dto);
                    PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                    pageDAO.setCalPattern(LINE_PATTERN);
                    pageDAO.setServletConfig(configDTO);
                    pageDAO.produceWebData();

                    String opt = optProducer.getDeptOption(dto.getDeptNameOption());
                    dto.setDeptNameOption(opt);
                    message = assetsUpdateDAO.getMessage();
                    req.setAttribute(WebAttrConstant.SYSCHRONIZE_DTO, dto);
                   forwardURL = URLDefineList.UNSYNCHRONIZED_ASSETS;
               } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {  //导出操作
                   File file = assetsUpdateDAO.getExportFile();
                   WebFileDownload fileDown = new WebFileDownload(req, res);
                   fileDown.setFilePath(file.getAbsolutePath());
                   fileDown.download();
                   file.delete();
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
            } finally {
                DBManager.closeDBConnection(conn);
                setHandleMessage(req, message);
                if(!StrUtil.isEmpty(forwardURL)){
                    ServletForwarder forwarder = new ServletForwarder(req, res);
                    forwarder.forwardView(forwardURL);
                }
            }

        }
    }


