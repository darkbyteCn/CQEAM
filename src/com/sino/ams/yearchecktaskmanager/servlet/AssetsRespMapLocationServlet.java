package com.sino.ams.yearchecktaskmanager.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.PDAUtil;

/**
 * @author Administrator
 *
 */
public class AssetsRespMapLocationServlet extends BaseServlet {

	private static final int startRowNum = 2; //从第几行开始读取

	private static final int columnNum = 4;
	
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		String forwardURL = "/yearchecktaskmanager/assetsRespMapLocation.jsp";
		Message message = new Message();
		String action ="";
		action = req.getParameter("act");
		Connection conn = null;
		action = StrUtil.nullToString(action);
		try {
			conn = DBManager.getDBConnection();
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AssetsRespMapLocationDTO dto = new AssetsRespMapLocationDTO();
			AssetsRespMapLocationDAO dao = new AssetsRespMapLocationDAO(user,dto, conn);
			String fileName = null; // 上传的文件名称

			RequestParser reqPar = new RequestParser();
			reqPar.transData(req);

			UploadFile[] upFiles = null;
			UploadRow uploadRow;
			String conFilePath = PDAUtil.getCurUploadFilePath(conn);
			UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
			uploadFileSaver.saveFiles(conFilePath);
			uploadRow = uploadFileSaver.getRow();
			upFiles = uploadRow.getFiles();
			if (upFiles == null) {
			} else if (upFiles.length != 1
					|| upFiles[0].getFileName().equals("")) {
			}
			UploadFile uploadFile = upFiles[0];
			fileName = uploadFile.getAbsolutePath();

			// excel文件上传
			Logger.logInfo("AuthorityBatchUpdateServlet-->Excel submit servlet begin....");
			// 1---解析request
			if (fileName == null) {
				throw new Exception("获取上传文件失败");
			}
			Logger.logInfo("AuthorityBatchUpdateServlet--> step 1 upload excel file completed.");

			// 2---读取excel文件内容到DTOSet
			ExcelReader xlsUtil = new ExcelReader();
			xlsUtil.setFileName(fileName);
			xlsUtil.setNumberOfColumn(columnNum);
			xlsUtil.setStartRowNum(startRowNum);
			DTOSet dtoSet = xlsUtil.readXls(0);

			Logger.logInfo("AuthorityBatchUpdateServlet--> step 2 excel file resoving completed.");
			// 3---删除临时表数据
			dao.deleteTmpByUser();
			Logger.logInfo("AuthorityBatchUpdateServlet--> step 3 delete tmp data completed.");
			// 4--保存数据到临时表
			dao.saveData(dtoSet);
			Logger.logInfo("AuthorityBatchUpdateServlet--> step 4 save data to tmp table completed.");
			// 5--数据校验
			boolean isSuccess = this.verifyDataAndUpdate(dtoSet, dao);
			Logger.logInfo("AuthorityBatchUpdateServlet--> step 5 validate data completed.");
			if (isSuccess) {
				// 7--数据校验通过后，开始新增或者修改权限
				dao.saveOrUpdateData(dtoSet);
				String msgValue = "批量盘点责任人和地点匹配关系数据成功!";
				message.setMessageValue(msgValue);
				message.setIsError(false);
			} else {
				String msgValue = "批量导入盘点责任人和地点匹配关系数据失败,请看具体行中错误信息!";
				message.setMessageValue(msgValue);
				message.setIsError(true);
			}

			// --返回结果
			RowSet rows = dao.getImportDataDetail();
			req.setAttribute(QueryConstant.SPLIT_DATA_VIEW, rows);
			forwardURL = "/yearchecktaskmanager/assetsRespMapLocationDetail.jsp";
				
		} catch (PoolPassivateException ex) {
			message.setMessageValue("失败,"+ex.getMessage());
			message.setIsError(true);
			//forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
			ex.printStackTrace();
		} catch (DTOException ex) {
			message.setMessageValue("失败,"+ex.getMessage());
			message.setIsError(true);
			//forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
			ex.printStackTrace();
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
