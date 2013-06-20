package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.constant.WebAttrConstant;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ItemMainBottomServlet extends BaseServlet {

    /**
     * 所有的Servlet都必须实现的方法。
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            conn = getDBConnection(req);
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            
            String specialDepOpt = optProducer.getSpecialAsssetsDeptOption( "" );
            req.setAttribute("DEPT_OPTIONS2", specialDepOpt);
            
            String deptOpt = optProducer.getAllDeptOption("");
            req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);
            String itemStatus = optProducer.getDictOption(AssetsDictConstant.ITEM_STATUS, "");
		    req.setAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS, itemStatus);
            String itemUnitOpt = optProducer.getItemUnitOption("");
		    req.setAttribute(AssetsWebAttributes.ITEM_UNIT_OPTIONS, itemUnitOpt);
            String discardTypeOption = optProducer.getDictOption(AssetsDictConstant.DIS_TYPE, "");
            req.setAttribute(AssetsWebAttributes.ITEM_DISCARD_OPTIONS, discardTypeOption);
            String dealTypeOption = optProducer.getDictOption(AssetsDictConstant.DEAL_TYPE, "");
            req.setAttribute(AssetsWebAttributes.DEAL_TYPE_OPTIONS, dealTypeOption);
            String shareOption = optProducer.getDictOption("SHARE_STATUS", "");
		    req.setAttribute("SHARE_OPTION", shareOption);
            String constructStatusOption = optProducer.getDictOption("CONSTRUCT_STATUS", "");
		    req.setAttribute("CONSTRUCT_OPTION", constructStatusOption);
            forwardURL = AssetsURLList.ITEM_BOTTOM_PAGE;
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.QUERY_ERROR);
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
