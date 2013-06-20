package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dao.AmsAssetsNoMatchDetailDAO;
import com.sino.ams.newasset.dto.AmsAssetsNoMatchDTO;
import com.sino.ams.newasset.model.AmsAssetsNoMatchDetailModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
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
 * Date: 2008-3-14
 * Time: 16:06:22
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsNoMatchDetailServlet extends BaseServlet {


    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsNoMatchDTO.class.getName());
            AmsAssetsNoMatchDTO dtoParameter = (AmsAssetsNoMatchDTO) req2DTO.
                                               getDTO(req);
            AmsAssetsNoMatchDetailDAO dao = new AmsAssetsNoMatchDetailDAO(user,
                    dtoParameter, conn);
            conn = getDBConnection(req);
            String action = req.getParameter("act");
            action = StrUtil.nullToString(action);
            OptionProducer op = new OptionProducer(user, conn);
            int organizationId = dtoParameter.getOrganizationId();
            String companySelect = op.getAllOrganization(organizationId, true);
            req.setAttribute("OU", companySelect);
            req.setAttribute("AMSBJTRANSNOHDTO", dtoParameter);
            if (action.equals("")) {
                forwardURL = "/newasset/assetsNoMatchDetailQuery.jsp";
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsAssetsNoMatchDetailModel(
                        user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
//				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
//				checkProp.addDbField("BATCH_ID");
//				pageDAO.setWebCheckProp(checkProp);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
//				if(dtoParameter.getSrcPage().equals(AssetsActionConstant.QUERY_ACTION)){
//					AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
//					dtoParameter = optProducer.fillBatchStatus(dtoParameter);
//				}
//				AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
//				String deptOptions = optProducer.getUserAsssetsDeptOption(dtoParameter.getCheckDept());
//				dtoParameter.setCheckDeptOption(deptOptions);
                req.setAttribute("AMSBJTRANSNOHDTO", dtoParameter);
                forwardURL = "/newasset/assetsNoMatchDetailQuery.jsp";
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
            closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }

    }
}
