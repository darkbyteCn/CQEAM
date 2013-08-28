package com.sino.ams.yearchecktaskmanager.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dao.AssetsRespMapLocationDAO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsRespMapLocationDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * @author Administrator
 *
 */
public class AssetsRespMapCheckPersonsExportServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		String forwardURL = "/yearchecktaskmanager/assetsRespMapLocation.jsp";
		Message message = new Message();
		String action ="";
		Connection conn = null;
		action = StrUtil.nullToString(action);
		try {
			conn = DBManager.getDBConnection();
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AssetsRespMapLocationDTO dto = new AssetsRespMapLocationDTO();
			AssetsRespMapLocationDAO dao = new AssetsRespMapLocationDAO(user,dto,conn);
				//µ¼³ö
            File file = dao.getExportPersonsFile();
            WebFileDownload fileDown = new WebFileDownload(req, res);
            fileDown.setFilePath(file.getAbsolutePath());
		    fileDown.download();
            file.delete();
		} catch (Exception e) {
			message.setMessageValue("Ê§°Ü,"+e.getMessage());
			message.setIsError(true);
			//forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
