package com.sino.sinoflow.framework.resource.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.web.ServletForwarder;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.constant.URLDefineList;


/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ResourceFrmServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletForwarder forwarder = new ServletForwarder(req, res);
        forwarder.forwardView(URLDefineList.RES_FRM_PAGE);
    }
}
