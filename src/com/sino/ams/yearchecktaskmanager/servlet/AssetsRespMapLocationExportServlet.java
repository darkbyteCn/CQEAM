package com.sino.ams.yearchecktaskmanager.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dao.AssetsRespMapLocationDAO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsRespMapLocationDTO;
import com.sino.ams.yearchecktaskmanager.util.ExcelReader;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
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
public class AssetsRespMapLocationExportServlet extends BaseServlet {

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
				//导出
            File file = dao.getExportFile();
            WebFileDownload fileDown = new WebFileDownload(req, res);
            fileDown.setFilePath(file.getAbsolutePath());
		    fileDown.download();
            file.delete();
		} catch (Exception e) {
			message.setMessageValue("失败,"+e.getMessage());
			message.setIsError(true);
			//forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
	
	
	//校验数据
	private boolean verifyDataAndUpdate(DTOSet dtoSet,AssetsRespMapLocationDAO dao) throws SQLModelException, QueryException, DataHandleException{
		boolean result = true;
		for(int i=0;i<dtoSet.getSize();i++){
			AssetsRespMapLocationDTO dto = (AssetsRespMapLocationDTO) dtoSet.getDTO(i);
			
			boolean isHaveValue = true;
			//是否填写值校验
			if(dto.getEmployeeNumber() == null || dto.getEmployeeNumber().equals("")){
				result = false;
				isHaveValue = false;
				dao.updateError(dto, "[员工编号]不能为空.");
			}else if(dto.getUserName() == null || dto.getUserName().equals("")){
				result = false;
				isHaveValue = false;
				dao.updateError(dto, "[姓名]不能为空.");
			}else if(dto.getWorkOrderObjectCode() == null || dto.getWorkOrderObjectCode().equals("")){
				result = false;
				isHaveValue = false;
				dao.updateError(dto, "[无线地点组合编号]不能为空.");
			}
			
			//空值校验通过后，检验数据的有效性
			if(isHaveValue){
				//新增和修改公共校验部分
				if(dao.validateWorkOrderObjectCodehasRepeat(dto.getWorkOrderObjectCode())){
					dao.updateError(dto,"[无线地点组合编号]重复.");
					result = false;
				}else if(!(dao.validateWorkOrderObjectCode(dto.getWorkOrderObjectCode()))){
					dao.updateError(dto, "[无线地点组合编号]在系统中不存在.");
					result = false;
				}else if(!(dao.validateEmployeeNumber(dto.getEmployeeNumber()))){
					dao.updateError(dto, "[员工编号]在系统中不存在.");
					result = false;
				}
			}
		}
		return result;
	}
	
	//解析request,获取上传文件路径
	/*private String resolveRequest(HttpServletRequest req,Connection conn)throws Exception{
		String fileName = null;
		RequestParser reqPar = new RequestParser();
		reqPar.transData(req);
		String act = reqPar.getParameter("act");
		UploadFile[] upFiles = null;
		UploadRow uploadRow;
		String conFilePath = PDAUtil.getCurUploadFilePath(conn);
		UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
		uploadFileSaver.saveFiles(conFilePath);
		uploadRow = uploadFileSaver.getRow();
		upFiles = uploadRow.getFiles();
		if (upFiles == null) {
			return fileName;
		} else if (upFiles.length != 1 || upFiles[0].getFileName().equals("")) {
			return fileName;
		}
		UploadFile uploadFile = upFiles[0];
		fileName = uploadFile.getAbsolutePath();
		return fileName;
	}*/
}
