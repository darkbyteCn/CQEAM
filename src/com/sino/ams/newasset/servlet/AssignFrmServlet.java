package com.sino.ams.newasset.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AssignFrmServlet extends BaseServlet {

    /**
     * 所有的Servlet都必须实现的方法。
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        SfUserDTO user = (SfUserDTO) getUserAccount(req);
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        boolean hasError = false;
        try {
//			if (user.getEmployeeNumber().equals("")) {
//				message = getMessage(AssetsMessageKeys.HAS_NO_MIS_NUMBER);
//				message.setIsError(true);
//				message.setNeedClose(true);
//				forwardURL = MessageConstant.MSG_PRC_SERVLET;
//				hasError = true;
//			} else {
            forwardURL = AssetsURLList.ASSIGN_USER_SERVLET;
            req.setAttribute(AssetsWebAttributes.ASSETS_DYNAMIC_URL, forwardURL);
            req.setAttribute(AssetsWebAttributes.ASS_PROP,
                             AssetsWebAttributes.ASSIGN_MAINTAIN_USER);
//			}
        } finally {
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (hasError) {
                forwarder.forwardView(forwardURL);
            } else {
                forwarder.forwardView(AssetsURLList.ASSETS_ASSIGN_FRM);
            }
        }
//		if(user.isComAssetsManager()){//公司资产管理员，可分配部门
//			forwardURL = AssetsURLList.ASSIGN_DEPT_SERVLET;
//		} else if(user.isDptAssetsManager()){//部门资产管理员，可分配责任人
//			forwardURL = AssetsURLList.ASSIGN_LEFT_SERVLET;
//		} else {//普通用户，可分配资产使用人
//			forwardURL = AssetsURLList.ASSIGN_USER_SERVLET;
//		}
    }
}
