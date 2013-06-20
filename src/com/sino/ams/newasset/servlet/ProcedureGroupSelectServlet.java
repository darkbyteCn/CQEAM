package com.sino.ams.newasset.servlet;

import com.sino.ams.newasset.service.ProcedureGroupSelectService;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ProcedureGroupSelectServlet extends BaseServlet {
	/**
	 * 所有的Servlet都必须实现的方法。
	 *
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Connection conn = null;
		try {
            conn = getDBConnection(req);
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
            ProcedureGroupSelectService service = new ProcedureGroupSelectService(user, null, conn);
            service.setServletConfig(getServletConfig(req));
            String groups = service.getProSpecialGroups();
            processAjaxResponse(groups, res);
		} catch (Throwable ex) {
            Logger.logError(ex);
		} finally {
			closeDBConnection(conn);
			if (!forwardURL.equals("")) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}


    /**
     * 功能：处理Ajax请求
     *
     * @param responseResult     Http响应内容
     * @param res     Http响应对象
     * @throws java.io.IOException 响应Ajax出错时抛出该异常
     */
    private void processAjaxResponse(String responseResult, HttpServletResponse res) throws IOException {
        if (responseResult != null) {
            res.setContentType("text/plain;charset=GBK");
            PrintWriter out = res.getWriter();
            if (!StrUtil.isEmpty(out)) {
                out.print(responseResult);
                System.out.println(responseResult);
            }
            out.close();
        }
    }
}