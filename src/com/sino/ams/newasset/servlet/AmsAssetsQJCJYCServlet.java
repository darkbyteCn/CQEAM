package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dao.AmsAssetsQJCJYCDAO;
import com.sino.ams.newasset.dto.AmsAssetsCJYCDTO;
import com.sino.ams.newasset.model.AmsAssetsQJCJYCModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.config.SinoConfig;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-17
 * Time: 18:12:32
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsQJCJYCServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            conn = getDBConnection(req);
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsCJYCDTO.class.getName());
            AmsAssetsCJYCDTO dtoParameter = (AmsAssetsCJYCDTO) req2DTO.getDTO(req);
            AmsAssetsQJCJYCDAO dao = new AmsAssetsQJCJYCDAO(user, dtoParameter, conn);
            ServletConfigDTO sevletConfig = SessionUtil.getServletConfigDTO(req);
            dtoParameter.setFaCategory1(SinoConfig.getFaCat1Mis());
            dtoParameter.setFaCategory2(SinoConfig.getFaCat2Mis());
            dtoParameter.setFaCategory3(SinoConfig.getFaCat3Mis());

            String action = req.getParameter("act");
            action = StrUtil.nullToString(action);
            OptionProducer op = new OptionProducer(user, conn);
            int organizationId = StrUtil.strToInt(dtoParameter.getBookTypeCode());
            String companySelect = op.getAllOrganization(organizationId, true);
            req.setAttribute("OU", companySelect);
            String monthOption = op.getFromMonthOption(dtoParameter.getFromMonth());
            String tomonthOption = op.getFromMonthOption(dtoParameter.getToMonth());
            String bookTypeCode = dtoParameter.getBookTypeCode();
            String bookSelect = op.getAllBookTypeName(bookTypeCode, true);
            String catSegment1 = dtoParameter.getCatSegment1();
            String catSegment1Servlet = op.getUseField(catSegment1, true);
            req.setAttribute("CATSEGMENT1", catSegment1Servlet);
            req.setAttribute("BOOK_TYPE_CODE", bookSelect);
            req.setAttribute("FROM_MONTH_OPTION", monthOption);
            req.setAttribute("TO_MONTH_OPTION", tomonthOption);
            req.setAttribute("AMSBJTRANSNOHDTO", dtoParameter);
            if (action.equals("")) {
                forwardURL = "/newasset/assetsQJCJYC.jsp";
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsAssetsQJCJYCModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.setCountPages(false);
                pageDAO.produceWebData();
                req.setAttribute("AMSBJTRANSNOHDTO", dtoParameter);
                forwardURL = "/newasset/assetsQJCJYC.jsp";
            }else if(action.equals(AssetsActionConstant.EXPORT_ACTION)){
                  File file = dao.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
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