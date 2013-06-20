package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dao.AmsAssetsFLCJYCDAO;
import com.sino.ams.newasset.dto.AmsAssetsCJYCDTO;
import com.sino.ams.newasset.model.AmsAssetsFLCJYCModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-17
 * Time: 20:57:12
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsFLCJYCServlet extends BaseServlet {


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
            AmsAssetsFLCJYCDAO dao = new AmsAssetsFLCJYCDAO(user, dtoParameter, conn);

            String action = req.getParameter("act");
            action = StrUtil.nullToString(action);
            OptionProducer op = new OptionProducer(user, conn);
            int organizationId = StrUtil.strToInt(dtoParameter.getBookTypeCode());
            String companySelect = op.getAllOrganization(organizationId, true);
            req.setAttribute("OU", companySelect);

            String  bookTypeCode=dtoParameter.getBookTypeCode();
            String bookSelect=op.getAllBookTypeName(bookTypeCode,true) ;
            req.setAttribute("BOOK_TYPE_CODE",bookSelect);
            req.setAttribute("AMSBJTRANSNOHDTO", dtoParameter);
            if (action.equals("")) {
                forwardURL = "/newasset/assetsFLCJYC.jsp";
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsAssetsFLCJYCModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute("AMSBJTRANSNOHDTO", dtoParameter);
                forwardURL = "/newasset/assetsFLCJYC.jsp";
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
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }

    }
}
