package com.sino.ams.spare.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.ams.spare.dao.ItemCountByOuDAO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.constant.SpareURLDefine;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-10-31
 */
public class ItemCountByOuServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = SpareURLDefine.SL_OU_PAGE;
        SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
        Message message = SessionUtil.getMessage(req);
        String showMsg = "";
        String action = req.getParameter("act");
        String itemCode = req.getParameter("itemCode");
        String transId = req.getParameter("transId");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            Request2DTO req2dto = new Request2DTO();
            req2dto.setDTOClassName(AmsItemTransHDTO.class.getName());
            AmsItemTransHDTO headerDTO = (AmsItemTransHDTO) req2dto.getDTO(req);
            conn = getDBConnection(req);
            ItemCountByOuDAO ouDAO = new ItemCountByOuDAO(user, headerDTO, conn);
            if (action.equals(WebActionConstant.QUERY_ACTION)) {

                req.setAttribute("OU_ITEM_COUNT", ouDAO.produceWebData(itemCode, transId));
            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {
                //写申领明细表
                Request2DTO r2d = new Request2DTO();
//                r2d.setDTOClassName(AmsItemTransDDTO.class.getName());
                DTOSet dtos = r2d.getDTOSet(req);

            }
        } catch (PoolPassivateException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
        } catch (QueryException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
        } catch (DTOException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder sf = new ServletForwarder(req, res);
            sf.forwardView(forwardURL);
        }

    }
}
