package com.sino.td.synAssets.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.td.commom.TdURLDefineList;
import com.sino.td.synAssets.dao.TdAssetsChangeSynDAO;
import com.sino.td.synAssets.model.TdAssetsChangeSynModel;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: TD资产变动直接同步，用于地市公司同步责任部门、地点的变更</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2007~2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class TdAssetsChangeSynServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            ServletConfigDTO servletConfig = getServletConfig(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EamSyschronizeDTO.class.getName());
            EamSyschronizeDTO dto = (EamSyschronizeDTO) req2DTO.getDTO(req);
            String action = dto.getAct();
            conn = getDBConnection(req);
            TdAssetsChangeSynDAO dirSynchDAO = new TdAssetsChangeSynDAO(user, dto, conn);
            dirSynchDAO.setServletConfig(servletConfig);
            OptionProducer optProducer = new OptionProducer(user, conn);
            String opt = "";
            int organizationId = StrUtil.isEmpty(dto.getOrganizationId()) ? user.getOrganizationId() : dto.getOrganizationId();
            if (action.equals("")) {
                opt = optProducer.getTdOrganization(StrUtil.nullToString(organizationId));
                dto.setOrganizationOpt(opt);
                req.setAttribute(WebAttrConstant.SYSCHRONIZE_DTO, dto);
                forwardURL = TdURLDefineList.TD_ASSETS_CHANGE_SYN_PAGE;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) { //查询操作
                BaseSQLProducer sqlProducer = new TdAssetsChangeSynModel(user, dto);
                sqlProducer.setServletConfig(servletConfig);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);

                pageDAO.setCalPattern(LINE_PATTERN);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("ASSET_ID");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.produceWebData();

                opt = optProducer.getTdOrganization(StrUtil.nullToString(organizationId));
                dto.setOrganizationOpt(opt);
                req.setAttribute(WebAttrConstant.SYSCHRONIZE_DTO, dto);
                forwardURL = TdURLDefineList.TD_ASSETS_CHANGE_SYN_PAGE;
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) { //导出操作
                File file = dirSynchDAO.getExportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals(WebActionConstant.SYSCHRONIZE_ACTION)) { //同步操作
                RequestParser parser = new RequestParser();
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.setIgnoreOtherField(true);
                parser.setCheckBoxProp(checkProp);
                parser.transData(req);
                String[] assetIds = parser.getParameterValues("assetId");
                if (assetIds != null && assetIds.length > 0) {
                    dirSynchDAO.syschronizeAssets(StrUtil.nullToString(organizationId), assetIds);
                    message = dirSynchDAO.getMessage();
                }
                forwardURL = "/servlet/com.sino.td.synAssets.servlet.TdAssetsChangeSynServlet?act=QUERY_ACTION";
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
        } catch (UploadException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
