package com.sino.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public abstract class PubServlet extends HttpServlet {

    /**
     * 所有的Servlet都必须实现的方法。
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public abstract void performTask(HttpServletRequest req,
                                     HttpServletResponse res) throws
            ServletException, IOException;

    /**
     * 功能：将doGet方法转向doPost方法处理。
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        super.doPost(req, res);
    }

    /**
     * 由Web服务器调用的方法。由其调用performTask方法。
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void service(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        performTask(req, res);
    }
}
