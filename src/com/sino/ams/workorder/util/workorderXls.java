package com.sino.ams.workorder.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.FileSizeException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.XLSException;
import com.sino.base.file.parse.XLSParser;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.PDAUtil;

/**
 * User: zhoujs
 * Date: 2008-4-8
 * Time: 17:25:58
 * Function:
 */
public class workorderXls extends BaseServlet {
	private static final String sContentType = "text/html;charset=GBK";

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		String returnValue="";
		res.setContentType(sContentType);

		try {
			conn = DBManager.getDBConnection();
			RequestParser reqPar = new RequestParser();
			reqPar.transData(req);
			UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
			uploadFileSaver.saveFiles(PDAUtil.getCurUploadFilePath(conn));
			UploadFile[] upFiles = null;
			UploadRow uploadRow = uploadFileSaver.getRow();

			upFiles = uploadRow.getFiles();
			if (upFiles != null && upFiles.length > 0) {
				String fl = upFiles[0].getAbsolutePath();
				
				/*String f2=file.getPath();
				//File file=new File(fl);
				System.out.println(f2+"\n"+fl);*/
				XLSParser xlsParser = new XLSParser();
				RowSet rs = xlsParser.parseXLSData(fl);
				if (rs != null && rs.getSize() > 0) {
					for (int i = 0; i < rs.getSize(); i++) {
						if (i==rs.getSize()-1) {
							 returnValue+="'"+rs.getRow(i).getStrValue(0)+"'";
						}else{
							returnValue+="'"+rs.getRow(i).getStrValue(0)+"',";
						}
					}
				}
			}

			forwardURL="/public/windowClose.jsp?retValue="+returnValue;

		} catch (PoolException e) {
			e.printStackTrace();
		} catch (UploadException e) {
			e.printStackTrace();
		} catch (ContainerException e) {
			e.printStackTrace();
		} catch (XLSException e) {
			e.printStackTrace();
		} catch (FileSizeException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
