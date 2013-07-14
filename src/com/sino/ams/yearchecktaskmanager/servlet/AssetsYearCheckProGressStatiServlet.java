package com.sino.ams.yearchecktaskmanager.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dao.AssetsYearCheckProGressStatiDAO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckProGressStatiDTO;
import com.sino.ams.yearchecktaskmanager.model.AssetsYearCheckProGressStatiModel;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * 年度盘点进度统计
 * @author Administrator
 *
 */
public class AssetsYearCheckProGressStatiServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "/yearchecktaskmanager/AssetsYearCheckProGressStati.jsp";
		Message message = new Message();
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            conn = getDBConnection(req); //获取数据库连接
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AssetsYearCheckProGressStatiDTO.class.getName());
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            ServletConfigDTO servletConfig = getServletConfig(req);
            AssetsYearCheckProGressStatiDTO dto = (AssetsYearCheckProGressStatiDTO) req2DTO.getDTO(req);
            AssetsYearCheckProGressStatiDAO dao = new AssetsYearCheckProGressStatiDAO(user,dto,conn);
            req.setAttribute("OU_OPTIONS", dao.getOrgnizationOption(String.valueOf(dto.getOrganizationId()), true));
            System.out.println(dto.getOrganizationOpt());
            if(action.equals("")){
            
            }else if(action.equals("QUERY_ACTION")){
            	//查询
            	BaseSQLProducer sqlProducer = new AssetsYearCheckProGressStatiModel(user,dto);
            	PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
            	pageDAO.setServletConfig(servletConfig);
            	pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
            }else if(action.equals("EXPORT_ACTION")) {
            	//导出
            	File file = dao.getExportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
                file.delete();
            }
        }catch(Exception ex){
        	ex.printStackTrace();
        	message.setIsError(true);
        	message.setMessageKey(ex.getMessage());
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
        }
	}
}
