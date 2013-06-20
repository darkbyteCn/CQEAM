package com.sino.ams.print.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.ResNameConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.print.dao.BarcodeReceiveDAO;
import com.sino.ams.print.dto.BarcodeReceiveDTO;
import com.sino.ams.print.model.BarcodeReceiveModel;
import com.sino.ams.print.servlet.ReadBarcodeReceiveServlet;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.util.UserUtil;
import com.sino.ams.util.ResUtil;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.FileSizeException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.pda.PDAUtil;


public class BarcodeReceiveServlet extends BaseServlet {

	/**
	 * 
	 * Title: 			SinoApplication
	 * Description:		Java Enterprise Edition 应用开发
	 * Copyright: 		其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。
	 * Copyright: 		作者授权北京思诺博信息技术有限公司在一定范围内使用
	 * Company: 		北京思诺博信息技术有限公司
	 */
	public BarcodeReceiveServlet() {
		
	}

    private final static int startRowNum = 1;
    private final static int columnNum = 8;
	
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;		
		SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
		UserUtil sfUserUtil = new UserUtil(userAccount);
		String orgOption = "";
		String deptOption = "";
		PrintWriter pw = null;	
		JSONArray retArray = new JSONArray();
		String showMsg = "";
		try {		
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(BarcodeReceiveDTO.class.getName());			
			BarcodeReceiveDTO dtoParameter = (BarcodeReceiveDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();	
			sfUserUtil.setServletConfig(getServletConfig(req));
			
			BarcodeReceiveDAO barcodeReceiveDAO = new BarcodeReceiveDAO(userAccount, dtoParameter, conn);
			OptionProducer op = new OptionProducer(userAccount, conn);
			int organizationId = dtoParameter.getOrganizationId() == 0 || dtoParameter.getOrganizationId() == -1 ? userAccount.getOrganizationId() : dtoParameter.getOrganizationId();
			req.setAttribute(WebAttrConstant.BARCODE_RECEIVE_DTO, dtoParameter);
			
			if(userAccount.isProvinceUser()){
				if("".equals(dtoParameter.getOrganizationId())){
					orgOption = op.getAllOrganization(0, true);
				}else{
					orgOption = op.getAllOrganization(Integer.valueOf(organizationId), true);
				}					
			}else{
				orgOption = op.getOrganizationOpt(organizationId);
			}
			
			if (action.equals("")) {
				
				//栏目定义标头
				ResUtil.setAllResName(conn, req, ResNameConstant.BARCODE_RECEIVE );
				
				orgOption = op.getAllOrganization(0, true);   //用户所能选择的OU,就是地市
				deptOption = op.getDeptOption(userAccount.getCompanyCode());//得到地市下的所有领用部门
				
				req.setAttribute(WebAttrConstant.CITY_OPTION, orgOption);
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, deptOption);
				forwardURL = URLDefineList.BARCODE_RECEIVE_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {       //查询
				//栏目定义标头
				ResUtil.setAllResName(conn, req, ResNameConstant.BARCODE_RECEIVE );
				
				BaseSQLProducer sqlProducer = new BarcodeReceiveModel(userAccount, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                if(!"".equals(dtoParameter.getReceiveDept())){
    				deptOption = op.getDeptOption1(barcodeReceiveDAO.getCompanyCodeByOrganization(organizationId), dtoParameter.getReceiveDept());//得到地市下的所有领用部门
                }else {
                	deptOption = op.getDeptOption1(barcodeReceiveDAO.getCompanyCodeByOrganization(organizationId), "22");//得到地市下的所有领用部门
                }
				
                req.setAttribute(WebAttrConstant.CITY_OPTION, orgOption);
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, deptOption);
				forwardURL = URLDefineList.BARCODE_RECEIVE_PAGE;
				
            } else if (action.equals("ImportClaimBarcode")) {
                req.setAttribute("NOBARCODE_DATA", null);
                req.setAttribute(QueryConstant.SPLIT_DATA_VIEW, null);
                forwardURL = "/print/importClaimBarcode.jsp";
            } else if (action.equals("IMP_BARCODE_ACTION")) {
                Logger.logInfo("Excel submit servlet begin....");
                String conFilePath = PDAUtil.getCurUploadFilePath(conn);
                DTOSet dtoSet = parseExcel2DTO(req, conFilePath);
                message = new Message();
                if (dtoSet != null) {
                    //清除数据
                	barcodeReceiveDAO.deleteImportModel();
                    //导入到接口表AMS_DZYH_ASSETS_IMPORT
                	barcodeReceiveDAO.itemImportData(dtoSet);
                    //检查接口表数据的有效性。
                    showMsg = barcodeReceiveDAO.doVerifyData();
                    if (StrUtil.isEmpty(showMsg)) {//校验没有错误
                        showMsg = barcodeReceiveDAO.submitOrderDtl();
                        if (StrUtil.isEmpty(showMsg)) {
                            showMsg = "标签领用导入成功";
                            message.setMessageValue(showMsg);
                        } else {
                            message.setMessageValue(showMsg);
                            message.setIsError(true);
                        }
                        forwardURL = "/print/importClaimBarcode.jsp";
                        //forwardURL = "/servlet/com.sino.ams.print.servlet.BarcodeReceiveServlet?act=IMPORT_SUCESS";
                    } else {
                        message.setMessageValue(showMsg);
                        message.setIsError(true);
                        RowSet rows = barcodeReceiveDAO.getImportErrors();
                        req.setAttribute(WebAttrConstant.REC_BARCODE_DTO, rows);
                        forwardURL = "/print/importClaimBarcodeError.jsp";
                    }
                } else {
                    showMsg = "不能从上载的Excel中解析出正确的数据，请确认上载Excel与模板格式一致！";
                    message.setMessageValue(showMsg);
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                }
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出记录
				String excelType = req.getParameter("excelType");
				File file = barcodeReceiveDAO.exportFile(excelType);
				barcodeReceiveDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {      //点新增操作,转到新增页面
				dtoParameter = new BarcodeReceiveDTO();
				orgOption = op.getAllOrganization(userAccount.getOrganizationId());   //用户所能选择的OU,就是地市
				deptOption = op.getDeptOption(userAccount.getCompanyCode());//得到地市下的所有领用部门
				req.setAttribute(WebAttrConstant.CITY_OPTION, orgOption);
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, deptOption);
				req.setAttribute(WebAttrConstant.BARCODE_RECEIVE_DTO, dtoParameter);				
				forwardURL = URLDefineList.BARCODE_RECEIVE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {  //在数据库中新增标签领用维护				
				barcodeReceiveDAO.createData();
				message = barcodeReceiveDAO.getMessage();
				message = getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
				message.addParameterValue("标签领用记录");
				req.setAttribute(WebAttrConstant.BARCODE_RECEIVE_DTO, dtoParameter);
				forwardURL = URLDefineList.BARCODE_RECEIVE_PAGE;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {    //修改操作			
				barcodeReceiveDAO.updateData();
				message = barcodeReceiveDAO.getMessage();
				message = getMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
				message.addParameterValue("标签领用记录");
				forwardURL = URLDefineList.BARCODE_RECEIVE_PAGE;
			} else if(action.equals(WebActionConstant.DELETE_ACTION)){
				barcodeReceiveDAO.deleteData();
				message = barcodeReceiveDAO.getMessage();
				message = getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
				message.addParameterValue("标签领用记录");
				dtoParameter = new BarcodeReceiveDTO();
				req.setAttribute(WebAttrConstant.BARCODE_RECEIVE_DTO, dtoParameter);
				orgOption = op.getAllOrganization(userAccount.getOrganizationId());   //用户所能选择的OU,就是地市
				deptOption = op.getDeptOption(userAccount.getCompanyCode());//得到地市下的所有领用部门
				req.setAttribute(WebAttrConstant.CITY_OPTION, orgOption);
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, deptOption);
				forwardURL =URLDefineList.BARCODE_RECEIVE_PAGE;
			} else if(action.equals(WebActionConstant.DETAIL_ACTION)){  //展现标签领用维护记录详细信息
				barcodeReceiveDAO.setDTOClassName(BarcodeReceiveDTO.class.getName());
				BarcodeReceiveDTO brDTO = (BarcodeReceiveDTO)barcodeReceiveDAO.getDataByPrimaryKey();
				if(brDTO == null){
					brDTO = new BarcodeReceiveDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
//				req.getParameter("")
				orgOption = op.getAllOrganization(brDTO.getOrganizationId(), true);   //用户所能选择的OU,就是地市
				deptOption = op.getDeptOption1(barcodeReceiveDAO.getCompanyCodeByOrganization(brDTO.getOrganizationId()), brDTO.getReceiveDept());//得到地市下的所有领用部门
				
				req.setAttribute(WebAttrConstant.CITY_OPTION, orgOption);
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, deptOption);
				req.setAttribute(WebAttrConstant.BARCODE_RECEIVE_DTO, brDTO);
				forwardURL =URLDefineList.BARCODE_RECEIVE_DETAIL_PAGE;
			} else if (action.equals("CHOOSE_GROUP")) {
				res.setContentType("text/xml;charset=GBK");
				pw = res.getWriter();
				RowSet rows = barcodeReceiveDAO.getDeptByOu(organizationId);
				Row row = null;
				if (rows != null && rows.getSize() > 0) {
					for (int i = 0; i < rows.getSize(); i++) {
						row = rows.getRow(i);
						retArray.put(i, row.getValue("DEPT_CODE").toString() + "$" + row.getValue("DEPT_NAME"));
					}
				}
			}else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			} 
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException e) {
			e.printStackTrace();
		} catch (PoolException e) {
			
			e.printStackTrace();
		} catch (ContainerException e) {
			
			e.printStackTrace();
		} catch (JSONException e) {
			
			e.printStackTrace();
		} catch (UploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if (!forwardURL.equals("")) {
				if (message != null && !message.getMessageValue().equals("") && !message.getMessageValue().trim().equals("导入数据存在错误，请检查数据")) {
					forwarder.forwardOpenerView(forwardURL, message.getMessageValue());
				} else {
					forwarder.forwardView(forwardURL);
				}
			} else {
				pw.print(retArray.toString());
				pw.flush();
				pw.close();
			}
		}
	}

    private DTOSet parseExcel2DTO(HttpServletRequest req, String conFilePath) throws UploadException {
        DTOSet dtoSet = null;
        try {
            RequestParser reqPar = new RequestParser();
            reqPar.transData(req);
            UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
            uploadFileSaver.saveFiles(conFilePath);
            UploadRow uploadRow = uploadFileSaver.getRow();
            UploadFile[] upFiles = uploadRow.getFiles();
            if (upFiles != null) {
                if (upFiles.length == 1 && !upFiles[0].getFileName().equals("")) {
                    UploadFile uploadFile = upFiles[0];
                    String fileName = uploadFile.getAbsolutePath();
                    ReadBarcodeReceiveServlet xlsUtil = new ReadBarcodeReceiveServlet();
                    xlsUtil.setFileName(fileName);
                    xlsUtil.setNumberOfColumn(columnNum);
                    xlsUtil.setStartRowNum(startRowNum);
                    dtoSet = xlsUtil.readXls(0);
                }
            }
        } catch (IOException ex) {
            Logger.logError(ex);
            throw new UploadException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new UploadException(ex.getMessage());
        }
        return dtoSet;
    }
}
